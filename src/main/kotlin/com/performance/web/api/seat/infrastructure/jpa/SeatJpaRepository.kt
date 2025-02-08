package com.performance.web.api.seat.infrastructure.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface SeatJpaRepository : JpaRepository<SeatEntity, Long> {

    fun findAllBySessionId(sessionId: Long): List<SeatEntity>
}
