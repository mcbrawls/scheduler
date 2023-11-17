package net.mcbrawls.scheduler

import net.mcbrawls.scheduler.duration.TickDuration
import net.mcbrawls.scheduler.task.ScheduledTask
import net.mcbrawls.scheduler.task.Task
import java.time.Duration

/**
 * A scheduler which performs in relation to a local tick variable.
 */
class RelativeScheduler(
    /**
     * The amount of time which is simulated per tick.
     */
    durationPerTick: Duration = TickDuration.create(1)
) : Scheduler {
    private val tasks: MutableList<ScheduledTask> = mutableListOf()

    private val nanosPerTick = durationPerTick.toNanos()

    private var tickNanos: Long = 0L

    override fun processTick() {
        // get tasks to perform
        val predicate = ScheduledTask.shouldPerformPredicate(tickNanos)
        val tasksToPerform = tasks.filter(predicate)

        // perform
        if (tasksToPerform.isNotEmpty()) {
            // execute tasks
            tasksToPerform.forEach { (task) -> task.execute() }

            // remove all tasks
            tasks.removeAll(tasksToPerform)
        }

        // increment tick
        tickNanos += nanosPerTick
    }

    override fun schedule(task: Task): ScheduledTask {
        val scheduledTask = ScheduledTask(task, tickNanos, this)
        tasks.add(scheduledTask)
        return scheduledTask
    }

    override fun cancel(task: ScheduledTask): Boolean {
        return tasks.remove(task)
    }
}
