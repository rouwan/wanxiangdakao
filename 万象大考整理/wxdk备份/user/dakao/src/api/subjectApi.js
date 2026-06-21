// 题目工厂api  按照功能模块划分 
import Vue from 'vue'

const subjectApi = {

  getSubjectNum: () => { //获取用户 题目工厂基础题目数据（提交 ，通过 数量等）
    return new Promise((resolve, reject) => {
      Vue.prototype.fly.post('/subject/getSubjectNum').then(res => {
        resolve(res.data);
      })
    })
  },

  updateSubject: ({id,parentType, answer, title, option, description }) => { //添加题目
    return new Promise((resolve, reject) => {
      Vue.prototype.fly.post('/subject/updateSubject', {
        id:id,//题目id
        parentType: parentType, //题目类型 文理艺体综 <=> 1 2 3 4 5
        answer: answer, //正确答案序号 1...
        title: title,//题目
        option: option,//选项 用%#@ 分隔
        description: description,//解析
      }).then(res => {
        resolve(res.data);
      })
    })
  },

  getSubjectList: ({pageIndex,pageSize}) => { //获取用户出题列表
    return new Promise((resolve, reject) => {
      Vue.prototype.fly.post('/subject/getSubjectList',{
        pageIndex:pageIndex,
        pageSize:pageSize
      }).then(res => {
        resolve(res.data);
      })
    })
  },
  getSubjectListByStatus: ({status,pageIndex,pageSize}) => { //获取用户出题列表
    return new Promise((resolve, reject) => {
      Vue.prototype.fly.post('/subject/getSubjectListByStatus',{
        status:status,
        pageIndex:pageIndex,
        pageSize:pageSize
      }).then(res => {
        resolve(res.data);
      })
    })
  },
  getSubjectById: ({id}) => { //获取题目
    return new Promise((resolve, reject) => {
      Vue.prototype.fly.post('/subject/getSubjectById',{
        id:id
      }).then(res => {
        resolve(res.data);
      })
    })
  },

}

export default subjectApi;