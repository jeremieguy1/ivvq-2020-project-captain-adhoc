<template>
  <div>
    <Navbar></Navbar>
    <router-view/>
  </div>
</template>

<script>
import Navbar from './components/Navbar'
export default {
  name: 'App',
  components: {
    Navbar
  }
}
</script>

<style>
  @import "../node_modules/bulma/css/bulma.css";
  @import "../node_modules/animate.css/animate.css";
</style>
