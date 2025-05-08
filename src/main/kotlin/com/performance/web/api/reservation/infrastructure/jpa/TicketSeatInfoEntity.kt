package com.performance.web.api.reservation.infrastructure.jpa

import com.performance.web.api.reservation.domain.TicketSeatInfo
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class TicketSeatInfoEntity(
    @Column(nullable = false)
    var rowIndex: Int,
    @Column(nullable = false)
    var columnIndex: Int,
    @Column(nullable = false)
    var floor: Int,
    @Column(nullable = false)
    var seatType: String,
) {

    fun toDomain(): TicketSeatInfo =
        TicketSeatInfo(
            row = rowIndex,
            column = columnIndex,
            floor = floor,
            seatType = seatType,
        )

    companion object {
        fun fromDomain(ticketSeatInfo: TicketSeatInfo): TicketSeatInfoEntity =
            TicketSeatInfoEntity(
                rowIndex = ticketSeatInfo.row,
                columnIndex = ticketSeatInfo.column,
                floor = ticketSeatInfo.floor,
                seatType = ticketSeatInfo.seatType,
            )
    }
}
