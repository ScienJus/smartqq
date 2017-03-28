package com.scienjus.smartqqkotlin.util

import java.util.function.Consumer

/**
 * 表示事件的类。
 */

class Event<T> {
    private val handlers = arrayListOf<Consumer<T>>()
    operator fun plusAssign(handler: Consumer<T>) {
        handlers.add(handler)
    }

    operator fun minusAssign(handler: Consumer<T>) {
        handlers.remove(handler)
    }

    internal fun invoke(value: T) {
        for (handler in handlers) handler.accept(value)
    }
}