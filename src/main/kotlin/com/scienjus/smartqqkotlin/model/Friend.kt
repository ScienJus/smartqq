package com.scienjus.smartqqkotlin.model

import com.scienjus.smartqqkotlin.client.SmartQqClient
import java.util.*

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

    private val info by lazy {
        client.getFriendInfo(id)
    }

    val bio: String?
        get() = info.bio

    val gender: String?
        get() = info.gender

    val phone: String?
        get() = info.phone

    val cellphone: String?
        get() = info.cellphone

    val email: String?
        get() = info.email

    val homepage: String?
        get() = info.homepage

    val birthday: Date
        get() = info.birthday

    val school: String?
        get() = info.school

    val job: String?
        get() = info.job

    val bloodType: Int
        get() = info.bloodType

    val country: String?
        get() = info.country

    val province: String?
        get() = info.province

    val city: String?
        get() = info.city

    val personal: String?
        get() = info.personal

    val shengxiao: Int
        get() = info.shengxiao

    val account: String?
        get() = info.account

    val vipInfo: Int
        get() = info.vipInfo
}