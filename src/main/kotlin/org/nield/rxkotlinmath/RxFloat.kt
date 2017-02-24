package org.nield.rxkotlinmath

import rx.Observable

fun Observable<Float>.sum() = reduce { total, next -> total + next }
fun Observable<Float>.min() = reduce { min, next -> if (min > next) next else min }
fun Observable<Float>.max() = reduce { min, next -> if (min < next) next else min }

fun Observable<Float>.median() = toList().flatMap { list ->
    val listSize = list.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        Observable.just(list[middle])
    else
        Observable.just((list[middle - 1] + list[middle]) / 2.0)
}


fun Observable<Float>.average() = publish()
        .autoConnect(2).let {
            Observable.zip(it.sum(), it.count()) { sum, count -> sum / count.toFloat() }
        }

fun Observable<Float>.meanAbsoluteDeviation() = toList().flatMap { numbers ->
    Observable.from(numbers).average().flatMap { avg ->
        Observable.from(numbers).map { it - avg }
    }.average()
}

fun Observable<Float>.variance() = toList().flatMap { numbers ->
    Observable.from(numbers).average().flatMap { avg ->
        Observable.from(numbers).map { (it - avg).let { it * it } }
    }.average()
}


fun Observable<Float>.standardDeviation() = variance().map { Math.sqrt(it.toDouble()).toFloat() }