package com.performance.web.api.session.controller.dto

import com.performance.web.api.session.domain.Session
import java.time.LocalDateTime

data class SessionApiResponse(
    val id: Long,
    val performanceId: Long,
    val startDateTime: LocalDateTime,
) {

    companion object {
        fun from(session: Session): SessionApiResponse =
            SessionApiResponse(
                id = session.getId(),
                performanceId = session.getPerformanceId(),
                startDateTime = session.getStartDateTime(),
            )
    }
}
