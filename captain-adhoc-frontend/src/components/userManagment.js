import axios from 'axios'
import store from '../store'
import { configs } from '../http-common'
export default {
  getUser () {
    axios
      .get('/current-user', configs)
      .then(response => {
        store.commit('setCurrentUser', response.data)
      })
  }
}
