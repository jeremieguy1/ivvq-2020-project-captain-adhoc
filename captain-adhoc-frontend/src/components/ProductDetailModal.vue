<template>
  <div class="modal-card">
    <header class="modal-card-head">
      <div>
        <p class="title has-text-centered">{{product.nom_produit}}</p>
      </div>
    </header>
    <section class="modal-card-body">
        <div class="columns">
            <div class="column image is-one-third">
                <figure class="image is-3by2">
                  <img :src="`${product.image_produit}`">
                </figure>
            </div>
            <div class="column corps is-flex">
                <div>
                    <p>{{product.description_produit}}</p>
                </div>
                <div>
                    <p>{{product.prix_produit}}€</p>
                </div>
            </div>
        </div>
    </section>
    <footer class="modal-card-foot">
      <button @click="cancel"
        class="button"
        name="Annuler">
        <span>Annuler</span>
        <span class="icon is-small is-right">
            <i class="fas fa-times"></i>
        </span>
      </button>
      <button @click="addToCart(product)"
        class="button is-success"
        name="Déconnexion">
        <p>Ajouter au panier</p>
        <span class="fontawesome-icon">
            <i class="fas fa-cart-plus"></i>
        </span>
      </button>
    </footer>
  </div>
</template>

<script>
import Cart from './cart'

export default {
  name: 'ProductDetailModal',
  props: ['product'],
  methods: {
    addToCart (product) {
      var listCommandProduct = JSON.parse(window.localStorage.getItem('commandsProduct'))
      listCommandProduct = Cart.addToCart(listCommandProduct, product)
      window.localStorage.setItem('commandsProduct', JSON.stringify(listCommandProduct))
    },
    cancel () {
      this.$parent.close()
    }
  }
}
</script>

<style scoped>
.column.corps {
    flex-direction: column;
    justify-content: space-between;
    font-weight: bold;
  }
.modal-card {
  background-size: cover;
}

.modal-card-foot {
  display: flex;
  justify-content: space-evenly;
}

.fontawesome-icon {
  font-size: 1.325rem;
  padding-left: 0.325rem;
}
</style>
