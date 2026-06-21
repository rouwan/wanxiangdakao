<template>
    <div class="m-modal">
      <div class="u-modal-inner">
        <div class="close" @click="close"></div>
        <div class="u-head">
          <div v-if="showRadio">设置</div>
          <div v-else>公告</div>
          <img class="u-head-img" src="/static/modal-bg.png" alt="">
        </div>
        <div class="u-content">
          <div class="u-radio-box" v-if="showRadio">
            <div class="u-radio" :class="{'u-radio-act':radioOne}" @click="radioChangeOne">
                <div class="u-radio-name">音乐</div>
                <div class="u-radio-body">
                  <div class="u-radio-ball"></div>
                  <div class="u-radio-txt-act">开</div>
                  <div class="u-radio-txt">关</div>
                </div>
              </div>
              <div class="u-radio" :class="{'u-radio-act':radioTwo}" @click="radioChangeTwo">
                <div class="u-radio-name">音效</div>
                <div class="u-radio-body">
                  <div class="u-radio-ball"></div>
                  <div class="u-radio-txt-act">开</div>
                  <div class="u-radio-txt">关</div>
                </div>
              </div>
          </div>

          <div class="theme" v-if="showRadio">
            <div class="t-t">
              主题选择
            </div>
            <div class="t-radio-box" v-for="(item,index) in radioList" :key="index" :class="{'selected':index==radioIndex}">
              <div class="t-radio" @click="changeTheme(index)"></div>
              <div class="t-radio-label" :style="item.color"@click="changeTheme(index)">{{item.name}}</div>
              <div class="t-btn"@click="viewTheme(index)">预览</div>
            </div>
           
          </div>

          <div class="u-notice-box" v-else>
            <div class="u-notice-head">游戏宗旨:</div>
            <div class="u-notice-content">公平竞技、巩固知识！</div>
            <div class="u-notice-head">温馨提示:</div>
            <div class="u-notice-content">适度游戏益脑，沉迷游戏伤身，</div>
            <div class="u-notice-content">合理安排时间，享受生活快乐！</div>
            <div class="u-notice-content">添加微信号：bingiling，进入万象大考互助群</div>
            
          </div>

            <div class="u-btn-box">
              <div class="u-btn u-btn-red" v-if="showRadio" @click="showRadio=false">公告</div>
              <div class="u-btn u-btn-red" v-else @click="showRadio=true">设置</div>
              <button  class="u-btn u-btn-green" open-type="contact">客服</button>
            </div>

        </div>
    
        <!-- <div class="advice-submit" @click="submitAdvice">提交</div> -->
      </div>


       <div class="theme-view" v-if="showThemeView" @click="showThemeView=false">
          <img v-if="viewIndex==0" src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/theme-1.png">
          <img v-if="viewIndex==1" src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/theme-2.png">
        </div>
    </div>
</template>

<script>
import {mapState,mapMutations} from "vuex";
export default {
  name:"settingModal",
  props:[],
  data(){
    return{
      showRadio:true,
      radioOne:false,
      radioTwo:false,
      showThemeView:false,
      radioIndex:0,
      viewIndex:0,
      radioList:[
        {name:"星空",color:"color:#a065ea"},
        {name:"天蓝",color:"color:#557ef3"}
      ]
    }
  },
  computed:{
    ...mapState([""]),
  },
  methods:{
    ...mapMutations(["setGamePageType"]),
    radioChangeOne(){
      this.radioOne=!this.radioOne;
      this.$emit('radioChangeOne',this.radioOne);
    },
    radioChangeTwo(){
      this.radioTwo=!this.radioTwo;
      this.$emit('radioChangeTwo',this.radioTwo);
    },
    close(){
      this.$emit('close');
    },
    viewTheme(index){
      this.showThemeView = true;
      this.viewIndex = index;
    },
    changeTheme(index){
      this.radioIndex = index;
      this.setGamePageType(index);
      wx.setStorage({
        key: "themeSet",
        data:index,
      });
    },
    getSetting(){
      wx.getStorage({
        key: "musicObj[bg]",
        success:res=>{
            this.radioOne=res.data;
        },
        fail: () => {
            this.radioOne=true;
        }
      });
      wx.getStorage({
        key:"themeSet",
        success:res=>{
          this.radioIndex=res.data;
        },
        fail: () => {
          this.radioIndex=0;
        }
      })
       wx.getStorage({
        key: "musicObj[sound]",
        success:res=>{
            this.radioTwo=res.data;
        },
        fail: () => {
            this.radioTwo=true;
        }
      });
    },
  },
  mounted(){
    this.getSetting();
  },
}
</script>

<style lang="scss" scoped>
@import url("../../scss/s-modal.scss");
.mt108{
  margin-top: 108rpx;
}
.mb108{
  margin-bottom: 108rpx;
}
.m-modal{
  background: rgba(#000,.55);
}
.u-content{
  box-sizing: border-box;
}
.u-radio-box{
  display: flex;
  justify-content:center;
  align-items: center;
  height: 212rpx;
  border-bottom: 2rpx solid #d6d6d6;
  margin-bottom: 30rpx;
}
.u-radio{
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  font-size: 30rpx;
  margin-right: 120rpx;
  font-weight: bold;
  &:last-child{
    margin-right: 0;
  }
}
.u-radio-name{
  color: #a69ee3;
  margin-right: 50rpx;
  text-align: left;
  font-weight: bold;
  margin-bottom: 20rpx;
}
.u-radio-body{
  width: 160rpx;
  height: 58rpx;
  padding: 0 30rpx;
  box-sizing: border-box;
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #fff;
  background:#d6d6d6;
  transition:all .2s ease;
  border-radius: 200rpx;
}
.u-radio-ball{
  width: 48rpx;
  height: 48rpx;
  border-radius: 50%;
  background: #fff;
  position: absolute;
  top: 50%;
  left: 10rpx;
  transform: translate(0,-50%);
  transition:all .2s ease;
}
.u-radio-txt{
  opacity: 1;
  transition:all .2s ease;
  
}
.u-radio-txt-act{
  opacity: 0;
  transition:all .2s ease;
}
.u-radio-act .u-radio-body{
  background: #85d85d;
}
.u-radio-act .u-radio-ball{
  transform: translate(190%,-50%);
}
.u-radio-act .u-radio-txt{
  opacity: 0;
}
.u-radio-act .u-radio-txt-act{
  opacity: 1;
}

.u-notice-box{
  text-align: left;
  margin-bottom: 100rpx;
}
.u-notice-head{
  font-size: 36rpx;
  font-weight: bold;
  color:#a69ee3;
  padding-top: 40rpx;
  margin-bottom: 10rpx;
}
.u-notice-content{
  font-size: 30rpx;
  color: #000;
}

.theme{
  width: 100%;
}
.t-t{
  font-size: 36rpx;
  font-weight: bold;
  color:#a69ee3;
  line-height: 1;
  margin-bottom: 30rpx;
  text-align:left;
}
.t-radio-box{ 
  display: flex;
  align-items: center;
  margin-bottom: 40rpx;
}
.t-radio{
  width: 40rpx;
  height: 40rpx;
  margin-right: 16rpx;
  border-radius: 50%;
  border: 2rpx solid #d6d6d6;
  display: flex;
  justify-content:center;
  align-items: center;
  &::after{
    width: 26rpx;
    height: 26rpx;
    content: "";
    background: #ff217b;
    border-radius: 50%;
    opacity: 0;
  }
}
.selected .t-radio::after{
  opacity: 1!important;
}
.t-radio-label{
  font-size: 36rpx;
  font-weight: bold;
  margin-right: 40rpx;
}
.t-btn{
  height: 62rpx;
  width: 150rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 30rpx;
  font-weight: bold;
  color: #fff;
  background: #85d85d;
  border-radius: 10rpx;
}
.theme-view{
  position: fixed;
  left: 0;
  top:0;
  width: 100%;
  height: 100%;
  background: rgba(#000,.55);
  z-index: 20;
  display: flex;
  justify-content: center;
  align-items: center;
  img{
    width: 630rpx;
    height: 1100rpx;
    border: 10rpx solid #fff;
    border-radius: 10rpx;
    box-sizing: border-box;
  }
}
</style>
