<template>
<v-container fill-height>
        <v-layout justify-center>
          <v-flex xs12 sm8 md4>
            <v-card class="elevation-12">
              <v-toolbar dark color="primary">
                <v-toolbar-title>Activation</v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>
              <v-card-text>
                <div>
                    <span class="red--text" v-if="status === 'error'">Something went wrong, try again</span>
                    <span class="green--text" v-else-if="status === 'success'">Succesfully activated. Now you can sign in</span>
                    <span v-else>An activation code is sent, please check your email</span>
                </div>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn v-if="status === 'success'" color="primary" to="/login" replace flat>Sign in</v-btn>
                <v-btn v-else-if="status !== 'error'" color="primary" to="/" replace flat>Main page</v-btn>
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
</template>

<script>
	export default {
            computed: {
              status() {
                return this.$route.params.status
              }
            }
	}
</script>

<style>

</style>
