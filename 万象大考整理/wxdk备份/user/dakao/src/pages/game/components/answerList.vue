<template>
  <div class="ans-box"
       v-if="dataReady"
       :class="{'bule-type':gamePageType==1}">
    <!-- 题型和序号 -->
    <div class="title-type"
         :class="{titleTypeIn:showTitleType}">
      <div class="title-txt rank-icon"
           :class="{
              'rank-icon-word-de':questionType=='德',
              'rank-icon-word-shang':questionType=='商',
              'rank-icon-word-wen':questionType=='文',
              'rank-icon-word-li':questionType=='理',
              'rank-icon-word-yi':questionType=='艺',
              'rank-icon-word-ti':questionType=='体',
              'rank-icon-word-zong':questionType=='综',
            }">
      </div>
      <img class="tt-bg"
           src="/static/title-bg.png"
           alt="">
      <div class="title-number">第{{currentIndex+1}}题</div>
      <img class="tt-double"
           v-if="showDoubleTip"
           src="/static/X2.png">
    </div>
    <!-- 知识书加分  -->
    <div class="b-score-left"
         :class="{'show-score':showLeftBook,'shang':myScore.bookType==2,'wen':myScore.bookType==3,'li':myScore.bookType==4,'yi':myScore.bookType==5,'ti':myScore.bookType==6,'zong':myScore.bookType==7,}">
      <img :src="bookTypeMap[myScore.bookType||2].img"> +{{myScore.bookScore||0}}
      <div class="b-level">{{myScore.bookLevel}}</div>
    </div>
    <div class="b-score-right"
         :class="{'show-score':showRightBook,'shang':otherScore.bookType==2,'wen':otherScore.bookType==3,'li':otherScore.bookType==4,'yi':otherScore.bookType==5,'ti':otherScore.bookType==6,'zong':otherScore.bookType==7,}">
      <img :src="bookTypeMap[otherScore.bookType||2].img"> +{{otherScore.bookScore||0}}
      <div class="b-level">{{otherScore.bookLevel}}</div>
    </div>
    <div class="box-top">
      <!-- 文字 英语题 -->
      <div class="m-word-title"
           v-if="list[currentIndex].modeType==3||list[currentIndex].modeType==4||list[currentIndex].modeType==6"
           :class="{titleIn:showTitle,english:list[currentIndex].modeType==6}">
        <div class="u-word-level"
             v-if="isRank"
             :class="{titleLevelIn:showTitleType,blue:levelList[currentIndex].blue,red:levelList[currentIndex].red,yellow:levelList[currentIndex].yellow,}">
          {{levelList[currentIndex].type}}
        </div>
        {{list[currentIndex].title}}
      </div>
      <!-- 成语 -->
      <div class="m-idiom"
           v-if="list[currentIndex].modeType==5"
           :class="{titleIn:showTitle}">
        <div class="u-word-level"
             v-if="isRank"
             :class="{titleLevelIn:showTitleType,blue:levelList[currentIndex].blue,red:levelList[currentIndex].red,yellow:levelList[currentIndex].yellow,}">
          {{levelList[currentIndex].type}}
        </div>
        <div class="u-word-box">
          <div class="u-word"
               v-for="(item,index) in idiomList[currentIndex]"
               :key="index">{{item}}</div>
        </div>
      </div>

      <!-- 图文 -->
      <div class="m-img"
           v-if="list[currentIndex].modeType==1"
           :class="{titleIn:showTitle}">
        <div class="img-title">
          <div class="img-level"
               :class="{blue:levelList[currentIndex].blue,red:levelList[currentIndex].red,yellow:levelList[currentIndex].yellow}">{{levelList[currentIndex].type}}</div>
          {{list[currentIndex].title}}
        </div>
        <div class="img-box"
             :class="{titleIn:showTitle}">
          <img mode="aspectFit"
               :src="IMGHEAD+list[currentIndex].image"
               alt="">
        </div>
      </div>

      <!-- 诗词类 -->

      <div class="m-poetry"
           v-if="list[currentIndex].modeType==7||list[currentIndex].modeType==8||list[currentIndex].modeType==9||list[currentIndex].modeType==10"
           :class="{'m-poetry-reverce':(list[currentIndex].modeType==8||list[currentIndex].modeType==10),titleIn:showTitle}">
        <div class="u-word-level"
             v-if="isRank"
             :class="{titleLevelIn:showTitleType,blue:levelList[currentIndex].blue,red:levelList[currentIndex].red,yellow:levelList[currentIndex].yellow,}">
          {{levelList[currentIndex].type}}
        </div>
        <div class="u-sentence">{{list[currentIndex].title}}
          <div class="u-symbol"
               v-if="list[currentIndex].modeType==7||list[currentIndex].modeType==9">,</div>
          <div class="u-symbol"
               v-else>。</div>
        </div>
        <div class="u-line">
          <div class="u-line-item"
               v-for="(item,index) in poetryList[currentIndex]"
               :key="index"></div>
          <div class="u-symbol"
               v-if="list[currentIndex].modeType==7||list[currentIndex].modeType==9">。</div>
          <div class="u-symbol"
               v-else>,</div>
        </div>
      </div>

    </div>
    <div class="box-bottom">
      <div class="score-process"
           :class="{processIn:showProcess}">
        <div class="score">{{myScoreNumber}}</div>
        <div class="process"
             :style="myProcess"></div>
        <!-- <img src="/static/rank/bubble.png" alt=""> -->
      </div>

      <!-- 题目选项 -->
      <div class="ans-center"
           v-if="dataReady">
        <div class="select-list"
             :class="{listIn:showList}">
          <div class="select-item"
               :class="{success:item.state=='success',fail:item.state=='fail',wait:item.state=='waitResoponse',noSelect:item.noSelect,big:list[currentIndex].modeType==4}"
               v-for="(item,index) in list[currentIndex].optionList"
               :key="index"
               @click="select(item,index)">
            <div v-if="item.state=='success'&&item.userOne"
                 class="yes-arrow-l"></div>
            <div v-if="item.state=='fail'&&item.userOne"
                 class="err-arrow-l"></div>
            <div class="select-item-txt">
              <img v-if="item.state=='success'&&item.userOne&&list[currentIndex].modeType!=4"
                   class="left-img yes"
                   src="/static/rank/yes.png"
                   alt="">
              <img v-if="item.state=='fail'&&item.userOne&&list[currentIndex].modeType!=4"
                   class="left-img err"
                   src="/static/rank/error.png"
                   alt="">
              <div class="txt"
                   v-if="list[currentIndex].modeType!=4">
                {{item.name}}
              </div>
              <img v-if="list[currentIndex].modeType==4&&item.name=='对'&&!item.state&&gamePageType==0"
                   class="select-img select-yes"
                   src="/static/rank/b_yes.png"
                   alt="">
              <img v-if="list[currentIndex].modeType==4&&item.name=='错'&&!item.state&&gamePageType==0"
                   class="select-img select-err"
                   src="/static/rank/b_error.png"
                   alt="">

              <img v-if="list[currentIndex].modeType==4&&item.name=='对'&&!item.state&&gamePageType==1"
                   class="select-img select-yes"
                   src="/static/rank/d_yes.png"
                   alt="">
              <img v-if="list[currentIndex].modeType==4&&item.name=='错'&&!item.state&&gamePageType==1"
                   class="select-img select-err"
                   src="/static/rank/d_error.png"
                   alt="">

              <img v-if="list[currentIndex].modeType==4&&item.name=='对'&&item.state"
                   class="select-img select-yes"
                   src="/static/rank/yes_w.png"
                   alt="">
              <img v-if="list[currentIndex].modeType==4&&item.name=='错'&&item.state"
                   class="select-img select-err"
                   src="/static/rank/error_w.png"
                   alt="">

              <img v-if="item.state=='success'&&item.userTwo&&list[currentIndex].modeType!=4"
                   class="right-img yes"
                   src="/static/rank/yes.png"
                   alt="">
              <img v-if="item.state=='fail'&&item.userTwo&&list[currentIndex].modeType!=4"
                   class="right-img err"
                   src="/static/rank/error.png"
                   alt="">
            </div>
            <div v-if="item.state=='success'&&item.userTwo"
                 class="yes-arrow-r"></div>
            <div v-if="item.state=='fail'&&item.userTwo"
                 class="err-arrow-r"></div>
          </div>
          <div class="creat-info"
               :class="{'showCreat':showList}">
            ——本题由
            <img v-if="!list[currentIndex].createUserUrl" src="/static/default-head.png">
            <img v-else :src="list[currentIndex].createUserUrl">

            <div>{{list[currentIndex].createUserName}}</div> 贡献——
          </div>
        </div>

      </div>

      <!-- 用户二计分 -->
      <div class="score-process"
           :class="{processIn:showProcess}">
        <div class="score">{{otherScoreNumber}}</div>
        <div class="process"
             :style="otherProcess"
             :class="{otherAnsFail:otherAnsFail}"></div>
        <!-- <img src="/static/rank/bubble.png" alt=""> -->
      </div>
    </div>

    <!-- 答错遮罩 -->
    <div class="fail-mask"
         v-if="showFailMask"></div>
    <!-- </div> -->

  </div>

</template>

<script>
import { mapState, mapMutations } from "vuex";

export default {
  props: [
    "questionList",
    "currentIndex",
    "otherScore",
    "myScore",
    "canNext",
    "oldGame",
    "netWork",
    "initTime"
  ],
  data() {
    return {
      timer: null,
      time: 10000,
      timeOut: false,
      list: null,
      myScoreNumber: 0,
      myProcess: "height:0%;",
      otherScoreNumber: 0,
      otherProcess: "height:0%;",
      otherAns: null,
      otherAnsOk: false,
      answerMap: {},
      result: { myScore: 0, otherScore: 0 }, //答题结果,
      idiomList: [], //成语题目列表
      poetryList: [], //诗词长度
      //动画相关
      isFirst: true,

      showTitle: false,
      showTitleType: false,
      showList: false,
      showProcess: false,
      showFailMask: false, //错误红色遮罩
      animationTime: 1,
      showDoubleTip: false,
      otherAnsFail: false,
      levelList: [],
      imgLevelList: [],
      dataReady: false,
      answerTimer:null,
      // 结果动画
      showAntSuccess: false,
      showAntFail: false,
      showAntBalance: false,
      // 连对动画
      showCrLeft: false,
      showCrRight: false,
      showLeftBook: false,
      showRightBook: false,
      bookTypeMap: {
        2: { img: "/static/icon/shang.png" },
        3: { img: "/static/icon/wen.png" },
        4: { img: "/static/icon/li.png" },
        5: { img: "/static/icon/yi.png" },
        6: { img: "/static/icon/ti.png" },
        7: { img: "/static/icon/zong.png" }
      }
    };
  },
  watch: {
    canNext() {
      this.showOtherAns(); //展示双方答案
    },
    time(val) {
      if (val <= 0) {
        this.stopTimer();
        this.timeOut = true;
        console.log("timeout");
      }
    },
    initTime(val) {
      this.timeOut=false;
      this.time = val;
    },

    currentIndex(val) {
      if (!this.netWork) {
        this.stopTimer();
        // 更新题目动画
        this.showTitleTag();
      } else {
        console.log("重连题目更新",val)
        this.oldGameShow();
      }
    },
    otherScore(val) {
      if (!val.right) {
        this.otherAnsFail = true;
        setTimeout(() => {
          this.otherAnsFail = false;
        }, 500);
      }
      this.otherScoreNumber = val.score;
      this.otherProcess = `height:${this.otherScoreNumber / 13.8}%;`;
      this.result.otherScore = this.otherScoreNumber;
      if (val.continueRightNum >= 2) {
        this.showCrRight = true;
        setTimeout(() => {
          this.showCrRight = false;
        }, 1500);
      }
      if (val.bookScore > 0) {
        this.showRightBook = true;
        setTimeout(() => {
          this.showRightBook = false;
        }, 2500);
      }
    },
    myScore(val) {
      console.log("myScore",val)
      this.myScoreNumber = val.score;
      this.myProcess = `height:${this.myScoreNumber / 13.8}%;`;
      this.result.myScore = this.myScoreNumber;
      clearTimeout(this.answerTimer);
      this.showMyAnswer(val);
      if (val.continueRightNum >= 2) {
        this.showCrLeft = true;
        setTimeout(() => {
          this.showCrLeft = false;
        }, 1500);
      }
      if (val.bookScore > 0) {
        this.showLeftBook = true;
        setTimeout(() => {
          this.showLeftBook = false;
        }, 2500);
      }
    }
  },
  computed: {
    ...mapState([
      "userOneState",
      "gamePageType",
      "userTwoState",
      "isRank",
      "IMGHEAD",
      "globalSoundPlayer",
      "socketState"
    ]),
    answer() {
      //当前题目答案
      if (this.list && this.list[this.currentIndex]) {
        return this.list[this.currentIndex].answer;
      }
    },
    questionType() {
      if (this.list) {
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
  methods: {
    ...mapMutations(["setUserOneState"]),
    audioPlay(type) {
      if (this.globalSoundPlayer.volume == 0) {
        return false;
      }
      if (type == "success") {
        this.globalSoundPlayer.src =
          "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/right.mp3";
      } else {
        this.globalSoundPlayer.src =
          "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/wrong.mp3";
      }
      this.globalSoundPlayer.play();
      setTimeout(() => {
        this.globalSoundPlayer.src = "";
      }, 3000);
    },
    showMyAnswer(res){
      for(let i=0;i<this.list[res.index].optionList.length;i++){
        if(this.list[res.index].optionList[i].sortId==res.answer){
          let obj =JSON.parse(JSON.stringify(this.list[res.index]));
          if(res.right==true){
            this.audioPlay("success");
            obj.optionList[i].state="success";
            this.$set(this.list,res.index,Object.assign(this.list[res.index],obj));
            break;
          }else{
            this.audioPlay("fail");
            obj.optionList[i].state="fail";
            this.$set(this.list,res.index,Object.assign(this.list[res.index],obj));
            this.showFailMask = true;
            setTimeout(() => {
              this.showFailMask = false;
            }, 400);
            break;
          }
        }
      }
    },
    // 可以发送type6
    sendNextToService() {
      this.showList = true;
      this.setUserOneState(false);
      this.startTimer();

      this.$emit("nextQuestion");
      console.log("ansList emit nextQuestion", this.currentIndex);
      if (this.currentIndex != 4) {
        this.showProcess = true;
      }
    },
    startTimer() {
      this.timeOut=false;
      this.timer = setInterval(() => {
        this.time -= 100;
      }, 100);
    },
    stopTimer() {
      clearInterval(this.timer);
      this.time = 10000;
    },
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
          this.sendNextToService();
        }, 1000);
      }, 1600);
    },
    oldGameShow() {
      console.log('oldGame',this.currentIndex)
      this.timeOut = false;
      this.showTitle = true;
      this.showProcess = true;
      // this.sendNextToService();
    },
    getList() {
      this.list = JSON.parse(JSON.stringify(this.questionList)); //深拷贝 问题数据
      for (let i = 0; i < this.list.length; i++) {
        // 处理选项
        for (let j = 0; j < this.list[i].optionList.length; j++) {
          this.list[i].optionList[j].state = null;
          this.list[i].optionList[j].name = this.list[i].optionList[
            j
          ].name.replace(/\s+/g, "");
          this.list[i].optionList[j].isSelect = false;
        }
        // 判断成语题 生成题目列表

        if (this.list[i].modeType == 5) {
          let list = this.list[i].title.replace("_", "?").split("");
          this.idiomList.push(list);
        } else {
          this.idiomList.push(0);
        }

        if (
          this.list[i].modeType == 7 ||
          this.list[i].modeType == 8 ||
          this.list[i].modeType == 9 ||
          this.list[i].modeType == 10
        ) {
          let list = this.list[i].title.replace("_", "?").split("");
          this.poetryList.push(list);
        } else {
          this.poetryList.push(0);
        }
      }

      this.dataReady = true;
      this.getRatioList(this.list);
    },
    getRatioList(list) {
      for (let item of list) {
        if (item.rightRatio >= 66) {
          this.levelList.push({ type: "简单", blue: true });
          this.imgLevelList.push({ type: "简", blue: true });
        }
        if (item.rightRatio >= 33 && item.rightRatio < 66) {
          this.levelList.push({ type: "一般", yellow: true });
          this.imgLevelList.push({ type: "中", yellow: true });
        }
        if (item.rightRatio >= 0 && item.rightRatio < 33) {
          this.levelList.push({ type: "困难", red: true });
          this.imgLevelList.push({ type: "难", red: true });
        }
      }
    },
    shake() {
      wx.vibrateShort({
        success: () => {
          console.log("点击震动");
        }
      });
    },
    select(item, index) {
      //TODO 检查答案，处理选中逻辑
      console.log(
        "选项是否选过",
        item.select,
        "此题是否答过",
        this.userOneState,
        "是否超时",
        this.timeOut
      );
      if ((!item.select) && !this.userOneState && !this.timeOut) {
        this.shake();
        let answerMap = {};
        for (
          let i = 0;
          i < this.list[this.currentIndex].optionList.length;
          i++
        ) {
          answerMap[
            this.list[this.currentIndex].optionList[i].name
          ] = this.list[this.currentIndex].optionList[i].sortId;
        }
        let obj = {
          type: 4,
          data: {
            answer: answerMap[item.name],
            index: this.currentIndex,
            seconds: this.time, //到 questionBox在赋值
            timeout: false, // 默认false, qsb 赋值
            uid: 0 //qbs 赋值
          }
        };
        item.select = true;
        item.userOne = true;
        item.state='waitResoponse';
        this.answerTimer=setTimeout(() => {
          wx.showToast({
            title: "网络较差,请在良好环境下游戏",
            icon:"none",
          });
        }, 3000);
        if (item.sortId == this.list[this.currentIndex].optionSortId) {

          this.$emit("success", obj); //提交答案 send type4
        } else {
     
          this.$emit("fail", obj);
        }
        this.setUserOneState(true);
        this.stopTimer(); //计时器停止
      }
    },
    showOtherAns() {
      //双方都答题后 才显示 B用户数据  则此行为即为 可下一题状态 不需要在判定
      //显示对方选项
      let list = this.list[this.currentIndex].optionList;
      let _otherScore = this.otherScore || {};
      // 显示对手答案
      for (let i = 0; i < list.length; i++) {
        if (
          _otherScore.right &&
          list[i].sortId == _otherScore.rightAnswer &&
          _otherScore.index == this.currentIndex
        ) {
          list[i].state = "success";
          list[i].select = true;
          list[i].userTwo = true;
        } else if (
          !_otherScore.right &&
          list[i].sortId == _otherScore.answer &&
          _otherScore.index == this.currentIndex
        ) {
          list[i].state = "fail";
          list[i].select = true;
          list[i].userTwo = true;
        }
      }

      //展示题目正确选项 隐藏未选择选项
      for (let i = 0; i < list.length; i++) {
        if (
          list[i].state != "success" &&
          list[i].state != "fail" &&
          list[i].sortId != this.list[this.currentIndex].optionSortId
        ) {
          list[i].noSelect = true;
        } else if (
          list[i].state != "success" &&
          list[i].state != "fail" &&
          list[i].sortId == this.list[this.currentIndex].optionSortId
        ) {
          list[i].state = "success";
          list[i].select = true;
        }
      }

      setTimeout(() => {
        this.$emit("showSelectComplete", this.result);
      }, 400);

      // 上一题退场动画
      setTimeout(() => {
        this.showTitle = false;
        this.showList = false;
      }, 1500);
    }
  },
  mounted() {
    this.getList();
    if (this.oldGame) {
      this.oldGameShow();
    } else {
      this.showTitleTag();
    }
  },
  onUnload() {
    this.stopTimer();
  }
};
</script>

<style lang="scss" scoped>
@import url("../../../scss/icon.scss");
@mixin triangle($direction, $size, $borderColor) {
  content: "";
  height: 0;
  width: 0;

  @if $direction==top {
    border-bottom: $size solid $borderColor;
    border-left: $size dashed $transparent;
    border-right: $size dashed transparent;
  } @else if $direction==right {
    border-left: $size solid $borderColor;
    border-top: $size dashed transparent;
    border-bottom: $size dashed transparent;
  } @else if $direction==bottom {
    border-top: $size solid $borderColor;
    border-left: $size dashed transparent;
    border-right: $size dashed transparent;
  } @else if $direction== left {
    border-right: $size solid $borderColor;
    border-top: $size dashed transparent;
    border-bottom: $size dashed transparent;
  }
}
.ans-box {
  width: 100%;
  height: 960rpx;
  position: relative;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
}

.box-bottom,
.box-top {
  display: flex;
  justify-content: center;
  align-items: center;
}
.box-top {
  height: 470rpx;
  position: relative;
}

.cr-l,
.cr-r {
  position: absolute;
  z-index: 10;
  transition: all 0.2s ease;
  z-index: 10;
  top: 220rpx;
  .cr-num {
    width: 60rpx;
    height: 73rpx;
  }
  .cr-bg {
    width: 256rpx;
    height: 69rpx;
  }
  .cr-info {
    position: absolute;
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    color: #fff;
    font-weight: bold;
    top: -22rpx;
    right: 0rpx;
    width: 100%;
    box-sizing: border-box;

    .cr-score {
      font-weight: normal;
      color: #fff3ae;
      margin-right: 50rpx;
      font-size: 26rpx;
    }
  }
}
.cr-l {
  left: 0;
  transform: translateX(-260rpx);
  .cr-num {
    margin-left: 24rpx;
  }
}
.cr-r {
  right: 0;
  transform: translateX(260rpx);
  .cr-info {
    .cr-score {
      margin-right: 30rpx;
    }
  }
  .cr-num {
    margin-left: 40rpx;
  }
}

.show-cr {
  transform: translate(0) !important;
}
.l45 {
  left: 45rpx !important;
}
.ans-center {
  min-height: 100rpx;
  transition: all 0.4s ease;
  align-self: flex-start;
}
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
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  font-size: 36rpx;
  color: #fff;
  opacity: 0;
  transform: translateY(-20rpx);
  transition: all 0.5s ease;
  border-radius: 20rpx;
  box-sizing: border-box;
  padding: 0 40rpx;
  font-weight: bold;
}
.english {
  font-size: 64rpx;
  font-weight: bold;
}
.u-word-level {
  width: 80rpx;
  height: 46rpx;
  line-height: 46rpx;
  border-radius: 8rpx;
  color: #fff;
  font-size: 26rpx;
  text-align: center;
  position: absolute;
  top: -60rpx;
  left: 50%;
  transform: translateX(-50%);
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
    margin-bottom: 10rpx;
    .img-level {
      width: 80rpx;
      text-align: center;
      border-radius: 8rpx;
      font-size: 26rpx;
      color: #fff;
      margin-right: 12rpx;
    }
  }
  .img-box {
    height: 380rpx;
    margin: 0;
    img {
      max-height: 380rpx;
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
  .u-word-level {
    position: relative;
    // margin-bottom: 24rpx;
    top: -24rpx;
  }
  .u-word {
    width: 124rpx;
    height: 124rpx;
    border: 4rpx dashed #fff;
    border-radius: 8rpx;
    box-sizing: border-box;
    font-size: 80rpx;
    font-family: "宋体";
    font-weight: bold;
    color: #fff;
    margin-right: 30rpx;
    &:last-child {
      margin-right: 0;
    }
  }
}

.m-poetry {
  min-width: 380rpx;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-size: 60rpx;
  color: #fff;
  opacity: 0;
  transform: translateY(-20rpx);
  transition: all 0.5s ease;
  .u-word-level {
    top: -60rpx;
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
    height: 60rpx;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: flex-end;
    position: relative;
    .u-line-item {
      width: 60rpx;
      height: 4rpx;
      background: #fff;
      margin-right: 6rpx;
    }
  }
  .u-symbol {
    width: 60rpx;
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
}

.spec {
  width: 44rpx;
  height: 44rpx;
  line-height: 44rpx;
  text-align: center;
  border-radius: 50%;
}
.blue {
  background: #02debb;
}
.red {
  background: #fa537f;
}
.yellow {
  background: #fa9c4d;
}
.img-box {
  width: 500rpx;
  height: 278rpx;
  margin: 0 auto 30rpx auto;
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

.double-tip {
  position: absolute;
  width: 522rpx;
  height: 46rpx;
  background: rgba(#67c29f, 0.23);
  border-radius: 1000rpx;
  left: 50%;
  transform: translate(-50%, 0rpx) scale(0);
  opacity: 0;
  top: 0;
  &:before,
  &:after {
    position: absolute;
    top: 0;
    left: 50%;
    transform: translateX(-50%);
    height: 46rpx;
    border-radius: 1000rpx;
  }
  &:before {
    content: "";
    width: 424rpx;
    background: rgba(#67c29f, 0.46);
  }
  &:after {
    width: 320rpx;
    background: #67c29f;
    content: "双倍得分";
    text-align: left;
    padding-left: 46rpx;
    box-sizing: border-box;
    line-height: 46rpx;
    font-size: 36rpx;
    color: #fff;
  }
  .x2 {
    font-size: 72rpx;
    color: #fff;
    font-weight: bold;
    -webkit-text-stroke: 2rpx #67c29f;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(40%, -50%);
    z-index: 5;
  }
}

.select-box {
  margin-top: 80rpx;
  display: flex;
  justify-content: center;
}

.select-list {
  height: 490rpx;
  margin: 0 60rpx;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: relative;
  .creat-info {
    width: 100%;
    position: absolute;
    left: 50%;
    transform: translate(-50%, 20rpx);
    bottom: -80rpx;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 26rpx;
    font-weight: bold;
    color: #7c74b9;
    transition: all 0.2s ease;
    opacity: 0;
    img {
      width: 58rpx;
      height: 58rpx;
      margin: 0 10rpx;
      border-radius: 50%;
      border: 4rpx solid #fff;
      box-sizing: border-box;
    }
    div {
      margin-right: 10rpx;
      color: #85d85d;
    }
  }
  .showCreat {
    opacity: 1;
    transform: translate(-50%, 0);
  }
  .select-item {
    width: 500rpx;
    height: 106rpx;
    position: relative;
    font-size: 40rpx;
    border: 2rpx solid #17c4c9;
    color: #7cf7e4;
    border-radius: 10rpx;
    box-sizing: border-box;
    opacity: 0;
    transform: scale(0);
    // filter: drop-shadow(0 0 6px rgba(0, 0, 0, 0.4));
    transition: all 0.4s ease;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 20rpx;
    &:last-child {
      margin-bottom: 0;
    }
    .select-item-txt {
      position: relative;
      text-align: center;
      font-weight: bold;
      .txt {
        min-width: 380rpx;
      }
    }
    .yes {
      width: 40rpx;
      height: 35rpx;
    }
    .err {
      width: 31rpx;
      height: 35rpx;
    }

    .yes-arrow-l,
    .yes-arrow-r,
    .err-arrow-l,
    .err-arrow-r {
      position: absolute;
      top: 50%;
      transform: translateY(-50%);
    }
    .yes-arrow-l {
      @include triangle(left, 26rpx, #85d85d);
      left: -22rpx;
    }
    .yes-arrow-r {
      @include triangle(right, 26rpx, #85d85d);
      right: -22rpx;
    }
    .err-arrow-l {
      @include triangle(left, 26rpx, #ff217b);
      left: -22rpx;
    }
    .err-arrow-r {
      @include triangle(right, 26rpx, #ff217b);
      right: -22rpx;
    }
    .select-img {
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%) !important;
    }
    .select-yes {
      width: 101rpx;
      height: 82rpx;
    }
    .select-err {
      width: 80rpx;
      height: 82rpx;
    }
    img {
      position: absolute;
      top: 50%;
      transform: translateY(-50%);
    }
    .left-img {
      left: -40rpx;
    }
    .right-img {
      right: -40rpx;
    }
  }
  .big {
    height: 190rpx;
    margin-bottom: 30rpx;
  }
  .wait{
    transform: scale(.9)!important;
    background: #ccc;
    color: #999;
  }
  .success,
  .fail {
    color: #fff;
    animation: btnScale 0.4s ease;
  }

  .success {
    background: #85d85d;
    border-color: #85d85d;
    // box-shadow: 0 0 10rpx 0rpx  rgba(#85d85d,.9);
  }
  .fail {
    background: #ff217b;
    border-color: #ff217b;
    // box-shadow: 0 0 20rpx 0rpx  rgba(#ff217b,.9);
  }
  .noSelect {
    transform: scale(0) !important;
    opacity: 0 !important;
  }
}

.fail-mask {
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 50;
  background: rgba(red, 0.2);
}
@keyframes btnScale {
  50% {
    transform: scale(1.2);
  }
  100% {
    transform: scale(1);
  }
}
.listIn {
  .select-item {
    transform: scale(1);
    opacity: 1;
  }
}
.processIn {
  transform: translateX(0) !important;
}
.score-process {
  box-sizing: border-box;
  width: 24rpx;
  height: 476rpx;
  border: 2rpx solid #17c4c9;
  padding: 4rpx;
  border-radius: 1000rpx;
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  margin-top: 20rpx;
  transition: all 0.4s ease;
  transform: translateX(-120rpx);
  &:last-child {
    transform: translateX(120rpx);
  }
  & > img {
    position: absolute;
    width: 112rpx;
    height: 102rpx;
    bottom: -30rpx;
    left: 50%;
    transform: translateX(-50%);
  }
  .score {
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    font-size: 42rpx;
    color: #fff;
    line-height: 1;
    top: -60rpx;
  }
  .process {
    width: 100%;
    background: #7ef4ec;
    border-radius: 1000rpx;
    height: 0;
    transition: all 0.3s linear;
  }
}
.otherAnsFail {
  background: #fd5c5c !important;
}

// 新增风格
.bule-type {
  .ans-center {
    .select-list {
      .select-item {
        border: none;
        background: #fff;
        color: #4056a9;
      }
      .success {
        background: #02debb;
        border-color: #02debb;
        color: #fff;
      }
      .fail {
        background: #fa537f;
        border-color: #fa537f;
        color: #fff;
      }
      .yes-arrow-l {
        @include triangle(left, 26rpx, #02debb);
      }
      .yes-arrow-r {
        @include triangle(right, 26rpx, #02debb);
      }
      .err-arrow-l {
        @include triangle(left, 26rpx, #fa537f);
      }
      .err-arrow-r {
        @include triangle(right, 26rpx, #fa537f);
      }
    }
  }

  .score-process {
    border-color: #2246b8;
    background: #2246b8;
    .process {
      background: #4fe7f4;
    }
  }
}

.b-score-left,
.b-score-right {
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  top: 84rpx;
  width: 194rpx;
  height: 70rpx;
  box-sizing: border-box;
  border: 4rpx solid #fff;
  transition: all 0.2s ease;
  color: #fff;
  font-size: 36rpx;
  font-weight: bold;
  background: #37d3c5;
  z-index: 10;
  img {
    width: 68rpx;
    height: 68rpx;
    margin-right: 16rpx;
  }
  .b-level {
    position: absolute;
    left: 68rpx;
    top: 30rpx;
    width: 26rpx;
    height: 26rpx;
    border-radius: 50%;
    background: #fff;
    font-size: 22rpx;
    font-weight: bold;
    display: flex;
    justify-content: center;
    align-items: center;
  }
}

.b-score-left {
  left: 0;
  transform: translateX(-200rpx);
  border-top-right-radius: 1000rpx;
  border-bottom-right-radius: 1000rpx;
  border-left: 0;
}

.b-score-right {
  right: 0;
  transform: translateX(200rpx);
  border-top-left-radius: 1000rpx;
  border-bottom-left-radius: 1000rpx;
  border-right: 0;
}

.show-score {
  transform: translateX(0);
}
.wen {
  background: #37d3c5;
  .b-level {
    color: #1ba8b5;
  }
}
.li {
  background: #6ec74a;
  .b-level {
    color: #1ba8b5;
  }
}
.yi {
  background: #e575d0;
  .b-level {
    color: #bf1396;
  }
}
.ti {
  background: #5378ce;
  .b-level {
    color: #1b47b0;
  }
}
.shang {
  background: #e6a349;
  .b-level {
    color: #d0894b;
  }
}
.zong {
  background: #e97b5d;
  .b-level {
    color: #c44a2d;
  }
}
</style>
