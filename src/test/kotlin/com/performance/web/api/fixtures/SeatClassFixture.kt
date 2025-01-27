package com.performance.web.api.fixtures

import com.performance.web.api.common.domain.Money
import com.performance.web.api.discount.domain.DiscountPolicy
import com.performance.web.api.reservation.domain.SeatClass

class SeatClassFixture {

    companion object {

        fun create(
            price: Money = Money.of(10000),
            classType: String = "VIP",
            discountPolicies: List<DiscountPolicy> = mutableListOf()
        ): SeatClass =
            SeatClass(
                price = price,
                classType = classType,
                discountPolicies = discountPolicies,
            )
    }
}
