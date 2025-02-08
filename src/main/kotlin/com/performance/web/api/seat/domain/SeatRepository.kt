package com.performance.web.api.seat.domain

import com.performance.web.api.common.domain.BaseRepository
import org.springframework.stereotype.Repository

@Repository
interface SeatRepository : BaseRepository<Seat> {

    fun save(seat: Seat): Seat

    fun saveAll(seats: List<Seat>): List<Seat>

    fun findAllBySessionId(sessionId: Long): List<Seat>
}
