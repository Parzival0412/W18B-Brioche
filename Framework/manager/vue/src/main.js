import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/css/global.css'
import '@/assets/css/theme/index.css'
import request from "@/utils/request"

// 导入 ElementUI 的英文语言包
import enLocale from 'element-ui/lib/locale/lang/en'

Vue.config.productionTip = false

Vue.prototype.$request = request
Vue.prototype.$baseUrl = process.env.VUE_APP_BASEURL

// 使用 ElementUI 时传入英文语言包
Vue.use(ElementUI, { size: "small", locale: enLocale })

new Vue({
    router,
    render: h => h(App)
}).$mount('#app')
