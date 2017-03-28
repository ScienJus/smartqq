package com.scienjus.smartqqkotlin.model

import com.alibaba.fastjson.JSONObject
import com.scienjus.smartqqkotlin.client.SmartQqClient

/**
 * 消息.
 * @author ScienJus
 * @author [Liang Ding](http://88250.b3log.org)
 * @date 15/12/19.
 */
data class FriendMessage internal constructor(
        override val client: SmartQqClient,
        internal val senderId: Long = 0,
        override val content: String,
        override val timestamp: Long) : Message {
    override val sender: Friend
        get() = client.friends.find { it.id == senderId }!!

    override fun reply(content: String) {
        client.message(SmartQqClient.TargetType.FRIEND, senderId, content)
    }

    override val replyableTarget: Friend
        get() = sender

    internal constructor(client: SmartQqClient, obj: JSONObject) : this(
            client,
            obj.getLongValue("from_uin"),
            obj.getJSONArray("content").drop(1).joinToString(),
            obj.getLongValue("time"))
}