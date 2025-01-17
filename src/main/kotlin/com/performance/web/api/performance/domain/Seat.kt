package com.performance.web.api.performance.domain

import com.performance.web.api.performance.domain.discount.DiscountFactor
import com.performance.web.api.performance.domain.discount.DiscountPolicy


class Seat(
    id: Long = 0L,
    seatClass: SeatClass,
    seatStatus: SeatStatus
) {

    private val _id: Long = id
    private val _seatClass: SeatClass = seatClass
    private var _seatStatus: SeatStatus = seatStatus

    fun reserve(discount: DiscountPolicy, discountFactor: DiscountFactor): Ticket {
        require(_seatStatus.canReserve()) { " 이미 예약된 좌석 입니다 " }
        _seatStatus = SeatStatus.RESERVED

        return Ticket(
            totalAmount = _seatClass.calculateTotalAmount(discount, discountFactor), // 티켓을 구매한 당시의 가격
            regularPrice = _seatClass.getPrice(), // Ticket 을 구매한 당시의 정가
            seat = this,
        )
    }


    fun getId(): Long = _id


}
