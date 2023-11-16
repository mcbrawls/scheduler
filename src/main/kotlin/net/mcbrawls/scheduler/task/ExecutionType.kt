package net.mcbrawls.scheduler.task

/**
 * How a task should be executed.
 */
enum class ExecutionType {
    /**
     * The task is run synchronously.
     */
    SYNC,

    /**
     * The task is run asynchronously.
     */
    ASYNC
}
