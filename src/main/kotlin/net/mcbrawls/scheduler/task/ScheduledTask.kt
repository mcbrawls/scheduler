package net.mcbrawls.scheduler.task

import net.mcbrawls.scheduler.Scheduler

/**
 * Represents a task after being scheduled.
 */
data class ScheduledTask(
    /**
     * The task to perform.
     */
    val task: Task,

    /**
     * The time that the task was scheduled at in nanoseconds.
     */
    val scheduledAtNanos: Long,

    /**
     * The owner scheduler.
     */
    val owner: Scheduler
) {
    /**
     * Cancels the scheduled task.
     * @return whether the task was cancelled
     */
    fun cancel(): Boolean {
        return owner.cancel(this)
    }

    companion object {
        /**
         * Creates a predicate for [currentTime] to check if a task should be performed.
         * @return a predicate of a scheduled task
         */
        fun shouldPerformPredicate(currentTime: Long): (ScheduledTask) -> Boolean {
            return { (task, scheduledAtNanos) -> currentTime - scheduledAtNanos >= task.delay.toNanos() }
        }
    }
}
