package com.scienjus.smartqqkotlin.model

import com.alibaba.fastjson.JSONObject
import com.scienjus.smartqqkotlin.client.SmartQqClient

/**
 * 群消息.
 * @author ScienJus
 * @author [Liang Ding](http://88250.b3log.org)
 * @date 15/12/19.
 */
data class GroupMessage internal constructor(
        override val client: SmartQqClient,
        internal val groupId: Long,
        internal val senderId: Long,
        override val content: String,
        override val timestamp: Long) : Message {
    override val replyableTarget: Group
        get() = group
    override val sender: GroupMember
        get() = group.members.find { it.id == senderId }!!

    val group: Group
        get() = client.groups.find { it.id == groupId }!!

    override fun reply(content: String) {
        client.message(SmartQqClient.TargetType.GROUP, groupId, content)
    }

    internal constructor(client: SmartQqClient, obj: JSONObject) : this(
            client,
            obj.getLongValue("group_code"),
            obj.getLongValue("send_uin"),
            obj.getJSONArray("content").drop(1).joinToString(),
            obj.getLongValue("time"))
}