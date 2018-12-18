import Vue from 'vue';
import Vuetify from 'vuetify';

import 'vuetify/dist/vuetify.min.css';


import router from 'router';
import resource from 'resource';
import store from 'stores/store';

Vue.use(Vuetify);

import App from 'pages/App.vue';


const app = new Vue({
    router,
    store,
    ...App
}).$mount('#app');
