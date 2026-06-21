import os

base = r'd:\workspace\workspace_2\wanxiangdakao\万象大考v3\dakao\src'

# =============================================
# Page 1: Entry (Home/Login)
# =============================================
entry_page = '''<template>
  <view class="out-box">
    <view class="wrap">

      <!-- 头部：金币 + 消息跑马灯 -->
      <view class="head">
        <view class="gold-box">
          <image src="/static/gold.png" mode="aspectFit" />
          <text>{{ store.appInfo.money }}</text>
        </view>
        <scroll-view class="season-tag" scroll-y :scroll-with-animation="true">
          <view class="tag-inner" v-for="(item, index) in messageList" :key="index">
            <image v-if="item.type===0" :src="item.headImgUrl" mode="aspectFit" />
            <text v-if="item.type===0">{{ item.message }}</text>
            <text v-if="item.type===1" class="top">
              恭喜 <image :src="item.headImgUrl" mode="aspectFit" />
              {{ item.nickname }} 登顶王者
            </text>
          </view>
        </scroll-view>
      </view>

      <!-- 功能入口图标 -->
      <view class="icon-box">
        <image src="/static/icon/gift.png" @tap="showDayGift = true" mode="aspectFit" />
        <image src="/static/icon/gold.png" @tap="showInvate = true" mode="aspectFit" />
        <image src="/static/icon/book.png" @tap="toLottery" mode="aspectFit" />
        <image src="/static/icon/rank-icon.png" @tap="toRankList" mode="aspectFit" />
      </view>

      <!-- 主按钮：排位赛 + 房间对战 -->
      <view class="content">
        <button class="rank-btn" @tap="rankStart" hover-class="click-active">
          <text>排位赛</text>
          <text v-if="store.appInfo.paragraphName" class="para">{{ store.appInfo.paragraphName }}</text>
        </button>
        <view class="btn-box">
          <button class="btn-group" @tap="toRoom" hover-class="click-active">
            <text>房间对战</text>
          </button>
          <button class="btn-rank" @tap="toBook" hover-class="click-active">
            <text>知识宝典</text>
          </button>
        </view>
      </view>

      <!-- 登录授权页 -->
      <view class="login-page sky-bg" v-if="!store.isAuthorized">
        <image src="/static/logo.png" mode="aspectFit" />
        <button class="login-btn" @tap="handleLogin" hover-class="click-active">
          <image src="/static/login-btn3.png" mode="aspectFit" />
        </button>
      </view>

    </view>

    <!-- 弹窗组件 -->
    <season-modal v-if="showSeason" :seasonData="seasonData" @close="showSeason=false" />
    <day-gift v-if="showDayGift" @close="showDayGift=false" />
    <energe-modal v-if="showEnerge" @close="showEnerge=false" />

  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useAppStore } from '@/store/app'
import { get, post } from '@/config/request'

const store = useAppStore()

const messageList = ref([])
const seasonData = ref({})
const showSeason = ref(false)
const showDayGift = ref(false)
const showEnerge = ref(false)
const showInvate = ref(false)

// 微信登录
async function handleLogin() {
  try {
    const loginRes = await uni.login()
    const resp = await get('/login', { code: loginRes.code })
    if (resp.Result) {
      store.setLoginData(resp.Result)
    }
    // 获取用户信息
    const userInfo = await uni.getUserProfile({ desc: '用于展示头像昵称' })
    await post('/authorize', {
      userId: store.appInfo.userId,
      nickName: userInfo.userInfo.nickName,
      avatarUrl: userInfo.userInfo.avatarUrl,
      gender: userInfo.userInfo.gender,
      city: userInfo.userInfo.city,
      province: userInfo.userInfo.province,
      country: userInfo.userInfo.country
    })
    initApp()
  } catch (e) {
    console.error('Login failed:', e)
  }
}

// 初始化
async function initApp() {
  const resp = await post('/init', { userId: store.appInfo.userId, isNew: 1 })
  if (resp.Result) {
    store.updateAppInfo(resp.Result)
    seasonData.value = resp.Result
    if (resp.Result.maxParagraph) {
      showSeason.value = true
    }
  }
  // 获取实时数据
  const realtime = await post('/user/getRealTimeData')
  if (realtime.Result) {
    store.updateAppInfo(realtime.Result)
  }
  // 获取留言板
  const msgs = await post('/getMessageBoardList', { pageIndex: 0, pageSize: 30 })
  if (msgs.Result) {
    messageList.value = msgs.Result
  }
}

function rankStart() {
  uni.navigateTo({ url: '/pages/preGamePage/index' })
}

function toRoom() {
  uni.navigateTo({ url: '/subPackages/rooms/roomList' })
}

function toBook() {
  uni.navigateTo({ url: '/pages/book/index' })
}

function toLottery() {
  uni.navigateTo({ url: '/pages/lottery/index' })
}

function toRankList() {
  uni.navigateTo({ url: '/pages/rankPage/index' })
}

onMounted(() => {
  // 检查本地存储的登录态
  const saved = uni.getStorageSync('appInfo')
  if (saved && saved.userId) {
    store.setLoginData(saved)
    store.updateAppInfo(saved)
    initApp()
  }
})
</script>

<style lang="scss" scoped>
@import '@/styles/public.scss';
@import '@/styles/layout.scss';
@import '@/styles/et-img-map.scss';

.out-box {
  min-height: 100vh;
  background-color: #28235d;
}
.wrap {
  position: relative;
  padding: 20rpx;
}
.head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20rpx 0;
}
.gold-box {
  display: flex;
  align-items: center;
  color: #fcd45f;
  font-size: 28rpx;
  image { width: 40rpx; height: 40rpx; margin-right: 10rpx; }
}
.season-tag {
  flex: 1;
  height: 60rpx;
  margin-left: 20rpx;
  overflow: hidden;
}
.tag-inner {
  display: flex;
  align-items: center;
  height: 60rpx;
  font-size: 24rpx;
  color: #fff7df;
  image { width: 36rpx; height: 36rpx; border-radius: 50%; margin: 0 8rpx; }
}
.icon-box {
  display: flex;
  justify-content: space-around;
  padding: 40rpx 0;
  image { width: 100rpx; height: 100rpx; }
}
.content {
  text-align: center;
}
.rank-btn {
  width: 500rpx;
  height: 120rpx;
  margin: 30rpx auto;
  background: linear-gradient(135deg, #524c99, #7f78c4);
  border-radius: 20rpx;
  color: #fff;
  font-size: 36rpx;
  line-height: 120rpx;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  .para { font-size: 22rpx; color: #fcd45f; }
}
.btn-box {
  display: flex;
  justify-content: center;
  gap: 30rpx;
}
.btn-group, .btn-rank {
  width: 220rpx;
  height: 100rpx;
  background: rgba(255,255,255,0.1);
  border-radius: 16rpx;
  color: #fff;
  font-size: 28rpx;
}
.login-page {
  position: fixed;
  top: 0; left: 0; right: 0; bottom: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  z-index: 999;
  image { width: 300rpx; margin-bottom: 60rpx; }
}
.login-btn {
  width: 400rpx;
  height: 100rpx;
  background: transparent;
  border: none;
  image { width: 100%; height: 100%; }
}
</style>
'''

pages_dir = os.path.join(base, 'pages', 'entry')
os.makedirs(pages_dir, exist_ok=True)
with open(os.path.join(pages_dir, 'index.vue'), 'w', encoding='utf-8') as f:
    f.write(entry_page)
print('entry/index.vue created')
