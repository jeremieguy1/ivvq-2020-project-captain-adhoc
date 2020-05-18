<template>
  <div class="container">
    <div class="section">
      <div class="columns">
        <div class="column is-half is-offset-one-quarter cart title">
          <div class="fontawesome-icon">
            <i class="fas fa-cart-arrow-down"></i>
          </div>
          <div>
            <p>Votre Panier</p>
          </div>
        </div>
      </div>
    </div>
    <div class="section">
      <div v-for="product in cartProducts" v-bind:key="product.id_produit" class="card animated fadeIn">
        <header v-on:click="displayContentCart(product)" class="card-header">
          <p class="card-header-title date">
            <time> {{product.nom_produit}}</time>
          </p>
          <p class="card-header-title total">
            {{getTotalPrixProduct(product)}}$ ({{product.quantity}} produits)
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
                  <img src="../assets/cyberbox_large.png">
                </figure>
              </div>
              <div class="column corps">
                  <table class="table is-fullwidth">
                    <tr style="background-color: #eeeeee">
                      <div>
                        <p class="description">Description : </p>
                      </div>
                      <div>
                        <p class="description_produit"><br>{{product.description_produit}}</p>
                      </div>
                    </tr>
                    <tr >
                      <div class="section quantite_produit has-text-centered">
                        <p class="quantite_produit has-text-centered" style="">Quantité : </p> <br>
                        <p class="stock_quantite_produit" style="display: inline-block;">(Stock: {{product.quantite_produit}}) </p>
                        <div class="select" >
                          <select class="product.quantite">
                            <option v-for="(value, index) in product.quantite_produit" :key="index" :id="`${product.nom_produit}`">
                              <div>{{ index + 1 }}</div>
                            </option>
                          </select>
                        </div>
                        &nbsp;{{product.prix_produit}}$/u <br>
                      </div>
                    </tr>
                    <tr >
                      <div class="box-shadow has-text-centered">
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
  computed: mapState(['cartProducts']),
  methods: {
    getData () {
      this.$store.commit('getData')
    },
    getTotalPrixProduct (product) {
      console.log('getTotalPrixProduct')
      console.log(product.prix_produit)
      return product.quantity * product.prix_produit
    },
    displayContentCart (commande) {
      this.$store.commit('displayContentCart', commande)
    },
    updateQuantity (product) {
      var select = document.getElementsByClassName('product.quantite')
      var index = 0
      for (var element in select) {
        if (Number.isInteger(parseInt(element))) {
          if (select[element].options[0].id === product.nom_produit) {
            index = element
          }
        }
      }
      var newQuantityProduct = {
        nom_produit: product.nom_produit,
        quantity: select[index].value
      }
      this.$store.commit('updateQuantity', newQuantityProduct)
    },
    getProductsCart () {
      axios
        .get('/produits', configs)
        .then(response => {
          var localProducts = JSON.parse(window.localStorage.getItem('commandsProduct'))
          for (var product in response.data) {
            for (var localProduct in localProducts) {
              if (response.data[product].nom_produit === localProducts[localProduct].product) {
                response.data[product].display = false
                response.data[product].quantity = 2
                console.log(response.data[product])
                this.products.push(response.data[product])
                console.log(this.products)
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
  .card {
    margin-bottom: 2%
  }
  .column.corps {
    justify-content: left;
    font-weight: bold;
  }

  .column.title {
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: bold;
  }
  .button {
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
