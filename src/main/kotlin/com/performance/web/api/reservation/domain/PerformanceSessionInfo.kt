package com.performance.web.api.reservation.domain

import com.performance.web.api.performance.domain.Performance
import com.performance.web.api.session.domain.Session
import java.time.LocalDate
import java.time.LocalTime

class PerformanceSessionInfo(
    val performanceName: String,
    val sessionStartDate: LocalDate,
    val sessionStartTime : LocalTime,
    val sessionEndTime: LocalTime,
) {


    companion object {
        fun create(performance: Performance, session:Session): PerformanceSessionInfo {
            return PerformanceSessionInfo(
                performanceName = performance.getName(),
                sessionStartDate = session.getStartDateTime().toLocalDate(),
                sessionStartTime = session.getStartDateTime().toLocalTime(),
                sessionEndTime = session.getEndDateTime().toLocalTime()
            )
        }
    }
}
