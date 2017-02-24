package org.nield.rxkotlinmath

import rx.Observable

fun Observable<Double>.sum() = reduce { total, next -> total + next }
fun Observable<Double>.min() = reduce {min, next -> if (min > next) next else min }
fun Observable<Double>.max() = reduce {min, next -> if (min < next) next else min }

fun Observable<Double>.median() = toList().flatMap { list ->
    val listSize = list.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        Observable.just(list[middle])
    else
        Observable.just((list[middle - 1] + list[middle]) / 2.0)
}

fun Observable<Double>.average() = publish().autoConnect(2).let {
    Observable.zip(it.sum(), it.count()) { sum, count -> sum / count.toDouble() }
}

fun Observable<Double>.variance() = toList().flatMap { numbers ->
    Observable.from(numbers).average().flatMap { avg ->
        Observable.from(numbers).map { (it - avg).let { it * it } }
    }.average()
}

fun Observable<Double>.meanAbsoluteDeviation() = toList().flatMap { numbers ->
    Observable.from(numbers).average().flatMap { avg ->
        Observable.from(numbers).map { it - avg }
    }.average()
}


fun Observable<Double>.standardDeviation() = variance()
        .map { Math.sqrt(it.toDouble()) }

