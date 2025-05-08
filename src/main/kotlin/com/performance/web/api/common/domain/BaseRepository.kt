package com.performance.web.api.common.domain

import java.util.Optional

interface BaseRepository<T> {

    fun findById(id: Long): Optional<T>

    fun findByIdThrown(id: Long): T =
        findById(id).orElseThrow {
            throw ResourceNotFoundException("$id 에 해당하는 값을 찾을 수 없습니다. ")
        }
}
