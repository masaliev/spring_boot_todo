<template>
<v-container fill-height>
        <v-layout justify-center>
          <v-flex xs12 sm8 md4>
            <v-card class="elevation-12">
              <v-toolbar dark color="primary">
                <v-toolbar-title>Login form</v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>
              <v-card-text>
                <v-form>
                  <v-text-field prepend-icon="person" name="login" label="Login" type="text" v-model="username" ref="username" :rules="[rules.required]" required></v-text-field>
                  <v-text-field id="password" prepend-icon="lock" name="password" label="Password" type="password" v-model="password" ref="password" :rules="[rules.required, rules.counter]" required></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" @click="login">Login</v-btn>
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
</template>

<script>
	export default {
            data: function(){
                return {
                    username: '',
                    password: '',
                    rules: {
                        required: value => !!value || 'Required.',
                        counter: value => value.length > 2 || 'Min 2 characters',
                    }
                }
            },
            computed: {
              form () {
                return {
                  username: this.username,
                  password: this.password
                }
              }
            },
            beforeCreate: function(){
                if (this.$store.getters['account/isAuthenticated'] === true){
                        this.$router.replace('/')
                }
            },
            methods: {
                login(){
                    let formHasErrors = false;
                    Object.keys(this.form).forEach(f => {
                      if (!this.form[f] || !this.$refs[f].validate(true)) formHasErrors = true
                    })
                    if (formHasErrors === false){
                        console.log(this.username);
                        console.log(this.password);
                    this.$http.post('auth/sign-in', {username: this.username, password: this.password}).then(response => {
                        response.json().then(data => {
                            this.$store.commit('account/setToken', data.token)
                            this.$store.commit('account/setUsername', data.username)
                            this.$router.replace('/')
                        })
                    }, response => {
                        console.log(response)
                        //todo handle error
                    })
                    }
                }
            }
	}
</script>

<style>

</style>
