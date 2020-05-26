import Vuex from 'vuex'
import Vue from 'vue'
import axios from 'axios'
import { configs } from '../http-common'

Vue.use(Vuex)

const state = {
  commandes: [],
  products: [],
  userStore: '',
  isLoggedStore: false,
  isMercant: false,
  cartProducts: [],
  produits: [],
  inventoryProducts: []
}

export const mutations = {
  /* History managment */
  getData (state) {
    axios
      .get('/purchases', configs)
      .then(response => {
        response.data.forEach(commande => Vue.observable(commande.display = false))
        state.commandes = response.data
      })
  },
  inventoryProducts (state, productsToManage) {
    state.inventoryProducts = productsToManage
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
  /* Product managment */
  storeProducts (state, productsToStore) {
    state.products = productsToStore
  },
  /* isLoggedStore managment */
  setActiveUser (state, isLoggedStore) {
    state.isLoggedStore = isLoggedStore
  },
  setCurrentUser (state, userStore) {
    state.userStore = userStore
  },
  setMercant (state) {
    state.isMercant = true
  },
  displayContentInventory (state, product) {
    state.inventoryProducts.forEach(prod => {
      if (prod.id_produit === product.id_produit) {
        prod.display = !prod.display
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
  },
  isLoggedStore: state => {
    return state.isLoggedStore
  },
  isMercant: state => {
    return state.isMercant
  },
  userStore: state => {
    return state.userStore
  }
}
export default new Vuex.Store({
  state,
  getters,
  mutations
})
