package com.performance.web.api.mock

import com.performance.web.api.seat.domain.Seat
import com.performance.web.api.seat.domain.SeatRepository
import java.util.*

class FakeSeatRepository: SeatRepository {

    private var autoIncrementId = 1L
    private val store = mutableMapOf<Long, Seat>()

    override fun findById(id: Long): Optional<Seat> {
        return Optional.ofNullable(store[id])
    }

    override fun save(seat: Seat): Seat {
        val newSeat = Seat(
            id = if(seat.getId()==0L) autoIncrementId++ else seat.getId(),
            seatClass = seat.getSeatClass(),
            seatStatus = seat.getSeatStatus(),
            seatPosition = seat.getSeatPosition(),
            sessionId = seat.getSessionId()
        )

        store.put(newSeat.getId(), newSeat)
        return newSeat
    }

    override fun saveAll(seats: List<Seat>): List<Seat> {
        val list = mutableListOf<Seat>()
        for (seat in seats) {
            list.add(this.save(seat))
        }
        return list
    }

    override fun findAllBySessionId(sessionId: Long): List<Seat> {
        return store.values.toList().filter { it.getSessionId() == sessionId }
    }
}
