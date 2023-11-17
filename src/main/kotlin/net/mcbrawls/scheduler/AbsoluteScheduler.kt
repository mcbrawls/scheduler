package net.mcbrawls.scheduler

/**
 * A scheduler which performs in relation to absolute time.
 * The scheduler will calculate the time taken between ticks and perform tasks respectively.
 */
class AbsoluteScheduler : AbstractScheduler() {
    override val currentTimeNanos: Long get() = nanoTime

    companion object {
        /**
         * Provides the current time in nanoseconds.
         */
        private val nanoTime: Long get() = System.nanoTime()
    }
}
