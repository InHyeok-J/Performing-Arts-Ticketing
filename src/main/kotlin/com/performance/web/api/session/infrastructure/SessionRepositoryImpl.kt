package com.performance.web.api.session.infrastructure

import com.performance.web.api.seat.infrastructure.jpa.SeatJpaRepository
import com.performance.web.api.seat.infrastructure.jpa.dto.SeatAvailabilityDto
import com.performance.web.api.session.domain.Session
import com.performance.web.api.session.domain.SessionQueryRepository
import com.performance.web.api.session.domain.SessionRepository
import com.performance.web.api.session.infrastructure.jpa.SessionEntity
import com.performance.web.api.session.infrastructure.jpa.SessionJpaRepository
import com.performance.web.api.session.service.dto.SessionWithSeatInfoResponse
import org.springframework.stereotype.Component
import java.util.*

@Component
class SessionRepositoryImpl(
    private val sessionJpaRepository: SessionJpaRepository,
    private val seatJpaRepository: SeatJpaRepository,
) : SessionRepository, SessionQueryRepository {

    override fun findById(id: Long): Optional<Session> {
        return sessionJpaRepository.findById(id).map { it.toDomain() }
    }

    override fun save(session: Session): Session {
        return sessionJpaRepository.save(SessionEntity.fromDomain(session)).toDomain()
    }

    override fun findAll(): List<Session> {
        return sessionJpaRepository.findAll().map { it.toDomain() }
    }

    override fun existById(id: Long): Boolean {
        return sessionJpaRepository.existsById(id)
    }

    override fun findByYearMonthWithSeat(
        performanceId: Long,
        year: Int,
        month: Int,
    ): List<SessionWithSeatInfoResponse> {
        val sessions = sessionJpaRepository.finAllByYearMonth(performanceId, year, month)
        if (sessions.isEmpty()) return emptyList()

        val seats: List<SeatAvailabilityDto> =
            seatJpaRepository.findAvailableSeatsBySessionIds(sessions.map { it.id!! })

        val seatMap = seats.groupBy { it.sessionId }

        return sessions.map { session ->
            SessionWithSeatInfoResponse(
                id = session.id!!,
                startDateTime = session.startDateTime,
                endDateTime = session.endDateTime,
                seatCurrentStatus =
                    seatMap[session.id]?.map { seat: SeatAvailabilityDto ->
                        SessionWithSeatInfoResponse.SeatCurrentStatus(
                            classType = seat.classType,
                            remain = seat.count,
                        )
                    } ?: emptyList(),
            )
        }
    }
}
