import Vue from 'vue';
import Vuex from 'vuex';

import accountStore from './account'

Vue.use(Vuex);

export default new Vuex.Store({
        state:{
            baseUrl: process.env.NODE_ENV === 'development' ? "http://localhost:8082/" : "/"
        },
        modules: {
            account: accountStore
        }
})
