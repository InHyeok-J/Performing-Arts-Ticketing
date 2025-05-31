package com.performance.web.api.reservation.domain

import com.performance.web.api.member.domain.Member
import com.performance.web.api.performance.domain.Performance

data class ReservationConfirmEvent(
    val reservation: Reservation,
    val performance: Performance,
    val member: Member
) {
}
