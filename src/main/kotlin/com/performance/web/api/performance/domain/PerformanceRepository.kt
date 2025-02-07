package com.performance.web.api.performance.domain

import com.performance.web.api.common.domain.BaseRepository

interface PerformanceRepository : BaseRepository<Performance> {

    fun findByPage(pageNum: Int): List<Performance>

    fun findAll(): List<Performance>

    fun save(performance: Performance): Performance

    fun isExistsById(id: Long): Boolean
}
