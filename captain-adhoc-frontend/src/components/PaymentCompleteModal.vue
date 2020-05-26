<template>
  <div class="modal-card animated fadeIn">
    <header class="modal-card-head">
      <div>
        <p class="title has-text-centered">Merci pour votre achat !</p>
      </div>
    </header>
    <section class="modal-card-body">
        <div class="columns">
          <div class="column">
            <p>Redirection vers les escorqueries en cours</p>
          </div>
        </div>
        <div>
          <circular-count-down-timer
            :initial-value="7"
            :steps="1"
            @finish="backToProducts"
          ></circular-count-down-timer>
          </div>
    </section>
    <footer class="modal-card-foot">
      <button @click="backToProducts"
        class="button"
        name="Retourner aux escroqueries">
        <span class="icon is-small is-right">
          <i class="fas fa-arrow-left"></i>
        </span>
        <span>Retourner aux escroqueries</span>
      </button>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'PaymentCompleteModal',
  methods: {
    backToProducts (product) {
      localStorage.removeItem('commandsProduct')
      localStorage.removeItem('commandToPay')
      this.$router.push('products')
      this.$parent.close()
    }
  }
}
</script>

<style scoped>
.modal-card {
  background: url('https://pbs.twimg.com/media/EROfX4-X0AAIUrk.jpg') no-repeat center center;
  background-size: cover;
  border-radius: 25px;
}

.modal-card-head {
  border-bottom: none;
  background: none;
}

.title {
  background-color: rgba(255, 253, 231, 0.5);
  margin-left: 10%;
  margin-right: 10%;
}

p {
    color: #363636;
}

button {
  background-color: rgba(255, 253, 231, 0.55);
  border: none
}

.columns {
  display: flex;
  flex-direction: column;
}
.modal-card-body {
    height: 12.5rem;
    background: none;
    color: black;
    font-size: 1.875rem;
    display: flex;
    align-items: center;
    margin-left: 2%;
    margin-right: 25%;
}
.modal-card-foot {
  display: flex;
  justify-content: center;
  background: none;
  border-top: none;
}
</style>
