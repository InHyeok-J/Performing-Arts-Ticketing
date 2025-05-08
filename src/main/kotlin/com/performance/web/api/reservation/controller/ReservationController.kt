package com.performance.web.api.reservation.controller

import com.performance.web.api.member.service.MemberService
import com.performance.web.api.reservation.controller.dto.ReservationApiRequest
import com.performance.web.api.reservation.controller.dto.ReservationApiResponse
import com.performance.web.api.reservation.service.usecase.FindReservationUseCase
import com.performance.web.api.reservation.service.usecase.ReservationUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reservations")
class ReservationController(
    private val reservationUseCase: ReservationUseCase,
    private val findReservationUseCase: FindReservationUseCase,
    private val memberService: MemberService,
) {

    @PostMapping("")
    fun createReservation(
        @RequestBody reservationApiRequest: ReservationApiRequest,
    ): ResponseEntity<ReservationApiResponse> {
        val customer = memberService.findById(reservationApiRequest.customerId)
        val result = reservationUseCase.reserve(reservationApiRequest.toServiceCommand(customer))
        return ResponseEntity.status(201)
            .body(ReservationApiResponse.from(result))
    }

    @GetMapping("/{reservationId}")
    fun getReservation(
        @PathVariable("reservationId") reservationId: Long,
    ): ResponseEntity<ReservationApiResponse> {
        val result = findReservationUseCase.findById(reservationId)
        return ResponseEntity.ok(ReservationApiResponse.from(result))
    }
}
