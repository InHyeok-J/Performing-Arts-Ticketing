package com.performance.web.api.reservation.domain

import com.performance.web.api.common.domain.BaseRepository

interface ReservationRepository : BaseRepository<Reservation> {

    fun save(reservation: Reservation): Reservation
}
