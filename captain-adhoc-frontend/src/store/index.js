import Vuex from 'vuex'
import Vue from 'vue'

Vue.use(Vuex)
import {HTTP} from '../http-common'

export default new Vuex.Store({
  state: {
    commandes: []
  },
  mutations: {
    getData (state) {
      HTTP
        .get('/commandes')
        .then(response => {
          this.info = response.data
          response.data.forEach(commande => Vue.observable(commande.display = false))
          state.commandes = response.data
        })
    },
    displayContent (state, commande) {
      state.commandes.forEach(com => {
        if (com.id_commande === commande.id_commande) {
          com.display = !com.display;
        }
      })
    }
  }
})
