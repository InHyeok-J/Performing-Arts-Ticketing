package com.performance.web.api.reservation.domain

class Customer(
    id: Long = 0L,
) {

    private val _id: Long = id

    fun getId() = _id
}
