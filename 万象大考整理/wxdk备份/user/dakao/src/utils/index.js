// 随机分享设置
export function getRondom(min, max) {
  let c = max - min + 1;
  return Math.floor(Math.random() * c - min)
}


export function getStorage(state, key, defaultValue) { //本地获取缓存
  return new Promise((resolve, reject) => {
    wx.getStorage({
      key: key,
      success: res => {
        state[key] = res.data;
        resolve(res.data)
      },
      fail: res => {
        state.key = defaultValue;
        reject('获取缓存失败')
      }
    })
  })

}

export function setStorage(key, vaule) {  //异步设置本地缓存
  return new Promise((resolve, reject) => {
    wx.setStorage({
      key: key,
      data: vaule,
      success: () => {
        resolve();
      },
      fail: () => {
        reject();
      }
    })
  })

}

export function clearStorage(key) {  //把缓存数据 置空
  return new Promise((resolve, reject) => {
    wx.setStorage({
      key: key,
      data: '',
      success: () => {
        resolve();
      },
      fail: () => {
        reject();
      }
    })
  })
}
let thtrottleTimer=null;
export function throttle(fn, delay) {
  clearTimeout(thtrottleTimer);
  thtrottleTimer = setTimeout(() => {
    try{
      fn();
    }catch(e){
      console.log("节流函数错误抛出 @/untils/index/62--line")
    }
  }, delay);
}
export function checkNewDay(type) { //type 区分不同场景都需要检测判定过期
  return new Promise((resolve, reject) => {
    wx.getStorage({
      key: "dayObj",
      success: res => {
        let today = new Date().getDate();
        if (!res.data[type]) {
          let obj = res.data;
          obj[type] = new Date().getDate();
          wx.setStorage({
            key: "dayObj",
            value: obj,
          })
          resolve(true);
        } else if (res.data[type] && (today == res.data[type])) {
          resolve(false);
        }else if(res.data[type] && (today != res.data[type])){
          let obj = {};
          obj[type] = new Date().getDate();
          wx.setStorage({
            key: "dayObj",
            data: obj,
          })
          resolve(true);
        }
      },
      fail: () => {
        let obj = {};
        obj[type] = new Date().getDate();
        wx.setStorage({
          key: "dayObj",
          data: obj,
        })
          resolve(true);
      }
    })
  })

}

export default {
  getStorage,
  setStorage,
  throttle,
  checkNewDay
}
