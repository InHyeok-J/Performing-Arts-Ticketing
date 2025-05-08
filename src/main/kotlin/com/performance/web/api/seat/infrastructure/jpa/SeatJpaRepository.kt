package com.performance.web.api.seat.infrastructure.jpa

import com.performance.web.api.seat.infrastructure.jpa.dto.SeatAvailabilityDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface SeatJpaRepository : JpaRepository<SeatEntity, Long> {

    fun findAllBySessionId(sessionId: Long): List<SeatEntity>

    @Query(value = "SELECT * FROM seat s WHERE s.id = :seatId FOR update nowait  ", nativeQuery = true)
    fun findByIdWithLock(
        @Param("seatId") seatId: Long,
    ): Optional<SeatEntity>

    @Query(
        """
        SELECT new com.performance.web.api.seat.infrastructure.jpa.dto.SeatAvailabilityDto(
            s.sessionId, s.seatClass.classType, COUNT(s)
        )
        FROM SeatEntity  s
        WHERE s.sessionId in :sessionIds AND s.seatStatus = 'UN_RESERVE'
        GROUP BY s.sessionId, s.seatClass.classType
    """,
    )
    fun findAvailableSeatsBySessionIds(
        @Param("sessionIds") sessionIds: List<Long>,
    ): List<SeatAvailabilityDto>
}
