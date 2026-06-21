<template>
  <div  :class="{resultType:mode==1,reverse:reverse,}">
    <div class="user-head" :class="{transition:isTransition,show:show,success:result==0,fail:result==1,balance:result==2}">
      <div class="txt" v-if="mode==1">
        <div class="txt-t">{{user.name}}</div>
        <div class="txt-b">{{user.score}}分</div>
      </div>
      <div class="img">
        <img v-if="user.headImg" :src="user.headImg" alt="">
        <!-- <img v-else src="/static/default-head.png" alt=""> -->
      </div>
      <div class="pos-txt" v-if="mode==0">{{user.name}}</div>
      <div class="ans-list" v-if="mode==1">
          <div class="ans-item" :class="{ans0:item==0,ans1:item==1,ans2:item==2}" v-for="(item,index) in ansList" :key="index"></div>
      </div>
    </div>
  </div>

</template>


<script>
// todo
// 通常样式
// 结算样式--成功-失败

export default {
  props: {
    user: {
      type: Object,
      default: {
        headImg: null,
        name: "???",
        score: 0,
        ansList: [0, 0, 0, 0, 0]
      }
    },
    mode: {
      type: Number,
      default: 0 // 0 普通 1 结算
    },
    result: {
      type: Number,
      default: 0 // 0 胜利 1 失败 2平局
    },
    reverse: {
      type: Boolean,
      default: false //默认方向 左
    },
    show: {
      type: Boolean,
      default: false
    },
    ansList:{
      type:Object,
      default:[0,0,0,0,0],
    },
    isTransition:{
      type:Boolean,
      default:true,
    }
  }
};
</script>

<style lang="scss" scoped>
// 普通状态
.user-head {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  position: relative;
  width: 200rpx;
  height: 156rpx;
  background: #67c29f;
  border-top-right-radius: 1000rpx;
  border-bottom-right-radius: 1000rpx;
  padding-right: 15rpx;
  transform: translateX(-200rpx);
  box-sizing: border-box;
  .img {
    position: relative;
    width: 132rpx;
    height: 132rpx;
    background: #d4eddb;
    border: 4rpx solid #fff;
    box-sizing: border-box;
    border-radius: 50%;
    z-index: 2;
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
    }
  }
  .txt {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-end;
    color: #fff;
    margin-right: 15rpx;
    .txt-t {
      font-size: 30rpx;
      width: 4.5em;
      margin-bottom: 10rpx;
      text-overflow: ellipsis;
      overflow: hidden;
      white-space: nowrap;
      text-align: right;
    }
    .txt-b {
      font-size: 44rpx;
    }
  }
  .pos-txt {
    position: absolute;
    bottom: -50rpx;
    font-size: 30rpx;
    width: 5.5em;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    text-align: right;
  }
}
.transition{
  transition: all 0.16s ease;
}
.show {
  transform: translateX(0);
}
//右边-样式反转
.reverse {
  .user-head {
    flex-direction: row-reverse;
    border-top-right-radius: 0;
    border-bottom-right-radius: 0;
    padding-right: 0;
    border-top-left-radius: 1000rpx;
    border-bottom-left-radius: 1000rpx;
    padding-left: 15rpx;
    transform: translateX(200rpx);
  }
  .txt{
    margin-right: 0;
    margin-left: 15rpx;
    align-items: flex-start;
    .txt-t,.txt-b{
      text-align: left;
    }
    
  }
  .pos-txt {
    text-align: left;
  }
  .show {
    transform: translateX(0);
  }
  .ans-list {
    left: auto;
    right: 25rpx;
  }
}

// 结算状态
.resultType {
  .success {
    width: 420rpx;
    height: 200rpx;
    .img {
      width: 172rpx;
      height: 172rpx;
    }
  }
  .fail {
    width: 320rpx;
    height: 175rpx;
    background-color: #d6d6d6;
    .img {
      width: 150rpx;
      height: 150rpx;
    }
  }
  .balance{
    width: 360rpx;
    height: 175rpx;
    .img{
      width: 148rpx;
      height: 148rpx;
    }
  }
}

// 新增 回答记录列表
.ans-list {
  width: 200rpx;
  display: flex;
  justify-content: space-around;
  align-items: center;
  position: absolute;
  bottom: -50rpx;
  left: 25rpx;
}


.ans0 {
  width: 20rpx;
  height: 4rpx;
  background: #abadab;
}

.ans1 {
  width: 34rpx;
  height: 34rpx;
  border-radius: 50%;
  box-sizing: border-box;
  border: 4rpx solid #67c29f;
}

.ans2 {
  width: 40rpx; // height: 4rpx;
  // background: #fd5c5c;
  // border-radius: 10rpx;
  // transform: rotate(45deg);
  position: relative;
  &:after {
    position: absolute;
    top: 50%;
    left: 50%;
    content: "";
    width: 40rpx;
    height: 4rpx;
    background: #fd5c5c;
    border-radius: 10rpx;
    transform: translate(-50%, -50%) rotate(-45deg);
  }
  &:before {
    position: absolute;
    top: 50%;
    left: 50%;
    content: "";
    width: 40rpx;
    height: 4rpx;
    background: #fd5c5c;
    border-radius: 10rpx;
    transform: translate(-50%, -50%) rotate(45deg);
  }
}

</style>
