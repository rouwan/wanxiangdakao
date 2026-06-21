<template>
  <div class="get-gift-box"@click="changeGift">
    <div class="get-gift">
    <img v-if="double" class="double" src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/db-gfit.png">
      <div class="img-box"
           :class="{'showAnt':showAnt}">
        <img class="box-bg"
             src="/static/icon/gift-light.png">
        <img class="img-gift"
             :src="giftList[showIndex].url">
      </div>
      {{giftList[showIndex].name}}X{{giftList[showIndex].num}}
    </div>
  </div>

</template>

<script>
//@component
//props  gift list
// if last  close modal
// giftList=[{url:String,name:String,num:Int}]
export default {
  props: ["giftList","double"],
  data() {
    return {
      showIndex: 0,
      showAnt: false
    };
  },
  methods: {
    changeGift() {
      if (this.showIndex < this.giftList.length - 1) {
        this.showAnt = false;
        setTimeout(() => {
          this.showIndex++;
          this.showAnt = true;
          if (this.showIndex == this.giftList.length - 1) {
            setTimeout(() => {
              this.showAnt = false;
              this.$emit("close");
            }, 1000);
          }
        }, 100);
      }else{
        this.$emit("close");
      }
    }
  },
  mounted() {
    setTimeout(() => {
      this.showAnt = true;
      if(this.giftList.length==this.showIndex+1){
        setTimeout(() => {
          this.$emit("close");
        }, 1500);
      }
    }, 0);
  }
};
</script>

<style lang="scss" scoped>
.get-gift-box {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(#000, .7);
  z-index: 35;
}
.get-gift {
  z-index: 20;
  text-align: center;
  color: #fff;
  font-size: 40rpx;
  font-weight: bold;
}
.get-gift,
.img-gift {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.img-box {
  margin: 0 auto 20rpx auto;
}
.box-bg {
  width: 400rpx;
  height: 400rpx;
  opacity: 0;
}
.img-gift {
  width: 200rpx;
  height: 200rpx;
  top: 40%;
  transform: translate(-50%, -50%) scale(0);
}
.showAnt {
  .box-bg {
    animation: showBg 0.29s ease forwards;
  }
  .img-gift {
    animation: showGift 0.29s ease forwards;
  }
}
@keyframes showBg {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
@keyframes showGift {
  0% {
    transform: translate(-50%, -50%) scale(0);
  }
  57.1% {
    transform: translate(-50%, -50%) scale(1.2);
  }
  100% {
    transform: translate(-50%, -50%) scale(1);
  }
}
.double{
  width: 213rpx;
  height: 57rpx;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  top:-100rpx;
}
</style>
