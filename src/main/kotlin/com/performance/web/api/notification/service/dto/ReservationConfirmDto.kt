package com.performance.web.api.notification.service.dto

import com.performance.web.api.common.domain.Money
import com.performance.web.api.member.domain.Member
import com.performance.web.api.performance.domain.Performance
import com.performance.web.api.reservation.domain.Reservation
import java.time.LocalDate
import java.time.LocalTime

data class ReservationConfirmDto(
    val reservationId: Long,
    val reservationCode: String,
    val customerId: Long,
    val customerName: String,
    val customerEmail: String,
    val totalAmount: Money,
    val confirmPerformanceDto: ConfirmPerformanceDto,
    val confirmTicketDtoList: List<ConfirmTicketDto>,
) {

    data class ConfirmPerformanceDto(
        val performanceName: String,
        val performancePosterUrl: String,
        val sessionStartDate: LocalDate,
        val sessionStartTime: LocalTime,
        val sessionEndTime: LocalTime,
    )

    data class ConfirmTicketDto(
        val totalAmount: Money,
        val regularPrice: Money,
        val row: Int,
        val column: Int,
        val floor: Int,
        val seatType: String,
        val discountName: String
    )


    companion object {

        fun from(reservation: Reservation, member: Member, performance: Performance): ReservationConfirmDto {

            return ReservationConfirmDto(
                reservationId = reservation.getId(),
                reservationCode = reservation.getReservationCode(),
                customerId = member.getId(),
                customerName = member.getName(),
                customerEmail = member.getEmail(),
                confirmPerformanceDto = ConfirmPerformanceDto(
                    performanceName = performance.getName(),
                    performancePosterUrl = performance.getPoster(),
                    sessionStartDate = reservation.getPerformanceSessionInfo().sessionStartDate,
                    sessionStartTime = reservation.getPerformanceSessionInfo().sessionStartTime,
                    sessionEndTime = reservation.getPerformanceSessionInfo().sessionEndTime,
                ),
                totalAmount = reservation.getTotalAmount(),
                confirmTicketDtoList = reservation.getTickets().map { it ->
                    ConfirmTicketDto(
                        totalAmount = it.getTotalAmount(),
                        regularPrice = it.getRegularPrice(),
                        row = it.getTicketSeatInfo().row,
                        column = it.getTicketSeatInfo().column,
                        floor = it.getTicketSeatInfo().floor,
                        seatType = it.getTicketSeatInfo().seatType,
                        discountName = it.getDiscountInfo().name,
                    )
                },
            )
        }
    }
}
