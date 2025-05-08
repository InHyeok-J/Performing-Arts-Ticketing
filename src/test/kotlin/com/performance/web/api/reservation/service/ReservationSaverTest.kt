package com.performance.web.api.reservation.service

import com.performance.web.api.common.domain.BusinessException
import com.performance.web.api.fixtures.ReservationFixture
import com.performance.web.api.reservation.domain.ReservationCodeGenerator
import com.performance.web.api.reservation.domain.ReservationRepository
import com.performance.web.api.reservation.domain.TodayBasedRandomStringReservationCodeGenerator
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.dao.DataIntegrityViolationException

// unit test
class ReservationSaverTest {

    private lateinit var reservationRepository: ReservationRepository
    private lateinit var reservationSaver: ReservationSaver
    private val reservationCodeGenerator: ReservationCodeGenerator = TodayBasedRandomStringReservationCodeGenerator()

    @BeforeEach
    fun init() {
        reservationRepository = mockk()

        reservationSaver = ReservationSaver(
            reservationRepository = reservationRepository,
            reservationCodeGenerator = reservationCodeGenerator,
        )
    }


    @Test
    fun `예약코드 중복 예외 2번 후 3번째에 성공한다`() {
        //given
        val reservation = ReservationFixture.create()

        val command = ReservationSaver.ReservationSaveCommand(
            sessionId = reservation.getSessionId(),
            performanceSessionInfo = reservation.getPerformanceSessionInfo(),
            customer = reservation.getCustomer(),
            tickets = reservation.getTickets(),
        )

        var attempt = 0
        every { reservationRepository.save(any()) } answers {
            when (attempt++) {
                0, 1 -> throw DataIntegrityViolationException("중복 에러")
                else -> reservation
            }
        }

        //when
        reservationSaver.saveAndRetry(command)

        //then
        verify(exactly = 3) { reservationRepository.save(any()) }
    }

    @Test
    fun `예약코드 중복 예외 3번 후 예외를 반환한다 `() {
        //given
        val reservation = ReservationFixture.create()

        val command = ReservationSaver.ReservationSaveCommand(
            sessionId = reservation.getSessionId(),
            performanceSessionInfo = reservation.getPerformanceSessionInfo(),
            customer = reservation.getCustomer(),
            tickets = reservation.getTickets(),
        )

        var attempt = 0
        every { reservationRepository.save(any()) } throws DataIntegrityViolationException("중복 에러")

        //when + then

        Assertions.assertThatThrownBy { reservationSaver.saveAndRetry(command) }
            .isInstanceOf(BusinessException::class.java)
            .hasMessage("알수 없는 예외입니다, 다시한번 시도해주세요.")

        verify(exactly = 3) { reservationRepository.save(any()) }
    }

}
