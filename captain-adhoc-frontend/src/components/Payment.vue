<template>
    <div class="container">
      <div class="section">
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
        <div class="columns">
          <div class="column">
            <div class="box">
                <div class="field">
                  <label class="section subtitle">Montant de la transaction: € pour xx produits</label>
                </div>
                <table class="table is-fullwidth">
                  <div class="columns is-centered">

                    <div class="column is-half">
                      <tr>
                        <div class="columns formulaire">
                          <div class="column is-half">
                            <div class="carte-bancaire has-text-right">
                              <p class="num-carte-bancaire">Numéro de la carte bancaire</p>
                            </div>
                          </div>
                          <div class="column is-half">
                            <div class="carte-bancaire ">
                              <b-field class="b-field  ">
                                <b-input v-model="name"></b-input>
                              </b-field>
                            </div>
                          </div>
                        </div>
                      </tr >
                      <tr>
                        <div class="columns formulaire">
                          <div class="column is-half">
                            <div class="carte-bancaire has-text-right">
                              <p class="num-carte-bancaire">Date d'expiration</p>
                            </div>
                          </div>
                          <div class="column is-half">
                            <div class="carte-bancaire ">
                              <div class="columns">
                                <div class="column">
                                  <b-field>
                                    <b-select placeholder="Mois">
                                      <option
                                        v-for="option in mois"
                                        :value="option.id"
                                        :key="option.id">
                                        {{ option.nom }}
                                      </option>
                                    </b-select>
                                  </b-field>
                                </div>
                                <div class="column">
                                  <b-field>
                                    <b-select placeholder="Année">
                                      <option
                                        v-for="option in range(anneeMin, anneeMax)"
                                        :value="option"
                                        :key="option">
                                        {{ option }}
                                      </option>
                                    </b-select>
                                  </b-field>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </tr>
                      <tr>
                        <div class="columns formulaire">
                          <div class="column is-half">
                            <div class="carte-bancaire has-text-right" >
                              <p class="num-carte-bancaire">Code de sécurité</p>
                            </div>
                          </div>
                          <div class="column is-half">
                            <div class="carte-bancaire ">
                              <b-field class="b-field " >
                                <b-input v-model="name"></b-input>
                              </b-field>
                            </div>
                          </div>
                        </div>
                      </tr >
                    </div>
                  </div>
                </table>
              <div class="section to-pay box-shadow has-text-centered">
                <button v-on:click="payCart()"  class="button has-text-centered to-pay">Payez votre panier</button>
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
  name: 'Payment',
  mounted () {
    // this.getProducts()
  },
  data () {
    return {
      mois: [{'id': 1, 'nom': 'Janvier'},
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
      ],
      anneeMin: 2020,
      anneeMax: 2030
    }
  },
  methods: {
    range: function (start, end) {
      return Array(end - start + 1).fill().map((_, idx) => start + idx)
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
  .b-field {
    display: flex;
    justify-content: left;
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

  div.fontawesome-icon {
    padding-right: 0.325rem;
  }

</style>
