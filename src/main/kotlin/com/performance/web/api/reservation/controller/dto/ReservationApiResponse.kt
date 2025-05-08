package com.performance.web.api.reservation.controller.dto

import com.performance.web.api.reservation.domain.Reservation
import com.performance.web.api.reservation.domain.Ticket
import com.performance.web.api.reservation.domain.TicketSeatInfo
import java.time.LocalDate
import java.time.LocalTime

data class ReservationApiResponse(
    val id: Long,
    val reservationCode : String,
    val performanceName: String,
    val startDate: LocalDate,
    val starTime: LocalTime,
    val endTime: LocalTime,
    val tickets: List<TicketApiResponse>,
) {

    companion object {
        fun from(result: Reservation): ReservationApiResponse =
            ReservationApiResponse(
                id = result.getId(),
                performanceName = result.getPerformanceSessionInfo().performanceName,
                startDate = result.getPerformanceSessionInfo().sessionStartDate,
                starTime = result.getPerformanceSessionInfo().sessionStartTime,
                endTime = result.getPerformanceSessionInfo().sessionEndTime,
                tickets =
                    result.getTickets().map { ticket ->
                        TicketApiResponse.from(ticket)
                    },
                reservationCode = result.getReservationCode()
            )
    }

    data class TicketApiResponse(
        val id: Long,
        val seatInfo: SeatInfoApiResponse,
        val totalAmount: Long,
        val regularPrice: Long,
        val discountName: String,
    ) {
        companion object {

            fun from(ticket: Ticket): TicketApiResponse =
                TicketApiResponse(
                    id = ticket.getId(),
                    seatInfo = SeatInfoApiResponse.from(ticket.getTicketSeatInfo()),
                    totalAmount = ticket.getTotalAmount().longValue(),
                    regularPrice = ticket.getRegularPrice().longValue(),
                    discountName = ticket.getDiscountInfo().name,
                )
        }
    }

    data class SeatInfoApiResponse(
        val seatClassType: String,
        val floor: Int,
        val row: Int,
        val column: Int,
    ) {
        companion object {
            fun from(ticketSeatInfo: TicketSeatInfo): SeatInfoApiResponse =
                SeatInfoApiResponse(
                    seatClassType = ticketSeatInfo.seatType,
                    floor = ticketSeatInfo.floor,
                    row = ticketSeatInfo.row,
                    column = ticketSeatInfo.column,
                )
        }
    }
}
