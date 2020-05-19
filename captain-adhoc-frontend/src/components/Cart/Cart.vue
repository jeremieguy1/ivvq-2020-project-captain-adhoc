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
                  <img :src="`${product.image_produit}`">
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
                            <option v-for="(value, index) in product.quantite_produit + 1" :key="index" :id="`${product.nom_produit}`">
                              <div>{{ index }}</div>
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
    <section v-if="cartProducts.length !== 0" class="section">
      <div class="container animated fadeIn">
        <div class="columns is-flex has-text-centered is-centered">
          <div class="column is-4">
            <div class="box">
              <div>
                <div class="field">
                  <label class="label">Saisir un code de réduction</label>
                  <div class="control has-icons-right">
                    <div :class="{'animated headShake': $v.code.$dirty && $v.code.$error}">
                      <input
                        id="code"
                        class="input" :class="{ 'is-success': !$v.code.$error && $v.code.$dirty, 'is-danger': $v.code.$error && $v.code.$dirty}"
                        v-model.trim="$v.code.$model"
                        name="code"
                        type="text">
                      <span v-if="!$v.code.$error && $v.code.$dirty">
                      <span class="icon is-small is-right animated zoomIn">
                        <i class="fas fa-check"></i>
                      </span>
                    </span>
                      <span v-else class="icon is-small is-right">
                        <i class="fas fa-times"></i>
                    </span>
                    </div>
                    <p class="has-text-danger" v-if="!$v.code.minLength">
                      Le code doit contenir au moins {{$v.code.$params.minLength.min}} caractères</p>
                  </div>
                </div>
              </div>
              <div>
                <p class="to_pay total_cart">
                  Total à payer : {{getTotalCart()}}$ ({{getTotalProduct()}} produits)
                </p>
              </div>
              <div class="to_pay box-shadow has-text-centered">
                <button v-on:click="payCart()"  class="button has-text-centered">Payez votre panier</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <section v-else class="section is-centered">
      <div>
        <figure class="image is-1">
          <img src="../../assets/rick_info.png">
        </figure>
      </div>
    </section>

  </div>
</template>

<script>
import axios from 'axios'
import { configs } from '../../http-common'
import {mapState} from 'vuex'
import { required, minLength } from 'vuelidate/lib/validators'

export default {
  name: 'Cart',
  mounted () {
    this.getProductsCart()
  },
  data () {
    return {
      code: '',
      products: []
    }
  },
  validations: {
    code: {
      required,
      minLength: minLength(6)
    }
  },
  computed: mapState(['cartProducts']),
  methods: {
    getData () {
      this.$store.commit('getData')
    },
    getTotalPrixProduct (product) {
      return product.quantity * product.prix_produit
    },
    getTotalProduct () {
      var localProducts = JSON.parse(window.localStorage.getItem('commandsProduct'))
      var totalProduct = 0
      for (var localProduct in localProducts) {
        totalProduct = parseInt(totalProduct) + parseInt(localProducts[localProduct].quantity)
      }
      return totalProduct
    },
    getTotalCart () {
      var localProducts = JSON.parse(window.localStorage.getItem('commandsProduct'))
      var totalCart = 0
      for (var localProduct in localProducts) {
        totalCart = parseInt(totalCart) + parseInt(localProducts[localProduct].quantity * localProducts[localProduct].prix_produit)
      }
      return parseInt(totalCart)
    },
    payCart () {
      // A voir ici si on a besoin de rajouter de données dans commandsProduct localstorage
      console.log('Go to PAYYYYY')
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
            if (response.data[product].quantite_produit > 0) {
              for (var localProduct in localProducts) {
                if (localProducts[localProduct].quantity > 0) {
                  if (response.data[product].nom_produit === localProducts[localProduct].nom_produit) {
                    response.data[product].display = false
                    if (localProducts[localProduct].quantity < response.data[product].quantite_produit) {
                      response.data[product].quantity = localProducts[localProduct].quantity
                    } else {
                      response.data[product].quantity = response.data[product].quantite_produit
                    }
                    this.products.push(response.data[product])
                  }
                }
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

  .to_pay {
    font-weight: bold;
    padding-top: 1rem;
  }

</style>
