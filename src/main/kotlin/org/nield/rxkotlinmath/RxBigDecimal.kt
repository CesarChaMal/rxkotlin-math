package org.nield.rxkotlinmath

import rx.Observable
import java.math.BigDecimal

fun Observable<BigDecimal>.sum() = reduce { total, next -> total + next }
fun Observable<BigDecimal>.min() = reduce { min, next -> if (min > next) next else min }
fun Observable<BigDecimal>.max() = reduce { min, next -> if (min < next) next else min }

fun Observable<BigDecimal>.median() = toList().flatMap { list ->
    val listSize = list.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        Observable.just(list[middle])
    else
        Observable.just((list[middle - 1] + list[middle]) / BigDecimal.valueOf(2.0))
}


fun Observable<BigDecimal>.average() = publish().autoConnect(2).let {
    Observable.zip(it.sum(), it.count()) { sum, count -> BigDecimal.valueOf(sum.toLong()) / BigDecimal.valueOf(count.toLong()) }
}

fun Observable<BigDecimal>.meanAbsoluteDeviation() = toList().flatMap { numbers ->
    Observable.from(numbers).average().flatMap { avg ->
        Observable.from(numbers).map { it - avg }
    }.average()
}


fun Observable<BigDecimal>.variance() = toList().flatMap { numbers ->
    Observable.from(numbers).average().flatMap { avg ->
        Observable.from(numbers).map { (it - avg).let { it * it } }
    }.average()
}


fun Observable<BigDecimal>.standardDeviation() = variance()
        .map { BigDecimal.valueOf(Math.sqrt(it.toDouble())) }