package net.mcbrawls.scheduler

import net.mcbrawls.scheduler.task.ScheduledTask
import net.mcbrawls.scheduler.task.Task

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

    override fun schedule(task: Task): ScheduledTask {
        return scheduler.schedule(task)
    }

    override fun cancel(task: ScheduledTask): Boolean {
        return scheduler.cancel(task)
    }
}
