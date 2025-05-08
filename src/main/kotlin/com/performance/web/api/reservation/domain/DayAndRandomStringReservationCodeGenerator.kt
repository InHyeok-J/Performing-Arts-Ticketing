package com.performance.web.api.reservation.domain

import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class DayAndRandomStringReservationCodeGenerator {

    companion object {
        private const val LENGTH = 8
        private const val CHAR_POOL = "ACDEFGHJKLMNPQRSTUVWXYZ2345679"
        private val random = SecureRandom()
        private val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    }

    fun generate(date: LocalDate = LocalDate.now()): String {
        val datePart = date.format(formatter)
        val randomPart = (1..LENGTH)
            .map { CHAR_POOL[random.nextInt(CHAR_POOL.length)] }
            .joinToString("")

        return "$datePart-$randomPart"
    }

}
