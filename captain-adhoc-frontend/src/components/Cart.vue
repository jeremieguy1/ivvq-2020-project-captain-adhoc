<template>
    <div> Cart works !!</div>
</template>

<script>
import axios from 'axios'
import { configs } from '../http-common'
import {mapState} from 'vuex'

export default {
  name: 'Cart',
  mounted () {
    console.log(window.localStorage.getItem('commandsProduct'))
    console.log('oi')

    this.getProductsCart()
  },
  data () {
    return {
      products: []
    }
  },
  computed: mapState(['commandes']),
  methods: {
    displayContentCart (commande) {
      this.$store.commit('displayContentCart', commande)
    },
    getProductsCart () {
      axios
        .get('/produits', configs)
        .then(response => {
          var localProducts = JSON.parse(window.localStorage.getItem('commandsProduct'))
          for (var product in response.data) {
            for (var localProduct in localProducts) {
              if (response.data[product].nom_produit === localProducts[localProduct].product) {
                this.products.push(response.data[product])
              }
            }
          }
          this.$store.commit('cartProducts', this.products)
        })
    }
  }
}
</script>

<style scoped>

</style>
