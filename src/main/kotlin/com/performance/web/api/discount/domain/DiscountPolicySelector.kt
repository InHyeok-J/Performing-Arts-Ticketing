package com.performance.web.api.discount.domain

import org.springframework.stereotype.Component
import java.util.Optional

@Component
class DiscountPolicySelector(
    private val discountPolicyRepository: DiscountPolicyRepository,
) {

    fun findById(id: Long?): Optional<DiscountPolicy> {
        if (id == null) {
            return Optional.of(DiscountPolicy.none())
        }
        return discountPolicyRepository.findById(id)
    }
}
