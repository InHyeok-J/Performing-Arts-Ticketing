package com.performance.web.api.reservation.service

import com.performance.web.api.reservation.service.dto.ReservationCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.Sql.ExecutionPhase
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
@ActiveProfiles("test")
@SqlGroup(
//    Sql(value = ["/sql/reservation-concurrency-test.sql"], executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
//    Sql(value = ["/sql/delete-all-data.sql"], executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
)
class ReservationIntegrationTest(
    @Autowired private val reservationService: ReservationService
) {


    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }


    @BeforeEach
    fun init() {
    }

    @Test
    fun `좌석 예매 동시성 테스트`() {
        log.info { "예매 시작전 셋팅" }
        val threadCount = 10;
        val latch = CountDownLatch(threadCount)
        val executor = Executors.newFixedThreadPool(threadCount)
        val successCount = AtomicInteger()
        val failCount = AtomicInteger()


        for (i in 1..threadCount) {
            executor.execute {
                val command = creatCommand(i.toLong(), listOf(1L, 2L));
                try {
                    val reserve = reservationService.reserve(command)
                    successCount.incrementAndGet()
                } catch (e: Exception) {
                    log.error { "${e.message}" }
                    failCount.incrementAndGet()
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await() // 모든 스레드가 완료될 때까지 대기
        executor.shutdown()

        log.info { "성공 횟수: ${successCount.get()} -- 실패 횟수: ${failCount.get()}" }
        assertThat(successCount.get()).isEqualTo(1)
        assertThat(failCount.get()).isEqualTo(9)
    }

    @Test
    fun `좌석 예매 순차적 동시성 테스트`() {
        val memberCount = 10;
        val successCount = AtomicInteger()
        val failCount = AtomicInteger()


        for (i in 1..memberCount) {
            val command = creatCommand(i.toLong(), listOf(1L, 2L));
            try {
                val reserve = reservationService.reserve(command)
                successCount.incrementAndGet()
            } catch (e: Exception) {
                log.error { "${e.message}" }
                failCount.incrementAndGet()
            }
        }

        log.info { "성공 횟수: ${successCount.get()} -- 실패 횟수: ${failCount.get()}" }
        assertThat(successCount.get()).isEqualTo(1)
        assertThat(failCount.get()).isEqualTo(9)
    }


    private fun creatCommand(memberId: Long, seatIds: List<Long>): ReservationCommand {
        return ReservationCommand(
            customerId = memberId,
            sessionId = 1L,
            seatCommands = seatIds.map {
                ReservationCommand.ReservationSeatCommand(
                    seatId = it,
                    discountPolicyId = 1L,
                )
            }.toList(),
        )
    }
}
