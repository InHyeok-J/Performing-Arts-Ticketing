package com.performance.web.api.fixtures

import com.performance.web.api.discount.domain.DiscountFactor
import java.time.LocalDateTime

class DiscountFactorFixture {

    companion object {
        fun create(
            reserveDateTime: LocalDateTime = LocalDateTime.now(),
            ticketTotalAmount: Int = 1,
        ): DiscountFactor =
            DiscountFactor(
                reserveDateTime = reserveDateTime,
                ticketTotalAmount = ticketTotalAmount,
            )
    }
}
