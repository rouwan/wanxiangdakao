<template>
<div>
  <div style="font-size:50rpx;">{{value}}</div>
  <button @click="test">点击测试</button>
</div>

</template>

<script>
export default {
  data(){
    return {
      value:0,
      gameStatus:1,
    }
  },
  methods:{
    test(){
    if (this.gameStatus == 0) {
        wx.showToast({ title: "游戏已结束", icon: "none" });
        return false;
      }
      this.value++;
    },
  },
}
</script>

<style>

</style>
