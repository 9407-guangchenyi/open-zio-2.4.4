Mio 2.4.4 简体中文汉化
Minecraft 1.21.11 · Fabric 客户端模组的完整中文本地化

   

项目简介
这是 open-zio 的简体中文汉化版。

原项目是一个 Minecraft 1.21.11 的 Fabric 客户端模组，包含战斗、移动、渲染等 200 多个功能模块。本项目为其补齐了完整的中文界面，让中文用户可以直接使用。

汉化作者：9407-guangchenyi

汉化成果
类别	数量	说明
面板分类	8	战斗、移动、渲染、玩家、杂项、漏洞、客户端、界面
模块显示名	202	ClickGUI 中所有模块名
设置项名称	926	每个模块下的所有设置项
模块描述	176	悬停显示的说明文字
合计	1312	—
效果预览
汉化前	汉化后
Combat / Movement	战斗 / 移动
Aura	杀戮光环
AutoCrystal	自动水晶
Criticals	暴击
Sprint	强制疾跑
Velocity	反击退
Flight	魔法飞行
NoFall	没有摔落伤害
安装方法
前置要求
组件	版本
Minecraft	1.21.11
Fabric Loader	0.19.5+
Fabric API	0.141.6+1.21.11
Java	25
步骤
安装 Minecraft 1.21.11
安装 Fabric Loader
下载 Fabric API 并放入 mods
从 Releases 下载 Zio-2.4.4.jar
放入 .minecraft/mods/ 目录
启动游戏
切换中文
代码块


按 右 Shift 打开菜单
  → 客户端 面板
  → 找到 UI 模块
  → 确保 Descriptions 已开启
  → 点 Language
  → 选择 Chinese
如果中文显示为方块：进入 客户端 面板 → 开启 Fonts 模块 → 勾选 SimplifiedChinese

技术实现
汉化不是简单的字符串替换，而是在不破坏原有逻辑的前提下新增了一层显示名翻译层。

核心设计
代码块


内部名 (Sprint)  ──显示时翻译──>  强制疾跑
    ↓                                ↓
配置文件 modules.json            ClickGUI 界面
命令 /zio sprint或/mio sprint               玩家看到的名字
搜索关键词 Sprint
内部名保持不变是关键设计，因为模块名同时也是：

配置文件的键名（改了会导致存档失效）
命令的别名（/zio sprint）
搜索的匹配词
新增文件
文件	作用
render/text/DisplayNames.java	翻译层核心，内部名 → 显示名
assets/zio/lang/zh_cn.ini	1312 条翻译
翻译键结构
ini


; 面板分类
[names.categories]
Combat = 战斗

; 模块显示名
[names.modules]
Sprint = 强制疾跑

; 设置项名称
[names.settings]
Range = 距离

; 模块描述
[descriptions.modules]
Aura = "攻击附近的实体。"
修复的三个问题
1. 汉字缺字

原版字库只有 2761 个常用汉字，缺少「戮」「氛」「隧」「骼」等字，导致「杀戮光环」显示成「杀光环」。

解决：新增 CHINESE_EXTRA 字符范围，补齐 11 个缺失字形。

2. 文字截断

面板宽度固定 92px，中文比英文宽，长模块名被裁切。

解决：Panel.fitWidthToChildren() 按实际内容自动测量宽度。

3. 配置覆盖

旧配置文件中存有旧品牌名，会覆盖代码中的新默认值。

解决：WatermarkModule 加入兜底逻辑，配置值为旧名时自动替换。

使用说明
命令仍是英文
因为命令解析器只认英文，所以：

代码块


✅ /zio sprint on
❌ /zio 强制疾跑 on
搜索仍是英文
ClickGUI 搜索框匹配的是内部名：

代码块


✅ 输入 Sprint → 找到「强制疾跑」
❌ 输入 疾跑   → 找不到
未汉化的部分
部分模块的 info 后缀（如 TO:NEVER_LOCK）为代码动态生成
数值单位、枚举值（如 Vanilla、Distance）保持英文
mods 列表中的模组 ID 仍为 zio
常见问题
<details> <summary><b>中文显示成方块 ▯▯▯</b></summary>
进入 客户端 面板 → 开启 Fonts 模块 → 展开 ExtraChars → 勾选 SimplifiedChinese

系统会自动切换到支持中文的字体（如微软雅黑）。

</details> <details> <summary><b>某个模块还是英文</b></summary>
这是正常的回退机制。未翻译的模块会显示英文原名，不影响使用。 如果需要补充翻译，可以提交 Issue 或在 zh_cn.ini 中添加。

</details> <details> <summary><b>游戏崩溃</b></summary>
确认 Minecraft 版本是 1.21.11
确认安装了 Fabric API
确认 Java 版本 ≥ 21
删除其它客户端类模组（可能冲突）
把 logs/latest.log 提交到 Issue
</details> <details> <summary><b>和原版 mod 能共存吗</b></summary>
不能。本项目和原版 zio 是同一个 mod ID，只能装一个。

</details>
开发与构建
克隆
bash


git clone https://github.com/9407-guangchenyi/mio-chinese.git
cd mio-chinese
构建
bash


# 设置 Gradle 缓存目录（可选，避免污染全局）
export GRADLE_USER_HOME="$PWD/.gradle-user-home"

# 构建
./gradlew build
产物：build/libs/zio-2.4.4.jar

修改翻译
直接编辑 src/main/resources/assets/zio/lang/zh_cn.ini，然后重新构建。

格式说明：

; 开头是注释
[节名] 为下面所有键加前缀
值的引号会自动去除
\n 表示换行
<red> <yellow> 表示颜色
致谢
9407
戾气比鬼重
guangchenyi
