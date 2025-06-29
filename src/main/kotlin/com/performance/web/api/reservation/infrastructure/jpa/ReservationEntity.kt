package com.performance.web.api.reservation.infrastructure.jpa

import com.performance.web.api.reservation.domain.Customer
import com.performance.web.api.reservation.domain.Reservation
import jakarta.persistence.*

@Entity
@Table(name = "reservation")
class ReservationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Embedded
    var performanceSessionInfoEntity: PerformanceSessionInfoEntity,
    var customerId: Long,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reservation", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    var tickets: MutableList<TicketEntity> = mutableListOf<TicketEntity>(),

    @Column(name = "reservation_code", unique = true)
    var reservationCode: String,
) {

    fun toDomain(): Reservation {
        return Reservation(
            id = id!!,
            customer = Customer(customerId),
            tickets = tickets.map { it.toDomain() },
            performanceSessionInfo = performanceSessionInfoEntity.toDomain(),
            reservationCode = reservationCode,
        )
    }

    companion object {
        fun fromDomain(reservation: Reservation): ReservationEntity {
            val reservationEntity =
                ReservationEntity(
                    id = if (reservation.getId() == 0L) null else reservation.getId(),
                    customerId = reservation.getCustomer().getId(),
                    performanceSessionInfoEntity =
                        PerformanceSessionInfoEntity.fromDomain(
                            reservation.getPerformanceSessionInfo(),
                        ),
                    reservationCode = reservation.getReservationCode(),
                )

            val ticketEntities =
                reservation.getTickets().map {
                    TicketEntity.fromDomain(
                        it,
                        reservationEntity,
                    )
                }.toMutableList()
            reservationEntity.tickets = ticketEntities
            return reservationEntity
        }
    }
}
