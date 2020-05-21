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
                    <p>{{product.quantite_produit}} en stock</p>
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
import CartManagment from '../components/Cart/CartManagment'

export default {
  name: 'ProductDetailModal',
  props: ['product'],
  methods: {
    addToCart (product) {
      var listCommandProduct = JSON.parse(window.localStorage.getItem('commandsProduct'))
      listCommandProduct = CartManagment.addToCart(listCommandProduct, product)
      window.localStorage.setItem('commandsProduct', JSON.stringify(listCommandProduct))
      this.$parent.close()
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
    line-height: 1rem;
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
