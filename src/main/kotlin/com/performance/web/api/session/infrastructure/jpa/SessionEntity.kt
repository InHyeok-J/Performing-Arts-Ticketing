package com.performance.web.api.session.infrastructure.jpa

import com.performance.web.api.session.domain.Session
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "performance_session")
class SessionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "performance_id")
    var performanceId: Long,
    @Column(nullable = false)
    var startDateTime: LocalDateTime,
    var endDateTime: LocalDateTime,
) {

    fun toDomain(): Session =
        Session(
            id = id!!,
            performanceId = performanceId,
            startDateTime = startDateTime,
            endDateTime = endDateTime,
        )

    companion object {
        fun fromDomain(session: Session): SessionEntity =
            SessionEntity(
                id = if (session.getId() == 0L) null else session.getId(),
                performanceId = session.getPerformanceId(),
                startDateTime = session.getStartDateTime(),
                endDateTime = session.getEndDateTime(),
            )
    }
}
