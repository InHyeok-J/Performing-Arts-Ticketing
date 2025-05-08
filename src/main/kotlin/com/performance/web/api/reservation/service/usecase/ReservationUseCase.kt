package com.performance.web.api.reservation.service.usecase

import com.performance.web.api.reservation.domain.Reservation
import com.performance.web.api.reservation.service.dto.ReservationCommand

interface ReservationUseCase {

    fun reserve(reservationCommand: ReservationCommand): Reservation
}
