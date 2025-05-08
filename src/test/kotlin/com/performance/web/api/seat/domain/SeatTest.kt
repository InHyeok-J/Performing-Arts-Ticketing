package com.performance.web.api.seat.domain

import com.performance.web.api.common.domain.BusinessException
import com.performance.web.api.common.domain.Money
import com.performance.web.api.fixtures.DiscountFactorFixture
import com.performance.web.api.fixtures.DiscountPolicyFixture
import com.performance.web.api.fixtures.SeatClassFixture
import com.performance.web.api.fixtures.SeatFixture
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

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
            seat.reserveTicket(
                discountPolicy = DiscountPolicyFixture.createPercent(),
                discountFactor = DiscountFactorFixture.create(),
            )
        }.isInstanceOf(BusinessException::class.java).hasMessageContaining("이미 예약된 좌석입니다.")
    }

    @Test
    fun `할인 정책과 요소를 확인해 티켓을 생성한다`() {
        // given
        val discountPolicy =
            DiscountPolicyFixture.createPercent(
                percent = 0.5,
                name = "퍼센트할인입니다",
            )
        val discountFactor = DiscountFactorFixture.create()
        val seat =
            SeatFixture.create(
                seatClass = SeatClassFixture.create(price = Money.of(10000)),
                seatPosition = SeatPosition(1, 1, 1),
            )

        // when
        val ticket = seat.reserveTicket(discountPolicy, discountFactor)

        // then
        assertThat(ticket.getTotalAmount()).isEqualTo(Money.of(5000))
        assertThat(ticket.getRegularPrice()).isEqualTo(Money.of(10000))
        assertThat(ticket.getTicketSeatInfo().row).isEqualTo(1)
        assertThat(ticket.getTicketSeatInfo().column).isEqualTo(1)
        assertThat(ticket.getTicketSeatInfo().floor).isEqualTo(1)
        assertThat(ticket.getDiscountInfo().name).isEqualTo("퍼센트할인입니다")
        assertThat(seat.getSeatStatus()).isEqualTo(SeatStatus.RESERVED)
    }
}
