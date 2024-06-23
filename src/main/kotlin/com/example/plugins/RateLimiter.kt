package com.example.plugins

class RateLimiter {
    private val MAXIMUM_ENTRANCES = 2
    private val TOLERANCE_TIME_IN_MS = 10_000

    private val userEntrances = mutableMapOf<String, Pair<Int, Long>>()

    fun control(id: String): Boolean {
        println("Before: ${userEntrances.toString()}")

        val entrancesWithTimestamp = userEntrances.getOrDefault(id, Pair(0, 0L))
        var entrances = entrancesWithTimestamp.first
        var timestamp = entrancesWithTimestamp.second

        if (System.currentTimeMillis() > timestamp + TOLERANCE_TIME_IN_MS) {
            entrances = 0
            timestamp = System.currentTimeMillis()
        }

        if (entrances >= MAXIMUM_ENTRANCES) {
            return false
        }

        userEntrances[id] = Pair(entrances + 1, timestamp)

        println("Updated: ${userEntrances.toString()}")
        return true
    }
}