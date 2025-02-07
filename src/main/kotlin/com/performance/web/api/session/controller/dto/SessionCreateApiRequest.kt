package com.performance.web.api.session.controller.dto

import com.performance.web.api.session.service.dto.SessionCreateCommand
import java.time.LocalDateTime

data class SessionCreateApiRequest(
    val performanceId: Long,
    val startDateTime: LocalDateTime,
) {

    fun toCommand(): SessionCreateCommand {
        return SessionCreateCommand(
            performanceId = performanceId,
            startDateTime = startDateTime,
        )
    }
}
