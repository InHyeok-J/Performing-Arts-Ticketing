package com.performance.web.api.session.controller

import com.performance.web.api.session.controller.dto.SessionApiResponse
import com.performance.web.api.session.controller.dto.SessionCreateApiRequest
import com.performance.web.api.session.controller.dto.SessionWithSeatInfoApiResponse
import com.performance.web.api.session.service.SessionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sessions")
class SessionController(
    private val sessionService: SessionService,
) {

    @GetMapping("/all")
    fun getAllSession(): ResponseEntity<List<SessionApiResponse>> {
        val result = sessionService.getAll()
        return ResponseEntity.ok()
            .body(result.map { SessionApiResponse.from(it) })
    }

    @PostMapping
    fun create(
        @RequestBody sessionCreateApiRequest: SessionCreateApiRequest,
    ): ResponseEntity<SessionApiResponse> {
        val result = sessionService.create(sessionCreateApiRequest.toCommand())
        return ResponseEntity.ok()
            .body(SessionApiResponse.from(result))
    }

    @GetMapping("/{performanceId}/seats")
    fun getSessionAndSeatsByYearMonth(
        @PathVariable performanceId: Long,
        @RequestParam year: Int,
        @RequestParam(defaultValue = "1") month: Int,
    ): ResponseEntity<List<SessionWithSeatInfoApiResponse>> {
        val result = sessionService.getWithSeatsByYearMonth(performanceId, year, month)
        return ResponseEntity.ok()
            .body(result.map { SessionWithSeatInfoApiResponse.from(it) })
    }
}
