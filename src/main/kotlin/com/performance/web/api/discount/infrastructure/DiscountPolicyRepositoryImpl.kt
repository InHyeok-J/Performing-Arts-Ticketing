package com.performance.web.api.discount.infrastructure

import com.performance.web.api.discount.domain.DiscountPolicy
import com.performance.web.api.discount.domain.DiscountPolicyRepository
import com.performance.web.api.discount.infrastructure.jpa.DiscountPolicyJpaRepository
import com.performance.web.api.discount.infrastructure.jpa.mapper.DiscountPolicyEntityMapper
import org.springframework.stereotype.Component
import java.util.*

@Component
class DiscountPolicyRepositoryImpl(
    private val discountPolicyJpaRepository: DiscountPolicyJpaRepository
) : DiscountPolicyRepository {

    override fun findById(id: Long): Optional<DiscountPolicy> {
        return discountPolicyJpaRepository.findByIdWithConditions(id).map { it.toDomain() }
    }

    override fun findAllByPerformanceSeatClassIds(ids: List<Long>): List<DiscountPolicy> {
        return discountPolicyJpaRepository.findAllByPerformanceSeatClassIds(ids).map { it.toDomain() }
    }

    override fun save(policy: DiscountPolicy): DiscountPolicy {
        return discountPolicyJpaRepository.save(DiscountPolicyEntityMapper.fromDomainToEntity(policy)).toDomain()
    }
}
