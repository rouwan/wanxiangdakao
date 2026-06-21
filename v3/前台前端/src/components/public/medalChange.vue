<template>
  <div class="wrap medal-bg">
    <div class="medal-box" v-if="true">
      <!-- <img class="m-word-img" :class="{show:levelUp}" src="/static/rank/level-up.png"> -->
      <div class=" m-word medal-word" :class="{'show-medal-title':levelUpWord&&medalInfo.paragraph>=41}">星级提升</div>
      <div class=" m-word-blue" :class="{'show-medal-title':levelUpWord&&medalInfo.paragraph<41}">星级提升</div>
      <div class="m-word medal-word" :class="{'show-medal-title':isMedalUp}">段位提升</div>
      <div class=" m-word-blue" :class="{'show-medal-title':streakUpWord}">连胜奖励：更多星数</div>

      <div class=" m-word-black" :class="{'show-medal-title':levelDownWord}">星级降低</div>
      <div class=" m-word-black medal-word" :class="{'show-medal-title':isMedalDown}">段位降低</div>

      <div class="m-color" :class="{'color-down':showColor}">
        <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/color-div.png" alt="">
      </div>
      <div class="u-light-bg" :class="{'show':showLightBg}">
          <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/light-bg.png" alt="">
      </div>
      <div class="u-light-bg left60" :class="{'show':showLightBg&&medalInfo.paragraph>=41}">
          <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/topLightBg.png" alt="">
      </div>
      <div class="m-light-cycle" :class="{'short-big':shineLightBg,'loop-big':loopLight}">
          <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/light-cycle.png" alt="">
      </div>
        <!-- 最强王者 特殊css -->
      <div class="m-top-star" :class="{showTopStar:medalInfo.paragraph>=41}">
        <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/star.png">
        x
        <div class="star-num">{{medalInfo.star}}</div>
      </div>


      <!-- <div class="m-star-list" v-if="medalInfo.paragraph<41" :class="{'m-star-list-hide':hideAllStar}">
        <div class="u-star" v-for="(item,index) in starList" :key="index">
          <img class="u-star-act" :class="{tansStar:item.trans,showStaticStar:item.static,showStar:item.act,'downStar-1':item.down&&index==0,'downStar-2':item.down&&index==1,'downStar-3':item.down&&index==2}" src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/star.png">
        </div>
      </div> -->

      <div class="m-star-list" :class="{
        'star-hide-1':hideAllStar&&needStar==1,
        'star-hide-2':hideAllStar&&needStar==2,
        'star-hide-3':hideAllStar&&needStar==3,
        'star-hide-4':hideAllStar&&needStar==4,
        'star-hide-5':hideAllStar&&needStar==5,
        'star-1':needStar==1,
        'star-2':needStar==2,
        'star-3':needStar==3,'star-4':needStar==4,
        'star-5':needStar==5}" v-if="medalInfo.paragraph<41">
        <div class="u-star" v-for="(item,index) in starList" :key="index">
          <img class="u-star-act" :class="{tansStar:item.trans,
                                  showStaticStar:item.static,
                                  showStar:item.act,
                                  'downStar-0':item.down&&((needStar==1&&index==0)||(needStar==3&&index==1)||(needStar==5&&index==2)),
                                  'downStar-l-5':item.down&&((needStar==2&&index==0)||(needStar==4&&index==1)),
                                  'downStar-r-5':item.down&&((needStar==2&&index==1)||(needStar==4&&index==2)),
                                  'downStar-l-10':item.down&&((needStar==3&&index==0)||(needStar==4&&index==1)),
                                  'downStar-r-10':item.down&&((needStar==3&&index==2)||(needStar==4&&index==3)),
                                  'downStar-l-15':item.down&&((needStar==4&&index==0)||(needStar==5&&index==0)),
                                  'downStar-r-15':item.down&&((needStar==4&&index==3)||(needStar==5&&index==4))
                                  }" 
                                  src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/star.png">
        </div>
      </div>

      <div class="m-medal">
        <div class="u-img" :class="{shrinkImg:shrinkImgBtn}">
          <img :src="IMGHEAD+medalImg">
         
        </div>
        <div class="u-img" v-if="magnifyBtn" :class="{magnify:magnifyBtn}">
          <img :src="IMGHEAD+newMedalImg">
        </div>
      
      </div>
       <div class="u-medal-level" :class="{'small-medal-change':smallMedalUp}">
            <img v-if="showNewBg||medalInfo.paragraph>=41"  src="/static/rank/topMedalBg.png">
            <img v-else src="/static/rank/medal-bg.png">
            <div class="u-medal-name">
              {{medalName}}{{medalNum}} 
            </div>
        </div>
        <img class="u-btn" :class="{'show':showBtn}" @click="closeEvent" src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/close-btn-new.png" alt="">
    </div>
  </div>

</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  props:["medalInfo","isRun"],
  data() {
    return {
      needStar: 0,
      shrinkBtn: false, //光圈
      shrinkImgBtn: false, //旧图标
      magnifyBtn: false, //新图标 显示
      showLightBg:false,
      shineLightBg: false, //光圈闪现一次
      showColor:false,//彩带背景
      loopLight: false, //佛光
      showBtn:false,
      levelDownWord:false,
      isMedalDown:false,
      starTrans: true, //星星过渡效果
      fullStar: false, //旧段位满星状态
      bigMedalChange: false, //大段变化
      smallMedalUp: false,
      isMedalUp:false,
      streakUpWord:false,
      levelUpWord: false,
      eventOnce: true,
      showNewBg:false,//最强王者 段位背景图 更换
      winStreak: false, //是否连胜
      // 动画控制
      hideAllStar:false,
      // mock data
      starList: [], //星星列表
      medalName: "鱼跃龙门",
      medalNum: "Ⅰ",
      medalImg:
        "http://192.168.0.21:8012/vientianeTestParagraphImage/f58c2981-9aa4-4e78-90da-2991a9685973.jpg",
      newMedalName: "侮辱青铜",
      newMedalNum: "Ⅰ",
      newMedalImg:
        "http://192.168.0.21:8012/vientianeTestParagraphImage/d256f1c6-b1ba-4889-83f8-90379bae602f.jpg",

      // medalInfo: {
      //   continueWinNum: 0,
      //   paragraph: 3,
      //   paragraphImage:
      //     "http://192.168.4.108:8012/vientianeTestParagraphImage/f58c2981-9aa4-4e78-90da-2991a9685973.jpg",
      //   paragraphName: "不屈黄铜Ⅴ",
      //   star: 0
      // },
      // newData: {
      //   continueWinNum:0,
      //   paragraph: 2,
      //   paragraphImage:
      //     "http://192.168.4.108:8012/vientianeTestParagraphImage/d256f1c6-b1ba-4889-83f8-90379bae602f.jpg",
      //   paragraphName: "不屈黄铜Ⅰ",
      //   star: 3
      // }
    };
  },
  computed:{
    ...mapState(["IMGHEAD"])
  },
  watch: {
    medalInfo(_new, _old) {
      
      // 初始化新段位名字和数字
      this.newMedalName = _new.paragraphName.slice(0, 4);

      if(_new.paragraph>=41){
        this.newMedalNum ="";
      }else{
        this.newMedalNum = _new.paragraphName.slice(4)||"";
      }
      // this.newMedalNum = _new.paragraphName.slice(4);
      this.newMedalImg = _new.paragraphImage;
      //区分 升级 掉级 升小段 掉小段 升大段 掉大段
      _old.star == this.getNeedStar(_old.paragraph) ? this.fullStar = true : this.fullStar=false; //判断满星
      _new.continueWinNum >= 3&&_old.paragraph<30? (this.streakUpWord = true,this.showBtn=true) : null; //判断连胜
      _old.paragraphName.slice(0,4) == _new.paragraphName.slice(0,4)
        ? (this.bigMedalChange = false)
        : (this.bigMedalChange = true); //是否大段变化

      _new.paragraph > _old.paragraph ? this.handleMedalUp() : null; //先升级 在升段
      _new.paragraph < _old.paragraph ? this.handleMedalDown() : null;


      if (_new.paragraph == _old.paragraph && _new.star > _old.star) {
        if(!this.streakUpWord){
          this.levelUpWord=true;
          this.showBtn=true;
        }
        this.handleLevelUp();
        setTimeout(() => {
          this.checkWinStreak();
        }, 500);
      }

      _new.paragraph == _old.paragraph && _new.star < _old.star
        ? this.handleLevelDown()
        : null;

      // 段位保护
       if (_new.paragraph == _old.paragraph && _new.star == _old.star){
        this.showBtn=true;
       }
      setTimeout(() => {
        this.needStar = this.getNeedStar(_new.paragraph);
      }, 900);
    },
  },
  computed: {
    ...mapState(["IMGHEAD"]),
  },
  methods: {
    medalUp() {
      this.medalInfo = this.newData;
    },
    initStarList() {
      let list = [];
        for (let i = 0; i < this.needStar; i++) {
          let star = { static: false, act: false, down: false };
          if (i < this.medalInfo.star) {
            star.static = true;
          }
          list.push(star);
        }
      this.starList = list;
    },
    initEmptyStarList(){
     let list = [];
      for (let i = 0; i < this.needStar; i++) {
        let star = { static: false, act: false, down: false };
        list.push(star);
      }
      this.starList = list;
    },
    getNeedStar(paragraph){
     if (paragraph < 6) {
        return 1;
      } else if (paragraph >= 6 && paragraph < 11) {
        return 2;
      } else if (paragraph >= 11 && paragraph < 21) {
        return 3;
      } else if (paragraph >= 21 && paragraph < 31) {
        return 4;
      } else {
        return 5;
      }
    },
    initMedalName() {
      this.medalName = this.medalInfo.paragraphName.slice(0,4)||"数据错误";
      if(this.medalInfo.paragraph>=41){
        this.medalNum ="";
      }else{
        this.medalNum = this.medalInfo.paragraphName.slice(4)||"";
      }
      this.medalImg = this.medalInfo.paragraphImage;
    },

    handleMedalUp() {
      this.handleLevelUp(); //升级 加一星
        setTimeout(() => {
          this.checkWinStreak(); //连胜 在加一星
        }, 500);

      if (this.bigMedalChange) {
        //升大段
        setTimeout(() => {
          this.handleHideOldMedal();
        }, 600);
      } else {
        setTimeout(() => {
          this.isMedalUp=true;
          this.showBtn=true;
          this.streakUpWord=false;
          this.handleSmallMedalChange(); //小段位变化
          this.hanldeHideAllStar(); //隐藏星星
          setTimeout(() => {
            this.showLightBg = true;
          }, 150);
          if(this.fullStar){
            setTimeout(() => {
              this.checkWinStreak(); //连胜 在加一星
            }, 500);
          }
        }, 600);
      }
    },

    handleMedalDown() {
      if (this.bigMedalChange) {
        this.isMedalDown=true;
        this.showBtn=true;
        this.shrinkImgBtn = true;
        this.handleSmallMedalChange();
        setTimeout(() => {
        this.medalName = this.medalInfo.paragraphName.slice(0,4);
      }, 150);
        setTimeout(() => {
          this.magnifyBtn = true;
        }, 300);
        setTimeout(() => {
          this.initStarList();
        }, 1000);

      } else {
        this.isMedalDown=true;
        this.showBtn=true;
        this.handleSmallMedalChange();
        this.initStarList();
        // this.initEmptyStarList();
      }
    },

    handleLevelUp() {
      this.levelUp = true;
      for (let i = 0; i < this.starList.length; i++) {
        if (!this.starList[i].static) {
          this.starList[i].act = true;
          this.starList[i].static=true;
          if (this.eventOnce) {
            this.eventOnce = false; //执行一次
          }
          break;
        }
      }
    },

    handleSmallMedalChange() {
      //小段变化
      this.smallMedalUp = true;
      setTimeout(() => {
        if(this.medalInfo.paragraph>=41){
          this.medalNum = "";
          this.showNewBg=true;
        }else{
          this.medalNum = this.medalInfo.paragraphName.slice(4)||"";
        }
      }, 150);
     
    },

    handleLevelDown() {
      this.levelDownWord=true;
      this.showBtn=true;
      let last = 0;
      for (let i = 0; i < this.starList.length; i++) {
        if (this.starList[i].static) {
          last = i;
        }
      }
      this.starList[last].down = true;
    },

    checkWinStreak() {
      if (this.medalInfo.continueWinNum >= 3&&this.medalInfo.paragraph<31) {
        this.winStreak = true;
        this.handleLevelUp();
      }
    },
    hanldeHideAllStar(){
      this.hideAllStar=true;
          setTimeout(() => {
            this.initEmptyStarList();
            this.hideAllStar=false;
        }, 300);
    },
    handleHideOldMedal() {
      // 旧段位图标隐藏
      // this.shrinkBtn = true;
      // setTimeout(() => {
        
      // }, 300);
          this.hanldeHideAllStar();
          setTimeout(() => {
            this.shrinkImgBtn = true;
            this.handleSmallMedalChange();
            setTimeout(() => {
            this.medalName = this.medalInfo.paragraphName.slice(0,4);
              if(this.medalInfo.paragraph>41){
                this.showNewBg=true;
               }
             }, 150);
            setTimeout(() => {
              this.handleShowNewMedal();
            }, 300);
          }, 290);
    },

    handleShowNewMedal() {
      //新段位图标出现
      this.streakUpWord=false;//连胜标题隐藏
      this.levelUpWord=false;//星级标题隐藏
      this.isMedalUp=true;//段位标题出现
      this.showBtn=true;
      this.magnifyBtn = true;
      setTimeout(() => {
        this.shineLightBg = true; //光圈闪现
      }, 200);
      setTimeout(() => {
        this.shineLightBg=false;
        this.showLightBg = true;
        this.loopLight=true;
        this.showColor=true;
      }, 950);
      setTimeout(() => {
          this.initEmptyStarList();
            // 判断连胜
          if(this.fullStar){
            this.checkWinStreak();
          }
      }, 420);
    
    },
     closeEvent(){
      this.$emit("close");
    },

    //
  },
  mounted() {
    try{
      this.needStar = this.getNeedStar(this.medalInfo.paragraph); //2018-11-16
      this.initStarList(); //执行一次初始化 旧的 段位星级列表
      this.initMedalName(); //初始化 段位名字和数字
    }catch(err){
      console.log(err);
      console.log('medalchange mounted error')
    }
    
  }
};
</script>

<style lang="scss" scoped>
.left60{
  left: 60%!important;
}
.wrap {
  width: 100%;
  height: 100%;
}
.medal-box {
  width: 100%;
  height: 100%;
  position: absolute;
  left: 0;
  top: 0;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  flex-direction: column;
}
@mixin cssCenter {
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.m-color{
  width: 100%;
  height: 100%;
  position: absolute;
  left: 0;
  top: 0;
  transform: translateY(-100%);
  img{
    width: 100%;
    height: 100%;
  }
}
.color-down{
  animation: color-down 3.65s linear;
}
@keyframes color-down {
  0%{
   transform: translateY(-100%);
  }
   100%{
   transform: translateY(100%);
  }
}
.m-word,
.m-word-blue,
.m-word-black{
  width: 100%;
  text-align: center;
  position: absolute;
  left: 50%;
  top: 120rpx;
  transform: translateX(-50%);
  font-size: 48rpx;
  font-weight: bold;
  opacity: 0;
  transition:all .291s linear;
}
.m-word{
  background: linear-gradient(180deg,#fdcea2,#e39939);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  text-shadow: 0 0 30rpx rgba(#fbaa5b,.6);
}

.m-word-blue{
  background: linear-gradient(180deg,#93d4f9,#78c4f3,#47a5e7);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  text-shadow: 0 0 30rpx rgba(#78c4f3,.6);
}
.m-word-black{
  color: #c3c3c3;
}
.medal-word{
  transform: translate(-50%,30rpx);
}
.show-medal-title{
  transform: translate(-50%,0rpx)!important;
  opacity: 1!important;
}



.m-word-img {
  width: 226rpx;
  height: 66rpx;
  margin-bottom: 40rpx;
  opacity: 0;
  transition: all 0.3s ease;
}
.m-medal {
  width: 258rpx;
  height: 258rpx;
  position: relative;
  position: absolute;
  top:334rpx;
  left: 50%;
  transform: translateX(-50%);
}
.u-img {
  width: 258rpx;
  height: 258rpx;
  position: absolute;
  @include cssCenter;
  z-index: 2;
  & > img {
    width: 100%;
    height: 100%;
  }
  

}


.u-medal-level {
    width: 100%;
    height: 72rpx;
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    margin-top: 610rpx;
    img {
      width: 326rpx;
      height: 80rpx;
      position: absolute;
      @include cssCenter;
    }
    .u-medal-name {
      position: absolute;
      width:100%;
      top: 8rpx;
      left: 50%;
      transform:translateX(-50%);
      font-size: 36rpx;
      font-weight: bold;
      color: #fff;
      line-height: 1;
      display: flex;
      align-items: center;
       justify-content: center;
    }
  }
  .small-medal-change{
    animation:smallMedalChange .33s ease;
  }
  @keyframes smallMedalChange {
    0%{opacity: 1;transform: translate(-50%,0)}
    50%{opacity: 0;transform: translate(-50%,30rpx)}
    100%{opacity: 1;transform: translate(-50%,0)}
  }

.u-light-bg {
  position: absolute;
  top: 150rpx;
  left: 50%;
  transform: translate(-50%,0);
  transition: all 0.291s ease;
  opacity: 0;
  img {
    width: 750rpx;
    height: 750rpx;
  }
}
.show{
  opacity: 1!important;
}
.showTopStar{
  opacity: 1!important;
  transform:translateX(-50%) scale(1)!important;
}
// 光圈
.m-light-cycle{
  width: 700rpx;
  height: 700rpx;
  position: absolute;
  top: 600rpx;
  left: 50%;
  transform: translate(-50%,0) scale(0);
  opacity: 0;
  img{
    width: 100%;
    height: 100%;
  }
}
.short-big{
  animation:short-big .75s ease;
}
@keyframes short-big {
  0%{
   transform: translate(-51%,-68%) scale(0);
   opacity: 0;
  }
  33%{
  transform: translate(-51%,-68%) scale(1);
   opacity: .7;
  }
  100%{
   transform: translate(-51%,-68%) scale(1.2);
   opacity: 0;
  }
}
.loop-big{
  animation:loop-big 1s linear infinite;
}
@keyframes loop-big {
  0%{
   transform: translate(-51%,-70%) scale(0);
   opacity: 0;
  }
  50%{
    transform: translate(-51%,-70%) scale(.35);
    opacity: 1;
  }
  100%{
  transform: translate(-51%,-70%) scale(.7);
   opacity: 0;
  }
  // 100%{
  //  transform: translate(-51%,-68%) scale(0);
  //  opacity: 0;
  // }
}

.m-top-star{
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 260rpx;
  left: 50%;
  transform: translateX(-50%);
  color: #fff;
  font-size: 46rpx;
  opacity: 0;
  transform: scale(0);
  transition: all 1s linear;
  img{
    width: 51rpx;
    height: 51rpx;
    margin-right: 10rpx;
  }
  .star-num{
    font-size: 48rpx;
    margin-left: 10rpx;
  }
}
// 星星列表
.m-star-list {
  display: flex;
  justify-content: center;
  align-items: flex-end;
  height: 65rpx;
  position: absolute;
  top: 260rpx;
  left: 50%;
  transform: translateX(-50%);
}

.u-star {
  position: relative;
  width: 51rpx;
  height: 51rpx;
  margin-right: 16rpx;
  background-size: 100%;
  background-repeat: no-repeat;
  background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/star-black.png");
  .u-star-act {
    width: 48rpx;
    height: 48rpx;
    transform:scale(0);
  }
 
  .showStaticStar{
    transform: scale(1);
  }
  .tansStar{
    animation:transStar .25s ease forwards;
  }
  .showStar {
    animation: showStar .29s ease forwards;
  }
}
@keyframes transStar {
  0%{
    opacity: 0;
    transform: scale(1);
  }
  100%{
    opacity: 1;
    transform: scale(1);
  }
}
@keyframes showStar {
  0%{
    transform: scale(0);
  }
  57%{
    transform: scale(1.2);
    
  }
  100%{
    transform: scale(1);
  }
}


.star-1 {
  .u-star {
    transform: rotate(0deg);
  }
}

.star-2 {
  .u-star {
    &:nth-of-type(1){
      transform: rotate(-15deg);
    }
    &:nth-of-type(2){
      transform: rotate(15deg);
    }
  }
}
.star-3 {
  .u-star {
    &:nth-of-type(1){
      transform: rotate(-15deg);
    }
    &:nth-of-type(2){
      align-self: flex-start;
      transform: rotate(0);
    }
    &:nth-of-type(3){
      transform: rotate(15deg);
    }
  }
}
.star-4 {
  height: 75rpx;
  .u-star {
    &:nth-of-type(1){
      transform: rotate(-30deg);
    }
    &:nth-of-type(2){
      align-self: flex-start;
      transform: rotate(-15deg);
    }
    &:nth-of-type(3){
      align-self: flex-start;
      transform: rotate(15deg);
    }
    &:nth-of-type(4){
      transform: rotate(30deg);
    }
  }
}
.star-5 {
  height: 90rpx;
  .u-star {
    &:nth-of-type(1){
      transform: rotate(-30deg);
    }
    &:nth-of-type(2){
      align-self: flex-start;
      margin-top: 10rpx;
      transform: rotate(-15deg);
    }
    &:nth-of-type(3){
      align-self: flex-start;
      transform: rotate(0);
    }
    &:nth-of-type(4){
      align-self: flex-start;
      margin-top: 10rpx;
      transform: rotate(15deg);
    }
    &:nth-of-type(5){
      transform: rotate(30deg);
    }
  }
}
// 满星 收缩 样式
.star-hide-1 {
  .u-star {
    animation: starHide11 0.29s ease forwards;
  }
  @keyframes starHide11 {
    0% {
      transform: translate(0, 0);
    }
    100% {
      transform: translate(0rpx, 200rpx);
    }
  }
}
.star-hide-2 {
  .u-star {
    &:nth-of-type(1){
      animation: starHide21 0.29s ease forwards;
    }
    &:nth-of-type(2){
      animation: starHide22 0.29s ease forwards;
    }
  }
  @keyframes starHide21 {
    0% {
      transform: translate(0, 0);
    }
    100% {
      transform: translate(30rpx, 200rpx);
    }
  }
    @keyframes starHide22 {
    0% {
      transform: translate(0, 0);
    }
    100% {
      transform: translate(-30rpx, 200rpx);
    }
  }
}
.star-hide-3 {
  .u-star {
    &:nth-of-type(1) {
      .u-star-act {
        animation: starHide31 0.29s ease forwards;
      }
    }
    &:nth-of-type(2) {
      .u-star-act {
        animation: starHide31 0.29s ease forwards;
      }
    }
    &:nth-of-type(3) {
      .u-star-act {
        animation: starHide31 0.29s ease forwards;
      }
    }
  }
  @keyframes starHide31 {
    0% {
      transform: translate(0, 0);
    }
    100% {
      transform: translate(0, 200rpx);
    }
  }
}
.star-hide-4 {
  .u-star {
    &:nth-of-type(1) {
      .u-star-act {
        animation: starHide41 0.29s ease forwards;
      }
    }
    &:nth-of-type(2) {
      .u-star-act {
        animation: starHide42 0.29s ease forwards;
      }
    }
    &:nth-of-type(3) {
      .u-star-act {
        animation: starHide43 0.29s ease forwards;
      }
    }
    &:nth-of-type(4) {
      .u-star-act {
        animation: starHide44 0.29s ease forwards;
      }
    }
    
  }
  @keyframes starHide41 {
    0% {
      transform: translate(0, 0);
    }
    100% {
      transform: translate(-30rpx, 230rpx);
    }
  }
   @keyframes starHide42 {
    0% {
      transform: translate(0, 0);
    }
    100% {
      transform: translate(-10rpx, 230rpx);
    }
  }
    @keyframes starHide43 {
    0% {
      transform: translate(0, 0);
    }
    100% {
      transform: translate(10rpx, 230rpx);
    }
  }
     @keyframes starHide44 {
    0% {
      transform: translate(0, 0);
    }
    100% {
      transform: translate(30rpx, 230rpx);
    }
  }
}
.star-hide-5 {
  .u-star {
    &:nth-of-type(1) {
      .u-star-act {
        animation: starHide51 0.29s ease forwards;
      }
    }
    &:nth-of-type(2) {
      .u-star-act {
        animation: starHide52 0.29s ease forwards;
      }
    }
    &:nth-of-type(3) {
      .u-star-act {
        animation: starHide53 0.29s ease forwards;
      }
    }
    &:nth-of-type(4) {
      .u-star-act {
        animation: starHide54 0.29s ease forwards;
      }
    }
    &:nth-of-type(5) {
      .u-star-act {
        animation: starHide55 0.29s ease forwards;
      }
    }
    
  }
  @keyframes starHide51 {
    0% {
      transform: translate(0, 0);
    }
    100% {
      transform: translate(-10rpx, 230rpx);
    }
  }
   @keyframes starHide52 {
    0% {
      transform: translate(0, 0);
    }
    100% {
      transform: translate(-10rpx, 230rpx);
    }
  }
    @keyframes starHide53 {
    0% {
      transform: translate(0, 0);
    }
    100% {
      transform: translate(0, 230rpx);
    }
  }
    @keyframes starHide54 {
    0% {
      transform: translate(0, 0);
    }
    100% {
      transform: translate(10rpx, 230rpx);
    }
  }
     @keyframes starHide55 {
    0% {
      transform: translate(0, 0);
    }
    100% {
      transform: translate(10rpx, 230rpx);
    }
  }
}
// ---------------------------------new -end
.u-win-streak {
  opacity: 0;
  font-size: 46rpx;
  color: #fff;
  text-align: center;
  margin-top: 70rpx;
  transition: all 0.4s ease;
}
// 动画集合
.shrink {
  animation: shrink 0.6s ease;
}
.shrinkImg {
  animation: shrinkImg 0.16s ease forwards;
}
.magnify {
  animation: magnify 0.41s ease forwards;
}
.downStar-0 {
  animation: downStar-0 0.58s ease-out forwards;
}
.downStar-l-5{
  animation: downStar-l-5 0.58s ease-out forwards;
}
.downStar-r-5 {
  animation: downStar-r-5 0.58s ease-out forwards;
}
.downStar-l-10{
  animation: downStar-l-10 0.58s ease-out forwards;
}
.downStar-r-10 {
  animation: downStar-r-10 0.58s ease-out forwards;
}
.downStar-l-15 {
  animation: downStar-l-15 0.58s ease-out forwards;
}
.downStar-r-15 {
  animation: downStar-r-15 0.58s ease-out forwards;
}
.numChange {
  animation: numChange 0.6s ease forwards;
}
.hide {
  opacity: 0;
}
.show {
  opacity: 1!important;
}
.showHalf {
  opacity: 0.7;
}
// @keyframes
// 光圈动画
@keyframes shrink {
  0%,
  30.2%,
  62.2% {
    width: 480rpx;
    height: 480rpx;
    opacity: 0.6;
  }
  30%,
  62% {
    width: 280rpx;
    height: 280rpx;
    opacity: 1;
  }
  30.1%,
  62.1% {
    width: 480rpx;
    height: 480rpx;
    opacity: 0;
  }
  92.2%,
  100% {
    width: 340rpx;
    height: 340rpx;
    opacity: 0;
  }
}
//旧图标动画
@keyframes shrinkImg {
  0% {
    transform: translate(-50%, -50%) scale(1);
  }
  100% {
    transform: translate(-50%, -50%) scale(0);
  }
}
//新图标动画
@keyframes magnify {
  0% {
    transform: translate(-50%, -50%) scale(0);
  }
  60%{
    transform: translate(-50%, -50%) scale(1.2);
  }
  100% {
    transform: translate(-50%, -50%) scale(1);
  }
}
// 掉星动画
// 掉星动画
@keyframes downStar-0 {
  0%,
  28.5%,
  57.1% {
    transform: translate(0, 0) skewY(0deg);
    opacity: 1;
  }
  14.8%,
  42.8% {
    transform: translate(0, 0) skewY(-10deg);
    opacity: 1;
  }
  78.5% {
    transform: translate(0, 0) skewY(0deg);
    opacity: 1;
  }
  100% {
    transform: translate(0, 100rpx) skewY(0deg);
    opacity: 0;
  }
}
@keyframes downStar-l-5 {
  0%,
  28.5%,
  57.1% {
    transform: translate(0, 0) skewY(0deg);
    opacity: 1;
  }
  14.8%,
  42.8% {
    transform: translate(0, 0) skewY(-10deg);
    opacity: 1;
  }
  78.5% {
    transform: translate(0, 0) skewY(0deg);
    opacity: 1;
  }
  100% {
    transform: translate(-30rpx, 100rpx) skewY(0deg);
    opacity: 0;
  }
}
@keyframes downStar-r-5 {
  0%,
  28.5%,
  57.1% {
    transform: translate(0, 0) skewY(0deg);
    opacity: 1;
  }
  14.8%,
  42.8% {
    transform: translate(0, 0) skewY(-10deg);
    opacity: 1;
  }
  78.5% {
    transform: translate(0, 0) skewY(0deg);
    opacity: 1;
  }
  100% {
    transform: translate(30rpx, 100rpx) skewY(0deg);
    opacity: 0;
  }
}
@keyframes downStar-l-10 {
  0%,
  28.5%,
  57.1% {
    transform: translate(0, 0) skewY(0deg);
    opacity: 1;
  }
  14.8%,
  42.8% {
    transform: translate(0, 0) skewY(-10deg);
    opacity: 1;
  }
  78.5% {
    transform: translate(0, 0) skewY(0deg);
    opacity: 1;
  }
  100% {
    transform: translate(-25rpx, 100rpx) skewY(0deg);
    opacity: 0;
  }
}
@keyframes downStar-r-10 {
  0%,
  28.5%,
  57.1% {
    transform: translate(0, 0) skewY(0deg);
    opacity: 1;
  }
  14.8%,
  42.8% {
    transform: translate(0, 0) skewY(-10deg);
    opacity: 1;
  }
  78.5% {
    transform: translate(0, 0) skewY(0deg);
    opacity: 1;
  }
  100% {
    transform: translate(25rpx, 100rpx) skewY(0deg);
    opacity: 0;
  }
}
@keyframes downStar-l-15 {
  0%,
  28.5%,
  57.1% {
    transform: translate(0, 0) skewY(0deg);
    opacity: 1;
  }
  14.8%,
  42.8% {
    transform: translate(0, 0) skewY(-10deg);
    opacity: 1;
  }
  78.5% {
    transform: translate(0, 0) skewY(0deg);
    opacity: 1;
  }
  100% {
    transform: translate(-50rpx, 100rpx) skewY(0deg);
    opacity: 0;
  }
}
@keyframes downStar-r-15 {
  0%,
  28.5%,
  57.1% {
    transform: translate(0, 0) skewY(0deg);
    opacity: 1;
  }
  14.8%,
  42.8% {
    transform: translate(0, 0) skewY(-10deg);
    opacity: 1;
  }
  78.5% {
    transform: translate(0, 0) skewY(0deg);
    opacity: 1;
  }
  100% {
    transform: translate(50rpx, 100rpx) skewY(0deg);
    opacity: 0;
  }
}
// 生小段 数字变化
@keyframes numChange {
  0% {
    opacity: 1;
    transform: translateY(-50%) scale(1);
  }
  50% {
    opacity: 0;
    transform: translateY(-50%) scale(0);
  }
  100% {
    opacity: 1;
    transform: translateY(-50%) scale(1);
  }
}

.u-btn{
  position: absolute;
  width: 250rpx;
  height: 78rpx;
  top: 880rpx;
  left: 50%;
  transform: translateX(-50%);
  transition: all .291s ease;
  opacity: 0;
}
</style>
