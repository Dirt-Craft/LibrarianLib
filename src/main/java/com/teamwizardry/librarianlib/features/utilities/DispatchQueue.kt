package com.teamwizardry.librarianlib.features.utilities

import java.util.concurrent.ConcurrentLinkedQueue

typealias DispatchJob = () -> Unit

class DispatchQueue {
    private val queue = ConcurrentLinkedQueue<DispatchJob>()

    fun dispatch(job: DispatchJob) {
        queue.add(job)
    }

    fun runJobs() {
        while(queue.isNotEmpty()) {
            queue.remove().invoke()
        }
    }

    companion object {
        @JvmStatic val clientThread = DispatchQueue()
        @JvmStatic val serverThread = DispatchQueue()
    }
}

