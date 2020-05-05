<template>
  <nav class="navbar is-white has-border-bottom-red" role="navigation" aria-label="main navigation">

    <div class="navbar-brand">
      <router-link to="/home" class="navbar-item">
        <img src="../assets/brand-icon.png" alt="go to home">
        <span><b>Home</b></span>
      </router-link>

      <a role="button" class="navbar-burger burger" aria-label="menu" aria-expanded="false" data-target="navbar-menu">
        <span aria-hidden="true"></span>
        <span aria-hidden="true"></span>
        <span aria-hidden="true"></span>
      </a>
    </div>

  <div id="navbar-menu" class="navbar-menu">
    <div class="navbar-start">
      <div v-if="isLogged" class="is-flex">
        <router-link to="/products" class="navbar-item">
          <span>Produits</span>
        </router-link>
      </div>
    </div>

    <div class="navbar-end">
      <div v-if="!isLogged" class="buttons">
        <router-link to="/log-in" class="navbar-item button is-flex">
          <span>
            <img src="../assets/log-in.svg" alt="">
          </span>
          <span>Connexion</span>
        </router-link>
        <router-link to="/sign-up" class="navbar-item button">
          <span>
            <img src="../assets/log-in.svg" alt="">
          </span>
          <span>Inscription</span></router-link>
      </div>
      <div v-else class="buttons">
        <router-link to="/cart" class="navbar-item button is-flex" aria-label="Your cart">
          <span>
            <img src="../assets/cart.svg" alt="">
          </span>
          <span>Cart</span>
        </router-link>

        <router-link to="/purchases-history" class="navbar-item button is-flex">
          <span>
            <img src="../assets/history.svg" alt="">
          </span>
          <span>History</span>
        </router-link>

        <router-link to="/log-out" class="navbar-item button is-flex">
          <span>
            <img src="../assets/log-out.svg" alt="">
          </span>
          <span>Log out</span>
        </router-link>
      </div>
    </div>
  </div>
  </nav>
</template>

<script>
export default {
  name: 'NavBar',

  data () {
    return {
      isLogged: false
    }
  },
  mounted () {
    const burger = document.querySelector('.burger')
    const nav = document.querySelector('#' + burger.dataset.target)

    burger.addEventListener('click', function () {
      burger.classList.toggle('is-active')
      nav.classList.toggle('is-active')
      const buttons = nav.querySelector('.buttons')
      buttons.classList.toggle('animated')
      buttons.classList.toggle('slideInLeft')
    })
  }
}

</script>

<style scoped>
img {
  padding-right: 0.385rem;
}

.buttons {
  padding-right: 0.385rem;
}

.button >>> img {
  display: block;
}

.navbar-menu.is-active {
  display: flex;
  justify-content: end;
}

</style>
