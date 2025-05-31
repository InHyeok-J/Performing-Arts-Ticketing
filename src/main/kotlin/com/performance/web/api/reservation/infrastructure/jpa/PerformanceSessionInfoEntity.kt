package com.performance.web.api.reservation.infrastructure.jpa

import com.performance.web.api.reservation.domain.PerformanceSessionInfo
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.LocalDate
import java.time.LocalTime

@Embeddable
class PerformanceSessionInfoEntity(
    @Column(nullable = false)
    var sessionId: Long,
    @Column(nullable = false)
    var performanceId: Long,
    @Column(nullable = false)
    var performanceName: String,
    @Column(nullable = false)
    var sessionStartDate: LocalDate,
    @Column(nullable = false)
    var sessionStartTime: LocalTime,
    @Column(nullable = false)
    var sessionEndTime: LocalTime,
) {

    fun toDomain(): PerformanceSessionInfo =
        PerformanceSessionInfo(
            performanceName = performanceName,
            sessionStartDate = sessionStartDate,
            sessionStartTime = sessionStartTime,
            sessionEndTime = sessionEndTime,
            sessionId = sessionId,
            performanceId = performanceId,
        )

    companion object {
        fun fromDomain(performanceSessionInfo: PerformanceSessionInfo): PerformanceSessionInfoEntity =
            PerformanceSessionInfoEntity(
                performanceName = performanceSessionInfo.performanceName,
                sessionStartDate = performanceSessionInfo.sessionStartDate,
                sessionStartTime = performanceSessionInfo.sessionStartTime,
                sessionEndTime = performanceSessionInfo.sessionEndTime,
                sessionId = performanceSessionInfo.sessionId,
                performanceId = performanceSessionInfo.performanceId
            )
    }
}
