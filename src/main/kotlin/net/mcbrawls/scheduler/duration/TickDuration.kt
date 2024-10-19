package net.mcbrawls.scheduler.duration

import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds

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
        return (nanosPerTick * ticks).nanoseconds
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
        val nanos = duration.inWholeNanoseconds
        val nanosPerTick = 1_000_000_000L / ticksPerSecond
        return nanos / nanosPerTick
    }

    /**
     * Creates a tick duration for a single tick.
     * @return a duration
     */
    fun nextTick(
        /**
         * The amount of ticks which comprise a second.
         */
        ticksPerSecond: Long = 20
    ): Duration {
        return create(1, ticksPerSecond)
    }
}
