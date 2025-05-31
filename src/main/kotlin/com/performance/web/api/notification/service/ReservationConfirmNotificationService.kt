package com.performance.web.api.notification.service

import com.performance.web.api.common.utils.Logger
import com.performance.web.api.notification.exception.MailSendFailedException
import com.performance.web.api.notification.service.dto.ReservationConfirmDto
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service


@Service
class ReservationConfirmNotificationService(
    private val reservationConfirmNotifier: ReservationConfirmNotifier
) {

    @Retryable(
        value = [MailSendFailedException::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 2000) // 2초 간격 재시도
    )
    fun notify(reservationConfirmDto: ReservationConfirmDto) {
        reservationConfirmNotifier.notify(reservationConfirmDto)
    }


    @Recover
    fun recover(ex: MailSendFailedException, reservationConfirmDto: ReservationConfirmDto) {
        Logger.error("ReservationConfirmNotificationService", ex.message, ex)
        // throw require
        throw ex
    }
}
