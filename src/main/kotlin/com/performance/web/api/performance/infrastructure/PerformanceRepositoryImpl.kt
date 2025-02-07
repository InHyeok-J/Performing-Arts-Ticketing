package com.performance.web.api.performance.infrastructure

import com.performance.web.api.performance.domain.Performance
import com.performance.web.api.performance.domain.PerformanceRepository
import com.performance.web.api.performance.infrastructure.jpa.PerformanceEntity
import com.performance.web.api.performance.infrastructure.jpa.PerformanceJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class PerformanceRepositoryImpl(
    private val performanceJpaRepository: PerformanceJpaRepository
) : PerformanceRepository {

    override fun findById(id: Long): Optional<Performance> {
        return performanceJpaRepository.findByIdWithSeatClass(id).map { it.toDomain() }
    }

    override fun findByPage(pageNum: Int): List<Performance> {
        TODO("Not yet implemented")
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
}
