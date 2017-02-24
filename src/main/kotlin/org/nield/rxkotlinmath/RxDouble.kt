package org.nield.rxkotlinmath

import rx.Observable
import java.math.BigDecimal
import kotlin.comparisons.compareValues

fun Observable<Double>.sum() = reduce { total, next -> total + next }

fun Observable<Double>.min() = reduce {min, next -> if (min > next) next else min }

fun Observable<Double>.max() = reduce {min, next -> if (min < next) next else min }


fun Observable<Double>.averageAsInt() = this.publish().autoConnect(2).let {
    Observable.zip(it.sum(), it.count()) { sum, count -> sum / count }.map(Double::toInt)
}

fun Observable<Double>.averageAsDouble() = this.publish().autoConnect(2).let {
    Observable.zip(it.sum(), it.count()) { sum, count -> sum / count.toDouble() }
}

fun Observable<Double>.averageAsFloat() = this.publish().autoConnect(2).let {
    Observable.zip(it.sum(), it.count()) { sum, count -> sum.toFloat() / count.toFloat() }
}

fun Observable<Double>.averageAsBigDecimal() = this.publish().autoConnect(2).let {
    Observable.zip(it.sum(), it.count()) { sum, count -> BigDecimal.valueOf(sum) / BigDecimal.valueOf(count.toLong()) }
}

fun Observable<Double>.varianceAsDouble() = this.replay().autoConnect().let { numbers ->

    val average = numbers.averageAsDouble().replay(1).autoConnect()

    val variance = average.flatMap { avg ->
        numbers.map { (it - avg).let { it * it } }
    }
}