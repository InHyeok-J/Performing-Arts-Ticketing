package com.performance.web.api.discount.controller.dto

import com.performance.web.api.discount.service.dto.DiscountCreateCommand
import java.time.LocalDate
import java.time.LocalTime

data class DiscountCreateApiRequest(
    val performanceId: Long,
    val name: String,
    val type: String,
    val percent: Double?,
    val seatClassId: Long,
    val conditions: List<DiscountConditionApiRequest>,
) {
    fun toCommand(): DiscountCreateCommand =
        DiscountCreateCommand(
            performanceId = performanceId,
            name = name,
            type = type,
            percent = percent,
            seatClassId = seatClassId,
            conditions = conditions.map { it.toCommand() },
        )

    data class DiscountConditionApiRequest(
        val type: String,
        val startDate: LocalDate?,
        val endDate: LocalDate?,
        val startTime: LocalTime?,
        val endTime: LocalTime?,
    ) {

        fun toCommand(): DiscountCreateCommand.DiscountConditionCreateCommand =
            DiscountCreateCommand.DiscountConditionCreateCommand(
                type = this.type,
                startDate = this.startDate,
                endDate = this.endDate,
                startTime = this.startTime,
                endTime = this.endTime,
            )
    }
}
