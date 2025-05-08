package com.performance.web.api.performance.service.dto

import com.performance.web.api.performance.domain.Performance

data class PerformancePagingResponse(
    val performances: List<Performance>,
    val totalPage: Int,
)
