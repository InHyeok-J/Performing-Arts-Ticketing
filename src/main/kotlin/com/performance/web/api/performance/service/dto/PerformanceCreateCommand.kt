package com.performance.web.api.performance.service.dto

import com.performance.web.api.common.domain.Money
import com.performance.web.api.performance.domain.Performance
import com.performance.web.api.performance.domain.PerformanceSeatClass
import java.time.LocalDate

data class PerformanceCreateCommand(
    val name: String,
    val runTime: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val description: String,
    val poster: String,
    val location: String,
    val seatClasses: List<PerformanceSeatClassCreateCommand>,
) {

    fun toEntity(): Performance =
        Performance(
            name = name,
            runTimeInMinutes = runTime,
            startDate = startDate,
            endDate = endDate,
            description = description,
            poster = poster,
            location = location,
            seatClasses =
                seatClasses.map {
                    PerformanceSeatClass(
                        price = it.price,
                        classType = it.classType,
                    )
                },
        )

    data class PerformanceSeatClassCreateCommand(
        val classType: String,
        val price: Money,
    )
}
