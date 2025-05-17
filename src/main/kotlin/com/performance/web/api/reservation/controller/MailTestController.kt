package com.performance.web.api.reservation.controller


import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@Profile("default","local")
@RestController
class MailTestController() {

    @GetMapping("/test/send")
    fun testMail(): String {
//        mailService.sendReservationConfirmedEmail("example@example.com", Reservation(
//            id = 0,
//            reservationCode = "RESERVER_",
//            sessionId = 1L,
//            performanceSessionInfo = PerformanceSessionInfo(
//                performanceName = "공연임",
//                sessionStartDate = LocalDate.of(2025, 5, 5),
//                sessionStartTime = LocalTime.of(13, 0),
//                sessionEndTime = LocalTime.of(15, 0)
//            ),
//            customer = Customer(1),
//            tickets = listOf(
//                Ticket(
//                    id = 1L,
//                    totalAmount = Money.Companion.of(100_000L),
//                    regularPrice = Money.Companion.of(100_000L),
//                    ticketSeatInfo = TicketSeatInfo(
//                        row = 1,
//                        column = 1,
//                        seatType = "R"
//                    ),
//                    discountInfo = DiscountInfo(
//                        name = "할인 없음"
//                    )
//                ),
//                Ticket(
//                    id = 2L,
//                    totalAmount = Money.Companion.of(100_000L),
//                    regularPrice = Money.Companion.of(100_000L),
//                    ticketSeatInfo = TicketSeatInfo(
//                        row = 2,
//                        column = 1,
//                        seatType = "R"
//                    ),
//                    discountInfo = DiscountInfo(
//                        name = "할인 없음"
//                    )
//                )
//            )
//        )
//        )
        return "메일 전송 성공"
    }

}
