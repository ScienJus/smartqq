package com.scienjus.smartqq.kotlin.model

import com.scienjus.smartqq.kotlin.client.SmartQqClient

/**
 * 分组.
 * @author ScienJus
 * @author [Liang Ding](http://88250.b3log.org)
 * @date 15/12/19.
 */
data class FriendCategory internal constructor(
        val client: SmartQqClient,
        val index: Int = 0,
        val name: String? = "") {

    override fun equals(other: Any?): Boolean = if (other is FriendCategory) other.index == index else false

    override fun hashCode(): Int = index.hashCode()

    var members: List<Friend> = ArrayList()
        get() = client.friends.filter { friend -> friend.categoryIndex == index }

}
