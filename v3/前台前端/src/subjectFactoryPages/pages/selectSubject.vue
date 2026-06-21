<template>
  <div class="m-subject sky-bg-2">
    <div class="u-head">请选择类别</div>
    <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/subjectFactory/class-wen.png"@click="handRoute('3')">
    <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/subjectFactory/class-li.png"@click="handRoute('4')">
    <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/subjectFactory/class-yi.png"@click="handRoute('5')">
    <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/subjectFactory/class-ti.png"@click="handRoute('6')">
    <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/subjectFactory/class-shang.png"@click="handRoute('2')">
    <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/subjectFactory/class-zong.png"@click="handRoute('7')">
  </div>
</template>

<script>
// 文、体 艺 理 其他
export default {
  methods:{
    handRoute(type){
      this.$router.push(`/subjectFactoryPages/pages/addSubject?parentType=${type}`);
    },
  },
}
</script>

<style lang="scss" scoped>
.m-subject{
  width: 100%;
  height: 100%;
  text-align: center;
  .u-head{
    width: 100%;
    padding: 85rpx 0;
    box-sizing: border-box;
    line-height: 1;
    font-size: 48rpx;
    color: #7efce4;
    font-weight: bold;
    text-align: center;
  }
  img{
    width: 280rpx;
    height: 280rpx;
    margin-right: 50rpx;
    margin-bottom: 50rpx;
    &:nth-of-type(2n+2){
      margin-right: 0;
    }
    &:nth-of-type(5),&:nth-of-type(6){
      margin-bottom: 0;
    }
  }
}
</style>
