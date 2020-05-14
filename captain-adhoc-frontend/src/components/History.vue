<template>
  <div class="container">
    <div class="section">
      <div class="columns">
        <div class="column is-half is-offset-one-quarter historique title">
          <p>Historique des escroqueries</p>
        </div>
      </div>
    </div>
    <div class="section">
      <div v-for="commande in commandes" v-bind:key="commande.id_commande" class="card animated fadeIn">
        <header v-on:click="displayContent(commande)" class="card-header">
          <p class="card-header-title date">
            <time> {{commande.date_commande}}</time>
          </p>
          <p class="card-header-title total">
            {{getTotalPrix(commande)}}$
          </p>
          <a class="card-header-icon" aria-label="more options">
            <div v-if="!commande.display">
              <span class="icon">
                <i class="fas fa-angle-up" aria-hidden="true"></i>
              </span>
            </div>
            <div v-if="commande.display">
              <span class="icon">
                <i class="fas fa-angle-down" aria-hidden="true"></i>
              </span>
            </div>
          </a>
        </header>
        <div v-if="commande.display" class="card-content">
          <table class="content">
            <tr class="columns">
              <td class="column">
                <p>{{tableHead.image_produit}}</p>
              </td>
              <td class="column">
                <p>{{tableHead.produit}}</p>
              </td>
              <td class="column">
                <p>{{tableHead.quantite}}</p>
              </td>
            </tr>
            <tr v-for="prod in commande.commandeProduitsList" v-bind:key="prod.id_commandeProduit" class="columns">
              <td class="column">
                <figure class="image">
                  <img :src="getImgUrl(prod.produit.image_produit)">
                </figure>
              </td>
              <td class="column nom_produit">
                <div class="nom_produit">{{prod.produit.nom_produit}}</div>
              </td>
              <td class="column">
                {{prod.produit.quantite_produit}}
              </td>
            </tr>
          </table>
          <td class="code">
            <div v-if="commande.code !== ''">
              <p>Code <b> {{commande.code}} </b></p>
            </div>
            <div v-else>
              <p>Aucun code utilisé</p>
            </div>
          </td>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {HTTP} from '../http-common'
import Vuex, {mapState} from 'vuex'
import Vue from 'vue'

window.Vue = Vue
Vue.use(Vuex)

const store = new Vuex.Store({
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
          if (com.display) com.display = false
          else com.display = true
        }
      })
    }
  }
})

export default {
  name: 'History',
  store,
  created () {
    this.getData()
  },
  data: () => ({
    tableHead: {
      image_produit: '',
      produit: 'Produit',
      quantite: 'Quantité'
    }
  }),
  computed: mapState(['commandes']),
  methods: {
    getData () {
      this.$store.commit('getData')
    },
    displayContent (commande) {
      this.$store.commit('displayContent', commande)
    },
    getTotalPrix (commande) {
      let total = 0
      commande.commandeProduitsList.forEach(commandeProduit => {
        total = total + commandeProduit.produit.quantite_produit * commandeProduit.produit.prix_produit
      })
      return total
    },
    getImgUrl (img) {
      return require('@/assets/' + img)
    }
  }
}
</script>

<style scoped>
  table {
    border-collapse: collapse;
    width: 100%;
  }

  th, td {
    text-align: left;
    padding: 8px;
  }

  tr:nth-child(even) {
    background-color: #cccccc;
  }

  tr:nth-child(odd) {
    background-color: #eeeeee;
  }

  .card {
    margin-bottom: 2%
  }

  .column {
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: bold;
  }

  .card-header-title {
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: bold;
  }

  .code {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  div.card.animated {
    cursor: pointer;
  }
</style>
