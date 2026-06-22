# API 接口清单

> 来源：扫描全部 51 个 Controller 文件 | 生成：2026-06-22

---

# 第一部分：前台接口（用户端 API）

**项目**：vientianetestappendapi | **端口**：8008 | **Controller 数**：16 | **接口数**：57
**通用前缀**：`/vientianetest/mobile/`

> 所有 POST 接口（除 login）均需登录态，通过 `@RequiredPermission(PermissionConstants.TAGSIGNOUT)` 校验。
> 入参第一项统一为 `HttpServletRequest request`（用于从 Cookie 提取 Session）。
> 返回均为 JSON 字符串。

---

## 1. 登录 & 授权

| 方法 | URL | 功能 | 入参 |
|------|-----|------|------|
| GET | `/vientianetest/mobile/login` | 微信小程序登录。DEV 模式下 code 以 `dev_` 开头则绕过微信直接登录 | `String code` |
| POST | `/vientianetest/mobile/authorize` | 保存用户微信授权信息（昵称/头像/性别/地区） | `nickName, avatarUrl, gender, city, province, country` |

## 2. 初始化 & 系统

| 方法 | URL | 功能 | 入参 |
|------|-----|------|------|
| POST | `/vientianetest/mobile/init` | 初始化/恢复用户会话，返回所有初始数据 | `int userId, int isNew` |
| POST | `/vientianetest/mobile/system` | 记录用户设备/系统信息 | `brand, model, pixelRatio, screenWidth, screenHeight, windowWidth, windowHeight, statusBarHeight, language, version, system, platform, fontSizeSetting, SDKVersion` |
| POST | `/vientianetest/mobile/wsLogin` | WebSocket 连接前注册登录 | `int type` |

## 3. 用户数据

| 方法 | URL | 功能 | 入参 |
|------|-----|------|------|
| POST | `/vientianetest/mobile/user/getUser` | 获取指定用户公开信息 | `int userId, int type` |
| POST | `/vientianetest/mobile/user/getStrength` | 获取当前用户体力值 | — |
| POST | `/vientianetest/mobile/user/getRealTimeData` | 获取实时数据（在线人数等） | — |
| POST | `/vientianetest/mobile/user/writeLog` | 写入客户端调试日志 | `String content` |
| POST | `/vientianetest/mobile/user/randomPrize` | 随机抽奖 | `int type` |
| POST | `/vientianetest/mobile/user/testUserRandomPrize` | 测试用抽奖 | — |

## 4. 排位榜

| 方法 | URL | 功能 | 入参 |
|------|-----|------|------|
| POST | `/vientianetest/mobile/rank/beforeMatch` | 赛前准备数据 | — |
| POST | `/vientianetest/mobile/rank/getRankList` | 排行榜。type=1段位/2战力/3连胜/4高分/5好友/6日战力/7群好友/8群世界 | `int type` |
| POST | `/vientianetest/mobile/rank/receiveGood` | 领取段位奖励 | — |

## 5. 房间对战

| 方法 | URL | 功能 | 入参 |
|------|-----|------|------|
| POST | `/vientianetest/mobile/room/getHeadData` | 获取房间头部数据 | — |
| POST | `/vientianetest/mobile/room/rankHallData` | 获取排位大厅数据 | — |

## 6. 题库（题目）

| 方法 | URL | 功能 | 入参 |
|------|-----|------|------|
| POST | `/vientianetest/mobile/subject/getSubjectNum` | 获取题库总数 | — |
| POST | `/vientianetest/mobile/subject/getSubjectById` | 按 ID 取题目 | `int id` |
| POST | `/vientianetest/mobile/subject/getSubjectList` | 分页取题目列表 | `int pageIndex, int pageSize` |
| POST | `/vientianetest/mobile/subject/getSubjectListByStatus` | 按状态筛选题目 | `int status, int pageIndex, int pageSize` |
| POST | `/vientianetest/mobile/subject/updateSubject` | 提交/更新题目（id<=0新增，id>0修改） | `int id, int parentType, int answer, String title, String option, String description` |
| POST | `/vientianetest/mobile/subject/receiveGood` | 领取题目奖励 | `int subjectId` |
| POST | `/vientianetest/mobile/subject/updateLikeStatus` | 批量更新点赞/踩 | `String likeList` |

## 7. 每日闯关

| 方法 | URL | 功能 | 入参 |
|------|-----|------|------|
| POST | `/vientianetest/mobile/dayChallenge/getRankList` | 闯关排行榜 | — |
| POST | `/vientianetest/mobile/dayChallenge/getSubject` | 获取闯关题目 | — |
| POST | `/vientianetest/mobile/dayChallenge/submitSubject` | 提交闯关答案 | `int subjectId, int answer` |
| POST | `/vientianetest/mobile/dayChallenge/updateGoldCoin` | 更新金币 | `double number` |

## 8. 每周挑战

| 方法 | URL | 功能 | 入参 |
|------|-----|------|------|
| POST | `/vientianetest/mobile/weekChallenge/getWeekTitleList` | 本周挑战主题列表 | — |
| POST | `/vientianetest/mobile/weekChallenge/getSubject` | 获取挑战题目 | `int titleId` |
| POST | `/vientianetest/mobile/weekChallenge/submitSubject` | 提交挑战答案 | `int subjectId, int answer, int cardNumber` |

## 9. 每日福利（签到/撸猫/抽奖）

| 方法 | URL | 功能 | 入参 |
|------|-----|------|------|
| POST | `/vientianetest/mobile/dayWelfare/getDayWelfareList` | 每日福利数据（type=1签到+撸猫+抽奖，type=3抽奖号码） | `int type` |
| POST | `/vientianetest/mobile/dayWelfare/sign` | 每日签到 | `int type, int day` |
| POST | `/vientianetest/mobile/dayWelfare/hitCat` | 撸猫 | `int money` |
| POST | `/vientianetest/mobile/dayWelfare/numberReceive` | 领取抽奖号码奖励 | `int number` |

## 10. 每日任务

| 方法 | URL | 功能 | 入参 |
|------|-----|------|------|
| POST | `/vientianetest/mobile/dayTask/getTaskList` | 今日任务列表 | — |
| POST | `/vientianetest/mobile/dayTask/receiveGood` | 领取任务奖励 | — |

## 11. 好友

| 方法 | URL | 功能 | 入参 |
|------|-----|------|------|
| POST | `/vientianetest/mobile/friend/getFriendList` | 好友列表 | — |
| POST | `/vientianetest/mobile/friend/receiveFriendGood` | 接收好友赠礼 | `int userId, int goodType, int goodNum` |

## 12. 装备

| 方法 | URL | 功能 | 入参 |
|------|-----|------|------|
| POST | `/vientianetest/mobile/equipment/getEquipmentList` | 道具背包 | — |
| POST | `/vientianetest/mobile/equipment/useEquipment` | 使用道具 | `int count, int type` |

## 13. 邮件/站内信

| 方法 | URL | 功能 | 入参 |
|------|-----|------|------|
| POST | `/vientianetest/mobile/email/getEmailList` | 邮件列表 | — |
| POST | `/vientianetest/mobile/email/updateStatus` | 更新邮件状态（type=1已读，type=2领取附件） | `int emailId, int type` |

## 14. 图鉴

| 方法 | URL | 功能 | 入参 |
|------|-----|------|------|
| POST | `/vientianetest/mobile/book/getUserBookList` | 用户图鉴列表 | — |
| POST | `/vientianetest/mobile/book/upLevelByType` | 升级图鉴 | `int type` |
| POST | `/vientianetest/mobile/book/getBookUpLevelCount` | 可升级次数 | — |

## 15. 专项练习

| 方法 | URL | 功能 | 入参 |
|------|-----|------|------|
| POST | `/vientianetest/mobile/specialSubject/getSubject` | 获取专项练习题目 | — |
| POST | `/vientianetest/mobile/specialSubject/submitSubject` | 提交练习答案 | `int subjectId, String answer` |
| POST | `/vientianetest/mobile/specialSubject/promptSubject` | 获取题目提示 | `int subjectId` |

## 16. 反馈 & 留言板 & 模板消息 & 跨小程序

| 方法 | URL | 功能 | 入参 |
|------|-----|------|------|
| POST | `/vientianetest/mobile/feedBackSubject` | 题目纠错反馈 | `int subjectId, String types, String remark` |
| POST | `/vientianetest/mobile/feedBackUser` | 举报用户 | `int userId, String types` |
| POST | `/vientianetest/mobile/getMessageBoardList` | 留言板列表 | `int pageIndex, int pageSize` |
| POST | `/vientianetest/mobile/addMessageBoard` | 发留言 | `String message` |
| POST | `/vientianetest/mobile/getSeasonParagraphInfo` | 当前赛季段位信息 | — |
| POST | `/vientianetest/mobile/template/updateFormId` | 保存微信模板消息 formId | `String formId` |
| POST | `/vientianetest/mobile/user/userToXcx` | 跳转其他小程序 | `String name, int type, int status, int goodType, int goodNum` |
| POST | `/vientianetest/mobile/user/userFromXcx` | 记录来源小程序 | `String from` |

## 17. 公众号（唯一 GET 接口，无需登录）

| 方法 | URL | 功能 |
|------|-----|------|
| GET | `/vientianetest/web/versionLowTips` | 微信版本过低提示页（从公众号跳转小程序失败时的兜底页） |

---

## 前台接口速查：按 Controller 分组

| Controller | 基础路径 | 接口数 |
|---|---|---|
| MobileApiController | `/vientianetest/mobile` | 10 |
| UserApiController | `/vientianetest/mobile/user` | 8 |
| SubjectApiController | `/vientianetest/mobile/subject` | 7 |
| DayChallengeApiController | `/vientianetest/mobile/dayChallenge` | 4 |
| DayWelfareController | `/vientianetest/mobile/dayWelfare` | 4 |
| WeekChallengeController | `/vientianetest/mobile/weekChallenge` | 3 |
| SpecialSubjectController | `/vientianetest/mobile/specialSubject` | 3 |
| RankApiController | `/vientianetest/mobile/rank` | 3 |
| BookApiController | `/vientianetest/mobile/book` | 3 |
| DayTaskController | `/vientianetest/mobile/dayTask` | 2 |
| EmailController | `/vientianetest/mobile/email` | 2 |
| EquipmentController | `/vientianetest/mobile/equipment` | 2 |
| FriendController | `/vientianetest/mobile/friend` | 2 |
| RoomApiController | `/vientianetest/mobile/room` | 2 |
| TemplateController | `/vientianetest/mobile/template` | 1 |
| WebApiController | `/vientianetest/web` | 1 |
| **合计** | | **57** |

---

# 第二部分：后台接口（管理端 API）

**项目**：renren-fast（人人开源） | **Controller 数**：34 | **接口数**：177

---

## A. 系统管理（7 个 Controller，31 个接口）

### A1. 登录

| 方法 | URL | 功能 |
|------|-----|------|
| GET | `/captcha.jpg` | 获取验证码图片 |
| POST | `/sys/login` | 后台登录 |
| POST | `/sys/logout` | 退出登录 |

### A2. 用户管理 (`/sys/user`)

| 方法 | URL | 功能 |
|------|-----|------|
| GET | `/sys/user/list` | 用户列表（分页） |
| GET | `/sys/user/info` | 当前用户信息 |
| GET | `/sys/user/info/{userId}` | 指定用户+角色 |
| POST | `/sys/user/save` | 新增用户 |
| POST | `/sys/user/update` | 修改用户 |
| POST | `/sys/user/delete` | 批量删除 |
| POST | `/sys/user/password` | 修改密码 |

### A3. 角色管理 (`/sys/role`)

| 方法 | URL | 功能 |
|------|-----|------|
| GET | `/sys/role/list` | 角色列表 |
| GET | `/sys/role/select` | 角色下拉 |
| GET | `/sys/role/info/{roleId}` | 角色详情 |
| POST | `/sys/role/save` | 新增 |
| POST | `/sys/role/update` | 修改 |
| POST | `/sys/role/delete` | 批量删除 |

### A4. 菜单管理 (`/sys/menu`)

| 方法 | URL | 功能 |
|------|-----|------|
| GET | `/sys/menu/nav` | 当前用户导航菜单 |
| GET | `/sys/menu/list` | 全部菜单 |
| GET | `/sys/menu/select` | 菜单下拉树 |
| GET | `/sys/menu/info/{menuId}` | 菜单详情 |
| POST | `/sys/menu/save` | 新增 |
| POST | `/sys/menu/update` | 修改 |
| POST | `/sys/menu/delete/{menuId}` | 删除 |

### A5. 配置管理 (`/sys/config`)

| 方法 | URL | 功能 |
|------|-----|------|
| GET | `/sys/config/list` | 配置列表 |
| GET | `/sys/config/info/{id}` | 配置详情 |
| POST | `/sys/config/save` | 新增 |
| POST | `/sys/config/update` | 修改 |
| POST | `/sys/config/delete` | 批量删除 |

### A6. 日志 (`/sys/log`)

| 方法 | URL | 功能 |
|------|-----|------|
| GET | `/sys/log/list` | 操作日志列表 |

### A7. 定时任务 (`/sys/schedule`)

| 方法 | URL | 功能 |
|------|-----|------|
| GET | `/sys/schedule/list` | 任务列表 |
| GET | `/sys/schedule/info/{jobId}` | 任务详情 |
| POST | `/sys/schedule/save` | 新增 |
| POST | `/sys/schedule/update` | 修改 |
| POST | `/sys/schedule/delete` | 批量删除 |
| POST | `/sys/schedule/run` | 立即执行 |
| POST | `/sys/schedule/pause` | 暂停 |
| POST | `/sys/schedule/resume` | 恢复 |

### A8. 定时任务日志 (`/sys/scheduleLog`)

| 方法 | URL | 功能 |
|------|-----|------|
| GET | `/sys/scheduleLog/list` | 执行日志列表 |
| GET | `/sys/scheduleLog/info/{logId}` | 日志详情 |

---

## B. 万象大考运营管理（18 个 Controller，93 个接口）

前缀统一：`/smallVientianeTest/`

### B1. 题库管理 (`/smallVientianeTest/subject`) — 14 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| GET | `/smallVientianeTest/subject/getPageList` | 题目列表（7 重筛选：类型/大类/状态/模式/难度） |
| POST | `/smallVientianeTest/subject/add` | 新增题目 |
| POST | `/smallVientianeTest/subject/updateSubjectById` | 修改题目 |
| POST | `/smallVientianeTest/subject/updateSubjectTypeById` | 批量修改题目类型/难度 |
| GET | `/smallVientianeTest/subject/getSubjectById` | 题目详情 |
| GET | `/smallVientianeTest/subject/getSubjectBySortId` | 按排序ID取题 |
| GET | `/smallVientianeTest/subject/delete` | 批量删除 |
| GET | `/smallVientianeTest/subject/updateStatus` | 批量改状态 |
| GET | `/smallVientianeTest/subject/saveSort` | 保存排序 |
| POST | `/smallVientianeTest/subject/uploadExcel` | Excel 导入 |
| POST | `/smallVientianeTest/subject/uploadImage` | 上传题目图片 |
| POST | `/smallVientianeTest/subject/uploadAudio` | 上传题目音频 |
| GET | `/smallVientianeTest/subject/getModeTypeNumList` | 各模式题目数量统计 |
| GET | `/smallVientianeTest/subject/batchUpdateStatus` | 按模式和数量批量改状态 |

### B2. 题目分类 (`/smallVientianeTest/subjectType`) — 6 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| POST | `/smallVientianeTest/subjectType/add` | 新增分类 |
| GET | `/smallVientianeTest/subjectType/delete` | 删除分类 |
| POST | `/smallVientianeTest/subjectType/update` | 修改分类 |
| GET | `/smallVientianeTest/subjectType/getSubjectTypeById` | 分类详情 |
| GET | `/smallVientianeTest/subjectType/getPageList` | 分类列表 |
| GET | `/smallVientianeTest/subjectType/getSubjectTypeByTypeId` | 按父类型查子分类 |

### B3. 题目难度统计

| 方法 | URL | 功能 |
|------|-----|------|
| GET | `/smallVientianeTest/subjectLevelTypeRatio/get` | 题目难度配比 |

### B4. 用户出题审核 (`/smallVientianeTest/userSubject`) — 5 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| GET | `/smallVientianeTest/userSubject/getPageList` | 待审题目列表 |
| GET | `/smallVientianeTest/userSubject/getSubjectById` | 题目详情 |
| GET | `/smallVientianeTest/userSubject/getNextSubjectById` | 下一题 |
| GET | `/smallVientianeTest/userSubject/getPreSubjectById` | 上一题 |
| POST | `/smallVientianeTest/userSubject/updateRejectRemark` | 批量驳回/备注 |

### B5. 每周挑战题库 (`/smallVientianeTest/weekSubject`) — 11 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| GET | `/smallVientianeTest/weekSubject/getPageList` | 题目列表 |
| POST | `/smallVientianeTest/weekSubject/add` | 新增（含 titleId） |
| POST | `/smallVientianeTest/weekSubject/update` | 修改 |
| GET | `/smallVientianeTest/weekSubject/getSubjectById` | 详情 |
| GET | `/smallVientianeTest/weekSubject/delete` | 批量删除 |
| GET | `/smallVientianeTest/weekSubject/delete2` | 批量删除（备选） |
| GET | `/smallVientianeTest/weekSubject/updateStatus` | 批量改状态 |
| GET | `/smallVientianeTest/weekSubject/saveSort` | 保存排序 |
| POST | `/smallVientianeTest/weekSubject/uploadExcel` | Excel 导入 |
| POST | `/smallVientianeTest/weekSubject/uploadImage` | 上传图片 |
| POST | `/smallVientianeTest/weekSubject/uploadAudio` | 上传音频 |

### B6. 每周主题 (`/smallVientianeTest/weekTitle`) — 6 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| POST | `/smallVientianeTest/weekTitle/add` | 新增主题 |
| POST | `/smallVientianeTest/weekTitle/update` | 修改 |
| GET | `/smallVientianeTest/weekTitle/delete` | 删除 |
| GET | `/smallVientianeTest/weekTitle/getWeekTitleById` | 详情 |
| GET | `/smallVientianeTest/weekTitle/getPageList` | 列表 |
| POST | `/smallVientianeTest/weekTitle/uploadImage` | 上传主题图 |

### B7. 机器人管理 (`/smallVientianeTest/robot`) — 7 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| POST | `/smallVientianeTest/robot/add` | 新增机器人（16 字段：昵称/头像/正确率/耗时/段位等） |
| POST | `/smallVientianeTest/robot/update` | 修改 |
| GET | `/smallVientianeTest/robot/delete` | 删除 |
| GET | `/smallVientianeTest/robot/getRobotById` | 详情 |
| GET | `/smallVientianeTest/robot/getPageList` | 列表（按类型筛选） |
| POST | `/smallVientianeTest/robot/uploadImage` | 上传头像 |

### B8. 段位配置 (`/smallVientianeTest/paragraph`) — 6 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| POST | `/smallVientianeTest/paragraph/add` | 新增段位 |
| POST | `/smallVientianeTest/paragraph/update` | 修改 |
| GET | `/smallVientianeTest/paragraph/delete` | 删除 |
| GET | `/smallVientianeTest/paragraph/getParagraphById` | 详情 |
| GET | `/smallVientianeTest/paragraph/getPageList` | 列表 |
| POST | `/smallVientianeTest/paragraph/uploadImage` | 上传段位图 |

### B9. 道具 (`/smallVientianeTest/goods`) — 6 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| POST | `/smallVientianeTest/goods/add` | 新增 |
| POST | `/smallVientianeTest/goods/update` | 修改 |
| GET | `/smallVientianeTest/goods/delete` | 删除 |
| GET | `/smallVientianeTest/goods/getGoodsById` | 详情 |
| GET | `/smallVientianeTest/goods/getPageList` | 列表 |
| POST | `/smallVientianeTest/goods/uploadImage` | 上传图片 |

### B10. 抽奖号码配置 (`/smallVientianeTest/luckNumber`) — 5 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| POST | `/smallVientianeTest/luckNumber/add` | 新增号码奖品 |
| POST | `/smallVientianeTest/luckNumber/update` | 修改 |
| GET | `/smallVientianeTest/luckNumber/delete` | 删除 |
| GET | `/smallVientianeTest/luckNumber/getLuckNumberById` | 详情 |
| GET | `/smallVientianeTest/luckNumber/getPageList` | 列表 |

### B11. 每日签到配置 (`/smallVientianeTest/daySign`) — 5 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| POST | `/smallVientianeTest/daySign/add` | 新增签到奖励 |
| POST | `/smallVientianeTest/daySign/update` | 修改 |
| GET | `/smallVientianeTest/daySign/delete` | 删除 |
| GET | `/smallVientianeTest/daySign/getDaySignById` | 详情 |
| GET | `/smallVientianeTest/daySign/getPageList` | 列表 |

### B12. 邮件模板 (`/smallVientianeTest/email`) — 5 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| POST | `/smallVientianeTest/email/add` | 新增 |
| POST | `/smallVientianeTest/email/update` | 修改 |
| GET | `/smallVientianeTest/email/delete` | 批量删除 |
| GET | `/smallVientianeTest/email/getEmailById` | 详情 |
| GET | `/smallVientianeTest/email/getPageList` | 列表 |

### B13. 奖品概率 (`/smallVientianeTest/prize`) — 6 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| POST | `/smallVientianeTest/prize/add` | 新增概率配置 |
| POST | `/smallVientianeTest/prize/update` | 修改 |
| GET | `/smallVientianeTest/prize/delete` | 删除 |
| GET | `/smallVientianeTest/prize/getPrizeById` | 详情 |
| GET | `/smallVientianeTest/prize/getPageList` | 列表 |
| POST | `/smallVientianeTest/prize/uploadImage` | 上传图片 |

### B14. 图鉴配置 (`/smallVientianeTest/book`) — 6 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| POST | `/smallVientianeTest/book/add` | 新增图鉴 |
| POST | `/smallVientianeTest/book/update` | 修改 |
| GET | `/smallVientianeTest/book/delete` | 删除 |
| GET | `/smallVientianeTest/book/getBookById` | 详情 |
| GET | `/smallVientianeTest/book/getPageList` | 列表 |
| POST | `/smallVientianeTest/book/uploadImage` | 上传图片 |

### B15. 数据重置时间 (`/smallVientianeTest/clearDataTime`) — 5 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| POST | `/smallVientianeTest/clearDataTime/add` | 新增 |
| POST | `/smallVientianeTest/clearDataTime/update` | 修改 |
| GET | `/smallVientianeTest/clearDataTime/delete` | 批量删除 |
| GET | `/smallVientianeTest/clearDataTime/getClearDataTimeById` | 详情 |
| GET | `/smallVientianeTest/clearDataTime/getPageList` | 列表 |

### B16. 系统配置 (`/smallVientianeTest/dataConfig`) — 5 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| POST | `/smallVientianeTest/dataConfig/add` | 新增键值配置 |
| POST | `/smallVientianeTest/dataConfig/update` | 修改 |
| GET | `/smallVientianeTest/dataConfig/delete` | 批量删除 |
| GET | `/smallVientianeTest/dataConfig/getDataConfigById` | 详情 |
| GET | `/smallVientianeTest/dataConfig/getPageList` | 列表 |

### B17. 题目反馈管理 (`/smallVientianeTest/feedBackSubject`) — 2 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| GET | `/smallVientianeTest/feedBackSubject/updateStatus` | 批量处理 |
| GET | `/smallVientianeTest/feedBackSubject/getPageList` | 列表 |

### B18. 用户举报管理 (`/smallVientianeTest/feedBackUser`) — 1 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| GET | `/smallVientianeTest/feedBackUser/getPageList` | 列表 |

---

## C. 看图猜词（ktcc，5 个 Controller，22 个接口）

前缀：`/smallLookGuess/`

| Controller | 核心功能 | 接口数 |
|---|---|---|
| KTCCWebController | 题目 CRUD + Excel 导入 + 统计 | 10 |
| CardNumberUseController | 卡片使用统计 | 1 |
| DataConfigController | 系统配置 CRUD | 5 |
| TestUserController | 测试用户管理 | 4 |
| RechargeRecordController | 充值记录 | 1 |
| **合计** | | **22** |

---

## D. 其他小程序（2 个 Controller，14 个接口）

### 朋友关系 (`/friendrelationship`) — 7 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| GET | `/friendrelationship/getContentList` | 内容列表 |
| POST | `/friendrelationship/addContent` | 新增 |
| POST | `/friendrelationship/updateContent` | 修改 |
| GET | `/friendrelationship/deleteContent` | 删除 |
| GET | `/friendrelationship/updateContentStatus` | 批量改状态 |
| POST | `/friendrelationship/uploadImage` | 上传图片 |
| GET | `/friendrelationship/getContentById` | 详情 |

### 神奇的测试 (`/smallMagictest`) — 6 个接口

| 方法 | URL | 功能 |
|------|-----|------|
| GET | `/smallMagictest/titleList` | 测试列表 |
| POST | `/smallMagictest/createTitle` | 新增测试 |
| POST | `/smallMagictest/updateTitleById` | 修改 |
| GET | `/smallMagictest/delTitleById` | 删除 |
| GET | `/smallMagictest/getTitleById` | 详情 |
| POST | `/smallMagictest/uploadImage` | 上传图片 |

## E. 超神健康（csjk，1 个 Controller，7 个接口）

前缀：`/smallLongHealth/video/`

| 方法 | URL | 功能 |
|------|-----|------|
| POST | `/smallLongHealth/video/add` | 新增视频 |
| POST | `/smallLongHealth/video/update` | 修改 |
| GET | `/smallLongHealth/video/delete` | 删除 |
| GET | `/smallLongHealth/video/getVideoById` | 详情 |
| GET | `/smallLongHealth/video/getPageList` | 列表 |
| POST | `/smallLongHealth/video/uploadVideo` | 上传视频 |
| GET | `/smallLongHealth/video/getProcessImg` | 提取视频帧 |

---

## 汇总

| 类别 | Controller | 接口数 |
|------|-----------|--------|
| **前台用户端** | 16 | **57** |
| 后台-系统管理 | 8 | 41 |
| 后台-万象大考运营 | 18 | 93 |
| 后台-看图猜词 | 5 | 22 |
| 后台-其他小程序 | 2 | 14 |
| 后台-超神健康 | 1 | 7 |
| **后台合计** | 34 | **177** |
| **总计** | **51** | **234** |

---

## 重构优先级建议

| 优先级 | 范围 | 说明 |
|--------|------|------|
| 🔴 高 | 前台 57 个接口 | 用户直接使用，重构核心 |
| 🟡 中 | 后台 wxdk 运营 93 个接口 | 万象大考运营必需 |
| 🟢 低 | 后台 系统管理 41 个接口 | 可用现成框架替代 |
| ⚪ 跳过 | ktck/csjk/其他小程序 43 个接口 | 非本项目关键功能 |
