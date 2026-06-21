<template>
  <div class="m-user sky-bg-2">
    <div class="u-user-head">
      <img v-if="dataReady" :src="userInfo.avatarUrl">
      <img v-else src="/static/default-head.png">
      <div class="game-info">
        <div class="info-top">
          <div class="name">{{userInfo.nickname}}</div>
          <img v-if="userInfo.gender=='女'" class="girl" src="/static/girl.png">
          <img v-else class="boy" src="/static/boy.png">
          <div class="province">{{userInfo.province||"宇宙"}}</div>
          <div class="city">{{userInfo.city||"火星"}}</div>
        </div>
        <div class="info-bottom">
          等级{{userInfo.grade}}
          <div class="exp-process">
            <div class="exp-inner" :style="expStyle"></div>
            <div class="exp-num">{{(userInfo.expValue||0)+"/"+(userInfo.maxExpValue||100)}}</div>
          </div>
        </div>
      </div>
    </div>

    <div class="list">
      <div class="list-head">
        <div v-for="(item,index) in headList" :key="index" :class="{active:index==current}" @click="handleTabClick(index)">{{item}}</div>
      </div>
      <div class="info-box">
        <!-- 对局数据 -->
        <div v-if="current==0" class="total-data">
          <div class="total-head">
            <div class="head-item">
              <img v-if="userInfo.paragraphImage" :src="IMGHEAD+userInfo.paragraphImage">
              <div class="item-name">{{userInfo.paragraphName||"无段位信息"}}</div>
            </div>
            <div class="head-item">
              <div class="item-title">总场数</div>
              <div class="item-num">{{userInfo.totalNum}}</div>
            </div>
            <div class="head-item">
              <div class="item-title">胜率</div>
              <div class="item-num">{{userInfo.winRatio}}%</div>
            </div>
            <div class="head-item">
              <div class="item-title">逃跑率</div>
              <div class="item-num">{{userInfo.exitRatio}}%</div>
            </div>
          </div>
          <div class="total-content">
            <div class="content-item">
              胜局数
              <div>{{userInfo.winNum}}</div>
            </div>
            <div class="content-item">
              连胜纪录
              <div>{{userInfo.maxContinueWinNum}}胜</div>
            </div>
            <div class="content-item">
              平局数
              <div>{{userInfo.flatNum}}</div>
            </div>
            <div class="content-item">
              单局最高
              <div>{{userInfo.maxSingleScore}}</div>
            </div>
            <div class="content-item">
              败局数
              <div>{{userInfo.loseNum}}</div>
            </div>
            <div class="content-item">
              全对纪录
              <div>{{userInfo.maxAllRightNum}}</div>
            </div>
            <div class="content-item">
              逃跑数
              <div>{{userInfo.exitNum}}</div>
            </div>
            <button plain="true" open-type="share" class="bnt-share">分享战绩</button>
          </div>
        </div>
        <!-- 战斗力 -->
        <div v-if="current==1" class="total-fight">
          <div class="f-head">
            <img src="/static/icon/fight.png"> {{totalFight}}
          </div>
          <div class="f-list">
            <div class="f-item" v-for="(item,index) in fightList" :key="index">
              <div class="f-type">{{item.type}}</div>
              <div class="f-num">{{item.num}}</div>
            </div>
          </div>
        </div>
        <!-- 赛季历史 -->
        <div v-if="current==2" class="season">
          <div class="s-head">
            <div class="h-item sActive"  @click="selectYear(2018,0)">2018年</div>
            <div class="h-item" :class="{sActive:nowYear>=2019,}" @click="selectYear(2019,1)">2019年</div>
          </div>
          <div class="s-list" v-if="seasonFomateList[seasonIndex].length>0">
            <div class="s-item" v-for="(item,index) in seasonFomateList[seasonIndex]" :key="index">
              <div class="s-time">{{item.year}}年{{item.day}}月</div>
              <div class="s-medal">
                <img class="medal-img" :src="IMGHEAD+item.paragraphImage" alt="">
                <!-- <img class="medal-img" src="http://192.168.4.108:8012/vientianeTestParagraphImage/2bb946f9-8831-4109-8b7c-e922a5895895.jpg" alt=""> -->
                <div class="medal-info">
                  <div class="medal-star">
                    <img src="/static/star.png" alt=""> {{item.star}}
                  </div>
                  <div class="medal-name">
                    {{item.paragraphName}}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- 对局历史 -->
        <div v-if="current==3" class="game-data">
          <div class="g-item" v-for="(item,index) in hisData" :key="index">
            <div class="g-res">
              <img v-if="((item.myInfo.abilityInfo.score>item.otherInfo.abilityInfo.score)&&userId!=item.exitUserId)||item.otherInfo.basicInfo.userId==item.exitUserId" class="word-s" src="/static/word-success.png">
              <img v-if="(item.myInfo.abilityInfo.score<item.otherInfo.abilityInfo.score&&item.otherInfo.basicInfo.userId!=item.exitUserId)||userId==item.exitUserId" class="word-f" src="/static/word-fail.png">
              <img v-if="(item.myInfo.abilityInfo.score==item.otherInfo.abilityInfo.score)&&userId!=item.exitUserId&&item.otherInfo.basicInfo.userId!=item.exitUserId" class="word-b" src="/static/word-balance.png">
            </div>

            <div class="g-user g-user-left">
              <div class="user-top">
                <img v-if="item.myInfo.basicInfo.avatarUrl" :src="item.myInfo.basicInfo.avatarUrl">
                <img v-else src="/static/default-head.png">
                <div class="top-right">
                  <div class="score">{{item.myInfo.abilityInfo.score}}</div>
                  <div class="ans">
                    <div class="ans-item" v-for="(ans,ansIndex) in item.myAns" :key="ansIndex">
                      <img v-if="ans==0" src="/static/main_yes.png">
                      <img v-if="ans==1" src="/static/main_err.png">
                      <img v-if="ans==2" src="/static/main_none.png">
                    </div>
                  </div>
                </div>
              </div>
              <div class="user-bottom">
                <img :src="IMGHEAD+item.myInfo.abilityInfo.paragraphImage"> {{item.myInfo.basicInfo.nickName}}
              </div>
            </div>

            <img class="g-vs" src="/static/vs2.png">

            <div class="g-user g-user-left">
              <div class="user-top">
                <img v-if="item.otherInfo.boot" :src="IMGHEAD+item.otherInfo.basicInfo.avatarUrl">
                <img v-else :src="item.otherInfo.basicInfo.avatarUrl">
                <div class="top-right">
                  <div class="score">{{item.otherInfo.abilityInfo.score}}</div>
                  <div class="ans">
                    <div class="ans-item" v-for="(ans2,ansIndex2) in item.otherAns" :key="ansIndex2">
                      <img v-if="ans2==0" src="/static/main_yes.png">
                      <img v-if="ans2==1" src="/static/main_err.png">
                      <img v-if="ans2==2" src="/static/main_none.png">
                    </div>
                  </div>
                </div>

              </div>
              <div class="user-bottom">
                <img :src="IMGHEAD+item.otherInfo.abilityInfo.paragraphImage"> {{item.otherInfo.basicInfo.nickName}}
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import singleApi from "@/api/singleApi";
export default {
    onShareAppMessage: function(res) {
    return {
      title: "呔！晒出你的战绩来",
      path: `/pages/entry/index?userId=${this.appInfo.userId}`
    };
  },
  data() {
    return {
      dataReady: false,
      userId: 0,
      current: 0,
      expStyle: "",
      userInfo: {},
      headList: ["竞技数据", "战斗力", "赛季历史", "对战历史"],
      hisData: [],
      fightingNum: [],
      seasonData: [],
      totalFight: 0,
      fightList: [
        { pType: 3, type: "文", num: 0 },
        { pType: 4, type: "理", num: 0 },
        { pType: 5, type: "艺", num: 0 },
        { pType: 6, type: "体", num: 0 },
        { pType: 2, type: "商", num: 0 },
        { pType: 7, type: "综", num: 0 }
      ],
      seasonList: [
         {
          paragraph: 11,
          paragraphImage:"/vientianeTestParagraphImage/2bb946f9-8831-4109-8b7c-e922a5895895.jpg",
          paragraphName: "不屈白银Ⅴ",
          seasonTime: "2018-11"
        },
        {
          paragraph: 11,
          paragraphImage:"/vientianeTestParagraphImage/2bb946f9-8831-4109-8b7c-e922a5895895.jpg",
          paragraphName: "不屈白银Ⅴ",
          seasonTime: "2018-12"
        },
        {
          paragraph: 11,
          paragraphImage:"/vientianeTestParagraphImage/2bb946f9-8831-4109-8b7c-e922a5895895.jpg",
          paragraphName: "不屈白银Ⅴ",
          seasonTime: "2019-01"
        },
        {
          paragraph: 11,
          paragraphImage:"/vientianeTestParagraphImage/2bb946f9-8831-4109-8b7c-e922a5895895.jpg",
          paragraphName: "不屈白银Ⅴ",
          seasonTime: "2019-02"
        }
      ],
      nowYear:2019,
      seasonFomateList:[[],[],],
      seasonIndex: 0
    };
  },
  computed: {
    ...mapState(["IMGHEAD", "appInfo"])
  },
  methods: {
    getUserInfo(id) {
      wx.showLoading({ title: "加载中", mask: true });
      singleApi.getUserInfoById({ id: id, type: 1 }).then(res => {
        this.userInfo = res.Result;
        this.expStyle = `width:${this.userInfo.expValue /
          this.userInfo.maxExpValue *
          100}%;`;
        this.dataReady = true;
        wx.hideLoading();
        this.formateData();
      });
    },
    handleTabClick(index) {
      this.current = index;
      let defind = {
        1: this.userInfo,
        2: this.fightingNum,
        3: this.seasonData,
        4: this.hisData
      };

      if (defind[index + 1].length == 0 || defind[index + 1].size == 0) {
        wx.showLoading({ title: "加载中", mask: true });
        singleApi
          .getUserInfoById({ id: this.userId, type: index + 1 })
          .then(res => {
            wx.hideLoading();
            if (index + 1 == 2) {
              //战斗力
              console.log("战斗力", res);
              this.fightingNum = res.Result.combatList;
              this.fomateFightList(this.fightingNum);
            }

            if (index + 1 == 3) {
              this.seasonData = res.Result.paragraphHistoryList;
              this.getSeasonList(res.Result.paragraphHistoryList);
            }
            if (index + 1 == 4) {
              console.log("对局历史", res);
              this.formateData(res.Result.rankHistoryList);
            }
          });
      }
    },
    fomateFightList(list) {
      for (let i = 0; i < list.length; i++) {
        for (let j = 0; j < this.fightList.length; j++) {
          if (this.fightList[j].pType == list[i].parentType) {
            this.fightList[j].num = list[i].combat;
            this.totalFight += parseInt(list[i].combat);
          }
        }
      }
    },
    formateData(data) {
      let history = [],
        formatList = [];

      for (let n = 0; n < (data && data.length); n++) {
        history.push(JSON.parse(data[n].detail));
      }
      for (let i = 0; i < data.length; i++) {
        let myInfo = {},
          otherInfo = {};
        let myAns = [2, 2, 2, 2, 2],
          otherAns = [2, 2, 2, 2, 2];

        for (let j = 0; j < history[i].userList.length; j++) {
          if (this.userId == history[i].userList[j].basicInfo.userId) {
            myInfo = history[i].userList[j];
          } else {
            otherInfo = history[i].userList[j];
          }
        }

        for (let k = 0; k < history[i].subjectList.length; k++) {
          let ans = history[i].subjectList[k].answerList;
          if (ans) {
            for (let f = 0; f < ans.length; f++) {
              if (this.userId == ans[f].uid) {
                if (ans[f].right) {
                  myAns[k] = 0; //答对
                } else if (!ans[f].right && ans[f].answer > 0) {
                  myAns[k] = 1; //答错
                }
              } else {
                if (ans[f].right) {
                  otherAns[k] = 0; //答对
                } else if (!ans[f].right && ans[f].answer > 0) {
                  otherAns[k] = 1; //答错
                }
              }
            }
          }
        }
        formatList[i] = {
          exitUserId: data[i].exitUserId || 0,
          myInfo: myInfo,
          otherInfo: otherInfo,
          myAns: myAns,
          otherAns: otherAns
        };
      }
      this.hisData = formatList;
    },
    getSeasonList(list){
      // let list = this.seasonList;
      this.seasonFomateList=[[],[]];
      for(let i=0;i<list.length;i++){
        if(list[i].seasonTime.indexOf("2018")>-1){
          this.seasonFomateList[0].push(list[i]);
        }else if(list[i].seasonTime.indexOf("2019")>-1){
          this.seasonFomateList[1].push(list[i]);
        }
      }
      for(let j=0;j<this.seasonFomateList.length;j++){
        for(let k=0;k<this.seasonFomateList[j].length;k++){
          this.seasonFomateList[j][k].year = this.seasonFomateList[j][k].seasonTime.split("-")[0];
          this.seasonFomateList[j][k].day = this.seasonFomateList[j][k].seasonTime.split("-")[1];
        }
      }
    },
    selectYear(year,index){
      if(year<=this.nowYear){
        this.seasonIndex=index;
      }
    },
    showData(index) {
      this.current = index;
    }
  },
  mounted() {
    this.userId = this.$root.$mp.query.userId;
    this.getUserInfo(this.userId);
    this.nowYear = new Date().getFullYear();
  },
  onUnload() {
    this.current = 0;
    this.dataReady = false;
    this.hisData = [];
    this.userInfo={};
    this.seasonFomateList=[[],[]];
    this.fightList=[
        { pType: 3, type: "文", num: 0 },
        { pType: 4, type: "理", num: 0 },
        { pType: 5, type: "艺", num: 0 },
        { pType: 6, type: "体", num: 0 },
        { pType: 2, type: "商", num: 0 },
        { pType: 7, type: "综", num: 0 }
      ];
    this.fightingNum=[];
    this.totalFight=0;
    this.seasonData=[];
  }
};
</script>

<style lang="scss" scoped>
@mixin flex {
  display: flex;
  align-items: center;
}
.m-user {
  width: 100%;
  height: 100%;
  padding-top: 54rpx;
  box-sizing: border-box;
}
.u-user-head {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  flex-shrink: 0;
  width: 100%;
  width: 630rpx;
  height: 140rpx;
  background: #342d63;
  font-size: 40rpx;
  border-radius: 40rpx;
  margin: 0 auto;
  padding-right: 40rpx;
  box-sizing: border-box;
  & > img {
    width: 174rpx;
    height: 174rpx;
    border: 8rpx solid #716e8d;
    border-radius: 50%;
    box-sizing: border-box;
    position: absolute;
    left: 50rpx;
  }
  .game-info {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    width: 400rpx;
    color: #fff;
    font-size: 30rpx;
    .info-top,
    .info-bottom {
      width: 100%;
      display: flex;
      align-items: center;
    }
    .info-top {
      margin-bottom: 20rpx;
      justify-content: flex-start;
      .name {
        font-size: 36rpx;
        max-width: 230rpx;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
        font-weight: bold;
      }
      img {
        margin: 0 10rpx;
      }
      .boy {
        width: 24rpx;
        height: 21rpx;
      }
      .girl {
        width: 14rpx;
        height: 21rpx;
      }
      .province,
      .city {
        color: #c1e4e5;
        font-size: 26rpx;
      }
      .province {
        margin-right: 10rpx;
      }
    }
    .info-bottom {
      justify-content: space-between;
      font-size: 28rpx;
      .exp-process {
        margin-left: 12rpx;
        width: 294rpx;
        height: 24rpx;
        background: #f2f2f2;
        box-sizing: border-box;
        border-radius: 1000rpx;
        position: relative;
      }
      .exp-inner {
        width: 0%;
        height: 100%;
        background: #7dfbe5;
        border-radius: 1000rpx;
        transition: all 0.2s ease;
      }
      .exp-num {
        position: absolute;
        right: 18rpx;
        top: 50%;
        transform: translateY(-50%);
        font-size: 20rpx;
        color: #342d64;
      }
    }
  }
}

.list {
  width: 650rpx;
  margin: 50rpx auto 0 auto;
}
.list-head {
  width: 100%;
  @include flex;
  justify-content: center;
  div {
    width: 162rpx;
    height: 70rpx;
    font-size: 32rpx;
    color: #a9f3ff;
    @include flex;
    justify-content: center;
    background: #1e2b3d;
    border: 1px solid #303b48;
    border-top-left-radius: 16rpx;
    border-top-right-radius: 16rpx;
    box-sizing: border-box;
  }
  .active {
    color: #a9f3ff;
    background-size: 100%;
    background-image: url("data:image/png;base64,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");
  }
}
.info-box {
  width: 100%;
  height: 820rpx;
  border: 2rpx solid #4b7c93;
  padding: 8rpx;
  box-sizing: border-box;
}
.total-data {
  width: 100%;
  height: 100%;
  padding: 22rpx 7rpx;
  box-sizing: border-box;
}
.total-head {
  width: 100%;
  height: 230rpx;
  @include flex;
  justify-content: center;
  margin-bottom: 20rpx;
  background: #30386a;
  border: 10rpx solid #2c2e63;
  box-sizing: border-box;
  div {
    text-align: center;
  }
  .head-item {
    margin-right: 50rpx;
    &:last-child {
      margin-right: 0;
    }
    img {
      width: 150rpx;
      height: 150rpx;
    }
    .item-name {
      font-size: 24rpx;
      line-height: 1;
      font-weight: bold;
      color: #fff;
    }
    .item-title {
      font-size: 30rpx;
      font-weight: bold;
      color: #dcebdc;
      margin-bottom: 10rpx;
    }
    .item-num {
      font-size: 28rpx;
      color: #3cf2f2;
    }
  }
}
.total-content {
  width: 100%;
  height: 520rpx;
  background: #30386a;
  box-sizing: border-box;
  padding-left: 40rpx;
  padding-top: 75rpx;
  text-align: left;
  border: 10rpx solid #2c2e63;
  box-sizing: border-box;
  position: relative;
}
.content-item {
  width: 45%;
  font-size: 28rpx;
  color: #ddecdd;
  margin-right: 30rpx;
  margin-bottom: 16rpx;
  display: inline-block;
  font-weight: bold;
  &:nth-of-type(2n + 2) {
    margin-right: 0;
  }
  div {
    width: 110rpx;
    height: 44rpx;
    line-height: 44rpx;
    text-align: right;
    font-size: 30rpx;
    color: #3cf2f2;
    display: inline-block;
    font-weight: normal;
  }
}

.game-data {
  width: 100%;
  height: 100%;
  overflow: scroll;
}
.g-item {
  width: 100%;
  height: 150rpx;
  @include flex;
  justify-content: flex-start;
  background: #394e77;
  .g-res {
    width: 80rpx;
    height: 100%;
    background: #3d577d;
    @include flex;
    justify-content: center;
    .word-s {
      width: 60rpx;
      height: 64rpx;
    }
    .word-f {
      width: 50rpx;
      height: 56rpx;
    }
    .word-b {
      width: 54rpx;
      height: 54rpx;
    }
  }
  &:nth-of-type(2n + 2) {
    background: #406082;
    .g-res {
      background: #436887;
    }
  }

  .g-user {
    width: 40%;
    @include flex;
    flex-direction: column;
    align-items: flex-start;
    box-sizing: border-box;
    .user-top {
      @include flex;
      img {
        width: 56rpx;
        height: 56rpx;
        border-radius: 50%;
        margin-right: 10rpx;
      }
      .top-right {
        .score {
          font-size: 30rpx;
          color: #fff;
        }
        .ans {
          @include flex;
          img {
            width: 28rpx;
            height: 28rpx;
            margin-right: 0;
          }
        }
      }
    }

    .user-bottom {
      @include flex;
      width: 100%;
      text-overflow: ellipsis;
      overflow: hidden;
      white-space: nowrap;
      font-size: 24rpx;
      color: #fff;
      img {
        width: 45rpx;
        height: 45rpx;
        margin-right: 10rpx;
      }
    }
  }

  .g-user-left {
    padding-left: 24rpx;
    .user-bottom {
      align-self: flex-start;
    }
  }
  .g-user-right {
    align-items: flex-end;
    padding-right: 24rpx;
    .user-top {
      .score {
        text-align: right;
      }
      & > img {
        margin-right: 0;
        margin-left: 10rpx;
      }
    }
    .user-bottom {
      align-self: flex-end;
    }
  }
  .g-vs {
    width: 58rpx;
    height: 58rpx;
  }
}

.total-fight {
  width: 600rpx;
  height: 780rpx;
  margin: 10rpx auto 0 auto;
  background: #30386a;
  border: 10rpx solid #2c2e63;
  box-sizing: border-box;
}
.f-head {
  width: 100%;
  height: 140rpx;
  @include flex;
  justify-content: center;
  img {
    width: 232rpx;
    height: 114rpx;
  }
  color: #ffff00;
  font-size: 50rpx;
}

.f-list {
  width: 500rpx;
  border-radius: 20rpx;
  margin: 0 auto;
  overflow: hidden;
}
.f-item {
  width: 100%;
  @include flex;
  &:nth-of-type(2n + 2) {
    .f-type {
      background: #384b76;
    }
    .f-num {
      background: #344270;
    }
  }
  .f-type {
    background: #3c547c;
  }
  .f-num {
    background: #384b76;
  }
}
.f-type,
.f-num {
  @include flex;
  justify-content: center;
  height: 88rpx;
}
.f-type {
  width: 166rpx;
  font-size: 44rpx;
  color: #79cff7;
  flex-shrink: 0;
}
.f-num {
  width: 100%;
  font-size: 40rpx;
  color: #fff;
}
.season {
  width: 600rpx;
  height: 780rpx;
  padding: 0 18rpx;
  margin: 10rpx auto 0 auto;
  background: #2c2e63;
  box-sizing: border-box;
  color: #fff;
  overflow: hidden;
}
.s-head {
  padding: 14rpx 0;
  @include flex;
}
.h-item {
  width: 150rpx;
  height: 56rpx;
  @include flex;
  justify-content: center;
  font-size: 32rpx;
  background: #bfbfbf;
  border-radius: 10rpx;
  margin-right: 20rpx;
}
.sActive {
  background: #85d85d;
}
.s-list {
  width: 100%;
  height: 700rpx;
  overflow: scroll;
}
.s-item {
  width: 100%;
  height: 100rpx;
  @include flex;
  justify-content: center;
  &:nth-of-type(2n + 2) {
    .s-time {
      background: #394b76;
    }
    .s-medal {
      background: #354270;
    }
  }
}
.s-time,
.s-medal {
  @include flex;
  justify-content: center;
}
.s-time {
  width: 50%;
  height: 100%;
  font-size: 32rpx;
  background: #354270;
}
.s-medal {
  width: 50%;
  height: 100%;
  background: #31386a;
  .medal-img {
    width: 80rpx;
    height: 80rpx;
  }
  .medal-info {
    display: flex;
    align-items: flex-start;
    justify-content: center;
    flex-direction: column;
    font-size: 28rpx;
    .medal-star {
      img {
        width: 30rpx;
        height: 30rpx;
        margin-right: 0 10rpx;
      }
      color: #8ef4fd;
    }
  }
}
.bnt-share{
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  width: 260rpx;
  height: 80rpx;
  border-radius: 10rpx;
  background: #29abe2;
  color: #fff;
  font-weight: bold;
  font-size: 32rpx;
  display: flex;
  justify-content: center;
  align-items: center;
  bottom: 50rpx;
  &:active{
    transform:  translateX(-50%) scale(.95);
  }
}
</style>
