package com.performance.web.api.performance.service

import com.performance.web.api.common.domain.ResourceNotFoundException
import com.performance.web.api.discount.domain.DiscountPolicy
import com.performance.web.api.discount.domain.DiscountPolicyRepository
import com.performance.web.api.performance.domain.Performance
import com.performance.web.api.performance.domain.PerformanceRepository
import com.performance.web.api.performance.service.dto.PerformanceCreateCommand
import com.performance.web.api.performance.service.dto.PerformanceDiscountResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class PerformanceService(
    private val performanceRepository: PerformanceRepository,
    private val discountPolicyRepository: DiscountPolicyRepository
) {


    @Transactional(readOnly = true)
    fun findById(id: Long): Performance {
        return performanceRepository.findById(id)
            .orElseThrow { throw ResourceNotFoundException("$id 에 해당하는 Performance를 찾을 수 없습니다.") }
    }

    @Transactional(readOnly = true)
    fun findAll(): List<Performance> {
        return performanceRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun findSeatClassByIdWithDiscounts(id: Long): List<PerformanceDiscountResponse> {
        val performance: Performance = performanceRepository.findByIdThrown(id)

        val discounts: List<DiscountPolicy> =
            discountPolicyRepository.findAllByPerformanceSeatClassIds(performance.getSeatClasses().map { it.getId() })

        val discountGroupByPerformanceIds: Map<Long, List<DiscountPolicy>> = discounts.groupBy {
            it.getPerformanceSeatClassId()
        }

        return performance.getSeatClasses().map {
            PerformanceDiscountResponse(
                performanceSeatClass = it,
                discountPolies = discountGroupByPerformanceIds[it.getId()] ?: emptyList()
            )
        }
    }


    @Transactional
    fun createPerformance(performanceCreateCommand: PerformanceCreateCommand) : Performance {
        val performance = performanceCreateCommand.toEntity()
        return performanceRepository.save(performance)
    }
}
