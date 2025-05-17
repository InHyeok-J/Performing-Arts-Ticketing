package com.performance.web.api.reservation.infrastructure.notifier

import com.performance.web.api.reservation.infrastructure.mail.MailTemplateRenderer
import com.performance.web.api.reservation.service.ReservationConfirmNotifier
import com.performance.web.api.reservation.service.dto.ReservationConfirmDto
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class MailReservationConfirmNotifier(
    private val mailSender: JavaMailSender,
    private val templateRenderer: MailTemplateRenderer
): ReservationConfirmNotifier {

    override fun notify(confirmDto: ReservationConfirmDto) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setTo(confirmDto.customerEmail)
        helper.setSubject("예매가 완료되었습니다")
        helper.setFrom("test@example.com")

        val variables = mapOf(
            "dto" to  confirmDto,
            "reservationTime" to LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), // 실제 예약 시간 포맷팅
        )

        val htmlBody = templateRenderer.render("reservation/success-mail", variables)
        helper.setText(htmlBody, true) // HTML 모드 true

        mailSender.send(message)
    }

}
