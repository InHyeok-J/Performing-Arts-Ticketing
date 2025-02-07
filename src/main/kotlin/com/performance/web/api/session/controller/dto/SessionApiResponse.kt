package com.performance.web.api.session.controller.dto

import com.performance.web.api.session.domain.Session
import java.time.LocalDateTime

data class SessionApiResponse (
    val id: Long,
    val performanceId: Long,
    val startDateTime : LocalDateTime,
    val createdAt : LocalDateTime,
    val updatedAt : LocalDateTime
){

    companion object {
        fun from(session: Session): SessionApiResponse {
            return SessionApiResponse(
                id = session.getId(),
                performanceId = session.getPerformanceId(),
                startDateTime = session.getStartDateTime(),
                createdAt = session.getCreatedAt(),
                updatedAt = session.getUpdatedAt()
            )
        }
    }
}
