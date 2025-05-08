package com.performance.web.api.reservation.domain

import com.performance.web.api.common.domain.Money

class Ticket(
    id: Long = 0L,
    totalAmount: Money,
    regularPrice: Money,
    ticketSeatInfo: TicketSeatInfo,
    discountInfo: DiscountInfo,
) {

    private val _id: Long = id
    private val _totalAmount: Money = totalAmount
    private val _seatInfo: TicketSeatInfo = ticketSeatInfo
    private val _regularPrice: Money = regularPrice
    private val _discountInfo: DiscountInfo = discountInfo

    fun getId(): Long = _id

    fun getTotalAmount(): Money = _totalAmount

    fun getRegularPrice(): Money = _regularPrice

    fun getTicketSeatInfo(): TicketSeatInfo = _seatInfo

    fun getDiscountInfo(): DiscountInfo = _discountInfo
}
