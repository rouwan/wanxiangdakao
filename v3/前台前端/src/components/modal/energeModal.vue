<template>
    <div class="m-modal">
      <div class="u-modal-inner">
        <div class="close" @click="close"></div>
        <div class="u-head">
          <div>体力</div>
          <img class="u-head-img" src="/static/modal-bg.png" alt="">
        </div>
        <div class="u-content">
            <div class="u-energe">
              <div class="u-icon">
                <img src="/static/entry/energe.png">
              </div>
                {{strengthCount}}/{{maxStrength}}
            </div>
            <div class="u-props">
              <div class="u-props-item" v-for="(item,index) in goodList" :key="index">
                <div class="u-box">
                  <img :src="item.url">
                  <div class="u-num">X{{item.num}}</div>
                </div>
                <div class="u-props-desc">体力+{{item.energe}}</div>
                <div class="u-btn u-btn-red u-btn-samll"@click="throttle(item,200)">使用</div>
              </div>
            </div>

        </div>
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
    throttleTimer:null,
    goodList:[
        {
          type:1,
          url:"/static/icon/s-eng.png",
          num:0,
          energe:1,
          maxNum:999,
          desc:"小体力瓶:恢复1点体力(上限999)"
        },
        {
          type:2,
          url:"/static/icon/m-eng.png",
          num:0,
          energe:15,
          maxNum:99,
          desc:"中体力瓶:恢复15点体力(上限99)"
        },
        {
          type:3,
          url:"/static/icon/b-eng.png",
          num:0,
          energe:30,
          maxNum:99,
          desc:"大体力瓶:恢复30点体力(上限99)"
        }
      ]
    }
  },
  computed:{
    ...mapState(["strengthCount","maxStrength","goodStorageList"]),
  },
  methods:{
    ...mapMutations(["setStrengthCount","setGoodStorageList","setSingleGoodNum"]),
  async  getGoodList(){
      let good=[];
      if(this.goodStorageList.length>0){
        good = this.goodStorageList;
      }else{
        await  this.fly.post('/equipment/getEquipmentList').then(res=>{
          return new Promise((resolve,reject)=>{
            good = res.data.Result;
            this.setGoodStorageList(good);
            resolve();
          })
        })
      }
      for(let i=0;i<good.length;i++){
            for(let j=0;j<3;j++){
              if(good[i].type==this.goodList[j].type){
                this.goodList[j].num = good[i].num;
                this.goodList[j].desc = good[i].description;
              }
            }
        }
    },
    throttle(good,delay) {
      clearTimeout(this.throttleTimer);
      this.throttleTimer = setTimeout(()=> {
        this.useGood(good);
      }, delay);
    },
    useGood(good){
      if(this.strengthCount==this.maxStrength){
        wx.showToast({
          title:"体力已满,无法使用",
          icon:"none"
        })
      }else if(good.num<=0){
        return false;
      }else{
        this.fly.post('/equipment/useEquipment',{
          count:1,
          type:good.type
          }).then(res=>{
          if(res.data.Tag==1){
            good.num--;
            this.setSingleGoodNum({
              type:good.type,
              num:good.num
            })
              let addNum=this.strengthCount+good.energe>this.maxStrength?this.maxStrength:this.strengthCount+good.energe;
              this.setStrengthCount(addNum);
              wx.showToast({
                title:`体力+${good.energe}`,
                icon:"none"
              })
          }
          console.log("使用道具反馈",res.data)
        })
      }
    },
    close(){
      this.$emit('close');
    },

  },
  mounted(){
    console.log("123123213")
    this.getGoodList();
  },
}
</script>

<style lang="scss" scoped>
@import url("../../scss/s-modal.scss");
.m-modal{
  background: rgba(#000,.55);
}
.u-content{
  box-sizing: border-box;
  border-bottom: 30rpx;
}

.u-energe{
  padding: 28rpx 0;
  box-sizing: border-box;
  font-size: 58rpx;
  font-weight: bold;
  color: #a69ee3;
  display: flex;
  justify-content: center;
  align-items: center;
}
.u-icon{
  width: 116rpx;
  height: 116rpx;
  border-radius: 50%;
  background: #28235c;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right:24rpx;
  img{
    width: 81rpx;
    height: 108rpx;
  }
}
.u-props{
  width: 500rpx;
  height: 290rpx;
  box-sizing: border-box;
  border: 8rpx solid #c4bcfb;
  background: #ebe8ff;
  border-radius: 32rpx;
  display: flex;
  align-items: center;
  justify-content: space-around;
}

.u-props-item{
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
.u-box{
  display: flex;
  align-items: center;
  justify-content: center;
  background: #cdc7fb;
  border-radius: 10rpx;
  width: 100rpx;
  height: 100rpx;
  position: relative;
}
.u-box img{
  width: 80rpx;
  height: 80rpx;
  transform: rotate(30deg);
}
.u-num{
  position: absolute;
  right: 4rpx;
  bottom: 4rpx;
  color: #fff;
  font-weight: bold;
  font-size: 30rpx;
}
.u-props-desc{
  color: #958bd8;
  font-size: 26rpx;
  font-weight: bold;
  line-height: 1;
  padding: 20rpx 0;
}
</style>
