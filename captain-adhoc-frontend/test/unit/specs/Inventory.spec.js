import Vuex from 'vuex'
import Inventory from '@/components/Inventory'
import { mount, createLocalVue } from '@vue/test-utils'
import { mutations } from '@/store/index'
import moxios from 'moxios'
import axios from 'axios'
import sinon from 'sinon'
import chai from 'chai'
import Vuelidate from 'vuelidate'

const localVue = createLocalVue()
localVue.use(Vuelidate)

let store
const listProductsResponse = [
  {
    id_produit: 2,
    quantite_produit: 15,
    nom_produit: 'PS5',
    description_produit: 'Encore une playstation de folie \\o/',
    image_produit: 'https://urlz.fr/cHLz',
    prix_produit: 1,
    marchand: {
      id_marchand: 1,
      identifiant_marchand: 'marchand1'
    },
    display: false
  },
  {
    id_produit: 3,
    quantite_produit: 16,
    nom_produit: 'CyberboX',
    description_produit: "Non comptant d'avoir les meilleures voitures au MONDE, Tesla propose la meilleure console de jeu grand public !",
    image_produit: 'https://urlz.fr/cHLH',
    prix_produit: 100000,
    marchand: {
      id_marchand: 1,
      identifiant_marchand: 'marchand1'
    },
    display: false
  },
  {
    id_produit: 4,
    quantite_produit: 2,
    nom_produit: 'Mad box',
    description_produit: 'Cette console va révolutionner le du la de esport !',
    image_produit: 'https://urlz.fr/cHJp',
    prix_produit: 666,
    marchand: null,
    display: false
  },
  {
    id_produit: 5,
    quantite_produit: 100,
    nom_produit: 'New retro +',
    description_produit: 'Elle fera tourner les jeux dernières générations tels que tetris et même Donkey kong 64 ! Et tout àa pour seulement 1399,99€',
    image_produit: 'https://urlz.fr/cHJz',
    prix_produit: 10,
    marchand: null,
    display: false
  },
  {
    id_produit: 6,
    quantite_produit: 5,
    nom_produit: 'Xbox Serie X',
    description_produit: "C'est partiiiii pour la console pc !",
    image_produit: 'https://urlz.fr/cHLM',
    prix_produit: 200,
    marchand: null,
    display: false
  }
]

describe('Inventory.vue', () => {
  beforeEach(() => {
    moxios.install(axios)
  })

  afterEach(() => {
    moxios.uninstall(axios)
  })

  it('Should call getProducts at the component creation', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/produits').then(spy)
      moxios.wait(() => {
        const spy = sinon.spy(Inventory.methods, 'getProducts')

        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            listProducts: listProductsResponse
          }
        }).then(
          response => {
            storeTest(response.data.listProducts)

            // When
            mount(Inventory, {
              store,
              localVue
            })

            // Then
            chai.assert.strictEqual(spy.calledOnce, true)
            spy.restore()
            done()
          })
      })
    })
  })

  it('Should display products after click them', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/produits').then(spy)
      moxios.wait(() => {
        const spy = sinon.spy(Inventory.methods, 'displayContentInventory')

        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            listProducts: listProductsResponse
          }
        }).then(
          response => {
            storeTest(response.data.listProducts)

            // When
            const wrapper = mount(Inventory, {
              store,
              localVue
            })

            wrapper.findAll('header').at(0).trigger('click')
            // Then
            chai.assert.isNotEmpty(store.state.inventoryProducts)
            chai.assert.strictEqual(spy.calledOnce, true)

            spy.restore()
            done()
          })
      })
    })
  })

  it('Should display products info', (done) => {
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/produits').then(spy)
      moxios.wait(() => {
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            listProducts: listProductsResponse
          }
        }).then(
          response => {
            storeTest(response.data.listProducts)

            // When
            const wrapper = mount(Inventory, {
              store,
              localVue
            })

            // Then
            chai.assert.include(wrapper.findAll('.card-header-title.prix-produit').at(0).text(), '1€')
            chai.assert.include(wrapper.findAll('.card-header-title.prix-produit').at(1).text(), '100000€')
            chai.assert.include(wrapper.findAll('.card-header-title.prix-produit').at(2).text(), '666€')
            chai.assert.include(wrapper.findAll('.card-header-title.prix-produit').at(3).text(), '10€')
            chai.assert.include(wrapper.findAll('.card-header-title.prix-produit').at(4).text(), '200€')

            chai.assert.include(wrapper.findAll('.card-header-title.nom-produit').at(0).text(), 'PS5')
            chai.assert.include(wrapper.findAll('.card-header-title.nom-produit').at(1).text(), 'CyberboX')
            chai.assert.include(wrapper.findAll('.card-header-title.nom-produit').at(2).text(), 'Mad box')
            chai.assert.include(wrapper.findAll('.card-header-title.nom-produit').at(3).text(), 'New retro +')
            chai.assert.include(wrapper.findAll('.card-header-title.nom-produit').at(4).text(), 'Xbox Serie X')

            chai.assert.include(wrapper.findAll('.card-header-title.quantiteMulti').at(0).text(), '15')
            chai.assert.include(wrapper.findAll('.card-header-title.quantiteMulti').at(1).text(), '16')
            chai.assert.include(wrapper.findAll('.card-header-title.quantiteMulti').at(2).text(), '2')
            chai.assert.include(wrapper.findAll('.card-header-title.quantiteMulti').at(3).text(), '100')
            chai.assert.include(wrapper.findAll('.card-header-title.quantiteMulti').at(4).text(), '5')
            done()
          })
      })
    })
  })

  it('Should be unfold/fold after a clicked', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/produits').then(spy)
      moxios.wait(() => {
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            listProducts: listProductsResponse
          }
        }).then(
          response => {
            storeTest(response.data.listProducts)

            // When
            const wrapper = mount(Inventory, {
              store,
              localVue
            })

            // When
            wrapper.findAll('header').at(4).trigger('click')

            // Then
            chai.assert.strictEqual(store.state.inventoryProducts[0].display, true)
            chai.assert.strictEqual(store.state.inventoryProducts[1].display, false)
            chai.assert.strictEqual(store.state.inventoryProducts[2].display, false)
            chai.assert.strictEqual(store.state.inventoryProducts[3].display, false)
            chai.assert.strictEqual(store.state.inventoryProducts[4].display, true)
            done()
          })
      })
    })
  })

  it('Should change quantity', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/produits').then(spy)
      moxios.wait(() => {
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            listProducts: [
              {
                id_produit: 2,
                quantite_produit: 15,
                nom_produit: 'PS5',
                description_produit: 'Encore une playstation de folie \\o/',
                image_produit: 'https://urlz.fr/cHLz',
                prix_produit: 1,
                marchand: {
                  id_marchand: 1,
                  identifiant_marchand: 'marchand1'
                },
                display: true
              }]
          }
        }).then(
          response => {
            storeTest(response.data.listProducts)

            // When
            const wrapper = mount(Inventory, {
              store,
              localVue
            })

            // When
            chai.assert.equal(wrapper.findAll('img').at(0).attributes().src, 'https://urlz.fr/cHLz')
            done()
          })
      })
    })
  })
})
function storeTest (InventoryProducts) {
  store = new Vuex.Store({
    mutations,
    state: {
      inventoryProducts: InventoryProducts
    }
  })
}
