package com.scienjus.smartqqkotlin.model

import com.alibaba.fastjson.JSONObject
import java.util.*

/**
 * 用户.
 * @author ScienJus
 * @author [Liang Ding](http://88250.b3log.org)
 * @date 2015/12/24.
 */
internal data class FriendInfo constructor(
        val id: Long,
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
        val vipInfo: Int = 0) {
    constructor(json: JSONObject) : this(
            json.getLongValue("uin"),
            json.getString("nick"),
            json.getString("lnick"),
            json.getString("gender"),
            json.getString("phone"),
            json.getString("mobile"),
            json.getString("email"),
            json.getString("homepage"),
            json.getJSONObject("birthday").toDate(),
            json.getString("college"),
            json.getString("occupation"),
            json.getIntValue("blood"),
            json.getString("country"),
            json.getString("province"),
            json.getString("city"),
            json.getString("personal"),
            json.getIntValue("shengxiao"),
            json.getString("account"),
            json.getIntValue("vip_info"))

    companion object {
        private fun JSONObject.toDate(): Date {
            return Date(this.getInteger("year"), this.getInteger("month"), this.getInteger("day"))
        }
    }
}
