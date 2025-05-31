package com.performance.web.api.notification.service

import com.ninjasquad.springmockk.MockkBean
import com.performance.web.api.common.domain.Money
import com.performance.web.api.notification.exception.MailSendFailedException
import com.performance.web.api.notification.service.dto.ReservationConfirmDto
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.retry.annotation.EnableRetry
import java.time.LocalDate
import java.time.LocalTime


@Import(TestRetryConfig::class)
@SpringBootTest
class ReservationConfirmNotificationServiceRetryTest() {


    @MockkBean
    private lateinit var reservationConfirmNotifier: ReservationConfirmNotifier

    @Autowired
    private lateinit var reservationConfirmNotificationService: ReservationConfirmNotificationService


    @Test
    fun `notifier 예외 발생 시 최대 3회까지 Retry 를 수행한다`() {

        val dto = createDto()

        every { reservationConfirmNotifier.notify(any()) } throws MailSendFailedException("테스트 예외 발생")

        assertThatThrownBy { reservationConfirmNotificationService.notify(dto) }
            .isInstanceOf(MailSendFailedException::class.java)
            .hasMessage("테스트 예외 발생")


        verify(exactly = 3) { reservationConfirmNotifier.notify(dto) }
    }

    private fun createDto(): ReservationConfirmDto {
        return ReservationConfirmDto(
            reservationCode = "UNIQUE-CODE",
            reservationId = 1L,
            customerId = 1L,
            customerName = "name",
            customerEmail = "email@email.com",
            totalAmount = Money.of(10000L),
            confirmPerformanceDto = ReservationConfirmDto.ConfirmPerformanceDto(
                performanceName = "공연",
                performancePosterUrl = "http://www.com",
                sessionStartDate = LocalDate.now(),
                sessionStartTime = LocalTime.now(),
                sessionEndTime = LocalTime.now(),
            ),
            confirmTicketDtoList = listOf(
            ),
        )
    }
}

@Configuration
@EnableRetry
class TestRetryConfig
