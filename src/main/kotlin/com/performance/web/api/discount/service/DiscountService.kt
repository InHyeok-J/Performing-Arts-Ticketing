package com.performance.web.api.discount.service

import com.performance.web.api.discount.domain.DiscountPolicy
import com.performance.web.api.discount.domain.DiscountPolicyRepository
import com.performance.web.api.discount.service.dto.DiscountCreateCommand
import com.performance.web.api.discount.service.mapper.DiscountPolicyMapper
import com.performance.web.api.performance.domain.PerformanceRepository
import org.springframework.stereotype.Service

@Service
class DiscountService(
    private val discountPolicyRepository: DiscountPolicyRepository,
    private val performanceRepository: PerformanceRepository,
) {

    fun findById(id: Long): DiscountPolicy = discountPolicyRepository.findByIdThrown(id)

    fun create(command: DiscountCreateCommand): DiscountPolicy {
        val performance = performanceRepository.findByIdThrown(command.performanceId)
        performance.checkClassId(command.seatClassId)

        val discountPolicy = DiscountPolicyMapper.map(command)
        return discountPolicyRepository.save(discountPolicy)
    }
}
