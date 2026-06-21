<template>
  <div class="m-match-box sky-bg-2">
    <div class="u-match-type">{{mactchType}}</div>
    <div class="u-user-box">
      <div class="u-left">
        <div class="u-head">
          <img :src="appInfo.headImgUrl" alt="">
        </div>
        <div class="u-medal">
          <img v-if="appInfo.image" :src="IMGHEAD+appInfo.image">
          <div class="u-name">{{appInfo.nickname}}</div>
          <div class="u-medal-name">{{appInfo.paragraphName}}</div>
        </div>
      </div>

      <img class="u-lighting" src="/static/lighting.png" alt="">
      <div class="u-right">
        <!-- 等待占位动效 -->
        <div class="u-ant" v-if="other&&!other.basicInfo">
          <div class="u-ant-head">
            <div class="u-ant-big-cycle">
              <img class="u-big-star" src="/static/big-star.png" alt="">
            </div>
            <div class="u-ant-cycle">
              <img class="u-small-star" src="/static/small-star.png" alt="">
            </div>
            <div class="u-ant-cycle-fill">
              <div class="u-ant-dot"></div>
              <div class="u-ant-dot"></div>
              <div class="u-ant-dot"></div>
            </div>
          </div>
          <div class="u-ant-medal"></div>
        </div>
        <!-- 有数据 -->
        <div class="u-data" v-if="other&&other.basicInfo">
          <div class="u-head">
            <img :src="other.basicInfo.avatarUrl" alt="">
          </div>
          <div class="u-medal">
            <img :src="IMGHEAD+other.abilityInfo.paragraphImage">
            <div class="u-name">{{other.basicInfo.nickName}}</div>
            <div class="u-medal-name">{{other.abilityInfo.paragraphName}}</div>
          </div>
        </div>

      </div>

    </div>
    <!-- <img :src="appInfo.headImgUrl" alt=""> -->
    <div class="u-match-tag">
      <div class="u-tag-normal">匹配到了一位</div>
      <div class="u-tag-spec" v-if="!match" :style="wordColor">{{randomList[randomNum]}}</div>
      <div class="u-tag-spec" v-else :style="wordColor">{{staticList[staticRandomNum]}}{{sex}}</div>

    </div>
    <div v-if="!isMatched" class="btn" @click.stop="cancle">取消匹配</div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  props: [ "reStart", "matchInfo","isMatched"], //开关
  data() {
    return {
      match:false,
      sex:"小哥哥",
      time: 0,
      mactchType: "匹配中",
      other: {}, //对战者信息
      randomTimer:null,
      randomNum:0,
      randomColorNum:0,
      staticRandomNum:0,
      randomList:[
        "[神游太空的小哥哥]",
        "[神游太空的小姐姐]",
        "[贴心的小哥哥]",
        "[贴心的小姐姐]",
        "[闷骚的小哥哥]",
        "[可爱的萌妹子]",
        "[温柔的小哥哥]",
        "[温柔的小姐姐]",
        "[害羞的小哥哥]",
        "[害羞的小姐姐]",
        "[才华横溢的小哥哥]",
        "[才华横溢的小姐姐]",
        "[呆萌小哥哥]",
        "[呆萌小姐姐]",
        "[萌萌的小正太]",
        "[萌萌的小萝莉]",
        "[小帅哥]",
        "[小仙女]",
        "[颜值爆表的小哥哥]",
        "[颜值爆表的小姐姐]",
        "[上班摸鱼的小哥哥]",
        "[上班摸鱼的小姐姐]",
        "[智商超高的小哥哥]",
        "[智商超高的小姐姐]",
        "[傲娇的小哥哥]",
        "[傲娇的小姐姐]",
        "[爱抠脚的老大叔]",
        "[爱追星的怪阿姨]",
        "[腹黑的小哥哥]",
        "[蠢萌的小姐姐]",
        "[高冷的小哥哥]",
        "[高冷的小姐姐]",
      ],
      staticList:[
        "年龄相仿的",
        "实力相近的"
      ],
    };
  },
  computed: {
    ...mapState(["appInfo", "IMGHEAD"]),
    wordColor(){
      let list=[
        "color:#fcee21",
        "color:#ff3c8d",
        "color:#93278f",
        "color:#26cfdf",
        "color:#ff9179",
      ]
      return list[this.randomNum];
    },
  },
  watch: {

    matchInfo(val) {
      val.forEach(item => {
        if (item.basicInfo.userId != this.appInfo.userId) {
          this.other =JSON.parse(JSON.stringify(item));
          if (item.boot) {
            this.other.basicInfo.avatarUrl =this.IMGHEAD + this.other.basicInfo.avatarUrl;
            if(this.other.basicInfo.gender!="男"){
              this.sex="小姐姐";
            }
            
          }
        }
      });
      this.match=true;
      clearInterval(this.randomTimer);
    }

  },
  methods: {
    cancle() {
      //取消匹配
      this.$emit("cancleMatch");
      clearInterval(this.randomTimer);
    },
    getRandom(){
      this.randomTimer=setInterval(()=>{
        this.randomNum = parseInt(Math.ceil(Math.random()*31-1));
        this.randomColorNum = parseInt(Math.ceil(Math.random()*5-1));
        this.staticRandomNum = parseInt(Math.ceil(Math.random()*2-1));
        
      },100)
    },
  },
  mounted(){
    this.getRandom();
  },

};
</script>

<style lang="scss" scoped>
@mixin flex {
  display: flex;
  justify-content: center;
  align-items: center;
}
.m-match-box {
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  @include flex;
  justify-content: flex-start;
  flex-direction: column;
  
}

.u-match-type {
  font-size: 48rpx;
  font-weight: bold;
  color: #7efce4;
  line-height: 1;
  margin-top: 120rpx;
  margin-bottom: 30rpx;
}

.u-user-box {
  width: 640rpx;
  height: 460rpx;
  border-radius: 8rpx;
  background: rgba($color: #7efce4, $alpha: 0.1);
  @include flex;
  position: relative;
}
.u-lighting {
  position: absolute;
  left: 50%;
  transform: translate(-50%);
  top: 160rpx;
  width: 111rpx;
  height: 51rpx;
}
.u-left,
.u-right {
  width: 240rpx;
  @include flex;
  flex-direction: column;
}
.u-left {
  margin-right: 90rpx;
}

.u-head {
  width: 200rpx;
  height: 200rpx;
  border: 2rpx solid #75e3d4;
  border-radius: 50%;
  margin-bottom: 24rpx;
  padding: 0;
  @include flex;
  img {
    width: 156rpx;
    height: 156rpx;
    border-radius: 50%;
  }
}

.u-medal {
  position: relative;
  width: 180rpx;
  height: 66rpx;
  background: #424c63;
  border-radius: 20rpx;
  @include flex;
  flex-direction: column;
  margin-left: 30rpx;
  img {
    position: absolute;
    width: 80rpx;
    height: 80rpx;
    left: -45rpx;
    top: 45%;
    transform: translateY(-50%);
  }
  .u-name {
    font-size: 24rpx;
    color: #c7b299;
    width: 100rpx;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    text-align: left;
  }
  .u-medal-name {
    font-size: 20rpx;
    color: #fff;
    text-align: left;
  }
}

.u-ant {
  height: 294rpx;
  width: 100%;
  .u-ant-head {
    @include flex;
    position: relative;
    margin-bottom: 40rpx;
    height: 200rpx;
    width: 100%;
    .u-ant-big-cycle {
      width: 200rpx;
      height: 200rpx;
      border: 2rpx solid #75e3d4;
      border-radius: 50%;
      position: relative;
      animation: rotate 8s linear infinite;
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      .u-big-star {
        width: 32rpx;
        height: 32rpx;
        position: absolute;
        left: 50%;
        top: -16rpx;
        transform: translateX(-50%);
      }
    }

    .u-ant-cycle {
      width: 160rpx;
      height: 160rpx;
      border-radius: 50%;
      border: 2rpx solid #75e3d4;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      animation: rotate2 8s linear infinite;
      .u-small-star {
        width: 32rpx;
        height: 32rpx;
        position: absolute;
        left: -16rpx;
        top: 50%;
        transform: translateY(-50%);
      }
    }
    .u-ant-cycle-fill {
      width: 130rpx;
      height: 130rpx;
      border-radius: 50%;
      background: #5390a0;
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      padding: 0 20rpx;
      box-sizing: border-box;
      @include flex;
      justify-content: space-around;
      .u-ant-dot {
        width: 14rpx;
        height: 14rpx;
        border-radius: 50%;
        background: #7efce4;
        box-shadow: 0 0 10rpx 0rpx #fff;
        opacity: 0;
        animation:dot 1s ease infinite;
        &:nth-child(2){
          animation:dot-2 1s ease infinite;
        }
        &:nth-child(3){
          animation:dot-3 1s ease infinite;
        }
      }
    }
  }
  .u-ant-medal {
    width: 144rpx;
    height: 44rpx;
    background: #579ba7;
    border-radius: 10rpx;
    margin: 0 auto;
  }
}

.u-match-tag{
  margin: 60rpx auto 70rpx;
  @include flex;
  font-size: 28rpx;
  .u-tag-normal{
    color: #7efce4;
    margin-right: 10rpx;
  }
  .u-tag-spec{
    color:#fcee21;
  }
}
.btn{
  width: 260rpx;
  height: 84rpx;
  @include flex;
  border:2rpx solid #7efce4;
  font-size: 36rpx;
  color: #7efce4;
  border-radius: 12rpx;
}
@keyframes rotate {
  0% {
    transform: translate(-50%, -50%) rotate(0);
  }
  100% {
    transform: translate(-50%, -50%) rotate(360deg);
  }
}
@keyframes rotate2 {
  0% {
    transform: translate(-50%, -50%) rotate(0);
  }
  100% {
    transform: translate(-50%, -50%) rotate(-360deg);
  }
}


@keyframes dot {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

@keyframes dot-2 {
  33% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
@keyframes dot-3 {
  66% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
</style>
