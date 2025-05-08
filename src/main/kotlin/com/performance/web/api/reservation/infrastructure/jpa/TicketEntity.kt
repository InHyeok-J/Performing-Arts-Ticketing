package com.performance.web.api.reservation.infrastructure.jpa

import com.performance.web.api.common.domain.Money
import com.performance.web.api.reservation.domain.Ticket
import jakarta.persistence.*

@Entity
@Table(name = "ticket")
class TicketEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false)
    var totalAmount: Long,
    @Column(nullable = false)
    var regularPrice: Long,
    @Embedded
    var seatInfo: TicketSeatInfoEntity,
    @Embedded
    var discountInfo: DiscountInfoEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    var reservation: ReservationEntity,
) {

    fun toDomain(): Ticket =
        Ticket(
            id = id!!,
            totalAmount = Money.of(totalAmount),
            regularPrice = Money.of(regularPrice),
            ticketSeatInfo = seatInfo.toDomain(),
            discountInfo = discountInfo.toDomain(),
        )

    companion object {
        fun fromDomain(
            ticket: Ticket,
            reservation: ReservationEntity,
        ): TicketEntity =
            TicketEntity(
                id = if (ticket.getId() == 0L) null else ticket.getId(),
                totalAmount = ticket.getTotalAmount().longValue(),
                regularPrice = ticket.getRegularPrice().longValue(),
                seatInfo = TicketSeatInfoEntity.fromDomain(ticket.getTicketSeatInfo()),
                discountInfo = DiscountInfoEntity.fromDomain(ticket.getDiscountInfo()),
                reservation = reservation,
            )
    }
}
