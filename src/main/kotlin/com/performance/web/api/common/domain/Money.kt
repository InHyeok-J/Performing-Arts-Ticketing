package com.performance.web.api.common.domain

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale
import java.util.Objects

data class Money(
    private val amount: BigDecimal,
) {

    companion object {
        val ZERO = this.of(0)

        fun of(amount: Long): Money {
            return Money(BigDecimal(amount))
        }

        fun of(amount: Double): Money {
            return Money(BigDecimal(amount))
        }
    }

    fun plus(amount: Money): Money = Money(this.amount.add(amount.amount))

    fun minus(amount: Money): Money = Money(this.amount.subtract(amount.amount))

    fun times(percent: Double): Money = Money(this.amount.multiply(BigDecimal.valueOf(percent)))

    fun times(percent: Int): Money = Money(this.amount.multiply(BigDecimal(percent)))

    fun divide(divisor: Double): Money = Money(this.amount.divide(BigDecimal.valueOf(divisor)))

    fun isLessThan(other: Money): Boolean = amount < other.amount

    fun isGreaterThanOrEqual(other: Money): Boolean = amount >= other.amount

    fun longValue(): Long {
        return this.amount.toLong()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Money
        return this.amount.toDouble() == other.amount.toDouble()
    }

    override fun hashCode(): Int = Objects.hash(amount.toDouble())

    override fun toString(): String  {
        return NumberFormat.getNumberInstance(Locale.KOREA).format(amount).toString()
    }
}

fun List<Money>.sum(): Money =
    if (this.isNotEmpty()) {
        this.reduce { acc, curr -> acc.plus(curr) }
    } else {
        Money.ZERO
    }
