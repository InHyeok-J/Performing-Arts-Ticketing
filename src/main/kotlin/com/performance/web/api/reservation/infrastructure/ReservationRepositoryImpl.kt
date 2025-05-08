package com.performance.web.api.reservation.infrastructure

import com.performance.web.api.reservation.domain.Reservation
import com.performance.web.api.reservation.domain.ReservationRepository
import com.performance.web.api.reservation.infrastructure.jpa.ReservationEntity
import com.performance.web.api.reservation.infrastructure.jpa.ReservationJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class ReservationRepositoryImpl(
    private val reservationJpaRepository: ReservationJpaRepository,
) : ReservationRepository {

    override fun save(reservation: Reservation): Reservation =
        reservationJpaRepository.save(
            ReservationEntity.fromDomain(reservation),
        ).toDomain()

    override fun findById(reservationId: Long): Optional<Reservation> =
        reservationJpaRepository.findByIdWithTickets(
            reservationId,
        ).map {
            it.toDomain()
        }
}
