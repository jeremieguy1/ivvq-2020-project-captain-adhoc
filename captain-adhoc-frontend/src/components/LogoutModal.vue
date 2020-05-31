<template>
  <div class="modal-card">
    <header class="modal-card-head">
      <div>
        <p class="title has-text-centered">Êtes-vous sûr de vouloir vous déconnecter ?</p>
      </div>
    </header>
    <section class="modal-card-body">

    </section>
    <footer class="modal-card-foot">
      <button @click="cancel"
        class="button is-success"
        name="Annuler">Annuler
      </button>
      <button @click="logout"
        class="button is-danger"
        name="Déconnexion">Déconnexion
      </button>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'LogoutModal',
  data () {
    return {}
  },
  methods: {
    logout () {
      localStorage.removeItem('Authorization')
      localStorage.removeItem('commandsProduct')
      localStorage.removeItem('commandToPay')
      this.$store.commit('setActiveUser', false)
      this.$router.push('home')
      this.$parent.close()
    },
    cancel () {
      this.$parent.close()
    }
  }
}
</script>

<style scoped>
.modal-card {
  background: url('../assets/sadTN.jpg') no-repeat center center;
  background-size: cover;
  border-radius: 25px;
  pointer-events: none;
}

.modal-card-head {
  border-bottom: none;
  background: none;
}

.title {
  background-color: rgba(255, 253, 231, 0.5);
  border-bottom: none;
  margin-left: 10%;
  margin-right: 10%;
}

.modal-card-body {
  height: 200px;
  background: none;
}

.modal-card-foot {
  display: flex;
  justify-content: space-evenly;
  background: none;
  border-top: none;
}

button.is-danger {
  pointer-events: auto;
}

.modal-card:hover {
  background: url('../assets/angryTN.png') no-repeat center center;
  background-size: cover;
}
</style>
