package net.mcbrawls.scheduler.duration

import java.time.Duration

/**
 * Game tick duration utilities.
 */
object TickDuration {
    /**
     * Creates a duration from ticks and ticks per second.
     * @return a duration
     */
    fun create(
        /**
         * The amount of ticks within the duration.
         */
        ticks: Long,

        /**
         * The amount of ticks which comprise a second.
         */
        ticksPerSecond: Long = 20
    ): Duration {
        val nanosPerTick = 1_000_000_000L / ticksPerSecond
        return Duration.ofNanos(nanosPerTick * ticks)
    }

    /**
     * Calculates the ticks of the given duration.
     * @return a quantity of ticks
     */
    fun getTicks(
        /**
         * The duration to get timings from.
         */
        duration: Duration,

        /**
         * The amount of ticks which comprise a second.
         */
        ticksPerSecond: Long = 20
    ): Long {
        val nanos = duration.toNanos()
        val nanosPerTick = 1_000_000_000L / ticksPerSecond
        return nanos / nanosPerTick
    }
}
