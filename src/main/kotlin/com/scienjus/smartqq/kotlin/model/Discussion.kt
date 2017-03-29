package com.scienjus.smartqq.kotlin.model

import com.scienjus.smartqq.kotlin.client.SmartQqClient

/**
 * 讨论组.
 * @author ScienJus
 * @author [Liang Ding](http://88250.b3log.org)
 * @date 2015/12/23.
 */
data class Discussion internal constructor(
        override val client: SmartQqClient,
        override val id: Long,
        override val name: String?
) : MessageTarget {
    override fun message(content: String) {
        client.message(SmartQqClient.TargetType.DISCUSSION, id, content)
    }

    override fun equals(other: Any?): Boolean = if (other is Discussion) other.id == id else false

    override fun hashCode(): Int = id.hashCode()

    private val info by lazy {
        client.getDiscussInfo(id)
    }

    val members: List<DiscussionMember>
        get() = info.members
}
