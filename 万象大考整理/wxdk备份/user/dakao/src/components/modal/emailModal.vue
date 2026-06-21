<template>
    <div class="m-modal">
      <div class="u-modal-inner">
        <div class="close" @click="close"></div>
        <div class="u-head">
          <div>邮件</div>
          <img class="u-head-img" src="/static/modal-bg.png" alt="">
        </div>
        <div class="u-content">
          <div class="u-content-inner">
              <div class="u-email" :class="{'show-inner':showIndex==index}" v-for="(item,index) in emailList":key="index">
                <div class="u-email-head">
                  <img class="gift" src="/static/icon/gift.png">
                  <div class="u-basic-info">
                    <div class="u-title">{{item.title}}</div>
                    <div class="u-time">{{item.createTime}}</div>
                  </div>
                  <div class="u-btn u-btn-samll u-btn-red" v-if="item.isReceive==0&&item.gift&&showIndex==index" @click="updateEmail(item,index,true)">领取</div>
                  <div class="u-btn u-btn-samll u-btn-green" v-if="showIndex!=index" @click="updateEmail(item,index,false)">查看</div>
                  <div class="u-btn u-btn-samll u-btn-green" v-if="(showIndex==index&&!item.gift)||(item.gift&&item.isReceive==1&&showIndex==index)" @click="showIndex=-1">收回</div>
                </div>
                <div class="u-email-content">
                    <div class="u-txt">亲爱的用户:</div>
                    <div class="u-txt u-txt-indent">{{item.content}}</div>
                    <div class="u-gift" v-if="item.gift&&item.isReceive==0">
                      <div class="u-gift-item" v-for="(good,gIndex) in giftList[index]" :key="gIndex">
                        <img :src="goodMap[good.type]">
                        <div class="u-gift-num">{{good.num}}</div>
                      </div>
                    </div>
                </div>
                <img class="u-back" src="/static/icon/back.png"@click="showIndex=-1">
              </div>
          </div>
          <div class="u-tip">邮件只保存7天</div>
        </div>
      </div>
        <get-gift v-if="showGetGift"
              :giftList="showGiftList"
              @close="showGetGift=false"></get-gift>
    </div>
</template>

<script>
import {mapState,mapMutations} from "vuex";
import getGift from "@/components/gift/get-gift";

export default {
  name:"settingModal",
  props:[],
  components:{
    "get-gift":getGift,
  },
  data(){
    return{
      show:false,
      showIndex:-1,
      showGetGift:false,
      showGiftList:[],
      giftList:[],
      goodMap:{
        1:"/static/icon/s-eng.png",
        2:"/static/icon/m-eng.png",
        3:"/static/icon/b-eng.png",
        4:"/static/icon/s-box.png",
        5:"/static/icon/m-box.png",
        6:"/static/icon/b-box.png",
      },
      nameMap:{
        1:"小体力瓶",
        2:"中体力瓶",
        3:"大体力瓶",
        4:"小宝箱",
        5:"中宝箱",
        6:"大宝箱",
      },
    }
  },
  computed:{
    ...mapState(["emailList","appInfo","goodStorageList"]),
  },
  methods:{
    ...mapMutations(["setEmailList"]),
    getEmailList(){
      if(this.emailList.length==0){
        this.fly.post('/email/getEmailList').then(res=>{
          this.setEmailList(res.data.Result);
          this.getGiftList(res.data.Result);
        })
      }else{
        this.getGiftList(this.emailList);
      }
    },
    getGiftList(data){
      let list=[];
      for(let i=0;i<data.length;i++){
        if(data[i].gift){
          let innerList = [];
          let gift = data[i].gift.split(";");
          for(let j=0;j<gift.length;j++){
            if(gift[j].length>2){
              let giftItem = gift[j].split(",");
              innerList.push({type:giftItem[0],num:giftItem[1]});

            }
          }
          list.push(innerList);
        }else{
          list.push([]);
        }
      }
      this.giftList = list;
      
    },
    updateEmail(item,index,isHandle){
      this.showIndex = index;
      if((item.isReceive==0&&!item.gift)||(item.isReceive==0&&isHandle)){
        this.fly.post("email/updateStatus",{
          emailId:item.id,
          type:item.gift?2:1,
        }).then(res=>{
          if(res.data.Tag){
            this.showGiftList = [];
            for(let i=0;i<this.giftList[index].length;i++){
              let type=this.giftList[index][i].type;
              let num = this.giftList[index][i].num;
              this.showGiftList.push({name:this.nameMap[type],num:num,url:this.goodMap[type]});
            }
            this.showGetGift=true;
            item.isReceive = 1;
            this.appInfo.emailNoReadNum--;
            if(item.gift&&this.goodStorageList.length>0){
              for(let i=0;i<this.giftList[index].length;i++){
                for(let j=0;j<this.goodStorageList.length;j++){
                  if(this.giftList[index][i].type==this.goodStorageList[j].type){
                    this.goodStorageList[j].num=parseInt(this.giftList[index][i].num)+parseInt(this.goodStorageList[j].num);
                  }
                }
              }

              for(let i=0;i<this.giftList[index].length;i++){
                for(let j=0;j<this.goodStorageList.length;j++){
                  if(this.giftList[index][i].type==this.goodStorageList[j].type){
                    break;
                  }
                  if(this.giftList[index][i].type!=this.goodStorageList[j].type&&j==this.goodStorageList.length-1){
                    this.goodStorageList.push(this.giftList[index][i]);
                  }
                }
              }
              // 物品列表排序
              this.goodStorageList.sort(this.keysrt("type",false));
            }
          }
        })
      }
      
    },
  keysrt(key,desc) {
    return function(a,b){
      return desc ? ~~(a[key] < b[key]) : ~~(a[key] > b[key]);
    }
  },
    close(){
      this.$emit('close');
    },

  },
  mounted(){
    this.getEmailList();
  },

}
</script>

<style lang="scss" scoped>
@import url("../../scss/s-modal.scss");
.m-modal{
  background: rgba(#000,.55);
}
.u-modal-inner{
  top:90rpx;
}
.u-content{
  box-sizing: border-box;
  border-bottom: 30rpx;
  padding: 20rpx 12rpx 0 12rpx;
}

.u-content-inner{
  width: 100%;
  height: 724rpx;
  background: #cdc7fb;
  border-radius: 6rpx;
  padding: 18rpx;
  box-sizing: border-box;
  overflow: scroll;
}

.u-email{
  width: 100%;
  padding: 0 20rpx;
  box-sizing: border-box;
  margin-bottom: 32rpx;
  background: #fff;
  border-radius: 10rpx;
  height: 108rpx;
  overflow: hidden;
  transition: all .2s ease;
}
.show-inner{
  height: auto;
}
.u-email-head{
  display: flex;
  align-items: center;
  justify-content: center;
  height: 108rpx;
}
.gift{
  width: 79rpx;
  height: 70rpx;
  margin-right: 10rpx;
}
.u-basic-info{
  width: 260rpx;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
}
.u-basic-info .u-title{
  font-size: 28rpx;
  font-weight: bold;
  color: #28235c;
  line-height: 1;
  margin-bottom: 14rpx;
}
.u-basic-info .u-time{
  font-size: 20rpx;
  color: #a69ee3;
  font-weight: bold;
  line-height: 1;
}

.u-email-content{
  padding: 28rpx 10rpx 0 10rpx;
  box-sizing: border-box;
}
.u-txt{
  font-size:24rpx;
  color: #6e64ad;
  text-align: left;
  font-weight: bold;
}
.u-txt-indent{
  text-indent: 2em;
}

.u-gift{
  padding: 20rpx 0;
}
.u-gift,.u-gift-item{
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}
.u-gift-item{
  width: 72rpx;
  height: 72rpx;
  background: #cdc7fb;
  border-radius: 10rpx;
  margin-right:30rpx;
  &:last-child{
    margin-right: 0;
  }
}
.u-gift-item img{
  width: 50rpx;
  height: 50rpx;
}
.u-gift-num{
  position: absolute;
  right: 4rpx;
  bottom:0;
  font-size: 24rpx;
  font-weight: bold;
  color: #fff;
}
.u-back{
  width: 54rpx;
  height: 54rpx;
  margin: 0 auto;
}
.u-tip{
  font-size: 30rpx;
  font-weight: bold;
  color: #a69ee3;
  text-align: center;
  padding: 20rpx 0;

}
</style>
