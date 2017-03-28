package com.scienjus.smartqqkotlin.model

import com.scienjus.smartqqkotlin.client.SmartQqClient

/**
 * 群.
 * @author ScienJus
 * @author [Liang Ding](http://88250.b3log.org)
 * @date 2015/12/18.
 */
data class Group internal constructor(
        override val client: SmartQqClient,
        override val id: Long,
        internal val code: Long,
        override val name: String?
) : MessageTarget {
    override fun message(content: String) {
        client.message(SmartQqClient.TargetType.GROUP, id, content)
    }
}
