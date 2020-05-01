<template>
    <div class="hero">
    <div class="hero-body">
          <h1 class="has-text-centered">Welcome to IVVQ app o/</h1>
    </div>

    <div class="is-flex rows">
      <div class="row">
        <button class="button is-primary has-text-centered"
         v-on:click="getData">Click me to get stub data</button>
      </div>
      <div class="row">
        <p v-if="info"> Info : {{ info }} </p>
      </div>
    </div>

  </div>
</template>

<script>

import {
  HTTP
} from '../http-common'

export default {
  name: 'Login',
  data () {
    return {
      info: null
    }
  },
  methods: {
    getData () {
      HTTP
        .get('/test')
        .then(response => {
          this.info = response.data
        })
    }
  }
}
</script>

<style scoped>

</style>
