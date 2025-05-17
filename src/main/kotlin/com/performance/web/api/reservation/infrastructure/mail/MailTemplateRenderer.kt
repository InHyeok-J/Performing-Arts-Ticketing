package com.performance.web.api.reservation.infrastructure.mail

import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Component
class MailTemplateRenderer(
    private val templateEngine: TemplateEngine
) {
    fun render(template: String, variables: Map<String, Any>): String {
        val context = Context()
        context.setVariables(variables)
        return templateEngine.process(template, context)
    }
}
