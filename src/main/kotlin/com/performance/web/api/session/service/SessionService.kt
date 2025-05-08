package com.performance.web.api.session.service

import com.performance.web.api.common.domain.BusinessException
import com.performance.web.api.performance.domain.PerformanceRepository
import com.performance.web.api.session.domain.Session
import com.performance.web.api.session.domain.SessionQueryRepository
import com.performance.web.api.session.domain.SessionRepository
import com.performance.web.api.session.service.dto.SessionCreateCommand
import com.performance.web.api.session.service.dto.SessionWithSeatInfoResponse
import org.springframework.stereotype.Service

@Service
class SessionService(
    private val sessionRepository: SessionRepository,
    private val performanceRepository: PerformanceRepository,
    private val sessionQueryRepository: SessionQueryRepository,
) {

    fun getAll(): List<Session> {
        return sessionRepository.findAll()
    }

    fun getWithSeatsByYearMonth(
        performanceId: Long,
        year: Int,
        month: Int,
    ): List<SessionWithSeatInfoResponse> {
        return sessionQueryRepository.findByYearMonthWithSeat(performanceId, year, month)
    }

    fun create(command: SessionCreateCommand): Session {
        if (!performanceRepository.isExistsById(command.performanceId)) {
            throw BusinessException("존재하지 않는 공연입니다")
        }

        return sessionRepository.save(
            Session(
                performanceId = command.performanceId,
                startDateTime = command.startDateTime,
                endDateTime = command.endDateTime,
            ),
        )
    }
}
