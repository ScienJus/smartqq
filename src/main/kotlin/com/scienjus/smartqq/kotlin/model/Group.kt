package com.scienjus.smartqq.kotlin.model

import com.scienjus.smartqq.kotlin.client.SmartQqClient

/**
 * 群.
 * @author ScienJus
 * @author [Liang Ding](http://88250.b3log.org)
 * @date 2015/12/18.
 */
data class Group internal constructor(
        override val client: SmartQqClient,
        override val id: Long,
        internal val code: Long,
        override val name: String?
) : MessageTarget {
    override fun message(content: String) {
        client.message(SmartQqClient.TargetType.GROUP, id, content)
    }

    override fun equals(other: Any?): Boolean = if (other is Group) other.id == id else false

    override fun hashCode(): Int = id.hashCode()

    private val info: GroupInfo by lazy {
        client.getGroupInfo(code)
    }

    val ownerId: Long
        get() = info.ownerId
    val createTime: Long
        get() = info.createTime
    val announcement: String?
        get() = info.announcement
    val members: List<GroupMember>
        get() = info.members

    val owner: GroupMember
        @Throws(KotlinNullPointerException::class)
        get() = members.find { id == ownerId }!!
}
