package com.performance.web.api.seat.controller.dto

import com.performance.web.api.seat.domain.Seat
import com.performance.web.api.seat.domain.SeatStatus

data class SeatApiResponse(
    val id: Long,
    val classType: String,
    val seatStatus: SeatStatus,
    val row: Int,
    val column: Int,
    val floor: Int
) {


    companion object {
        fun from(seat: Seat): SeatApiResponse {
            return SeatApiResponse(
                id = seat.getId(),
                classType = seat.getSeatClass().classType,
                seatStatus = seat.getSeatStatus(),
                row = seat.getSeatPosition().row,
                column = seat.getSeatPosition().column,
                floor = seat.getSeatPosition().floor,
            )
        }
    }
}
