package com.performance.web.api.discount.infrastructure.jpa

import com.performance.web.api.discount.domain.DiscountCondition
import jakarta.persistence.*

@Entity
@Table(name = "discount_condition")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "condition_type")
abstract class DiscountConditionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    var discountPolicy: DiscountPolicyEntity? = null,
) {

    abstract fun toDomain(): DiscountCondition
}
