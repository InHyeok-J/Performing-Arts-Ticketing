package com.performance.web.api.performance.infrastructure.jpa

import com.performance.web.api.common.domain.Money
import com.performance.web.api.performance.domain.PerformanceSeatClass
import jakarta.persistence.*

@Entity
@Table(name = "performance_seat_class")
class PerformanceSeatClassEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var price: Long,

    @Column(nullable = false)
    var classType: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    var performance: PerformanceEntity? = null,

) {

    fun toDomain(): PerformanceSeatClass {
        return PerformanceSeatClass(
            id = id,
            price = Money.of(price),
            classType = classType,
        )
    }

    companion object {
        fun fromDomain(seatClass: PerformanceSeatClass, performance: PerformanceEntity?): PerformanceSeatClassEntity {
            return PerformanceSeatClassEntity(
                id = seatClass.getId(),
                price = seatClass.getPrice().longValue(),
                classType = seatClass.getClassType(),
                performance = performance
            )
        }
    }
}
