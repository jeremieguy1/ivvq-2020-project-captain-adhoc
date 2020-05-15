import History from '@/components/History'
import { mount, createLocalVue } from '@vue/test-utils'
import chai from 'chai'
import { mutations } from '@/store/index'
import Vuex from 'vuex'
import Vue from 'vue'
import sinon from 'sinon'
import moxios from 'moxios'
import axios from 'axios'

Vue.use(Vuex)

const localVue = createLocalVue()
localVue.use(Vuex)
let store
let respond = [
  {
    id_commande: 4,
    display: false,
    date_commande: '21/20/2020',
    code: 'code',
    commandeProduitsList: [
      {
        id_commandeProduit: 7,
        produit: {
          id_produit: 2,
          quantite_produit: 15,
          nom_produit: 'produit1',
          description_produit: 'description1',
          image_produit: 'cyberbox_large.png',
          prix_produit: 1,
          marchand: {
            id_marchand: 1,
            identifiant_marchand: 'marchand1'
          }
        },
        quantite_commande_produit: 4
      }
    ],
    acheteur: null
  },
  {
    id_commande: 5,
    display: true,
    date_commande: '22/20/2020',
    code: 'code',
    commandeProduitsList: [
      {
        id_commandeProduit: 8,
        produit: {
          id_produit: 2,
          quantite_produit: 15,
          nom_produit: 'produit1',
          description_produit: 'description1',
          image_produit: 'cyberbox_large.png',
          prix_produit: 1,
          marchand: {
            id_marchand: 1,
            identifiant_marchand: 'marchand1'
          }
        },
        quantite_commande_produit: 0
      },
      {
        id_commandeProduit: 9,
        produit: {
          id_produit: 3,
          quantite_produit: 16,
          nom_produit: 'produit2',
          description_produit: 'description2',
          image_produit: 'ps5_large.png',
          prix_produit: 2,
          marchand: {
            id_marchand: 1,
            identifiant_marchand: 'marchand1'
          }
        },
        quantite_commande_produit: 0
      }
    ],
    acheteur: null
  },
  {
    id_commande: 6,
    display: true,
    date_commande: '23/20/2020',
    code: '',
    commandeProduitsList: [
      {
        id_commandeProduit: 10,
        produit: {
          id_produit: 3,
          quantite_produit: 16,
          nom_produit: 'produit2',
          description_produit: 'description2',
          image_produit: 'ps5_large.png',
          prix_produit: 2,
          marchand: {
            id_marchand: 1,
            identifiant_marchand: 'marchand1'
          }
        },
        quantite_commande_produit: 0
      }
    ],
    acheteur: null
  }
]

describe('History.vue', () => {
  beforeEach(() => {
    moxios.install(axios)
  })

  afterEach(() => {
    moxios.uninstall(axios)
  })

  it('Should calculate good total ', () => {
    // Given
    moxios.withMock(function () {
      moxios.wait(() => {
        axios.get('/commandes').then()
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandes: respond
          }
        }).then(
          response => {
            submit(response.data.commandes)

            const wrapper = mount(History, {
              store,
              localVue
            })

            // When
            /*
              Commande 1:
                    quantite_produit: 15,
                    prix_produit: 1,
                    total = 15 * 1 = 15
             */
            // Then
            chai.assert.strictEqual(wrapper.findAll('.card-header-title.total').at(0).text(), '15$')

            // When
            /*
              Commande 2:
                    quantite_produit: 15,
                    prix_produit: 1,
                    total1 = 15 * 1 = 15
                    quantite_produit: 16,
                    prix_produit: 2,
                    total = 16 * 2 = 32
                    total = total1 + total2 = 15 + 32 = 47
             */
            // Then
            chai.assert.strictEqual(wrapper.findAll('.card-header-title.total').at(1).text(), '47$')

            // When
            /*
                Commande 3:
                   quantite_produit: 16,
                   prix_produit: 2,
                   total = 16 * 2 = 32
            */
            // Then
            chai.assert.strictEqual(wrapper.findAll('.card-header-title.total').at(2).text(), '32$')
            done()
          })
      })
    })
  })

  it('Should getTotalPrix calculate good total ', () => {
    // Given
    const spy = sinon.spy(History.methods, 'getTotalPrix')
    let commande = {
      commandeProduitsList: [
        {
          produit: {
            quantite_produit: 6,
            prix_produit: 5
          }
        },
        {
          produit: {
            quantite_produit: 36,
            prix_produit: 4
          }
        }
      ]
    }

    // When
    let total = History.methods.getTotalPrix(commande)

    // Then
    chai.assert.strictEqual(total, 174)
    chai.assert.strictEqual(spy.calledOnce, true)
  })

  it('Should display simple data ', () => {
    // Given
    moxios.withMock(function () {
      moxios.wait(() => {
        axios.get('/commandes').then()
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandes: respond
          }
        }).then(
          response => {
            submit(response.data.commandes)
            const wrapper = mount(History, {
              store,
              localVue
            })
            /*
              Commande 1:
                date_commande: '21/20/2020',
             */
            chai.assert.strictEqual(wrapper.findAll('.card-header-title.date').at(0).text(), '21/20/2020')

            /*
              Commande 2:
                date_commande: '22/20/2020',
             */
            chai.assert.strictEqual(wrapper.findAll('.card-header-title.date').at(1).text(), '22/20/2020')

            /*
              Commande 3:
                date_commande: '23/20/2020',
            */
            chai.assert.strictEqual(wrapper.findAll('.card-header-title.date').at(2).text(), '23/20/2020')
            done()
          })
      })
    })
  })

  it('Should call store action displayContent when button is clicked', () => {
    // Given
    moxios.withMock(function () {
      moxios.wait(() => {
        const spy = sinon.spy(History.methods, 'displayContent')
        axios.get('/commandes').then()
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandes: respond
          }
        }).then(
          response => {
            submit(response.data.commandes)
            const wrapper = mount(History, {
              store,
              localVue
            })

            // When
            wrapper.find('header').trigger('click')

            // Then
            chai.assert.strictEqual(spy.calledOnce, true)
            done()
          })
      })
    })
  })

  it('Should be unfold after a clicked when it was fold', () => {
    // Given
    moxios.withMock(function () {
      moxios.wait(() => {
        axios.get('/commandes').then()
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandes: respond
          }
        }).then(
          response => {
            submit(response.data.commandes)
            const wrapper = mount(History, {
              store,
              localVue
            })

            // When
            wrapper.findAll('header').at(0).trigger('click')

            // Then
            chai.assert.strictEqual(store.state.commandes[0].display, true)
            chai.assert.strictEqual(store.state.commandes[1].display, true)
            chai.assert.strictEqual(store.state.commandes[2].display, true)
            done()
          })
      })
    })
  })

  it('Should be fold after a clicked when it was unfold', () => {
    // Given
    moxios.withMock(function () {
      moxios.wait(() => {
        axios.get('/commandes').then()
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandes: respond
          }
        }).then(
          response => {
            submit(response.data.commandes)
            const wrapper = mount(History, {
              store,
              localVue
            })

            // When
            wrapper.findAll('header').at(1).trigger('click')

            // Then
            chai.assert.strictEqual(store.state.commandes[0].display, false)
            chai.assert.strictEqual(store.state.commandes[1].display, false)
            chai.assert.strictEqual(store.state.commandes[2].display, true)
            done()
          })
      })
    })
  })

  it('Should show the code when there is a code', () => {
    // Given
    moxios.withMock(function () {
      moxios.wait(() => {
        axios.get('/commandes').then()
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandes: respond
          }
        }).then(
          response => {
            submit(response.data.commandes)
            const wrapper = mount(History, {
              store,
              localVue
            })

            // When
            // for commande 1 code: 'code'
            wrapper.findAll('header').at(0).trigger('click')

            // Then
            chai.assert.strictEqual(wrapper.findAll('.codeToDisplay').at(0).text(), 'Code code')
            done()
          })
      })
    })
  })

  it('Should not show the code when there is no code', () => {
    // Given
    moxios.withMock(function () {
      moxios.wait(() => {
        axios.get('/commandes').then()
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandes: respond
          }
        }).then(
          response => {
            submit(response.data.commandes)
            const wrapper = mount(History, {
              store,
              localVue
            })

            // When
            // for commande 3 code: ''
            wrapper.findAll('header').at(2).trigger('click')

            // Then
            chai.assert.strictEqual(wrapper.findAll('.noCodeToDisplay').at(0).text(), 'Aucun code utilisé')
            done()
          })
      })
    })
  })

  it('Should getData called at the component creation', () => {
    // Given
    moxios.withMock(function () {
      moxios.wait(() => {
        const spy = sinon.spy(History.methods, 'getData')
        axios.get('/commandes').then()
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandes: respond
          }
        }).then(
          response => {
            submit(response.data.commandes)

            // When
            mount(History, {store, localVue})

            // Then
            chai.assert.strictEqual(spy.calledOnce, true)
            done()
          })
      })
    })
  })

  it('Should getImgUrl be called for each images to upload', () => {
    // Given
    moxios.withMock(function () {
      moxios.wait(() => {
        const spy = sinon.spy(History.methods, 'getImgUrl')
        axios.get('/commandes').then()
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandes: respond
          }
        }).then(
          response => {
            submit(response.data.commandes)

            // When
            // There are 3 images to upload at the creation
            mount(History, {
              store,
              localVue
            })

            // Then
            chai.assert.strictEqual(spy.calledThrice, true)
            done()
          })
      })
    })
  })
})

function submit (commandes) {
  store = new Vuex.Store({
    mutations,
    state: {
      commandes: commandes
    }
  })
}
