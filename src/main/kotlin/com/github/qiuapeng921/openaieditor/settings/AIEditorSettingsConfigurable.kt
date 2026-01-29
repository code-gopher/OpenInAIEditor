package com.github.qiuapeng921.openaieditor.settings

import com.github.qiuapeng921.openaieditor.editors.EditorRegistry
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBScrollPane
import com.intellij.util.ui.FormBuilder
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel

/**
 * AIEditor 设置界面
 */
class AIEditorSettingsConfigurable : Configurable {
    
    private var mainPanel: JPanel? = null
    private val editorCheckBoxes = mutableMapOf<String, JBCheckBox>()
    private val editorPathFields = mutableMapOf<String, TextFieldWithBrowseButton>()
    
    override fun getDisplayName(): String = "AIEditor"
    
    override fun createComponent(): JComponent {
        val settings = AIEditorSettings.getInstance()
        val formBuilder = FormBuilder.createFormBuilder()
        
        formBuilder.addComponent(JBLabel("启用的编辑器（只有启用的编辑器才会在右键菜单中显示）："))
        formBuilder.addVerticalGap(10)
        
        // 为每个编辑器创建配置项
        EditorRegistry.getAllEditorTypes().forEach { editorType ->
            val config = settings.getEditorConfig(editorType)
            
            // 创建启用复选框
            val checkBox = JBCheckBox(editorType, config.enabled)
            editorCheckBoxes[editorType] = checkBox
            
            // 创建路径选择器
            val pathField = TextFieldWithBrowseButton().apply {
                text = config.path
                addActionListener {
                    val descriptor = FileChooserDescriptor(true, false, false, false, false, false)
                        .withTitle("选择 $editorType 可执行文件")
                        .withDescription("选择 $editorType 可执行文件的路径")
                    
                    com.intellij.openapi.fileChooser.FileChooser.chooseFile(
                        descriptor,
                        null as Project?,
                        null
                    ) { file ->
                        text = file.path
                    }
                }
            }
            editorPathFields[editorType] = pathField
            
            // 添加到表单
            formBuilder.addLabeledComponent(checkBox, pathField, 1, false)
        }
        
        val panel = formBuilder.panel
        val scrollPane = JBScrollPane(panel)
        
        mainPanel = JPanel(BorderLayout()).apply {
            add(scrollPane, BorderLayout.CENTER)
        }
        
        return mainPanel!!
    }
    
    override fun isModified(): Boolean {
        val settings = AIEditorSettings.getInstance()
        
        // 检查每个编辑器的配置是否修改
        return EditorRegistry.getAllEditorTypes().any { editorType ->
            val config = settings.getEditorConfig(editorType)
            val checkBox = editorCheckBoxes[editorType]
            val pathField = editorPathFields[editorType]
            
            checkBox?.isSelected != config.enabled || pathField?.text != config.path
        }
    }
    
    override fun apply() {
        val settings = AIEditorSettings.getInstance()
        
        // 保存每个编辑器的配置
        EditorRegistry.getAllEditorTypes().forEach { editorType ->
            val checkBox = editorCheckBoxes[editorType]
            val pathField = editorPathFields[editorType]
            
            if (checkBox != null && pathField != null) {
                settings.setEditorConfig(editorType, checkBox.isSelected, pathField.text)
            }
        }
    }
    
    override fun reset() {
        val settings = AIEditorSettings.getInstance()
        
        // 重置每个编辑器的配置
        EditorRegistry.getAllEditorTypes().forEach { editorType ->
            val config = settings.getEditorConfig(editorType)
            editorCheckBoxes[editorType]?.isSelected = config.enabled
            editorPathFields[editorType]?.text = config.path
        }
    }
}