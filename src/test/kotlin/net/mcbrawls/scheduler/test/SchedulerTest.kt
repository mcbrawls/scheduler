package net.mcbrawls.scheduler.test

import net.mcbrawls.scheduler.AbsoluteScheduler
import java.time.Duration
import kotlin.math.abs
import kotlin.test.Test

object SchedulerTest {
    private val milliTime: Long get() = System.currentTimeMillis()

    @JvmStatic
    fun main(args: Array<String>) {
    }

    @Test
    fun scheduleTaskFor3Seconds() {
        var passed = false

        // schedule
        val scheduler = AbsoluteScheduler()
        val expectedMillis = 3 * 1000L

        println("Scheduling pass")
        scheduler.schedule({ passed = true }, Duration.ofMillis(expectedMillis))

        // wait for schedule and tick
        val timeMs = milliTime
        while (milliTime - timeMs < 5 * 1000) {
            scheduler.processTick()

            if (passed) {
                println("Passed!")
                break
            }
        }

        // assert
        val deltaTimeMs = milliTime - timeMs
        val percentageDiff = abs(deltaTimeMs - expectedMillis) / ((deltaTimeMs + expectedMillis) / 2.0) * 100
        val roundedPercentageDiff = percentageDiff.toString().substring(0, 5)

        println("Delta time $deltaTimeMs ms, expected time $expectedMillis, diff $roundedPercentageDiff%")
        assert(percentageDiff < 0.1)
    }
}
