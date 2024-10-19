package net.mcbrawls.scheduler

import net.mcbrawls.scheduler.task.ExecutionType
import net.mcbrawls.scheduler.task.ScheduledTask
import net.mcbrawls.scheduler.task.Task
import kotlin.time.Duration

/**
 * An interface which allows objects to act as schedulers.
 */
interface Schedulable : Scheduler {
    /**
     * The scheduler to inherit from.
     */
    val scheduler: Scheduler

    override fun processTick() {
        return scheduler.processTick()
    }

    override fun schedule(task: Task, delay: Duration, repeatDelay: Duration?): ScheduledTask {
        return scheduler.schedule(task, delay, repeatDelay)
    }

    override fun schedule(delay: Duration, repeatDelay: Duration?, executionType: ExecutionType, runnable: Runnable): ScheduledTask {
        return scheduler.schedule(delay, repeatDelay, executionType, runnable)
    }

    override fun cancel(task: ScheduledTask): Boolean {
        return scheduler.cancel(task)
    }
}
