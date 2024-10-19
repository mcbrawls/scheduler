package net.mcbrawls.scheduler.test

import net.mcbrawls.scheduler.AbsoluteScheduler
import net.mcbrawls.scheduler.duration.TickDuration.inWholeTicks
import net.mcbrawls.scheduler.duration.TickDuration.ticks
import kotlin.math.abs
import kotlin.math.min
import kotlin.test.Test
import kotlin.test.assertEquals

object SchedulerTest {
    private val milliTime: Long get() = System.currentTimeMillis()

    @Test
    fun scheduleTaskWithAbsoluteSchedulerFor60TicksRepeatedWithProcessDelay() {
        var passes = 0
        val expectedPasses = 10

        // schedule
        val scheduler = AbsoluteScheduler()
        val expectedMillis = (3 + expectedPasses - 1) * 1000L

        var prescheduleTimeMs = milliTime
        println("Scheduling pass")
        scheduler.schedule(
            60.ticks,
            20.ticks,
        ) {
            passes++
            println("Pass #$passes after ${(milliTime - prescheduleTimeMs) / 1000.0}s")
            prescheduleTimeMs = milliTime
        }

        // wait for schedule and tick
        val timeMs = milliTime
        while (true) {
            val delta = milliTime - timeMs

            // timeout after expected millis
            if (delta >= expectedMillis * 2) {
                break
            }

            // only process scheduler tick after 2 seconds
            if (delta > 2 * 1000) {
                scheduler.processTick()

                if (passes == expectedPasses) {
                    println("Passed!")
                    break
                }
            }
        }

        // assert
        val deltaTimeMs = milliTime - timeMs
        val percentageDiff = abs(deltaTimeMs - expectedMillis) / ((deltaTimeMs + expectedMillis) / 2.0) * 100
        val percentageDiffString = percentageDiff.toString()
        val roundedPercentageDiff = percentageDiffString.substring(0, min(5, percentageDiffString.length))

        println("Delta time $deltaTimeMs ms, expected time $expectedMillis, diff $roundedPercentageDiff%")
        assert(percentageDiff < 0.2)
    }

    @Test
    fun tickDurationConversion() {
        val expected = 50L
        val tickDuration = expected.ticks
        assertEquals(tickDuration.inWholeTicks, expected)
    }
}
