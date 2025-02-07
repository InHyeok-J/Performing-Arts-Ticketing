package com.performance.web.api.session.domain

import com.performance.web.api.common.domain.BaseRepository

interface SessionRepository : BaseRepository<Session> {
    fun save(session: Session): Session

    fun findAll(): List<Session>
}
