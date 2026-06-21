# 万象大考前台前端 — Uni-app 迁移方案报告

> 日期：2026-06-14  
> 目标：用 Uni-app 替换已死的 mpvue，重写微信小程序前端

---

## 一、背景：为什么必须换

你现在的小程序前端用的是 **mpvue 1.0**（美团开源），有三个致命问题：

1. **框架已死**：美团 2019 年归档了 mpvue 项目，GitHub 仓库只读
2. **无法编译**：mpvue 绑死 Webpack 3 + Node 8/10，现代 Node 完全跑不了
3. **Bug 无人修**：长列表渲染比微信原生慢 5 倍，大量组件直接崩溃

结论：**不是"要不要换"的问题，是没有第二个选择。**

---

## 二、Uni-app 是什么

Uni-app 是 DCloud 推出的跨平台框架，目前 GitHub **41,500+ Star**，国内小程序开发**事实标准**。

### 一句话解释

> 你用 Vue 3 写一套代码，它帮你编译成微信小程序、支付宝小程序、H5 网页、iOS App、Android App。

### 和 mpvue 的本质区别

| | mpvue（2018） | Uni-app（2026） |
|---|---|---|
| 底层 Vue 版本 | Vue 2 | Vue 3 + Composition API |
| 构建工具 | Webpack 3 | Vite |
| 维护状态 | ❌ 已归档 | ✅ 活跃更新（最近提交 6 月 9 日） |
| 组件库 | 无（全手写） | uview-plus 70+ 组件 |
| TypeScript | 不支持 | 完整支持 |
| 条件编译 | 无 | 最强大的 `#ifdef` 宏 |
| 开发工具 | CLI 基础 | HBuilderX（专用 IDE）+ VS Code 插件 |
| 可发布平台 | 仅微信 | 微信/支付宝/百度/抖音/H5/App/鸿蒙 |
| 小程序包体积 | 中等 | 可控（Tree-shaking） |
| 性能（长列表） | 4493ms ⚠️ | 741ms ✅ |

### 为什么会存在两种模式

Uni-app 有两个版本：

| | **标准版（推荐你用的）** | **uni-app x（新一代）** |
|---|---|---|
| 技术栈 | Vue 3 + Vite | uts 语言 + 原生渲染 |
| 渲染方式 | WebView（JS 逻辑层 + WebView 渲染层） | 原生引擎 |
| 上手难度 | 低，就是写 Vue | 中等，类 TypeScript |
| 成熟度 | 非常高，生态丰富 | 新，仍在迭代 |
| 适合场景 | 你现在的项目 | 对性能有极端要求的场景 |

**建议选标准版（Vue 3 + Vite）**。你的旧项目就是 Vue 2，升级到 Vue 3 是最平滑的路径。

---

## 三、和旧项目的对应关系

### 技术栈一一对应

| 旧项目概念 | Uni-app 对应 | 说明 |
|-----------|-------------|------|
| `.vue` 单文件组件 | 完全一样 | `<template>` + `<script>` + `<style>` 结构不变 |
| `rpx` 响应式单位 | 完全一样 | 750rpx = 屏幕宽度，零改动 |
| `<style lang="scss" scoped>` | 完全一样 | SCSS 变量、嵌套、mixin 全支持 |
| Vuex 状态管理 | Pinia（官方推荐）/ Vuex 仍可用 | 概念相同，API 更简洁 |
| `wx.navigateTo` 页面跳转 | `uni.navigateTo` | 功能一样，名字换了 |
| `wx.request` HTTP 请求 | `uni.request` | 功能一样，名字换了 |
| `wx.connectSocket` WebSocket | `uni.connectSocket` | 功能一样，API 略不同 |
| `wx.login` 微信登录 | `uni.login` | 功能一样，自动适配平台 |
| `flyio` HTTP 客户端 | 直接用 `uni.request` 或保留 flyio | uni.request 内置，第三方库不需要了 |
| `this.$root.$mp` 页面实例 | 不需要了 | Uni-app 有自己的页面生命周期 |
| mpvue-router-patch | Uni-app 内置路由 | `uni.navigateTo` / `uni.redirectTo` |

### 页面结构对应

| 旧项目 | Uni-app | 说明 |
|--------|---------|------|
| `src/pages/entry/index.vue` 首页 | `pages/index/index.vue` | 路径规范不同，内容迁移 |
| `src/pages/game/index.vue` 对战页 | `pages/game/index.vue` | 核心 WebSocket 逻辑移植 |
| `src/roomPages/` 子包 | `subPackages/rooms/` | 分包机制有，配置方式不同 |
| `src/pages.js` 路由配置 | `pages.json` | 统一的页面配置 + 分包 + 窗口样式 |
| `src/static/` 静态资源 | `static/` | 130 个 PNG 直接复制 |
| `src/scss/` 全局样式 | `styles/` | 7 个 SCSS 文件直接复制 |
| `src/store/` Vuex 状态 | `store/` Pinia | 迁移需改写，逻辑不变 |

---

## 四、美术资源和 UI 排版迁移

### 图片资源 — 零损失

| 资源类型 | 数量 | 旧位置 | 新位置 | 注意事项 |
|---------|------|--------|--------|----------|
| 首页/入口图标 | ~15 个 | `static/*.png` | `static/*.png` | 直接复制，不改名 |
| 数字图标 | 16 个 | `static/g-num-*.png` | 同上 | 金币风格 + 白色风格两套 |
| 答题反馈图标 | 6 个 | `static/main_*.png` | 同上 | 正确/错误/未答三态 |
| 分类图标 | ~40 个 | `static/icon/*.png` | 同上 | 6 科学科文字图 + 功能图标 |
| 排行榜图标 | 20 个 | `static/rank/*.png` | 同上 | 勋章、气泡、结果标记 |
| 加载/装饰图标 | ~30 个 | `static/*.png` | 同上 | 星星、皇冠、体力、箭头 |
| CDN 远程图片 | 约 50 个 | `ggbsq.xingpanwang.com/...` | ⚠️ 需要处理 | 该 CDN 大概率已不可用 |

**重点**：CDN 远程图片（精灵图、背景、表情、分享卡）需要：
1. 如果 CDN 还活着 → 保持 HTTPS 引用
2. 如果 CDN 死了 → 从旧备份中提取，放到本地 `static/`

### SCSS 样式 — 七个文件直接搬

你当年写的 7 个 SCSS 文件，每一个都是 **纯 CSS/SCSS 语法，和框架无关**：

```
src/scss/
├── public.scss          → 移动到 styles/public.scss（全局背景、按钮）
├── layout.scss          → 移动到 styles/layout.scss（Flexbox 工具类）
├── icon.scss            → 移动到 styles/icon.scss（精灵图图标定位）
├── s-modal.scss         → 移动到 styles/s-modal.scss（弹窗框架）
├── hybird.scss          → 移动到 styles/hybird.scss（匹配/房间 UI）
├── et-img-map.scss      → 移动到 styles/et-img-map.scss（首页精灵图）
└── 3v3-img-map.scss     → 移动到 styles/3v3-img-map.scss（3v3 精灵图）
```

这些文件里的 `rpx`、`@keyframes`、`transition`、`@import`、`&` 嵌套 — 全部是 CSS 标准语法，Uni-app 完全支持。

### 布局排版 — 零改动

| 旧项目写法 | Uni-app 写法 | 兼容性 |
|-----------|-------------|--------|
| Flexbox `.flex`, `.flex-col()` | 完全一样 | ✅ |
| `rpx` 单位 750rpx 设计稿 | 完全一样 | ✅ |
| CSS `@keyframes` 动画 | 完全一样 | ✅ |
| SCSS 变量 `$--color-primary` | 完全一样 | ✅ |
| `<style lang="scss" scoped>` | 完全一样 | ✅ |
| 模态框 `position: fixed` 全屏覆盖 | 完全一样 | ✅ |

**你当年全部手写 UI，没用任何第三方 UI 库**。这个选择今天看反而是好事——搬家时不受库版本约束。

---

## 五、真正要改的：API 调用层

样式和图片不是问题。以下这些才是迁移的工作量所在：

### 工作量分级

| 难度 | 改动内容 | 涉及文件数 | 预估工时 |
|------|----------|-----------|----------|
| 🟢 低 | `wx.navigateTo` → `uni.navigateTo` | ~10 个 | 1 小时 |
| 🟢 低 | `wx.request` / flyio → `uni.request` | ~3 个 | 1 小时 |
| 🟢 低 | `wx.setStorage` → `uni.setStorage` | ~5 个 | 0.5 小时 |
| 🟡 中 | `wx.createInnerAudioContext` → `uni.createInnerAudioContext` | ~3 个 | 2 小时 |
| 🟡 中 | `wx.getUserInfo` → `uni.getUserProfile` | ~2 个 | 1 小时 |
| 🟡 中 | `wx.showToast/showModal` → `uni.showToast/showModal` | ~15 个 | 2 小时 |
| 🔴 高 | `wx.connectSocket` → `uni.connectSocket` | 2 个（game/index.vue + room pages） | 4 小时 |
| 🔴 高 | `this.$root.$mp` / `this.$mp.page` 移除 | ~5 个 | 2 小时 |
| 🔴 高 | mpvue-router-patch → Uni-app 内置路由 | 3 个子包 | 3 小时 |
| 🔴 高 | WeChat `<ad>` 组件 | 1 个（entry/index.vue） | 删掉或替换 |

**总预估**：一个熟练的 Vue 开发者大约 **2-3 天** 完成全部 API 迁移。

### WebSocket 迁移 — 这是最关键的

你的对战核心（`game/index.vue` + 房间页面）约 3000 行，高度依赖 WebSocket。核心逻辑不用改：

```
匹配等待 → 发送 type=1 MATCH → 接收匹配结果 → 答题倒计时 → 发送 type=4 ANSWER
→ 接收判分结果 → 发送 type=6 NEXT → ... → 结算动画
```

这个流程**完全不变**。要改的只是连接方式：

```javascript
// 旧：mpvue + wx.connectSocket
wx.connectSocket({ url: 'wss://ggbsq.xingpanwang.com/vientianetest/wsRank' })
wx.onSocketMessage((data) => { /* 你的业务逻辑 */ })

// 新：uni-app + uni.connectSocket
uni.connectSocket({ url: 'wss://你的新域名/vientianetest/wsRank' })
uni.onSocketMessage((data) => { /* 你的业务逻辑，一行不改 */ })
```

---

## 六、为什么不是 Taro

你做的是 Vue，Taro 也可以。但几个现实考虑：

| 维度 | Uni-app | Taro |
|------|---------|------|
| Vue 生态 | 原生 Vue，DCloud 自己的 Vue 编译器 | Vue 支持是 2021 年才加的，React 是亲儿子 |
| 组件库 | uview-plus 70+ 组件，全 Vue 生态 | NutUI 有 Vue 版，但京东系 |
| 社区规模 | 更大，中文资料多 | 大，京东系项目用得多 |
| 你旧代码的迁移 | Vue 2 → Vue 3，语法相近 | 同样要经历 Vue 2 → Vue 3 升级 |
| IDE | HBuilderX 专用 IDE，一键发布 | CLI + VS Code，偏工程化 |
| 学习曲线 | 你以前就是写 mpvue 的，门槛极低 | 需要适应 Taro 的编译体系 |

两者都能完成任务。**你现在熟悉 mpvue（Vue 2），选 Uni-app（Vue 3）是阻力最小的路径。**

---

## 七、项目初始化步骤（实操）

### 第一步：安装 HBuilderX

从 [dcloud.io/hbuilderx.html](https://www.dcloud.io/hbuilderx.html) 下载，这是 DCloud 官方的 IDE，也可以用 VS Code + uni-app 插件。

### 第二步：创建项目

```
HBuilderX → 新建项目 → uni-app → Vue 3 版本 → 项目名 dakao_v3
```

或者在 VS Code 里：

```bash
npx degit dcloudio/uni-preset-vue#vite-ts dakao_v3
cd dakao_v3
npm install
```

### 第三步：搬资源

```bash
# 复制所有图片
cp 旧项目/static/**/*.png 新项目/static/

# 复制所有 SCSS
cp 旧项目/src/scss/*.scss 新项目/src/styles/

# 复制关键配置文件
# config/dataFile.js（分享文案、表情、数字路径）
# config/flyio.js（API 地址，需要改成你的新后端地址）
```

### 第四步：搬页面（建议顺序）

1. **先搬最简单的页面**：排行榜（纯数据展示）、宝典（CRUD）——验证 Vue 模板语法兼容
2. **再搬首页**：登录 + 功能入口 + 弹窗系统
3. **再搬核心对战页**：WebSocket 对战逻辑（这一步最复杂）
4. **最后搬房间系统**：4 人混战 + 3v3 组队

### 第五步：替换 API 调用

全局搜索替换：
- `wx.` → `uni.`（大部分 API 名完全一致）
- `this.$root.$mp` → 删除（Uni-app 不需要）
- `mpvue-router-patch` → `uni.navigateTo`

### 第六步：配置 pages.json

Uni-app 用 `pages.json` 管理路由和分包，对应旧项目的 `pages.js`。

---

## 八、新增收益：以后不只是微信

用 Uni-app 重写后，你得到的不仅是一个微信小程序：

| 平台 | 改动量 | 说明 |
|------|--------|------|
| 微信小程序 | 无 | 目标平台，直接编译 |
| H5 网页版 | 极小 | 同一套代码，加一个编译目标 |
| iOS App | 小 | 云打包或者本地打包，不需要 Mac |
| Android App | 小 | 同上 |
| 支付宝/百度/抖音小程序 | 中 | 需要处理平台差异（支付、登录），条件编译搞定 |

**先只编译微信小程序上线，后续如果需要在其他平台发布，代码不改，改个编译配置就行。**

---

## 九、风险点

| 风险 | 概率 | 影响 | 应对 |
|------|------|------|------|
| WebSocket 协议不兼容 | 低 | 高 | Uni-app 支持原生 WebSocket，协议层一模一样，先写一个 demo 验证 |
| 精灵图 CDN 失效 | 高 | 中 | 提前下载 CDN 图片到本地 static |
| 微信登录 API 变更 | 中 | 中 | 需要新注册小程序 AppID，旧 AppID 可能已过期 |
| 小程序包体积超限 | 低 | 低 | 130 个 PNG 加起来不大，且 Uni-app Tree-shaking 效果好 |
| 分包路径变化 | 低 | 低 | pages.json 的分包配置和旧 pages.js 机制类似 |

---

## 十、总结

| 问题 | 答案 |
|------|------|
| Uni-app 学起来难吗？ | 不难。你就是写 Vue，和你写 mpvue 时一样。 |
| 美术资料会不会丢？ | 不会。130 个 PNG 直接复制，CDN 远程图片下载本地化即可。 |
| UI 排版要不要重写？ | 不用。`rpx`、Flexbox、`@keyframes`、SCSS 全兼容，7 个样式文件直接搬。 |
| 动画效果能保留吗？ | 能。所有 `transition`、`@keyframes`、`setTimeout` 动画链都是标准 CSS/JS。 |
| 页面交互流程要不要重新设计？ | 不用。WebSocket 协议不变，匹配→答题→结算的流程不变。 |
| 要改多少代码？ | API 调用层约 2-3 天工作量，模板和样式几乎不动。 |
| 做完可以发微信小程序吗？ | 可以。用你新注册的小程序 AppID 编译上传。 |
| 以后可以发 App 吗？ | 可以。同一套代码加编译目标，不重写。 |
