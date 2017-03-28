package com.scienjus.smartqqkotlin.model

/**
 * 表示消息的接口。
 */
interface Message {
    val content: String
    val replyableTarget: MessageTarget
    val sender: User
    val timestamp: Long
    fun reply(content: String)
}