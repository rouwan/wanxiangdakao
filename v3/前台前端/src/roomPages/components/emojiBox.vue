<template>
  <div class="m-emoji" @click="close">
    <div class="u-wrap">
      <swiper :indicator-dots="false" :autoplay="false" :current="sIndex" duration="300" @animationfinish="handleChange">
        <swiper-item>
          <div class="u-img-box" v-for="(item,index) in arr1" :key="index" @click.stop="throttle(index,400)">
            <img :src="item.imgUrl">
          </div>
        </swiper-item>
         <swiper-item>
           <div class="u-img-box" v-for="(item,index) in arr2" :key="index"@click.stop="throttle(index+6,400)">
            <img :src="item.imgUrl">
           </div>
        </swiper-item>
      </swiper>
      <div class="u-dot">
        <div :class="{'active':sIndex==0}"></div>
        <div :class="{'active':sIndex==1}"></div>
      </div>
    </div>
    
  </div>
</template>

<script>

import {emoji} from "@/config/dataFile"
export default {
  data(){
    return{
      sIndex:0,
      sendTimer:null,
      throttleTimer:null,
      sendTime:0,
      arr1:[],
      arr2:[],
    }
  },
  watch:{
    sendTime(val){
      if(val>=5){
        clearInterval(this.sendTimer);
        this.sendTime=0;
      }
    },
  },
  computed:{
    appInfo(){
      return this.store.state.appInfo;
    }
  },
  methods:{
    handleChange(event){
        this.sIndex = event.mp.detail.current;
    },
    close(){
      this.$emit("close");
    },
    throttle(index,delay) {
      clearTimeout(this.throttleTimer);
      this.throttleTimer = setTimeout(()=> {
        this.sendEmoji(index);
      }, delay);
    },
    sendEmoji(index){
      if(this.sendTime==0){
        this.sendTimer=setInterval(()=>{
          this.sendTime++;
        },1000)
        let emojiInfo=`{index:${index},userId:${this.appInfo.userId},userName:'${this.appInfo.nickname}'}`;
        this.close();
        this.$emit("sendEmoji",emojiInfo);
      }else{
        wx.showToast({title:"5s内只能发一次",icon:"none"});
      }
    },
  },
  mounted(){
    this.arr1 = emoji.slice(0,6);
    this.arr2 = emoji.slice(6);
  },
}
</script>

<style lang="scss" scoped>
.m-emoji{
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(#000,.55);
  display: flex;
  justify-content: center;
  z-index: 10;
}
.u-wrap{
  margin-top: 60rpx;
  width: 600rpx;
  height: 945rpx;
  background-size:100%;
  background-image: url("data:image/png;base64,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");
  padding: 50rpx 10rpx 105rpx 10rpx;
  box-sizing: border-box;
}
swiper{
  width: 100%;
  height: 720rpx;
}
swiper-item{
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  .u-img-box,img{
    width: 264rpx;
    height: 210rpx;
  }

}
.u-dot{
  width: 100%;
  height: 100rpx;
  display: flex;
  justify-content: center;
  align-items: center;
  div{
    width: 22rpx;
    height: 22rpx;
    background: #29234f;
    border-radius: 50%;
    margin-right: 12rpx;
    &:last-child{
      margin-right: 0;
    }
  }
  .active{
    background: #ff217b;
  }
}


</style>
