package com.performance.web.api.notification.service

import com.performance.web.api.notification.service.dto.ReservationConfirmDto
import org.springframework.stereotype.Service

@Service
class ReservationConfirmNotificationService(
    private val reservationConfirmNotifier: ReservationConfirmNotifier
) {


    fun notify(reservationConfirmDto: ReservationConfirmDto) {
        reservationConfirmNotifier.notify(reservationConfirmDto)
    }
}
