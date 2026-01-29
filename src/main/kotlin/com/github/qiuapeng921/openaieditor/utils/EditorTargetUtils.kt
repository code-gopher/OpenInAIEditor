package com.github.qiuapeng921.openaieditor.utils

import com.github.qiuapeng921.openaieditor.settings.AIEditorSettings
import com.intellij.openapi.project.Project

/**
 * 编辑器目标工具类
 * 用于获取和设置当前选中的目标编辑器
 */
object EditorTargetUtils {
    
    /**
     * 获取目标编辑器类型
     * @param project 当前项目
     * @return 编辑器类型名称
     */
    fun getTargetEditor(project: Project?): String {
        val settings = AIEditorSettings.getInstance()
        return settings.selectedEditorType
    }
    
    /**
     * 设置目标编辑器类型
     * @param project 当前项目
     * @param editorType 编辑器类型名称
     */
    fun setTargetEditor(project: Project?, editorType: String) {
        val settings = AIEditorSettings.getInstance()
        settings.selectedEditorType = editorType
    }
}
