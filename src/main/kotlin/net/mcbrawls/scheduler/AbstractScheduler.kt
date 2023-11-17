package net.mcbrawls.scheduler

import net.mcbrawls.scheduler.task.ExecutionType
import net.mcbrawls.scheduler.task.ScheduledTask
import net.mcbrawls.scheduler.task.Task
import java.time.Duration

/**
 * The default implementatiojn of a scheduler.
 */
abstract class AbstractScheduler : Scheduler, Schedulable {
    override val scheduler: Scheduler get() = this

    private val tasks: MutableList<ScheduledTask> = mutableListOf()

    abstract val currentTimeNanos: Long

    override fun processTick() {
        // get tasks to perform
        val predicate = ScheduledTask.shouldPerformPredicate(currentTimeNanos)
        val tasksToPerform = tasks.filter(predicate)

        // perform
        if (tasksToPerform.isNotEmpty()) {
            // execute tasks
            tasksToPerform.forEach { (task) -> task.execute() }

            // remove all tasks
            tasks.removeAll(tasksToPerform)

            // repeat tasks
            tasksToPerform
                .associateWith { it.repeatDelay }
                .forEach { (scheduledTask, repeatDelay) ->
                    if (repeatDelay != null) {
                        tasks.add(
                            scheduledTask.copy(
                                delay = repeatDelay,
                                repeatDelay = repeatDelay,
                                scheduledAtNanos = currentTimeNanos
                            )
                        )
                    }
                }
        }
    }

    override fun schedule(task: Task, delay: Duration, repeatDelay: Duration?): ScheduledTask {
        val scheduledTask = ScheduledTask(task, this, delay, repeatDelay, currentTimeNanos)
        tasks.add(scheduledTask)
        return scheduledTask
    }

    override fun schedule(runnable: Runnable, delay: Duration, repeatDelay: Duration?, executionType: ExecutionType): ScheduledTask {
        val task = Task(runnable, executionType)
        return schedule(task, delay, repeatDelay)
    }

    override fun cancel(task: ScheduledTask): Boolean {
        return tasks.remove(task)
    }
}
