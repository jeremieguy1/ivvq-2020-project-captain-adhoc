<template>
  <nav class="navbar is-white" role="navigation" aria-label="main navigation">

    <div class="navbar-brand">
      <router-link to="/home" class="navbar-item is-flex">
        <div>
          <img src="../assets/brand-icon.png" alt="Accueil">
        </div>
        <div>
          <span><b>Accueil</b></span>
        </div>
      </router-link>

      <a role="button" class="navbar-burger burger" aria-label="menu" aria-expanded="false" data-target="navbar-menu">
        <span aria-hidden="true"></span>
        <span aria-hidden="true"></span>
        <span aria-hidden="true"></span>
      </a>
    </div>

  <div id="navbar-menu" class="navbar-menu">
    <div class="navbar-start">
      <div class="is-flex" v-if="isLogged">
        <router-link to="/products" class="navbar-item is-flex">
          <div class="fontawesome-icon">
            <i class="fas fa-grip-horizontal"></i>
          </div>
          <div>
            <span>Produits</span>
          </div>
        </router-link>
      </div>
    </div>

    <div class="navbar-end">
      <div v-if="!isLogged" class="buttons is-flex">
        <router-link to="/log-in" class="navbar-item button is-flex">
          <div class="fontawesome-icon">
            <i class="fas fa-sign-in-alt"></i>
          </div>
          <div>
            <span>Connexion</span>
          </div>
        </router-link>

        <router-link to="/sign-up" class="navbar-item button is-flex">
          <div class="fontawesome-icon">
            <i class="fas fa-user-plus"></i>
          </div>
          <div>
            <span>Inscription</span>
          </div>
        </router-link>

      </div>
      <div v-else class="buttons is-flex">
        <router-link to="/cart" class="navbar-item button is-flex" aria-label="Your cart">
          <div class="fontawesome-icon">
            <i class="fas fa-cart-arrow-down"></i>
          </div>
          <div>
            <span>Panier</span>
          </div>
        </router-link>

        <router-link to="/purchases-history" class="navbar-item button is-flex">
          <div class="fontawesome-icon">
            <i class="fas fa-history"></i>
          </div>
          <div>
            <span>Commandes</span>
          </div>
        </router-link>

        <router-link to="/log-out" class="navbar-item button is-flex">
          <div class="fontawesome-icon">
            <i class="fas fa-sign-out-alt"></i>
          </div>
          <div>
            <span>Déconnexion</span>
          </div>
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

div.fontawesome-icon {
  padding-right: 0.325rem;
  font-size: 1.175rem;
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
