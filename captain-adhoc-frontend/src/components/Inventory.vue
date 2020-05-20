<template>
  <div class="container">
    <div class="section">
      <div class="columns">
        <div class="column is-half is-offset-one-quarter cart title">
          <div class="fontawesome-icon">
            <i class="fas fa-cart-plus"></i>
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
          <div class="columns is-fullwidth" style="width: 100%; position: relative">
            <div class="column">
              <p class="card-header-title">
                {{product.nom_produit}}
              </p>
            </div>
            <div class="column">
              <p v-if="product.quantite_produit > 1" class="card-header-title quantiteMulti">
                En stock : {{product.quantite_produit}} produits disponibles
              </p>
              <p v-if="product.quantite_produit === 1" class="card-header-title quantiteUni">
                En stock : {{product.quantite_produit}} produit disponible
              </p>
              <p v-else-if="product.quantite_produit === 0" class="card-header-title quantiteNull">
                Rupture stock : Aucun produit disponible
              </p>
            </div>
            <div class="column ">
              <p class="card-header-title total">
                {{product.prix_produit}}€ unité
              </p>
            </div>
            <div class="column is-1">
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
            </div>
          </div>
        </header>
        <div v-if="product.display" class="card-content">
          <div class="columns">
            <div class="column image is-one-third">
              <figure class="image is-3by2">
                <img :src="`${product.image_produit}`">
              </figure>
            </div>
            <div class="column corps">
              <div class="section quantite_produit">
                <p class="quantite_produit has-text-centered">Mettre à jour la quantité disponible : </p>
                <div>
                  <b-field class="section numberinput">
                    <b-numberinput min="0" :max="`${Number.MAX_SAFE_INTEGER}`" v-model="product.quantite_produit"
                                   controls-rounded controls-position="compact" type="is-success" ></b-numberinput>
                  </b-field>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <section class="paybox">
      <div class="container animated fadeIn">
        <div class="to-pay box-shadow has-text-centered">
          <button v-on:click="updateInventory()"  class="button has-text-centered">Validez vos modifications</button>
        </div>
      </div>
    </section>
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
