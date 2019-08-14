import md5 from 'blueimp-md5'
/**
 * Created by jiachenpan on 16/11/18.
 */

Date.prototype.Format = function(a) {
    const b = {
        'M+': this.getMonth() + 1,
        'd+': this.getDate(),
        'h+': this.getHours(),
        'm+': this.getMinutes(),
        's+': this.getSeconds(),
        'q+': Math.floor((this.getMonth() + 3) / 3),
        S: this.getMilliseconds()
    };
    /(y+)/.test(a) && (a = a.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length)))
    for (const c in b) RegExp('(' + c + ')').test(a) && (a = a.replace(RegExp.$1, RegExp.$1.length === 1 ? b[c] : ('00' + b[c]).substr(('' + b[c]).length)))
    return a
}

export function parseTime(time, cFormat) {
  if (arguments.length === 0) {
    return null
  }
  const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if (('' + time).length === 10) time = parseInt(time) * 1000
    date = new Date(time)
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
    let value = formatObj[key]
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') {
      return ['日', '一', '二', '三', '四', '五', '六'][value]
    }
    if (result.length > 0 && value < 10) {
      value = '0' + value
    }
    return value || 0
  })
  return time_str
}

export function formatTime(time, option) {
  // time = +time * 1000
  const d = new Date(time)
  const now = Date.now()

  const diff = (now - d) / 1000

  if (diff < 30) {
    return '刚刚'
  } else if (diff < 3600) {
    // less 1 hour
    return Math.ceil(diff / 60) + '分钟前'
  } else if (diff < 3600 * 24) {
    return Math.ceil(diff / 3600) + '小时前'
  } else if (diff < 3600 * 24 * 2) {
    return '1天前'
  }
  if (option) {
    return parseTime(time, option)
  } else {
    return (
      d.getMonth() +
      1 +
      '月' +
      d.getDate() +
      '日 ' +
      d.getHours() +
      '时' +
      d.getMinutes() +
      '分'
    )
  }
}

/**
 * 发布时间格式化
 * @author wcm
 * @DateTime 2018-12-01T11:55:00+0800
 * @param    {[type]}                 post_modified [description]
 * @return   {[type]}                               [description]
 */
export function handlePublishTimeDesc(post_modified) {

  // 拿到当前时间戳和发布时的时间戳，然后得出时间戳差
  var curTime = new Date();
  var postTime = new Date(post_modified);
  var timeDiff = curTime.getTime() - postTime.getTime();

  // 单位换算
  var min = 60 * 1000;
  var hour = min * 60;
  var day = hour * 24;
  var week = day * 7;

  // 计算发布时间距离当前时间的周、天、时、分
  var exceedWeek = Math.floor(timeDiff / week);
  var exceedDay = Math.floor(timeDiff / day);
  var exceedHour = Math.floor(timeDiff / hour);
  var exceedMin = Math.floor(timeDiff / min);

  // 最后判断时间差到底是属于哪个区间，然后return
  if (exceedWeek > 0) {
    return parseTime(post_modified);
  } else {
    if (exceedDay < 7 && exceedDay > 0) {
      return exceedDay + "天前";
    } else {
      if (exceedHour < 24 && exceedHour > 0) {
        return exceedHour + "小时前";
      } else {
        return exceedMin + "分钟前";
      }
    }
  }
}

// 格式化时间
export function getQueryObject(url) {
  url = url == null ? window.location.href : url
  const search = url.substring(url.lastIndexOf('?') + 1)
  const obj = {}
  const reg = /([^?&=]+)=([^?&=]*)/g
  search.replace(reg, (rs, $1, $2) => {
    const name = decodeURIComponent($1)
    let val = decodeURIComponent($2)
    val = String(val)
    obj[name] = val
    return rs
  })
  return obj
}

/**
 *get getByteLen
 * @param {Sting} val input value
 * @returns {number} output value
 */
export function getByteLen(val) {
  let len = 0
  for (let i = 0; i < val.length; i++) {
    if (val[i].match(/[^\x00-\xff]/gi) != null) {
      len += 1
    } else {
      len += 0.5
    }
  }
  return Math.floor(len)
}

export function cleanArray(actual) {
  const newArray = []
  for (let i = 0; i < actual.length; i++) {
    if (actual[i]) {
      newArray.push(actual[i])
    }
  }
  return newArray
}

export function param(json) {
  if (!json) return ''
  return cleanArray(
    Object.keys(json).map(key => {
      if (json[key] === undefined) return ''
      return encodeURIComponent(key) + '=' + encodeURIComponent(json[key])
    })
  ).join('&')
}

export function param2Obj(url) {
  const search = url.split('?')[1]
  if (!search) {
    return {}
  }
  return JSON.parse(
    '{"' +
    decodeURIComponent(search)
    .replace(/"/g, '\\"')
    .replace(/&/g, '","')
    .replace(/=/g, '":"') +
    '"}'
  )
}

export function html2Text(val) {
  const div = document.createElement('div')
  div.innerHTML = val
  return div.textContent || div.innerText
}

export function objectMerge(target, source) {
  /* Merges two  objects,
     giving the last one precedence */

  if (typeof target !== 'object') {
    target = {}
  }
  if (Array.isArray(source)) {
    return source.slice()
  }
  Object.keys(source).forEach(property => {
    const sourceProperty = source[property]
    if (typeof sourceProperty === 'object') {
      target[property] = objectMerge(target[property], sourceProperty)
    } else {
      target[property] = sourceProperty
    }
  })
  return target
}

export function toggleClass(element, className) {
  if (!element || !className) {
    return
  }
  let classString = element.className
  const nameIndex = classString.indexOf(className)
  if (nameIndex === -1) {
    classString += '' + className
  } else {
    classString =
      classString.substr(0, nameIndex) +
      classString.substr(nameIndex + className.length)
  }
  element.className = classString
}

export const pickerOptions = [{
  text: '今天',
  onClick(picker) {
    const end = new Date()
    const start = new Date(new Date().toDateString())
    end.setTime(start.getTime())
    picker.$emit('pick', [start, end])
  }
}, {
  text: '最近一周',
  onClick(picker) {
    const end = new Date(new Date().toDateString())
    const start = new Date()
    start.setTime(end.getTime() - 3600 * 1000 * 24 * 7)
    picker.$emit('pick', [start, end])
  }
}, {
  text: '最近一个月',
  onClick(picker) {
    const end = new Date(new Date().toDateString())
    const start = new Date()
    start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
    picker.$emit('pick', [start, end])
  }
}, {
  text: '最近三个月',
  onClick(picker) {
    const end = new Date(new Date().toDateString())
    const start = new Date()
    start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
    picker.$emit('pick', [start, end])
  }
}]

export function getTime(type) {
  if (type === 'start') {
    return new Date().getTime() - 3600 * 1000 * 24 * 90
  } else {
    return new Date(new Date().toDateString())
  }
}

export function debounce(func, wait, immediate) {
  let timeout, args, context, timestamp, result

  const later = function() {
    // 据上一次触发时间间隔
    const last = +new Date() - timestamp

    // 上次被包装函数被调用时间间隔last小于设定时间间隔wait
    if (last < wait && last > 0) {
      timeout = setTimeout(later, wait - last)
    } else {
      timeout = null
      // 如果设定为immediate===true，因为开始边界已经调用过了此处无需调用
      if (!immediate) {
        result = func.apply(context, args)
        if (!timeout) context = args = null
      }
    }
  }

  return function(...args) {
    context = this
    timestamp = +new Date()
    const callNow = immediate && !timeout
    // 如果延时不存在，重新设定延时
    if (!timeout) timeout = setTimeout(later, wait)
    if (callNow) {
      result = func.apply(context, args)
      context = args = null
    }

    return result
  }
}

/**
 * This is just a simple version of deep copy
 * Has a lot of edge cases bug
 * If you want to use a perfect deep copy, use lodash's _.cloneDeep
 */
/*export function deepClone(source) {
  if (!source && typeof source !== 'object') {
    throw new Error('error arguments', 'shallowClone')
  }
  const targetObj = source.constructor === Array ? [] : {}
  Object.keys(source).forEach(keys => {
    if (source[keys] && typeof source[keys] === 'object') {
      targetObj[keys] = deepClone(source[keys])
    } else {
      targetObj[keys] = source[keys]
    }
  })
  return targetObj
}*/

export function uniqueArr(arr) {
  return Array.from(new Set(arr))
}

export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * 格式化金额
 * @author wcm
 * @DateTime 2018-12-22T16:08:51+0800
 * @param    {[type]}                 num [description]
 * @param    {[type]}                 opt [description]
 * @return   {[type]}                     [description]
 */
export function formartMoney(num,opt){
  var neg = (num < 0),
          x, x1, x2, x3, i, len;
    var options=Object.assign({
      useGrouping: true, // 1,000,000 vs 1000000
      separator: ',', // character to use as a separator
      decimal: '.', // character to use as a decimal
      prefix: '', // optional text before the result
      suffix: '', // optional text after the result
      numerals: [] // optionally pass an array of custom numerals for 0-9
    },opt)
    num = Math.abs(num).toFixed(2);
    num += '';
    x = num.split('.');
    x1 = x[0];
    x2 = x.length > 1 ? options.decimal + x[1] : '';
    if (options.useGrouping) {
      x3 = '';
      for (i = 0, len = x1.length; i < len; ++i) {
        if (i !== 0 && ((i % 3) === 0)) {
          x3 = options.separator + x3;
        }
        x3 = x1[len - i - 1] + x3;
      }
      x1 = x3;
    }
    // optional numeral substitution
    if (options.numerals.length) {
      x1 = x1.replace(/[0-9]/g, function(w) {
        return options.numerals[+w];
      })
      x2 = x2.replace(/[0-9]/g, function(w) {
        return options.numerals[+w];
      })
    }
    return (neg ? '-' : '') + options.prefix + x1 + x2 + options.suffix;
}

/**
 * 密码加密
 * @param {[type]} pwd [description]
 */
export function CalcuMD5(pwd) {
  var totleStr = "CHINALICHAOLOVEANNI20131117BEIJINGPINGQIANGRUANJIAN";
  pwd = pwd.toUpperCase();
  pwd = pwd.replace(/0/g, '~').replace(/1/g, '$').replace(/2/g, '!').replace(/3/g, '@').replace(/4/g, ':').
  replace(/5/g, ']').replace(/6/g, '[').replace(/7/g, '{').replace(/8/g, '}').replace(/9/g, '`');
  totleStr = totleStr.substring(0, totleStr.length - pwd.length);
  pwd = totleStr + pwd;
  pwd = md5(pwd);
  return pwd;
}

/**
 * 日期格式化
 * @author wcm
 * @DateTime 2018-12-25T22:03:01+0800
 * @param    {[type]}                 dateParam 可以使毫秒数、日期、日期字符串
 * @param    {[type]}                 ptn       'yyyy-M-d hh:mm:ss' 去0格式化;  'yyyy-MM-dd hh:mm:ss' 补零格式化(默认)
 * @return   {[type]}                           [description]
 */
export function dateFormat(dateParam, ptn) {
  let dateTime = undefined
  let pattern = ptn ? ptn : 'yyyy-MM-dd hh:mm:ss'

  if (dateParam instanceof Date) {
    dateTime = dateParam
  } else {
    try {
      dateTime = new Date(dateParam)
    } catch (error) {
      console.error('请传入类似yyyy-MM-dd hh:mm:ss的日期字符串')
    }
  }
  const b = {
    'M+': dateTime.getMonth() + 1,
    'd+': dateTime.getDate(),
    'h+': dateTime.getHours(),
    'm+': dateTime.getMinutes(),
    's+': dateTime.getSeconds(),
    'q+': Math.floor((dateTime.getMonth() + 3) / 3),
    S: dateTime.getMilliseconds()
  };
  /(y+)/.test(pattern) && (pattern = pattern.replace(RegExp.$1, (dateTime.getFullYear() + '').substr(4 - RegExp.$1.length)))
  for (const c in b)
    RegExp('(' + c + ')').test(pattern) && (pattern = pattern.replace(RegExp.$1, RegExp.$1.length === 1 ? b[c] : ('00' + b[c]).substr(('' + b[c]).length)))
  return pattern
}

/**
 * 定位
 * @Author   wcm
 * @DateTime 2019-01-14T09:58:55+0800
 * @return   {[type]}                 [description]
 */
export function getLocation() {
  return new Promise((resolve, reject) => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(position => {
        resolve({
          state: 0,
          lat: position.coords.latitude,
          lng: position.coords.longitude
        })
      }, error => {
        switch (error.code) {
          case error.PERMISSION_DENIED:
            reject({
              state: error.PERMISSION_DENIED,
              msg: "User denied the request for Geolocation."
            });
            break;
          case error.POSITION_UNAVAILABLE:
            reject({
              state: error.POSITION_UNAVAILABLE,
              msg: "Location information is unavailable."
            });
            break;
          case error.TIMEOUT:
            reject({
              state: error.TIMEOUT,
              msg: "The request to get user location timed out."
            });
            break;
          case error.UNKNOWN_ERROR:
            reject({
              state: error.UNKNOWN_ERROR,
              msg: "An unknown error occurred."
            });
            break;
        }
      });
    } else {
      reject({
        state: -1,
        msg: '您的浏览器不支持定位功能'
      })
    }
  })

}

/**
 * 深度拷贝
 * @author wcm
 * @DateTime 2019-01-19T23:19:53+0800
 * @param    {[type]}                 obj [description]
 * @return   {[type]}                     [description]
 */
export function deepClone(origin,target){
  var target = target || {};
  for (var prop in target) {
    if (target.hasOwnProperty(prop)) {
      if (target[prop] !== null && typeof target[prop] === 'object') {
        origin[prop] = Object.prototype.toString.call(target[prop]) === '[object Array]' ? [] : {};
        deepClone(origin[prop], target[prop]);
      } else {
        origin[prop] = target[prop]
      }
    }

  }
}

/**
 * 判断是否是微信浏览器
 * @author wcm
 * @DateTime 2019-01-30T20:49:23+0800
 * @return   {Boolean}                [description]
 */
export function isWeiXin() {
    //window.navigator.userAgent属性包含了浏览器类型、版本、操作系统类型、浏览器引擎类型等信息，这个属性可以用来判断浏览器类型
    var ua = window.navigator.userAgent.toLowerCase();
    //通过正则表达式匹配ua中是否含有MicroMessenger字符串
    if (ua.match(/MicroMessenger/i) == 'micromessenger') {
        return true;
    } else {
        return false;
    }
}
/**
 * 地址栏参数获取
 * @param  {[type]} name [description]
 * @return {[type]}      [description]
 */
export function getUrlParameter (name) {
    var search = document.location.search;
    var pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
    var matcher = pattern.exec(search);
    var items = null;
    if (null != matcher) {
      try {
        items = decodeURIComponent(decodeURIComponent(matcher[1]));
      } catch (e) {
        try {
          items = decodeURIComponent(matcher[1]);
        } catch (e) {
          items = matcher[1];
        };
      };
    }
    return items;
}