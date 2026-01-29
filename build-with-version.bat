@echo off
REM ========================================
REM 自动版本递增构建脚本
REM ========================================

echo.
echo ========================================
echo Open in External IDE - 版本构建工具
echo ========================================
echo.

REM 设置 Java 环境
set JAVA_HOME=D:\app\Java\corretto-17.0.15
set PATH=%JAVA_HOME%\bin;%PATH%

REM 显示当前版本
echo 正在读取当前版本...
findstr /C:"version = " build.gradle.kts
echo.

REM 询问版本类型
echo 请选择版本递增类型:
echo 1. PATCH (1.1.0 -^> 1.1.1) - 错误修复
echo 2. MINOR (1.1.0 -^> 1.2.0) - 新功能
echo 3. MAJOR (1.1.0 -^> 2.0.0) - 重大变更
echo 4. 自定义版本号
echo 5. 不修改版本，直接构建
echo.

set /p choice="请输入选择 (1-5): "

if "%choice%"=="5" goto build

REM 这里可以添加自动版本递增逻辑
REM 目前需要手动在 build.gradle.kts 中更新版本号

echo.
echo 提示: 请手动更新以下文件中的版本号:
echo   1. build.gradle.kts - version = "x.x.x"
echo   2. plugin.xml - change-notes 中添加新版本说明
echo.
pause

:build
echo.
echo ========================================
echo 开始构建插件...
echo ========================================
echo.

REM 清理并构建
call gradlew.bat clean build

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo 构建成功！
    echo ========================================
    echo.
    echo 插件文件位置: build\distributions\
    dir /B build\distributions\*.zip
    echo.
    echo 下一步:
    echo 1. 测试插件功能
    echo 2. 更新文档
    echo 3. 提交代码
    echo 4. 创建 Git 标签
    echo.
) else (
    echo.
    echo ========================================
    echo 构建失败！请检查错误信息。
    echo ========================================
    echo.
)

pause
