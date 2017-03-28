package com.scienjus.smartqqkotlin.model

import com.scienjus.smartqqkotlin.client.SmartQqClient

/**
 * 好友.
 * @author ScienJus
 * @author [Liang Ding](http://88250.b3log.org)
 * @date 2015/12/18.
 */
data class Friend internal constructor(
        override val client: SmartQqClient,
        override val id: Long,
        internal val categoryIndex: Int,
        override val nickname: String?,
        val alias: String?,
        val status: String?,
        val clientType: Int?,
        val isVip: Boolean,
        val vipLevel: Int) : MessageTarget, User() {
    override val name: String?
        get() = nickname
    val category: FriendCategory
        get() = client.friendCategories[categoryIndex]

    override fun message(content: String) {
        client.message(SmartQqClient.TargetType.FRIEND, id, content)
    }
}