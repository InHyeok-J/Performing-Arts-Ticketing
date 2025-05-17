package com.performance.web.api.reservation.service

import com.performance.web.api.reservation.domain.Reservation
import com.performance.web.api.reservation.service.dto.ReservationConfirmDto

interface ReservationConfirmNotifier {

    fun notify(confirmDto: ReservationConfirmDto)

}
