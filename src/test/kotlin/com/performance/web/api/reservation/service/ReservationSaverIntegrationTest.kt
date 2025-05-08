package com.performance.web.api.reservation.service

import com.ninjasquad.springmockk.MockkBean
import com.performance.web.api.reservation.domain.Customer
import com.performance.web.api.reservation.domain.PerformanceSessionInfo
import com.performance.web.api.reservation.domain.Reservation
import com.performance.web.api.reservation.domain.ReservationCodeGenerator
import com.performance.web.api.reservation.domain.ReservationRepository
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
import java.time.LocalTime


@SpringBootTest
@ActiveProfiles("testh2")
class ReservationSaverIntegrationTest(
) {
    @MockkBean
    private lateinit var reservationCodeGenerator: ReservationCodeGenerator

    @Autowired
    private lateinit var reservationRepository: ReservationRepository

    @Autowired
    private lateinit var reservationSaver: ReservationSaver

    @Test
    fun `예약코드 중복 두 번 발생 후 세 번째에 성공한다`() {
        // given: 중복될 reservationCode 미리 저장
        every { reservationCodeGenerator.generate() } returnsMany listOf(
            "DUPLICATE-CODE",
            "DUPLICATE-CODE",
            "UNIQUE-CODE",
        )

        val duplicateCode = "DUPLICATE-CODE"
        val existingReservation = Reservation(
            reservationCode = duplicateCode,
            sessionId = 1L,
            performanceSessionInfo = PerformanceSessionInfo(
                performanceName = "name",
                sessionStartDate = LocalDate.now(),
                sessionStartTime = LocalTime.now(),
                sessionEndTime = LocalTime.now(),
            ),
            customer = Customer(1L),
            tickets = listOf(),
        )


        reservationRepository.save(existingReservation)

        val command = ReservationSaver.ReservationSaveCommand(
            sessionId = 1L,
            performanceSessionInfo = existingReservation.getPerformanceSessionInfo(),
            customer = Customer(1L),
            tickets = listOf(),
        )

        // when
        val saved = reservationSaver.saveAndRetry(command)

        // then
        assertThat(saved.getReservationCode()).isEqualTo("UNIQUE-CODE")
    }

    @Test
    fun `성공 케이스는 첫 번째 시도에 성공`() {
        every { reservationCodeGenerator.generate() } returns "SUCCESS-CODE"


        val command = ReservationSaver.ReservationSaveCommand(
            sessionId = 1L,
            performanceSessionInfo = PerformanceSessionInfo(
                performanceName = "name",
                sessionStartDate = LocalDate.now(),
                sessionStartTime = LocalTime.now(),
                sessionEndTime = LocalTime.now(),
            ),
            customer = Customer(1L),
            tickets = listOf(),
        )

        val result = reservationSaver.saveAndRetry(command)

        assertThat(result.getReservationCode()).isEqualTo("SUCCESS-CODE")
    }

}
