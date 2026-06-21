<template>
  <div class="gift-box"@click="close">
    <img class="light-bg"
         :class="{show:showBg}"
         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/box-light.png">
    <img class="box-head"
          :class="{show:showEl}"
         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/box-head.png">
    <div class="book-wrap":class="{show:showEl,antBook:showBook}" v-if="bookListFormat.length>0">
      <div class="book" v-for="(item,index) in bookListFormat" :key="index">
        <img v-if="!isMaxLv" :src="bookMap[item.type].img">
        <div v-if="!isMaxLv" class="book-name">{{bookMap[item.type].name}}X{{item.num}}</div> 
        <img v-if="isMaxLv" :src="bottleMap[item.type].img">
        <div v-if="isMaxLv" class="book-name">{{bottleMap[item.type].name}}X{{item.num}}</div> 
      </div>
    </div>
    <div class="box-wrap" :class="{'box-shake':antBox}">
      <img class="b-b" :class="{'boxBottomAnt':antBoxBottom}":src="boxMap[type].bottom">
      <img class="b-l" :class="{show:showBook}":src="boxMap[type].light">
      <img class="b-t" :class="{'boxTopAnt':antBoxTop}" :src="boxMap[type].top">
    </div>

  </div>
</template>

<script>
export default {
  props:["bookList","type","isMaxLv"],
  data() {
    return {
      antBox:false,
      antBoxTop:false,
      antBoxBottom:false,
      showBg:false,
      showEl:false,
      showBook:false,
      type:4, //宝箱类型
      boxMap:{
        4:{
          top:"https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/s-b-t.png",
          light:"https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/s-b-l.png",
          bottom:"https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/s-b-b.png",
        },
        5:{
          top:"https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/m-b-t.png",
          light:"https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/m-b-l.png",
          bottom:"https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/m-b-b.png",
        },
        6:{
          top:"https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/b-b-t.png",
          light:"https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/b-b-l.png",
          bottom:"https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/b-b-b.png",
        },
      },
      bookMap: {
        2:{img:"/static/icon/shang.png",name:"商科"},
        3:{img:"/static/icon/wen.png",name:"文科"},
        4:{img:"/static/icon/li.png",name:"理科"},
        5:{img:"/static/icon/yi.png",name:"艺科"},
        6:{img:"/static/icon/ti.png",name:"体科"},
        7:{img:"/static/icon/zong.png",name:"综科"},
      },
      bottleMap:{
        1:{img:"/static/icon/s-eng.png",name:"小体力瓶"},
        2:{img:"/static/icon/m-eng.png",name:"中体力瓶"},
        3:{img:"/static/icon/b-eng.png",name:"大体力瓶"},
      },
      bookListFormat:[],
    };
  },
  methods:{
    close(){
      if(this.showBook){
        this.$emit("close");
      }
    },
    formatList(){
      this.bookListFormat=[];
      for(let key in this.bookList){
        this.bookListFormat.push({
          type:key,
          num:this.bookList[key]
        })
      }
    },
  },
  mounted(){
    this.formatList();
    setTimeout(() => { //boxShake
      this.antBox=true;
      setTimeout(() => { //boxTop
        this.antBoxTop=true;
        this.antBoxBottom=true;
        setTimeout(()=>{ //show Bg
          this.showBg = true;
          setTimeout(() => {//show book
            this.showEl = true;
             this.showBook = true;
             if(this.isMaxLv){
               wx.showToast({title:"知识书已满",icon:"none"});
             }
          }, 200);
        },750)
      }, 1000);
    }, 500);
  },
};
</script>

<style lang="scss" scoped>
.gift-box {
  position: fixed;
  width: 100%;
  height: 100%;
  left: 0;
  top: 0;
  background: rgba(#000, 0.88);
  display: flex;
  flex-direction: column;
  align-items: center;
  z-index: 100;
}
.box-head {
  width: 437rpx;
  height: 162rpx;
  margin-top: 58rpx;
  margin-bottom: 40rpx;
  opacity: 0;
  transition: all .2s ease;
}
.light-bg {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  width: 1031rpx;
  height: 1665rpx;
  opacity: 0;
  transition: all 0.3s ease;
}
.book-wrap {
  width: 450rpx;
  height: 400rpx;
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  align-items: flex-start;
  color: #fff;
  font-size: 30rpx;
  font-weight: bold;
  text-align: center;
  opacity: 0;
  transition: all .2s ease;
  .book {
    margin-right: 30rpx;
    &:nth-of-type(3n+3){
      margin-right: 0;
    }
    img {
      width: 125rpx;
      height: 125rpx;
      display: block;
      transform: scale(0);
    }
  }
}
 .antBook{
    .book{
      img{
        animation: scaleTobig .4s ease forwards;
      }
    }
  }
  @keyframes scaleTobig {
    0%{
      transform: scale(0);
    }
    57%{
      transform: scale(1.2);
    }
    100%{
      transform: scale(1);
    }
  }


.box-wrap{
  width: 300rpx;
  height: 300rpx;
  position: absolute;
  left: 50%;
  top: 360rpx;
  transform: translateX(-50%);
  transform-origin: center;

  img{
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
  }
  .b-t{
    width: 300rpx;
    height: 300rpx;
  }
  .b-l{
    width: 292rpx;
    height: 216rpx;
    top: -54rpx;
    opacity: 0;
    transition: all .2s ease;
    transform: translate(-50%,328rpx);
  }
  .b-b{
    width: 300rpx;
    height: 300rpx;
  }

}
.show{
  opacity: 1!important;
}
.box-shake{
  animation:boxShake .3s linear;
  animation-iteration-count:3;
}
@keyframes boxShake {
  0%,50%,100%{
    transform: translateX(-50%) rotate(0);
  }
  25%{
    transform: translateX(-50%) rotate(5deg);
  }
  75%{
    transform: translateX(-50%) rotate(-5deg);
  }
}

.boxTopAnt{
  animation:boxTopAnt .18s ease-out forwards;
}
@keyframes boxTopAnt {
  0%{
    opacity: 1;
    transform:translate(-50%,0);
  }
  100%{
    opacity: 0;
    transform: translate(-50%,-122rpx);
  }
}

.boxBottomAnt{
  animation:boxBottomAnt .75s ease-out forwards;
}
@keyframes boxBottomAnt {
  0%{
    transform:translate(-50%,0);
  }
  33%{
    transform: translate(-50%,328rpx);
  }
  44%{
    transform: translate(-50%,328rpx) rotate(8deg);
  }
  55%,77%,100%{
    transform: translate(-50%,328rpx) rotate(0);
  }
  66%{
    transform: translate(-50%,328rpx) rotate(-5deg);
  }
  88%{
    transform: translate(-50%,328rpx) rotate(2deg);
  }
}
</style>
