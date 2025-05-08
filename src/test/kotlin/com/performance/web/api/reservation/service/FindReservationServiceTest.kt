package com.performance.web.api.reservation.service

import com.performance.web.api.common.domain.Money
import com.performance.web.api.common.domain.ResourceNotFoundException
import com.performance.web.api.mock.FakeReservationRepository
import com.performance.web.api.reservation.domain.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime

class FindReservationServiceTest {

    private lateinit var findReservationService: FindReservationService
    private lateinit var reservationRepository: ReservationRepository

    @BeforeEach
    fun init() {
        reservationRepository = FakeReservationRepository()

        findReservationService = FindReservationService(reservationRepository)
    }

    @Test
    fun `findById 시 없는 id로 요청하면 예외를 반환한다`() {
        // given
        val id = 2L

        // when
        // then
        assertThatThrownBy {
            findReservationService.findById(id)
        }.isInstanceOf(ResourceNotFoundException::class.java)
    }

    @Test
    fun `findById시 조회가 성공하면 정상적으로 예매를 반환한다`() {
        // givne
        reservationRepository.save(
            Reservation(
                sessionId = 1L,
                performanceSessionInfo =
                    PerformanceSessionInfo(
                        performanceName = "공연",
                        sessionStartDate = LocalDate.now(),
                        sessionStartTime = LocalTime.now(),
                        sessionEndTime = LocalTime.now(),
                    ),
                customer = Customer(1L),
                tickets =
                    listOf(
                        Ticket(
                            totalAmount = Money.of(10000),
                            regularPrice = Money.of(10000),
                            ticketSeatInfo = TicketSeatInfo(1, 1, 1, "VIP"),
                            discountInfo = DiscountInfo("할인 적용 X"),
                        ),
                    ),
            ),
        )

        // when
        val result = findReservationService.findById(1L)

        assertThat(result.getId()).isEqualTo(1L)
        assertThat(result.getTotalAmount()).isEqualTo(Money.of(10000))
        assertThat(result.getCustomer().getId()).isEqualTo(1L)
        assertThat(result.getTickets().size).isEqualTo(1)
    }
}
