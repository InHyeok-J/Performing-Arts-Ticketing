package com.performance.web.api.discount.infrastructure.jpa

import com.performance.web.api.discount.domain.DiscountCondition
import com.performance.web.api.discount.domain.TimeRangeCondition
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.time.LocalTime

@Entity
@DiscriminatorValue("TIME_RANGE")
class TimeRangeConditionEntity(
    id: Long,
    discountPolicyEntity: DiscountPolicyEntity? = null,
    @Column(nullable = true)
    var startTime: LocalTime,
    @Column(nullable = true)
    var endTime: LocalTime,
) : DiscountConditionEntity(id, discountPolicyEntity) {

    override fun toDomain(): DiscountCondition =
        TimeRangeCondition(
            id = this.id,
            startTime = startTime,
            endTime = endTime,
        )
}
