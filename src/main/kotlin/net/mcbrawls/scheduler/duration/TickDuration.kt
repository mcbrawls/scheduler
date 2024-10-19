package net.mcbrawls.scheduler.duration

import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds

/**
 * Game tick duration utilities.
 */
object TickDuration {
    const val NANOS_PER_TICK = 1_000_000_000L / 20

    /**
     * A tick duration for a single tick.
     */
    val NEXT_TICK = NANOS_PER_TICK.nanoseconds

    inline val Int.ticks get() = toLong().ticks
    inline val Long.ticks get() = (this * NANOS_PER_TICK).nanoseconds
    inline val Double.ticks get() = toLong().ticks

    inline val Duration.inWholeTicks get() = inWholeNanoseconds / NANOS_PER_TICK
}
