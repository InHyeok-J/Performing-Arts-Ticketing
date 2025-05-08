package com.performance.web.api.performance.infrastructure

import com.performance.web.api.performance.domain.Performance
import com.performance.web.api.performance.domain.PerformanceRepository
import com.performance.web.api.performance.infrastructure.jpa.PerformanceEntity
import com.performance.web.api.performance.infrastructure.jpa.PerformanceJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.util.*

@Component
class PerformanceRepositoryImpl(
    private val performanceJpaRepository: PerformanceJpaRepository,
) : PerformanceRepository {

    override fun findById(id: Long): Optional<Performance> {
        return performanceJpaRepository.findByIdWithSeatClass(id).map { it.toDomain() }
    }

    override fun findAll(): List<Performance> {
        return performanceJpaRepository.findAll().map { it.toDomain() }
    }

    override fun save(performance: Performance): Performance {
        return performanceJpaRepository.save(PerformanceEntity.fromDomain(performance)).toDomain()
    }

    override fun isExistsById(id: Long): Boolean {
        return performanceJpaRepository.existsById(id)
    }

    override fun findAllByPaging(
        pageNum: Int,
        pageSize: Int,
    ): Pair<List<Performance>, Int> {
        val pagingResult = performanceJpaRepository.findAllWithPaging(PageRequest.of(pageNum, pageSize))
        return Pair(pagingResult.content.map { it.toDomain() }, pagingResult.totalPages)
    }
}
