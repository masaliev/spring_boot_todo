<template>
	<div>Welcome</div>
</template>

<script>
	export default{
		beforeCreate: function(){
                    if (this.$store.getters['account/isAuthenticated'] === false){
                            this.$router.replace('login')
                    }
		}
	}
</script>

<style>
</style>
