<template>
  <div class="result sky-bg-3"
       v-if="dataReady">
    <div class="inner">
      <div class="r-head">
        <div class="r-user-head"
             :class="{'success-type':myInfo.abilityInfo.score>otherInfo.abilityInfo.score||oRun}">
          <img class="crown"
               src="/static/crown.png">
          <img class="r-user-img"
               :src="myInfo.basicInfo.avatarUrl"
               alt="">
          <div class="r-user-name">{{myInfo.basicInfo.nickName}}</div>
          <div class="r-user-score">{{myInfo.abilityInfo.score||0}}</div>
        </div>
        <!-- 中间的胜利 -->
        <div class="r-r">
          <img v-if="myInfo.abilityInfo.score>otherInfo.abilityInfo.score||oRun"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/success-head.png"
               alt="">
          <img v-if="myInfo.abilityInfo.score<otherInfo.abilityInfo.score"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/fail-head.png"
               alt="">
          <img v-if="(myInfo.abilityInfo.score==otherInfo.abilityInfo.score)&&!oRun"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/balance-head.png"
               alt="">
          <div v-if="myInfo.abilityInfo.continueWinNum>1">
            <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/feather_l.png"
                 alt=""> {{myInfo.abilityInfo.continueWinNum}}连胜
            <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/feather_r.png"
                 alt="">
          </div>
        </div>
        <div class="r-user-head"
             :class="{'success-type':otherInfo.abilityInfo.score>myInfo.abilityInfo.score}">
          <img class="crown"
               src="/static/crown.png">
          <div class="r-user-img-box">
            <img class="r-user-img"
                 v-if="otherInfo.boot"
                 :src="IMGHEAD+otherInfo.basicInfo.avatarUrl">
            <img class="r-user-img"
                 v-else
                 :src="otherInfo.basicInfo.avatarUrl">
            <div class="r-user-run"
                 v-if="oRun">逃跑</div>
            <!-- <div class="r-user-run" v-if="true">逃跑</div> -->
          </div>
          <div class="r-user-name">{{otherInfo.basicInfo.nickName}}</div>
          <div class="r-user-score">{{otherInfo.abilityInfo.score||0}}</div>
        </div>
      </div>

      <div class="game-table">
        <div class="t-head">
          <img :src="IMGHEAD+myInfo.abilityInfo.paragraphImage"> {{myInfo.abilityInfo.paragraphName}}
          <div class="vs">VS</div>
          {{otherInfo.abilityInfo.paragraphName}}
          <img :src="IMGHEAD+otherInfo.abilityInfo.paragraphImage">
        </div>
        <div class="t-line"
             v-for="(item,index) in myAnsList"
             :key="index">
          <div>
            <img v-if="item.type==1"
                 src="/static/yes.png">
            <img v-if="item.type==2"
                 src="/static/err.png">
            <img v-if="item.type==0"
                 src="/static/none.png">
            <div class="t-score">{{item.score}}
              <div class="b-score"
                   v-if="item.score>0">+{{item.bookScore}}</div>
            </div>
          </div>
          <div>
            <div class="t-score">{{otherAnsList[index].score}}
              <div class="b-score"
                   v-if="otherAnsList[index].score>0">+{{otherAnsList[index].bookScore}}</div>
            </div>
            <img v-if="otherAnsList[index].type==1"
                 src="/static/yes.png">
            <img v-if="otherAnsList[index].type==2"
                 src="/static/err.png">
            <img v-if="otherAnsList[index].type==0"
                 src="/static/none.png">
          </div>
        </div>
        <!-- combo -->
        <!-- <div class="t-line">
        <div>
          <img style="opacity:0!important;" src="/static/num-0.png">
          <div class="t-score">{{myInfo.abilityInfo.bookTotalScore}}</div>
        </div>
        <div>
          <div class="t-score">{{otherInfo.abilityInfo.bookTotalScore}}</div>
          <img style="opacity:0!important;" src="/static/num-0.png">
        </div>
      </div> -->
        <!-- 分割 -->
        <div class="t-divition">
          <div>{{questionType[gameRecord.Result.subjectList[0].parentType]}}</div>
          <div>{{questionType[gameRecord.Result.subjectList[1].parentType]}}</div>
          <div>{{questionType[gameRecord.Result.subjectList[2].parentType]}}</div>
          <div>{{questionType[gameRecord.Result.subjectList[3].parentType]}}</div>
          <div>{{questionType[gameRecord.Result.subjectList[4].parentType]}}</div>
        </div>
      </div>
    </div>
    <div class="btn-box">
      <div class="btn-game"
           @click="reStart">
        再玩一局
      </div>
      <div v-if="!oRun"
           class="btn-back"
           @click="toReview">
        本局回顾
      </div>
      <!-- <div @click="getGIft">领取奖励</div> -->
      <div v-if="myInfo.abilityInfo.score<=otherInfo.abilityInfo.score"
           class="btn-book"
           @click="toBook">
        知识升级
      </div>
      <button v-if="myInfo.abilityInfo.score>otherInfo.abilityInfo.score"
              plain="true"
              class="btn-share"
              open-type="share">
        分享战绩
      </button>
    </div>
    <div class="ad-box">
      <ad unit-id="adunit-aa03c7a8d0838a81"></ad>
    </div>
    <!-- <div class="advice" @click="showModal=true">
      <img src="/static/advice-big.png"> 举报对手
    </div> -->

    <!-- 举报弹框 -->
    <div class="advice-modal"
         v-if="showModal">
      <div class="mask"></div>
      <div class="modal">
        <div class="close"
             @click="showModal = false"></div>
        <div class="head">
          举报玩家
          <img src="/static/modal-bg.png"
               alt="">
        </div>
        <div class="content">请您选择相应的举报类型,我们会尽快审查和处理,您的反馈是对我们最大的支持。</div>
        <div class="select-item"
             v-for="(item,index) in selectList"
             :key="index"
             @click="selectAdvice(item)">
          <div class="select-box">
            <img v-if="adviceType==item.type"
                 src="/static/gou.png"
                 alt="">
          </div>
          {{item.text}}
        </div>
        <div class="advice-submit"
             @click="submitAdvice">提交</div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import { setStorage, clearStorage } from "@/utils/index";
let dataStack = [];
export default {
  onShareAppMessage: function(res) {
    return {
      title: "快来看我智战群雄",
      path: `/pages/entry/index?userId=${this.appInfo.userId}`
    };
  },
  data() {
    return {
      myInfo: {},
      otherInfo: {},
      myAnsList: [
        { score: 0, type: 0, bookScore: 0 },
        { score: 0, type: 0, bookScore: 0 },
        { score: 0, type: 0, bookScore: 0 },
        { score: 0, type: 0, bookScore: 0 },
        { score: 0, type: 0, bookScore: 0 }
      ],
      otherAnsList: [
        { score: 0, type: 0, bookScore: 0 },
        { score: 0, type: 0, bookScore: 0 },
        { score: 0, type: 0, bookScore: 0 },
        { score: 0, type: 0, bookScore: 0 },
        { score: 0, type: 0, bookScore: 0 }
      ],
      questionType: {
        1: "德",
        2: "商",
        3: "文",
        4: "理",
        5: "艺",
        6: "体",
        7: "综"
      },
      oRun: false,
      selectList: [
        {
          type: "1",
          text: "对方疑似作弊"
        },
        {
          type: "2",
          text: "对方头像和昵称违规"
        }
      ],
      adviceType: "1",
      dataReady: false,
      showModal: false
    };
  },
  computed: {
    ...mapState(["IMGHEAD", "gameRecord", "appInfo"])
  },
  methods: {
    getGIft() {
      this.fly.post("rank/receiveGood").then(res => {
        console.log(res);
      });
    },
    getRunType(key) {
      return new Promise((resolve, reject) => {
        wx.getStorage({
          key: key,
          success: res => {
            this.oRun = res.data.oRun;
            resolve(res.data);
          },
          fail: res => {
            reject("获取缓存失败");
          }
        });
      });
    },
    getResult() {
      let subject = this.gameRecord.Result.subjectList;
      let userList = this.gameRecord.Result.userList;

      for (let u = 0; u < userList.length; u++) {
        if (userList[u].basicInfo.userId == this.appInfo.userId) {
          this.myInfo = userList[u];
        } else {
          this.otherInfo = userList[u];
        }

        for (let i = 0; i < subject.length; i++) {
          if (subject[i].answerList) {
            for (let j = 0; j < subject[i].answerList.length; j++) {
              if (
                userList[u].basicInfo.userId == subject[i].answerList[j].uid &&
                userList[u].basicInfo.userId == this.appInfo.userId
              ) {
                this.myAnsList[i].score = subject[i].answerList[j].score;
                this.myAnsList[i].bookScore =
                  subject[i].answerList[j].bookScore;

                if (subject[i].answerList[j].right) {
                  this.myAnsList[i].type = 1;
                } else if (
                  !subject[i].answerList[j].right &&
                  subject[i].answerList[j].answer != "-1"
                ) {
                  this.myAnsList[i].type = 2;
                }
              } else if (
                userList[u].basicInfo.userId == subject[i].answerList[j].uid &&
                userList[u].basicInfo.userId != this.appInfo.userId
              ) {
                this.otherAnsList[i].score = subject[i].answerList[j].score;
                this.otherAnsList[i].bookScore =
                  subject[i].answerList[j].bookScore;
                if (subject[i].answerList[j].right) {
                  this.otherAnsList[i].type = 1;
                } else if (
                  !subject[i].answerList[j].right &&
                  subject[i].answerList[j].answer != "-1"
                ) {
                  this.otherAnsList[i].type = 2;
                }
              }
            }
          }
        }
      }
      this.dataReady = true;
    },
    toReview() {
      this.$router.push("/pages/gameReview/index");
    },
    toBook() {
      this.$router.push("/pages/book/index");
    },
    reStart() {
      // this.fly.post('/rank/beforeMatch').then(res=>{
      //    if(res.data.Result.isBegin==1){
      //        this.$router.replace("/pages/game/index?rankGame=true&&isNew=true");
      //     }else{
      //       wx.showToast({title:"体力不足",icon:"none"});
      //     }
      // })
      this.$router.back();
    },
    selectAdvice(item) {
      this.adviceType = item.type;
    },
    submitAdvice() {
      this.fly
        .post("/feedBackUser", {
          userId: this.otherInfo.basicInfo.userId,
          types: this.adviceType
        })
        .then(res => {
          wx.showToast({
            title: "提交成功,谢谢！",
            icon: "none"
          });
          this.showModal = false;
        });
    }
  },
  async mounted() {
    await this.getResult("rankGameResult");
    await this.getRunType("rankGameResult");
  },
  onLoad(options) {
    dataStack.push({
      ...this.$data
    });
    console.log("dataStack", dataStack);
  },
  onUnload() {
    this.dataReady = false;
    Object.assign(this.$data, dataStack.pop());
    this.myAnsList = [
      { score: 0, type: 0, bookScore: 0 },
      { score: 0, type: 0, bookScore: 0 },
      { score: 0, type: 0, bookScore: 0 },
      { score: 0, type: 0, bookScore: 0 },
      { score: 0, type: 0, bookScore: 0 }
    ];
    this.otherAnsList = [
      { score: 0, type: 0, bookScore: 0 },
      { score: 0, type: 0, bookScore: 0 },
      { score: 0, type: 0, bookScore: 0 },
      { score: 0, type: 0, bookScore: 0 },
      { score: 0, type: 0, bookScore: 0 }
    ];
    clearStorage("rankGameResult");
  }
};
</script>

<style lang="scss" scoped>
.result {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
  box-sizing: border-box;
  overflow: scroll;
  .success-type {
    .r-user-score {
      color: #09ddb8 !important;
    }
    .crown {
      opacity: 1 !important;
    }
  }
}
.inner {
  padding: 50rpx 44rpx 0 44rpx;
}
.r-head {
  width: 100%;
  display: flex;
  justify-content: space-between;
  position: relative;
  margin-bottom: 40rpx;
  .r-user-head {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 176rpx;
    height: 290rpx;
    color: #fff;
    position: relative;
    &:first-child {
      .crown {
        left: 15rpx;
      }
    }
    &:last-child {
      .crown {
        right: 15rpx;
        transform: rotate(70deg);
      }
    }
    .crown {
      position: absolute;
      top: -15rpx;
      width: 55rpx;
      height: 54rpx;
      opacity: 0;
    }
    .r-user-img-box {
      width: 120rpx;
      height: 120rpx;
      position: relative;
    }
    .r-user-img {
      width: 120rpx;
      height: 120rpx;
      box-sizing: border-box;
      border: 4rpx solid #9491ae;
      border-radius: 50%;
      margin-bottom: 12rpx;
    }
    .r-user-run {
      position: absolute;
      width: 120rpx;
      height: 120rpx;
      line-height: 120rpx;
      border-radius: 50%;
      text-align: center;
      background: rgba(#4f4b48, 0.7);
      font-size: 34rpx;
      color: #ffff00;
      top: 0;
      left: 0;
    }
    .r-user-name {
      width: 176rpx;
      text-align: center;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      font-size: 28rpx;
      color: #fff;
      margin-bottom: 30rpx;
    }
    .r-user-score {
      font-size: 54rpx;
      font-weight: bold;
      color: #fff;
    }
  }
}

.r-r {
  position: absolute;
  left: 50%;
  top: -50rpx;
  transform: translateX(-50%);
  & > img {
    width: 380rpx;
    height: 336rpx;
  }
  div {
    position: absolute;
    left: 50%;
    top: 300rpx;
    width: 100%;
    transform: translateX(-50%);
    font-size: 30rpx;
    color: #fff;
    display: flex;
    justify-content: center;
    align-items: center;
    img {
      width: 53rpx;
      height: 52rpx;
    }
  }
}

.game-table {
  width: 100%;
  position: relative;
  color: #fff;
  .t-head {
    width: 100%;
    height: 84rpx;
    background: rgba(#7efbe3, 0.5);
    font-size: 32rpx;
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    img {
      width: 124rpx;
      height: 124rpx;
      position: absolute;
      top: -50rpx;
      &:first-child {
        left: 0;
      }
      &:last-child {
        right: 0;
      }
    }
    .vs {
      font-size: 50rpx;
      font-weight: bold;
      background: linear-gradient(180deg, #fff, #4ec5fe);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      margin: 0 30rpx;
    }
  }
  .t-line {
    width: 100%;
    height: 84rpx;
    display: flex;
    justify-content: space-between;
    &:nth-of-type(1n + 1) {
      background: rgba(#7efbe3, 0.2);
    }
    &:nth-of-type(2n + 2) {
      background: rgba(#7efbe3, 0.1);
    }
    & > div {
      width: 254rpx;
      height: 100%;
      display: flex;
      align-items: center;
      position: relative;
      img {
        width: 72rpx;
        height: 71rpx;
        position: absolute;
        top: 50%;
        transform: translateY(-50%);
      }
      &:first-child {
        left: 15rpx;
        padding-left: 90rpx;
        img {
          left: 0;
        }
      }
      &:last-child {
        right: 15rpx;
        img {
          right: 0;
        }
        .t-score {
          margin-left: 15rpx;
        }
      }

      .t-score {
        width: 110rpx;
        text-align: left;
        font-size: 40rpx;
        font-weight: bold;
        position: relative;
      }
      .b-score {
        position: absolute;
        width: 100rpx;
        text-align: left;
        right: -60rpx;
        top: 50%;
        transform: translateY(-50%);
        color: #09ddb8;
      }
    }
    .hide {
      opacity: 0;
    }
  }
  .t-divition {
    width: 76rpx;
    position: absolute;
    left: 50%;
    top: 84rpx;
    transform: translateX(-50%);
    background: rgba(#617192, 0.5);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    div {
      display: flex;
      justify-content: center;
      align-items: center;
      width: 100%;
      height: 84rpx;
      color: #fff;
      font-size: 32rpx;
      font-weight: bold;
    }
  }
}

.advice {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  // position: absolute;
  // bottom: 20rpx;
  // left: 44rpx;
  img {
    width: 44rpx;
    height: 36rpx;
    margin-right: 10rpx;
  }
  font-size: 22rpx;
  color: #00c6d9;
}

.advice-modal,
.advice-modal .mask {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 10;
}

.mask {
  background: rgba(#000, 0.7);
}

.modal {
  position: absolute;
  top: 223rpx;
  left: 50%;
  transform: translateX(-50%);
  width: 588rpx;
  height: 656rpx;
  border-radius: 8rpx;
  padding: 8rpx;
  box-sizing: border-box;
  background: #fff;
  z-index: 15;

  .close {
    width: 96rpx;
    height: 96rpx;
    position: absolute;
    right: -30rpx;
    top: -30rpx;
    background: #fff;
    border-radius: 50%;
    z-index: 5;
    &:before,
    &:after {
      content: "";
      position: absolute;
      left: 50%;
      top: 50%;
      width: 60rpx;
      height: 6rpx;
      background: #ef8a7f;
    }
    &:before {
      transform: translate(-50%, -50%) rotate(45deg);
    }
    &:after {
      transform: translate(-50%, -50%) rotate(-45deg);
    }
  }
  .head {
    font-size: 52rpx;
    color: #fff;
    font-weight: bold;
    line-height: 1;
    margin-bottom: 40rpx;
    padding: 25rpx 0;
    text-align: center;
    background: #ef8a7f;
    border-top-left-radius: 10rpx;
    border-top-right-radius: 10rpx;
    overflow: hidden;
    position: relative;
    img {
      width: 182rpx;
      height: 130rpx;
      right: -30rpx;
      bottom: -30rpx;
      position: absolute;
    }
  }
  .content {
    font-size: 30rpx;
    color: #000;
    text-align: center;
    line-height: 1.5em;
    margin-bottom: 33rpx;
    padding: 0 60rpx;
    box-sizing: border-box;
    margin-bottom: 40rpx;
  }
  .select-item {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    margin-bottom: 40rpx;
    font-size: 36rpx;
    color: #ef8a7f;
    padding: 0 60rpx;
    box-sizing: border-box;
    .select-box {
      width: 50rpx;
      height: 50rpx;
      border: 4rpx solid #aaaaaa;
      border-radius: 4rpx;
      box-sizing: border-box;
      position: relative;
      margin-right: 15rpx;
      img {
        width: 60rpx;
        height: 44rpx;
        position: absolute;
        top: -5rpx;
        right: -20rpx;
      }
    }
  }
  .advice-submit {
    width: 480rpx;
    height: 100rpx;
    line-height: 100rpx;
    margin: 0 auto;
    text-align: center;
    font-size: 44rpx;
    color: #fff;
    background: #85d85d;
    border-radius: 26rpx;
  }
}

.name-box {
  width: 100%;
  display: flex;
  justify-content: space-between;
  padding: 0 170rpx;
  box-sizing: border-box;
  position: absolute;
  left: 0;
  bottom: 25rpx;
  font-size: 28rpx;
  color: #fff;
  opacity: 0;
  transition: all 0.35s ease;
  .u-head-name {
    width: 100rpx;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
  }
}
.show {
  opacity: 1 !important;
}
//顏色
.green {
  background-color: #67c29f;
}

.gray {
  background-color: #d9d9d9;
}

.red {
  background-color: #eb7c7c;
}

//勝利內容
.btn-box {
  display: flex;
  align-items: center;
  justify-content: space-around;
  margin-bottom: 50rpx;
  font-size: 34rpx;
  color: #fff;
  margin-top: 50rpx;
}
.btn-game,
.btn-back,
.btn-share,
.btn-book {
  height: 86rpx;
  line-height: 86rpx;
  text-align: center;
  border-radius: 30rpx;
  &:active {
    transform: scale(0.95);
  }
}
.btn-game {
  width: 298rpx;
  background: #ff217b;
}
.btn-back {
  width: 182rpx;
  background: #85d85d;
}
.btn-book,
.btn-share {
  width: 182rpx;
  background: #29abe2;
}
.btn-share {
  padding: 0;
  margin: 0;
  color: #fff;
  font-size: 34rpx;
}
.ad-box {
  width: 100%;
}
</style>
