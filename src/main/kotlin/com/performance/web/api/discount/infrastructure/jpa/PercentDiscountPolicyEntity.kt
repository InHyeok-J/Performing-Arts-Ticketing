package com.performance.web.api.discount.infrastructure.jpa

import com.performance.web.api.discount.domain.DiscountPolicy
import com.performance.web.api.discount.domain.PercentDiscountPolicy
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("PERCENT")
class PercentDiscountPolicyEntity(
    id: Long,
    name: String,
    conditions: MutableList<DiscountConditionEntity> = mutableListOf(),
    performanceSeatClassId: Long,
    @Column(name = "percent", nullable = true)
    var percent: Double,
) : DiscountPolicyEntity(
        id = id,
        name = name,
        conditions = conditions,
        performanceSeatClassId = performanceSeatClassId,
    ) {

    override fun toDomain(): DiscountPolicy =
        PercentDiscountPolicy(
            id = this.id ?: 0L,
            name = this.name,
            percent = this.percent,
            conditions = this.conditions.map { it.toDomain() }.toTypedArray(),
            seatClassId = performanceSeatClassId,
        )
}
