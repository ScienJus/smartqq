package com.scienjus.smartqqkotlin.model

import com.scienjus.smartqqkotlin.client.SmartQqClient

/**
 * 最近会话.
 * @author ScienJus
 * @author [Liang Ding](http://88250.b3log.org)
 * @date 2015/12/24.
 */
data class ChatHistory internal constructor(
        val client: SmartQqClient,
        val id: Long,
        internal val type: HistoryType) {
    internal enum class HistoryType {
        FRIEND,
        GROUP,
        DISCUSSION;

        companion object {
            fun fromInt(ordinal: Int): HistoryType {
                return when (ordinal) {
                    0 -> FRIEND
                    1 -> GROUP
                    2 -> DISCUSSION
                    else -> throw IndexOutOfBoundsException()
                }
            }
        }
    }

    @Suppress("Destructure")
    val target: MessageTarget
        @Throws(KotlinNullPointerException::class)
        get() = when (type) {
            HistoryType.FRIEND -> client.friends.find { friend -> friend.id == id }
            HistoryType.GROUP -> client.groups.find { group -> group.id == id }
            HistoryType.DISCUSSION -> client.discussions.find { discussion -> discussion.id == id }
        }!!
}
