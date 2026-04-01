# 发布指南

## 当前状态

✅ GitHub Release 已自动创建
❌ JetBrains Marketplace 发布需要手动配置

## 为什么 publishPlugin 没有工作？

`publishPlugin` 任务需要 `PUBLISH_TOKEN` 环境变量，但目前 GitHub 仓库中没有配置这个 Secret。

## 解决方案

### 方案一：配置自动发布到 Marketplace（推荐）

1. **获取 JetBrains Marketplace Token**
   - 访问 [JetBrains Marketplace](https://plugins.jetbrains.com/)
   - 登录你的账号
   - 进入个人资料 → Tokens
   - 点击 "Generate New Token"
   - 复制生成的 Token

2. **在 GitHub 仓库中配置 Secret**
   - 打开仓库设置：https://github.com/code-gopher/OpenInAIEditor/settings/secrets/actions
   - 点击 "New repository secret"
   - Name: `PUBLISH_TOKEN`
   - Value: 粘贴你的 JetBrains Marketplace Token
   - 点击 "Add secret"

3. **更新工作流文件**
   - 我已经修改了 `.github/workflows/release.yml`
   - 添加了 `Publish to JetBrains Marketplace` 步骤
   - 需要提交这个修改：
   ```bash
   git add .github/workflows/release.yml
   git commit -m "feat: 添加自动发布到 JetBrains Marketplace 的步骤"
   git push origin master
   ```

4. **重新触发发布**
   - 配置好 Token 后，可以删除并重新创建标签来触发发布：
   ```bash
   git tag -d v1.0.2
   git push origin :refs/tags/v1.0.2
   git tag v1.0.2
   git push origin v1.0.2
   ```

### 方案二：手动发布到 Marketplace

如果不想配置自动发布，可以手动上传：

1. **下载构建好的插件**
   - 访问 [GitHub Releases](https://github.com/code-gopher/OpenInAIEditor/releases)
   - 下载最新版本的 ZIP 文件

2. **上传到 Marketplace**
   - 访问 [JetBrains Marketplace](https://plugins.jetbrains.com/)
   - 登录并进入你的插件管理页面
   - 点击 "Upload update"
   - 上传下载的 ZIP 文件
   - 填写版本说明（可以从 CHANGELOG.md 复制）
   - 提交审核

## 当前发布状态

- ✅ 代码已提交到 GitHub
- ✅ 标签 v1.0.2 已创建
- ✅ GitHub Release 已自动创建
- ✅ 插件 ZIP 文件已上传到 GitHub Release
- ⏳ 等待配置 PUBLISH_TOKEN 或手动上传到 Marketplace

## 下次发布流程

配置好 `PUBLISH_TOKEN` 后，下次发布只需要：

1. 更新版本号（build.gradle.kts）
2. 更新 CHANGELOG.md
3. 提交代码
4. 创建并推送标签

GitHub Actions 会自动：
- 构建插件
- 创建 GitHub Release
- 发布到 JetBrains Marketplace ✨
