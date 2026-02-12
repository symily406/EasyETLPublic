import Vue from "vue";
import Vuex from "vuex";

import global from "./modules/global/global.js"

Vue.use(Vuex)

const modulesFiles = require.context('./modules', true, /\.js$/)

// you do not need `import app from './modules/app'`
// it will auto require all vuex module from modules file
let modules = modulesFiles.keys().reduce((modules, modulePath) => {
  // set './app.js' => 'app'
  const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, '$1')
  
  const value = modulesFiles(modulePath)
  modules[moduleName] = value.default
  return modules
}, {})

modules['global']=global;


const store = new Vuex.Store({
  modules
});

export default store;
