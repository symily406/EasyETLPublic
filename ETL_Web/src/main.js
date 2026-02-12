import Vue from "vue";
import App from "./App.vue";
import router from "@/router";
import store from "@/store";
import app from "@/utils/app";
import ajax from "@/utils/request";
import permission from "@/utils/permission";


import md5 from 'js-md5';

import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import vuescroll from 'vuescroll';
import 'vuescroll/dist/vuescroll.css';

//图标
import eIconPicker from 'e-icon-picker';
import 'e-icon-picker/lib/index.css';
import 'font-awesome/css/font-awesome.min.css' // font-awesome 图标库
import 'element-ui/lib/theme-chalk/icon.css'; //element-ui 图标库


import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import "@/assets/scss/global.scss";
//文件上传
import uploader from 'vue-simple-uploader';
//菜单
import VueContextMenu from 'vue-contextmenu';
//复制
import VueClipboard from 'vue-clipboard2';
Vue.use(VueClipboard);

Vue.prototype.$app=app;
Vue.prototype.$ajax=ajax;
Vue.prototype.$permission=permission;
Vue.prototype.$md5 = md5;

Vue.filter('fileSize', function(size) {
  return app.fileSize(size);
})


Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.use(vuescroll);
Vue.use(uploader)
Vue.use(VueContextMenu)

//全局删除增加图标
Vue.use(eIconPicker, {
    FontAwesome: true,
    ElementUI: true,
    eIcon: true,//自带的图标，来自阿里妈妈
    eIconSymbol: false,//是否开启彩色图标
    addIconList: [],
    removeIconList: [],
    zIndex: 3100//选择器弹层的最低层,全局配置
});

import moment from 'moment';
Vue.prototype.$moment = moment;




new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
