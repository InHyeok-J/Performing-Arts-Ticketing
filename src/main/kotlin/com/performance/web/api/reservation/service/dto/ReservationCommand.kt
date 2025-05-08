package com.performance.web.api.reservation.service.dto

data class ReservationCommand(
    val customerId: Long,
    val sessionId: Long,
    val seatCommands: List<ReservationSeatCommand>,
) {

    data class ReservationSeatCommand(
        val seatId: Long,
        val discountPolicyId: Long?,
    )
}
