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
                          <div class="column">
                            <div class="carte-bancaire has-text-right">
                              <p class="num-carte-bancaire">Numéro de la carte bancaire</p>
                            </div>
                          </div>
                          <div class="column">
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
                          <div class="column">
                            <div class="carte-bancaire has-text-right">
                              <p class="num-carte-bancaire">Date d'expiration</p>
                            </div>
                          </div>
                          <div class="column">
                            <div class="carte-bancaire ">
                              <b-field class="b-field ">
                                <b-input v-model="name"></b-input>
                              </b-field>
                            </div>
                          </div>
                        </div>
                      </tr >
                      <tr>
                        <div class="columns formulaire">
                          <div class="column">
                            <div class="carte-bancaire has-text-right" >
                              <p class="num-carte-bancaire">Code de sécurité</p>
                            </div>
                          </div>
                          <div class="column">
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
export default {
  name: 'Payment'
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
