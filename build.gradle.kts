plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.3.10"
    id("org.jetbrains.intellij") version "1.17.5"
}

group = "com.github.qiuapeng921.openaieditor"
version = "1.0.2"

repositories {
    mavenCentral()
}

intellij {
    version.set("2023.3.8")
    type.set("IC") // IntelliJ IDEA 社区版
    
    // 关键配置:防止自动更新版本范围
    updateSinceUntilBuild.set(false)
    sameSinceUntilBuild.set(false)
    
    // 移除插件配置,使用默认的平台插件
    plugins.set(listOf())
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
    
    // 在打包时排除 Kotlin 标准库，减小插件体积
    buildPlugin {
        exclude("**/kotlin-stdlib*.jar")
        exclude("**/kotlin-reflect*.jar")
        exclude("**/kotlinx-*.jar")
    }

    patchPluginXml {
        sinceBuild.set("233")
        // 不设置 untilBuild，支持所有未来版本
        
        // 插件描述信息
        pluginDescription.set("""
            <h2>Open In AIEditor - Seamlessly Switch to AI Code Editors</h2>
            
            <p>A powerful JetBrains IDE plugin that enables seamless switching to 16 mainstream AI code editors while preserving cursor position and editing context.</p>
            <p>一个强大的 JetBrains IDE 插件，让你可以无缝切换到 16 个主流 AI 代码编辑器，保持光标位置和编辑上下文。</p>
            
            <h3>✨ Core Features / 核心特性</h3>
            <ul>
                <li><strong>16 AI Code Editors Support / 支持 16 个 AI 代码编辑器</strong>: Cursor, Windsurf, Void, Trae, Qoder, Kiro, Antigravity, CatPawAI, Melty, Aide, Zed, PearAI, Void Editor, Supermaven, Aider, Continue</li>
                <li><strong>Smart Enable Control / 智能启用控制</strong>: Individual enable switch for each editor, only show what you need / 每个编辑器独立的启用开关，只显示你需要的编辑器</li>
                <li><strong>Context Menu Integration / 右键菜单集成</strong>: Right-click on files or folders to quickly open / 在文件或文件夹上右键即可快速打开</li>
                <li><strong>Status Bar Widget / 状态栏组件</strong>: Quick switch default editor / 快速切换默认编辑器</li>
                <li><strong>Cursor Position Preservation / 光标位置保持</strong>: Automatically locate to the same line and column / 自动定位到相同的行和列</li>
                <li><strong>Cross-Platform / 跨平台支持</strong>: Full support for macOS, Windows, Linux / 完整支持 macOS, Windows, Linux</li>
                <li><strong>Flexible Configuration / 灵活配置</strong>: Individual path configuration for each editor / 每个编辑器独立的路径配置</li>
                <li><strong>Internationalization / 国际化支持</strong>: Chinese and English interface / 中英文界面</li>
            </ul>
            
            <h3>🚀 How to Use / 使用方法</h3>
            <ol>
                <li>Open <code>Settings → Tools → AIEditor</code> / 打开设置</li>
                <li>Check the AI editors you want to use / 勾选要使用的 AI 编辑器</li>
                <li>Configure editor paths (macOS usually auto-detects) / 配置编辑器路径（macOS 通常自动检测）</li>
                <li>Right-click on file → <code>Open In AIEditor</code> → Select editor / 右键点击文件 → 选择编辑器</li>
            </ol>
            
            <h3>⚡ Performance Optimization / 性能优化</h3>
            <ul>
                <li>Plugin size only <strong>50 KB</strong>, extremely fast download and installation / 插件体积仅 50 KB，下载和安装极快</li>
                <li>Uses IDE-provided Kotlin standard library, avoiding version conflicts / 使用 IDE 提供的 Kotlin 标准库，避免版本冲突</li>
            </ul>
            
            <h3>🤖 Supported AI Editors / 支持的编辑器</h3>
            <p>Cursor, Windsurf, Void, Trae, Qoder, Kiro, Antigravity, CatPawAI, Melty, Aide, Zed, PearAI, Void Editor, Supermaven, Aider, Continue</p>
        """.trimIndent())
        
        // 从 CHANGELOG.md 读取变更日志
        val changelogFile = file("CHANGELOG.md")
        if (changelogFile.exists()) {
            val changelog = changelogFile.readText()
            // 提取最新版本的变更内容（从第一个 ## 到下一个 ## 或文件结束）
            val latestChanges = changelog
                .substringAfter("## [")
                .substringBefore("\n## [")
                .let { section ->
                    val version = section.substringBefore("]")
                    val content = section.substringAfter("\n").trim()
                    // 转换 Markdown 为简单 HTML
                    val htmlContent = content
                        .replace(Regex("### (.+)")) { "<h4>${it.groupValues[1]}</h4>" }
                        .replace(Regex("- (.+)")) { "<li>${it.groupValues[1]}</li>" }
                        .let { "<h3>🎉 Version $version</h3><ul>$it</ul>" }
                    htmlContent
                }
            changeNotes.set(latestChanges)
        }
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}