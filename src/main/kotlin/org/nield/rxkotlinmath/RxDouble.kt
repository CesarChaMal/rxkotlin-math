package org.nield.rxkotlinmath

import rx.Observable

fun Observable<Double>.sum() = reduce { total, next -> total + next }
fun Observable<Double>.min() = reduce {min, next -> if (min > next) next else min }
fun Observable<Double>.max() = reduce {min, next -> if (min < next) next else min }

fun Observable<Double>.average() = publish().autoConnect(2).let {
    Observable.zip(it.sum(), it.count()) { sum, count -> sum / count.toDouble() }
}

fun Observable<Double>.variance() = replay().autoConnect().let { numbers ->
    numbers.average().flatMap { avg ->
        numbers.map { (it - avg).let { it * it } }
    }.average()
}

fun Observable<Double>.meanAbsoluteDeviation() = replay().autoConnect().let { numbers ->
    numbers.average().flatMap { avg ->
        numbers.map { (it - avg) }
    }.average()
}

fun Observable<Double>.standardDeviation() = variance()
        .map { Math.sqrt(it.toDouble()) }

