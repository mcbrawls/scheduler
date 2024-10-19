package net.mcbrawls.scheduler

import net.mcbrawls.scheduler.duration.TickDuration

/**
 * A scheduler which performs in relation to a local tick variable.
 */
class RelativeScheduler : AbstractScheduler() {
    override val currentTimeNanos: Long get() = tickNanos

    private var tickNanos: Long = 0L

    override fun processTick() {
        super.processTick()

        // increment tick
        tickNanos += TickDuration.NANOS_PER_TICK
    }
}
