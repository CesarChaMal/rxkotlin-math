package org.nield.rxkotlinmath

import rx.Observable
import java.math.BigDecimal

fun Observable<Int>.sum() = reduce { total, next -> total + next }

fun Observable<Int>.min() = reduce {min, next -> if (min > next) next else min }

fun Observable<Int>.max() = reduce {min, next -> if (min < next) next else min }


fun Observable<Int>.averageAsDouble() = this.publish().autoConnect(2).let {
    Observable.zip(it.sum(), it.count()) { sum, count -> (sum *1.0) / (count * 1.0) }
}

fun Observable<Int>.averageAsFloat() = this.publish().autoConnect(2).let {
    Observable.zip(it.sum(), it.count()) { sum, count -> (sum *1.0F) / (count * 1.0F) }
}

fun Observable<Int>.averageAsBigDecimal() = this.publish().autoConnect(2).let {
    Observable.zip(it.sum(), it.count()) { sum, count -> BigDecimal.valueOf(sum.toLong()) / BigDecimal.valueOf(count.toLong()) }
}

fun Observable<Int>.varianceAsDouble() = this.replay().autoConnect().let { numbers ->
    numbers.averageAsDouble().replay(1).autoConnect().flatMap { avg ->
        numbers.map { (it.toDouble() - avg).let { it * it } }
    }.averageAsDouble()
}

fun Observable<Int>.varianceAsFloat() = this.replay().autoConnect().let { numbers ->
    numbers.averageAsDouble().replay(1).autoConnect().flatMap { avg ->
        numbers.map { (it.toDouble() - avg).let { it * it } }
    }.averageAsFloat()
}

fun Observable<Int>.varianceAsBigDecimal() = this.replay().autoConnect().let { numbers ->
    numbers.averageAsBigDecimal().flatMap { avg ->
        numbers.map { (BigDecimal.valueOf(it.toDouble()) - avg).let { it * it } }
    }.averageAsBigDecimal()
}

fun Observable<Int>.standardDeviationAsFloat() = varianceAsFloat()
        .map { Math.sqrt(it.toDouble()).toFloat() }

fun Observable<Int>.standardDeviationAsDouble() = varianceAsDouble()
        .map { Math.sqrt(it) }

fun Observable<Int>.standardDeviationAsBigDecimal() = varianceAsBigDecimal()
        .map { BigDecimal.valueOf(Math.sqrt(it.toDouble())) }