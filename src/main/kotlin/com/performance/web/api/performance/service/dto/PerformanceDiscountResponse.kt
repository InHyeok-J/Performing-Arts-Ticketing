package com.performance.web.api.performance.service.dto

import com.performance.web.api.discount.domain.DiscountPolicy
import com.performance.web.api.performance.domain.PerformanceSeatClass

data class PerformanceDiscountResponse(
    val performanceSeatClass: PerformanceSeatClass,
    val discountPolies: List<DiscountPolicy>,
)
