package com.performance.web.api.reservation.service.dto

import com.performance.web.api.performance.domain.Performance
import com.performance.web.api.reservation.domain.Reservation
import com.performance.web.api.session.domain.Session

data class ReservationResponse(
    val reservation: Reservation,
    val session: Session,
    val performance: Performance,
)
