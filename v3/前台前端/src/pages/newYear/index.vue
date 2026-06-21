<template>
  <div class="g-wrap">
    <div class="inner-wrap">
      <div class="gold"
           v-if="pageData">
        <img src="/static/gold.png"
             alt="">{{appInfo.money}}
      </div>
      <div class="content"
           v-if="pageData&&!isPass">
        <div class="current-level">第{{pageData.cardNumber}}关</div>
        <div class="image-box"
             v-if="pageData.modeType==1">
          <img mode="aspectFit"
               :src="IMGHEAD+pageData.image"
               alt="">
          <div class="image-title">{{pageData.title}}</div>
        </div>
        <div class="text-mode"
             v-if="pageData.modeType==3">
          {{pageData.title}}
        </div>

        <!-- 答案列表 -->
        <div class="anwer">
          <div class="text-item"
               v-for="(aItem,index) in answerList"
               :key="index"
               @click="removeWord(aItem,index)">{{aItem.word}}</div>
        </div>
      </div>
      <!-- 选项列表 -->
      <div class="select-area"
           v-if="!isPass">
        <div class="select-list">
          <div class="select-item"
               :class="{'hideItem':!sItem.show}"
               v-for="(sItem,index) in selectList"
               :key="index"
               @click="addWord(sItem,index)">{{sItem.word}}</div>
        </div>
        <div class="btn-list">
          <!-- 提示 -->
          <img class="hint"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newYear/promt.png"
               @click="hint"
               alt="">
          <!-- 求助 分享 -->
          <button open-type="share">
            <img src="/static/btn-help.png"
                 alt="">
          </button>
        </div>
      </div>

      <!-- 通关弹窗 -->

      <div class="model-box pass-model"
           v-if="isPass">
        <div class="mask"></div>
        <div class="model">
          <img class="title"
               src="/static/pass-model-head.png"
               alt="">
          <div class="text">恭喜你通关了!</div>
          <div class="model-btn">
            <div class="btn-yellow help"
                 @click="back">返回</div>
          </div>
        </div>
      </div>
    </div>
    <div class="ad-box">
      <ad unit-id="adunit-b4aadf3766ad114d"></ad>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  onShareAppMessage: function(res) {
    return {
      title: "万象新年专题挑战开始啦",
      path: `/pages/entry/index?userId=${this.appInfo.userId}`
    };
  },
  onUnload() {
    this.isPass = false;
  },
  data() {
    return {
      showModel: false,
      pageData: null,
      imgHead: "https://ggbsq.xingpanwang.com/",
      selectList: [],
      answerList: ["", ""],
      myAnswer: "", //用来监听答案正确与否,
      answerComplete: false,
      isPass: false,
      touchContext: null
    };
  },
  watch: {
    myAnswer(value) {
      let reg = /^(?=.*\d.*\b)/;
      this.answerComplete = reg.test(value); //不包含数字 则回答完整
      if (!this.answerComplete) {
        if (value == this.pageData.answer) {
          console.log("回答正确");
          this.fly
            .post("/specialSubject/submitSubject", {
              subjectId: this.pageData.id,
              answer: value
            })
            .then(res => {
              wx.setStorage({
                key: "newFoodCard",
                data: this.pageData.cardNumber
              });
              console.log(res);
              if (res.data.Tag == 1) {
                this.isPass = false;
                //  回答正确
                wx.showModal({
                  title: this.pageData.answer,
                  content: `【解释】${this.pageData.description}\n\n\n\n`,
                  showCancel: false,
                  success: () => {
                    this.getPageInit();
                  }
                });
              } else if (res.data.Tag == 2) {
                this.isPass = true;
                wx.showModal({
                  title: this.pageData.answer,
                  content: `【解释】${this.pageData.description}\n\n\n`,
                  showCancel: false,
                  success: () => {
                    wx.showToast({title:"通关奖励已发放至邮箱",icon:"none"})
                  }
                });
              }
            });
        } else {
          wx.showToast({
            title: "回答错误",
            icon: "none",
            duration: 500
          });
        }
      }
    }
  },
  computed: {
    ...mapState(["appInfo", "IMGHEAD"])
  },
  methods: {
    ...mapMutations(["setAppInfo"]),
    back() {
      this.$router.back();
    },
    getPageInit() {
      this.fly.post("/specialSubject/getSubject").then(res => {
        if (res.data.Tag == 2) {
          this.isPass = true;
          return false;
        }
        this.pageData = res.data.Result;
        this.myAnswer = "";
        this.answerList = [];
        this.initSelect();
        // 根据答案长度初始化 答案框 和 我的预设答案
        for (let i = 0; i < res.data.Result.answer.length; i++) {
          this.answerList[i] = {
            word: "",
            fromIndex: ""
          };
          this.myAnswer = this.myAnswer + i;
        }
      });
    },
    strUnique(str) {
      //字符串去重
      let se = new Set();
      str = str.split("");
      for (let i = 0; i < str.length; i++) {
        se.add(str[i]);
      }
      str = Array.from(se).join("");
      return str;
    },
    randomSort(a) {
      //数组随机
      var arr = a,
        random = [],
        len = arr.length;
      for (var i = 0; i < len; i++) {
        var index = Math.floor(Math.random() * (len - i));
        random.push(a[index]);
        arr.splice(index, 1);
      }
      return random;
    },
    initSelect() {
      //初始化选择列表
      let str = "",
        len = 0,
        list = [],
        successIndex, //正确选项下标
        random;
      this.selectList = [];
      this.pageData.optionList.forEach((item, index) => {
        // str += item.name;
        // len = str.length;
        if (item.sortId == this.pageData.optionSortId) {
          successIndex = index;
        }
      });

      //超过21个字
      // if (len > 21) {
      str = "";
      this.pageData.optionList.forEach((item, index) => {
        if (successIndex != index) {
          str += item.name;
          len = str.length;
        }
      });
      console.log(str);
      // }
      // if (len < 21) {
      let newStr = "";
      str = this.strUnique(str, len);
      console.log(str);
      str += this.pageData.optionList[successIndex].name;
      console.log(str);

      str = str.replace(/(^\s*)|(\s*$)/g, "");
      len = str.length;
      if (len > 12) {
        str = str.substring(len - 12);
      }
      if (len < 12) {
        newStr = this.pageData.randomStr.substring(0, 12 - len);
      }
      str += newStr;
      len = str.length;
      // }
      list = str.split("");
      //---star重新排列字符串
      // list.sort();
      list = this.randomSort(list);
      // ---end 排列
      list.forEach((item, index) => {
        let obj = {
          id: index,
          word: item,
          show: true,
          select: false
        };
        this.selectList.push(obj);
      });
    },

    addWord(sItem, index) {
      if (this.answerComplete) {
        sItem.show = false;
        let min = this.getMinNullIndex();
        this.answerList[min] = {
          word: sItem.word,
          fromIndex: index
        };
        this.myAnswer = this.myAnswer.replace(min, sItem.word);
      }
    },
    removeWord(aItem, index) {
      if (aItem.word != "") {
        // 修改相同下标的答案
        this.myAnswer = this.myAnswer.split("");
        this.myAnswer[index] = index;
        this.myAnswer = this.myAnswer.join("");
        aItem.word = "";
        this.selectList[aItem.fromIndex].show = true;
      }
    },
    getMinNullIndex() {
      for (let i = 0; i < this.answerList.length; i++) {
        if (this.answerList[i].word == "") {
          return i;
        }
      }
    },
    hint() {
      // 回答完整
      if (this.answerComplete) {
        // 先提示 后请求 增加体验
        if (this.appInfo.money >= 10) {
          this.setAppInfo({ money: this.appInfo.money - 10 });
          let min = this.getMinNullIndex();
          let word = this.pageData.answer.slice(min, min + 1);
          let fromIndex;
          this.selectList.forEach((sel, index) => {
            if (sel.word == word) {
              sel.show = false;
              fromIndex = index;
            }
          });
          this.answerList[min] = {
            word: word,
            fromIndex: fromIndex
          };
          this.myAnswer = this.myAnswer.replace(min, word);
          this.fly
            .post("/specialSubject/promptSubject", {
              subjectId: this.pageData.id
            })
            .then(res => {
              if (res.data.Tag == 1) {
                console.log("提示成功");
              }
            });
        } else {
          wx.showToast({ title: "金币不足", icon: "none" });
        }
      }
    }
  },
  mounted() {
    this.getPageInit();
  }
};
</script>
<style lang="scss" scoped>
.hideItem {
  visibility: hidden;
}
.g-wrap {
  position: relative;
  height: 100%;
  padding-top: 100rpx;
  box-sizing: border-box;
  overflow: scroll;
  -webkit-overflow-scrolling: touch;
  background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newYear/bg-food.png");
  background-size: 750rpx 113rpx;
  background-repeat: repeat-y;
  .inner-wrap {
    padding: 0 70rpx;
  }

  .gold {
    position: absolute;
    right: 20rpx;
    top: 40rpx;
    width: 200rpx;
    img {
      width: 48rpx;
      height: 48rpx;
      vertical-align: middle;
      margin-right: 10rpx;
    }
    color: #fff;
  }
  .content {
    margin: 0 auto 20rpx auto;
    width: 569rpx;
    height: 572rpx;
    padding: 20rpx;
    position: relative;
    box-sizing: border-box;
    background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newYear/bg-food-content.png");
    background-size: 100%;
    .current-level {
      position: absolute;
      left: 50%;
      transform: translate(-50%);
      top: -50rpx;
      width: 250rpx;
      height: 72rpx;
      line-height: 72rpx;
      text-align: center;
      border: 8rpx solid #53a3f4;
      border-radius: 8rpx;
      font-size: 48rpx;
      font-weight: bold;
      color: #3c3b79;
      background: #ecfffe;
    }
    .image-box {
      padding: 0 30rpx;
      margin: 40rpx 0 20rpx;
      display: flex;
      align-items: center;
      justify-content: center;
      flex-direction: column;
      img {
        max-width: 500rpx;
        max-height: 290rpx;
        margin-bottom: 20rpx;
      }
      .image-title {
        font-size: 30rpx;
        color: #62261c;
      }
    }
    .anwer {
      display: flex;
      justify-content: center;
      position: absolute;
      bottom: 50rpx;
      left: 50%;
      transform: translateX(-50%);

      .text-item {
        width: 76rpx;
        height: 76rpx;
        text-align: center;
        line-height: 76rpx;
        margin-right: 20rpx;
        color: #af6351;
        font-weight: bold;
        font-size: 48rpx;
        color: #fff;
        background: #87c8f2;
        border-radius: 8rpx;
        &:last-child {
          margin-right: 0;
        }
      }
    }
  }
  .select-area {
    margin: 0 auto 20rpx auto;
    width: 90%;
    display: flex;
  }
  .select-list {
    display: flex;
    justify-content: space-around;
    flex-wrap: wrap;
    margin-right: 4rpx;
    .select-item {
      display: inline-block;
      margin-bottom: 10rpx;
      margin-right: 4rpx;
      text-align: center;
      width: 81rpx;
      height: 81rpx;
      line-height: 81rpx;
      background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newYear/bg-food-item.png");
      background-size: 100%;
      color: #3c3b79;
      font-size: 38rpx;
      &:nth-of-type(5n + 5) {
        margin-right: 0;
      }
    }
  }
  .btn-list {
    text-align: center;
    img {
      width: 179rpx;
      height: 101rpx;
      &:last-child {
        margin-right: 0;
      }
    }
    .hint {
      height: 126rpx;
    }
    button {
      width: 179rpx;
      height: 101rpx;
      display: inline-block;
      padding: 0;
    }
  }

  .model-box {
    position: absolute;
    top: 0;
    left: 0;
    z-index: 10;
    width: 100vw;
    height: 100%;
    .mask {
      position: absolute;
      left: 0;
      top: 0;
      width: 100vw;
      height: 100%;
      background: rgba(#000, 0.5);
      z-index: 15;
    }
    .model {
      padding: 210rpx 40rpx 0 40rpx;
      width: 564rpx;
      height: 435rpx;
      box-sizing: border-box;
      background: #fff;
      position: absolute;
      left: 50%;
      top: 20%;
      transform: translateX(-50%);
      z-index: 20;
      border-radius: 8rpx;
      .title {
        width: 238rpx;
        height: 238rpx;
        position: absolute;
        left: 50%;
        transform: translateX(-50%);
        top: -70rpx;
      }
      .close {
        position: absolute;
        right: 15rpx;
        top: 15rpx;
        width: 40rpx;
        height: 40rpx;
      }
      .text {
        color: #eb8d6c;
        font-size: 30rpx;
        box-sizing: border-box;
        padding: 0 30rpx;
        margin-bottom: 34rpx;
        text-align: center;
      }
      .model-btn {
        display: flex;
        justify-content: center;
        align-items: center;
        button {
          height: 72rpx !important;
        }
        .btn-yellow {
          width: 230rpx;
          height: 64rpx;
          line-height: 64rpx;
          color: #a8503b;
          font-size: 30rpx;
          font-weight: normal;
          background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAsAAABKCAIAAADiyjm4AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA39pVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMDY3IDc5LjE1Nzc0NywgMjAxNS8wMy8zMC0yMzo0MDo0MiAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo1YjNkMTY1ZS02MzFhLTRmNGMtYTQ2NS01OTZhYjljOGYzOGEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NzZCMERBRDQ1OEVGMTFFOEJGNkJGNkY0Q0NBQkU0NTEiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NzZCMERBRDM1OEVGMTFFOEJGNkJGNkY0Q0NBQkU0NTEiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIChXaW5kb3dzKSI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOmM3MTM1MDA0LWIwM2MtYTE0Mi05MTVlLWYxYWM0NTdjNGFhYyIgc3RSZWY6ZG9jdW1lbnRJRD0iYWRvYmU6ZG9jaWQ6cGhvdG9zaG9wOmNmMjIzN2IyLTEwMDUtZDQ0Yi04MGFkLWM2YmJhNzQzNDVhNyIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/Pk5LjpsAAABASURBVHjaYvz/bgHDr3sMLEIMrIoMjCwMEPD/N0jw30cGViUmBkJgVMWoilEVoypGVYyqGFUxqmJUxcCqAAgwAPP2CyKDaSfIAAAAAElFTkSuQmCC");
          background-size: 11rpx 74rpx;
          background-repeat: repeat-x;
          text-align: center;
          border-radius: 50px;
          border: 4rpx solid #eeab4c;

          &:active {
            opacity: 0.8;
          }
        }
        .help {
          margin-right: 20rpx;
          padding: 0;
        }
      }
    }
  }
  .pass-model .model {
    width: 570rpx;
    height: 525rpx;
    padding-top: 286rpx;
    .title {
      width: 405rpx;
      height: 393rpx;
      top: -148rpx;
    }
    .text {
      font-size: 48rpx;
    }
    .help {
      margin: 0 !important;
      width: 320rpx !important;
      height: 90rpx !important;
      line-height: 90rpx !important;
      background-size: 11rpx 90rpx !important;
    }
  }
}

.text-mode {
  width: 504rpx;
  height: 394rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  color: #3c3b79;
  font-size: 30rpx;
  font-weight: bold;
  margin: 40rpx auto 0 auto;
}
.ad-box {
  width: 100%;
}
</style>