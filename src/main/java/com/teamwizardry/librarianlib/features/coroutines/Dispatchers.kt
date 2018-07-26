package com.teamwizardry.librarianlib.features.coroutines

import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executor

internal class QueueExecutor(): Executor {
    val tasks: Queue<Runnable> = ConcurrentLinkedQueue<Runnable>()

    override fun execute(r: Runnable) {
        tasks.add(r)
    }

    fun runTasks() {
        while(true) {
            val task = tasks.poll() ?: break
            task.run()
        }
    }
}

internal val clientTickExecutor = QueueExecutor()
internal val serverTickExecutor = QueueExecutor()

