package com.performance.web.api.discount.infrastructure.jpa.mapper

import com.performance.web.api.discount.domain.DiscountPolicy
import com.performance.web.api.discount.domain.PercentDiscountPolicy
import com.performance.web.api.discount.infrastructure.jpa.DiscountPolicyEntity
import com.performance.web.api.discount.infrastructure.jpa.PercentDiscountPolicyEntity

class DiscountPolicyEntityMapper {

    companion object {

        fun fromDomainToEntity(discountPolicy: DiscountPolicy): DiscountPolicyEntity {
            return when (discountPolicy) {
                is PercentDiscountPolicy -> fromPercentToEntity(discountPolicy)
                else -> throw IllegalArgumentException("Discount policy type not mapped")
            }
        }

        private fun fromPercentToEntity(percentDiscountPolicy: PercentDiscountPolicy): PercentDiscountPolicyEntity {
            val policyEntity =
                PercentDiscountPolicyEntity(
                    id = percentDiscountPolicy.getId(),
                    name = percentDiscountPolicy.getName(),
                    percent = percentDiscountPolicy.getPercent(),
                    performanceSeatClassId = percentDiscountPolicy.getPerformanceSeatClassId(),
                )
            policyEntity.conditions =
                percentDiscountPolicy.getConditions()
                    .map { DiscountConditionEntityMapper.fromDomainToEntity(it, policyEntity) }
                    .toMutableList()

            return policyEntity
        }
    }
}
