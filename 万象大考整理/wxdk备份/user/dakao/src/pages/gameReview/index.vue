<template>
  <div class="wrap sky-bg">
 
    <div class="desc">
         <img class="title"
         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/title.png">
    <div class="advice"
         @click="advice">
      <img src="/static/icon/gantan.png"
           alt=""> 题目纠错
    </div>
    <div class="like-box">
      <div v-if="likeList[current]"
           @click="updateLikeStatu(1)">
        <img v-if="likeList[current]&&likeList[current].status==1"
             src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/gp.png">
        <img v-else
             src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/gp-null.png"> ({{likeList[current].goodNum}})
      </div>
      <div v-if="likeList[current]"
           @click="updateLikeStatu(0)">
        <img v-if="likeList[current]&&likeList[current].status==0"
             src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/bp.png">
        <img v-else
             src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/bp-null.png">  ({{likeList[current].badNum}})
      </div>
    </div>
      <div class="desc-text"
           v-if="dataReady">{{subjectList[current].description}}</div>
    </div>
    <div class="quesIndex">
      <div class="q-txt">
        第{{current+1}}/{{subjectList.length}}题
      </div>
    </div>
    <div class="swiper-wrap"
         v-if="dataReady">
      <img class="left-btn"
           src="/static/arrow.png"
           @click="throttleProxy(1)">
      <img class="right-btn"
           src="/static/arrow.png"
           @click="throttleProxy(2)">
      <swiper :current="current"
              @animationfinish="swiperChange">
        <swiper-item v-for="(subject,index) in subjectList"
                     :key="index">
          <!-- <div class="content">{{subject.title}}</div> -->
          <div class="box-top">
            <div class="m-word-title"
                 v-if="subject.modeType==3||subject.modeType==4||subject.modeType==6"
                 :class="{titleIn:showTitle,english:subject.modeType==6}">
              <!-- <div class="u-word-level"
                   :class="{titleLevelIn:showTitleType,blue:levelList[current].blue,red:levelList[current].red,yellow:levelList[current].yellow,}">
                {{levelList[current].type}}
              </div> -->
              {{subject.title}}
            </div>

            <div class="m-idiom"
                 v-if="subject.modeType==5"
                 :class="{titleIn:showTitle}">
              <!-- <div class="u-word-level"
                   :class="{titleLevelIn:showTitleType,blue:levelList[current].blue,red:levelList[current].red,yellow:levelList[current].yellow,}">
                {{levelList[current].type}}
              </div> -->
              <div class="u-word-box">
                <div class="u-word"
                     v-for="(item,wordIndex) in idiomList[current]"
                     :key="wordIndex">{{item}}</div>
              </div>
            </div>

            <div class="m-img"
                 v-if="subject.modeType==1">
              <div class="img-title">
                <!-- <div class="img-level"
                     :class="{blue:imgLevelList[current].blue,red:imgLevelList[current].red,yellow:imgLevelList[current].yellow}">{{imgLevelList[current].type}}</div> -->
                {{subject.title}}
              </div>
              <div class="img-box">
                <img mode="aspectFit"
                     :src="IMGHEAD+subject.image"
                     alt="">
              </div>
            </div>

            <!-- 诗词类 -->

            <div class="m-poetry"
                 v-if="subject.modeType==7||subject.modeType==8||subject.modeType==9||subject.modeType==10"
                 :class="{'m-poetry-reverce':(subject.modeType==8||subject.modeType==10),titleIn:showTitle}">
              <!-- <div class="u-word-level"
                   :class="{titleLevelIn:showTitleType,blue:levelList[current].blue,red:levelList[current].red,yellow:levelList[current].yellow,}">
                {{levelList[current].type}}
              </div> -->
              <div class="u-sentence">{{subject.title}}
                <div class="u-symbol"
                     v-if="subject.modeType==7||subject.modeType==9">,</div>
                <div class="u-symbol"
                     v-else>。</div>
              </div>
              <div class="u-line">
                <div class="u-line-item"
                     v-for="(poetry,poetryIndex) in poetryList[current]"
                     :key="poetryIndex"></div>
                <div class="u-symbol"
                     v-if="subject.modeType==7||subject.modeType==9">。</div>
                <div class="u-symbol"
                     v-else>,</div>
              </div>
            </div>
          </div>

          <div class="select-list">
            <div class="select-item"
                 :class="{'judge-type':subject.modeType==4,success:item.sortId==subject.optionSortId,fail:((!myInfo.ansList[current].right&&myInfo.ansList[current].answer==item.sortId)||(!oInfo.ansList[current].right&&oInfo.ansList[current].answer==item.sortId&&!from))}"
                 v-for="(item,indexKey) in subject.optionList"
                 :key="indexKey">
              <div v-if="myInfo.ansList[current].right&&myInfo.ansList[current].answer==item.sortId"
                   class="yes-arrow-l"></div>
              <div v-if="!myInfo.ansList[current].right&&myInfo.ansList[current].answer==item.sortId"
                   class="err-arrow-l"></div>
              <div class="select-item-txt">
                <img v-if="myInfo.ansList[current].right&&myInfo.ansList[current].answer==item.sortId&&subject.modeType!=4"
                     class="left-img yes"
                     src="/static/rank/yes.png"
                     alt="">
                <img v-if="!myInfo.ansList[current].right&&myInfo.ansList[current].answer==item.sortId&&subject.modeType!=4"
                     class="left-img err"
                     src="/static/rank/error.png"
                     alt="">
                <div class="txt"
                     v-if="subject.modeType!=4">
                  {{item.name}}
                </div>
                <img v-if="subject.modeType==4&&item.name=='对'&&(myInfo.ansList[current].answer!=item.sortId&&oInfo.ansList[current].answer!=item.sortId)"
                     class="select-img select-yes"
                     src="/static/rank/b_yes.png"
                     alt="">
                <img v-if="subject.modeType==4&&item.name=='错'&&(myInfo.ansList[current].answer!=item.sortId&&oInfo.ansList[current].answer!=item.sortId)"
                     class="select-img select-err"
                     src="/static/rank/b_error.png"
                     alt="">

                <img v-if="subject.modeType==4&&item.name=='对'&&(myInfo.ansList[current].answer==item.sortId||oInfo.ansList[current].answer==item.sortId||item.sortId==subject.optionSortId)"
                     class="select-img select-yes"
                     src="/static/rank/yes_w.png"
                     alt="">
                <img v-if="subject.modeType==4&&item.name=='错'&&(myInfo.ansList[current].answer==item.sortId||oInfo.ansList[current].answer==item.sortId||item.sortId==subject.optionSortId)"
                     class="select-img select-err"
                     src="/static/rank/error_w.png"
                     alt="">

                <img v-if="oInfo.ansList[current].right&&oInfo.ansList[current].answer==item.sortId&&subject.modeType!=4&&!from"
                     class="right-img yes"
                     src="/static/rank/yes.png"
                     alt="">
                <img v-if="!oInfo.ansList[current].right&&oInfo.ansList[current].answer==item.sortId&&subject.modeType!=4&&!from"
                     class="right-img err"
                     src="/static/rank/error.png"
                     alt="">
              </div>
              <div v-if="!from&&oInfo.ansList[current].right&&oInfo.ansList[current].answer==item.sortId"
                   class="yes-arrow-r"></div>
              <div v-if="!from&&!oInfo.ansList[current].right&&oInfo.ansList[current].answer==item.sortId"
                   class="err-arrow-r"></div>
            </div>
          </div>
        </swiper-item>
      </swiper>

    </div>
    <div class="creat-info"
         :class="{'showCreat':true}">
      ——本题由
      <img v-if="subjectList[current]&&subjectList[current].createUserUrl"
           :src="subjectList[current].createUserUrl">
      <img v-else
           src="/static/default-head.png">
      <div v-if="subjectList[current]&&subjectList[current].createUserName">{{subjectList[current].createUserName}}</div> 贡献——
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import { emoji } from "@/config/dataFile";

//TODO
//1.一页当一个item  item包括用户信息， 当前题目分数，选项，正确与否
export default {
  data() {
    return {
      dataReady: false,
      current: 0,
      from: null,
      userList: [],
 
      dataToBakEnd: {}, //状态列表
      myInfo: {
        ansList: [],
        basicInfo: {}
      },
      oInfo: {
        ansList: [],
        basicInfo: {}
      },
      subjectList: [],
      likeList: [], //喜欢列表
      levelList: [],
      idiomList: [], //成语题目列表
      poetryList: [],
      imgLevelList: [],
      msgList: [],
      msgTimer: null,
      showMsgData: { userNmae: "", msg: "" },
      emoji: emoji,
      initType: 1,
      showTimer:null,
    };
  },
  watch: {
    msgList(list) {
      if (list.length == 0) {
        clearInterval(this.msgTimer);
        this.msgTimer = null;
        return false;
      }
      if (list.length > 0 && this.msgTimer == null) {
        this.msgTimer = setInterval(() => {
          this.showMsgData = list[0];
          this.msgList.splice(0, 1);
        }, 1000);
      }
    }
  },
  computed: {
    ...mapState(["gameRecord", "appInfo", "IMGHEAD", "deviceInfo"]) //gameRecord
  },
  methods: {
    ...mapMutations({
      connect: "socketStore/connect",
      addMessageListener: "socketStore/addMessageListener",
      removeMessageListener: "socketStore/removeMessageListener",
      send: "socketStore/send",
      close: "socketStore/close"
    }),
    initData(type) {
      let gameData;
      this.initType = 2;
      gameData = JSON.parse(JSON.stringify(this.gameRecord.Result));
        this.userList = gameData.userList;
        this.subjectList = gameData.subjectList;
        if (this.from == "room") {
         this.userList = gameData.room.userList;
         this.subjectList = gameData.game.subjectList;
        }
        // 筛选出 题目喜好
        if (type == 1) {
          for (let i = 0; i < this.subjectList.length; i++) {
            for (
              let k = 0;
              k < this.subjectList[i].likeStatusList.length;
              k++
            ) {
              if (
                this.subjectList[i].likeStatusList[k].userId ==
                this.appInfo.userId
              ) {
                this.likeList[i] = {
                  status: this.subjectList[i].likeStatusList[k].likeStatus,
                  goodNum: this.subjectList[i].likeNum,
                  badNum: this.subjectList[i].noLikeNum
                };
                this.dataToBakEnd[this.subjectList[i].id] = this.subjectList[
                  i
                ].likeStatusList[k].likeStatus;
              }
            }
          }
        }
      // }
      this.getRatioList(this.subjectList);
      this.classifyUserInfo(this.userList);
      this.getUserAnserInfo(this.subjectList);
      this.getIdiomList(this.subjectList);

      setTimeout(() => {
        this.dataReady = true;
      }, 0);
    },
    handMessage(message) {
      if (message.Type == 15) {
        let emojiInfo = JSON.parse(message.Result);
        this.msgList.push({
          userName: emojiInfo.userName,
          msg: this.emoji[emojiInfo.index].title
        });
        // this.showEmoji(message);
      }
    },
    swiperChange(event) {
      this.current = event.mp.detail.current;
    },
    pre() {
      if (this.current != 0) {
        this.current--;
      }
    },
    next() {
      if (this.current != this.subjectList.length-1) {
        this.current++;
      }
    },
    // 获取题目难度列表
    getRatioList(list) {
      try {
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
      } catch (err) {}
    },
    //序列化成语题目 和 诗词
    getIdiomList(list) {
      // 判断成语题 生成题目列表
      for (let i = 0; i < list.length; i++) {
        if (list[i].modeType == 5) {
          let wordList = list[i].title.replace("_", "?").split("");
          this.idiomList.push(wordList);
        } else {
          this.idiomList.push(0);
        }
        if (
          list[i].modeType == 7 ||
          list[i].modeType == 8 ||
          list[i].modeType == 9 ||
          list[i].modeType == 10
        ) {
          let arr = list[i].title.replace("_", "?").split("");
          this.poetryList.push(arr);
        } else {
          this.poetryList.push(0);
        }
      }
    },
    classifyUserInfo(userList) {
      for (let i = 0; i < userList.length; i++) {
        //分类用户信息
        if (this.appInfo.userId == userList[i].basicInfo.userId) {
          this.myInfo = userList[i];
          this.myInfo.ansList = [
            { answer: -1, right: false },
            { answer: -1, right: false },
            { answer: -1, right: false },
            { answer: -1, right: false },
            { answer: -1, right: false }
          ];
        } else {
          this.oInfo = userList[i];
          this.oInfo.ansList = [
            { answer: -1, right: false },
            { answer: -1, right: false },
            { answer: -1, right: false },
            { answer: -1, right: false },
            { answer: -1, right: false }
          ];
          if (userList[i].boot) {
            //机器人头像地址 加域名
            this.oInfo.basicInfo.avatarUrl =
              this.IMGHEAD + this.oInfo.basicInfo.avatarUrl;
          }
        }
      }
    },
    // 获取双方答题信息
    getUserAnserInfo(list) {
      for (let i = 0; i < list.length; i++) {
        //题目列表 没有回答数据
        if (list[i].answerList) {
          for (let j = 0; j < list[i].answerList.length; j++) {
            //题目里的回答列表
            if (list[i].answerList[j].uid == this.appInfo.userId) {
              //分类推入 我的 或者 他的对象中
              this.myInfo.ansList[i] = list[i].answerList[j];
            } else {
              this.oInfo.ansList[i] = list[i].answerList[j];
            }
          }
        }
      }
    },
    updateLikeStatu(statusType) {
      if (this.likeList[this.current].status != statusType) {
        if (statusType == 1) {
          this.$set(
            this.likeList,
            this.current,
            Object.assign(this.likeList[this.current], {
              goodNum: this.likeList[this.current].goodNum + 1
            })
          );
          if (this.likeList[this.current].status == 0) {
            this.$set(
              this.likeList,
              this.current,
              Object.assign(this.likeList[this.current], {
                badNum: this.likeList[this.current].badNum - 1
              })
            );
          }
        } else {
          this.$set(
            this.likeList,
            this.current,
            Object.assign(this.likeList[this.current], {
              badNum: this.likeList[this.current].badNum + 1
            })
          );
          if (this.likeList[this.current].status == 1) {
            this.$set(
              this.likeList,
              this.current,
              Object.assign(this.likeList[this.current], {
                goodNum: this.likeList[this.current].goodNum - 1
              })
            );
          }
        }
        this.$set(
          this.likeList,
          this.current,
          Object.assign(this.likeList[this.current], { status: statusType })
        );
        this.dataToBakEnd[this.subjectList[this.current].id] = statusType;
      } else {
        if (statusType == 1) {
          this.$set(
            this.likeList,
            this.current,
            Object.assign(this.likeList[this.current], {
              goodNum: this.likeList[this.current].goodNum - 1
            })
          );
        } else {
          this.$set(
            this.likeList,
            this.current,
            Object.assign(this.likeList[this.current], {
              badNum: this.likeList[this.current].badNum - 1
            })
          );
        }
        this.$set(
          this.likeList,
          this.current,
          Object.assign(this.likeList[this.current], { status: -1 })
        );
        this.dataToBakEnd[this.subjectList[this.current].id] = -1;
      }
    },
    updateLikeList() {
      let handle = false;
      for (var item in this.dataToBakEnd) {
        if (item != -1) {
          handle = true;
        } else {
          handle = false;
        }
      }

      if (handle) {
        this.fly
          .post("/subject/updateLikeStatus", {
            likeList: JSON.stringify(this.dataToBakEnd).toString()
          })
          .then(res => {
            console.log("更新喜好列表", res);
          });
      }
    },
    throttle(fn, delay) {
      clearTimeout(this.timer);
      this.timer = setTimeout(() => {
        fn();
      }, delay);
    },
    throttleProxy(str) {
      if (str == 1) {
        this.throttle(this.pre, 200);
      } else {
        this.throttle(this.next, 200);
      }
    },
    advice() {
      this.$router.push(`/pages/questionAdvice/index?current=${this.current}&&from=${this.from}`);
    }
  },
  onHide() {
    this.updateLikeList();
  },
  onUnload() {
    this.current = 0;
    this.idiomList = [];
    this.subjectList = [];
    this.levelList = [];
    this.idiomList = []; //成语题目列表
    this.imgLevelList = [];
    this.headList = [[], [], [], []];
    this.updateLikeList();
    this.dataToBakEnd = {};
    this.showTimer=setTimeout(() => {
      this.initType=1;
    }, 30000);
    this.dataReady = false;
  },
  mounted() {},
  onShow() {
    clearTimeout(this.showTimer);
    this.from = this.$root.$mp.query.from;
    this.initData(this.initType);
  }
};
</script>

<style lang="scss" scoped>
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
.wrap {
  padding: 105rpx 40rpx 0 40rpx;
  display: block;
  height: auto;
  // box-sizing: border-box;
  // justify-content: flex-start;
}
.head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  .my-head,
  .o-head {
    width: 280rpx;
    height: 92rpx;
    line-height: 92rpx;
    font-size: 32rpx;
    font-weight: bold;
    color: #fff;
    padding: 0 34rpx;
    box-sizing: border-box;
    background: #494776;
    position: relative;
    border-radius: 1000rpx;
    .head-img-box {
      position: absolute;
      top: 50%;
      transform: translateY(-50%);
      border-radius: 50%;
      border: 4rpx solid #221f56;
      width: 122rpx;
      height: 122rpx;
    }
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      border: 6rpx solid #484574;
      box-sizing: border-box;
    }
  }
  .my-head {
    text-align: right;
    .head-img-box {
      left: 0;
    }
  }
  .o-head {
    text-align: left;
    .head-img-box {
      right: 0;
    }
  }
  .isFail {
    background: #c2c2c2;
  }
  .swiper-index {
    position: absolute;
    font-size: 40rpx;
    font-weight: bold;
    color: #67c29f;
    top: 60rpx;
    left: 50%;
    transform: translateX(-50%);
  }
}
.name {
  width: 100%;
  line-height: 1;
  font-size: 30rpx;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 50rpx 30rpx 24rpx 30rpx;
  box-sizing: border-box;
  div {
    width: 160rpx;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    color: #fff;
  }
}
.swiper-wrap {
  width: 100%;
  height: 580rpx;
  position: relative;
  .left-btn,
  .right-btn {
    position: absolute;
    top: 50%;
    // transform: translateY(-50%);
    width: 47rpx;
    height: 47rpx;
    z-index: 20;
  }
  .left-btn {
    left: 0;
  }
  .right-btn {
    right: 0;
    transform: rotate(180deg);
  }
}
swiper {
  width: 100%;
  height: 100%;
  max-height: 886rpx;
}

.box-top {
  height: 220rpx;
  display: flex;
  justify-content: center;
  align-items: center;
}
.m-word-title {
  position: relative;
  width: 585rpx;
  height: 168rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  font-size: 28rpx;
  color: #fff;
  opacity: 1;
  transition: opacity 0.8s ease;
  border-radius: 20rpx;
  box-sizing: border-box;
  padding: 0 20rpx;
}
.english {
  font-size: 36rpx;
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
  top: -23rpx;
  left: 50%;
  transform: translateX(-50%);
  // box-shadow:0 0 30rpx -4rpx #fff;
}

.m-img {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  .img-title {
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    color: #fff;
    font-size: 26rpx;
    margin-bottom: 16rpx;
    .img-level {
      width: 38rpx;
      height: 38rpx;
      line-height: 38rpx;
      text-align: center;
      border-radius: 50%;
      font-size: 24rpx;
      color: #fff;
      margin-right: 12rpx;
    }
  }
  .img-box {
    img {
      max-height: 170rpx;
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
  opacity: 1;
  transition: opacity 0.8s ease;
  .u-word-level {
    position: relative;
    margin-bottom: 24rpx;
  }
  .u-word {
    width: 98rpx;
    height: 98rpx;
    border: 2rpx dashed #fff;
    border-radius: 8rpx;
    box-sizing: border-box;
    font-size: 64rpx;
    font-family: "宋体";
    color: #fff;
    margin-right: 26rpx;
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
  font-size: 48rpx;
  color: #fff;
  .u-word-level {
    top: 0rpx;
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
// .img-box {
//   width: 500rpx;
//   height: 278rpx;
//   margin: 0 auto 30rpx auto;
//   opacity: 1;
//   transition: all 0.8s ease;
//   img {
//     width: 100%;
//     height: 100%;
//   }
// }

.select-list {
  height: 320rpx;
  margin-top: 20rpx;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  .select-item {
    width: 330rpx;
    height: 65rpx;
    position: relative;
    font-size: 24rpx;
    border: 2rpx solid #17c4c9;
    color: #7cf7e4;
    border-radius: 10rpx;
    box-sizing: border-box;
    opacity: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 20rpx;
    &:last-child {
      margin-bottom: 0;
    }
    .select-item-txt {
      position: relative;
      min-width: 250rpx;
      // max-width: 300rpx;
      text-align: center;
      font-weight: bold;
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
      @include triangle(left, 20rpx, #85d85d);
      left: -20rpx;
    }
    .yes-arrow-r {
      @include triangle(right, 20rpx, #85d85d);
      right: -20rpx;
    }
    .err-arrow-l {
      @include triangle(left, 20rpx, #ff217b);
      left: -20rpx;
    }
    .err-arrow-r {
      @include triangle(right, 20rpx, #ff217b);
      right: -20rpx;
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
      transform: translateY(-50%) scale(0.8);
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
  }
  .success,
  .fail {
    color: #fff;
    animation: btnScale 0.4s ease;
  }

  .success {
    background: #85d85d;
    border-color: #85d85d;
  }
  .fail {
    background: #ff217b;
    border-color: #ff217b;
  }
  .noSelect {
    transform: scale(0) !important;
    opacity: 0 !important;
  }
}
.judge-type {
  height: 150rpx !important;
  width: 320rpx !important;
}

.title {
  position: absolute;
  top: -30rpx;
  left: 50%;
  transform: translate(-50%, 0);
  width: 201rpx;
  height: 57rpx;
  z-index: 2;
}
.like-box {
  width: 664rpx;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  top: -70rpx;
  display: flex;
  justify-content: space-between;
  align-items: center;
  div {
    img {
      width: 53rpx;
      height: 45rpx;
      margin-right: 4rpx;
    }
    font-size: 24rpx;
    font-weight: bold;
    color: #fff;
  }
}
.desc {
  width: 664rpx;
  min-height: 350rpx;
  position: relative;
  margin: 0 auto 50rpx auto;
  padding:30rpx 50rpx;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  box-sizing: border-box;
  color: #776cc4;
  background: #fff;
  font-size: 40rpx;
  font-weight: bold;
  border-radius: 30rpx;
  .desc-text {
    font-size: 30rpx;
    text-align: left;
  }
  .desc-bottom {
    margin-top: 20rpx;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .author {
      display: flex;
      justify-content: center;
      align-items: center;
      font-size: 20rpx;
      color: #7efce4;
      img {
        width: 28rpx;
        height: 28rpx;
        border-radius: 50%;
        margin: 0 10rpx;
      }
    }
  }
}
.advice {
  display: flex;
  font-size: 20rpx;
  align-items: center;
  color: #776cc4;
  position: absolute;
  top: -80rpx;
  left: 50%;
  transform: translateX(-50%);
  z-index: 2;
  img {
    width: 22rpx;
    height: 22rpx;
    margin-right: 15rpx;
  }
}

@mixin flex {
  display: flex;
  justify-content: center;
  align-items: center;
}

.m-head {
  width: 100%;
  @include flex;
  align-items: flex-start;
  color: #fff;
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
  border: 4rpx solid #4d497c;
  border-radius: 10rpx;
  box-sizing: border-box;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  top: -32rpx;
  background: #28235c;
  z-index: 3;
  .u-inner-box {
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    border: 3rpx solid #fff;
    border-radius: 10rpx;
    font-size: 30rpx;
    transition: all 0.2s linear;
    @include flex;
  }
  .show-add-type {
    border-color: #85d85d !important;
    .u-add-score {
      color: #85d85d !important;
    }
  }
  .show-none-type {
    border-color: #ff217b !important;
    .u-add-score {
      color: #ff217b !important;
    }
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
.quesIndex {
  width: 570rpx;
  height: 4rpx;
  background: #a69ee3;
  position: relative;
  margin:0 auto 30rpx auto;
  .q-txt {
    width: 130rpx;
    font-size: 30rpx;
    color: #a69ee3;
    text-align: center;
    background: #28235c;
    position: absolute;
    font-weight: bold;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }
}
.u-message {
  width: 580rpx;
  height: 68rpx;
  @include flex;
  justify-content: flex-start;
  padding: 0 30rpx;
  background: #120e44;
  border-radius: 10rpx;
  font-size: 36rpx;
  color: #fff;
  box-sizing: border-box;
  margin-top: 40rpx;
  img {
    width: 49rpx;
    height: 40rpx;
    margin-right: 20rpx;
  }
}

.creat-info {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 26rpx;
  font-weight: bold;
  color: #7c74b9;
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
</style>
