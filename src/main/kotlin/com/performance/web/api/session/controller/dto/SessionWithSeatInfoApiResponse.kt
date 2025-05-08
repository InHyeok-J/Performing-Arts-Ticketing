package com.performance.web.api.session.controller.dto

import com.performance.web.api.session.service.dto.SessionWithSeatInfoResponse
import java.time.LocalDateTime

data class SessionWithSeatInfoApiResponse(
    val id: Long,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val seatCurrentStatus: List<SeatCurrentStatusApiResponse>,
) {

    data class SeatCurrentStatusApiResponse(
        val classType: String,
        val remain: Long,
    )

    companion object {
        fun from(sessionWithSeatInfo: SessionWithSeatInfoResponse): SessionWithSeatInfoApiResponse =
            SessionWithSeatInfoApiResponse(
                id = sessionWithSeatInfo.id,
                startDateTime = sessionWithSeatInfo.startDateTime,
                endDateTime = sessionWithSeatInfo.endDateTime,
                seatCurrentStatus =
                    sessionWithSeatInfo.seatCurrentStatus.map {
                        SeatCurrentStatusApiResponse(
                            classType = it.classType,
                            remain = it.remain,
                        )
                    },
            )
    }
}
