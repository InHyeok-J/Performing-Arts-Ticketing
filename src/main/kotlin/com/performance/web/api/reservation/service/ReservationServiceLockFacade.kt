package com.performance.web.api.reservation.service

import com.performance.web.api.common.domain.BusinessException
import com.performance.web.api.reservation.domain.Reservation
import com.performance.web.api.reservation.service.dto.ReservationCommand
import com.performance.web.api.reservation.service.usecase.ReservationUseCase
import org.springframework.dao.PessimisticLockingFailureException
import org.springframework.stereotype.Component

@Component
class ReservationServiceLockFacade(
    private val reservationService: ReservationService,
) : ReservationUseCase {

    override fun reserve(reservationCommand: ReservationCommand): Reservation {
        try {
            return reservationService.reserve(reservationCommand)
        } catch (e: PessimisticLockingFailureException) {
            // 비관적 잠금 실패
            println("비관적 잠금 실패 -  ${e.message}")
            throw BusinessException("이미 예매된 좌석입니다. 다시 시도해주세요.")
        }
    }
}
