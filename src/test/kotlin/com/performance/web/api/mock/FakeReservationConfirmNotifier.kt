package com.performance.web.api.mock

import com.performance.web.api.notification.service.ReservationConfirmNotifier
import com.performance.web.api.notification.service.dto.ReservationConfirmDto

class FakeReservationConfirmNotifier : ReservationConfirmNotifier {

    private var isSend = false

    override fun notify(confirmDto: ReservationConfirmDto) {
        isSend = true;
    }

    fun init() {
        isSend = false
    }

    fun isSend(): Boolean {
        return isSend
    }
}
