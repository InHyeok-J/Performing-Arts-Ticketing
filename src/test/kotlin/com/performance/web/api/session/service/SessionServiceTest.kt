package com.performance.web.api.session.service

import com.performance.web.api.common.domain.BusinessException
import com.performance.web.api.fixtures.PerformanceFixture
import com.performance.web.api.fixtures.PerformanceSeatClassFixture
import com.performance.web.api.mock.FakePerformanceRepository
import com.performance.web.api.mock.FakeSessionRepository
import com.performance.web.api.session.domain.SessionRepository
import com.performance.web.api.session.service.dto.SessionCreateCommand
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class SessionServiceTest {

    private lateinit var sessionService: SessionService
    private lateinit var sessionRepository: SessionRepository

    @BeforeEach
    fun setup() {
        sessionRepository = FakeSessionRepository()
        val performanceRepository = FakePerformanceRepository()
        performanceRepository.save(
            PerformanceFixture.create(
                seatClasses = listOf(
                    PerformanceSeatClassFixture.create(),
                ),
            ),
        )

        sessionService = SessionService(sessionRepository, performanceRepository)
    }

    @Test
    fun `존재하지 않는 공연으로 예매 생성시 예외를 반환한다`() {
        //given
        val command = SessionCreateCommand(
            performanceId = 2L,
            startDateTime = LocalDateTime.now()
        )

        //when
        //then
        assertThatThrownBy {
            sessionService.create(command)
        }.isInstanceOf(BusinessException::class.java)
    }

    @Test
    fun `회차를 생성할 수 있다`(){
        //given
        val command = SessionCreateCommand(
            performanceId = 1L,
            startDateTime = LocalDateTime.now()
        )

        //when
        val result = sessionService.create(command)

        assertThat(result.getId()).isEqualTo(1)
    }
}
