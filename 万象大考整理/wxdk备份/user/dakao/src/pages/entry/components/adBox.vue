<template>
  <div class="ad-wrap">
    <div class="ad-bg"
         @click="close"></div>
    <div class="ad-box"
         :class="{show:showAd}">
      <div class="ad-btn"
           @click="close"><img src="/static/entry/ad-btn.png"></div>
      <div class="link-item">
        <navigator target="miniProgram"
                   open-type="navigate"
                   app-id="wxee2d09dc74c446a3"
                   @success="countOutNum('梦想大富豪')"
                   @fail="countOutNumFail">
          <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/out-link/fuhao.jpg">
        </navigator>
        <div class="ad-name">梦想大富豪</div>
      </div>
      <div class="link-item">
        <navigator target="miniProgram"
                   open-type="navigate"
                   app-id="wx76b5aeb10ce0e16c"
                   @success="countOutNum('快乐打方块')"
                   @fail="countOutNumFail">
          <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/out-link/dafangkuai.jpg">
        </navigator>
        <div class="ad-name">快乐打方块</div>
      </div>
      <div class="link-item">
        <navigator target="miniProgram"
                   open-type="navigate"
                   app-id="wx26dbc92890a0c876"
                   path="?ald_media_id=5371&ald_link_key=a30281c2aac352dd&ald_position_id=0"
                   @success="countOutNum('喵汪大战')"
                   @fail="countOutNumFail">
          <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/out-link/wangmiao.jpg">
        </navigator>
        <div class="ad-name">喵汪大战</div>
      </div>

     
      <div class="link-item">
        <navigator target="miniProgram"
                   open-type="navigate"
                   app-id="wx08a22523d259abdd"
                   @success="countOutNum('欢乐小黄胖')"
                   @fail="countOutNumFail">
          <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/out-link/xiaohuangpang.jpg">
        </navigator>
        <div class="ad-name">欢乐小黄胖</div>
      </div>
      <div class="link-item">
        <navigator target="miniProgram"
                   open-type="navigate"
                   app-id="wx6b751ee0ef7702fa"
                   @success="countOutNum('我要当妖精')"
                   @fail="countOutNumFail">
          <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/out-link/yaojing.jpg">
        </navigator>
        <div class="ad-name">我要当妖精</div>
      </div>
      
      <div class="link-item">
        <navigator target="miniProgram"
                   open-type="navigate"
                   app-id="wxa059f0becd1b47d4"
                   @success="countOutNum('生肖来了')"
                   @fail="countOutNumFail">
          <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/out-link/shengxiao.png">
        </navigator>
        <div class="ad-name">生肖来了</div>
      </div>
       <div class="link-item">
        <navigator target="miniProgram"
                   open-type="navigate"
                   app-id="wxca338da970e994ba"
                   path="page/index/index"
                   @success="countOutNum('豆小游')"
                   @fail="countOutNumFail">
          <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/out-link/douxiaoyou.png">
        </navigator>
        <div class="ad-name">豆小游</div>
      </div>
       <div class="link-item">
        <navigator target="miniProgram"
                   open-type="navigate"
                   app-id="wx4aa94d3af51b01ce"
                   path="pages/index?source=wxdk&ald_media_id=5220&ald_link_key=e9340db94a15efe9"
                   @success="countOutNum('汽车冲鸭')"
                   @fail="countOutNumFail">
          <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/out-link/qiche.jpg">
        </navigator>
        <div class="ad-name">汽车冲鸭</div>
      </div>
            <div class="link-item">
        <navigator target="miniProgram"
                   open-type="navigate"
                   app-id="wx44385adf4ffe28a8"
                   @success="countOutNum('口袋小黄鸡')"
                   @fail="countOutNumFail">
          <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/out-link/huangji.jpg">
        </navigator>
        <div class="ad-name">口袋小黄鸡</div>
      </div>
    </div>
    <div class="wx-ad">
      <ad unit-id="adunit-1f1cc70776e2e407"></ad>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showAd: false
    };
  },
  methods: {
    close() {
      this.showAd = false;
      setTimeout(() => {
        this.$emit("close");
      }, 200);
    },
    countOutNum(name) {
      console.log("调用跳转广告成功",name);
      this.fly.post("user/userToXcx",{
        name:name,
      });
    },
    countOutNumFail() {
      console.log("调用跳转广告失败");
    }
  },
  mounted() {
    setTimeout(() => {
      this.showAd = true;
    }, 200);
       this.fly.post("user/userToXcx",{
        name:"测试游戏",
        type:1,//领取传1
        status:1, //领取时传 1
        goodType:3,
        goodNum:1
      });
  }
};
</script>

<style lang="scss" scoped>
.ad-wrap {
  z-index: 15;
}
.ad-wrap,
.ad-bg {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
}
.ad-bg {
  background: rgba(#000, 0.8);
}
.ad-box {
  width: 480rpx;
  height: 650rpx;
  position: absolute;
  right: 0;
  top: 120rpx;
  border-radius: 10rpx;
  background: rgba(#fff, 0.5);
  transform: translateX(480rpx);
  transition: all 0.2s ease;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  align-content: flex-start;
  flex-wrap: wrap;
  padding: 50rpx 30rpx;
  box-sizing: border-box;
  .ad-btn {
    position: absolute;
    width: 100rpx;
    height: 100rpx;
    left: -100rpx;
    top: 150rpx;
    border-top-left-radius: 40rpx;
    border-bottom-left-radius: 40rpx;
    background: rgba(#fff, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    img {
      width: 69rpx;
      height: 81rpx;
      transform: rotate(180deg);
    }
  }
  .link-item {
    font-size: 26rpx;
    color: #fff;
    margin-right: 38rpx;
    margin-bottom: 50rpx;
    &:nth-of-type(3n + 4) {
      margin-right: 0;
    }
    .ad-name {
      width: 112rpx;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      margin-top: 20rpx;
      text-align: center;
      line-height: 1;
      font-weight: bold;
    }
  }
  navigator,
  navigator img {
    width: 112rpx;
    height: 112rpx;
    border-radius: 50%;
  }
}
.show {
  transform: translateX(0);
}
.wx-ad {
  position: fixed;
  left: 0;
  bottom: 0;
  width: 100%;
  display: flex;
  justify-content: center;
}
</style>
