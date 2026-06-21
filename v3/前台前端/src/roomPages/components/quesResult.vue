<template>
  <div class="m-result" v-if="dataReady">
    <div class="u-title">
      <img v-if="myRankNum==1" src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/groupBattle/first.png">
      <img v-if="myRankNum==2" src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/groupBattle/second.png">
      <img v-if="myRankNum==3" src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/groupBattle/third.png">
      <img v-if="myRankNum==4" src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/groupBattle/fourth.png">
    </div>
    <div class="u-list">
      <div class="u-item" :class="{'my-item':resultList[uIndex]&&resultList[uIndex].isMy}" v-for="(item,uIndex) in placeList" :key="uIndex">
        <div class="u-item-inner" v-if="resultList[uIndex]">
          <div class="u-icon">
            <img v-if="uIndex==0" src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/groupBattle/icon_first.png">
            <img v-if="uIndex==1" src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/groupBattle/icon_second.png">
            <img v-if="uIndex==2" src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/groupBattle/icon_third.png">
            <img v-if="uIndex==3" src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/groupBattle/icon_fourth.png">

          </div>
          <img v-if="resultList[uIndex].boot" :src="IMGHEAD+resultList[uIndex].basicInfo.avatarUrl">
          <img v-else :src="resultList[uIndex].basicInfo.avatarUrl">
          <div class="u-item-right">
            <div>
              <div class="u-name">{{resultList[uIndex].basicInfo.nickName}}</div>
              <div class="u-medal">
                <img :src="IMGHEAD+resultList[uIndex].abilityInfo.paragraphImage"> {{resultList[uIndex].abilityInfo.paragraphName}}
              </div>
            </div>
            <div>
              <div class="u-ans-list">
                <div class="u-ans-item" v-for="(ansIcon,ansIndex) in resultList[uIndex].ansList" :key="ansIndex">
                  <img v-if="ansIcon==0&&!resultList[uIndex].isMy" src="/static/main_yes.png">
                  <img v-if="ansIcon==1&&!resultList[uIndex].isMy" src="/static/main_err.png">
                  <img v-if="ansIcon==2&&!resultList[uIndex].isMy" src="/static/main_none.png">
                  <img v-if="ansIcon==0&&resultList[uIndex].isMy" src="/static/main_yes_active.png">
                  <img v-if="ansIcon==1&&resultList[uIndex].isMy" src="/static/main_err_active.png">
                  <img v-if="ansIcon==2&&resultList[uIndex].isMy" src="/static/main_none_active.png">
                </div>
              </div>
              <div class="u-score">{{resultList[uIndex].abilityInfo.score}}</div>
            </div>
          </div>
        </div>

      </div>
    </div>
    <div class="u-btn">
      <div class="u-btn-back" @click="back">返回房间</div>
      <div class="u-btn-invate" @click="toGameReview">本局回顾</div>
      <button class="u-btn-invate" open-type="share" plain="true">
        分享战绩
      </button>
      
      <!-- <img @click="emojiBtn=true" class="u-btn-emoji" src="/static/room/emoji-btn.png" alt=""> -->
    </div>
    <!-- <div class="u-message">
      <img src="/static/icon/voice.png">
      <div class="u-msg" v-if="showMsgData.msg!=''">
        {{showMsgData.userName}}:{{showMsgData.msg}};
      </div>
    </div> -->
    <!-- <emoji-box v-if="emojiBtn" @close="emojiBtn=false" @sendEmoji="handleSendEmoji"></emoji-box> -->
    
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import emojiBox from "../components/emojiBox.vue";
import {emoji} from "@/config/dataFile"
export default {
  props: ["result"],
  components:{
    "emoji-box":emojiBox,
  },
  data() {
    return {
      dataReady: false,
      myRankNum: 1,
      resultList: [],
      placeList: [0, 0, 0, 0],
      emoji:emoji,
      emojiBtn:false,
      msgList:[],
      msgTimer:null,
      showMsgData:{userNmae:"",msg:""},
    };
  },
  watch:{
    msgList(list){
      if(list.length==0){
        clearInterval(this.msgTimer);
        this.msgTimer=null;
        return false;
      }
      if(list.length>0&&this.msgTimer==null){
        this.msgTimer = setInterval(()=>{
          this.showMsgData = list[0];
          this.msgList.splice(0,1);
        },1000)
      }
    },
  },
  computed: {
    ...mapState(["IMGHEAD", "appInfo"])
  },
  methods: {
     ...mapMutations({
      connect:"socketStore/connect",
      addMessageListener:"socketStore/addMessageListener",
      removeMessageListener:"socketStore/removeMessageListener",
      send:"socketStore/send",
      close:"socketStore/close"
    }),

    initData() {
      let list = JSON.parse(JSON.stringify(this.result.Result));
      let quesList = list.game.subjectList;
      this.resultList = list.room.rankList;
      this.resultList.forEach((user, userIndex) => {
        user.basicInfo.userId == this.appInfo.userId
          ? ((user.isMy = true), (this.myRankNum = userIndex + 1))
          : null;
        user.ansList = [2,2,2,2,2];
      });
      quesList.forEach((item, index) => {
        if (item.answerList) {
          item.answerList.forEach((ansItem, ansIndex) => {
            this.resultList.forEach((user, userIndex) => {
              if (user.basicInfo.userId == ansItem.uid) {
                if (ansItem.right) {
                  user.ansList[index]=0; //答对
                } else if (!ansItem.right && ansItem.answer >= 0) {
                  user.ansList[index]=1; //答错
                } else if (!ansItem.right && ansItem.answer < 0) {
                  user.ansList[index]=2; //超时 没有做大
                }
                
              }
            });

          });
        } 
      });
      setTimeout(() => {
        this.dataReady = true;
      }, 100);
    },
    handleSendEmoji(index){
      this.send(`{'type':15,'data':${index}}`); //发送表情
    },
    handMessage(message){
      if(message.Type==15){
        let emojiInfo = JSON.parse(message.Result);
        this.msgList.push({userName:emojiInfo.userName,msg:this.emoji[emojiInfo.index].title});
        // this.showEmoji(message);
      }
    },
    back() {
      this.$emit("close");
      this.removeMessageListener(this.handleMessage);
    },
    toGameReview(){
      this.$router.push('/pages/gameReview/index?from=room');
      this.removeMessageListener(this.handleMessage);
      this.$emit("route");
    },
    showEmoji(message){
      let emojiInfo = JSON.parse(message.Result);
      this.msgList.push({userName:emojiInfo.userName,msg:this.emoji[emojiInfo.index].title});
    },
  },
  mounted() {
    this.initData();
    this.addMessageListener(this.handMessage);
  },

};
</script>

<style lang="scss" scoped>
@mixin flex {
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
}
.m-result {
  width: 100%;
  height: 100%;
  padding: 20rpx 60rpx 0 60rpx;
  box-sizing: border-box;
}

.u-title {
  @include flex;
  img {
    width: 380rpx;
    height: 336rpx;
  }
}
.u-list {
  width: 100%;
  .u-item {
    border-radius: 10rpx;
    height: 134rpx;
    margin-bottom: 12rpx;
    &:nth-of-type(1n + 1) {
      background: #31396a;
    }
    &:nth-of-type(2n + 2) {
      background: #394e77;
    }
    .u-item-inner {
      width: 100%;
      height: 134rpx;
      @include flex;
      justify-content: flex-start;
      color: #fff;
      font-size: 30rpx;
      font-weight: bold;
      border-radius: 10rpx;
      & > img {
        width: 112rpx;
        height: 112rpx;
        border-radius: 50%;
      }
    }
  }
  .my-item {
    background: #ff217b !important;
    .u-score {
      color: #fff !important;
    }
  }
}
.u-icon {
  width: 50rpx;
  height: 60rpx;
  margin: 0 20rpx;
  img {
    width: 100%;
    height: 100%;
  }
}
.u-item-right {
  @include flex;
  flex-direction: column;
  align-items: flex-start;
  margin-left: 22rpx;
  & > div {
    width: 380rpx;
    @include flex;
    justify-content: space-between;
    &:first-child {
      margin-bottom: 10rpx;
    }
    .u-ans-list {
      @include flex;
      justify-content: flex-start;
      .u-ans-item {
        margin-right: 2px;
        &:last-child {
          margin-right: 0;
        }
      }
      img {
        width: 38rpx;
        height: 38rpx;
        vertical-align: bottom;
      }
    }
    .u-medal {
      img {
        width: 38rpx;
        height: 38rpx;
        vertical-align: top;
        margin-right: 10rpx;
      }
    }
    .u-score {
      color: #fee94f;
    }
  }
}
.u-btn {
  @include flex;
  color: #fff;
  font-size: 30rpx;
  margin-top: 58rpx;
  width: 100%;
  .u-btn-back {
    width: 292rpx;
    height: 84rpx;
    border-radius: 10rpx;
    @include flex;
    background: #ff217b;
    margin-right: 24rpx;
  }
  .u-btn-invate {
    width: 214rpx;
    height: 84rpx;
    @include flex;
    background: #85d85d;
    border-radius: 10rpx;
    font-size: 30rpx;
    color: #fff;
    padding: 0;
    margin-right: 24rpx;
    border: 0;
  }
  .u-btn-emoji{
    width: 73rpx;
    height: 70rpx;
  }
}
.u-message{
  width: 100%;
  height: 68rpx;
  @include flex;
  justify-content: flex-start;
  padding: 0 30rpx;
  background: #120e44;
  border-radius: 10rpx;
  font-size: 36rpx;
  color:#fff;
  box-sizing: border-box;
  margin-top: 40rpx;
  img{
    width: 49rpx;
    height: 40rpx;
    margin-right: 20rpx;
  }

}
.u-name{
  width: 150rpx;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  text-align: left;
}
</style>
