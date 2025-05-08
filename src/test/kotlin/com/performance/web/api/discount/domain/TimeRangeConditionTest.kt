package com.performance.web.api.discount.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TimeRangeConditionTest {

    @ParameterizedTest
    @ValueSource(
        strings = [
            "05:59:59.000",
            "12:00:01.000",
        ],
    )
    fun `시간 범위가 일치하지 않으면 실패한다`(datetime: String)  {
        // given
        val requestTime = LocalTime.parse(datetime)
        val factor =
            DiscountFactor(reserveDateTime = LocalDateTime.of(LocalDate.now(), requestTime), ticketTotalAmount = 10)
        val condition =
            TimeRangeCondition( // 6~ 12시 사이의 할인 조건
                startTime = LocalTime.of(6, 0, 0, 0),
                endTime = LocalTime.of(12, 0, 0, 0),
            )

        // when
        val result = condition.isSatisfiedBy(factor)

        // then
        assertThat(result).isFalse()
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "06:00:00.000",
            "12:00:00.000",
            "11:00:00.000",
        ],
    )
    fun `시간 범위가 일치하면 성공`(datetime: String) {
        // given
        val requestTime = LocalTime.parse(datetime)
        val factor =
            DiscountFactor(reserveDateTime = LocalDateTime.of(LocalDate.now(), requestTime), ticketTotalAmount = 10)
        val condition =
            TimeRangeCondition( // 6~ 12시 사이의 할인 조건
                startTime = LocalTime.of(6, 0, 0, 0),
                endTime = LocalTime.of(12, 0, 0, 0),
            )

        // when
        val result = condition.isSatisfiedBy(factor)

        // then
        assertThat(result).isTrue()
    }
}
