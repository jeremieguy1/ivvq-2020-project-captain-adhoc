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
      <div v-for="purchase in commandes" v-bind:key="purchase.idPurchase" class="card animated fadeIn">
        <header v-on:click="displayContent(purchase)" class="card-header">
          <p class="card-header-title date">
            <time> {{date(purchase.purchaseDate)}}</time>
          </p>
          <p class="card-header-title total">
            {{getTotalPrix(purchase)}}€
          </p>
          <a class="card-header-icon" aria-label="more options">
            <div v-if="!purchase.display">
              <span class="icon">
                <i class="fas fa-angle-up" aria-hidden="true"></i>
              </span>
            </div>
            <div v-if="purchase.display">
              <span class="icon">
                <i class="fas fa-angle-down" aria-hidden="true"></i>
              </span>
            </div>
          </a>
        </header>
        <div v-if="purchase.display" class="card-content">
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
            <tr v-for="prod in purchase.purchaseProductList" v-bind:key="prod.idPurchaseProduct" class="columns">
              <td class="column">
                <figure class="image">
                  <img :src="`${prod.product.productPicture}`">
                </figure>
              </td>
              <td class="column">
                <div>
                <p class="nom_produit">{{prod.product.productName}}</p></div>
              </td>
              <td class="column">
                <p class="quantite_produit">{{prod.purchaseProductQuantity}}</p>
              </td>
            </tr>
          </table>
          <td class="code">
            <div v-if="purchase.code !== ''">
              <p class="codeToDisplay">Code<b> {{purchase.code}} </b></p>
            </div>
            <div v-else>
              <p class="noCodeToDisplay">Aucun code utilisé</p>
            </div>
          </td>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
  name: 'History',
  mounted () {
    this.getData()
  },
  data: () => ({
    tableHead: {
      productPicture: '',
      product: 'Produit',
      quantity: 'Quantité'
    }
  }),
  computed: mapState(['commandes']),
  methods: {
    getData () {
      this.$store.commit('getData')
    },
    displayContent (purchase) {
      this.$store.commit('displayContent', purchase)
    },
    getTotalPrix (purchase) {
      let total = 0
      purchase.purchaseProductList.forEach(purchaseProduct => {
        total = total + purchaseProduct.product.productPrice * purchaseProduct.purchaseProductQuantity
      })
      return total
    },
    date: function (str) {
      if (!str) {
        return '(n/a)'
      }
      str = new Date(str)
      return str.getFullYear() + '-' + ((str.getMonth() < 9) ? '0' : '') + (str.getMonth() + 1) + '-' +
        ((str.getDate() < 10) ? '0' : '') + str.getDate() + ' ' + str.getUTCHours() + ':' + str.getUTCMinutes()
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

  .card-header {
    cursor: pointer;
  }
</style>
