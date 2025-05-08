package com.performance.web.api.reservation.domain

import com.performance.web.api.common.domain.Money
import com.performance.web.api.discount.domain.DiscountPolicy
import com.performance.web.api.fixtures.*
import com.performance.web.api.mock.FakeSeatRepository
import com.performance.web.api.seat.domain.SeatRepository
import com.performance.web.api.seat.domain.SeatStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class TicketIssuerTest {

    private lateinit var ticketIssuer: TicketIssuer
    private lateinit var seatRepository: SeatRepository

    @BeforeEach
    fun setUp() {
        seatRepository = FakeSeatRepository()
        ticketIssuer = TicketIssuer(seatRepository)
    }

    @Test()
    fun `예매와 티켓을 함께 생성해 리턴한다 `() {
        // given
        val discountFactor =
            DiscountFactorFixture.create(
                reserveDateTime = LocalDateTime.of(2025, 2, 1, 12, 0, 0),
            )
        val commands =
            listOf(
                TicketIssuer.SeatReserveCommand(
                    seat =
                        SeatFixture.create(
                            id = 1L,
                            seatClass = SeatClassFixture.create(price = Money.of(10000), classType = "VIP"),
                            seatStatus = SeatStatus.UN_RESERVED,
                        ),
                    discountPolicy = DiscountPolicy.none(),
                ),
                TicketIssuer.SeatReserveCommand(
                    seat =
                        SeatFixture.create(
                            id = 2L,
                            seatClass = SeatClassFixture.create(price = Money.of(10000), classType = "VIP"),
                            seatStatus = SeatStatus.UN_RESERVED,
                        ),
                    discountPolicy = DiscountPolicyFixture.createPercent(percent = 0.5, name = "할인할인"),
                ),
            )

        // when
        val result = ticketIssuer.issue(commands, discountFactor)

        // then
        assertThat(result.size).isEqualTo(2)
        assertThat(result.find { it.getDiscountInfo().name == "할인할인" }!!.getTotalAmount()).isEqualTo(Money.of(5000))
        assertThat(result.find { it.getDiscountInfo().name != "할인할인" }!!.getTotalAmount()).isEqualTo(Money.of(10000))

        assertThat(seatRepository.findById(1L).get().getSeatStatus()).isEqualTo(SeatStatus.RESERVED)
        assertThat(seatRepository.findById(2L).get().getSeatStatus()).isEqualTo(SeatStatus.RESERVED)
    }
}
