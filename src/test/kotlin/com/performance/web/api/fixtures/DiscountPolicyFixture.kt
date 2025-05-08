package com.performance.web.api.fixtures

import com.performance.web.api.discount.domain.DiscountCondition
import com.performance.web.api.discount.domain.DiscountPolicy
import com.performance.web.api.discount.domain.PercentDiscountPolicy

class DiscountPolicyFixture {

    companion object {

        fun createPercent(
            percent: Double = 0.1,
            id: Long = 1L,
            name: String = "퍼센트할인정책",
            vararg conditions: DiscountCondition,
            seatClassId: Long = 1L,
        ): DiscountPolicy =
            PercentDiscountPolicy(
                percent = percent,
                name = name,
                id = id,
                conditions = conditions,
                seatClassId = seatClassId,
            )
    }
}
