package com.github.qiuapeng921.openaieditor.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * 编辑器配置数据类
 */
data class EditorConfigData(
    var enabled: Boolean = false,  // 是否启用
    var path: String = ""           // 可执行文件路径
)

/**
 * AIEditor 插件设置类
 * 用于持久化存储插件配置信息
 */
@State(
    name = "AIEditorSettings",
    storages = [Storage("aieditor-settings.xml")]
)
@Service(Service.Level.APP)
class AIEditorSettings : PersistentStateComponent<AIEditorSettings> {
    
    // 编辑器配置 Map：编辑器名称 -> 配置
    var editorConfigs: MutableMap<String, EditorConfigData> = mutableMapOf()
    
    // 当前选中的默认编辑器类型
    var selectedEditorType: String = "Cursor"
    
    /**
     * 获取编辑器配置
     */
    fun getEditorConfig(editorType: String): EditorConfigData {
        return editorConfigs.getOrPut(editorType) { EditorConfigData() }
    }
    
    /**
     * 设置编辑器配置
     */
    fun setEditorConfig(editorType: String, enabled: Boolean, path: String) {
        editorConfigs[editorType] = EditorConfigData(enabled, path)
    }
    
    /**
     * 检查编辑器是否启用
     */
    fun isEditorEnabled(editorType: String): Boolean {
        return getEditorConfig(editorType).enabled
    }
    
    /**
     * 获取编辑器路径
     */
    fun getEditorPath(editorType: String): String {
        return getEditorConfig(editorType).path
    }
    
    override fun getState(): AIEditorSettings = this
    
    override fun loadState(state: AIEditorSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }
    
    companion object {
        /**
         * 获取设置实例
         */
        fun getInstance(): AIEditorSettings {
            return ApplicationManager.getApplication().getService(AIEditorSettings::class.java)
        }
    }
}
