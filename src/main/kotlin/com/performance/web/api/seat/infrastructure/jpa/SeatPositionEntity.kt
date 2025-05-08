package com.performance.web.api.seat.infrastructure.jpa

import com.performance.web.api.seat.domain.SeatPosition
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class SeatPositionEntity(
    @Column(nullable = false)
    var rowIndex: Int,
    @Column(nullable = false)
    var columnIndex: Int,
    @Column(nullable = false)
    var floor: Int,
) {

    fun toDomain(): SeatPosition = SeatPosition(rowIndex, columnIndex, floor)

    companion object {

        fun fromDomain(seatPosition: SeatPosition): SeatPositionEntity =
            SeatPositionEntity(
                rowIndex = seatPosition.row,
                columnIndex = seatPosition.column,
                floor = seatPosition.floor,
            )
    }
}
