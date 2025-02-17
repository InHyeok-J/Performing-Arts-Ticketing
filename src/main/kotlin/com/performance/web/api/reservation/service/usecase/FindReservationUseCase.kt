package com.performance.web.api.reservation.service.usecase

import com.performance.web.api.reservation.domain.Reservation

interface FindReservationUseCase {

    fun findById(reservationId: Long): Reservation
}
