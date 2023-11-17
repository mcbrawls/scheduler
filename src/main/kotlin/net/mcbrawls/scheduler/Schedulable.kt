package net.mcbrawls.scheduler

import net.mcbrawls.scheduler.task.ExecutionType
import net.mcbrawls.scheduler.task.ScheduledTask
import net.mcbrawls.scheduler.task.Task
import java.time.Duration

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

    override fun schedule(runnable: Runnable, delay: Duration, repeatDelay: Duration?, executionType: ExecutionType): ScheduledTask {
        return scheduler.schedule(runnable, delay, repeatDelay, executionType)
    }

    override fun cancel(task: ScheduledTask): Boolean {
        return scheduler.cancel(task)
    }
}
