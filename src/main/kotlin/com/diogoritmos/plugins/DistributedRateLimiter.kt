package com.diogoritmos.plugins

import redis.clients.jedis.JedisPool
import redis.clients.jedis.args.ExpiryOption

class DistributedRateLimiter {
    private val MAXIMUM_ENTRANCES = 2
    private val TOLERANCE_TIME_IN_SECONDS = 10L

    private val pool = JedisPool("127.0.0.1", 6379)

    fun control(id: String): Boolean {
        val conn = pool.resource.multi()

        val entrances = conn.incr(id)
        conn.expire(id, TOLERANCE_TIME_IN_SECONDS, ExpiryOption.NX)
        conn.exec()

        return entrances.get() <= MAXIMUM_ENTRANCES
    }
}