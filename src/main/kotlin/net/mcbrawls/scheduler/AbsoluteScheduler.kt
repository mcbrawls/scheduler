package net.mcbrawls.scheduler

import net.mcbrawls.scheduler.task.ExecutionType
import net.mcbrawls.scheduler.task.ScheduledTask
import net.mcbrawls.scheduler.task.Task
import java.time.Duration

/**
 * A scheduler which performs in relation to absolute time.
 * The scheduler will calculate the time taken between ticks and perform tasks respectively.
 */
class AbsoluteScheduler : Scheduler {
    private val tasks: MutableList<ScheduledTask> = mutableListOf()

    override fun processTick() {
        // get tasks to perform
        val predicate = ScheduledTask.shouldPerformPredicate(nanoTime)
        val tasksToPerform = tasks.filter(predicate)

        // perform
        if (tasksToPerform.isNotEmpty()) {
            // execute tasks
            tasksToPerform.forEach { (task) -> task.execute() }

            // remove all tasks
            tasks.removeAll(tasksToPerform)
        }
    }

    override fun schedule(task: Task): ScheduledTask {
        val scheduledTask = ScheduledTask(task, nanoTime, this)
        tasks.add(scheduledTask)
        return scheduledTask
    }

    override fun schedule(runnable: Runnable, delay: Duration, executionType: ExecutionType): ScheduledTask {
        val task = Task(runnable, delay, executionType)
        return schedule(task)
    }

    override fun cancel(task: ScheduledTask): Boolean {
        return tasks.remove(task)
    }

    companion object {
        /**
         * Provides the current time in nanoseconds.
         */
        val nanoTime: Long get() = System.nanoTime()
    }
}
