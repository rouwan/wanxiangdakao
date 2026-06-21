<template>
  <div class="m-ant-box"
       @click="close">
    <img class="u-light"
         :class="{'light-rotate':ant5}"
         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/light.png">
    <div class="m-clear-box"
         :class="{'show-clear-box':ant3}">
      <img class="m-box-bg"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/gift-bg(new).png">
      <div class="c-content">
        <div class="c-head">答题评分</div>
        <div class="c-star-list">
          <div class="c-bg-list">
            <img v-for="(item,index) in starBgList"
                 :key="index"
                 class="c-star-bg"
                 src="/static/star-black.png">
          </div>
          <div class="c-act-list" >
            <img v-for="(item,index) in starActList"
                 :key="index"
                 class="c-star-act"
                 :class="{'show-star':ant4}"
                 src="/static/star.png">
          </div>
        </div>
        <div class="c-line">
          <div>战力提升</div>
        </div>
        <div class="c-fight">
          <div class="f-old">{{oldCombat}}</div>
          <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/arrow.png">
          <div class="f-new">{{gift.combat}}</div>
        </div>
        <div class="c-num-box"
             :class="{'align-center':gift.combatList.length==1}">
          <div v-for="(item,index) in gift.combatList"
               :key="index">{{fightType[item.parentType]}}{{item.combat}}</div>
        </div>
        <div class="c-line">
          <div>获得奖励</div>
        </div>
        <div class="c-gift">
          <img src="/static/entry/energe-normal.png">+{{gift.energe||0}}
        </div>
      </div>
    </div>
    <img class="m-box-top"
         :class="{show:ant1}"
         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/banner.png"
         alt="">
    <div class="u-sheng-box"
         :class="{'word-in':ant2}">
      <img class="u-sheng"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/sheng.png"
           alt="">
    </div>
    <div class="u-li-box"
         :class="{'word-in':ant2}">
      <img class="u-li"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/li.png"
           alt="">
    </div>
    <img class="star-mask"
         :class="{show:ant5}"
         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/star-mask.png">
    <div class="tip" :class="{show:ant6}">点击任意键继续</div>
  </div>
</template>

<script>
export default {
  props:["gift"],
  data() {
    return {
      ant1: false, //大球
      ant2: false, //红猫
      ant3: false, //蓝猫
      ant4: false, //文字
      ant5: false, //横幅
      ant6: false, //星星
      ant7: false, //炫光
      ant8: false, //奖励
      ant9: false, //结算弹窗
      shengIn: false,
      starBgList: [0, 0, 0, 0, 0],
      starActList: [],
      isShowClear: false,
      oldCombat: 0,
      fightType: {
        2: "商+",
        3: "文+",
        4: "理+",
        5: "艺+",
        6: "体+",
        7: "综+"
      },
      // gift: {
      //   energe: 0,
      //   combatList: [{ combat: 1, parentType: 3 }],
      //   rightNum: 5,
      //   combat: 197
      // },
   
    };
  },

  methods: {
    initData() {
      this.starActList = [];
      this.oldCombat = this.gift.combat;
      for (let i = 0; i < this.gift.rightNum; i++) {
        this.starActList.push(0);
      }
      if (this.gift.combatList.length > 0) {
        this.isShowClear = true;
        for (let i = 0; i < this.gift.combatList.length; i++) {
          this.oldCombat =
            this.oldCombat - parseInt(this.gift.combatList[i].combat);
        }
      } else {
        this.isShowClear = false;
      }
    },
    close() {
      this.$emit("close");
      // this.shengIn = !this.shengIn;
    }
  },
  mounted() {
    this.initData();
    setTimeout(() => {
      this.ant1 = true;
      setTimeout(() => {
        this.ant2 = true; //字体跳入
        setTimeout(() => {
          this.ant3 = true; //面板进入
          setTimeout(() => {
            this.ant4 = true;//显示星星
            setTimeout(() => {
              this.ant5=true;
              setTimeout(() => {
                this.ant6=true;
              }, 200);
            }, 200*(this.gift.rightNum));
          }, 200);
        }, 200);
      }, 200);
    }, 500);
  }
};
</script>

<style lang="scss" scoped>
@mixin pos {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
}
.m-ant-box {
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background: rgba(#000, 0.6);
  color: #fff;
  z-index: 20;
}
.u-light,
.u-star,
.u-ball,
.u-ball-shadow,
.u-ball-light,
.u-cat-red,
.u-cat-blue,
.u-banner,
.u-sheng,
.u-sheng-box,
.u-li-box,
.u-li {
  @include pos;
}

.u-light {
  width: 690rpx;
  height: 690rpx;
  top: 128rpx;
  opacity: 0;
  transition: all 0.2s linear;
}

.u-ball {
  width: 400rpx;
  height: 400rpx;
  top: 285rpx;
  transform: translateX(-50%) scale(0);
}
.u-cat-red {
  width: 139rpx;
  height: 141rpx;
  top: 330rpx;
  left: 43%;
  transform: translate(-50%) scale(0);
}
.u-cat-blue {
  width: 146rpx;
  height: 156rpx;
  top: 325rpx;
  left: 57.5%;
  transform: translate(-50%) scale(0);
}
.u-banner {
  width: 586rpx;
  height: 208rpx;
  top: 540rpx;
  opacity: 0;
  transition: all 0.25s ease;
}
.u-banner-in {
  opacity: 1;
  transform: translate(-50%, -60rpx);
}
.u-sheng-box,
.u-li-box {
  width: 132rpx;
  height: 149rpx;
  top: 150rpx;
  transition: all 0.1s ease;
  z-index: 2;
  img {
    width: 100%;
    height: 100%;
  }
}

.u-sheng-box {
  left: 42%;
  transform: translate(-450rpx, -250rpx);
}

.u-li-box {
  left: 60%;
  transform: translate(450rpx, -250rpx);
}
.word-in {
  transform: translate(-50%, -250rpx);
  img {
    // transition: all .25s ease;
    animation: wordScale 0.66s ease forwards;
    transform-origin: bottom;
    // transform: translate(-50%,150rpx);
  }
}
@keyframes wordScale {
  0% {
    transform: translate(-50%, 0) scaleY(0.68);
  }
  25% {
    transform: translate(-50%, 250rpx) scaleY(0.68);
  }
  62.5% {
    transform: translate(-50%, 250rpx) scaleY(1.2);
  }
  100% {
    transform: translate(-50%, 250rpx) scaleY(1);
  }
}

.star-mask {
  width: 717rpx;
  height: 916rpx;
  position: absolute;
  top: 0;
  left: 44%;
  transform: translate(-50%, 0);
  opacity: 0;
  transition: all 0.2s linear;
}

.u-star {
  width: 665rpx;
  height: 571rpx;
  left: 44%;
  top: 200rpx;
  opacity: 0;
  transition: all 0.2s ease;
}
.u-element-in {
  opacity: 1 !important;
}
.u-ball-light {
  width: 746rpx;
  height: 818rpx;
  top: 80rpx;
  opacity: 0;
  transition: all 0.2s ease;
}

//球 阴影
.u-ball-shadow {
  width: 170rpx;
  height: 170rpx;
  border-radius: 50%;
  background: rgba(#000, 0.4);
  transform: translateX(-50%) rotateX(100deg);
  top: 655rpx;
}
.u-gift {
  font-size: 30rpx;
  font-weight: bold;
  color: #fff;
  text-align: center;
  @include pos;
  top: 820rpx;
  opacity: 0;
  transition: all 0.2s linear;
  img {
    width: 185rpx;
    height: 220rpx;
    margin-bottom: 20rpx;
    display: block;
  }
}
// 动画
.ant-1 {
  animation: ant-1 0.29s ease forwards;
}
@keyframes ant-1 {
  0% {
    transform: translateX(-50%) scale(0);
  }
  57% {
    transform: translateX(-50%) scale(1.2);
  }
  100% {
    transform: translateX(-50%) scale(1);
  }
}

.ant-2 {
  animation: ant-2 0.25s ease forwards;
}
// 动画
@keyframes ant-2 {
  0% {
    transform: translateX(-50%) scale(0);
  }
  66.6% {
    transform: translateX(-50%) scale(1.2);
  }
  100% {
    transform: translateX(-50%) scale(1);
  }
}

.ant-3 {
  animation: ant-3 0.25s ease forwards;
}
// 动画
@keyframes ant-3 {
  0% {
    transform: translateX(-50%) scale(0);
  }
  66.6% {
    transform: translateX(-50%) scale(1.2);
  }
  100% {
    transform: translateX(-50%) scale(1);
  }
}

.m-ball-box {
  transition: all 0.2s ease;
}
.hide {
  opacity: 0 !important;
}
// 结算
.m-clear-box {
  width: 627rpx;
  height: 563rpx;
  position: absolute;
  top: 310rpx;
  left: 50%;
  opacity: 0;
  transform: translate(-50%, 50rpx);
  transition: all 0.2s ease;
}
.u-light {
  position: absolute;
  top: 20rpx;
  left: 50%;
  opacity: 0;
  width: 512rpx;
  height: 512rpx;
  z-index: -1;
}
.light-rotate {
  animation: rotate180 27s ease infinite;
}
@keyframes rotate180 {
  0% {
    opacity: 1;
    transform: translateX(-50%) rotate(0);
  }
  100% {
    opacity: 1;
    transform: translateX(-50%) rotate(300deg);
  }
}
.show-clear-box {
  opacity: 1;
  transform: translate(-50%, 0);
}
.m-box-bg {
  width: 100%;
  height: 100%;
}
.m-box-top {
  width: 586rpx;
  height: 208rpx;
  position: absolute;
  top: 180rpx;
  left: 50%;
  opacity: 0;
  transition: all 0.15s linear;
  transform: translateX(-50%);
}
.c-content {
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
  position: absolute;
  z-index: 2;
  left: 0;
  top: 0;
}
.c-head {
  font-size: 32rpx;
  font-weight: bold;
  line-height: 1;
  margin: 44rpx 0 24rpx 0;
  background: linear-gradient(180deg, #fff7df, #ffde5e);
  -webkit-background-clip: text;
  color: transparent;
}
.c-star-list {
  width: 346rpx;
  height: 72rpx;
  position: relative;
  background-size: 305rpx 72rpx;
  background-position: center;
  background-repeat: no-repeat;
  background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAATEAAABICAMAAACp4TLnAAAAn1BMVEUQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTwQDTxCNe9YAAAANHRSTlP67uTpFPbexq6HbVhQSjMLEB6kuZViPHQr1s55J5pntEHyOJ8j0trKwb2pkIyBfV1ELxoYIq2sNQAAA3hJREFUeNrtnImyokAMRaXBfVdcUFlEePv+5v+/baxXOozT0js1JLZfYJ1KbtI3CY3xeDLxvO+v6TTbrdefo1G6XCYfw+Fg0O+/LxZRr7dabd9eu92XZ98PD4fZU6cTz+ePD+32/SYPgrtWixDiuo6zbzYbjcavuv2O/6nZ3DuO67qEtFp3QbDZ3LfbD4/zedzpPM1mh9D3n1+63de37WrV60WLxXu/PxgMhx/JcpmORp/r9S6bTr++PW8yGY+vEUv+Ibb9i9ishJhTe2LOmVhuif1HYruLrKSJheesjE/ENjlEYvmJWCxKbMcjllA6VhpjJ2Ju7YntC2JBfhFjBwNZueQSi/9kJRRiDk2syMrwglhEx5glVkZsWx0xcFnJJOYXxGR1jCIWsWvlBlCMuaK1MqJ1zCAxMB3sniJmMsYScWI5QGJ0zx9yiK3NE3NrT8xhEtPOSn7PD7JWEoby87qL7AqxrKRWsnUMToxJ1Er9DhZBVl7r+WNh5bfEZIlpvcQB69i5u2DomH6MRZyXOJgY+wFWgaMoXSvBKL9QB0u/K1NLTJcYv4PlvMRBOYpE1IPVjzEEjmKh/Co6pqz8IdVdQCSmUCstMVnvwmalFDG+8qcSHiwYt+cITMTnN/1KCiHPK1Vm4onuSzzE5fbE+sR2JcSQ+mO6xDImMf5kZF9zYipZqa9jKGZJEsqfmCMGsFYW/ZjEZCTVdRSREvOrIwa553dFe/6oPCs95Z4/hkxMZpak3Y/B9fmpLQKG8ltifGLG3R4EHuw15Y+VX+Ieb4sAxWSkwj3YlFJ+JLWSFLWyamIoHEWKmL6O8TeHQxQ6FmgrP9+7QLCpckSm9RLP2BcQfA8WoKNYEFOajFhiRvuxBGFWUo6iWR1DqfxFrWR5sPreBZ7tdJV5JT/GbmJemVewOYzwkotNzLjbY7OSTSwVIAbyvlJA+RkeLJOYygUEoOmbfIxZYpXv8yO45GJPRmytvEKMdfUcVrbPT78rIW4RKHiwllgd9vnrn5Wu1o5ixtrtoWdJuL4QwqqVxjeHIbs9KhcQiermMIMYpH6MKBOTUX5MHSwR07FILyux3FcWOqZC7FbvxIW/dKT/vQsku9bnGKuWGKbtdDPEFL8KCLDnV7nk4tdK3F86ItQ2FP8lfst7sCJfCNmWXEBYYuaI4d1U+ZExI18F/A0tLvTWcGozPAAAAABJRU5ErkJggg==");
}
.c-bg-list,
.c-act-list {
  position: absolute;
  width: 100%;
  height: 100%;
  left: 0;
  top: 0;
  display: flex;
  align-items: center;
  img {
    width: 54rpx;
    height: 51rpx;
    margin-right: 16rpx;
    &:last-child {
      margin-right: 0;
    }
  }
}
.c-act-list {
  z-index: 2;
}

.c-star-act{
  opacity: 0;
  transform: scale(0);
  &:nth-of-type(2){
    animation-delay: .15s;
  }
  &:nth-of-type(3){
    animation-delay: .3s;
  }
  &:nth-of-type(4){
    animation-delay: .45s;
  }
    &:nth-of-type(5){
    animation-delay: .6s;
  }
}
.show-star{
  animation:showStar .3s linear forwards;
}
@keyframes showStar {
  0%{
    opacity: 0;
    transform: scale(0);
  }
  57%{
    opacity: 1;
    transform: scale(1.2);
  }
  100%{
    opacity: 1;
    transform: scale(1);
  }

}

.c-line {
  width: 260rpx;
  height: 2rpx;
  margin: 34rpx 0;
  background: #a69ee3;
  color: #a69ee3;
  font-size: 26rpx;
  font-weight: bold;
  position: relative;
  div {
    position: absolute;
    width: 4.5em;
    line-height: 1;
    text-align: center;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    background: #28235c;
  }
}
.c-fight {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 40rpx;
  font-weight: bold;
  div {
    line-height: 1;
  }
  img {
    width: 64rpx;
    height: 27rpx;
    margin: 0 10rpx;
  }
}
.f-old {
  color: #a69ee3;
}
.f-new {
  color: #fcd45f;
}
.c-num-box {
  margin-top: 20rpx;
  width: 200rpx;
  height: 100rpx;
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
  align-content: flex-start;
  div {
    width: 60;
    margin-bottom: 10rpx;
    margin-right: 70rpx;
    text-align: left;
    font-size: 24rpx;
    font-weight: bold;
    color: #7a73c8;
    &:nth-of-type(2n + 2) {
      margin-right: 0;
    }
  }
}
.align-center {
  justify-content: center;
  div {
    margin-right: 0;
  }
}
.c-gift {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 30rpx;
  font-weight: bold;
  img {
    width: 28rpx;
    height: 48rpx;
    margin-right: 10rpx;
  }
}
.tip {
  position: absolute;
  width: 100%;
  left: 0;
  bottom: 132rpx;
  text-align: center;
  color: #fff;
  font-size: 30rpx;
  opacity: 0;
  transition: all .2s linear;
}
.show {
  opacity: 1 !important;
}
</style>
