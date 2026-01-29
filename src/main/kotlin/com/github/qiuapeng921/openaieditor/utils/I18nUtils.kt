package com.github.qiuapeng921.openaieditor.utils

import java.text.MessageFormat
import java.util.*

/**
 * 国际化工具类
 * 用于获取本地化消息
 */
object I18nUtils {
    
    private const val BUNDLE_NAME = "messages.AIEditorBundle"
    
    /**
     * 获取国际化消息
     * @param key 消息键
     * @param params 参数
     * @return 本地化后的消息
     */
    fun message(key: String, vararg params: Any): String {
        return try {
            val bundle = ResourceBundle.getBundle(BUNDLE_NAME)
            val message = bundle.getString(key)
            if (params.isEmpty()) {
                message
            } else {
                // 使用 MessageFormat 来处理 {0}, {1} 等占位符
                MessageFormat.format(message, *params)
            }
        } catch (e: Exception) {
            key // 如果找不到消息，返回键本身
        }
    }
}
