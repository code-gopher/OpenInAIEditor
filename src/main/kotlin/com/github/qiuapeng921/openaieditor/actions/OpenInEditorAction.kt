package com.github.qiuapeng921.openaieditor.actions

import com.github.qiuapeng921.openaieditor.editors.EditorHandlerFactory
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys

/**
 * 通用的编辑器打开动作
 * 根据 editorType 参数动态选择编辑器
 */
class OpenInEditorAction(private val editorType: String) : BaseAction() {
    
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        
        // 获取指定类型的编辑器处理器
        val handler = EditorHandlerFactory.getHandler(editorType, project)
        
        // 检查编辑器路径是否存在
        if (!checkEditorPathExists(project, handler)) {
            return
        }
        
        val selectedFile = e.getData(CommonDataKeys.VIRTUAL_FILE)
        val editor = e.getData(CommonDataKeys.EDITOR)
        
        // 获取光标位置
        val lineNumber = editor?.caretModel?.currentCaret?.logicalPosition?.line?.plus(1)
        val columnNumber = editor?.caretModel?.currentCaret?.logicalPosition?.column?.plus(1)
        
        // 在指定编辑器中打开
        openInExternalEditor(project, handler, selectedFile, lineNumber, columnNumber)
    }
}
