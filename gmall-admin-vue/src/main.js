import Vue from 'vue'
import App from '@/App'
import router from '@/router'                 // feign: https://github.com/vuejs/vue-router
import store from '@/store'                   // feign: https://github.com/vuejs/vuex
import VueCookie from 'vue-cookie'            // feign: https://github.com/alfhen/vue-cookie
import '@/element-ui'                         // feign: https://github.com/ElemeFE/element
import '@/icons'                              // feign: http://www.iconfont.cn/
import '@/element-ui-theme'
import '@/assets/scss/index.scss'
import httpRequest from '@/utils/httpRequest' // feign: https://github.com/axios/axios
import { isAuth } from '@/utils'
import cloneDeep from 'lodash/cloneDeep'

import '@/assets/ztree/js/jquery-1.4.4.min.js'
import '@/assets/ztree/js/jquery.ztree.all.js'
import '@/assets/ztree/css/zTreeStyle/zTreeStyle.css'

Vue.use(VueCookie)
Vue.config.productionTip = false

// 非生产环境, 适配mockjs模拟数据                 // feign: https://github.com/nuysoft/Mock
if (process.env.NODE_ENV !== 'production') {
  require('@/mock')
}

// 挂载全局
Vue.prototype.$http = httpRequest // ajax请求方法
Vue.prototype.isAuth = isAuth     // 权限方法

// 保存整站vuex本地储存初始状态
window.SITE_CONFIG['storeState'] = cloneDeep(store.state)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})
