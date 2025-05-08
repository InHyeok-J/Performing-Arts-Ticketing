package com.performance.web.api.session.service.dto

import java.time.LocalDateTime

data class SessionCreateCommand(
    val performanceId: Long,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
)
