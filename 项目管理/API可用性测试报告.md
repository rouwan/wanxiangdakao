# API 可用性测试报告

> 日期：2026-06-16  
> 测试方式：登录 dev_test_01 → 逐接口调用 → 检查返回码和 SQL 错误  
> 初始结果：43 个接口中 37 个失败（原因：InitDataListener 因缺表未完成初始化）  
> 修复初始化后：43 个接口中 38 个通过，5 个失败

---

## 修复前后对比

| 阶段 | 通过 | 失败 | 原因 |
|------|------|------|------|
| 修复前 | 6 | 37 | InitDataListener 未初始化 dataMap → NullPointerException → 全部 500 |
| 修复后 | 38 | 5 | 3 个缺表 + 1 个缺列 + 1 个缺数据 |

---

## 已修复的问题（修复过程中发现）

| 问题 | 表/列 | 影响范围 |
|------|--------|----------|
| 缺表 | `wxdk_usercombat` | 登录初始化 |
| 缺列 | `wxdk_userability.expValue`, `grade` | 登录初始化、用户能力查询 |
| 缺表 | `wxdk_userbook` | 实时数据查询（宝典状态） |
| 缺配置 | `wxdk_dataconfig` 10 个配置项 | 全部接口（通过 DataConfiguration.getMaxGrade 等） |

---

## 当前仍失效的 5 个接口

### 1. BOOK_getUserBookList — 缺表 `wxdk_book`

```
POST /book/getUserBookList
Table 'wxdkgamedo.wxdk_book' doesn't exist
```

**用户流**：首页 → 知识宝典 → 6 科宝典列表  
**影响**：宝典页面白屏或数据为空

### 2. USER_getUser_type2 — 缺表 `wxdk_usercombatdetail`

```
POST /user/getUser?type=2
Table 'wxdkgamedo.wxdk_usercombatdetail' doesn't exist
```

**用户流**：玩家详情页 → 战斗力明细 tab  
**影响**：战斗力明细无法展示

### 3. SPECIAL_getSubject — 缺表 `wxdk_specialsubject`

```
POST /specialSubject/getSubject
Table 'wxdkgamedo.wxdk_specialsubject' doesn't exist
```

**用户流**：特殊活动页面（非核心流程）  
**影响**：特殊活动不可用

### 4. WELFARE_getList_type3 — 缺列 `isLookNum`

```
POST /dayWelfare/getDayWelfareList?type=3
Unknown column 'a.isLookNum'
```

**用户流**：每日福利 → 幸运数字页签  
**影响**：幸运数字功能不可用

### 5. BOOK_upLevelByType — 缺数据（非 schema 问题）

```
POST /book/upLevelByType?type=2
Incorrect result size: expected 1, actual 0
```

**用户流**：知识宝典 → 升级某学科  
**影响**：无法升级宝典（因为测试用户 `wxdk_userbook` 表里没有数据行）  
**说明**：这不是 schema 问题。新用户本来就没有宝典记录，代码应该创建一行而不是直接 queryForMap。这是旧代码的 bug，线上可能存在同样的边界情况。

---

## 全部通过的核心流程

以下接口已经全部可用，前端可直接调用：

| 页面 | 接口 | 状态 |
|------|------|------|
| 首页 | login, init, getRealTimeData, getMessageBoard, authorize, wsLogin | ✅ |
| 排位赛 | getSeasonParagraphInfo, getStrength, beforeMatch | ✅ |
| 排行榜 | getRankList (type 1-4) | ✅ |
| 宝典 | getBookUpLevelCount | ✅ |
| 抽奖 | randomPrize | ✅ |
| 每日福利 | getDayWelfareList (type 1), sign, hitCat, numberReceive | ✅ |
| 每日任务 | getTaskList, receiveGood | ✅ |
| 邮件 | getEmailList, updateStatus | ✅ |
| 背包 | getEquipmentList, useEquipment | ✅ |
| 好友 | getFriendList | ✅ |
| 题库 | getSubjectNum, getSubjectById, getSubjectList | ✅ |
| 周赛 | getWeekTitleList, getSubject | ✅ |
| 反馈 | feedBackSubject, feedBackUser | ✅ |
| 房间 | getHeadData, rankHallData | ✅ |
| 玩家 | getUser (type 1, 3, 4) | ✅ |

---

## 修补建议

### 数据库（3 张表 + 1 列）

```sql
-- 表 1：知识宝典配置（管理后台用）
CREATE TABLE wxdk_book (
  id INT AUTO_INCREMENT PRIMARY KEY,
  type INT, image VARCHAR(200), remark VARCHAR(200),
  sortId INT DEFAULT 0, flag INT DEFAULT 1,
  modifyTime DATETIME, createTime DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 表 2：战斗力明细
CREATE TABLE wxdk_usercombatdetail (
  id INT AUTO_INCREMENT PRIMARY KEY,
  userId INT NOT NULL,
  parentType INT, combat INT DEFAULT 0,
  modifyTime DATETIME, createTime DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 表 3：特殊活动题目
-- 需从代码反推字段（UserSpecialSubjectDao.java）
-- 暂估字段：
CREATE TABLE wxdk_specialsubject (
  id INT AUTO_INCREMENT PRIMARY KEY,
  ... -- 需读代码确认

-- 列：幸运数字
ALTER TABLE wxdk_lucknumber ADD COLUMN isLookNum INT DEFAULT 1;
-- 或者：ALTER TABLE wxdk_lucknumberconfig ADD COLUMN isLookNum INT DEFAULT 1;
```

### 代码（1 处）

- `UserBookService.upLevelByType()` — 当用户没有宝典记录时，不应 `queryForMap` 抛异常，应自动 insert 一行默认数据或返回友好提示。

---

## 结论

**核心答题流程（登录 → 首页 → 排位赛 → 排行榜 → 抽奖 → 签到）已全部打通。** 剩余 5 个失效接口属于辅助功能（宝典详情、战斗力明细、特殊活动、幸运数字）。补齐 3 张表 + 1 列 + 宝典升级的容错处理即可 100% 覆盖。
