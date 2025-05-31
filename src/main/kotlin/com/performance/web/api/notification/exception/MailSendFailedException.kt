package com.performance.web.api.notification.exception

import com.performance.web.api.common.domain.BusinessException

class MailSendFailedException(
    message: String = "메일 전송 실패",
    throwable: Throwable? = null,
) : BusinessException(message) {
}
