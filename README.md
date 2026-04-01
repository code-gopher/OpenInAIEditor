# Open In AIEditor

[![Release](https://github.com/code-gopher/OpenInAIEditor/actions/workflows/release.yml/badge.svg)](https://github.com/code-gopher/OpenInAIEditor/actions/workflows/release.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

一个强大的 JetBrains IDE 插件，让你可以快速在 AI 代码编辑器中打开当前文件或项目。

## ✨ 特性

- 🤖 **支持 16 个 AI 代码编辑器**: Cursor, Windsurf, Void, Trae, Qoder, Kiro, Antigravity, CatPawAI, Melty, Aide, Zed, PearAI, Void Editor, Supermaven, Aider, Continue
- 🎯 **智能启用控制**: 只显示你启用的编辑器，保持菜单简洁
- 📍 **光标位置保持**: 在外部编辑器中自动定位到相同的行和列
- 📊 **状态栏组件**: 快速切换默认编辑器
- 🌍 **跨平台支持**: 完整支持 macOS, Windows, Linux
- 🔧 **灵活配置**: 每个编辑器独立的启用开关和路径配置
- 🌐 **国际化**: 支持中英文界面

## 📋 系统要求

- JetBrains IDE 2023.3 或更高版本
- Java 17 或更高版本
- 至少一个支持的 AI 编辑器

## 🔧 安装

### 从 JetBrains Marketplace 安装（推荐）

1. 打开 IDE 设置 (`Settings` / `Preferences`)
2. 选择 `Plugins` → `Marketplace`
3. 搜索 "Open In AIEditor"
4. 点击 `Install`
5. 重启 IDE

### 手动安装

1. 从 [Releases](https://github.com/code-gopher/OpenInAIEditor/releases) 下载最新的 ZIP 文件
2. 打开 IDE 设置 (`Settings` / `Preferences`)
3. 选择 `Plugins` → `⚙️` → `Install Plugin from Disk...`
4. 选择下载的 ZIP 文件
5. 重启 IDE

## ⚙️ 配置

1. 打开 `Settings` → `Tools` → `AIEditor`
2. 勾选要使用的 AI 编辑器
3. 配置编辑器的可执行文件路径（macOS 通常自动检测，Windows 部分编辑器需要配置）
4. 点击 `Apply`

**注意**: 
- macOS: 所有编辑器都会自动检测，无需配置路径
- Windows: Cursor 和 Antigravity 支持通过 PATH 调用，其他编辑器需要配置路径
- Linux: 大部分编辑器需要配置路径

## 🚀 使用方法

### 通过右键菜单
1. 在编辑器或项目视图中右键点击文件/文件夹
2. 选择 "Open In AIEditor"
3. 选择要使用的编辑器（只显示已启用的编辑器）

### 通过状态栏切换默认编辑器
1. 点击 IDE 底部状态栏的 AI 编辑器图标
2. 从下拉菜单中选择默认编辑器

## 🏗️ 架构设计

本项目采用了清晰的架构模式：

- **Strategy + Factory 模式**: 使用 `EditorHandler` 接口和 `EditorHandlerFactory` 工厂类，便于扩展新的编辑器支持
- **关注点分离**: 代码按功能分包组织
  - `actions/`: 用户动作处理
  - `editors/`: 编辑器处理器
  - `settings/`: 配置管理
  - `statusbar/`: 状态栏组件
  - `utils/`: 工具类
- **配置驱动**: 使用配置注册表管理所有编辑器，添加新编辑器只需添加配置
- **状态持久化**: 使用 IntelliJ 推荐的 `PersistentStateComponent` 模式

## 🛠️ 开发

### 构建项目

```bash
./gradlew build
```

### 运行测试

```bash
./gradlew test
```

### 在开发 IDE 中运行

```bash
./gradlew runIde
```

## 📄 许可证

MIT License
