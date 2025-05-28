import './style/base.scss'
import Vue from 'vue'
import VueRouter from 'vue-router'
import root from './root.vue'
import routes from './routes'
import * as filters from '../common/filters'
import print from '../common/print'
import VueConfirm from 'vue-confirm'

Vue.use(VueConfirm);
Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.use(print);
Object.keys(filters).forEach(name => Vue.filter(name, filters[name]));

export default function (login) {
    let router = new VueRouter({routes});
    new Vue({
        router,
        render: h => h(root)
    }).$mount('#app');
    if (login) {
        router.replace('/login')
    }
}
