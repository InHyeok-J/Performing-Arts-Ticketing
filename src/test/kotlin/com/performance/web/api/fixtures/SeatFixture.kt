package com.performance.web.api.fixtures

import com.performance.web.api.common.domain.Money
import com.performance.web.api.reservation.domain.Seat
import com.performance.web.api.reservation.domain.SeatClass
import com.performance.web.api.reservation.domain.SeatPosition
import com.performance.web.api.reservation.domain.SeatStatus

class SeatFixture {

    companion object {
        fun create(
            id: Long = 0,
            seatClass: SeatClass =
                SeatClass(
                    price = Money.of(10000),
                    classType = "VIP",
                ),
            seatStatus: SeatStatus = SeatStatus.UN_RESERVED,
            seatPosition: SeatPosition =
                SeatPosition(
                    row = 1,
                    column = 0,
                ),
        ): Seat =
            Seat(
                id = id,
                seatClass = seatClass,
                seatStatus = seatStatus,
                seatPosition = seatPosition,
            )
    }
}