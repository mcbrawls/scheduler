package net.mcbrawls.scheduler.task

import net.mcbrawls.scheduler.Scheduler
import kotlin.time.Duration

/**
 * Represents a task after being scheduled.
 */
data class ScheduledTask(
    /**
     * The task to perform.
     */
    val task: Task,

    /**
     * The owner scheduler.
     */
    val owner: Scheduler,

    /**
     * The delay until the task is performed.
     */
    val delay: Duration,

    /**
     * The delay until the task repeats.
     */
    val repeatDelay: Duration?,

    /**
     * The time that the task was scheduled at in nanoseconds.
     */
    var scheduledAtNanos: Long
) {
    /**
     * Cancels the scheduled task.
     * @return whether the task was cancelled
     */
    fun cancel(): Boolean {
        return owner.cancel(this)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other?.javaClass != this.javaClass) return false

        other as ScheduledTask
        return other.task == task
    }

    override fun hashCode(): Int {
        var result = task.hashCode()
        result = 31 * result + owner.hashCode()
        result = 31 * result + delay.hashCode()
        result = 31 * result + (repeatDelay?.hashCode() ?: 0)
        result = 31 * result + scheduledAtNanos.hashCode()
        return result
    }

    companion object {
        /**
         * Creates a predicate for [currentTime] to check if a task should be performed.
         * @return a predicate of a scheduled task
         */
        fun shouldPerformPredicate(currentTime: Long): (ScheduledTask) -> Boolean {
            return { task -> currentTime - task.scheduledAtNanos >= task.delay.inWholeNanoseconds }
        }
    }
}
