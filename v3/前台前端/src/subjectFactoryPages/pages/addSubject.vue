<template>
  <div class="m-edit-subject sky-bg-2">
    <div class="u-head">
      <div class="u-type">{{wordList[parentType].type}}</div>{{wordList[parentType].desc}}</div>
    <div class="u-area">
      <textarea placeholder-style="color:#a69ee3;font-size:30rpx;" placeholder="题目字数在40个字以内" maxlength="40" v-model="quesTxt"></textarea>
      <div class="u-word-tip">{{quesTxt.length||0}}/40</div>
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
    <div class="u-area">
      <textarea placeholder-style="color:#a69ee3;font-size:30rpx;" placeholder="解析字数在500个字以内" maxlength="500" v-model="descTxt"></textarea>
      <div class="u-word-tip">{{descTxt.length||0}}/500</div>
    </div>

    <div class="u-submit" @click="handleAddSubject">提交题目</div>
  </div>
</template>

<script>
import subjectApi from "@/api/subjectApi";
import { getStorage, setStorage } from "@/utils/index";
export default {
  data() {
    return {
      subjectId: -1,
      parentType: 1,
      quesTxt: "",
      descTxt: "",
      op1: "",
      op2: "",
      op3: "",
      op4: "",
      wordList: {
        0:{ type: "未知", desc: "未知" },
        1:{ type: "未知", desc: "未知" },
        2:{ type: "商科", desc: "经济金融" },
        3:{ type: "文科", desc: "人文历史" },
        4:{ type: "理科", desc: "自然科学" },
        5:{ type: "艺科", desc: "艺术文化" },
        6:{ type: "体科", desc: "健康常识" },
        7:{ type: "综合", desc: "综合学识" }
      }
    };
  },
  methods: {
    handleAddSubject() {
      console.log("提交1");
      if (this.quesTxt == "") {
        wx.showToast({ title: "题目不能为空", icon: "none" });
      } else if (
        this.op1 == "" ||
        this.op2 == "" ||
        this.op3 == "" ||
        this.op4 == ""
      ) {
        wx.showToast({ title: "选项不能为空", icon: "none" });
      } else if (this.descTxt == "") {
        wx.showToast({ title: "解析不能为空", icon: "none" });
      } else {
        let _option = `${this.op1}%#@${this.op2}%#@${this.op3}%#@${this.op4}`;
        console.log("提交2");
        subjectApi
          .updateSubject({
            id: this.subjectId,
            parentType: this.parentType,
            answer: 1,
            title: this.quesTxt,
            description: this.descTxt,
            option: _option
          })
          .then(res => {
            if (res.Tag == 1) {
              wx.showToast({ title: "提交成功", icon: "none" });
              this.clearData();
              setTimeout(() => {
                this.$router.push('/subjectFactoryPages/pages/mySubjectList');
              }, 1500);
            } else {
              wx.showToast({ title: res.Message, icon: "none",duration:3000 });
            }
          });
      }
    },
    clearData() {
      this.quesTxt = "";
      this.op1 = "";
      this.op2 = "";
      this.op3 = "";
      this.op4 = "";
      this.descTxt = "";
    },
    getStorage() {
      let key = `parentType${this.parentType}`;
      wx.getStorage({
        key: key,
        success: res => {
          this.quesTxt =res.data.quesTxt;
          this.op1 =res.data.op1;
          this.op2 =res.data.op2;
          this.op3 =res.data.op3;
          this.op4 =res.data.op4;
          this.descTxt =res.data.descTxt;
        }
      });
    },
    editSubject() {
      //更新题目
      subjectApi.getSubjectById({ id: this.subjectId }).then(res => {
        this.quesTxt = res.Result.title;
        this.descTxt = res.Result.description;
        this.parentType = res.Result.parentType;
        this.op1 = res.Result.optionList[0].name;
        this.op2 = res.Result.optionList[1].name;
        this.op3 = res.Result.optionList[2].name;
        this.op4 = res.Result.optionList[3].name;
      });
    }
  },

  onShow() {
    this.parentType = this.$root.$mp.query.parentType || 1;
    this.subjectId = this.$root.$mp.query.subjectId || -1;
    if (this.subjectId == -1) {
      this.getStorage();
    } else {
      this.editSubject();
    }
  },
  onUnload() {
    let key = `parentType${this.parentType}`;
    setStorage(
      key,
      {
        quesTxt: this.quesTxt,
        descTxt: this.descTxt,
        op1: this.op1,
        op2: this.op2,
        op3: this.op3,
        op4: this.op4
      }
    );
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
}
.u-area {
  border-radius: 30rpx;
  box-shadow: 0 0 10rpx 4rpx rgba(#585192, 0.9);
  position: relative;
  margin-bottom: 30rpx;
}
textarea {
  width: 664rpx;
  height: 168rpx;
  border-radius: 30rpx;
  background: #211b4b;
  padding: 28rpx;
  box-sizing: border-box;
  color: #a69ee3;
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
.u-submit {
  width: 488rpx;
  height: 110rpx;
  line-height: 110rpx;
  text-align: center;
  border-radius: 12rpx;
  font-size: 36rpx;
  color: #fff;
  margin: 0 auto;
  background: #585192;
}
</style>
