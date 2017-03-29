package com.scienjus.smartqq.kotlin.model

import com.alibaba.fastjson.JSONObject
import com.scienjus.smartqq.kotlin.client.SmartQqClient

/**
 * 讨论组消息.
 * @author ScienJus
 * @author [Liang Ding](http://88250.b3log.org)
 * @date 15/12/19.
 */
data class DiscussionMessage internal constructor(
        override val client: SmartQqClient,
        internal val discussionId: Long,
        internal val senderId: Long,
        override val content: String,
        override val timestamp: Long) : Message {

    override val replyableTarget: MessageTarget
        get() = discussion

    override val sender: DiscussionMember
        get() = discussion.members.find { it.id == senderId }!!

    val discussion: Discussion
        get() = client.discussions.find { it.id == discussionId }!!

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