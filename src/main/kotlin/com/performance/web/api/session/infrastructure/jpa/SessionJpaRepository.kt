package com.performance.web.api.session.infrastructure.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface SessionJpaRepository : JpaRepository<SessionEntity, Long> {

    @Query(
        "select SessionEntity from SessionEntity s " +
            "WHERE s.id BETWEEN :start AND :end ",
    )
    fun findAllByIdBetweens(@Param("start") start: Long, @Param("end") end: Long): List<SessionEntity>

    fun findAllByIdBetween(idAfter: Long, idBefore: Long): MutableList<SessionEntity>
}
