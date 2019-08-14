export function hasClass(el, className) {
  let reg = new RegExp('(^|\\s)' + className + '(\\s|$)')
  return reg.test(el.className)
}

export function addClass(el, className) {
  if (hasClass(el, className)) {
    return
  }

  let newClass = el.className.split(' ')
  newClass.push(className)
  el.className = newClass.join(' ')
}

export function removeClass(el, className) {
  if (!hasClass(el, className)) {
    return
  }

  let reg = new RegExp('(^|\\s)' + className + '(\\s|$)', 'g')
  el.className = el.className.replace(reg, ' ')
}

export function getData(el, name, val) {
  let prefix = 'data-'
  if (val) {
    return el.setAttribute(prefix + name, val)
  }
  return el.getAttribute(prefix + name)
}

export function getRect(el) {
  if (el instanceof window.SVGElement) {
    let rect = el.getBoundingClientRect()
    return {
      top: rect.top,
      left: rect.left,
      width: rect.width,
      height: rect.height
    }
  } else {
    return {
      top: el.offsetTop,
      left: el.offsetLeft,
      width: el.offsetWidth,
      height: el.offsetHeight
    }
  }
}

/**
 * 获取元素到页面顶部的高度
 * @author wcm
 * @DateTime 2018-10-28T20:30:43+0800
 * @param    {[type]}                 el [description]
 * @return   {[type]}                    [description]
 */
export function getOffsetTop(el) {
  let top = el.offsetTop
  function _getTop(el, top) {
    el = el.offsetParent
    if (/BODY/.test(el.nodeName.toUpperCase())) {
      return top
    } else {
      top += el.offsetTop
      return _getTop(el, top)
    }
  }
  return _getTop(el, top)

}
export function getOffsetLeft(el) {
  let left = el.offsetLeft
  function _getLeft(el, left) {
    el = el.offsetParent
    if (/BODY/.test(el.nodeName.toUpperCase())) {
      return left
    } else {
      left += el.offsetLeft
      return _getLeft(el, left)
    }
  }
  return _getLeft(el, left)

}
/**
 * 根据属性获取css样式属性值
 * @author wcm
 * @DateTime 2019-01-28T11:49:50+0800
 * @param    {[type]}                 el  [description]
 * @param    {[type]}                 key [description]
 * @return   {[type]}                     [description]
 */
export function getElementStyleProperty(el, key) {
  if (el.style[key]) {
    return el.style[key]
  }
  if (window.getComputedStyle) {
    return window.getComputedStyle(el, null).getPropertyValue(key)
  } else {
    return el.currentStyle[key]
  }
}
