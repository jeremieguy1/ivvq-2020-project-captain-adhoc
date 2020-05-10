<template>
  <div>
    <h1 class="title">Historique des escroqueries</h1>
    <div v-for="commande in commandes" v-bind:key="commande.id" class="card animated fadeIn" >
      <header  class="card-header">
        <p class="card-header-title">
          <time> {{commande.date_commande}}</time>
        </p>
        <p class="card-header-title">
          {{commande.total}}$
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
        <div class="content">
          <div v-for="prod in commande.produits" v-bind:key="prod.nom_produit" class="columns">
            <div class="column">
              <figure class="image" >
                <img :src="prod.image_produit" />
              </figure>
            </div>
            <div class="column">
              {{prod.nom_produit}}
            </div>
            <div class="column">
              {{prod.quantite_produit}}
            </div>
          </div>
        </div>
        <div id="code">
          <div v-if="commande.code != ''" >
            Code <b> {{commande.code}} </b>
          </div>
          <div v-else>
            Aucun code utilisé
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'History',
  data: () => ({
    commandes: [
      {
        id: 1,
        name: 'James',
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
        date_commande: '22/02/99',
        total: '8',
        code: 'M2DL2019',
        display: false
      },
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
      }
    ]
  }),
  methods: {
    displayContent: function (commande) {
      if (commande.display) {
        commande.display = false
      } else {
        commande.display = true
      }
    }
  }
}
</script>

<style scoped>
.title {
  color: black;
  text-align: center;
  font-weight: bold;
  width: 100%;
}

.card {
  margin-left: 20%;
  margin-right: 20%;
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

#code {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
