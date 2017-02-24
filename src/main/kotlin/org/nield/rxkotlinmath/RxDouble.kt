package org.nield.rxkotlinmath

import rx.Observable
import java.math.BigDecimal

fun Observable<Double>.sum() = reduce { total, next -> total + next }

fun Observable<Double>.min() = reduce {min, next -> if (min > next) next else min }

fun Observable<Double>.max() = reduce {min, next -> if (min < next) next else min }


fun Observable<Double>.averageAsDouble() = publish().autoConnect(2).let {
    Observable.zip(it.sum(), it.count()) { sum, count -> sum / count.toDouble() }
}

fun Observable<Double>.averageAsFloat() = publish().autoConnect(2).let {
    Observable.zip(it.sum(), it.count()) { sum, count -> sum.toFloat() / count.toFloat() }
}

fun Observable<Double>.averageAsBigDecimal() = publish().autoConnect(2).let {
    Observable.zip(it.sum(), it.count()) { sum, count -> BigDecimal.valueOf(sum.toLong()) / BigDecimal.valueOf(count.toLong()) }
}

fun Observable<Double>.varianceAsDouble() = replay().autoConnect().let { numbers ->
    numbers.averageAsDouble().flatMap { avg ->
        numbers.map { (it - avg).let { it * it } }
    }.averageAsDouble()
}

fun Observable<Double>.varianceAsFloat() = replay().autoConnect().let { numbers ->
    numbers.averageAsDouble().replay(1).autoConnect().flatMap { avg ->
        numbers.map { (it.toDouble() - avg).let { it * it } }
    }.averageAsFloat()
}

fun Observable<Double>.varianceAsBigDecimal() = replay().autoConnect().let { numbers ->
    numbers.averageAsBigDecimal().flatMap { avg ->
        numbers.map { (BigDecimal.valueOf(it.toDouble()) - avg).let { it * it } }
    }.averageAsBigDecimal()
}

fun Observable<Double>.standardDeviationAsFloat() = varianceAsFloat()
        .map { Math.sqrt(it.toDouble()).toFloat() }

fun Observable<Double>.standardDeviationAsDouble() = varianceAsDouble()
        .map { Math.sqrt(it) }

fun Observable<Double>.standardDeviationAsBigDecimal() = varianceAsBigDecimal()
        .map { BigDecimal.valueOf(Math.sqrt(it.toDouble())) }