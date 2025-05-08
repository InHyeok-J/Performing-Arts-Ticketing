package com.performance.web.api.reservation.domain

import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class TodayBasedRandomStringReservationCodeGenerator : ReservationCodeGenerator {

    companion object {
        private const val LENGTH = 8
        private const val CHAR_POOL = "ACDEFGHJKLMNPQRSTUVWXYZ2345679"
        private val random = SecureRandom()
        private val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    }

    override fun generate(): String {
        return generateWith(LocalDate.now())
    }

    fun generateWith(date: LocalDate): String {
        val datePart = date.format(formatter)
        val randomPart = (1..LENGTH)
            .map { CHAR_POOL[random.nextInt(CHAR_POOL.length)] }
            .joinToString("")

        return "$datePart-$randomPart"
    }


}
