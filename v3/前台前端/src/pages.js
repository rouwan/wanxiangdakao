module.exports = [
  //首屏页
  {
    path:"/pages/entry/index",
    config:{
      disableScroll:true,
    }
  },
  
  // '/pages/entry/index',
  '/pages/test/index',//测试页面
  '/pages/newYear/index',//新年专题
  '/pages/book/index',//知识书 页面
  '/pages/lottery/index',//抽奖页面
  '/pages/gameReview/index',//答题回顾
  '/pages/questionAdvice/index',//意见
  '/pages/preGamePage/index',//排位准备页面
  {
    path:"/pages/game/index",
    config:{  
      disableScroll:true,
    }
  },
  // '/pages/game/index',//游戏主页
  '/pages/game/result',//游戏结果页
  '/pages/rankPage/index',//排位赛排行榜
  '/pages/talkRoom/index',//聊天室、公屏
  // '/pages/weekChallenge/index',//每周挑战--旧版存档
  // '/pages/weekChallenge/weekIndex',//每周挑战--旧版存档
  // '/pages/dayChallenge/rankPage',//每日挑战-1--旧版存档
  // '/pages/dayChallenge/index',//每日挑战-2--旧版存档
  
  // '/pages/battleRoom/index',//好友对战房间--旧版存档

  // 群对战页面
  {
    root:"roomPages",
    name:"roomPages",
    path: 'roomPages/pages/room',
    subPackage: true, //配置分包加载
    config: { // 页面配置，即 page.json 的内容
      navigationBarTitleText: '群对战',
      disableScroll:true,
    }
  },
  {
    root:"roomPages",
    path:'roomPages/pages/roomList',//群大厅
    config:{
      disableScroll:true,
    }
  },
  {
    root:"roomPages",
    path:'roomPages/pages/hybird-room',//混战房间
    config:{
      disableScroll:true,
    }
  },
  {
    root:"roomPages",
    path:'roomPages/pages/group-room',//3v3房间
    config:{
      disableScroll:true,
    }
  },
  {
    root:"subjectFactoryPages",
    name:"subjectFactoryPages",
    path:'subjectFactoryPages/pages/index',
    subPackage: true, //配置分包加载
    config: { // 页面配置，即 page.json 的内容
      navigationBarTitleText: '题目工厂'
    }
  },
  {
    root:"subjectFactoryPages",
    path:'subjectFactoryPages/pages/selectSubject',//选择录入题目类型
  },
  {
    root:"subjectFactoryPages",
    path:'subjectFactoryPages/pages/addSubject', //添加 更新题目
  },
  {
    root:"subjectFactoryPages",
    path:'subjectFactoryPages/pages/mySubjectList',//我的题目列表
  },
  {
    root:"subjectFactoryPages",
    path:'subjectFactoryPages/pages/lookSubject',//查看题目
  },
  {
    root:"playerPages",
    name:"playerPages",
    path:'playerPages/pages/index',
    subPackage: true, //配置分包加载
    config: { // 页面配置，即 page.json 的内容
      navigationBarTitleText: '用户信息'
    }
  },
]
