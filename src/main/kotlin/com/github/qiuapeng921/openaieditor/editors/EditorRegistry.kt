package com.github.qiuapeng921.openaieditor.editors

import com.intellij.openapi.util.SystemInfo

/**
 * 编辑器配置
 * 定义编辑器的基本信息
 */
data class EditorConfig(
    val name: String,
    val macPath: String,
    val windowsPath: String,
    val linuxPath: String = windowsPath,
    val useSystemPath: Boolean = false  // 是否使用系统 PATH（如 Antigravity）
)

/**
 * 编辑器配置注册表
 * 集中管理所有编辑器的配置
 */
object EditorRegistry {
    
    /**
     * 所有支持的编辑器配置
     * 添加新编辑器只需在这里添加一个配置项！
     */
    val EDITORS = mapOf(
        // ========== AI 代码编辑器 ==========
        
        "Cursor" to EditorConfig(
            name = "Cursor",
            macPath = "/Applications/Cursor.app/Contents/Resources/app/bin/cursor",
            windowsPath = "Cursor",
            useSystemPath = true
        ),
        
        "Windsurf" to EditorConfig(
            name = "Windsurf",
            macPath = "/Applications/Windsurf.app/Contents/Resources/app/bin/windsurf",
            windowsPath = "Windsurf"
        ),
        
        "Void" to EditorConfig(
            name = "Void",
            macPath = "/Applications/Void.app/Contents/Resources/app/bin/void",
            windowsPath = "Void"
        ),
        
        "Trae" to EditorConfig(
            name = "Trae",
            macPath = "/Applications/Trae.app/Contents/Resources/app/bin/trae",
            windowsPath = "Trae"
        ),
        
        "Qoder" to EditorConfig(
            name = "Qoder",
            macPath = "/Applications/Qoder.app/Contents/Resources/app/bin/qoder",
            windowsPath = "Qoder",
            useSystemPath = true
        ),
        
        "Kiro" to EditorConfig(
            name = "Kiro",
            macPath = "/Applications/Kiro.app/Contents/Resources/app/bin/kiro",
            windowsPath = "Kiro"
        ),
        
        "Antigravity" to EditorConfig(
            name = "Antigravity",
            macPath = "/Applications/Antigravity.app/Contents/Resources/app/bin/antigravity",
            windowsPath = "Antigravity",
            useSystemPath = true
        ),
        
        "CatPawAI" to EditorConfig(
            name = "CatPawAI",
            macPath = "/Applications/CatPawAI.app/Contents/Resources/app/bin/catpawai",
            windowsPath = "CatPawAI"
        ),
        
        "Melty" to EditorConfig(
            name = "Melty",
            macPath = "/Applications/Melty.app/Contents/Resources/app/bin/melty",
            windowsPath = "Melty"
        ),
        
        "Aide" to EditorConfig(
            name = "Aide",
            macPath = "/Applications/Aide.app/Contents/Resources/app/bin/aide",
            windowsPath = "Aide"
        ),
        
        "Zed" to EditorConfig(
            name = "Zed",
            macPath = "/Applications/Zed.app/Contents/MacOS/cli",
            windowsPath = "zed",
            linuxPath = "zed"
        ),
        
        "PearAI" to EditorConfig(
            name = "PearAI",
            macPath = "/Applications/PearAI.app/Contents/Resources/app/bin/pearai",
            windowsPath = "PearAI"
        ),
        
        "Void Editor" to EditorConfig(
            name = "Void Editor",
            macPath = "/Applications/Void Editor.app/Contents/Resources/app/bin/void",
            windowsPath = "Void Editor"
        ),
        
        "Supermaven" to EditorConfig(
            name = "Supermaven",
            macPath = "/Applications/Supermaven.app/Contents/Resources/app/bin/supermaven",
            windowsPath = "Supermaven"
        ),
        
        "Aider" to EditorConfig(
            name = "Aider",
            macPath = "/usr/local/bin/aider",
            windowsPath = "aider",
            linuxPath = "aider"
        ),
        
        "Continue" to EditorConfig(
            name = "Continue",
            macPath = "/Applications/Continue.app/Contents/Resources/app/bin/continue",
            windowsPath = "Continue"
        )
    )
    
    /**
     * 获取编辑器配置
     */
    fun getConfig(editorType: String): EditorConfig? {
        return EDITORS[editorType]
    }
    
    /**
     * 获取所有编辑器类型列表
     */
    fun getAllEditorTypes(): List<String> {
        return EDITORS.keys.toList()
    }
}

/**
 * 通用编辑器处理器
 * 根据配置动态处理不同的编辑器
 */
class GenericEditorHandler(
    private val config: EditorConfig,
    customPath: String?
) : BaseEditorHandler(customPath) {
    
    override fun getName(): String = config.name
    
    override fun getDefaultPath(): String {
        return when {
            SystemInfo.isMac -> config.macPath
            SystemInfo.isWindows -> config.windowsPath
            else -> config.linuxPath
        }
    }
}
