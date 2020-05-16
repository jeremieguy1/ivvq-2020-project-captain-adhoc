<template>
  <div class="background is-fullheight">
    <div class="hero-like">
      <div class="container">
        <div class="section">
          <div class="has-text-centered" :class="{ 'margin': !shrinkHeader}">
            <p class="title">Retrouvez toutes nos escroqueries !</p>
          </div>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="section">
        <div class="columns is-multiline">
          <div v-for="product in products" v-bind:key="product.id_produit" class="column is-one-third">
            <div @click="openDetails(product)"
             @keydown.enter="openDetails(product)"
             tabindex="0" class="card">
              <div class="card-image">
                <figure class="image is-4by3">
                  <img :src="`${product.image_produit}`" alt="">
                </figure>
              </div>
              <div class="infos is-flex">
                <div class=" is-flex info">
                  <div class="is-size-4">
                    <p class="has-text-right">{{product.nom_produit}}</p>
                  </div>
                  <div class="is-size-5">
                    <p class="has-text-right">{{product.prix_produit}}€</p>
                  </div>
                </div>
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
      this.shrinkHeader = currentScrollPosition <= 50
      this.lastScrollPosition = currentScrollPosition
    },
    getProducts () {
      axios
        .get('/produits', configs)
        .then(response => {
          this.products = response.data
          this.$store.commit('storeProducts', this.products)
        })
    },
    openDetails (product) {
      console.log('Open details on : ' + product.nom_produit)
    }
  }
}
</script>

<style scoped>

.hero-like {
  /* background-color: hsl(204, 86%, 53%); */
}
img {
  background-repeat: no-repeat;
}
div.card {
  outline: solid 0 #eeeeee;
  transition: outline 0.6s linear;
  margin: 0.5rem;
  cursor: pointer;
}
div .card:hover,
div .card:focus {
  outline-width: 0.825rem;
}

.infos {
  flex-direction: row;
  justify-content: space-evenly;
  align-items: center;
  padding: 0.525rem 0 0.525rem 0;
}

.info {
  flex-direction: column;
  justify-content: space-evenly;
}

.fontawesome-icon {
  font-size: 1.825rem;
}

.title {
  margin-top: 30px;
  margin-bottom: 30px;
  transition: font-size 0.6s, margin-top 0.5s, margin-bottom 0.5s;
}
.margin >>> .title {
  margin-top: 0;
  margin-bottom: 0;
  font-size: 99%;
}

.background {
  background-image: url('https://urlz.fr/cHWP');
  background-repeat: no-repeat;
  background-size: cover;
  object-fit: cover;
}

p.title {
  color: white;
}

</style>
