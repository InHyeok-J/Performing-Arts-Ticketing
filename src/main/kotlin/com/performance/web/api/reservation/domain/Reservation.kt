package com.performance.web.api.reservation.domain

import com.performance.web.api.common.domain.Money
import com.performance.web.api.common.domain.sum

class Reservation(
    id: Long = 0L,
    reservationCode: String,
    sessionId: Long,
    performanceSessionInfo: PerformanceSessionInfo,
    customer: Customer,
    tickets: List<Ticket> = mutableListOf(),
) {

    private val _id: Long = id
    private val _reservationCode = reservationCode
    private val _sessionId: Long = sessionId
    private val _performanceSessionInfo: PerformanceSessionInfo = performanceSessionInfo
    private val _customer: Customer = customer
    private val _totalAmount: Money = tickets.map { it.getTotalAmount() }.toList().sum()
    private val _tickets: List<Ticket> = tickets

    fun getId(): Long = _id

    fun getReservationCode(): String = _reservationCode

    fun getTickets(): List<Ticket> = _tickets

    fun getCustomer(): Customer = _customer

    fun getTotalAmount(): Money = _totalAmount

    fun getSessionId(): Long = _sessionId

    fun getPerformanceSessionInfo(): PerformanceSessionInfo = _performanceSessionInfo
}
