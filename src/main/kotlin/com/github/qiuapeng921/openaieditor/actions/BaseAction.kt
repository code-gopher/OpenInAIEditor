package com.github.qiuapeng921.openaieditor.actions

import com.github.qiuapeng921.openaieditor.editors.EditorHandler
import com.github.qiuapeng921.openaieditor.editors.EditorHandlerFactory
import com.github.qiuapeng921.openaieditor.settings.AIEditorSettings
import com.github.qiuapeng921.openaieditor.settings.AIEditorSettingsConfigurable
import com.github.qiuapeng921.openaieditor.utils.EditorTargetUtils
import com.github.qiuapeng921.openaieditor.utils.I18nUtils
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.vfs.VirtualFile
import java.io.File

/**
 * 基础动作类，提供通用方法
 */
abstract class BaseAction : AnAction() {
    
    override fun getActionUpdateThread() = ActionUpdateThread.BGT
    
    /**
     * 获取项目路径
     */
    protected open fun getProjectPath(project: Project): String? {
        return project.basePath
    }
    
    /**
     * 获取文件路径，子类可以覆盖此方法以提供不同的文件路径获取逻辑
     */
    protected open fun getFilePath(virtualFile: VirtualFile?): String? {
        if (virtualFile == null) {
            return null
        }
        return if (virtualFile.isDirectory) null else virtualFile.path
    }
    
    /**
     * 获取编辑器处理器
     */
    protected open fun getEditorHandler(project: Project): EditorHandler {
        val editorType = EditorTargetUtils.getTargetEditor(project)
        return EditorHandlerFactory.getHandler(editorType, project)
    }
    
    /**
     * 检查编辑器路径是否存在
     * @return 如果路径有效，则返回true；否则返回false
     */
    protected fun checkEditorPathExists(project: Project, handler: EditorHandler): Boolean {
        val settings = AIEditorSettings.getInstance()
        val targetEditor = EditorTargetUtils.getTargetEditor(project)
        val editorType = targetEditor.ifBlank { settings.selectedEditorType }
        
        // 获取编辑器配置
        val customPath = settings.getEditorPath(editorType)
        
        // macOS: 不需要检查路径，所有编辑器都自动检测
        if (SystemInfo.isMac) {
            return true
        }
        
        // Windows: Cursor 和 Antigravity 支持通过 PATH 调用，不需要检查
        if (SystemInfo.isWindows && (editorType == "Antigravity" || editorType == "Cursor")) {
            return true
        }
        
        // 其他情况: 检查用户自定义的路径是否为空
        if (customPath.isBlank()) {
            // 路径为空，提示用户配置
            val result = Messages.showYesNoDialog(
                project,
                I18nUtils.message("dialog.editorPathNotConfigured.message", editorType),
                I18nUtils.message("dialog.editorPathNotConfigured.title"),
                I18nUtils.message("dialog.editorPathNotConfigured.openSettings"),
                I18nUtils.message("dialog.editorPathNotConfigured.cancel"),
                Messages.getWarningIcon()
            )
            
            if (result == Messages.YES) {
                // 打开设置对话框
                ShowSettingsUtil.getInstance().showSettingsDialog(
                    project,
                    AIEditorSettingsConfigurable::class.java
                )
            }
            return false
        }
        return true
    }
    
    /**
     * 更新动作的可见性
     */
    override fun update(e: AnActionEvent) {
        val project = e.project
        // 只要有项目就启用该操作，不需要选择文件
        e.presentation.isEnabledAndVisible = project != null
    }
    
    /**
     * 在外部编辑器中打开
     */
    protected open fun openInExternalEditor(
        project: Project,
        handler: EditorHandler,
        file: VirtualFile?,
        lineNumber: Int? = null,
        columnNumber: Int? = null
    ) {
        val projectPath = getProjectPath(project)
        if (projectPath == null) {
            showNotification(
                project,
                I18nUtils.message("notification.error.title"),
                I18nUtils.message("notification.error.noProjectPath"),
                NotificationType.ERROR
            )
            return
        }
        
        val filePath = getFilePath(file)
        
        try {
            // 获取打开命令
            val command = handler.getOpenCommand(projectPath, filePath, lineNumber, columnNumber)
            
            // 创建命令行
            val commandLine = GeneralCommandLine()
                .withExePath(command[0])
                .withParameters(*command.drop(1).toTypedArray())
                .withWorkDirectory(File(projectPath))
            
            // 创建进程处理器并启动
            val processHandler: ProcessHandler = ProcessHandlerFactory.getInstance()
                .createColoredProcessHandler(commandLine)
            
            processHandler.startNotify()
            
            // 显示成功通知
            showNotification(
                project,
                I18nUtils.message("notification.success.title"),
                I18nUtils.message("notification.success.opening", handler.getName()),
                NotificationType.INFORMATION
            )
            
        } catch (e: Exception) {
            // 显示错误通知
            showNotification(
                project,
                I18nUtils.message("notification.error.title"),
                I18nUtils.message("notification.error.failed", handler.getName(), e.message ?: ""),
                NotificationType.ERROR
            )
        }
    }
    
    /**
     * 显示通知消息
     */
    protected fun showNotification(
        project: Project?,
        title: String,
        content: String,
        type: NotificationType
    ) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("AIEditor")
            .createNotification(title, content, type)
            .notify(project)
    }
}
