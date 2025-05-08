package com.performance.web.api.mock

import com.performance.web.api.performance.domain.Performance
import com.performance.web.api.performance.domain.PerformanceRepository
import com.performance.web.api.performance.domain.PerformanceSeatClass
import java.util.*

class FakePerformanceRepository : PerformanceRepository {

    private var autoIncrementId = 1L
    private var autoIncrementSeatClassId = 1L
    private var store = mutableMapOf<Long, Performance>()

    override fun findById(id: Long): Optional<Performance> {
        return Optional.ofNullable(store[id])
    }

    override fun findAll(): List<Performance> {
        return store.values.toList()
    }

    override fun save(performance: Performance): Performance {
        val newPerformance =
            Performance(
                id = if (performance.getId() == 0L) autoIncrementId++ else performance.getId(),
                name = performance.getName(),
                runTimeInMinutes = performance.getRunTime(),
                startDate = performance.getStartDate(),
                endDate = performance.getStartDate(),
                description = performance.getDescription(),
                poster = performance.getPoster(),
                location = performance.getLocation(),
                seatClasses =
                    performance.getSeatClasses().map {
                        PerformanceSeatClass(
                            id = if (it.getId() == 0L) autoIncrementSeatClassId++ else it.getId(),
                            price = it.getPrice(),
                            classType = it.getClassType(),
                        )
                    },
            )
        store.put(newPerformance.getId(), newPerformance)
        return newPerformance
    }

    override fun isExistsById(id: Long): Boolean = store.containsKey(id)

    override fun findAllByPaging(
        pageNum: Int,
        pageSize: Int,
    ): Pair<List<Performance>, Int> {
        TODO("Not yet implemented")
    }
}
