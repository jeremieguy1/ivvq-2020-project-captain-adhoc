import Vuex from 'vuex'
import Inventory from '@/components/Inventory'
import { mount, createLocalVue } from '@vue/test-utils'
import { mutations } from '@/store/index'
import moxios from 'moxios'
import axios from 'axios'
import sinon from 'sinon'
import chai from 'chai'
import Vuelidate from 'vuelidate'
var MockAdapter = require('axios-mock-adapter')
const mock = new MockAdapter(axios)

const localVue = createLocalVue()
localVue.use(Vuelidate)

let store
const listProductsResponse = [
  {
    idProduct: 2,
    productQuantity: 15,
    productName: 'PS5',
    productDescription: 'Encore une playstation de folie \\o/',
    productPicture: 'https://urlz.fr/cHLz',
    productPrice: 1,
    marchand: {
      id_marchand: 1,
      identifiant_marchand: 'marchand1'
    },
    display: false
  },
  {
    idProduct: 3,
    productQuantity: 16,
    productName: 'CyberboX',
    productDescription: "Non comptant d'avoir les meilleures voitures au MONDE, Tesla propose la meilleure console de jeu grand public !",
    productPicture: 'https://urlz.fr/cHLH',
    productPrice: 100000,
    marchand: {
      id_marchand: 1,
      identifiant_marchand: 'marchand1'
    },
    display: false
  },
  {
    idProduct: 4,
    productQuantity: 2,
    productName: 'Mad box',
    productDescription: 'Cette console va révolutionner le du la de esport !',
    productPicture: 'https://urlz.fr/cHJp',
    productPrice: 666,
    marchand: null,
    display: false
  },
  {
    idProduct: 5,
    productQuantity: 100,
    productName: 'New retro +',
    productDescription: 'Elle fera tourner les jeux dernières générations tels que tetris et même Donkey kong 64 ! Et tout àa pour seulement 1399,99€',
    productPicture: 'https://urlz.fr/cHJz',
    productPrice: 10,
    marchand: null,
    display: false
  },
  {
    idProduct: 6,
    productQuantity: 5,
    productName: 'Xbox Serie X',
    productDescription: "C'est partiiiii pour la console pc !",
    productPicture: 'https://urlz.fr/cHLM',
    productPrice: 200,
    marchand: null,
    display: false
  }
]

describe('Inventory.vue', () => {
  beforeEach(() => {
    moxios.install(axios)
    mock.restore()
  })

  afterEach(() => {
    moxios.uninstall(axios)
    mock.reset()
  })

  it('Should call getProducts at the component creation', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/products').then(spy)
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
      axios.get('/products').then(spy)
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
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/products').then(spy)
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
      axios.get('/products').then(spy)
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

  it('Should display image', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/products').then(spy)
      moxios.wait(() => {
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            listProducts: [
              {
                idProduct: 2,
                productQuantity: 15,
                productName: 'PS5',
                productDescription: 'Encore une playstation de folie \\o/',
                productPicture: 'https://urlz.fr/cHLz',
                productPrice: 1,
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

            // Then
            chai.assert.equal(wrapper.findAll('img').at(0).attributes().src, 'https://urlz.fr/cHLz')
            done()
          })
      })
    })
  })

  it('Should update inventory by click', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/products').then(spy)
      moxios.wait(() => {
        const spy = sinon.spy(Inventory.methods, 'updateInventory')

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

            wrapper.find('.button.has-text-centered').trigger('click')

            // Then
            chai.assert.strictEqual(spy.calledOnce, true)
            spy.restore()
            done()
          })
      })
    })
  })

  it('Should update inventory by call updateInventory', (done) => {
    // Given
    const spy = sinon.spy(Inventory.methods, 'updateInventory')
    storeTest(listProductsResponse)
    const wrapper = mount(Inventory, {
      store,
      localVue
    })

    wrapper.vm.getProducts()
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 200,
        response: {
          data: listProductsResponse
        }
      }).then(() => {
        // Then
        // When
        wrapper.vm.updateInventory()
        moxios.wait(() => {
          let request = moxios.requests.mostRecent()
          request.respondWith({
            status: 200,
            response: {
              data: listProductsResponse
            }
          }).then(() => {
            // Then
            chai.assert.strictEqual(spy.calledOnce, true)
            spy.restore()
            done()
          })
        })
      })
    })
  })

  it('Should axios be catch with error code', (done) => {
    // Given
    const spy = sinon.spy(Inventory.methods, 'updateInventory')
    storeTest(listProductsResponse)
    const wrapper = mount(Inventory, {
      store,
      localVue
    })

    wrapper.vm.getProducts()
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 200,
        response: {
          data: listProductsResponse
        }
      }).then(() => {
        // Then
        // When
        wrapper.vm.updateInventory()
        moxios.wait(() => {
          let request = moxios.requests.mostRecent()
          request.respondWith({
            status: 400,
            response: {
              data: ''
            }
          }).then(() => {
            // Then
            chai.assert.strictEqual(spy.calledOnce, true)
            spy.restore()
            done()
          })
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
