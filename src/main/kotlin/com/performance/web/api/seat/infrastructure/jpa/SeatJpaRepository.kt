package com.performance.web.api.seat.infrastructure.jpa

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface SeatJpaRepository : JpaRepository<SeatEntity, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    fun findAllBySessionId(sessionId: Long): List<SeatEntity>
}
