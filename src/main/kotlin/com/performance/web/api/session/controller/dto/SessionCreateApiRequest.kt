package com.performance.web.api.session.controller.dto

import com.performance.web.api.session.service.dto.SessionCreateCommand
import java.time.LocalDateTime

data class SessionCreateApiRequest(
    val performanceId: Long,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
) {

    fun toCommand(): SessionCreateCommand =
        SessionCreateCommand(
            performanceId = performanceId,
            startDateTime = startDateTime,
            endDateTime = endDateTime,
        )
}
