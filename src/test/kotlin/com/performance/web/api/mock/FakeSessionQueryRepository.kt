package com.performance.web.api.mock

import com.performance.web.api.session.domain.SessionQueryRepository
import com.performance.web.api.session.service.dto.SessionWithSeatInfoResponse

class FakeSessionQueryRepository : SessionQueryRepository {
    override fun findByYearMonthWithSeat(
        performanceId: Long,
        year: Int,
        month: Int,
    ): List<SessionWithSeatInfoResponse> {
        TODO("Not yet implemented")
    }
}
