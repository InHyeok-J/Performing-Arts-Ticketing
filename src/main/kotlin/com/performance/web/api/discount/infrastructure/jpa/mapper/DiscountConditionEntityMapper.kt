package com.performance.web.api.discount.infrastructure.jpa.mapper

import com.performance.web.api.discount.domain.DateRangeCondition
import com.performance.web.api.discount.domain.DiscountCondition
import com.performance.web.api.discount.domain.TimeRangeCondition
import com.performance.web.api.discount.infrastructure.jpa.DateRangeConditionEntity
import com.performance.web.api.discount.infrastructure.jpa.DiscountConditionEntity
import com.performance.web.api.discount.infrastructure.jpa.DiscountPolicyEntity
import com.performance.web.api.discount.infrastructure.jpa.TimeRangeConditionEntity

class DiscountConditionEntityMapper {

    companion object {

        fun fromDomainToEntity(
            discountCondition: DiscountCondition,
            discountPolicyEntity: DiscountPolicyEntity,
        ): DiscountConditionEntity =
            when (discountCondition) {
                is TimeRangeCondition -> fromTimeRangeToEntity(discountCondition, discountPolicyEntity)
                is DateRangeCondition -> fromDateRangeToEntity(discountCondition, discountPolicyEntity)
                else -> throw IllegalArgumentException(
                    "Discount condition type ${discountCondition.javaClass} not supported",
                )
            }

        private fun fromTimeRangeToEntity(
            timeRangeCondition: TimeRangeCondition,
            discountPolicyEntity: DiscountPolicyEntity,
        ): TimeRangeConditionEntity =
            TimeRangeConditionEntity(
                id = timeRangeCondition.getId(),
                startTime = timeRangeCondition.getStartTime(),
                endTime = timeRangeCondition.getEndTime(),
                discountPolicyEntity = discountPolicyEntity,
            )

        private fun fromDateRangeToEntity(
            dateRangeCondition: DateRangeCondition,
            discountPolicyEntity: DiscountPolicyEntity,
        ): DateRangeConditionEntity =
            DateRangeConditionEntity(
                id = dateRangeCondition.getId(),
                startDate = dateRangeCondition.getStartDate(),
                endDate = dateRangeCondition.getEndDate(),
                discountPolicyEntity = discountPolicyEntity,
            )
    }
}
