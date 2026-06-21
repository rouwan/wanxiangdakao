<template>
  <div class="m-subject sky-bg-2">

    <div class="m-user">
      <img class="u-btn"
           src="/static/ques.png"
           @click="showRule=!showRule">
      <img class="u-head-img-1"
           :src="appInfo.headImgUrl">
      <div class="u-info">
        <div class="u-name">
          {{appInfo.nickname}}
        </div>
        <div class="u-row">
          <div class="u-row-item-small" v-if="dataReady">等级{{mySubject.Result.level}}   {{mySubject.Result.name}}
          </div>
        </div>
        <div class="u-row">
          <div class="process">
            <div class="process-inner" :style="pStyle"></div>
          </div>
        </div>
        <div class="u-row">
          <div class="u-row-item">出题数:
            <div class="u-txt"
                 v-if="dataReady">{{mySubject.Result.totalNum}}</div>
          </div>
          <div class="u-row-item">影响力:
            <div class="u-txt"
                 v-if="dataReady">{{mySubject.Result.effectNum}}</div>
          </div>
        </div>
      
      </div>
    </div>

    <div class="u-btn-set">
      <img :src="wantBtn"
           @click="handRouter('selectSubject')">
    </div>
    <div class="u-btn-subject"
         @click="handRouter('mySubjectList')">
      <img :src="subjectBtn">
    </div>

    <!-- rule -->
    <div class="m-rule"
         v-if="showRule"
         @click="showRule=!showRule">
      <div class="u-rule-head">1、字数要求:</div>
      <div class="u-txt">
        题目字数≤
        <div class="p-fff">30</div>; 选项字数≤
        <div class="p-fff">10</div>; 解析字数≤
        <div class="p-fff">500</div>
      </div>
      <div class="u-rule-head">2、题目要求:</div>
      <div class="u-txt">
        <div class="u-num">①</div>题目要
        <div class="p-fff">完整</div>
      </div>
      <div class="u-txt">
        <div class="u-num">②</div>
        <div class="p-fff">有争议</div>的题目不予通过</div>
      <div class="u-txt">
        <div class="u-num">③</div>涉及
        <div class="p-fff">政治敏感</div>的题目不予通过</div>
      <div class="u-txt">
        <div class="u-num">④</div>解析要
        <div class="p-fff">合理</div>,要对题目进行适当分析</div>
      <div class="u-rule-head">3、注意事项:</div>
      <div class="u-txt">
        <div class="u-num">①</div>为保证题目质量,每个人一天最多出
        <div class="p-fff">50</div>题</div>
      <div class="u-txt">
        <div class="u-num">②</div>如果题目
        <div class="p-fff">通过率过低</div>,将可能被撤销出题权限</div>
      <div class="u-txt">
        <div class="u-num">③</div>为了感谢您对知识传播的贡献,采纳的题目,将在释义区
        <div class="p-fff">标明出题者的头像和名字</div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import subjectApi from "@/api/subjectApi";
export default {
  data() {
    return {
      dataReady: false,
      showRule: false,
      mySubject: {},
      pStyle:"",
      wantBtn:
        "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/subjectFactory/subject-want.png",
      subjectBtn:
        "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/subjectFactory/subject-sub.png"
    };
  },
  computed: {
    ...mapState(["appInfo"])
  },
  methods: {
    handRouter(route) {
      this.$router.push(`/subjectFactoryPages/pages/${route}`);
    }
  },
  async onShow() {
    this.mySubject = await subjectApi.getSubjectNum();
    this.pStyle = `width:${(this.mySubject.Result.passNum/this.mySubject.Result.nextNum)*100}%;`
    this.dataReady = true;
  },
  onUnload() {
    this.showRule = false;
  }
};
</script>

<style lang="scss" scoped>
.m-subject {
  padding-top: 50rpx;
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
}

.m-user {
  position: relative;
  width: 660rpx;
  height: 200rpx;
  margin-bottom: 30rpx;
  border-radius: 30rpx;
  background: #342d64;
  display: flex;
  justify-content: center;
  align-items: center;
  .u-btn {
    width: 50rpx;
    height: 50rpx;
    position: absolute;
    right: -20rpx;
    top: 20rpx;
  }
  .u-head-img-1 {
    width: 152rpx;
    height: 152rpx;
    border-radius: 50%;
    margin-right: 20rpx;
  }
  .u-info {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
    line-height: 1;
    .u-name {
      font-size: 30rpx;
      color: #fff;
      margin-bottom: 16rpx;
      position: relative;
    }
    .u-row {
      display: flex;
      justify-content: flex-start;
      align-items: center;
      font-size: 24rpx;
      color: #a69ee3;
      margin-bottom: 12rpx;
      .u-row-item {
        width: 180rpx;
        text-align: left;
        .u-txt {
          display: inline;
          color: #85d85d;
        }
      }
      .u-row-item-small{
        width: 250rpx;
        color:#9efcfc;
      }
    }

    .process{
      width: 440rpx;
      height: 28rpx;
      padding:8rpx 18rpx;
      background: #fff;
      box-sizing: border-box;
      border-radius: 1000rpx;
      .process-inner{
        width: 0%;
        height: 100%;
        background: #7efde4;
        border-radius: 1000rpx;
      }
    }
  }
}

.u-btn-set,
.u-btn-subject {
  border-radius: 12rpx;
  img {
    width: 100%;
    height: 100%;
  }
}

.u-btn-set {
  width: 660rpx;
  height: 294rpx;
  margin-bottom: 30rpx;
}

.u-btn-subject {
  width: 660rpx;
  height: 274rpx;
}

.m-rule {
  width: 100%;
  height: 100%;
  padding: 150rpx 40rpx 0 40rpx;
  box-sizing: border-box;
  position: fixed;
  left: 0;
  top: 0;
  z-index: 5;
  background: rgba(#28235c, 0.9);
  line-height: 1;
  .u-rule-head {
    font-size: 40rpx;
    color: #7efce4;
    margin-bottom: 35rpx;
    font-weight: bold;
  }
  .u-txt {
    font-size: 30rpx;
    color: #a69ee3;
    margin-bottom: 30rpx;
    line-height: 1.5em;
    .u-num {
      display: inline;
      margin-right: 20rpx;
    }
  }
  .p-fff {
    color: #fff;
    display: inline;
  }
}
</style>
