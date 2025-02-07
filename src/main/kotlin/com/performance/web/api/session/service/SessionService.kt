package com.performance.web.api.session.service

import com.performance.web.api.session.domain.Session
import com.performance.web.api.session.domain.SessionRepository
import org.springframework.stereotype.Service

@Service
class SessionService(
    private val sessionRepository: SessionRepository,
) {

    fun getAll(): List<Session>{
        return sessionRepository.findAll()
    }
}
