package com.performance.web.api.performance.controller.dto

import com.performance.web.api.common.domain.Money
import com.performance.web.api.discount.domain.DiscountPolicy
import com.performance.web.api.performance.service.dto.PerformanceDiscountResponse

data class PerformanceDiscountApiResponse(
    val id : Long,
    val seatClassType: String,
    val price: Long,
    val discountInfo: List<DiscountInfoApiResponse>
) {

    companion object {

        fun from(response: PerformanceDiscountResponse): PerformanceDiscountApiResponse {
            val seatClass = response.performanceSeatClass
            return PerformanceDiscountApiResponse(
                seatClassType = seatClass.getClassType(),
                price = seatClass.getPrice().longValue(),
                discountInfo = response.discountPolies.map { DiscountInfoApiResponse.from(it, seatClass.getPrice()) },
                id = seatClass.getId()
            )
        }
    }

    data class DiscountInfoApiResponse(
        val id: Long,
        val name: String,
        val fee: Long
    ) {

        companion object {
            fun from(discountPolicy: DiscountPolicy, price: Money): DiscountInfoApiResponse {
                return DiscountInfoApiResponse(
                    id = discountPolicy.getId(),
                    name = discountPolicy.getName(),
                    fee = discountPolicy.calculateDiscountAmountWithoutCondition(price).longValue(),
                )
            }
        }
    }

}
