package com.performance.web.api.fixtures

import com.performance.web.api.common.domain.Money
import com.performance.web.api.performance.domain.PerformanceSeatClass

class PerformanceSeatClassFixture {

    companion object {

        fun create(
            id: Long = 0L,
            classType: String = "VIP",
            price: Money = Money.of(10000),
        ): PerformanceSeatClass =
            PerformanceSeatClass(
                id = id,
                classType = classType,
                price = price,
            )
    }
}
