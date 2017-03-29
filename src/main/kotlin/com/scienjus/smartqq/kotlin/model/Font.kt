package com.scienjus.smartqq.kotlin.model

/**
 * 字体.
 * @author ScienJus
 * @author [Liang Ding](http://88250.b3log.org)
 * @date 15/12/19.
 */
@Suppress("ArrayInDataClass")
internal data class Font constructor(
        val style: IntArray?,
        val color: String?,
        val name: String?,
        val size: Int = 0) {
    companion object {
        val DEFAULT_FONT: Font
            get() {
                val font = Font(intArrayOf(0, 0, 0), "000000", "宋体", 10)
                return font
            }
    }
}
