package com.performance.web.api.reservation.domain

import com.performance.web.api.discount.domain.DiscountFactor
import com.performance.web.api.discount.domain.DiscountPolicy
import com.performance.web.api.seat.domain.Seat
import com.performance.web.api.seat.domain.SeatRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TicketIssuer(
    private val seatRepository: SeatRepository,
) {

    @Transactional()
    fun issue(
        commands: List<SeatReserveCommand>,
        discountFactor: DiscountFactor,
    ): List<Ticket> {
        val tickets = mutableListOf<Ticket>()

        for ((seat, discountPolicy) in commands) {
            val ticket = seat.reserveTicket(discountPolicy, discountFactor)
            tickets.add(ticket)
        }
        seatRepository.saveAll(commands.map { it.seat })
        return tickets
    }

    data class SeatReserveCommand(
        val seat: Seat,
        val discountPolicy: DiscountPolicy,
    )
}
