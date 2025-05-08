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
    @Column(nullable = false, name = "session_id")
    var sessionId: Long,
    @Embedded
    var performanceSessionInfoEntity: PerformanceSessionInfoEntity,
    var customerId: Long,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reservation", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    var tickets: MutableList<TicketEntity> = mutableListOf<TicketEntity>(),
) {

    fun toDomain(): Reservation {
        return Reservation(
            id = id!!,
            sessionId = sessionId,
            customer = Customer(customerId),
            tickets = tickets.map { it.toDomain() },
            performanceSessionInfo = performanceSessionInfoEntity.toDomain(),
        )
    }

    companion object {
        fun fromDomain(reservation: Reservation): ReservationEntity {
            val reservationEntity =
                ReservationEntity(
                    id = if (reservation.getId() == 0L) null else reservation.getId(),
                    sessionId = reservation.getSessionId(),
                    customerId = reservation.getCustomer().getId(),
                    performanceSessionInfoEntity =
                        PerformanceSessionInfoEntity.fromDomain(
                            reservation.getPerformanceSessionInfo(),
                        ),
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
