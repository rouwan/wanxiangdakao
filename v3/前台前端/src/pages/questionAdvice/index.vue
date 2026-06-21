<template>
  <div class="body sky-bg">
    <div class="top">
      <div class="title">题目纠错</div>
    </div>
    <div class="middle">
      <div class="typeList">
        <div class="type"
             v-for="(type,index) in typeList"
             :key="index"
             @click="onType(index)"
             :class="{selectType:index == selectType }">{{type}}</div>
      </div>
    </div>
    <div class="bottom">
      <div class="title">详细情况</div>
      <div class="remark">
        <textarea maxlength="300"
                  placeholder="我有意见"
                  class="remarkText"
                  v-model="remark" />
      </div>
      <div class="submitBtn"
           @click="onSubmit">提交</div>
    </div>
    <img class="moon"
         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/moon.png">
  </div>
</template>
<script>
import { mapState, mapMutations } from "vuex";
export default {
  data() {
    return {
      //1:排版有问题,2:题目不严谨,3:有错别字,4:题目错误,5:题目缺乏价值,6:其他问题
      typeList: {
        1: "排版有问题",
        2: "题目不严谨",
        3: "有错别字",
        4: "题目错误",
        5: "题目缺乏价值",
        6: "其他问题"
      },
      remark: "",
      selectType: -1,
      id: 0,
      qId: 0,
      from: ""
    };
  },
  computed: {
    ...mapState(["gameRecord"])
  },
  methods: {
    getId() {
      this.id = this.$root.$mp.query.current;
      this.from = this.$root.$mp.query.from;
      if (this.gameRecord) {
        if (this.from == "room") {
          this.qId = this.gameRecord.Result.game.subjectList[this.id].id;
        } else {
          this.qId = this.gameRecord.Result.subjectList[this.id].id;
        }

        console.log(this.qId);
      }
    },
    onType(index) {
      this.selectType = index;
      console.log(this.selectType);
    },
    onSubmit() {
      if (this.selectType == -1) {
        wx.showToast({ title: "请选择纠错类型", icon: "none" });
        return false;
      }
      if (this.qId == 0) {
        wx.showToast({ title: "页面数据有误,请重新进入", icon: "none" });
        return false;
      }
      this.fly
        .post("/feedBackSubject", {
          subjectId: this.qId,
          types: this.selectType,
          remark: this.remark
        })
        .then(res => {
          //输出请求数据
          wx.showToast({
            title: "提交成功",
            icon: "success",
            duration: 1000
          });
          setTimeout(() => {
            this.$router.back();
          }, 1100);
        })
        .catch(err => {
          console.log(err.status, err.message);
        });
    }
  },
  onUnload() {
    this.selectType = -1;
  },
  onShow() {
    this.getId();
  }
};
</script>
<style lang="scss" scoped>
.body {
  //width: 100%;
  height: 100%;
  padding: 0 60rpx;
  box-sizing: border-box;
  position: relative;
}
.top {
  display: flex;
  justify-content: center;
  align-items: center;
  .title {
    font-size: 66rpx;
    font-weight: bold;
    padding-top: 80rpx;
    padding-bottom: 50rpx;
    color: #83fbe0;
  }
}
.middle {
  .title {
    color: #67c29f;
    font-size: 36rpx;
    font-weight: bold;
    margin-bottom: 24rpx;
  }
  .typeList {
    width: 636rpx;
    height: 280rpx;
    border-radius: 10rpx;
    display: flex;
    justify-content: center;
    align-items: center;
    align-content: center;
    flex-wrap: wrap;
    background: rgba(#7efce4, 0.1);
    .type {
      width: 166rpx;
      height: 80rpx;
      display: flex;
      justify-content: center;
      align-items: center;
      //color: #ffffff;
      background: #fff;
      font-size: 24rpx;
      border-radius: 5rpx;
      margin-right: 24rpx;
      box-sizing: border-box;
      &:nth-of-type(1),
      &:nth-of-type(2),
      &:nth-of-type(3) {
        margin-bottom: 40rpx;
      }
      &:nth-of-type(3n) {
        margin-right: 0rpx;
      }
    }
    .selectType {
      color: #ffffff;
      background-color: #fe227c;
    }
  }
}
.bottom {
  .title {
    color: #83fbe0;
    font-size: 40rpx;
    font-weight: bold;
    margin-top: 60rpx;
    margin-bottom: 25rpx;
  }
  .remark {
    display: flex;
    justify-content: flex-start;
    .remarkText {
      width: 100%;
      height: 200rpx;
      font-size: 26rpx;
      border: 2rpx solid #12c6c3;
      box-sizing: border-box;
      padding: 30rpx;
      color: #fff;
      border-radius: 12rpx;
    }
  }
  .submitBtn {
    width: 178rpx;
    height: 58rpx;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #83fbe0;
    font-size: 26rpx;
    font-weight: bold;
    border-radius: 12rpx;
    border: 4rpx solid #12c6c3;
    margin: 100rpx auto 0 auto;
  }
}
</style>


