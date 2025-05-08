package com.performance.web.api.reservation.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class TodayBasedRandomStringReservationCodeGeneratorTest {

    private val codeGenerator: TodayBasedRandomStringReservationCodeGenerator = TodayBasedRandomStringReservationCodeGenerator()

    @Test()
    fun `입력한 날짜와 랜덤한 8자리 문자가 붙여서 코드가 생성된다`() {
        // given
        val date = LocalDate.of(2025,1,10)

        // when
        val generate = codeGenerator.generateWith(date)

        // then
        val result = generate.split("-")

        assertThat(result.size).isEqualTo(2)
        assertThat(result[0]).isEqualTo("20250110")
        assertThat(result[1].length).isEqualTo(8)
    }
}
