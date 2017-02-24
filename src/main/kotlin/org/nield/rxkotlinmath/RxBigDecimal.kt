package org.nield.rxkotlinmath

import rx.Observable
import java.math.BigDecimal

fun Observable<BigDecimal>.sum() = reduce { total, next -> total + next }

fun Observable<BigDecimal>.min() = reduce { min, next -> if (min > next) next else min }

fun Observable<BigDecimal>.max() = reduce { min, next -> if (min < next) next else min }

fun Observable<BigDecimal>.averageAsBigDecimal() = publish().autoConnect(2).let {
    Observable.zip(it.sum(), it.count()) { sum, count -> BigDecimal.valueOf(sum.toLong()) / BigDecimal.valueOf(count.toLong()) }
}

fun Observable<BigDecimal>.averageAsInt() = averageAsBigDecimal().map { it.toInt() }

fun Observable<BigDecimal>.averageAsDouble() = averageAsBigDecimal().map { it.toDouble() }

fun Observable<BigDecimal>.averageAsFloat() = averageAsBigDecimal().map { it.toFloat() }

fun Observable<BigDecimal>.varianceAsBigDecimal() = replay().autoConnect().let { numbers ->
    numbers.averageAsBigDecimal().replay(1).autoConnect().flatMap { avg ->
        numbers.map { (it - avg).let { it * it } }
    }.averageAsBigDecimal()
}

fun Observable<BigDecimal>.varianceAsInt() = varianceAsBigDecimal().averageAsInt()

fun Observable<BigDecimal>.varianceAsDouble() = varianceAsBigDecimal().averageAsDouble()

fun Observable<BigDecimal>.varianceAsFloat() = varianceAsBigDecimal().averageAsFloat()
