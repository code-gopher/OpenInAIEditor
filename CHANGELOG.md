# Changelog

All notable changes to this project will be documented in this file.

## [1.0.1] - 2025-12-18

### 🐛 Bug 修复
- **修复国际化占位符问题**: 使用 `MessageFormat` 正确处理 `{0}` 等占位符，现在会正确显示 "正在 Cursor 中打开..." 而不是 "正在 {0} 中打开..."
- **修复状态栏组件已弃用 API 警告**: 使用 `StatusBarWidget.TextPresentation` 替代已弃用的 `MultipleTextValuesPresentation`

### ⚡ 性能优化
- **插件体积大幅减小**: 从 1.65 MB 减小到 50 KB，减小 **97%**
- **排除 Kotlin 标准库**: 使用 IDE 提供的 Kotlin 标准库，避免版本冲突
- **加载速度提升**: 更小的体积带来更快的下载和安装速度

### 📝 技术改进
- 添加 `@Suppress("DEPRECATION")` 注解处理必要的已弃用方法
- 优化代码结构，提取 `getEnabledEditors()` 方法提高可读性
- 简化 `createPopup()` 方法实现

## [1.0.0] - 2025-12-18

### 🎉 首个正式版本
全新的 JetBrains IDE 插件，支持快速切换到外部编辑器

### ✨ 核心功能

#### 编辑器支持
- **16 个 AI 编辑器支持**: 专注于 AI 代码编辑器
  - Cursor, Windsurf, Void, Trae, Qoder, Kiro, Antigravity, CatPawAI
  - Melty, Aide, Zed, PearAI, Void Editor, Supermaven, Aider, Continue
- **智能启用控制**: 每个编辑器都有独立的启用开关，只显示你需要的编辑器

#### 使用方式
- **右键菜单**: 在文件/文件夹上右键 → "Open In AIEditor" → 选择编辑器
- **状态栏组件**: 点击状态栏图标快速切换默认编辑器

#### 智能功能
- **光标位置保持**: 在外部编辑器中自动定位到相同的行和列
- **跨平台支持**: 完整支持 macOS, Windows, Linux
- **智能路径检测**: 自动检测编辑器路径，无需手动配置（部分编辑器）
- **国际化支持**: 中英文界面

### 🏗️ 架构设计

#### 设计模式
- **配置化架构**: 使用配置注册表管理所有编辑器，添加新编辑器只需添加配置
- **Strategy 模式**: `EditorHandler` 接口统一编辑器处理逻辑
- **Factory 模式**: `EditorHandlerFactory` 动态创建编辑器处理器
- **动态菜单**: 根据配置自动生成右键菜单项

#### 代码组织
- `actions/`: 用户动作处理（BaseAction, AIEditorActionGroup, OpenInEditorAction）
- `editors/`: 编辑器处理器（EditorRegistry, EditorHandlerFactory, GenericEditorHandler）
- `settings/`: 配置管理（AIEditorSettings, AIEditorSettingsConfigurable）
- `statusbar/`: 状态栏组件（EditorSelectorWidget, EditorSelectorWidgetFactory）
- `utils/`: 工具类（EditorTargetUtils, I18nUtils）

### 🔧 技术特性
- **零代码扩展**: 添加新编辑器只需在配置文件中添加一行
- **类型安全**: 使用 Kotlin data class 确保配置完整性
- **错误处理**: 统一的通知和错误提示机制
- **状态持久化**: 使用 IntelliJ 推荐的 `PersistentStateComponent` 模式
- **完整注释**: 所有代码都有详细的中文注释

### 🎯 兼容性
- **IDE 版本**: 2023.3 及以上所有 JetBrains IDE
- **支持产品**: IntelliJ IDEA, PhpStorm, WebStorm, PyCharm, GoLand, RubyMine, CLion, Rider 等
- **系统要求**: Java 17+

