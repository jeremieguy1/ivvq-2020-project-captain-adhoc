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
      <div v-for="commande in commandes" v-bind:key="commande.id_commande" class="card animated fadeIn" >
        <header  class="card-header">
          <p class="card-header-title">
            <time> {{commande.date_commande}}</time>
          </p>
          <p class="card-header-title">
            {{getTotalPrix()}}$
          </p>
          <a v-on:click="displayContent(commande)" class="card-header-icon" aria-label="more options" >
            <div v-if="!commande.display">
              <span  class="icon">
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
            <tr v-for="prod in commande.commandeProduitsList" v-bind:key="prod.id_commandeProduit"  class="columns">
              <td class="column" >
                <div class="forback">
                  <figure class="image" >
                    <img :src="prod.image_produit" />
                  </figure>
                </div>
              </td>
              <td class="column nom_produit">
                <div class="nom_produit">{{prod.nom_produit}}</div>
              </td>
              <td class="column">
                {{prod.quantite_produit}}
              </td>
            </tr>
          </table>
          <td class="code">
            <div v-if="commande.code !== ''" >
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

  export default {
    name: 'History',
    created: function () {
      HTTP
        .get('/commandes')
        .then(response => {
          this.info = response.data
          console.log(response.data)
          this.commandes = response.data
          console.log(this.commandes)

        })
    },
    data: () => ({
      tableHead: {
        image_produit: '',
        produit: 'Produit',
        quantite: 'Quantité'
      },
      commandes: [/*
      {
        id_commande: 1,
        name: 'James',
        commandeProduitsList: [
          {
            id_commandeProduit: '4',
            produit: [
              {
                description_produit: '4',
                id_produit: '4',
                prix_produit: '4',
                nom_produit: '4',
                quantite_produit: '4',
                image_produit: '4'
              }
            ],
            quantite_produit: '4',
            nom_produit: 'PS5',
            image_produit: require('@/assets/ps5_large.png')
          },
          {
            quantite_produit: '2',
            nom_produit: 'cyberbox',
            image_produit: require('@/assets/cyberbox_large.png')
          },
          {
            quantite_produit: '9999',
            nom_produit: 'Rick le bg',
            image_produit: require('@/assets/cyberbox_large.png')
          }
        ],
        date_commande: '22/02/99',
        total: '8',
        code: 'M2DL2019',
        display: false
      }/*,
      {
        id: 2,
        name: 'Fatima',
        produits: [
          {
            quantite_produit: '4',
            nom_produit: 'PS5',
            image_produit: require('@/assets/ps5_large.png')
          },
          {
            quantite_produit: '2',
            nom_produit: 'cyberbox',
            image_produit: require('@/assets/cyberbox_large.png')
          },
          {
            quantite_produit: '9999',
            nom_produit: 'Rick le bg',
            image_produit: require('@/assets/LeftArrow.png')
          }
        ],
        date_commande: '20/02/99',
        total: '25',
        code: '',
        display: false
      },
      {
        id: 3,
        name: 'Xin',
        produits: [
          {
            quantite_produit: '4',
            nom_produit: 'PS5',
            image_produit: require('@/assets/ps5_large.png')
          },
          {
            quantite_produit: '2',
            nom_produit: 'cyberbox',
            image_produit: require('@/assets/cyberbox_large.png')
          },
          {
            quantite_produit: '9999',
            nom_produit: 'Rick le bg',
            image_produit: require('@/assets/cyberbox_large.png')
          }
        ],
        date_commande: '24/02/99',
        total: '9999',
        code: 'UPSTLS3',
        display: false
      }*/
      ]
    }),
    methods: {
      displayContent: function(commande) {
        if (commande.display) {
          commande.display = false
        } else {
          commande.display = true
          console.log(this.getData())
        }
      },
      getData () {
        HTTP
          .get('/commandes')
          .then(response => {
            this.info = response.data
            console.log(response.data)
            this.commandes = response.data
            console.log(this.commandes)

          })
      },
      getTotalPrix () {
        var total = 0
        this.commandes.forEach(commande =>
          commande.commandeProduitsList.forEach(commandeProduit =>
            total += commandeProduit.produit.quantite_produit * commandeProduit.produit.prix_produit

          )
        );
        return total
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

  .historique {

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
</style>
