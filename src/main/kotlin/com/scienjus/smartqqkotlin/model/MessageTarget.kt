package com.scienjus.smartqqkotlin.model

import com.scienjus.smartqqkotlin.client.SmartQqClient

/**
 * 表示可向其发送消息的接口。
 */
interface MessageTarget {
    val client: SmartQqClient
    val id: Long
    val name: String?
    fun message(content: String)
}