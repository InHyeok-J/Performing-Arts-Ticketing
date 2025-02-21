package com.performance.web.api.seat.infrastructure.jpa

import jakarta.persistence.LockModeType
import jakarta.persistence.QueryHint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.data.repository.query.Param
import java.util.Optional

interface SeatJpaRepository : JpaRepository<SeatEntity, Long> {

    fun findAllBySessionId(sessionId: Long): List<SeatEntity>

    @Query(value = "SELECT * FROM seat s WHERE s.id = :seatId FOR update nowait  ", nativeQuery = true)
    fun findByIdWithLock(@Param("seatId") seatId: Long): Optional<SeatEntity>
}
