package com.performance.web.api.notification.handler

import com.performance.web.api.notification.service.ReservationConfirmNotificationService
import com.performance.web.api.notification.service.dto.ReservationConfirmDto
import com.performance.web.api.reservation.domain.ReservationConfirmEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ReservationConfirmNotificationHandler(
    private val reservationConfirmNotificationService: ReservationConfirmNotificationService,
) {

    @EventListener
    fun handle(event: ReservationConfirmEvent) {

        reservationConfirmNotificationService.notify(
            ReservationConfirmDto.from(
                reservation = event.reservation,
                member = event.member,
                performance = event.performance,
            ),
        )
    }
}
