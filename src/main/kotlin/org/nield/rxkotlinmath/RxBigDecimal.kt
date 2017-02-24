package org.nield.rxkotlinmath

import rx.Observable
import java.math.BigDecimal

fun Observable<BigDecimal>.sum() = reduce { total, next -> total + next }
fun Observable<BigDecimal>.min() = reduce { min, next -> if (min > next) next else min }
fun Observable<BigDecimal>.max() = reduce { min, next -> if (min < next) next else min }

fun Observable<BigDecimal>.average() = publish().autoConnect(2).let {
    Observable.zip(it.sum(), it.count()) { sum, count -> BigDecimal.valueOf(sum.toLong()) / BigDecimal.valueOf(count.toLong()) }
}

fun Observable<BigDecimal>.variance() = replay().autoConnect().let { numbers ->
    numbers.average().replay(1).autoConnect().flatMap { avg ->
        numbers.map { (it - avg).let { it * it } }
    }.average()
}

fun Observable<BigDecimal>.standardDeviation() = variance()
        .map { BigDecimal.valueOf(Math.sqrt(it.toDouble())) }