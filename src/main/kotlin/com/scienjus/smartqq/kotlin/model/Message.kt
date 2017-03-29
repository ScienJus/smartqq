package com.scienjus.smartqq.kotlin.model

import com.scienjus.smartqq.kotlin.client.SmartQqClient

/**
 * 表示消息的接口。
 */
interface Message {
    val client: SmartQqClient
    val content: String
    val replyableTarget: MessageTarget
    val sender: User
    val timestamp: Long
    fun reply(content: String)
}