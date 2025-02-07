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

    @Column(nullable = false)
    var description: String,

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
            seatClasses = seatClasses.map { it.toDomain() } // Lazy 로딩된 seatClasses 처리
        )
    }

    companion object {

        fun fromDomain(performance: Performance): PerformanceEntity {
            return PerformanceEntity(
                id = performance.getId(),
                name = performance.getName(),
                runTime = performance.getRunTime(),
                startDate = performance.getStartDate(),
                endDate = performance.getEndDate(),
                description = performance.getDescription(),
                seatClasses = performance.getSeatClasses().map { PerformanceSeatClassEntity.fromDomain(it) }.toMutableSet(),
            )
        }
    }
}
