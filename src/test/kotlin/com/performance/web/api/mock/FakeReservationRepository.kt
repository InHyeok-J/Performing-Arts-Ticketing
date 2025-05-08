package com.performance.web.api.mock

import com.performance.web.api.reservation.domain.Reservation
import com.performance.web.api.reservation.domain.ReservationRepository
import com.performance.web.api.reservation.domain.Ticket
import java.util.*

class FakeReservationRepository : ReservationRepository {

    private var autoIncrementId = 1L
    private var autoIncrementTicketId = 1L
    private val store = mutableMapOf<Long, Reservation>()

    override fun save(reservation: Reservation): Reservation {
        val newReservation =
            Reservation(
                id = if (reservation.getId() == 0L) autoIncrementId++ else reservation.getId(),
                sessionId = reservation.getSessionId(),
                performanceSessionInfo = reservation.getPerformanceSessionInfo(),
                customer = reservation.getCustomer(),
                tickets =
                    reservation.getTickets().map {
                        Ticket(
                            id = if (it.getId() == 0L) autoIncrementTicketId++ else it.getId(),
                            totalAmount = it.getTotalAmount(),
                            regularPrice = it.getRegularPrice(),
                            ticketSeatInfo = it.getTicketSeatInfo(),
                            discountInfo = it.getDiscountInfo(),
                        )
                    },
            )
        store.put(newReservation.getId(), newReservation)
        return newReservation
    }

    override fun findById(reservationId: Long): Optional<Reservation> = Optional.ofNullable(store[reservationId])
}
