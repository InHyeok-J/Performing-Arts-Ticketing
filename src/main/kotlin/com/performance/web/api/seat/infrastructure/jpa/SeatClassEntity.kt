package com.performance.web.api.seat.infrastructure.jpa

import com.performance.web.api.common.domain.Money
import com.performance.web.api.seat.domain.SeatClass
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class SeatClassEntity(
    @Column(nullable = false)
    var price: Long,
    @Column(nullable = false)
    var classType: String,
) {

    fun toDomain(): SeatClass =
        SeatClass(
            price = Money.of(price),
            classType = classType,
        )

    companion object {
        fun fromDomain(seatClass: SeatClass): SeatClassEntity =
            SeatClassEntity(
                price = seatClass.price.longValue(),
                classType = seatClass.classType,
            )
    }
}
