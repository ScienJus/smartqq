package com.scienjus.smartqq.kotlin.model

/**
 * 讨论组资料.
 * @author ScienJus
 * @author [Liang Ding](http://88250.b3log.org)
 * @date 2015/12/24.
 */
internal data class DiscussionInfo constructor(
        val id: Long,
        val name: String?,
        val members: List<DiscussionMember>)
