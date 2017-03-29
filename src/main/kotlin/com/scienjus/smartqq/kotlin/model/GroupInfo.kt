package com.scienjus.smartqq.kotlin.model

/**
 * 群资料.
 * @author ScienJus
 * @author [Liang Ding](http://88250.b3log.org)
 * @date 2015/12/24.
 */
internal data class GroupInfo constructor(
        val id: Long,
        val name: String?,
        val ownerId: Long,
        val createTime: Long,
        val announcement: String?,
        val members: List<GroupMember>
)
