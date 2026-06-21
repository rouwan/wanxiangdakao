<template>
  <div class="m-book">
    <img class="u-bg"
         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/book-bg.png">
    <div class="book-content">
      <div class="book-head">
        <img src="/static/icon/book-head.png">
        <!-- 兑换券 -->
        <div class="card" v-if="false">
          <img class="card-img"
               src="/static/icon/book-card.png"> {{ticket}}
          <img class="card-tip"
               :class="{show:showTip}"
               src="/static/icon/book-tip.png">
        </div>
      </div>
      <div class="book-list"
           v-if="dataReady">
        <div class="book-item"
             v-for="(item,index) in bookList"
             :key="index">
          <div class="item-left">
            <img class="book-bg"
                 src="/static/icon/book-box-bg.png">
            <img class="book-book"
                 :src="bookType[item.type].img">
            <div class="book-name">{{bookType[item.type].bookName}}科</div>
          </div>
          <div class="item-center">
            <div class="grade">
              <div>
                等级{{item.level}}
              </div>
              <div class="g-txt"
                   v-if="item.level==maxLevel">{{item.num}}/
                <div class="big-txt">8</div>
              </div>
              <div class="g-txt"
                   v-else>{{item.num}}/{{item.nextNum}}</div>
            </div>
            <div class="process">
              <img v-if="item.num>=item.nextNum||item.level==maxLevel"
                   class="full"
                   src="/static/icon/book-p-act.png">
              <div class="process-inner"
                   :style="bookType[item.type].pWidth"></div>
            </div>
            <div>{{bookType[item.type].bookName}}科得分+{{item.addition}}%</div>
          </div>
          <div class="item-right">
            <img class="l-full"
                 v-if="item.level>=maxLevel"
                 src="/static/icon/book-manji.png">
            <img class="l-btn"
                 v-else-if="item.num<item.nextNum"
                 src="/static/icon/book-btn.png">
            <img class="l-btn"
                 v-else-if="item.num>=item.nextNum"
                 src="/static/icon/book-btn-act.png"
                 @click="update(item)">
          </div>
        </div>
        <div class="place"></div>
        <div class="place"></div>
      </div>
    </div>

    <div class="ad-box">
      <ad unit-id="adunit-275f5d9761ad9db1"></ad>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
// import lottery from "@/components/public/lottery";
export default {
  data() {
    return {
      showTip: false,
      dataReady: false,
      ticket: 0,
      maxLevel: 30,
      bookList: [],
      bookType: {
        2: {
          bookName: "商",
          img: "/static/icon/shang.png",
          pWidth: "width:0%;"
        },
        3: { bookName: "文", img: "/static/icon/wen.png", pWidth: "width:0%;" },
        4: { bookName: "理", img: "/static/icon/li.png", pWidth: "width:0%;" },
        5: { bookName: "艺", img: "/static/icon/yi.png", pWidth: "width:0%;" },
        6: { bookName: "体", img: "/static/icon/ti.png", pWidth: "width:0%;" },
        7: { bookName: "综", img: "/static/icon/zong.png", pWidth: "width:0%;" }
      },
      typeMap: {
        3: 8,
        4: 9,
        5: 10,
        6: 11,
        2: 7,
        7: 12
      }
    };
  },
  computed: {
    ...mapState(["goodStorageList","appInfo"])
  },
  methods: {
    ...mapMutations(["setSingleGoodNum"]),
    handleShowTip() {
      setTimeout(() => {
        this.showTip = true;
        setTimeout(() => {
          this.showTip = false;
        }, 5000);
      }, 2000);
    },
    getList() {
      this.fly.post("/book/getUserBookList").then(res => {
        this.bookList = res.data.Result.bookList;
        this.ticket = res.data.Result.ticket;
        this.maxLevel = res.data.Result.maxBookLevel;
        for (let i = 0; i < this.bookList.length; i++) {
          let p = this.bookList[i].num / this.bookList[i].nextNum * 100;
          p >= 100 ? (p = 100) : (p = p);
          this.bookType[this.bookList[i].type].pWidth = `width:${p}%;`;
        }
        this.dataReady = true;
      });
    },
    update(item) {
      this.fly
        .post("book/upLevelByType", {
          type: item.type
        })
        .then(res => {
          if (res.data.Tag == 1) {
            if (this.goodStorageList.length > 0) {
              this.setSingleGoodNum({
                type: this.typeMap[item.type],
                num: item.num - item.nextNum
              });
            }

            item.level = res.data.Result.level;
            item.num = res.data.Result.num;
            item.addition = res.data.Result.addition;
            item.nextNum = res.data.Result.nextNum;
            this.bookType[item.type].pWidth = `width:${
              item.num / item.nextNum * 100 >= 100
                ? 100
                : item.num / item.nextNum * 100
            }%`;
          }
        });
    },
  },
  mounted() {
    // this.handleShowTip();
    this.getList();
  }
};
</script>

<style lang="scss" scoped>
@mixin flex {
  display: flex;
  justify-content: center;
  align-items: center;
}
@mixin v-center {
  top: 50%;
  transform: translateY(-50%);
}
@mixin h-center {
  left: 50%;
  transform: translateX(-50%);
}
@mixin center {
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.m-book {
  width: 100%;
  height: 100%;
  background-color: "#39256c";
}
.u-bg {
  position: fixed;
  left: 0;
  top: 0;
  width: 750rpx;
  height: 100%;
}

.book-content {
  position: fixed;
  width: 100%;
  height: 100%;
  left: 0;
  top: 0;
  z-index: 2;
  overflow: scroll;
  padding-top: 186rpx;
}

.book-head {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 186rpx;
  background: #39256c;
  @include flex;
  z-index: 5;
  & > img {
    width: 313rpx;
    height: 98rpx;
  }
  .card {
    position: absolute;
    @include flex;
    right: 40rpx;
    bottom: 14rpx;
    min-width: 100rpx;
    height: 44rpx;
    line-height: 44rpx;
    text-align: center;
    border-radius: 20rpx;
    border: 4rpx solid #37317f;
    background: #50569e;
    box-sizing: border-box;
    color: #7ef9e1;
    font-size: 28rpx;
    .card-img {
      width: 57rpx;
      height: 55rpx;
      position: absolute;
      @include v-center;
      left: -40rpx;
    }
    .card-tip {
      width: 198rpx;
      height: 67rpx;
      position: absolute;
      bottom: -80rpx;
      left: -80rpx;
      opacity: 0;
      transition: all 0.2s ease;
      z-index: 5;
    }
  }
}

.show {
  opacity: 1 !important;
}

.book-list {
  width: 100%;
  box-sizing: border-box;
  padding: 0 40rpx;
}
.book-item {
  width: 100%;
  height: 154rpx;
  @include flex;
  justify-content: flex-start;
  background: rgba(#000, 0.1);
  border-radius: 10rpx;
  font-size: 26rpx;
  color: #fff;
}
.item-left {
  @include flex;
  align-items: flex-start;
  position: relative;
  height: 100%;
  padding: 10rpx 0 0 16rpx;
  margin-right: 40rpx;
  box-sizing: border-box;
  .book-bg {
    width: 119rpx;
    height: 117rpx;
    border: 4rpx solid #20d0c2;
    box-sizing: border-box;
    border-radius: 50%;
  }
  .book-book {
    width: 100rpx;
    height: 100rpx;
    position: absolute;
    @include center;
    left: 55%;
    top: 45%;
  }
  .book-name {
    width: 80rpx;
    height: 36rpx;
    text-align: center;
    background: #20d0c2;
    position: absolute;
    @include h-center;
    @include flex;
    bottom: 10rpx;
    border-radius: 10rpx;
  }
}

.book-item {
  position: relative;
  margin-bottom: 14rpx;
  &:nth-of-type(2) {
    .item-left {
      .book-bg {
        border-color: #39b54a;
      }
      .book-name {
        background: #39b54a;
      }
    }
  }
  &:nth-of-type(3) {
    .item-left {
      .book-bg {
        border-color: #c848ab;
      }
      .book-name {
        background: #c848ab;
      }
    }
  }
  &:nth-of-type(4) {
    .item-left {
      .book-bg {
        border-color: #476dc8;
      }
      .book-name {
        background: #476dc8;
      }
    }
  }
  &:nth-of-type(5) {
    .item-left {
      .book-bg {
        border-color: #c79646;
      }
      .book-name {
        background: #c79646;
      }
    }
  }
  &:nth-of-type(6) {
    .item-left {
      .book-bg {
        border-color: #c86d48;
      }
      .book-name {
        background: #c86d48;
      }
    }
  }
}

.item-center {
  width: 345rpx;
  @include flex;
  flex-direction: column;
  align-items: flex-start;
}

.grade {
  width: 476rpx;
  @include flex;
  justify-content: space-between;
  align-items: center;
}
.g-txt {
  @include flex;
}
.big-txt {
  transform: rotate(90deg);
  margin-left: 8rpx;
}
.process {
  position: relative;
  width: 100%;
  height: 24rpx;
  border: 4rpx solid #fff;
  box-sizing: border-box;
  border-radius: 2000rpx;
  margin: 10rpx 0;
  img {
    width: 384rpx;
    height: 56rpx;
    position: absolute;
    @include center;
    opacity: 1;
  }
  .process-inner {
    height: 100%;
    width: 0%;
    border-radius: 2000rpx;
    background: #eb613a;
    transition: all 0.5s ease;
  }
}
.item-right {
  position: absolute;
  right: 0;
  @include v-center;
  .l-btn {
    position: absolute;
    @include v-center;
    right: -10rpx;
    width: 171rpx;
    height: 103rpx;
    transform: translateY(-48%);
  }
  .l-full {
    width: 120rpx;
    height: 86rpx;
    margin-right: 10rpx;
  }
}
.place {
  width: 100%;
  height: 400rpx;
}
.ad-box {
  position: fixed;
  width: 100%;
  bottom: 0;
  left: 0;
  z-index: 5;
}
</style>
