import Vue from 'vue';
import VueResource from 'vue-resource';

Vue.use(VueResource);

Vue.http.options.root = process.env.NODE_ENV === 'development' ? "http://localhost:8082/" : "/"

Vue.http.interceptors.push(function(request) {
    if (!!this.$store.state.account.token){
        request.headers.set('X-Auth-Token', this.$store.state.account.token)
    }
});
