package com.performance.web.api.discount.infrastructure.jpa

import com.performance.web.api.discount.domain.DiscountCondition
import com.performance.web.api.discount.domain.OfflineCheckCondition
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("OFFLINE_CHECK")
class OfflineCheckConditionEntity(
    id: Long,
    discountPolicyEntity: DiscountPolicyEntity,
) : DiscountConditionEntity(id, discountPolicyEntity) {

    override fun toDomain(): DiscountCondition = OfflineCheckCondition()
}
