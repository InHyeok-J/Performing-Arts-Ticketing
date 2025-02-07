package com.performance.web.api.session.infrastructure

import com.performance.web.api.session.domain.Session
import com.performance.web.api.session.domain.SessionRepository
import com.performance.web.api.session.infrastructure.jpa.SessionEntity
import com.performance.web.api.session.infrastructure.jpa.SessionJpaRepository
import org.springframework.stereotype.Component
import java.util.*


@Component
class SessionRepositoryImpl(
    private val sessionJpaRepository: SessionJpaRepository,
) : SessionRepository {

    override fun findById(id: Long): Optional<Session> {
        return sessionJpaRepository.findById(id).map { it.toDomain() }
    }

    override fun save(session: Session):Session {
        return sessionJpaRepository.save(SessionEntity.fromDomain(session)).toDomain()
    }

    override fun findAll(): List<Session> {
        return sessionJpaRepository.findAll().map { it.toDomain() }
    }
}
