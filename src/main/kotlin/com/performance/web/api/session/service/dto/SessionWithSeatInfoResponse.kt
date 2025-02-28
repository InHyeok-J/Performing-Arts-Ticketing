package com.performance.web.api.session.service.dto

import java.time.LocalDateTime

data class SessionWithSeatInfoResponse(
    val id: Long,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val seatCurrentStatus: List<SeatCurrentStatus>
) {

    data class SeatCurrentStatus(
        val classType: String,
        val remain: Long
    )


}
