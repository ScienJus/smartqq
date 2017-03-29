package com.scienjus.smartqqkotlin.model

import com.scienjus.smartqqkotlin.client.SmartQqClient

/**
 * 群成员.
 * @author ScienJus
 * @author [Liang Ding](http://88250.b3log.org)
 * @date 2015/12/24.
 */

data class GroupMember internal constructor(
        override val client: SmartQqClient,
        override val id: Long,
        override val nickname: String?,
        val alias: String?,
        val gender: String?,
        val country: String?,
        val province: String?,
        val city: String?,
        val clientType: Int,
        val status: Int,
        val isVip: Boolean,
        val vipLevel: Int
) : User()