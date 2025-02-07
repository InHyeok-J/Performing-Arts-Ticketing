package com.performance.web.api.mock

import com.performance.web.api.session.domain.Session
import com.performance.web.api.session.domain.SessionRepository
import java.util.*

class FakeSessionRepository : SessionRepository {

    private var autoIncrementId = 1L;
    private val store = mutableMapOf<Long, Session>()

    override fun findById(id: Long): Optional<Session> {
        return Optional.ofNullable(store[id])
    }

    override fun save(session: Session): Session {
        var newSession = Session(
            id = if (session.getId() == 0L) autoIncrementId++ else session.getId(),
            performanceId = session.getPerformanceId(),
            startDateTime = session.getStartDateTime(),
        )
        store.put(newSession.getId(), newSession);
        return newSession
    }

    override fun findAll(): List<Session> {
        return store.values.toList()
    }
}
