<template>
  <div>
    <div class="hero is-small">
      <div class="hero-body">
        <div class="columns" style="position:relative">
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
          <div class="content is-medium " >
            <div  id="container" class="column is-narrow has-text-centered " >
              <div id="jeuxExlu" class="column is-narrow has-text-centered " >
                <p>Le seul site où vous pouvez trouver des consoles de jeux exclusives !</p>
              </div>
            </div>
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
          <p><br/>Alors, envie d'une escroquerie ? Connectez-vous ou alors rejoignez-nous !</p>
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
<style type ="text/css" >
  #container {
    display: flex;
    justify-content: center;
    align-items: flex-end;
  }
  #jeuxExlu {
    position:absolute;
    bottom: 0;
  }
</style>
