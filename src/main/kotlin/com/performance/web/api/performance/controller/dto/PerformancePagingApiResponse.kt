package com.performance.web.api.performance.controller.dto

import com.performance.web.api.common.controller.PageInfo
import com.performance.web.api.performance.domain.Performance
import com.performance.web.api.performance.service.dto.PerformancePagingResponse
import java.time.LocalDate

class PerformancePagingApiResponse(
    val data: List<PerformanceListResponse>,
    val pageInfo: PageInfo,
) {

    data class PerformanceListResponse(
        val id: Long,
        val name: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val poster: String,
    ) {
        companion object {
            fun from(performance: Performance): PerformanceListResponse =
                PerformanceListResponse(
                    id = performance.getId(),
                    name = performance.getName(),
                    startDate = performance.getStartDate(),
                    endDate = performance.getEndDate(),
                    poster = performance.getPoster(),
                )
        }
    }

    companion object {

        fun from(performancePagingResponse: PerformancePagingResponse): PerformancePagingApiResponse =
            PerformancePagingApiResponse(
                data = performancePagingResponse.performances.map { PerformanceListResponse.from(it) },
                pageInfo = PageInfo(performancePagingResponse.totalPage),
            )
    }
}
