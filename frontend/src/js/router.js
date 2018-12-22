import Vue from 'vue';
import VueRouter from 'vue-router';

import Main from 'pages/Main.vue';
import Login from 'pages/Login.vue';
import Registration from 'pages/Registration.vue';

Vue.use(VueRouter);

const routes = [
    {path: '/', component: Main},
    {path: '/login', component: Login},
    {path: '/registration', component: Registration}
];


export default new VueRouter({
    routes
});