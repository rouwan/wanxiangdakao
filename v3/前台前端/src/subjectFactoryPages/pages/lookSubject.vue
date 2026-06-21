<template>
  <div class="m-edit-subject sky-bg-2">
    <div class="u-head">
      <div class="u-type" v-if="wordList[parentType]">
        {{wordList[parentType].type||"未知type-parentType"}}
      </div>
        {{wordList[parentType].desc||"未知type"}}
      <div v-if="subjectStatus==2" class="u-edit" @click="handRouter">
        <img src="/static/bi.png"> 重新修改
      </div>
    </div>

    <div class="u-area">
      <textarea placeholder-style="color:#a69ee3;font-size:30rpx;" disabled="true" placeholder="题目字数在30个字以内" maxlength="30" v-model.lazy="quesTxt"></textarea>
      <!-- <div class="u-word-tip">{{quesTxt.length||0}}/30</div> -->
    </div>
    <div class="u-selct u-yes">
      <img src="/static/rank/yes.png" alt="">
      <input type="text" maxlength="10" placeholder-style="color:#fff;font-size:28rpx;" placeholder="请输入正确选项" v-model.lazy="op1">
    </div>
    <div class="u-selct">
      <img src="/static/rank/error.png" alt="">
      <input type="text" maxlength="10" placeholder-style="color:#fff;font-size:28rpx;" placeholder="请输入错误选项" v-model.lazy="op2">
    </div>
    <div class="u-selct">
      <img src="/static/rank/error.png" alt="">
      <input type="text" maxlength="10" placeholder-style="color:#fff;font-size:28rpx;" placeholder="请输入错误选项" v-model.lazy="op3">
    </div>
    <div class="u-selct mb50">
      <img src="/static/rank/error.png" alt="">
      <input type="text" maxlength="10" placeholder-style="color:#fff;font-size:28rpx;" placeholder="请输入错误选项" v-model.lazy="op4">
    </div>

    <div class="u-hr">
      <div class="u-hr-txt">本题解析</div>
    </div>
    <div class="u-desc pl30">
      {{descTxt}}
      <div class="u-info">
        ——
        <img v-if="createInfo.img" :src="createInfo.img"> 
        <img v-else src="/static/default-head.png"> 
        {{createInfo.name}}
      </div>
    </div>
    <div class="fail-desc" v-if="subjectStatus==2">
      {{rejectRemark||"驳回理由:不符合规定"}}
    </div>
    <div class="u-mask"></div>
  </div>
</template>

<script>
import subjectApi from "@/api/subjectApi";
export default {
  data() {
    return {
      subjectId: -1,
      parentType: 3,
      createInfo:{
        img:"",
        name:""
      },
      subjectStatus: 0,
      rejectRemark:"",
      quesTxt: "",
      descTxt: "",
      op1: "",
      op2: "",
      op3: "",
      op4: "",
      wordList: {
        0: { type: "未知", desc: "未知" },
        1: { type: "未知", desc: "未知" },
        2: { type: "未知", desc: "未知" },
        3: { type: "文科", desc: "人文历史" },
        4: { type: "理科", desc: "自然科学" },
        5: { type: "艺科", desc: "艺术文化" },
        6: { type: "体科", desc: "健康常识" },
        7: { type: "综合", desc: "综合学识" }
      }
    };
  },
  methods: {
    clearData() {
      this.quesTxt = "";
      this.op1 = "";
      this.op2 = "";
      this.op3 = "";
      this.op4 = "";
      this.descTxt = "";
    },
    getSubjectById() {
      //更新题目
      wx.showLoading();
      subjectApi.getSubjectById({ id: this.subjectId }).then(res => {
        this.quesTxt = res.Result.title;
        this.descTxt = res.Result.description;
        this.op1 = res.Result.optionList[0].name;
        this.op2 = res.Result.optionList[1].name;
        this.op3 = res.Result.optionList[2].name;
        this.op4 = res.Result.optionList[3].name;
        this.parentType = res.Result.parentType;
        this.subjectStatus = res.Result.status;
        this.rejectRemark = res.Result.rejectRemark;
        this.createInfo.img=res.Result.createUserUrl;
        this.createInfo.name=res.Result.createUserName;
        wx.hideLoading();
      });
    },
    handRouter(){
      this.$router.push(`/subjectFactoryPages/pages/addSubject?subjectId=${this.subjectId}`)
    },
  },

  onShow() {
    this.parentType = this.$root.$mp.query.parentType || 1;
    this.subjectId = this.$root.$mp.query.subjectId || -1;
    this.getSubjectById();
  }
};
</script>

<style lang="scss" scoped>
.m-edit-subject {
  width: 100%;
  height: 100%;
  padding: 40rpx;
  box-sizing: border-box;
}
.u-head {
  margin-bottom: 16rpx;
  color: #a69ee3;
  font-size: 30rpx;
  position: relative;
  .u-type {
    font-size: 46rpx;
    font-weight: bold;
    color: #fff;
    position: relative;
    margin-right: 30rpx;
    display: inline-block;
    &::after {
      content: "";
      position: absolute;
      width: 2rpx;
      height: 46rpx;
      right: -14rpx;
      top: 10rpx;
      background: #a69ee3;
    }
  }
  .u-edit {
    position: absolute;
    right: 0rpx;
    top: 50%;
    transform: translateY(-50%);
    display: flex;
    align-items: center;
    z-index: 20;
    img {
      width: 34rpx;
      height: 42rpx;
      margin-right: 10rpx;
    }
  }
}
.u-area {
  border-radius: 30rpx;
  position: relative;
  margin-bottom: 30rpx;
}
textarea {
  width: 664rpx;
  height: 168rpx;
  border-radius: 30rpx;
  background: #fff;
  padding: 28rpx;
  box-sizing: border-box;
  color: #000;
  font-size: 30rpx;
}
.u-word-tip {
  position: absolute;
  right: 40rpx;
  bottom: 24rpx;
  font-size: 24rpx;
  color: #a69ee3;
}

.u-selct {
  width: 416rpx;
  height: 92rpx;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  background: #ff217b;
  box-shadow: 0 0 10rpx 4rpx rgba(#ff217b, 0.5);
  margin: 0 auto 20rpx auto;
  border-radius: 10rpx;
  img {
    width: 31rpx;
    height: 35rpx;
    position: absolute;
    left: 24rpx;
    top: 50%;
    transform: translateY(-50%);
  }
  input {
    width: 300rpx;
    height: 60rpx;
    font-size: 28rpx;
    color: #fff;
    text-align: center;
    border: none;
  }
}
.mb50 {
  margin-bottom: 50rpx !important;
}
.u-yes {
  background: #85d85d;
  box-shadow: 0 0 10rpx 4rpx rgba(#85d85d, 0.5);
  img {
    width: 40rpx;
  }
}

.u-hr {
  width: 600rpx;
  height: 4rpx;
  position: relative;
  margin: 0 auto 40rpx auto;
  background: #a69ee3;
  .u-hr-txt {
    font-size: 36rpx;
    font-weight: bold;
    color: #a69ee3;
    background: #28235c;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -55%);
  }
}
.u-desc {
  width: 660rpx;
  position: relative;
  color: #a69ee3;
  font-size: 30rpx;
  margin: 0 auto;
  .u-info {
    position: absolute;
    right: 0;
    bottom: -50rpx;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #a69ee3;
    img {
      width: 50rpx;
      height: 50rpx;
      border-radius: 50%;
      margin: 0 10rpx;
    }
  }
}
.u-mask {
  width: 100%;
  height: 100%;
  position: fixed;
  z-index: 10;
  background: transparent;
  left: 0;
  top:0;
}
.fail-desc{
  color: #ff217b;
  margin-top: 50rpx;
  font-size: 30rpx;
  padding-left: 30rpx;
}
.pl30 {
  padding-left: 30rpx !important;
  box-sizing: border-box;
}
</style>
