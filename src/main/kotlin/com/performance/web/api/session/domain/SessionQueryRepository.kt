package com.performance.web.api.session.domain

import com.performance.web.api.session.service.dto.SessionWithSeatInfoResponse

interface SessionQueryRepository {

    fun findByYearMonthWithSeat(performanceId: Long, year: Int, month: Int): List<SessionWithSeatInfoResponse>

}
