package com.performance.web.api.notification.service

import com.performance.web.api.notification.service.dto.ReservationConfirmDto

interface ReservationConfirmNotifier {

    fun notify(confirmDto: ReservationConfirmDto)

}
