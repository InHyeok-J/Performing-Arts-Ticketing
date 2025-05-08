package com.performance.web.api.fixtures

import com.performance.web.api.session.domain.Session
import java.time.LocalDateTime

class SessionFixture {

    companion object {

        fun create(
            id: Long = 0L,
            performanceId: Long = 1L,
            startDateTime: LocalDateTime = LocalDateTime.now(),
            endDateTime: LocalDateTime = LocalDateTime.now(),
        ): Session =
            Session(
                id = id,
                performanceId = performanceId,
                startDateTime = startDateTime,
                endDateTime = endDateTime,
            )
    }
}
