package org.nield.rxkotlinmath

import rx.Observable
import java.math.BigDecimal

fun Observable<Long>.sum() = reduce { total, next -> total + next }
fun Observable<Long>.min() = reduce {min, next -> if (min > next) next else min }
fun Observable<Long>.max() = reduce {min, next -> if (min < next) next else min }

fun Observable<Long>.averageAsDouble() = map(Long::toDouble).average()
fun Observable<Long>.averageAsFloat() = map(Long::toFloat).average()
fun Observable<Long>.averageAsBigDecimal() = map { BigDecimal.valueOf(it.toLong()) }.average()

fun Observable<Long>.varianceAsDouble() = map(Long::toDouble).variance()
fun Observable<Long>.varianceAsFloat() = map(Long::toFloat).variance()
fun Observable<Long>.varianceAsBigDecimal() = map { BigDecimal.valueOf(it.toLong()) }.variance()

fun Observable<Long>.standardDeviationAsFloat() = map(Long::toFloat).standardDeviation()
fun Observable<Long>.standardDeviationAsDouble() = map(Long::toDouble).standardDeviation()
fun Observable<Long>.standardDeviationAsBigDecimal() =  map { BigDecimal.valueOf(it.toLong()) }.standardDeviation()