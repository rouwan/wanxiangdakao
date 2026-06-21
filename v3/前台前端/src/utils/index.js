// 随机分享设置
export function getRondom(min, max) {
  let c = max - min + 1;
  return Math.floor(Math.random() * c - min)
}


export function getStorage(state, key, defaultValue) {
  try {
    var val = wx.getStorageSync(key);
    if (state) state[key] = val;
    return Promise.resolve(val);
  } catch(e) {
    if (state) state[key] = defaultValue;
    return Promise.resolve(defaultValue);
  }
}

export function setStorage(key, vaule) {
  try { wx.setStorageSync(key, vaule); return Promise.resolve(); }
  catch(e) { return Promise.resolve(); }
}

export function clearStorage(key) {
  try { wx.setStorageSync(key, ''); return Promise.resolve(); }
  catch(e) { return Promise.resolve(); }
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
export function checkNewDay(type) {
  try {
    var data = wx.getStorageSync('dayObj') || {};
    var today = new Date().getDate();
    var isNew = !data[type] || data[type] != today;
    if (isNew) {
      data[type] = today;
      try { wx.setStorageSync('dayObj', data); } catch(e) {}
    }
    return Promise.resolve(isNew);
  } catch(e) {
    var obj = {};
    obj[type] = new Date().getDate();
    try { wx.setStorageSync('dayObj', obj); } catch(e2) {}
    return Promise.resolve(true);
  }
}

export default {
  getStorage,
  setStorage,
  throttle,
  checkNewDay
}
