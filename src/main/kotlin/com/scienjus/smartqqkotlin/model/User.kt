package com.scienjus.smartqqkotlin.model

/**
 * 表示用户的抽象类。
 */

abstract class User {
    abstract val id: Long
    abstract val nickname: String?
    abstract val qqNumber: Long

    override fun equals(other: Any?): Boolean = if (other is User) other.id == id else false
    override fun hashCode(): Int = id.hashCode()
}