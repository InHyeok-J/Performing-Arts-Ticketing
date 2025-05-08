package com.performance.web.api.performance.infrastructure.jpa

import com.performance.web.api.performance.domain.Performance
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "performance")
class PerformanceEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var runTime: Long,
    @Column(nullable = false)
    var startDate: LocalDate,
    @Column(nullable = false)
    var endDate: LocalDate,
    @Column(columnDefinition = "TEXT")
    var description: String,
    @Column(nullable = false)
    var poster: String,
    @Column(nullable = false)
    var location: String,
    @OneToMany(mappedBy = "performance", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    var seatClasses: MutableSet<PerformanceSeatClassEntity> = mutableSetOf(),
) {

    fun toDomain(): Performance {
        return Performance(
            id = id,
            name = name,
            runTimeInMinutes = runTime,
            startDate = startDate,
            endDate = endDate,
            description = description,
            poster = poster,
            location = location,
            seatClasses = seatClasses.map { it.toDomain() },
        )
    }

    companion object {

        fun fromDomain(performance: Performance): PerformanceEntity {
            val performanceEntity =
                PerformanceEntity(
                    id = performance.getId(),
                    name = performance.getName(),
                    runTime = performance.getRunTime(),
                    startDate = performance.getStartDate(),
                    endDate = performance.getEndDate(),
                    poster = performance.getPoster(),
                    location = performance.getLocation(),
                    description = performance.getDescription(),
                )
            val seatClassEntities =
                performance.getSeatClasses().map { PerformanceSeatClassEntity.fromDomain(it, performanceEntity) }
            performanceEntity.seatClasses = seatClassEntities.toMutableSet()
            return performanceEntity
        }
    }
}
