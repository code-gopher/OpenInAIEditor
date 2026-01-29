package com.github.qiuapeng921.openaieditor.statusbar

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidgetFactory

/**
 * 状态栏编辑器选择器组件工厂
 */
class EditorSelectorWidgetFactory : StatusBarWidgetFactory {
    
    override fun getId(): String = EditorSelectorWidget.ID
    
    override fun getDisplayName(): String = "AI Editor Selector"
    
    override fun isAvailable(project: Project): Boolean = true
    
    override fun createWidget(project: Project): StatusBarWidget {
        return EditorSelectorWidget(project)
    }
    
    override fun disposeWidget(widget: StatusBarWidget) {
        // 清理资源
    }
    
    override fun canBeEnabledOn(statusBar: StatusBar): Boolean = true
}
