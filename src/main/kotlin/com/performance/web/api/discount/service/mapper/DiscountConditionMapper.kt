package com.performance.web.api.discount.service.mapper

import com.performance.web.api.common.domain.BusinessException
import com.performance.web.api.common.utils.checkNotNullOrThrow
import com.performance.web.api.discount.domain.DateRangeCondition
import com.performance.web.api.discount.domain.DiscountCondition
import com.performance.web.api.discount.domain.OfflineCheckCondition
import com.performance.web.api.discount.domain.TimeRangeCondition
import com.performance.web.api.discount.service.dto.DiscountCreateCommand

class DiscountConditionMapper {

    enum class ConditionType {
        TIME_RANGE, DATE_RANGE, OFFINE_CHECK
    }

    companion object {

        fun map(command: DiscountCreateCommand.DiscountConditionCreateCommand): DiscountCondition {
            return when (command.type) {
                ConditionType.TIME_RANGE.name ->
                    TimeRangeCondition(
                        startTime = command.startTime.checkNotNullOrThrow("startTime"),
                        endTime = command.endTime.checkNotNullOrThrow("endTime"),
                    )

                ConditionType.DATE_RANGE.name -> DateRangeCondition(
                    startDate = command.startDate.checkNotNullOrThrow("startDate"),
                    endDate = command.endDate.checkNotNullOrThrow("endDate"),
                )

                ConditionType.OFFINE_CHECK.name -> return OfflineCheckCondition()
                else -> throw BusinessException("잘못된 discount Type입니다 ${command.type}")
            }
        }
    }



}
