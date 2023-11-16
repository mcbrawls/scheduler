package net.mcbrawls.scheduler.task

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Duration

/**
 * A single task within a scheduler.
 */
data class Task(
    /**
     * The function of the task.
     */
    val runnable: Runnable,

    /**
     * The delay until the task is performed.
     */
    val delay: Duration,

    /**
     * How the task should be executed.
     */
    val executionType: ExecutionType
) {
    /**
     * Performs this task.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun execute() {
        // execute runnable
        when (executionType) {
            ExecutionType.SYNC -> runnable.run()
            ExecutionType.ASYNC -> GlobalScope.launch { runnable.run() }
        }
    }
}
