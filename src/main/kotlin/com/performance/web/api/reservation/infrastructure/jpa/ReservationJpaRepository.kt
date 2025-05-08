package com.performance.web.api.reservation.infrastructure.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface ReservationJpaRepository : JpaRepository<ReservationEntity, Long> {

    @Query(
        "SELECT r FROM ReservationEntity r " +
            "JOIN FETCH r.tickets t " +
            "WHERE r.id = :reservationId",
    )
    fun findByIdWithTickets(
        @Param("reservationId") id: Long,
    ): Optional<ReservationEntity>
}
