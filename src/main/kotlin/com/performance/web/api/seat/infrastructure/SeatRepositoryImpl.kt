package com.performance.web.api.seat.infrastructure

import com.performance.web.api.seat.domain.Seat
import com.performance.web.api.seat.domain.SeatRepository
import com.performance.web.api.seat.infrastructure.jpa.SeatEntity
import com.performance.web.api.seat.infrastructure.jpa.SeatJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class SeatRepositoryImpl(
    private val seatJpaRepository: SeatJpaRepository
) : SeatRepository {


    override fun findById(id: Long): Optional<Seat> {
        return seatJpaRepository.findById(id).map { it.toDomain() }
    }

    override fun save(seat: Seat): Seat {
        return seatJpaRepository.save(SeatEntity.fromDomain(seat)).toDomain()
    }

    override fun saveAll(seats: List<Seat>): List<Seat> {
        return seatJpaRepository.saveAll( seats.map { SeatEntity.fromDomain(it) }).map { it.toDomain() }
    }

    override fun findAllBySessionId(sessionId: Long): List<Seat> {
        return seatJpaRepository.findAllBySessionId(sessionId).map { it.toDomain() }
    }

    override fun findByIdWithLock(id: Long): Optional<Seat> {
        return seatJpaRepository.findByIdWithLock(id).map { it.toDomain() }
    }
}
