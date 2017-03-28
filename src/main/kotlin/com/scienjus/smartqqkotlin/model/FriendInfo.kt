package com.scienjus.smartqqkotlin.model

import java.util.*

/**
 * 用户.
 * @author ScienJus
 * @author [Liang Ding](http://88250.b3log.org)
 * @date 2015/12/24.
 */
internal data class FriendInfo constructor(
        val id: String?,
        val nickname: String?,
        val bio: String?,
        val gender: String?,
        val phone: String?,
        val cellphone: String?,
        val email: String?,
        val homepage: String?,
        val birthday: Date,
        val school: String?,
        val job: String?,
        val bloodType: Int,
        val country: String?,
        val province: String?,
        val city: String?,
        val personal: String?,
        val shengxiao: Int,
        val account: String?,
        val vipInfo: Int = 0)
