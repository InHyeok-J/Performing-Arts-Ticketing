package com.performance.web.api.reservation.infrastructure.jpa

import com.performance.web.api.reservation.domain.DiscountInfo
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class DiscountInfoEntity(
    @Column
    var name: String,
) {

    fun toDomain(): DiscountInfo =
        DiscountInfo(
            name = name,
        )

    companion object {
        fun fromDomain(discountInfo: DiscountInfo): DiscountInfoEntity =
            DiscountInfoEntity(
                name = discountInfo.name,
            )
    }
}
