package com.performance.web.api.session.domain

import java.time.LocalDateTime

class Session(
    id: Long = 0L,
    performanceId: Long,
    startDateTime: LocalDateTime,
) {

    private val _id: Long = id
    private val _performanceId: Long = performanceId
    private val _startDateTime: LocalDateTime = startDateTime

    fun getId(): Long = _id

    fun getPerformanceId(): Long = _performanceId

    fun getStartDateTime(): LocalDateTime = _startDateTime

}
