<template>
  <div id="app">
    <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
	<router-view></router-view>
	<!-- <Index></Index> -->
  </div>
</template>

<script>
import Index from './views/index.vue'

export default {
  name: 'app',
  components: {
    Index
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /* margin-top: 10px; */
}
</style>
