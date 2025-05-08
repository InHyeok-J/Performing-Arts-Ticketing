package com.performance.web.api.common.utils

import io.github.oshai.kotlinlogging.KotlinLogging

object Logger {
    private val logger = KotlinLogging.logger {}

    // only use develop
    fun infoDev(message: String, data: Any? = null) {
        val caller = Throwable().stackTrace[1]  // 호출자 기준
        val context = "${caller.className}.${caller.methodName}"

        logger.info {
            "[$context] $message - ${objectMapper.writeValueAsString(data)}"
        }
    }

    fun info(
        context: String,
        message: String,
        data: Any? = null,
    ) {
        logger.info {
            "[${context}] $message - ${objectMapper.writeValueAsString(data)}"
        }
    }

    fun error(
        context: String,
        message: String,
        throwable: Throwable? = null,
    ) {
        logger.error {
            "[$context] - ${buildErrorMessage(context, message, throwable)}"
        }
    }


    private fun toJson(data: Any?): String =
        try {
            objectMapper.writeValueAsString(data)
        } catch (e: Exception) {
            "\"[JsonSerializationFailed: ${e.message}]\""
        }

    private fun buildErrorMessage(context: String, message: String, throwable: Throwable?): String {
        val errorInfo = mapOf(
            "context" to context,
            "message" to message,
            "error" to (throwable?.message ?: "null"),
            "stacktrace" to throwable?.stackTrace?.joinToString("\n") { it.toString() },
        )
        return toJson(errorInfo)
    }
}
