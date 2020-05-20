import Vuex from 'vuex'
import Vue from 'vue'
import axios from 'axios'
import { configs } from '../http-common'

Vue.use(Vuex)

const state = {
  commandes: [],
  cartProducts: [],
  products: []
}

export const mutations = {
  getData (state) {
    axios
      .get('/commandes', configs)
      .then(response => {
        response.data.forEach(commande => Vue.observable(commande.display = false))
        state.commandes = response.data
      })
  },
  storeProducts (state, productsToStore) {
    state.products = productsToStore
  },
  cartProducts (state, productsToCart) {
    state.cartProducts = productsToCart
  },
  displayContentCart (state, product) {
    state.cartProducts.forEach(prod => {
      if (prod.id_produit === product.id_produit) {
        prod.display = !prod.display
      }
    })
  },
  displayContent (state, command) {
    state.commandes.forEach(com => {
      if (com.id_commande === command.id_commande) {
        com.display = !com.display
      }
    })
  },
  updateQuantity (state, newQuantityProduct) {
    state.cartProducts.forEach(com => {
      if (com.nom_produit === newQuantityProduct.nom_produit) {
        com.quantity = newQuantityProduct.quantity
      }
    })
    window.localStorage.setItem('commandsProduct', JSON.stringify(state.cartProducts))
  }
}

export const getters = {
  allProducts: state => {
    return state.produits
  }
}
export default new Vuex.Store({
  state,
  getters,
  mutations
})
