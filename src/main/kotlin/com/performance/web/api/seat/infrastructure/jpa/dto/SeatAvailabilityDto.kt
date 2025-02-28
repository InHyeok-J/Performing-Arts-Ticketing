package com.performance.web.api.seat.infrastructure.jpa.dto

data class SeatAvailabilityDto(
    val sessionId: Long,
    val classType: String,
    val count: Long
) {

}
