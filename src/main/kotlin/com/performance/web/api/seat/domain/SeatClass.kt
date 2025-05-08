package com.performance.web.api.seat.domain

import com.performance.web.api.common.domain.Money

data class SeatClass(
    val price: Money,
    val classType: String,
)
