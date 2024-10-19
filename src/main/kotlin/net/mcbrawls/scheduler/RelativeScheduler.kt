package net.mcbrawls.scheduler

import net.mcbrawls.scheduler.duration.TickDuration
import kotlin.time.Duration

/**
 * A scheduler which performs in relation to a local tick variable.
 */
class RelativeScheduler(
    /**
     * The amount of time which is simulated per tick.
     */
    durationPerTick: Duration = TickDuration.create(1)
) : AbstractScheduler() {
    override val currentTimeNanos: Long get() = tickNanos

    private val nanosPerTick = durationPerTick.inWholeNanoseconds

    private var tickNanos: Long = 0L

    override fun processTick() {
        super.processTick()

        // increment tick
        tickNanos += nanosPerTick
    }
}
