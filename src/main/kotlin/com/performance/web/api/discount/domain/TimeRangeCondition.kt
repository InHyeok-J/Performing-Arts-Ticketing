package com.performance.web.api.discount.domain

import java.time.LocalTime

class TimeRangeCondition(
    id: Long = 0L,
    startTime: LocalTime,
    endTime: LocalTime,
) : DiscountCondition {

    private val _id: Long = id
    private val _startTime: LocalTime = startTime
    private val _endTime: LocalTime = endTime

    override fun isSatisfiedBy(discountFactor: DiscountFactor): Boolean {
        val reserveDateTime = discountFactor.reserveDateTime

        val reserveTime = reserveDateTime.toLocalTime()
        val isTimeInRange = !reserveTime.isBefore(_startTime) && !reserveTime.isAfter(_endTime)

        return isTimeInRange
    }

    fun getId(): Long = _id

    fun getStartTime(): LocalTime = _startTime

    fun getEndTime(): LocalTime = _endTime
}
