package com.scienjus.smartqqkotlin.model

import com.alibaba.fastjson.JSONObject
import com.scienjus.smartqqkotlin.client.SmartQqClient

/**
 * 讨论组消息.
 * @author ScienJus
 * @author [Liang Ding](http://88250.b3log.org)
 * @date 15/12/19.
 */
data class DiscussionMessage internal constructor(
        val client: SmartQqClient,
        internal val discussionId: Long,
        internal val senderId: Long,
        override val content: String,
        override val timestamp: Long) : Message {

    override val replyableTarget: MessageTarget
        get() = TODO() // TODO

    override val sender: User
        get() = TODO() // TODO

    override fun reply(content: String) {
        client.message(SmartQqClient.TargetType.DISCUSSION, discussionId, content)
    }

    internal constructor(client: SmartQqClient, obj: JSONObject) : this(
            client,
            obj.getLongValue("did"),
            obj.getLongValue("send_uin"),
            obj.getJSONArray("content").drop(1).joinToString(),
            obj.getLongValue("time"))
}