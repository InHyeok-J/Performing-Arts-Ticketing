package com.performance.web.api.discount.infrastructure.jpa

import com.performance.web.api.discount.domain.DateRangeCondition
import com.performance.web.api.discount.domain.DiscountCondition
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.time.LocalDate

@Entity
@DiscriminatorValue("DATE_RANGE")
class DateRangeConditionEntity(
    id: Long,
    discountPolicyEntity: DiscountPolicyEntity? = null,
    @Column(nullable = true)
    var startDate: LocalDate,
    @Column(nullable = true)
    var endDate: LocalDate,
) : DiscountConditionEntity(id, discountPolicyEntity) {

    override fun toDomain(): DiscountCondition =
        DateRangeCondition(
            id = id,
            startDate = startDate,
            endDate = endDate,
        )
}
