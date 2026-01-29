package com.github.qiuapeng921.openaieditor.statusbar

import com.github.qiuapeng921.openaieditor.editors.EditorRegistry
import com.github.qiuapeng921.openaieditor.settings.AIEditorSettings
import com.github.qiuapeng921.openaieditor.utils.EditorTargetUtils
import com.github.qiuapeng921.openaieditor.utils.I18nUtils
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.impl.status.EditorBasedWidget
import com.intellij.util.Consumer
import java.awt.event.MouseEvent

/**
 * 状态栏编辑器选择器组件
 * 允许用户快速切换目标编辑器
 */
class EditorSelectorWidget(project: Project) : EditorBasedWidget(project), StatusBarWidget.TextPresentation {
    
    companion object {
        const val ID = "AIEditorSelector"
    }
    
    override fun ID(): String = ID
    
    override fun getPresentation(): StatusBarWidget.WidgetPresentation = this
    
    override fun getText(): String {
        val currentEditor = EditorTargetUtils.getTargetEditor(project)
        return I18nUtils.message("statusbar.text", currentEditor)
    }
    
    override fun getTooltipText(): String {
        return I18nUtils.message("statusbar.tooltip")
    }
    
    @Suppress("DEPRECATION")
    override fun getClickConsumer(): Consumer<MouseEvent> {
        return Consumer { showPopup() }
    }
    
    override fun getAlignment(): Float = 0.5f
    
    /**
     * 显示编辑器选择弹窗
     */
    private fun showPopup() {
        val popup = createPopup()
        val component = myStatusBar?.component
        if (component != null) {
            popup.showInCenterOf(component)
        } else {
            popup.showInFocusCenter()
        }
    }
    
    /**
     * 创建编辑器选择弹窗
     */
    private fun createPopup() = JBPopupFactory.getInstance().createPopupChooserBuilder(getEnabledEditors())
        .setTitle(I18nUtils.message("statusbar.popup.title"))
        .setItemChosenCallback { selectedEditor ->
            val currentEditor = EditorTargetUtils.getTargetEditor(project)
            if (selectedEditor != currentEditor) {
                EditorTargetUtils.setTargetEditor(project, selectedEditor)
                // 更新状态栏显示
                myStatusBar?.updateWidget(ID)
            }
        }
        .createPopup()
    
    /**
     * 获取已启用的编辑器列表
     */
    private fun getEnabledEditors(): List<String> {
        val settings = AIEditorSettings.getInstance()
        return EditorRegistry.getAllEditorTypes()
            .filter { editorType -> settings.isEditorEnabled(editorType) }
    }
}
