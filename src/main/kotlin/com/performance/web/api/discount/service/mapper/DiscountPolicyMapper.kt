package com.performance.web.api.discount.service.mapper

import com.performance.web.api.common.domain.BusinessException
import com.performance.web.api.common.utils.checkNotNullOrThrow
import com.performance.web.api.discount.domain.DiscountPolicy
import com.performance.web.api.discount.domain.PercentDiscountPolicy
import com.performance.web.api.discount.service.dto.DiscountCreateCommand

class DiscountPolicyMapper {

    enum class PolicyType {
        PERCENT,
    }

    companion object {
        fun map(command: DiscountCreateCommand): DiscountPolicy =
            when (command.type) {
                PolicyType.PERCENT.name ->
                    PercentDiscountPolicy(
                        name = command.name,
                        percent = command.percent.checkNotNullOrThrow("discount percent"),
                        seatClassId = command.seatClassId,
                        conditions = command.conditions.map { DiscountConditionMapper.map(it) }.toTypedArray(),
                    )

                else -> throw BusinessException("Unsupported discount policy type ${command.type}")
            }
    }
}
