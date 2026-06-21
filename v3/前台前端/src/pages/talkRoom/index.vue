<template>
  <div class="m-talk sky-bg">
    <div class="u-text">
      <input type="text"
             v-model="msgValue"
             class="u-input">
      <div class="u-submit"
           @click="handleSubmit">提交</div>
    </div>
    <scroll-view class="u-scroll"
                 scroll-y="true"
                 scroll-with-animation="true"
                 @scrolltolower="getNextPage">
      <div class="u-msg"
           v-if="item.message"
           v-for="(item,index) in dataList"
           @click="toUserInfo(item.userId)"
           :key="index">
          <div class="u-user" v-if="item.type==0">
            <img :src="IMGHEAD+item.paragraphImage">
            <div class="u-medal-name">{{item.paragraphName}}</div>
            {{item.nickname}}
          </div>
          <img :src="item.headImgUrl"
               v-if="item.type==0">
          <div v-if="item.type==0" class="u-txt">{{item.message}}</div>
          <div class="top" v-if="item.type==1">
            恭喜
            <img :src="item.headImgUrl">
            <div>{{item.nickname}}</div>
              登顶
            <img :src="IMGHEAD+item.highParagraphImage">
            <div>最强王者</div>
          </div>

      </div>
      <img class="story" @click="showQrCode('story')" src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/story-icon.jpg">
      <img class="red-bag"
           @click="showQrCode('bag')"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/redBag.png">
    </scroll-view>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  data() {
    return {
      msgValue: "",
      dataList: [],
      dist: null,
      limitTime: 10000,
      sendLimit: false,
      timer: null,
      pageSize: 10,
      pageIndex: 0,
      totalCount: 0,
      viewImg: [
        "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/qrCode.png"
      ],
      storyImg:[
        "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/story-img.jpg"
      ]
    };
  },
  computed: {
    ...mapState(["IMGHEAD", "appInfo"])
  },
  methods: {
    getData(config) {
      return new Promise((resolve, reject) => {
        this.fly
          .post("/getMessageBoardList", {
            pageSize: config.pageSize,
            pageIndex: config.pageIndex
          })
          .then(res => {
            resolve(res);
            // this.dist = 0;
          });
      });
    },
    showQrCode(type) {
      let list= this.viewImg;
      if(type=='story'){
        list = this.storyImg;
      }
      wx.previewImage({
        urls: list // 需要预览的图片http链接列表
      });
    },
    toUserInfo(id) {
      this.$router.push(`/playerPages/pages/index?userId=${id}`);
    },
    getNextPage() {
      if (this.dataList.length == this.totalCount) {
        wx.showToast({ title: "我是有底线的", icon: "none" });
      }
      let page = Math.floor(this.dataList.length / this.pageSize);
      this.getData({ pageSize: 10, pageIndex: page }).then(res => {
        this.dataList.splice(page * 10, 10);
        this.dataList.concat(res.data.Result);
        for (let i = 0; i < res.data.Result.length; i++) {
          this.dataList.push(res.data.Result[i]);
        }
      });
    },
    handleSubmit() {
      if (!this.sendLimit) {
        if (this.msgValue == "") {
          wx.showToast({
            title: "写点东西呗~！",
            icon: "none"
          });
        } else {
          this.sendLimit = true;
          setTimeout(() => {
            this.sendLimit = false;
          }, this.limitTime);

          this.fly
            .post("/addMessageBoard", {
              message: this.msgValue
            })
            .then(res => {
              let arr = [
                {
                  type:0,
                  headImgUrl: this.appInfo.headImgUrl,
                  id: 0,
                  message: this.msgValue,
                  nickname: this.appInfo.nickname,
                  paragraphImage: this.appInfo.image,
                  paragraphName: this.appInfo.paragraphName,
                  userId: this.userId
                }
              ];
              this.dataList = arr.concat(this.dataList);
              this.msgValue = "";
            });
        }
      } else {
        wx.showToast({
          title: "不要发言太快~！",
          icon: "none"
        });
      }
    }
  },
  mounted() {
    this.getData({ pageSize: this.pageSize, pageIndex: this.pageIndex }).then(
      res => {
        this.totalCount = res.data.TotalCount;
        this.dataList = res.data.Result;
      }
    );
  },
  onUnload() {
    clearInterval(this.timer);
    this.pageIndex = 0;
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
.m-talk {
  width: 100%;
  height: 100%;
  padding-top: 118rpx;
  box-sizing: border-box;
  background-attachment: fixed;
}
.u-text {
  position: fixed;
  top: 0;
  left: 0;
  height: 118rpx;
  width: 100%;
  background: #31396a;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 5;
}
.u-input {
  width: 530rpx;
  height: 66rpx;
  margin-right: 30rpx;
  border-radius: 12rpx;
  background: #fff;
  padding-left: 20rpx;
  box-sizing: border-box;
}
.u-submit {
  width: 130rpx;
  height: 56rpx;
  line-height: 56rpx;
  background: #32b3e2;
  border-radius: 10rpx;
  text-align: center;
  font-size: 28rpx;
  color: #fff;
}

.u-scroll {
  width: 100%;
  height: 98%;
  padding-left: 38rpx;
  position: relative;
}
.u-msg {
  position: relative;
  width: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-bottom: 80rpx;
  .top{
    margin-left: 20rpx;
    width: 637rpx;
    height: 107rpx;
    padding-top: 30rpx;
    padding-left: 40rpx;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    justify-content: center;
    background-size: 638rpx 107rpx;
    background-repeat: no-repeat;
    background-image:url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/top-bg.png");
    font-size: 24rpx;
    color: #fff;
    font-weight: bold;
    img{
      margin: 0 10rpx;
      &:nth-of-type(1){
        width: 60rpx;
        height: 60rpx;
        box-sizing: border-box;
        border: 4rpx solid #fff;
        border-radius: 50%;
      }
      &:nth-of-type(2){
        width: 65rpx;
        height: 65rpx;
      }
    }
    div{
      width:5em;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      color: #82fbfb;
      text-align: center;
    }
  }
  &:first-child {
    margin-top: 100rpx;
  }
  .u-user {
    position: absolute;
    top: -40rpx;
    left: 100rpx;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    color: #fcee21;
    font-size: 24rpx;
    img {
      width: 36rpx;
      height: 36rpx;
      margin-right: 8rpx;
    }
    .u-medal-name {
      color: #fff;
      margin-right: 8rpx;
    }
  }
  & > img {
    width: 70rpx;
    height: 70rpx;
    border-radius: 50%;
    margin-right: 20rpx;
  }
  .u-txt {
    padding: 20rpx;
    width: 550rpx;
    min-height: 65rpx;
    border-radius: 12rpx;
    background: #fff;
    font-size: 24rpx;
    color: #000;
    display: flex;
    align-items: center;
    text-align: justify;
    position: relative;
    box-sizing: border-box;
    &::before {
      @include triangle(left, 10rpx, #fff);
      position: absolute;
      top: 50%;
      left: -8rpx;
      transform: translateY(-50%);
    }
  }
}
.red-bag {
  width: 113rpx;
  height: 138rpx;
  position: fixed;
  right: 0;
  top: 360rpx;
}
.story{
  width: 100rpx;
  height: 100rpx;
  position: fixed;
  right: 0;
  border-radius: 10rpx;
  top: 230rpx;
}
</style>
