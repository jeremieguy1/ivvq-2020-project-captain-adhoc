<template>
<div>
    <div class="hero is-small is-primary">
      <div class="hero-body">
        <h1>Welcome to the Captain Escroc market</h1>
      </div>
    </div>

    <section class="section">
      <div class="container">
        <div class="is-flex level is-vcentered">
          <div class="level-item">
            <div class="buttons is-flex">
              <button class="button is-primary is-fullwidth login"
                v-on:click="navigateToLogin">Login</button>
              <button class="button is-primary is-fullwidth">Sign up</button>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import router from '../router'

export default {
  name: 'Home',
  data () {
    return {}
  },
  methods: {
    navigateToLogin () {
      router.push({ name: 'Login' })
    }
  }
}
</script>
<style>
h1::before {
  size: 10rem;
}
</style>
