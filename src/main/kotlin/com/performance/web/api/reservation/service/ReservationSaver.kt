package com.performance.web.api.reservation.service

import com.performance.web.api.common.domain.BusinessException
import com.performance.web.api.common.utils.Logger
import com.performance.web.api.reservation.domain.Customer
import com.performance.web.api.reservation.domain.PerformanceSessionInfo
import com.performance.web.api.reservation.domain.Reservation
import com.performance.web.api.reservation.domain.ReservationCodeGenerator
import com.performance.web.api.reservation.domain.ReservationRepository
import com.performance.web.api.reservation.domain.Ticket
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class ReservationSaver(
    private val reservationRepository: ReservationRepository,
    private val reservationCodeGenerator: ReservationCodeGenerator
) {

    companion object {
        private const val RETRY_COUNT = 3

    }

    fun saveAndRetry(command: ReservationSaveCommand): Reservation {
        repeat(RETRY_COUNT) {
            val code = reservationCodeGenerator.generate()
            val reservation = Reservation(
                reservationCode = code,
                performanceSessionInfo = command.performanceSessionInfo,
                customer = command.customer,
                tickets = command.tickets,
            )

            try {
                return reservationRepository.save(reservation)
            } catch (ex: DataIntegrityViolationException) {
                // 해당 예외만 캐치 재시도
                Logger.error("ReservationSaver", "에러코드 중복 발생", ex)
            }
        }
        throw BusinessException("알수 없는 예외입니다, 다시한번 시도해주세요.")
    }


    data class ReservationSaveCommand(
        val performanceSessionInfo: PerformanceSessionInfo,
        val customer: Customer,
        val tickets: List<Ticket>
    )
}
