<template>
  <div id="app">
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
  body, input, textarea, select, button, div, p, span, b {
    font-family: Oswald, sans-serif;
  }
</style>
