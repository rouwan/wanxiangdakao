//零散api集合
import Vue from 'vue'

const singleApi = {

  getUserInfoById: ({id,type}) => { //获取用户 题目工厂基础题目数据（提交 ，通过 数量等）
    return new Promise((resolve, reject) => {
      Vue.prototype.fly.post('/user/getUser',{
        userId:id,
        type:type
      }).then(res => {
        resolve(res.data);
      })
    })
  },
  

}

export default singleApi;