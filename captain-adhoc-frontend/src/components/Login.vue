<template>
  <section class="section">
    <div class="container animated fadeIn">
      <div class="columns is-flex is-centered">
        <div class="column is-4">
          <div class="box">
            <form @submit.prevent="submit" class="form-group">
              <div class="levels">
                <div class="level-item">
                  <p class="title">Connexion</p>
                </div>
                <div class="level-item">
                  <p class="heading">Accéder aux escroqueries</p>
                </div>
              </div>
              <div class="field">
                <label class="label">Nom d'utilisateur *</label>
                <div class="control has-icons-left has-icons-right">
                  <div :class="{'animated headShake': $v.username.$dirty && $v.username.$error}">
                    <input
                      id="username"
                      class="input" :class="{ 'is-success': !$v.username.$error && $v.username.$dirty, 'is-danger': $v.username.$error && $v.username.$dirty}"
                      v-model.trim="$v.username.$model"
                      name="username"
                      type="text"
                      placeholder="Nom d'utilisateur...">
                    <span class="icon is-small is-left">
                      <i class="fas fa-user"></i>
                    </span>
                    <span v-if="!$v.username.$error && $v.username.$dirty">
                      <span class="icon is-small is-right animated zoomIn">
                        <i class="fas fa-check"></i>
                      </span>
                    </span>
                    <span v-else class="icon is-small is-right">
                        <i class="fas fa-times"></i>
                    </span>
                  </div>
                  <p class="has-text-danger" v-if="!$v.username.alphaNum && $v.username.$dirty">Le nom ne doit pas contenir de caractères spéciaux</p>
                  <p class="has-text-danger" v-if="!$v.username.required && $v.username.$dirty">Le nom est obligatoire</p>
                  <p class="has-text-danger" v-if="!$v.username.minLength">
                    Le nom doit contenir au moins {{$v.username.$params.minLength.min}} caractères</p>
                </div>
              </div>
              <div class="field">
                <label class="label">Mot de passe *</label>
                <div class="control has-icons-left has-icons-right">
                  <div :class="{'animated headShake': $v.password.$dirty && $v.password.$error}">
                    <input
                      id="password"
                      class="input" :class="{ 'is-success': !$v.password.$error && $v.password.$dirty,
                       'is-danger': $v.password.$error && $v.password.$dirty}"
                      v-model.trim="$v.password.$model"
                      name="password"
                      type="password"
                      placeholder="Mot de passe...">
                    <span class="icon is-small is-left">
                      <i class="fas fa-lock"></i>
                    </span>
                    <span v-if="!$v.password.$error && $v.password.$dirty">
                      <span class="icon is-small is-right animated zoomIn">
                        <i class="fas fa-check"></i>
                      </span>
                    </span>
                    <span v-else class="icon is-small is-right">
                        <i class="fas fa-times"></i>
                    </span>
                  </div>
                  <p class="has-text-danger" v-if="!$v.password.required && $v.password.$dirty">Le mot de passe est obligatoire</p>
                  <p class="has-text-danger" v-if="!$v.password.minLength">Le mot de passe doit contenir au moins {{$v.password.$params.minLength.min}} caractères</p>
                </div>
                <p class="has-text-danger" v-if="submitStatus != ''">{{submitStatus}}</p>
              </div>
              <div class="field is-flex">
                <button
                  class="button" :class="{ 'is-success': !$v.$invalid, 'is-danger': $v.$invalid && ($v.password.$dirty || $v.username.$dirty)}"
                  type="submit"
                >Se connecter</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>

import { required, minLength, alphaNum } from 'vuelidate/lib/validators'
import axios from 'axios'
import { configs } from '../http-common'

export default {
  name: 'Login',
  data () {
    return {
      username: '',
      password: '',
      submitStatus: ''
    }
  },
  validations: {
    username: {
      required,
      minLength: minLength(3),
      alphaNum
    },
    password: {
      required,
      minLength: minLength(8)
    }
  },
  methods: {
    submit () {
      this.submitStatus = ''
      this.$v.$touch()
      if (this.$v.$invalid) {
        // Invalid form
      } else {
        const body = {
          nomUtilisateur: this.username,
          motDePasse: this.password
        }
        axios
          .post('/login', body, configs)
          .then(response => {
            localStorage.setItem('Authorization', response.headers['authorization'])
            this.$router.push('Products')
          })
          .catch((e) => {
            switch (e.response.status) {
              case (403): {
                this.submitStatus = 'Nom d\'utilisateur ou mot de passe incorrect'
                break
              }
              case (409): {
                this.submitStatus = `Le nom d'utilisateur ${this.username} est déjà utilisé`
                break
              }
              default: {
                this.submitStatus = `Erreur de connexion (${e.response.status})`
              }
            }
          })
      }
    }
  }
}
</script>

<style scoped>
  div.is-flex.field {
    justify-content: center;
  }

  .box {
    box-shadow: 0 2px 3px rgba(10,10,10,.3), 0 0 0 1px rgba(10,10,10,.1);
  }

  .level-item >>> .title {
    padding-bottom: 1rem;
  }

  .level-item >>> .heading {
    padding-bottom: 0.325rem;
  }

  .has-text-danger {
    padding-top: 0.325rem;
  }
</style>
