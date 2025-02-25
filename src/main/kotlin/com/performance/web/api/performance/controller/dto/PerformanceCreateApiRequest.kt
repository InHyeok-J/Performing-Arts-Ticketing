package com.performance.web.api.performance.controller.dto

import com.performance.web.api.common.domain.Money
import com.performance.web.api.performance.service.dto.PerformanceCreateCommand
import java.time.LocalDate

data class PerformanceCreateApiRequest(
    val name: String,
    val runTime: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val description: String,
    val poster: String,
    val location: String,
    val seatClasses: List<PerformanceSeatClassCreateApiRequest>
) {


    fun toCommand(): PerformanceCreateCommand {
        return PerformanceCreateCommand(
            name = name,
            runTime = runTime,
            startDate = startDate,
            endDate = endDate,
            description = description,
            poster = poster,
            location = location,
            seatClasses = seatClasses.map { it.toCommand() },
        )
    }


    data class PerformanceSeatClassCreateApiRequest(
        val classType: String,
        val price: Long
    ) {

        fun toCommand(): PerformanceCreateCommand.PerformanceSeatClassCreateCommand {
            return PerformanceCreateCommand.PerformanceSeatClassCreateCommand(
                classType = classType,
                price = Money.of(price),
            )
        }
    }
}
