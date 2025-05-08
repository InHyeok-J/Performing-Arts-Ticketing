package com.performance.web.api.reservation.domain

import com.performance.web.api.seat.domain.Seat

data class TicketSeatInfo(
    val row: Int,
    val column: Int,
    val floor: Int = 1,
    val seatType: String,
) {

    companion object {

        fun from(seat: Seat): TicketSeatInfo =
            TicketSeatInfo(
                row = seat.getSeatPosition().row,
                column = seat.getSeatPosition().column,
                floor = seat.getSeatPosition().floor,
                seatType = seat.getSeatClass().classType,
            )
    }
}
