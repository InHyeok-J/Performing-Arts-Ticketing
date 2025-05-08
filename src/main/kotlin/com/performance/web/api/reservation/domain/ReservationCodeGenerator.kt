package com.performance.web.api.reservation.domain


interface ReservationCodeGenerator {

    fun generate(): String
}
