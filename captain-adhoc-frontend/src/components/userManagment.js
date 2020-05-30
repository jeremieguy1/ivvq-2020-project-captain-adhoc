import axios from 'axios'
import store from '../store'
import { configs } from '../http-common'
export default {
  getUser () {
    axios
      .get('/member', configs)
      .then(response => {
        store.commit('setCurrentUser', response.data)
      })
      .catch((e) => {
        switch (e.response.status) {
          default: {
            this.submitStatus = `Erreur de soumission (${e.response.status})`
          }
        }
      })
  }
}
