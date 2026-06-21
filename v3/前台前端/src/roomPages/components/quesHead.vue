<template>
  <div class="m-head"
       v-if="list.length>0">
    <div class="u-head-item"
         v-for="(item,headIndex) in list"
         :key="headIndex">
      <div class="u-score-box"
           v-if="item!=0"
           :class="{'show-add-type':item.abilityInfo.scoreSub>0&&item.showAnswer,'show-none-type':item.abilityInfo.scoreSub==0&&item.showAnswer}">
        <div class="u-inner-box">
          <div class="u-add-score"
               v-if="item.showAnswer">+{{item.abilityInfo.scoreSub}}</div>
          <div class="u-score"
               v-else>{{item.abilityInfo.score}}</div>
        </div>
      </div>
      <div class="u-out">
        <div class="u-inner">
          <img v-if="item!=0&&item.basicInfo.avatarUrl&&item.boot"
               :src="IMGHEAD+item.basicInfo.avatarUrl"
               :class="{'off-net':item.status==0}">
          <img v-if="item!=0&&item.basicInfo.avatarUrl&&!item.boot"
               :src="item.basicInfo.avatarUrl"
               :class="{'off-net':item.status==0}">
        </div>
        <div class="u-net-off"
             v-if="item!=0&&item.status==0">掉线</div>
      </div>
      <div class="u-name"
           v-if="item!=0">{{item.abilityInfo.nickName}}</div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  props: ["userList", "ansList", "userNum", "update"],
  data() {
    return {
      showAddScore: false,
      list: [0, 0, 0, 0],
      _userList: []
    };
  },
  watch: {
    update() {
        this.list.forEach((item, index) => {
          this.$set(
            this.list,
            index,
            Object.assign(item, { showAnswer: false })
          );
        });
    },
    userList(list) {
      let type = false;
      for (let i = 0; i < list.length; i++) {
        if (list[i].abilityInfo.score > 0 || list[i].status == 0) {
          type = true;
          break;
        }
      }
      if (type) {
        this.initUserList();
      }
    },
    ansList(_list) {
      if (_list.length > 0) {
        // let _last = JSON.parse(JSON.stringify(_list)).slice(_list.length - 1);
        let _last = _list.slice(_list.length - 1);
        this.list.forEach((item, index) => {
          if (item != 0 && item.basicInfo.userId == _last[0].uid) {
            item.abilityInfo.score = _last[0].score;
            item.abilityInfo.scoreSub = _last[0].scoreSub;
            item.showAnswer = true;
            // setTimeout(() => {
            //   this.$set(this.list,index,Object.assign(item, {showAnswer:false}));
            // }, 500);
          }
        });
      }
    
    }
  },
  computed: {
    ...mapState(["IMGHEAD"])
  },
  methods: {
    initUserList() {
      let _list = JSON.parse(JSON.stringify(this.userList));
      // this.list = JSON.parse(JSON.stringify(this.userList));
      for (let i = 0; i < this.list.length; i++) {
        if (_list[i]) {
          this.list[i] = _list[i];
        }
      }
    }
  },
  mounted() {
    this.initUserList();
  }
};
</script>

<style lang="scss" scoped>
@mixin flex {
  display: flex;
  justify-content: center;
  align-items: center;
}

.m-head {
  width: 100%;
  @include flex;
  color: #fff;
  height: 144rpx;
  text-align: center;
}
.u-head-item {
  @include flex;
  flex-direction: column;
  justify-content: flex-start;
  margin-right: 40rpx;
  height: 100%;
  position: relative;
  &:last-child {
    margin-right: 0;
  }
}
.u-score-box {
  width: 124rpx;
  height: 62rpx;
  border: 5rpx solid #4d497c;
  border-radius: 10rpx;
  box-sizing: border-box;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  top: -32rpx;
  background: #28235c;
  z-index: 3;
  transition: all 0.166s linear;
  .u-inner-box {
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    border: 3rpx solid #fff;
    border-radius: 10rpx;
    font-size: 30rpx;
    @include flex;
  }
}
.show-add-type {
  animation: right 0.2s linear;
  .u-inner-box {
    border-color: #85d85d !important;
  }
  .u-add-score {
    color: #85d85d !important;
  }
}
@keyframes right {
  0% {
    transform: translateX(-50%) scale(1);
  }
  50% {
    transform: translateX(-50%) scale(1.2);
  }
  100% {
    transform: translateX(-50%) scale(1);
  }
}
@keyframes false {
  0%,
  50%,
  100% {
    transform: translateX(-50%) rotate(0);
  }
  25% {
    transform: translateX(-50%) rotate(10deg);
  }
  75% {
    transform: translateX(-50%) rotate(-10deg);
  }
}
.show-none-type {
  animation: false 0.2s linear;
  .u-inner-box {
    border-color: #ff217b !important;
  }
  .u-add-score {
    color: #ff217b !important;
  }
}
.u-out,
.u-inner {
  box-sizing: border-box;
  border-radius: 50%;
}
.u-out {
  width: 134rpx;
  height: 134rpx;
  border: 2rpx solid #403d6e;
  @include flex;
  position: relative;
}
.u-inner {
  width: 116rpx;
  height: 116rpx;
  border: 4rpx solid #4d497c;
  border-radius: 50%;
  overflow: hidden;
  img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
  }
}
.u-net-off {
  width: 72rpx;
  height: 26rpx;
  line-height: 26rpx;
  font-size: 20rpx;
  background: #ea1e79;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  bottom: -6rpx;
  border-radius: 18rpx;
}
.u-name {
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font-size: 26rpx;
}

.off-net {
  filter: grayscale(100%);
  border-radius: 50%;
}
</style>
