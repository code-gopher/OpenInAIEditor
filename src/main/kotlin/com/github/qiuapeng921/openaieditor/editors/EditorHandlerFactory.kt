package com.github.qiuapeng921.openaieditor.editors

import com.github.qiuapeng921.openaieditor.settings.AIEditorSettings
import com.intellij.openapi.project.Project

/**
 * 编辑器处理器工厂类
 * 使用配置注册表创建编辑器处理器，无需为每个编辑器编写单独的类
 */
class EditorHandlerFactory {
    companion object {
        /**
         * 根据编辑器类型和自定义路径创建处理器
         */
        fun getHandler(editorType: String, customPath: String, project: Project?): EditorHandler {
            val config = EditorRegistry.getConfig(editorType)
            
            // 如果找到配置，使用通用处理器
            if (config != null) {
                return GenericEditorHandler(config, customPath)
            }
            
            // 如果没有配置，返回默认处理器（Cursor）
            val defaultConfig = EditorRegistry.getConfig("Cursor")!!
            return GenericEditorHandler(defaultConfig, customPath)
        }
        
        /**
         * 从设置中获取自定义路径并创建对应的编辑器处理器
         */
        fun getHandler(editorType: String, project: Project?): EditorHandler {
            val settings = AIEditorSettings.getInstance()
            val customPath = settings.getEditorPath(editorType)
            return getHandler(editorType, customPath, project)
        }
    }
}
