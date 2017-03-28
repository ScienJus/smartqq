package com.scienjus.smartqqkotlin.util

import java.time.Duration
import java.util.*

/**
 * 表示缓存的类。
 */

internal class Cache<E>(timeout: Duration) {
    var timeout: Duration = timeout
        get
        set(_value) {
            field = _value
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    value = null
                    isValid = false
                }
            }, timeout.toMillis())
        }
    private var value: E? = null
        get
        set(_value) {
            field = _value
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    value = null
                    isValid = false
                }
            }, timeout.toMillis())
        }
    private var isValid: Boolean = false
    private var timer: Timer = Timer()

    fun getValue(initializer: (Unit) -> E?): E? {
        if (!isValid) {
            value = initializer(Unit)
        }
        return value
    }

    fun invalidate() {
        timer = Timer()
        value = null
        isValid = false
    }
}