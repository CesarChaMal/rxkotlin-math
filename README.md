# RxKotlin Math

Mathematical and statistical extension functions for RxKotlin.

Development will resume after I get [Kotlin Statistics](https://github.com/thomasnield/kotlin-statistics) off the ground so I can use it as a dependency.

This is a work-in-progress to create a comprehensive library of Kotlin extension functions for RxJava 1.0/2.0 to quickly perform common math and statistics computations. The plan is to as precisely as possible support `Int`, `Double`, `Float`, `Long`, `BigDecimal`, and `BigInteger` variants of these extension functions.

```kotlin
val items = Observable.just(1.234, 1231.5, 345343.2, 1231.5, 0232.0, 02342.0)
        .publish()
        .autoConnect(7)

items.min().subscribe { println("Min: $it")}
items.max().subscribe { println("Max: $it")}
items.sum().subscribe { println("Sum: $it")}
items.mode().subscribe { println("Mode: $it")}

items.average().subscribe { println("Average: $it") }
items.variance().subscribe { println("Variance: $it")}
items.standardDeviation().subscribe { println("Standard Deviation: $it")}
```

**OUTPUT:**

```
Min: 1.234
Max: 345343.2
Sum: 350381.434
Mode: 1231.5
Average: 58396.905666666666
Variance: 1.646821770193556E10
Standard Deviation: 128328.55372805991
```
