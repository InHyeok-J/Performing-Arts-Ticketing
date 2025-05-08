package com.performance.web.api.performance.domain

import com.performance.web.api.common.domain.Money

class PerformanceSeatClass(
    id: Long = 0L,
    price: Money,
    classType: String,
) {

    private val _id: Long = id
    private val _price: Money = price
    private val _classType: String = classType

    fun getId(): Long = _id

    fun getPrice(): Money = _price

    fun getClassType(): String = _classType
}
