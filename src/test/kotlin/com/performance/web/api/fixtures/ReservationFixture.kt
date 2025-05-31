package com.performance.web.api.fixtures

import com.performance.web.api.reservation.domain.Customer
import com.performance.web.api.reservation.domain.PerformanceSessionInfo
import com.performance.web.api.reservation.domain.Reservation
import com.performance.web.api.reservation.domain.Ticket
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class ReservationFixture {

    companion object {

        fun create(
            id: Long = 0L,
            reservationCode: String = UUID.randomUUID().toString(),
            sessionId: Long = 0L,
            performanceSessionInfo: PerformanceSessionInfo = PerformanceSessionInfo(
                performanceName = "name",
                sessionStartDate = LocalDate.now(),
                sessionStartTime = LocalTime.now(),
                sessionEndTime = LocalTime.now(),
                performanceId = 1L,
                sessionId = 1L
                ),
            customer: Customer = Customer(id),
            tickets: List<Ticket> = listOf<Ticket>()
        ): Reservation =
            Reservation(
                id = id,
                reservationCode = reservationCode,
                performanceSessionInfo = performanceSessionInfo,
                customer = customer,
                tickets = tickets,
            )
    }
}
