package com.scienjus.smartqqkotlin.model

import com.scienjus.smartqqkotlin.client.SmartQqClient

/**
 * 表示用户的抽象类。
 */

abstract class User {
    abstract val client: SmartQqClient
    abstract val id: Long
    abstract val nickname: String?
    val qqNumber: Long
        get() = client.getQqNumber(id)

    override fun equals(other: Any?): Boolean = if (other is User) other.id == id else false
    override fun hashCode(): Int = id.hashCode()
}