package com.performance.web.api.seat.domain

import com.performance.web.api.common.domain.BusinessException
import com.performance.web.api.discount.domain.DiscountFactor
import com.performance.web.api.discount.domain.DiscountPolicy
import com.performance.web.api.reservation.domain.DiscountInfo
import com.performance.web.api.reservation.domain.Ticket
import com.performance.web.api.reservation.domain.TicketSeatInfo

class Seat(
    id: Long = 0L,
    seatClass: SeatClass,
    seatStatus: SeatStatus,
    seatPosition: SeatPosition,
    sessionId: Long,
    version: Int,
) {

    private val _id: Long = id
    private val _seatClass: SeatClass = seatClass
    private var _seatStatus: SeatStatus = seatStatus
    private val _seatPosition: SeatPosition = seatPosition
    private val _sessionId: Long = sessionId
    private val _version: Int = version

    fun reserveTicket(
        discountPolicy: DiscountPolicy,
        discountFactor: DiscountFactor,
    ): Ticket {
        if (!_seatStatus.canReserve()) {
            throw BusinessException("이미 예약된 좌석입니다.")
        }
        _seatStatus = SeatStatus.RESERVED

        val regularPrice = _seatClass.price
        val totalAmount = regularPrice.minus(discountPolicy.calculateFee(regularPrice, discountFactor))

        return Ticket(
            regularPrice = regularPrice,
            totalAmount = totalAmount,
            ticketSeatInfo = TicketSeatInfo.from(this),
            discountInfo = DiscountInfo(discountPolicy.getName()),
        )
    }

    fun getId(): Long = _id

    fun getSeatClass(): SeatClass = _seatClass

    fun getSeatStatus(): SeatStatus = _seatStatus

    fun getSeatPosition(): SeatPosition = _seatPosition

    fun getSessionId(): Long = _sessionId

    fun getVersion(): Int = _version
}
