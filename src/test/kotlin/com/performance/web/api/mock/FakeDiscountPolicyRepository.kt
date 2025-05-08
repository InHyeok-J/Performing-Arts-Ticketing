package com.performance.web.api.mock

import com.performance.web.api.discount.domain.*
import java.util.*

class FakeDiscountPolicyRepository : DiscountPolicyRepository {

    private var autoIncrementId = 1L
    private var autoIncrementConditionId = 1L
    private var store = mutableMapOf<Long, DiscountPolicy>()

    override fun findById(id: Long): Optional<DiscountPolicy> {
        return Optional.ofNullable(store[id])
    }

    override fun findAllByPerformanceSeatClassIds(ids: List<Long>): List<DiscountPolicy> {
        val list = mutableListOf<DiscountPolicy>()
        store.values.map {
            if (ids.contains(it.getPerformanceSeatClassId())) {
                list.add(it)
            }
        }
        return list
    }

    override fun save(policy: DiscountPolicy): DiscountPolicy {
        val policy = policyUpdateHelper(policy)
        store.put(policy.getId(), policy)
        return policy
    }

    private fun policyUpdateHelper(discountPolicy: DiscountPolicy): DiscountPolicy =
        when (discountPolicy) {
            is PercentDiscountPolicy -> {
                val percentPolicy: PercentDiscountPolicy = discountPolicy
                PercentDiscountPolicy(
                    id = if (percentPolicy.getId() == 0L) autoIncrementId++ else percentPolicy.getId(),
                    name = percentPolicy.getName(),
                    percent = percentPolicy.getPercent(),
                    seatClassId = percentPolicy.getPerformanceSeatClassId(),
                    conditions = discountPolicy.getConditions().map { conditionUpdaterHelper(it) }.toTypedArray(),
                )
            }
            is NoneDiscountPolicy -> NoneDiscountPolicy()
            else -> throw IllegalArgumentException("Unsupported discount policy type ${discountPolicy::class.java}")
        }

    private fun conditionUpdaterHelper(discountCondition: DiscountCondition): DiscountCondition =
        when (discountCondition) {
            is TimeRangeCondition -> {
                val timeCondition: TimeRangeCondition = discountCondition
                TimeRangeCondition(
                    id = if (timeCondition.getId() == 0L) autoIncrementConditionId++ else timeCondition.getId(),
                    startTime = timeCondition.getStartTime(),
                    endTime = timeCondition.getEndTime(),
                )
            }

            is DateRangeCondition -> {
                val dateCondition: DateRangeCondition = discountCondition
                DateRangeCondition(
                    id = if (dateCondition.getId() == 0L) autoIncrementConditionId++ else dateCondition.getId(),
                    startDate = dateCondition.getStartDate(),
                    endDate = dateCondition.getEndDate(),
                )
            }

            else -> throw IllegalArgumentException("잘못된 discount condition")
        }
}
