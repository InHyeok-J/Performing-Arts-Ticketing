package com.performance.web.api.discount.domain

import com.performance.web.api.common.domain.Money
import com.performance.web.api.fixtures.DiscountPolicyFixture
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class DiscountPolicyTest {

    @Test
    fun `할인 조건이 맞지 않으면 예외를 반환한다`() {
        // given
        val discountPolicy =
            DiscountPolicyFixture.createPercent(
                conditions =
                    arrayOf(
                        DateRangeCondition(
                            startDate = LocalDate.of(2025, 1, 1),
                            endDate = LocalDate.of(2025, 1, 2),
                        ),
                    ),
            )
        val discountFactor =
            DiscountFactor(
                reserveDateTime = LocalDateTime.of(2025, 1, 10, 0, 0, 0),
                ticketTotalAmount = 10,
            )

        // when
        // then
        assertThatThrownBy {
            discountPolicy.calculateFee(Money.of(10000), discountFactor)
        }.hasMessageContaining("할인 조건이 맞지 않습니다")
    }

    @Test
    fun `여러 할인 조건중 하나라도 맞지 않으면 예외를 반환한다`() {
        // given
        val discountPolicy =
            DiscountPolicyFixture.createPercent( // 1월 1일 06시~12시 사이의 할인
                conditions =
                    arrayOf(
                        DateRangeCondition(
                            startDate = LocalDate.of(2025, 1, 1),
                            endDate = LocalDate.of(2025, 1, 1),
                        ),
                        TimeRangeCondition(
                            startTime = LocalTime.of(6, 0, 0),
                            endTime = LocalTime.of(12, 0, 0),
                        ),
                    ),
            )
        val discountFactor =
            DiscountFactor( // 날짜는 맞지만 시간은 맞지 않는다
                reserveDateTime = LocalDateTime.of(2025, 1, 1, 12, 1, 0),
                ticketTotalAmount = 10,
            )

        // when
        // then
        assertThatThrownBy {
            discountPolicy.calculateFee(Money.of(10000), discountFactor)
        }.hasMessageContaining("할인 조건이 맞지 않습니다")
    }

    @Test
    fun `퍼센트 기반 할인시 할인 금액을 반환한다`() {
        // given
        val discountPolicy =
            DiscountPolicyFixture.createPercent(
                percent = 0.2,
                conditions =
                    arrayOf(
                        DateRangeCondition(
                            startDate = LocalDate.of(2025, 1, 1),
                            endDate = LocalDate.of(2025, 1, 2),
                        ),
                    ),
            )
        val discountFactor =
            DiscountFactor(
                reserveDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0),
                ticketTotalAmount = 10,
            )

        // when
        val calculateFee = discountPolicy.calculateFee(Money.of(10000), discountFactor)

        // then
        assertThat(calculateFee).isEqualTo(Money.of(2000))
    }
}
