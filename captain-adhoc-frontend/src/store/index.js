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
  isMercant: false
}

export const mutations = {
  /* History managment */
  getData (state) {
    axios
      .get('/commandes', configs)
      .then(response => {
        response.data.forEach(commande => Vue.observable(commande.display = false))
        state.commandes = response.data
      })
  },

  displayContent (state, commande) {
    state.commandes.forEach(com => {
      if (com.id_commande === commande.id_commande) {
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
