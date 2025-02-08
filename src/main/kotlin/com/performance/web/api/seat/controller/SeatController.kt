package com.performance.web.api.seat.controller

import com.performance.web.api.seat.controller.dto.SeatApiResponse
import com.performance.web.api.seat.service.SeatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/seats")
class SeatController(
    private val seatService: SeatService
) {


    @GetMapping("/{sessionId}")
    fun getSeat(@PathVariable sessionId: Long): ResponseEntity<List<SeatApiResponse>> {
        val result = seatService.findBySessionId(sessionId)

        return ResponseEntity.ok()
            .body(result.map { SeatApiResponse.from(it) })
    }
}
