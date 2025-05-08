package com.performance.web.api.performance.service.dto

import com.performance.web.api.common.domain.Money
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class PerformanceCreateCommandTest {

    @Test
    fun `Entity를 생성할 수 있다`() {
        // given

        val command =
            PerformanceCreateCommand(
                name = "공연",
                runTime = 100,
                startDate = LocalDate.of(2020, 1, 1),
                endDate = LocalDate.of(2020, 2, 1),
                description = "공연입니다",
                poster = "poster",
                location = "location",
                seatClasses =
                    listOf(
                        PerformanceCreateCommand.PerformanceSeatClassCreateCommand(
                            classType = "VIP",
                            price = Money.of(10000),
                        ),
                        PerformanceCreateCommand.PerformanceSeatClassCreateCommand(
                            classType = "R",
                            price = Money.of(5000),
                        ),
                    ),
            )

        // when
        val result = command.toEntity()

        // then
        assertThat(result.getName()).isEqualTo("공연")
        assertThat(result.getRunTime()).isEqualTo(100)
        assertThat(result.getStartDate()).isEqualTo(LocalDate.of(2020, 1, 1))
        assertThat(result.getEndDate()).isEqualTo(LocalDate.of(2020, 2, 1))
        assertThat(result.getDescription()).isEqualTo("공연입니다")
        assertThat(result.getSeatClasses().size).isEqualTo(2)
    }
}
