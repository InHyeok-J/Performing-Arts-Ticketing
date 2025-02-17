package com.performance.web.api.reservation.service

import com.performance.web.api.common.domain.BusinessException
import com.performance.web.api.reservation.domain.Reservation
import com.performance.web.api.reservation.service.dto.ReservationCommand
import com.performance.web.api.reservation.service.usecase.ReservationUseCase
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.stereotype.Component

@Component
class ReservationServiceOptimisticLockFacade(
    private val reservationService: ReservationService
) : ReservationUseCase {

    override fun reserve(reservationCommand: ReservationCommand): Reservation {
        try {
            return reservationService.reserve(reservationCommand)
        } catch (e: ObjectOptimisticLockingFailureException) {
            throw BusinessException("이미 예매된 좌석입니다 ")
        }
    }

}
