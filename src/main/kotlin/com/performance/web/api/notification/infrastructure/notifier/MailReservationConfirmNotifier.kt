package com.performance.web.api.notification.infrastructure.notifier

import com.performance.web.api.notification.exception.MailSendFailedException
import com.performance.web.api.notification.infrastructure.mail.MailTemplateRenderer
import com.performance.web.api.notification.service.ReservationConfirmNotifier
import com.performance.web.api.notification.service.dto.ReservationConfirmDto
import jakarta.mail.MessagingException
import org.springframework.mail.MailException
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

        try{

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
        }catch (ex: MailException) {
            throw MailSendFailedException("메일 전송 중 오류 발생 ", ex)
        }catch (ex: MessagingException) {
            throw MailSendFailedException("메일 메시지 구성 중 오류 발생", ex)
        }
    }

}
