package com.performance.web.api.reservation.infrastructure.jpa

import com.performance.web.api.customer.infrastructure.jpa.CustomerEntity
import jakarta.persistence.*

@Entity
@Table(name = "reservation")
class ReservationEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    var session: SessionEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    var customer: CustomerEntity,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reservation")
    var tickets: MutableList<TicketEntity> = mutableListOf()
    ) {

}
