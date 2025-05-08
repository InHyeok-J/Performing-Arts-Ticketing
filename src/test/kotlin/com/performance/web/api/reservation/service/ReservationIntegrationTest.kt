package com.performance.web.api.reservation.service

import com.performance.web.api.reservation.service.dto.ReservationCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
@ActiveProfiles("test")
// @SqlGroup(
//    Sql(value = ["/sql/reservation-concurrency-test.sql"], executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
//    Sql(value = ["/sql/delete-all-data.sql"], executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
// )
@Tag("TooLongTest")
class ReservationIntegrationTest(
    @Autowired private val reservationService: ReservationServiceLockFacade,
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
        val threadCount = 10
        val latch = CountDownLatch(threadCount)
        val executor = Executors.newFixedThreadPool(threadCount)
        val successCount = AtomicInteger()
        val failCount = AtomicInteger()

        // 테스트를 위한 시간 셋팅
        val successTimes = mutableListOf<Long>()
        val failTimes = mutableListOf<Long>()
        val startTime = System.nanoTime()

        for (i in 1..threadCount) {
            executor.execute {
                val command = creatCommand(i.toLong(), listOf(1L, 2L))
                val threadStartTime = System.nanoTime() // 개별 스레드 시작 시간

                try {
                    val reserve = reservationService.reserve(command)
                    val elapsedTime = System.nanoTime() - threadStartTime // 개별 스레드 실행 시간
                    successTimes.add(elapsedTime)
                    log.info { "성공: $i 번째 예매 완료" }
                    successCount.incrementAndGet()
                } catch (e: Exception) {
                    val elapsedTime = System.nanoTime() - threadStartTime
                    failTimes.add(elapsedTime)
                    log.error { "${e.message} , ${e.javaClass.simpleName}" }
                    failCount.incrementAndGet()
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await() // 모든 스레드가 완료될 때까지 대기
        executor.shutdown()

        val totalElapsedTime = System.nanoTime() - startTime // 전체 테스트 실행 시간

        log.info { "성공 횟수: ${successCount.get()} -- 실패 횟수: ${failCount.get()}" }
        log.info { "총 실행 시간: ${totalElapsedTime / 1_000_000}ms" }

        // 개별 실행 시간 로그
        log.info { "성공한 스레드 실행 시간 목록: ${successTimes.map { it / 1_000_000 }} ms" }
        log.info { "실패한 스레드 실행 시간 목록: ${failTimes.sortedDescending().take(20).map { it / 1_000_000 }} ms" }
        log.info { "실패한 스레드 평균 시간 목록: ${failTimes.average() / 1_000_000} ms" }

        assertThat(successCount.get()).isEqualTo(1)
        assertThat(failCount.get()).isEqualTo(threadCount - 1)
    }

    //    @Test
    fun `좌석 예매 순차적 동시성 테스트`() {
        val memberCount = 10
        val successCount = AtomicInteger()
        val failCount = AtomicInteger()

        for (i in 1..memberCount) {
            val command = creatCommand(i.toLong(), listOf(1L, 2L))
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

    private fun creatCommand(
        memberId: Long,
        seatIds: List<Long>,
    ): ReservationCommand =
        ReservationCommand(
            customerId = memberId,
            sessionId = 1L,
            seatCommands =
                seatIds.map {
                    ReservationCommand.ReservationSeatCommand(
                        seatId = it,
                        discountPolicyId = 1L,
                    )
                }.toList(),
        )
}
