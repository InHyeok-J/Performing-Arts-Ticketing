package com.performance.web.api.reservation.domain

import com.performance.web.api.common.domain.BusinessException
import com.performance.web.api.discount.domain.DiscountFactor
import com.performance.web.api.discount.domain.NoneDiscountPolicy
import com.performance.web.api.fixtures.SeatFixture
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class SeatTest {

    @Test
    fun `이미 예약된 좌석은 예매할 수 없다`() {
        // given
        val seat =
            SeatFixture.create(
                seatStatus = SeatStatus.RESERVED,
            )

        // when
        // then
        assertThatThrownBy {
            seat.reserve(
                discountPolicyId = 1L,
                discountFactor =
                    DiscountFactor(
                        reserveDateTime = LocalDateTime.of(2021, 1, 1, 0, 0),
                        ticketTotalAmount = 1,
                    ),
            )
        }.isInstanceOf(BusinessException::class.java).hasMessageContaining("이미 예약된 좌석입니다.")
    }
}
