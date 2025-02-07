package com.performance.web.api.session.domain

import java.time.LocalDateTime

class Session(
    id: Long = 0L,
    performanceId: Long,
    startDateTime: LocalDateTime,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
) {

    private val _id: Long = id
    private val _performanceId: Long = performanceId
    private val _startDateTime: LocalDateTime = startDateTime
    private val _createdAt: LocalDateTime = createdAt
    private val _updatedAt: LocalDateTime = updatedAt

    fun getId(): Long = _id

    fun getPerformanceId(): Long = _performanceId

    fun getStartDateTime(): LocalDateTime = _startDateTime

    fun getCreatedAt(): LocalDateTime = _createdAt
    fun getUpdatedAt(): LocalDateTime = _updatedAt
}
