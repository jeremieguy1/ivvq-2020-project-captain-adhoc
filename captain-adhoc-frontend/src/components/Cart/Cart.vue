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
    <div class="section showProducts">
      <div v-for="product in cartProducts" v-bind:key="product.idProduct" class="card animated fadeIn">
        <header v-on:click="displayContentCart(product)" class="card-header">
          <p class="card-header-title">
            {{product.productName}}
          </p>
          <p v-if="product.quantity > 1" class="card-header-title total">
            {{getTotalPrixProduct(product)}}€ ({{product.quantity}} produits)
          </p>
          <p v-else class="card-header-title total">
            {{getTotalPrixProduct(product)}}€ ({{product.quantity}} produit)
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
                <img :src="`${product.productPicture}`" alt="">
              </figure>
            </div>
            <div class="column corps">
              <table class="table is-fullwidth">
                <tr >
                  <div class="section quantite_produit has-text-centered">
                    <p class="quantite_produit has-text-centered">Quantité : </p> <br>
                    <p class="stock_quantite_produit">(Stock: {{product.productQuantity}}) </p>
                    <div class="select">
                      <select class="product-quantite"  @change="updateQuantity(product)" v-model="product.quantity" >
                        <option disabled selected>{{product.quantity}}</option>
                        <option v-for="(value, index) in product.productQuantity + 1"
                                :key="index" :id="`${product.productName}-${index}`">
                          <span>{{ index }}</span>
                        </option>
                      </select>
                    </div>
                    &nbsp;{{product.productPrice}}€/u <br>
                  </div>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
    <section v-if="cartProducts.length !== 0" class="paybox">
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
                <p v-if="getTotalProduct(cartProducts) > 1" class="to-pay total-cart">
                  Total à payer : {{getTotalCart(cartProducts)}}€ ({{getTotalProduct(cartProducts)}} produits)
                </p>
                <p v-else class="to-pay total-cart">
                  Total à payer : {{getTotalCart(cartProducts)}}€ ({{getTotalProduct(cartProducts)}} produit)
                </p>
              </div>
              <div class="to-pay box-shadow has-text-centered">
                <button v-on:click="payCart()" class="button has-text-centered">
                  <p>Payez votre panier</p>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <section v-else class="info is-centered animated fadeIn">
      <div>
        <figure class="has-text-centered">
          <img src="../../assets/rick_info.png" alt="" width="600" height="400" >
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
    getTotalPrixProduct (product) {
      return product.quantity * product.productPrice
    },
    getTotalProduct (products) {
      var totalProduct = 0
      for (var product in products) {
        totalProduct = parseInt(totalProduct) + parseInt(products[product].quantity)
      }
      return totalProduct
    },
    getTotalCart (products) {
      var totalCart = 0
      for (var product in products) {
        totalCart = parseInt(totalCart) + parseInt(products[product].quantity * products[product].productPrice)
      }
      return parseInt(totalCart)
    },
    payCart () {
      // A voir ici si on a besoin de rajouter de données dans commandsProduct localstorage
      var commandToPAy = {
        code: this.code,
        products: this.products,
        totalPrice: this.getTotalCart(this.products),
        totalProducts: this.getTotalProduct(this.products)
      }
      localStorage.setItem('commandToPay', JSON.stringify(commandToPAy))
      this.$router.push('payment')
    },
    displayContentCart (purchase) {
      this.$store.commit('displayContentCart', purchase)
    },
    updateQuantity (product) {
      var newQuantityProduct = {
        productName: product.productName,
        quantity: product.quantity
      }
      this.$store.commit('updateQuantity', newQuantityProduct)
    },
    getProductsCart () {
      axios
        .get('/products', configs)
        .then(response => {
          var localProducts = JSON.parse(localStorage.getItem('commandsProduct'))
          for (var product in response.data) {
            if (response.data[product].productQuantity > 0) {
              for (var localProduct in localProducts) {
                if (localProducts[localProduct].quantity > 0) {
                  if (response.data[product].productName === localProducts[localProduct].productName) {
                    response.data[product].display = false
                    if (localProducts[localProduct].quantity < response.data[product].productQuantity) {
                      response.data[product].quantity = localProducts[localProduct].quantity
                    } else {
                      response.data[product].quantity = response.data[product].productQuantity
                    }
                    this.products.push(response.data[product])
                  }
                }
              }
            }
          }
          if (this.products.length !== 0) {
            localStorage.setItem('commandsProduct', JSON.stringify(this.products))
          }
          this.$store.commit('cartProducts', this.products)
        })
        .catch((e) => {
          switch (e.response.status) {
            default: {
              this.submitStatus = `Erreur de soumission (${e.response.status})`
            }
          }
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

  .to-pay {
    font-weight: bold;
    padding-top: 1rem;
  }

  .stock_quantite_produit {
    display: inline-block;
    margin-top: 1%;
  }

</style>
