package com.performance.web.api.performance.service

import com.performance.web.api.common.domain.Money
import com.performance.web.api.discount.domain.PercentDiscountPolicy
import com.performance.web.api.fixtures.PerformanceFixture
import com.performance.web.api.mock.FakeDiscountPolicyRepository
import com.performance.web.api.mock.FakePerformanceRepository
import com.performance.web.api.performance.domain.PerformanceRepository
import com.performance.web.api.performance.domain.PerformanceSeatClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PerformanceServiceTest {

    private lateinit var performanceService: PerformanceService
    private lateinit var performanceRepository: PerformanceRepository

    @BeforeEach
    fun setUp() {
        performanceRepository = FakePerformanceRepository()
        performanceRepository.save(
            PerformanceFixture.create(
                name = "공연1",
                runTimeMinutes = 180,
                seatClasses =
                    listOf(
                        PerformanceSeatClass(
                            price = Money.of(10000),
                            classType = "VIP",
                        ),
                        PerformanceSeatClass(
                            price = Money.of(10000),
                            classType = "R",
                        ),
                    ),
            ),
        )
        val discountPolicyRepository = FakeDiscountPolicyRepository()
        discountPolicyRepository.save(
            PercentDiscountPolicy(
                name = "할인1",
                percent = 0.1,
                seatClassId = 1L,
            ),
        )
        discountPolicyRepository.save(
            PercentDiscountPolicy(
                name = "50%특가할인",
                percent = 0.5,
                seatClassId = 1L,
            ),
        )
        discountPolicyRepository.save(
            PercentDiscountPolicy(
                name = "다른공연의할인",
                percent = 0.5,
                seatClassId = 3L,
            ),
        )

        performanceService = PerformanceService(performanceRepository, discountPolicyRepository)
    }

    @Test
    fun `findById 시 해당하는 공연을 조회할 수 있다`() {
        // given
        // when
        val result = performanceService.findById(1)

        // then
        assertThat(result.getId()).isEqualTo(1L)
        assertThat(result.getName()).isEqualTo("공연1")
        assertThat(result.getSeatClasses().get(0).getClassType()).isEqualTo("VIP")
        assertThat(result.getSeatClasses().get(0).getPrice()).isEqualTo(Money.of(10000))
    }

    @Test
    fun `findSeatClassByIdWithDiscounts 시 공연Id에 해당하는 할인 목록을 전부 조회할 수있다`() {
        // given
        // when
        var result = performanceService.findSeatClassByIdWithDiscounts(1L)

        // then
        assertThat(result.size).isEqualTo(2)

        val vipClassDiscounts = result.first { it.performanceSeatClass.getClassType() == "VIP" }
        val rClassDiscounts = result.first { it.performanceSeatClass.getClassType() == "R" }

        assertThat(vipClassDiscounts.performanceSeatClass.getClassType()).isEqualTo("VIP")
        assertThat(vipClassDiscounts.discountPolies)
            .hasSize(2) // "할인1"과 "50%특가할인"이 포함됨
            .extracting("name")
            .containsExactlyInAnyOrder("할인1", "50%특가할인")
        assertThat(rClassDiscounts.performanceSeatClass.getClassType()).isEqualTo("R")
        assertThat(rClassDiscounts.discountPolies).isEmpty()
    }
}
