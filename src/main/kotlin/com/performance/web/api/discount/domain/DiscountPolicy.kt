package com.performance.web.api.discount.domain

import com.performance.web.api.common.domain.BusinessException
import com.performance.web.api.common.domain.Money

abstract class DiscountPolicy protected constructor(
    id: Long,
    name: String,
    performanceSeatClassId: Long,
    vararg conditions: DiscountCondition,
) {

    private val _id: Long = id
    private val _name: String = name
    private val _conditions: List<DiscountCondition> = conditions.toList()
    private val _performanceSeatClassId: Long = performanceSeatClassId

    fun calculateFee(
        price: Money,
        discountFactor: DiscountFactor,
    ): Money {
        for (condition in _conditions) {
            if (!condition.isSatisfiedBy(discountFactor)) {
                throw BusinessException("할인 조건이 맞지 않습니다")
            }
        }

        return getDiscountAmount(price)
    }

    fun calculateDiscountAmountWithoutCondition(price: Money): Money = getDiscountAmount(price)

    fun getId(): Long = _id

    fun getName(): String = _name

    fun getConditions(): List<DiscountCondition> = _conditions

    fun getPerformanceSeatClassId(): Long = _performanceSeatClassId

    protected abstract fun getDiscountAmount(price: Money): Money

    companion object {

        fun none(): DiscountPolicy = NoneDiscountPolicy()
    }
}
