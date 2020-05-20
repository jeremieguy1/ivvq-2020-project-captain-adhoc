<template>
  <div class="container">
    <div class="section">
      <div class="columns">
        <div class="column is-half is-offset-one-quarter cart title">
          <div class="fontawesome-icon">
            <i class="fas fa-cart-arrow-down"></i>
          </div>
          <div>
            <p>Gestion des stocks</p>
          </div>
        </div>
      </div>
    </div>
    <div class="section showProducts">
      <div v-for="product in inventoryProducts" v-bind:key="product.id_produit" class="card animated fadeIn">
        <header v-on:click="displayContentInventory(product)" class="card-header">
          <p class="card-header-title">
            {{product.nom_produit}}
          </p>
          <a class="card-header-icon" aria-label="more options">
            <div v-if="!product.display">
              <span class="icon">
                <i class="fas fa-angle-up" aria-hidden="true"></i>
              </span>
            </div>
            <div v-if="product.display">
              <span class="icon">
                <i class="fas fa-angle-down" aria-hidden="true"></i>
              </span>
            </div>
          </a>
        </header>
        <div v-if="product.display" class="card-content">
          <div class="columns">
            <div class="column image is-one-third">
              <figure class="image is-3by2">
                <img :src="`${product.image_produit}`">
              </figure>
            </div>
            <div class="column corps">
              <table class="table is-fullwidth">
                <tr >
                  <div class="section quantite_produit has-text-centered">
                    <p class="quantite_produit has-text-centered">Quantité : </p> <br>
                    <p class="stock_quantite_produit">(Stock: {{product.quantite_produit}}) </p>
                    <div class="select" >
                      <select class="product.quantite">
                        <option disabled selected>{{product.quantity}}</option>
                        <option v-for="(value, index) in product.quantite_produit + 1" :key="index" :id="`${product.nom_produit}`">
                          <div>{{ index }}</div>
                        </option>
                      </select>
                    </div>
                    &nbsp;{{product.prix_produit}}€/u <br>
                  </div>
                </tr>
                <tr >
                  <div class="box-shadow updateQuantity has-text-centered">
                    <button  v-on:click="updateQuantity(product)"  class="button has-text-centered">Confirmer la quantité</button>
                  </div>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { configs } from '../http-common'
import {mapState} from 'vuex'
import Buefy from 'buefy'
import Vue from 'vue'

Vue.use(Buefy, {
  defaultIconPack: 'fa'
})

export default {
  name: 'InventoryManagement',
  mounted () {
    this.getProducts()
  },
  data () {
    return {
      products: []
    }
  },
  computed: mapState(['inventoryProducts']),
  methods: {
    displayContentInventory (commande) {
      this.$store.commit('displayContentInventory', commande)
    },
    getProducts () {
      axios
        .get('/produits', configs)
        .then(response => {
          for (var product in response.data) {
            response.data[product].display = false
          }
          this.products = response.data
          console.log(this.products)
          this.$store.commit('inventoryProducts', this.products)
        })
    }
  }
}
</script>

<style scoped>
  .card {
    margin-bottom: 2%
  }

  .column.corps {
    justify-content: left;
    font-weight: bold;
  }
  .section.numberinput {
    display: flex;
    justify-content: center;
  }
  .column.title {
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

  .card-header {
    cursor: pointer;
  }

  div.fontawesome-icon {
    padding-right: 0.325rem;
  }

</style>
