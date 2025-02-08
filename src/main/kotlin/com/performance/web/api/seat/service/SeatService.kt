package com.performance.web.api.seat.service

import com.performance.web.api.common.domain.BusinessException
import com.performance.web.api.seat.domain.Seat
import com.performance.web.api.seat.domain.SeatRepository
import com.performance.web.api.session.domain.SessionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SeatService(
    private val seatRepository: SeatRepository,
    private val sessionRepository: SessionRepository,
) {

    @Transactional(readOnly = true)
    fun findBySessionId(sessionId: Long): List<Seat> {
        if (!sessionRepository.existById(sessionId)) {
            throw BusinessException("Session Not Found")
        }

        return seatRepository.findAllBySessionId(sessionId)
    }
}
