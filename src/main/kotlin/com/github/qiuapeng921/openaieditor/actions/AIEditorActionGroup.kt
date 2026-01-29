package com.github.qiuapeng921.openaieditor.actions

import com.github.qiuapeng921.openaieditor.editors.EditorRegistry
import com.github.qiuapeng921.openaieditor.settings.AIEditorSettings
import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * AIEditor 动态菜单组
 * 根据编辑器注册表和启用状态动态生成菜单项
 */
class AIEditorActionGroup : ActionGroup() {
    
    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        val settings = AIEditorSettings.getInstance()
        
        // 从注册表获取所有编辑器类型，过滤出已启用的编辑器
        return EditorRegistry.getAllEditorTypes()
            .filter { editorType -> settings.isEditorEnabled(editorType) }
            .map { editorType ->
                OpenInEditorAction(editorType).apply {
                    templatePresentation.text = editorType
                    templatePresentation.description = "在 $editorType IDE 中打开"
                }
            }
            .toTypedArray()
    }
}
