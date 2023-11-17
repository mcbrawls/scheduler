package net.mcbrawls.scheduler

import net.mcbrawls.scheduler.task.ExecutionType
import net.mcbrawls.scheduler.task.ScheduledTask
import net.mcbrawls.scheduler.task.Task
import java.time.Duration

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
    fun schedule(task: Task): ScheduledTask

    /**
     * Schedules a task from the given arguments.
     * @return the scheduled task
     */
    fun schedule(
        /**
         * The function of the task.
         */
        runnable: Runnable,

        /**
         * The delay until the task is performed.
         */
        delay: Duration,

        /**
         * How the task should be executed.
         */
        executionType: ExecutionType = ExecutionType.SYNC
    ): ScheduledTask {
        val task = Task(runnable, delay, executionType)
        return schedule(task)
    }

    /**
     * Cancels the given task.
     * @return whether the task was cancelled
     */
    fun cancel(task: ScheduledTask): Boolean
}
