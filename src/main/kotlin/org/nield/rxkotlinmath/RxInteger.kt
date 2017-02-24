package org.nield.rxkotlinmath

import rx.Observable
import java.math.BigDecimal

fun Observable<Int>.sum() = reduce { total, next -> total + next }
fun Observable<Int>.min() = reduce {min, next -> if (min > next) next else min }
fun Observable<Int>.max() = reduce {min, next -> if (min < next) next else min }

fun Observable<Int>.averageAsDouble() = map(Int::toDouble).average()
fun Observable<Int>.averageAsFloat() = map(Int::toFloat).average()
fun Observable<Int>.averageAsBigDecimal() = map { BigDecimal.valueOf(it.toLong()) }.average()

fun Observable<Int>.varianceAsDouble() = map(Int::toDouble).variance()
fun Observable<Int>.varianceAsFloat() = map(Int::toFloat).variance()
fun Observable<Int>.varianceAsBigDecimal() = map { BigDecimal.valueOf(it.toLong()) }.variance()

fun Observable<Int>.standardDeviationAsFloat() = map(Int::toFloat).standardDeviation()
fun Observable<Int>.standardDeviationAsDouble() = map(Int::toDouble).standardDeviation()
fun Observable<Int>.standardDeviationAsBigDecimal() =  map { BigDecimal.valueOf(it.toLong()) }.standardDeviation()