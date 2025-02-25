package com.performance.web.api.seat.domain

import com.performance.web.api.common.domain.BaseRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface SeatRepository : BaseRepository<Seat> {

    fun save(seat: Seat): Seat

    fun saveAll(seats: List<Seat>): List<Seat>

    fun findAllBySessionId(sessionId: Long): List<Seat>

    fun findByIdWithLock(id: Long): Optional<Seat>

}
