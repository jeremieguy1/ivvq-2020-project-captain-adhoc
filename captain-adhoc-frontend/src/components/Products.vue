<template>
  <div>
    <div class="hero is-primary">
      <div class="hero-body">
        <div class="container">
          <div :class="{ 'margin': !shrinkHeader}">
            <p class="title">Retrouvez toutes nos escroqueries !</p>
          </div>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="section">
        <div class="columns is-multiline">
          <div class="column is-one-third">
            <div class="card">
              <div class="card-content">
                <div class="card-image">
                  <figure class="image is-4by3">
                    <img src="../assets/cyberbox_large.png" alt="">
                  </figure>
                </div>
                <div class="infos is-flex">
                  <div class=" is-flex info">
                    <div class="is-size-6">
                      <p class="has-text-right">Ps5 de ses morts</p>
                    </div>
                    <div class="is-size-5">
                      <p class="has-text-right">1500$</p>
                    </div>
                  </div>
                  <div class="cart is-flex">
                    <div class="fontawesome-icon">
                      <i class="fas fa-cart-plus"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="column is-one-third">
            <div class="card">
              <div class="card-content">
                <div class="card-image">
                  <figure class="image is-4by3">
                    <img src="../assets/cyberbox_large.png" alt="">
                  </figure>
                </div>
                <div class="infos is-flex">
                  <div class=" is-flex info">
                    <div class="is-size-6">
                      <p class="has-text-right">Ps5 de ses morts</p>
                    </div>
                    <div class="is-size-5">
                      <p class="has-text-right">1500$</p>
                    </div>
                  </div>
                  <div class="cart is-flex">
                    <div class="fontawesome-icon">
                      <i class="fas fa-cart-plus"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="column is-one-third">
            <div class="card">
              <div class="card-content">
                <div class="card-image">
                  <figure class="image is-4by3">
                    <img src="../assets/cyberbox_large.png" alt="">
                  </figure>
                </div>
                <div class="infos is-flex">
                  <div class=" is-flex info">
                    <div class="is-size-6">
                      <p class="has-text-right">Ps5 de ses morts</p>
                    </div>
                    <div class="is-size-5">
                      <p class="has-text-right">1500$</p>
                    </div>
                  </div>
                  <div class="cart is-flex">
                    <div class="fontawesome-icon">
                      <i class="fas fa-cart-plus"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="column is-one-third">
            <div class="card">
              <div class="card-content">
                <div class="card-image">
                  <figure class="image is-4by3">
                    <img src="../assets/cyberbox_large.png" alt="">
                  </figure>
                </div>
                <div class="infos is-flex">
                  <div class=" is-flex info">
                    <div class="is-size-6">
                      <p class="has-text-right">Ps5 de ses morts</p>
                    </div>
                    <div class="is-size-5">
                      <p class="has-text-right">1500$</p>
                    </div>
                  </div>
                  <div class="cart is-flex">
                    <div class="fontawesome-icon">
                      <i class="fas fa-cart-plus"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="column is-one-third">
            <div class="card">
              <div class="card-content">
                <div class="card-image">
                  <figure class="image is-4by3">
                    <img src="../assets/cyberbox_large.png" alt="">
                  </figure>
                </div>
                <div class="infos is-flex">
                  <div class=" is-flex info">
                    <div class="is-size-6">
                      <p class="has-text-right">Ps5 de ses morts</p>
                    </div>
                    <div class="is-size-5">
                      <p class="has-text-right">1500$</p>
                    </div>
                  </div>
                  <div class="cart is-flex">
                    <div class="fontawesome-icon">
                      <i class="fas fa-cart-plus"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import axios from 'axios'
import { configs } from '../http-common'

export default {
  name: 'Products',

  mounted () {
    window.addEventListener('scroll', this.onScroll)
    this.getProducts()
  },
  beforeDestroy () {
    window.removeEventListener('scroll', this.onScroll)
  },
  data () {
    return {
      shrinkHeader: true,
      lastScrollPosition: 0,
      products: []
    }
  },
  computed: mapState(['produits']),
  methods: {
    onScroll () {
      const currentScrollPosition = window.pageYOffset || document.documentElement.scrollTop
      if (currentScrollPosition < 0) {
        return
      }
      this.shrinkHeader = currentScrollPosition === 0
      this.lastScrollPosition = currentScrollPosition
    },
    getProducts () {
      axios
        .get('/produits', configs)
        .then(response => {
          this.products = response.data
          this.$store.commit('storeProducts', this.products)
        })
    }
  }
}
</script>

<style scoped>

img {
  background-repeat: no-repeat;
}
div.card {
  outline: solid 0 #eeeeee;
  transition: outline 0.6s linear;
  margin: 0.5rem;
}
div.card:hover {
  outline-width: 0.825rem;
}

.infos {
  flex-direction: row;
  justify-content: space-evenly;
  align-items: center;
}

.info {
  flex-direction: column;
  justify-content: space-evenly;
  padding-bottom: 0.325rem;
}

.cart {
  justify-content: center;
}

.fontawesome-icon {
  font-size: 1.175rem;
}

.title {
  margin-top: 30px;
  margin-bottom: 30px;
  transition: margin-top 0.5s;
  transition: margin-bottom 0.5s;
  transition: font-size 0.6s;
}
.margin >>> .title {
  margin-top: 0;
  margin-bottom: 0;
  font-size: 99%;
}

</style>
