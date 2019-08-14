import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import permission from './modules/permission'
import frontEnd from './modules/frontEnd'
import getters from './getters'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    frontEnd,
    permission
  },
  getters
})

export default store
