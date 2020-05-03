<template>
  <div>

    <div class="hero is-small is-primary">
      <div class="hero-body">
        <div class="columns">
          <div class="column ">
            <figure class="image is-4by5">
              <img src="../assets/rickH.png" />
            </figure>
          </div>
        <div class="column is-half">
          <div class="level-item">
            <h2 class="subtitle is-5">Welcome to the</h2>
          </div>
          <div class="level-item">
            <h1 class="title is-1">Captain Escroc Market</h1>
          </div>
        </div>
          <div class="column ">
            <figure class="image is-4by5">
              <img src="../assets/Tom_Nook.png" />
            </figure>
          </div>
      </div>
      </div>
    </div>



    <div class="container">

      <div class="content is-medium">
        <div class="column is-narrow has-text-centered">

          <p>Si vous voulez accéder à nos escroqueries, veuillez vous connecter ou alors rejoignez nous !</p>
        </div>
      </div>
    </div>

    <section class="section">
      <div class="container">
        <div class="is-flex level is-vcentered">
          <div class="level-item">
            <div class="buttons is-flex">
              <button class="button is-primary is-fullwidth login"
                      v-on:click="navigate">Login</button>
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
