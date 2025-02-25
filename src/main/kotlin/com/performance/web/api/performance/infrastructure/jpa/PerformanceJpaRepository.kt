package com.performance.web.api.performance.infrastructure.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface PerformanceJpaRepository : JpaRepository<PerformanceEntity, Long> {

    @Query(
        "SELECT p FROM PerformanceEntity p "+
        "LEFT JOIN FETCH p.seatClasses " +
        "WHERE p.id = :performanceId "
    )
    fun findByIdWithSeatClass(@Param("performanceId") id : Long) : Optional<PerformanceEntity>

}
