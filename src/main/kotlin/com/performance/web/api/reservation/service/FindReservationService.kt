package com.performance.web.api.reservation.service

import com.performance.web.api.reservation.domain.Reservation
import com.performance.web.api.reservation.domain.ReservationRepository
import com.performance.web.api.reservation.service.usecase.FindReservationUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FindReservationService(
    private val reservationRepository: ReservationRepository,
) : FindReservationUseCase {

    override fun findById(reservationId: Long): Reservation {
        return reservationRepository.findByIdThrown(reservationId)
    }
}
