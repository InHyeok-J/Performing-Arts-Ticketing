package com.performance.web.api.discount.domain

import java.time.LocalDate

class DateRangeCondition(
    id: Long = 0L,
    startDate: LocalDate,
    endDate: LocalDate,
) : DiscountCondition {

    private val _id: Long = id
    private val _startDate: LocalDate = startDate
    private val _endDate: LocalDate = endDate

    override fun isSatisfiedBy(discountFactor: DiscountFactor): Boolean {
        val reserveDate = discountFactor.reserveDateTime.toLocalDate()
        return !reserveDate.isBefore(_startDate) && !reserveDate.isAfter(_endDate)
    }

    fun getId(): Long = _id

    fun getStartDate(): LocalDate = _startDate

    fun getEndDate(): LocalDate = _endDate
}
