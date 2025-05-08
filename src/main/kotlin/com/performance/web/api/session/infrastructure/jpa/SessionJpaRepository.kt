package com.performance.web.api.session.infrastructure.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SessionJpaRepository : JpaRepository<SessionEntity, Long> {

    fun findAllByIdBetween(
        idAfter: Long,
        idBefore: Long,
    ): MutableList<SessionEntity>

    @Query(
        "SELECT s FROM SessionEntity s WHERE YEAR(s.startDateTime) = :year AND MONTH(s.startDateTime) = :month AND s.performanceId = :performanceId",
    )
    fun finAllByYearMonth(
        @Param("performanceId") id: Long,
        @Param("year") year: Int,
        @Param("month") month: Int,
    ): List<SessionEntity>
}
