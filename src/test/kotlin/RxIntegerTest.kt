import org.junit.Assert.assertTrue
import org.junit.Test
import org.nield.rxkotlinmath.*
import rx.Observable
import java.math.BigDecimal
import java.util.*

class RxIntegerTest {

    @Test
    fun testSum() = Observable.just(10, 20, 30, 40)
                .sum()
                .toBlocking()
                .first()
                .let { assertTrue(it == 100) }


    @Test
    fun testMin() = Observable.just(20, 30, 10, 40)
                .min()
                .toBlocking()
                .first()
                .let { assertTrue(it == 10) }


    @Test
    fun testMax() = Observable.just(20, 30, 40, 10)
                .max()
                .toBlocking()
                .first()
                .let { assertTrue(it == 40) }


    @Test
    fun testMode() = Observable.just(20, 20, 30, 30, 90, 90, 20, 10, 90)
                .mode()
                .toList()
                .toBlocking()
                .first()
                .let { assertTrue(Arrays.equals(it.toIntArray(), intArrayOf(20, 90))) }

    @Test
    fun testAverageAsFloat() = Observable.just(20, 30, 40, 10)
            .averageAsFloat()
            .toBlocking()
            .first()
            .let { assertTrue(it == 25F) }

    @Test
    fun testAverageAsDouble() = Observable.just(20, 30, 40, 10)
            .averageAsDouble()
            .toBlocking()
            .first()
            .let { assertTrue(it == 25.0) }

    @Test
    fun testAverageAsBigDecimal() = Observable.just(20, 30, 40, 10)
            .averageAsBigDecimal()
            .toBlocking()
            .first()
            .let { assertTrue(it.setScale(2) == BigDecimal.valueOf(25.0).setScale(2)) }

    @Test
    fun testVarianceAsDouble() = Observable.just(20, 30, 40, 10)
            .varianceAsDouble()
            .toBlocking()
            .first()
            .let { assertTrue(it == 125.0) }


    @Test
    fun testStandardDeviationAsBigDecimal() = Observable.just(20, 30, 40, 10)
            .standardDeviationAsBigDecimal()
            .toBlocking()
            .first()
            .let { assertTrue(it == BigDecimal.valueOf(11.180339887498949)) }

}