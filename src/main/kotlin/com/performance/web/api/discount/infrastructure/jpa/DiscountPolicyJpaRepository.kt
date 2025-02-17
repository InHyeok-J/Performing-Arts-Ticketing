package com.performance.web.api.discount.infrastructure.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface DiscountPolicyJpaRepository : JpaRepository<DiscountPolicyEntity, Long> {

    @Query(
        "SELECT d FROM DiscountPolicyEntity d " +
            "LEFT JOIN FETCH d.conditions dc " +
            "WHERE d.id = :discountPolicyId ",
    )
    fun findByIdWithConditions(@Param("discountPolicyId") id: Long): Optional<DiscountPolicyEntity>


    @Query(
        "SELECT d FROM DiscountPolicyEntity d " +
            "LEFT JOIN FETCH d.conditions dc " +
            "JOIN PerformanceSeatClassEntity ps ON ps.id = d.performanceSeatClassId " +
            "WHERE ps.id IN :seatClassIds ",
    )
    fun findAllByPerformanceSeatClassIds(@Param("seatClassIds") ids: List<Long>): List<DiscountPolicyEntity>
}
