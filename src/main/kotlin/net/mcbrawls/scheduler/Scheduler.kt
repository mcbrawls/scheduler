package net.mcbrawls.scheduler

import net.mcbrawls.scheduler.task.ExecutionType
import net.mcbrawls.scheduler.task.ScheduledTask
import net.mcbrawls.scheduler.task.Task
import kotlin.time.Duration

/**
 * The structure of a scheduler.
 */
interface Scheduler {
    /**
     * Processes a tick of the scheduler.
     */
    fun processTick()

    /**
     * Schedules a task.
     * @return the scheduled task
     */
    fun schedule(task: Task, delay: Duration, repeatDelay: Duration? = null): ScheduledTask

    /**
     * Schedules a task from the given arguments.
     * @return the scheduled task
     */
    fun schedule(

        /**
         * The delay until the task is performed.
         */
        delay: Duration,

        /**
         * The delay until the task repeats.
         */
        repeatDelay: Duration? = null,

        /**
         * How the task should be executed.
         */
        executionType: ExecutionType = ExecutionType.SYNC,

        /**
         * The function of the task.
         */
        runnable: Runnable,
    ): ScheduledTask

    /**
     * Cancels the given task.
     * @return whether the task was cancelled
     */
    fun cancel(task: ScheduledTask): Boolean
}
