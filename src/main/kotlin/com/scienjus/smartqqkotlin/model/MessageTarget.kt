package com.scienjus.smartqqkotlin.model

/**
 * 表示可向其发送消息的接口。
 */
interface MessageTarget {
    val id: Long
    val name: String?
    fun message(content: String)
}