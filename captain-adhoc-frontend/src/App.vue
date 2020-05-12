<template>
  <div>
    <Navbar></Navbar>
    <router-view/>
  </div>
</template>

<script>
import axios from 'axios'
import Navbar from './components/Navbar'
export default {
  name: 'App',
  components: {
    Navbar
  },
  mounted () {
    axios.defaults.baseURL = process.env.API_URL
  }
}
</script>

<style>
  body, input, textarea, select, button, div, p, span, b {
    font-family: Oswald, sans-serif;
  }
</style>
