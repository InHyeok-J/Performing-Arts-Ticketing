package com.performance.web.api.discount.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDate
import java.time.LocalDateTime

class DateRangeConditionTest {

    @ParameterizedTest
    @ValueSource(
        strings = [
            "2025-12-31T00:00:00.000",
            "2025-01-08T00:00:00.000",
            "2025-01-08T01:01:01.000",
        ],
    )
    fun `날짜 범위가 일치하지 않으면 실패한다`(date: String) {
        // given
        val requestDate = LocalDateTime.parse(date)
        val factor = DiscountFactor(reserveDateTime = requestDate, ticketTotalAmount = 10)

        // 1월 1일~ 1월 7일 23:59까지
        val condition =
            DateRangeCondition(
                startDate = LocalDate.of(2025, 1, 1),
                endDate = LocalDate.of(2025, 1, 7),
            )

        // when
        val result = condition.isSatisfiedBy(factor)

        // then
        assertThat(result).isFalse()
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "2025-01-01T00:00:00.000",
            "2025-01-05T00:00:00.000",
            "2025-01-07T23:59:59.000",
        ],
    )
    fun `날짜 범위가 일치하면 성공한다`(date: String) {
        // given
        val requestDate = LocalDateTime.parse(date)
        val factor = DiscountFactor(reserveDateTime = requestDate, ticketTotalAmount = 10)

        // 1월 1일~ 1월 7일 23:59까지
        val condition =
            DateRangeCondition(
                startDate = LocalDate.of(2025, 1, 1),
                endDate = LocalDate.of(2025, 1, 7),
            )

        // when
        val result = condition.isSatisfiedBy(factor)

        // then
        assertThat(result).isTrue()
    }
}
