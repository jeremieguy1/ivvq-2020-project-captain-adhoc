import Vuex from 'vuex'
import Vue from 'vue'
import axios from 'axios'
import { configs } from '../http-common'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    commandes: []
  },
  mutations: {
    getData (state) {
      axios
        .get('/commandes', configs)
        .then(response => {
          this.info = response.data
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
    }
  }
})
