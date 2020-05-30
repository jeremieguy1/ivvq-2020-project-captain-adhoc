<template>
  <section v-if="productsToPay" class="section">
    <div class="container animated fadeIn">
      <div class="columns">
        <div class="column is-half is-offset-one-quarter cart title">
          <div class="fontawesome-icon">
            <i class="fas fa-cart-plus"></i>
          </div>
          <div>
            <p>Paiement de votre panier</p>
          </div>
        </div>
      </div>
    </div>
    <div class="section">
      <div class="container animated fadeIn">
        <div class="columns">
          <div class="column">
            <div class="box">
              <div class="field">
                <label class="section subtitle">Montant de la transaction: {{productsToPay.totalPrice}}€ pour {{productsToPay.totalProducts}} produits</label>
              </div>
              <table class="table is-fullwidth">
                <div class="columns is-centered">
                  <div class="column is-half">
                    <tr>
                      <div class="columns formulaire">
                        <div class="column is-half">
                          <div class="credit-card has-text-right">
                            <p class="num-credit-card">Numéro de la carte bancaire</p>
                          </div>
                        </div>
                        <div class="column is-half">
                          <div class="field credit-card ">
                            <div class="control has-icons-left has-icons-right">
                              <div class="field">
                                <div :class="{ 'animated headShake': $v.numberCart.$dirty && $v.numberCart.$error}">
                                  <input
                                    id="credit-card"
                                    placeholder=""
                                    v-model.trim="$v.numberCart.$model"
                                    class="input"  :class="{ 'is-success': !$v.numberCart.$error && $v.numberCart.$dirty && validPayment(numberCart) ,
                                      'is-danger': $v.numberCart.$error && $v.numberCart.$dirty && !validPayment(numberCart)}"
                                    type="num"
                                    name="credit-card">
                                  <span class="icon is-small is-left">
                                    <i class="fas fa-credit-card"></i>
                                  </span>
                                  <span v-if="!$v.numberCart.$error && $v.numberCart.$dirty">
                                    <span class="icon is-small is-right animated zoomIn">
                                      <i class="fas fa-check"></i>
                                    </span>
                                  </span>
                                  <span v-else class="icon is-small is-right" :class="{ 'animated headShake': numberCart.length != ''}">
                                    <i class="fas fa-times"></i>
                                  </span>
                                </div>
                                <p class="has-text-danger" v-if="!$v.numberCart.required && $v.numberCart.$dirty">Le numéro de carte bancaire est obligatoire</p>
                                <p class="has-text-danger" v-if="!$v.numberCart.minLength">
                                  Doit contenir exactement {{$v.numberCart.$params.minLength.min}} chiffres</p>
                                <p class="has-text-danger" v-if="!$v.numberCart.numeric">
                                  Doit contenir uniquement des chiffres</p>
                                <p class="has-text-danger" v-if="$v.numberCart.minLength && !$v.numberCart.maxlength">
                                  Doit contenir exactement {{$v.numberCart.$params.minLength.min}} chiffres</p>
                                <p class="has-text-danger" v-if="$v.numberCart.$params.minLength.min === numberCart.length && $v.numberCart.numeric && !validPayment(numberCart)">
                                  Votre code n'est pas conforme à la formule Luhn</p>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </tr >
                    <tr>
                      <div class="columns formulaire">
                        <div class="column is-half">
                          <div class="expiration-date has-text-right">
                            <p class="num-expiration-date">Date d'expiration</p>
                          </div>
                        </div>
                        <div class="column is-half">
                          <div class="expiration-date">
                            <div class="columns">
                              <div class="column">
                                <b-field>
                                  <b-select id="select-Month" class="select-Month"  v-model="year" placeholder="Mois">
                                    <option
                                      v-for="option in this.mois"
                                      :value="option.id"
                                      :key="option.id">
                                      {{ option.nom }}
                                    </option>
                                  </b-select>
                                </b-field>
                              </div>
                              <div class="column">
                                <b-field>
                                  <b-select  id="select-Year" class="select-Year"  v-model="month" placeholder="Année">
                                    <option
                                      v-for="option in range(this.anneeMin, this.anneeMax)"
                                      :value="option"
                                      :key="option">
                                      {{ option }}
                                    </option>
                                  </b-select>
                                </b-field>
                              </div>
                            </div>
                          </div>
                          <div>
                            <p class="expiration-date-alert has-text-danger" style="display: none">
                              La date d'expiration est obligatoire</p>
                          </div>
                        </div>
                      </div>
                    </tr>
                    <tr>
                      <div class="columns formulaire">
                        <div class="column is-half">
                          <div class="security-code has-text-right" >
                            <p class="num-security-code">Code de sécurité</p>
                          </div>
                        </div>
                        <div class="column is-half">
                          <div class="field security-code">
                            <div class="control has-icons-left has-icons-right">
                              <div class="field">
                                <div :class="{ 'animated headShake': $v.cvc.$dirty && $v.cvc.$error}">
                                  <input
                                    id="cvc"
                                    placeholder="CVC"
                                    v-model.trim="$v.cvc.$model"
                                    class="input"  :class="{ 'is-success': !$v.cvc.$error && $v.cvc.$dirty,
                                      'is-danger': $v.cvc.$error && $v.cvc.$dirty}"
                                    type="num"
                                    name="cvc">
                                  <span class="icon is-small is-left">
                                    <i class="fas fa-lock"></i>
                                  </span>
                                  <span v-if="!$v.cvc.$error && $v.cvc.$dirty">
                                    <span class="icon is-small is-right animated zoomIn">
                                      <i class="fas fa-check"></i>
                                    </span>
                                  </span>
                                  <span v-else class="icon is-small is-right" :class="{ 'animated headShake': cvc.length != ''}">
                                    <i class="fas fa-times"></i>
                                  </span>
                                </div>
                                <p class="has-text-danger" v-if="!$v.cvc.required && $v.cvc.$dirty">Le code de sécurité CVC est obligatoire</p>
                                <p class="has-text-danger" v-if="!$v.cvc.minLength">
                                  Doit contenir exactement {{$v.cvc.$params.minLength.min}} chiffres</p>
                                <p class="has-text-danger" v-if="$v.cvc.minLength && !$v.cvc.maxlength">
                                  Doit contenir exactement {{$v.cvc.$params.minLength.min}} chiffres</p>
                                <p class="has-text-danger" v-if="!$v.cvc.numeric">
                                  Doit contenir uniquement des chiffres</p>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </tr>
                  </div>
                </div>
              </table>
              <p :class="{ 'has-text-centered has-text-danger': !submitStatus.status, 'has-text-centered has-text-success': submitStatus.status}">{{submitStatus.message}}</p>
              <div class="section to-pay box-shadow has-text-centered">
                <button v-on:click="submitPayment()"  class="button has-text-centered to-pay">Payez votre panier</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <section v-else class="section">
    <div class="container animated fadeIn">
      <div class="columns">
        <div class="column cart title is-fourth-fifths">
          <div>
            <p class="no-products has-text-centered">Veuillez ajouter une escroquerie avant de payer, les dons ne sont pas acceptés !</p>
          </div>
          <div class="section">
            <button @click="backToProducts" class="button">
              <span class="fontawesome-icon">
                <i class="fas fa-arrow-left"></i>
              </span>
              <span>Retour à la liste des escroqueries</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import axios from 'axios'
import { configs } from '../http-common'
import { required, minLength, numeric, maxLength } from 'vuelidate/lib/validators'
import Buefy from 'buefy'
import Vue from 'vue'
import PaymentCompleteModal from './PaymentCompleteModal'
Vue.use(Buefy, {
  defaultIconPack: 'fa'
})

const moisInit = [{'id': 1, 'nom': 'Janvier'},
  {'id': 2, 'nom': 'Février'},
  {'id': 3, 'nom': 'Mars'},
  {'id': 4, 'nom': 'Avril'},
  {'id': 5, 'nom': 'Mai'},
  {'id': 6, 'nom': 'Juin'},
  {'id': 7, 'nom': 'Juillet'},
  {'id': 8, 'nom': 'Août'},
  {'id': 9, 'nom': 'Septembre'},
  {'id': 10, 'nom': 'Octobre'},
  {'id': 11, 'nom': 'Novembre'},
  {'id': 12, 'nom': 'Décembre'}
]

export default {
  name: 'Payment',
  created () {
    this.productsToPay = JSON.parse(window.localStorage.getItem('commandToPay'))
  },
  data () {
    return {
      mois: moisInit,
      anneeMin: 2020,
      anneeMax: 2030,
      year: null,
      month: null,
      cvc: '',
      submitStatus: [{
        status: null,
        message: ''
      }],
      numberCart: '',
      productsToPay: null
    }
  },
  validations: {
    cvc: {
      required,
      minLength: minLength(3),
      maxlength: maxLength(3),
      numeric
    },
    numberCart: {
      required,
      minLength: minLength(16),
      maxlength: maxLength(16),
      numeric
    }
  },
  methods: {
    range: function (start, end) {
      return Array(end - start + 1).fill().map((_, idx) => start + idx)
    },
    submitPayment () {
      this.submitStatus = ''
      this.$v.$touch()
      let expirationElement = document.getElementsByClassName('expiration-date-alert')[0]

      if (this.$v.$invalid || !this.validPayment(this.numberCart)) {
        // Invalid form
        if (this.month === null || this.year === null) {
          expirationElement.style.display = 'block'
        }
      } else {
        if (this.month === null || this.year === null) {
          expirationElement.style.display = 'block'
        } else {
          var productToPay = []
          var productToPush
          for (var product in this.productsToPay.products) {
            productToPush = {
              idProduct: this.productsToPay.products[product].idProduct,
              quantity: this.productsToPay.products[product].quantity
            }
            productToPay.push(productToPush)
          }
          var pay = {
            code: this.productsToPay.code,
            productPurchaseList: productToPay
          }

          axios
            .post('/purchases/order',
              pay
              , configs)
            .then(response => {
              this.submitStatus = {
                status: true,
                message: 'La commande a bien été prise en compte'
              }
              localStorage.removeItem('commandsProduct')
              localStorage.removeItem('commandToPay')
              this.$buefy.modal.open({
                parent: this,
                component: PaymentCompleteModal,
                hasModalCard: true,
                trapFocus: true
              })
            })
            .catch((e) => {
              switch (e.response.status) {
                case (409): {
                  this.submitStatus = {
                    status: false,
                    message: 'Le produit n\'est plus en stock !'
                  }
                  break
                }
                default: {
                  this.submitStatus = {
                    status: false,
                    message: `Erreur de soumission (${e.response.status})`
                  }
                }
              }
            })
        }
      }
    },
    validPayment (cartNumber) {
      if (cartNumber.length !== 16) {
        return false
      }
      var sommeAll = 0
      var even = false

      for (var i = cartNumber.length - 1; i >= 0; i--) {
        var temp = parseInt(cartNumber.charAt(i), 10)
        if (even && ((temp *= 2) > 9)) {
          temp -= 9
        }
        even = !even
        sommeAll += temp
      }
      return (sommeAll % 10) === 0
    },
    backToProducts () {
      this.$router.push('products')
    }
  }
}
</script>

<style scoped>
  .subtitle {
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: bold;
  }

  .columns.formulaire {
    padding: 2%
  }

  div.fontawesome-icon {
    padding-right: 0.325rem;
  }

  .column.title {
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: bold;
  }

  span.fontawesome-icon {
    padding-right: 0.325rem;
  }

  .cart {
    display: flex;
    flex-direction: column;
  }

  .no-products {
    padding-bottom: 0.875rem;
  }

</style>
