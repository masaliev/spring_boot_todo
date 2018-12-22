<template>
<v-container fill-height>
        <v-layout justify-center>
          <v-flex xs12 sm8 md4>
            <v-card class="elevation-12">
              <v-toolbar dark color="primary">
                <v-toolbar-title>Sign Up form</v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>
              <v-card-text>
                <v-form>
                  <v-text-field prepend-icon="person" name="login" label="Username" type="text" v-model="username" ref="username" :rules="[rules.required, rules.correctUsername]" required :error-messages="errorMessages.username"></v-text-field>
                  <v-text-field prepend-icon="alternate_email" name="email" label="Email" type="text" v-model="email" ref="email" :rules="[rules.required, rules.correctEmail]" required></v-text-field>
                  <v-text-field id="password" prepend-icon="lock" name="password" label="Password" type="password" v-model="password" ref="password" :rules="[rules.required, rules.counter]" required></v-text-field>
                  <v-text-field id="confirm_password" prepend-icon="repeat" name="confirm_password" label="Confirm password" type="password" v-model="confirm_password" ref="confirm_password" :rules="[rules.required, rules.confirm]" required></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn color="primary" to="/login" replace flat>Sign in instead</v-btn>
                <v-spacer></v-spacer>
                <v-btn color="primary" @click="signUp">Sign Up</v-btn>
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
</template>

<script>
        const emailRegex = /(^$|^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$)/

	export default {
            data: function(){
                return {
                    username: '',
                    email: '',
                    password: '',
                    confirm_password: '',
                    errorMessages: {
                        username: []
                    },
                    rules: {
                        required: value => !!value || 'Required.',
                        correctUsername: value => (!!value && /\W/gi.test(value.trim()) === false) || 'Invalid username',
                        correctEmail: value => (!!value && emailRegex.test(value.trim())) || 'E-mail must be valid',
                        counter: value => value.length >= 6 || 'Min 6 characters',
                        confirm: value => value === this.password || 'Those passwords didn\'t match'
                    }
                }
            },
            beforeCreate: function(){
                if (this.$store.getters['account/isAuthenticated'] === true){
                        this.$router.replace('/')
                }
            },
            computed: {
              form () {
                return {
                  username: this.username,
                  email: this.email,
                  password: this.password,
                  confirm_password: this.confirm_password
                }
              }
            },
            watch: {
                username () {
                    this.errorMessages.username = []
                }
            },
            methods: {
                signUp(){
                    let formHasErrors = false;
                    Object.keys(this.form).forEach(f => {
                      if (!this.form[f] || !this.$refs[f].validate(true)) formHasErrors = true
                    })
                    if (formHasErrors === false){
                        console.log(this.username);
                        console.log(this.password);
                    this.$http.post('auth/sign-up', {username: this.username, password: this.password}).then(response => {
                        response.json().then(data => {
                            this.$store.commit('account/setToken', data.token)
                            this.$store.commit('account/setUsername', data.username)
                            this.$router.replace('/')
                        })
                    }, response => {
                        if (response.ok === false && response.status === 400){
                            response.json().then(errors => {
                                Object.keys(errors).forEach(field => {
                                    if (field in this.errorMessages){
                                        this.errorMessages[field] = []
                                        this.errorMessages[field].push(errors[field])
                                    }
                                })
                            })
                        }

                    })
                    }
                }
            }
	}
</script>

<style>

</style>
