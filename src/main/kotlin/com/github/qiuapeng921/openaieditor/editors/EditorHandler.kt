package com.github.qiuapeng921.openaieditor.editors

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.SystemInfo
import java.io.File

/**
 * 编辑器处理器接口
 * 定义了打开外部编辑器的通用行为
 */
interface EditorHandler {
    /**
     * 获取编辑器名称
     */
    fun getName(): String
    
    /**
     * 获取编辑器可执行文件路径
     */
    fun getPath(): String
    
    /**
     * 获取默认路径
     */
    fun getDefaultPath(): String
    
    /**
     * 获取打开命令
     * @param projectPath 项目路径
     * @param filePath 文件路径，可能为null
     * @param lineNumber 行号，可能为null
     * @param columnNumber 列号，可能为null
     * @return 命令数组
     */
    fun getOpenCommand(
        projectPath: String,
        filePath: String?,
        lineNumber: Int?,
        columnNumber: Int?
    ): Array<String>
}

/**
 * 基础编辑器处理器抽象类
 * 提供通用的编辑器处理逻辑
 */
abstract class BaseEditorHandler(private val customPath: String?) : EditorHandler {
    
    override fun getPath(): String {
        return if (customPath.isNullOrEmpty()) getDefaultPath() else customPath
    }
    
    override fun getOpenCommand(
        projectPath: String,
        filePath: String?,
        lineNumber: Int?,
        columnNumber: Int?
    ): Array<String> {
        return when {
            filePath != null -> {
                val actualLineNumber = lineNumber ?: 1
                val actualColumnNumber = columnNumber ?: 1
                // 如果有文件路径和光标位置，则打开项目并定位到文件的具体行列
                val fileWithPosition = "$filePath:$actualLineNumber:$actualColumnNumber"
                if (SystemInfo.isWindows && getPath() == getDefaultPath()) {
                    arrayOf("cmd", "/c", getPath(), projectPath, "--goto", fileWithPosition)
                } else {
                    arrayOf(getPath(), projectPath, "--goto", fileWithPosition)
                }
            }
            else -> {
                // 只打开项目
                if (SystemInfo.isWindows && getPath() == getDefaultPath()) {
                    arrayOf("cmd", "/c", getPath(), projectPath)
                } else if (SystemInfo.isMac) {
                    arrayOf("open", "-a", getName(), projectPath)
                } else {
                    arrayOf(getPath(), projectPath)
                }
            }
        }
    }
}
