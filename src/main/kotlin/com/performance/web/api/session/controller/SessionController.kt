package com.performance.web.api.session.controller

import com.performance.web.api.session.controller.dto.SessionApiResponse
import com.performance.web.api.session.controller.dto.SessionCreateApiRequest
import com.performance.web.api.session.service.SessionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/sessions")
class SessionController(
    private val sessionService: SessionService
) {

    @GetMapping("/all")
    fun getAllSession(): ResponseEntity<List<SessionApiResponse>> {
        val result = sessionService.getAll()
        return ResponseEntity.ok()
            .body(result.map { SessionApiResponse.from(it) })
    }

    @PostMapping
    fun create(@RequestBody sessionCreateApiRequest: SessionCreateApiRequest): ResponseEntity<SessionApiResponse> {
        val result = sessionService.create(sessionCreateApiRequest.toCommand())
        return ResponseEntity.ok()
            .body(SessionApiResponse.from(result))
    }

}
