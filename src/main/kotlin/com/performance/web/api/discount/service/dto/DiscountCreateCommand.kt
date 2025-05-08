package com.performance.web.api.discount.service.dto

import java.time.LocalDate
import java.time.LocalTime

data class DiscountCreateCommand(
    val performanceId: Long,
    val name: String,
    val type: String,
    val percent: Double?,
    val seatClassId: Long,
    val conditions: List<DiscountConditionCreateCommand>,
) {

    data class DiscountConditionCreateCommand(
        val type: String,
        val startDate: LocalDate?,
        val endDate: LocalDate?,
        val startTime: LocalTime?,
        val endTime: LocalTime?,
    )
}
