package com.performance.web.api.common.utils

import com.performance.web.api.common.domain.BusinessException



fun <T : Any> T?.checkNotNullOrThrow(paramName: String): T {
    return this ?: throw BusinessException("$paramName 값이 필요합니다.")
}
