<template>
            <v-app>
                <v-toolbar app>
                    <v-toolbar-title>ToDo</v-toolbar-title>
                    <v-spacer></v-spacer>
                    <span v-if="isAuthenticated">{{username}}</span>
                    <v-btn v-if="isAuthenticated" icon @click="logout">
                        <v-icon>exit_to_app</v-icon>
                    </v-btn>
                </v-toolbar>
                <v-content>
                    <router-view></router-view>
                </v-content>

            </v-app>

</template>

<script>
    export default {
        computed: {
            username() {
                return this.$store.state.account.username
            },
            isAuthenticated() {
                return this.$store.getters['account/isAuthenticated']
            },
            token(){
                return this.$store.state.account.token
            }
        },
        methods: {
            logout(){
                this.$http.post('auth/signout', {}).then(response => {
                    if (response.ok){
                        this.$store.commit('account/clearToken')
                        this.$store.commit('account/setUsername', '')
                        this.$router.replace('/login')
                    }

                });
            }
        }
}
</script>

<style>

</style>
