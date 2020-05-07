<template>
  <nav class="navbar is-white" role="navigation" aria-label="main navigation">

    <div class="navbar-brand">
      <router-link to="/home" class="navbar-item">
        <img src="../assets/brand-icon.png" alt="go to home">
        <span><b>Accueil</b></span>
      </router-link>

      <a role="button" class="navbar-burger burger" aria-label="menu" aria-expanded="false" data-target="navbar-menu">
        <span aria-hidden="true"></span>
        <span aria-hidden="true"></span>
        <span aria-hidden="true"></span>
      </a>
    </div>

  <div id="navbar-menu" class="navbar-menu">
    <div class="navbar-start">
      <div v-if="isLogged">
        <router-link to="/products" class="navbar-item is-flex">
          <span class="fontawesome-icon">
            <i class="fas fa-grip-horizontal"></i>
          </span>
          <span>Produits</span>
        </router-link>
      </div>
    </div>

    <div class="navbar-end">
      <div v-if="!isLogged" class="buttons">
        <router-link to="/log-in" class="navbar-item button is-flex">
          <span class="fontawesome-icon">
            <i class="fas fa-sign-in-alt"></i>
          </span>
          <span>Connexion</span>
        </router-link>
        <router-link to="/sign-up" class="navbar-item button is-flex">
          <span class="fontawesome-icon">
            <i class="fas fa-user-plus"></i>
          </span>
          <span>Inscription</span></router-link>
      </div>
      <div v-else class="buttons">
        <router-link to="/cart" class="navbar-item button is-flex" aria-label="Your cart">
          <span class="fontawesome-icon">
            <i class="fas fa-cart-arrow-down"></i>
          </span>
          <span>Panier</span>
        </router-link>

        <router-link to="/purchases-history" class="navbar-item button is-flex">
          <span class="fontawesome-icon">
            <i class="fas fa-history"></i>
          </span>
          <span>Commandes</span>
        </router-link>

        <router-link to="/log-out" class="navbar-item button is-flex">
          <span class="fontawesome-icon">
            <i class="fas fa-sign-out-alt"></i>
          </span>
          <span>Déconnexion</span>
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
    const navMenu = document.querySelector('#' + burger.dataset.target)

    burger.addEventListener('click', function () {
      burger.classList.toggle('is-active')
      navMenu.classList.toggle('is-active')

      const buttons = navMenu.querySelector('.buttons')
      buttons.classList.toggle('animated')
      buttons.classList.toggle('slideInLeft')

      document.querySelector('.navbar').classList.toggle('is-mobile')
    })
  }
}

</script>

<style scoped>
img {
  padding-right: 0.325rem;
}

.buttons {
  padding-right: 0.325rem;
}

span.fontawesome-icon {
  padding-right: 0.325rem;
  font-size: 1.325rem;
}

nav.is-mobile  {
  display: flex;
  flex-direction: column;
}
.navbar-menu.is-active {
  display: flex;
  justify-content: space-between;
  flex-direction: row;
}

</style>
