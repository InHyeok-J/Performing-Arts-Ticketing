package com.performance.web.api.reservation.service

import com.performance.web.api.common.domain.ResourceNotFoundException
import com.performance.web.api.discount.domain.DiscountFactor
import com.performance.web.api.discount.domain.DiscountPolicySelector
import com.performance.web.api.member.domain.MemberRepository
import com.performance.web.api.performance.domain.PerformanceRepository
import com.performance.web.api.reservation.domain.*
import com.performance.web.api.reservation.service.dto.ReservationCommand
import com.performance.web.api.seat.domain.SeatRepository
import com.performance.web.api.session.domain.SessionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class ReservationService(
    private val sessionRepository: SessionRepository,
    private val performanceRepository: PerformanceRepository,
    private val seatRepository: SeatRepository,
    private val memberRepository: MemberRepository,
    private val discountPolicySelector: DiscountPolicySelector,
    private val ticketIssuer: TicketIssuer,
    private val reservationSaver: ReservationSaver
) {

    fun reserve(reservationCommand: ReservationCommand): Reservation {
        val member = memberRepository.findByIdThrown(reservationCommand.customerId)
        val session = sessionRepository.findByIdThrown(reservationCommand.sessionId)
        val performance = performanceRepository.findByIdThrown(session.getPerformanceId())

        // TODO : discountFactor 를 생성하는 책임을 가진 클래스를 추가한다
        val discountFactor =
            DiscountFactor(
                reserveDateTime = LocalDateTime.now(),
                reservationCommand.seatCommands.size,
            )

        val tickets =
            ticketIssuer.issue(
                discountFactor = discountFactor,
                commands = convertSeatReserveCommandList(reservationCommand.seatCommands),
            )

        return reservationSaver.saveAndRetry(
            command = ReservationSaver.ReservationSaveCommand(
                sessionId = session.getId(),
                performanceSessionInfo = PerformanceSessionInfo.create(performance, session),
                customer = Customer(member.getId()),
                tickets = tickets,
            ),
        )
    }

    private fun convertSeatReserveCommandList(
        seatCommand: List<ReservationCommand.ReservationSeatCommand>,
    ): List<TicketIssuer.SeatReserveCommand> =
        seatCommand.map {
            val seat =
                seatRepository.findByIdWithLock(it.seatId).orElseThrow {
                    throw ResourceNotFoundException("Seat ${it.seatId} 을 찾을 수 없습니다. ")
                }
            val discountPolicy =
                discountPolicySelector.findById(it.discountPolicyId).orElseThrow {
                    throw ResourceNotFoundException("$it.discountPolicyId 의 DiscountPolicy 를 찾을 수 없습니다.")
                }
            TicketIssuer.SeatReserveCommand(seat, discountPolicy)
        }.toList()
}
