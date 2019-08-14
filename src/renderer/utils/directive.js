import Vue from 'vue'

// 注册一个全局自定义指令 `v-focus`
Vue.directive('norepeat', {
    // 当被绑定的元素插入到 DOM 中时……
    inserted: function (el, binding, vnode, oldVnode) {
        let endTime = 0
        const wait = 1000
        let timerId = 0
        // 聚焦元素dde
        el.onclick = function (e) {
            
            let lastTime = new Date().getTime()
            
            if (!el.my_disabled) {
                endTime = lastTime + wait
                if (binding.modifiers.callBack) {
                    el.my_disabled = true
                    typeof binding.value === 'function' && binding.value()
                }
            }
            if (lastTime <= endTime && timerId) { //等待时间内
                endTime = lastTime + wait
                timerId && clearTimeout(timerId)
                Vue.prototype.$message({
                    message: '您的操作过于频繁，请稍后再试',
                    type: 'warning'
                })
            }
            
            timerId = setTimeout(() => {
                timerId=0
                el.my_disabled = false
            }, wait)
        }
    }
})