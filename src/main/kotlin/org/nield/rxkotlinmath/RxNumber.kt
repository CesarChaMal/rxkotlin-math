package org.nield.rxkotlinmath

import rx.Observable
import kotlin.comparisons.compareValues

fun <T> Observable<T>.mode() = groupBy { it }.flatMap { grp -> grp.count().map { grp.key to it } }
        .toSortedList { p1,p2 -> compareValues(p2.second,p1.second)  }
        .flatMap { list ->
            Observable.from(list)
                    .takeWhile { it.second == list[0].second }
                    .map { it.first }
        }