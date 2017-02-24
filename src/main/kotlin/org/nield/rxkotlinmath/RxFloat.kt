package org.nield.rxkotlinmath

import rx.Observable

fun Observable<Float>.sum() = reduce { total, next -> total + next }
fun Observable<Float>.min() = reduce { min, next -> if (min > next) next else min }
fun Observable<Float>.max() = reduce { min, next -> if (min < next) next else min }

fun Observable<Float>.average() = publish()
        .autoConnect(2).let {
            Observable.zip(it.sum(), it.count()) { sum, count -> sum / count.toFloat() }
        }

fun Observable<Float>.meanAbsoluteDeviation() = replay().autoConnect().let { numbers ->
    numbers.average().flatMap { avg ->
        numbers.map { (it - avg) }
    }.average()
}

fun Observable<Float>.variance() = replay().autoConnect().let { numbers ->
    numbers.average().flatMap { avg ->
        numbers.map { (it.toDouble() - avg).let { it * it } }
    }.average()
}

fun Observable<Float>.standardDeviation() = variance().map { Math.sqrt(it.toDouble()).toFloat() }