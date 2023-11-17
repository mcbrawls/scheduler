package net.mcbrawls.scheduler.test

import net.mcbrawls.scheduler.AbsoluteScheduler
import net.mcbrawls.scheduler.duration.TickDuration
import kotlin.math.abs
import kotlin.math.min
import kotlin.test.Test
import kotlin.test.assertEquals

object SchedulerTest {
    private val milliTime: Long get() = System.currentTimeMillis()

    @Test
    fun scheduleTaskWithAbsoluteSchedulerFor60TicksWithProcessDelay() {
        var passed = false

        // schedule
        val scheduler = AbsoluteScheduler()
        val expectedMillis = 3 * 1000L

        println("Scheduling pass")
        scheduler.schedule({ passed = true }, TickDuration.create(60))

        // wait for schedule and tick
        val timeMs = milliTime
        while (true) {
            val delta = milliTime - timeMs

            // timeout at 5s
            if (delta >= 5 * 1000) {
                break
            }

            // only process scheduler tick after 2 seconds
            if (delta > 2 * 1000) {
                scheduler.processTick()

                if (passed) {
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
        assert(percentageDiff < 0.1)
    }

    @Test
    fun tickDurationConversion() {
        val expected = 50L
        val tickDuration = TickDuration.create(expected)
        assertEquals(TickDuration.getTicks(tickDuration), expected)
    }
}
