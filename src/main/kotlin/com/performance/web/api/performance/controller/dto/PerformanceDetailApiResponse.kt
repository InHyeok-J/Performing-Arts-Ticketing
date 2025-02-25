package com.performance.web.api.performance.controller.dto

import com.performance.web.api.performance.domain.Performance
import com.performance.web.api.performance.domain.PerformanceSeatClass
import java.time.LocalDate
import java.time.LocalDateTime

class PerformanceDetailApiResponse(

    val id: Long,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val description: String,
    val poster: String,
    val location: String,
    val runTime: Long,
    val seatClassInfos: List<SeatClassApiResponse>,
) {

    data class SeatClassApiResponse(
        val classType: String,
        val price: Long
    ) {
        companion object {
            fun from(seatClass: PerformanceSeatClass): SeatClassApiResponse {
                return SeatClassApiResponse(
                    classType = seatClass.getClassType(),
                    price = seatClass.getPrice().longValue(),
                )
            }
        }
    }

    companion object {
        fun from(performance: Performance): PerformanceDetailApiResponse {
            return PerformanceDetailApiResponse(
                id = performance.getId(),
                name = performance.getName(),
                startDate = performance.getStartDate(),
                endDate = performance.getEndDate(),
                description = performance.getDescription(),
                runTime = performance.getRunTime(),
                seatClassInfos = performance.getSeatClasses().map { SeatClassApiResponse.from(it) },
                poster = performance.getPoster(),
                location = performance.getLocation()
            )
        }
    }
}
