<template>
  <div class="m-title" v-if="dataReady">
    <!-- 题型和序号 -->
    <div class="title-type" :class="{titleTypeIn:showTitleType}">
      <div class="title-txt rank-icon" :class="{
              'rank-icon-word-de':questionType=='德',
              'rank-icon-word-shang':questionType=='商',
              'rank-icon-word-wen':questionType=='文',
              'rank-icon-word-li':questionType=='理',
              'rank-icon-word-yi':questionType=='艺',
              'rank-icon-word-ti':questionType=='体',
              'rank-icon-word-zong':questionType=='综',
            }">
      </div>
      <img class="tt-bg" src="/static/title-bg.png" alt="">
      <div class="title-number">第{{currentIndex+1}}题</div>
      <img class="tt-double" v-if="showDoubleTip" src="/static/X2.png">
    </div>
    <!-- 文字 英语题 -->
    <div class="m-word-title" v-if="subjectList[currentIndex].modeType==3||subjectList[currentIndex].modeType==4||subjectList[currentIndex].modeType==6" :class="{titleIn:showTitle,english:subjectList[currentIndex].modeType==6}">
      <div class="u-word-level" :class="{titleLevelIn:showTitleType,blue:levelList[currentIndex].blue,red:levelList[currentIndex].red,yellow:levelList[currentIndex].yellow,}">
        {{levelList[currentIndex].type}}
      </div>
      {{subjectList[currentIndex].title}}
    </div>
    <!-- 成语 -->
    <div class="m-idiom" v-if="subjectList[currentIndex].modeType==5" :class="{titleIn:showTitle}">
      <div class="u-word-box">
        <div class="u-word" v-for="(item,index) in idiomList[currentIndex]" :key="index">{{item}}</div>
      </div>
      <div class="u-word-level" :class="{titleLevelIn:showTitleType,blue:levelList[currentIndex].blue,red:levelList[currentIndex].red,yellow:levelList[currentIndex].yellow,}">
        {{levelList[currentIndex].type}}
      </div>
    </div>

    <!-- 图文 -->
    <div class="m-img" v-if="subjectList[currentIndex].modeType==1" :class="{titleIn:showTitle}">
      <div class="img-title">
        <div class="img-level" :class="{blue:imgLevelList[currentIndex].blue,red:imgLevelList[currentIndex].red,yellow:imgLevelList[currentIndex].yellow}">{{imgLevelList[currentIndex].type}}</div>
        {{list[currentIndex].title}}
      </div>
      <div class="img-box" :class="{titleIn:showTitle}">
        <img mode="aspectFit" :src="IMGHEAD+subjectList[currentIndex].image" alt="">
      </div>
    </div>

       <div class="m-poetry" v-if="list[currentIndex].modeType==7||list[currentIndex].modeType==8||list[currentIndex].modeType==9||list[currentIndex].modeType==10" :class="{'m-poetry-reverce':(subjectList[currentIndex].modeType==8||subjectList[currentIndex].modeType==10),titleIn:showTitle}">
        <div class="u-word-level"  
                                  :class="{titleLevelIn:showTitleType,blue:levelList[currentIndex].blue,red:levelList[currentIndex].red,yellow:levelList[currentIndex].yellow,}">
                                  {{levelList[currentIndex].type}}
        </div>
        <div class="u-sentence">{{subjectList[currentIndex].title}}<div class="u-symbol" v-if="subjectList[currentIndex].modeType==7||subjectList[currentIndex].modeType==9">,</div><div class="u-symbol" v-else>。</div></div> 
        <div class="u-line"><div class="u-line-item" v-for="(item,index) in poetryList[currentIndex]" :key="index"></div><div class="u-symbol" v-if="subjectList[currentIndex].modeType==7||subjectList[currentIndex].modeType==9">。</div><div class="u-symbol" v-else>,</div></div>
      </div> 
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  props: ["list","idiomList","poetryList","levelList","imgLevelList","currentIndex","titleOff",],
  data() {
    return {
      dataReady:false,
      showTitleType: false,
      showTitle: false,
      showDoubleTip: false,
      subjectList:[],
    };
  },
  computed: {
    ...mapState(["IMGHEAD"]),
    questionType() {
      if (this.list&&this.list.length>0) {
        let type = this.list[this.currentIndex].parentType;
        let obj = {
          1: "德",
          2: "商",
          3: "文",
          4: "理",
          5: "艺",
          6: "体",
          7: "综"
        };
        return obj[type];
      }
    }
  },
  watch:{
    list(){
      this.subjectList=this.list;
    },
    titleOff(){
      this.showTitle=false;
    },
    currentIndex(value){
      this.showTitleTag();
    },
  },
  methods: {
 
    showTitleTag() {
      //题目类型
      this.showTitleType = true;
      if (this.currentIndex == 4) {
        //双倍提示
        this.showDoubleTip = true;
      }
      setTimeout(() => {
        //题目类型隐藏
        this.showTitleType = false;
        this.showDoubleTip = false;
      }, 1500);

      setTimeout(() => {
        //显示题目
        this.showTitle = true;
        setTimeout(() => {
          this.$emit('antEnd');
        }, 1000);
        // setTimeout(() => {
        //   this.animationStep1(false);
        // }, 1000);
      }, 1600);
    },
  },
  mounted(){
    if(this.list){
      this.subjectList=this.list;
      this.dataReady=true;
    }
    this.showTitleTag();
  },
};
</script>

<style lang="scss" scoped>
@import url("../../scss/icon.scss");
.imgTitle {
  .title {
    font-size: 34rpx;
    height: 114rpx;
    margin: 19rpx auto 11rpx auto;
  }
}
.m-word-title {
  position: relative;
  width: 650rpx;
  height: 188rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  font-size: 32rpx;
  color: #000;
  opacity: 0;
  transform: translateY(-20rpx);
  transition: all 0.5s ease;
  background: #fff;
  box-shadow: 0 0 30rpx -4rpx #fff;
  border-radius: 20rpx;
  box-sizing: border-box;
  padding: 0 60rpx;
  font-weight: bold;
}
.english {
  font-size: 64rpx;
  font-weight: bold;
}
.u-word-level {
  width: 138rpx;
  height: 46rpx;
  line-height: 46rpx;
  border-radius: 12rpx;
  color: #fff;
  font-size: 26rpx;
  text-align: center;
  position: absolute;
  bottom: -23rpx;
  left: 50%;
  transform: translateX(-50%);
  // box-shadow:0 0 30rpx -4rpx #fff;
}

.m-img {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  opacity: 0;
  transform: translateY(-20rpx);
  transition: all 0.5s ease;
  .img-title {
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    color: #fff;
    font-size: 32rpx;
    margin: 24rpx 0 10rpx;
    .img-level {
      width: 46rpx;
      height: 46rpx;
      line-height: 46rpx;
      text-align: center;
      border-radius: 50%;
      font-size: 26rpx;
      color: #fff;
      margin-right: 12rpx;
    }
  }
  .img-box {
    img {
      max-height: 200rpx;
      border-radius: 20rpx;
    }
  }
}
.u-word-box,
.idiom,
.u-word {
  display: flex;
  justify-content: center;
  align-items: center;
}
.m-idiom {
  flex-direction: column;
  opacity: 0;
  transform: translateY(-20rpx);
  transition: all 0.5s ease;
  margin-top: 40rpx;
  .u-word-level {
    position: relative;
  }
  .u-word {
    width: 122rpx;
    height: 122rpx;
    border: 4rpx dashed #fff;
    border-radius: 8rpx;
    box-sizing: border-box;
    font-size: 80rpx;
    font-family: "宋体";
    color: #fff;
    margin-right: 30rpx;
    &:last-child {
      margin-right: 0;
    }
  }
}

.m-poetry {
  // min-width: 380rpx;
  margin-top: 30rpx;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-size: 48rpx;
  color: #fff;
  opacity: 0;
  transform: translateY(-20rpx);
  transition: all 0.5s ease;
  .u-word-level {
    bottom: -80rpx;
  }
  .u-sentence {
    display: flex;
    justify-content: center;
    align-items: flex-end;
    width: 100%;
    text-align: center;
    letter-spacing: 8rpx;
    position: relative;
  }
  .u-line {
    width: 100%;
    display: flex;
    height: 48rpx;
    justify-content: center;
    align-items: flex-end;
    position: relative;
    .u-line-item {
      width: 50rpx;
      height: 4rpx;
      background: #fff;
      margin-right: 6rpx;
    }
  }
  .u-symbol {
    width: 50rpx;
    text-align: left;
    position: absolute;
    right: -50rpx;
    top: 0;
  }
}
.m-poetry-reverce {
  flex-direction: column-reverse;
  .u-line {
    margin-bottom: 40rpx;
  }
  .u-word-level{
    bottom:-80rpx;
  }

}


.spec {
  width: 44rpx;
  height: 44rpx;
  line-height: 44rpx;
  text-align: center;
  border-radius: 50%;
}
.blue {
  background: #0071b9;
}
.red {
  background: #ed1e79;
}
.yellow {
  background: #f8ad3b;
}
.img-box {
  width: 500rpx;
  height: 200rpx;
  opacity: 0;
  transition: all 0.8s ease;
  img {
    width: 100%;
    height: 100%;
  }
}
.titleIn {
  transform: translateX(0) !important;
  opacity: 1 !important;
}

.title-type {
  position: absolute;
  left: 50%;
  top: 0;
}
.title-type {
  width: 400rpx;
  height: 460rpx;
  transform: translate(-50%, 0rpx) scale(0);
  font-size: 60rpx;
  color: #fff;
  text-align: center;
  .title-txt {
    width: 74rpx;
    height: 73rpx;
    position: absolute;
    z-index: 2;
    left: 50%;
    top: 150rpx;
    transform: translateX(-50%);
  }
  .tt-bg {
    position: absolute;
    width: 368rpx;
    height: 276rpx;
    top: 80rpx;
    left: 50%;
    transform: translateX(-50%);
  }
  .tt-double {
    position: absolute;
    width: 160rpx;
    height: 121rpx;
    left: 50%;
    transform: translateX(-50%);
    bottom: -40rpx;
  }
  .title-number {
    position: absolute;
    left: 50%;
    top: 252rpx;
    width: 100%;
    font-size: 50rpx;
    color: #fff;
    text-align: center;
    font-weight: bold;
    transform: translate(-50%, 0rpx);
  }
}

.titleTypeIn {
  animation: ttIn 1.28s ease;
}
.titleNumberIn {
  animation: tnIn 1.28s ease;
}
.titleLevelIn {
  animation: tlIn 1.28s ease;
}

// .imgTitle {
//   .titleTypeIn {
//     animation: ttIn2 1.28s ease;
//   }
//   .titleNumberIn {
//     animation: tnIn2 1.28s ease;
//   }
//   .titleLevelIn {
//     animation: tlIn2 1.28s ease;
//   }
//   .doubuleTipIn {
//     animation: dtIn 1.28s ease;
//   }
// }
@keyframes ttIn {
  0% {
    transform: translate(-50%, 0rpx) scale(0);
  }
  33% {
    transform: translate(-50%, 0rpx) scale(1);
    opacity: 1;
  }
  66% {
    transform: translate(-50%, 0rpx) scale(1);
    opacity: 1;
  }
  100% {
    transform: translate(-50%, 0rpx) scale(0);
    opacity: 0;
  }
}
</style>
