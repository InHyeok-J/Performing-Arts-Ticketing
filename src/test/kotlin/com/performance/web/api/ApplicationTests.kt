package com.performance.web.api

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [Application::class])
@ActiveProfiles("testh2")
class ApplicationTests {

    @Test
    fun contextLoads() {
    }
}
