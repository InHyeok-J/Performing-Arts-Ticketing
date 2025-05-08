package com.performance.web.api.common.infrastructure.jpa

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["com.performance.web.api"])
class JpaConfig
