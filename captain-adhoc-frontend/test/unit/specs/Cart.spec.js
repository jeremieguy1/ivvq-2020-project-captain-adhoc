import Vuex from 'vuex'
import Cart from '@/components/Cart'
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
const commandsProductResponse1 = [
  {
    description_produit: 'description',
    id_produit: '1',
    image_produit: 'image_url',
    marchand: {},
    nom_produit: 'CyberboX',
    prix_produit: 1,
    quantite_produit: 1,
    quantity: 1,
    display: false
  }
]

const commandsProductResponse2 = [
  {
    description_produit: 'description',
    id_produit: '2',
    image_produit: 'image_url',
    marchand: {},
    nom_produit: 'PS5',
    prix_produit: 1,
    quantite_produit: 1,
    quantity: 1,
    display: false
  },
  {
    description_produit: 'description',
    id_produit: '1',
    image_produit: 'image_url',
    marchand: {},
    nom_produit: 'CyberboX',
    prix_produit: 5,
    quantite_produit: 3,
    quantity: 2,
    display: false
  }
]
describe('Cart.vue', () => {
  beforeEach(() => {
    moxios.install(axios)
  })

  afterEach(() => {
    moxios.uninstall(axios)
  })

  it('Should call getProductsCart at the component creation', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/commandes').then(spy)
      moxios.wait(() => {
        const spy = sinon.spy(Cart.methods, 'getProductsCart')

        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandsProduct: commandsProductResponse1
          }
        }).then(
          response => {
            storeTest(response.data.commandsProduct)

            // When
            mount(Cart, {
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

  it('Should display info if no products has chosen', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/commandes').then(spy)
      moxios.wait(() => {
        const spy = sinon.spy(Cart.methods, 'displayContentCart')

        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandsProduct: []
          }
        }).then(
          response => {
            storeTest(response.data.commandsProduct)

            // When
            mount(Cart, {
              store,
              localVue
            })

            // Then
            chai.assert.isEmpty(store.state.cartProducts)
            chai.assert.strictEqual(spy.calledOnce, false)
            spy.restore()
            done()
          })
      })
    })
  })
  it('Should display selected products and pay box', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/commandes').then(spy)
      moxios.wait(() => {
        const spy = sinon.spy(Cart.methods, 'displayContentCart')

        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandsProduct: commandsProductResponse1
          }
        }).then(
          response => {
            storeTest(response.data.commandsProduct)

            // When
            const wrapper = mount(Cart, {
              store,
              localVue
            })
            wrapper.findAll('header').at(0).trigger('click')

            // Then
            chai.assert.isNotEmpty(store.state.cartProducts)
            chai.assert.strictEqual(spy.calledOnce, true)
            spy.restore()
            done()
          })
      })
    })
  })

  it('Should calculate good total for a product ', (done) => {
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/commandes').then(spy)
      moxios.wait(() => {
        const spy = sinon.spy(Cart.methods, 'getTotalPrixProduct')
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandsProduct: commandsProductResponse2
          }
        }).then(
          response => {
            storeTest(response.data.commandsProduct)

            // When
            const wrapper = mount(Cart, {
              store,
              localVue
            })

            // Then
            chai.assert.include(wrapper.findAll('.card-header-title.total').at(0).text(), '1€')
            chai.assert.include(wrapper.findAll('.card-header-title.total').at(1).text(), '10€')
            chai.assert.strictEqual(spy.calledTwice, true)
            spy.restore()

            done()
          })
      })
    })
  })

  it('Should getTotalPrixProduct calculate good total ', () => {
    // Given
    // commandsProductResponse2 = [
    //   {
    //     description_produit: 'description',
    //     id_produit: '2',
    //     image_produit: 'image_url',
    //     marchand: {},
    //     nom_produit: 'PS5',
    //     prix_produit: 1,
    //     quantite_produit: 1,
    //     quantity: 1
    //   },
    //   {
    //     description_produit: 'description',
    //     id_produit: '1',
    //     image_produit: 'image_url',
    //     marchand: {},
    //     nom_produit: 'nom',
    //     prix_produit: 5,
    //     quantite_produit: 3,
    //     quantity: 2
    //   }
    // ]

    // When
    // getTotalPrixProduct(..)

    // Then
    chai.assert.strictEqual(Cart.methods.getTotalPrixProduct(commandsProductResponse2[0]), 1)
    chai.assert.strictEqual(Cart.methods.getTotalPrixProduct(commandsProductResponse2[1]), 10)
  })

  it('Should calculate good total for all products ', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/commandes').then(spy)
      moxios.wait(() => {
        const spy = sinon.spy(Cart.methods, 'getTotalCart')
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandsProduct: commandsProductResponse2
          }
        }).then(
          response => {
            storeTest(response.data.commandsProduct)

            // When
            const wrapper = mount(Cart, {
              store,
              localVue
            })
            chai.assert.strictEqual(spy.calledOnce, true)

            // Then
            chai.assert.include(wrapper.findAll('.to_pay.total_cart').at(0).text(), '11€')
            chai.assert.strictEqual(spy.calledOnce, true)
            spy.restore()
            done()
          })
      })
    })
  })

  it('Should getTotalCart calculate good total ', () => {
  // Given
  // commandsProductResponse2 = [
  //   {
  //     description_produit: 'description',
  //     id_produit: '2',
  //     image_produit: 'image_url',
  //     marchand: {},
  //     nom_produit: 'PS5',
  //     prix_produit: 1,
  //     quantite_produit: 1,
  //     quantity: 1
  //   },
  //   {
  //     description_produit: 'description',
  //     id_produit: '1',
  //     image_produit: 'image_url',
  //     marchand: {},
  //     nom_produit: 'nom',
  //     prix_produit: 5,
  //     quantite_produit: 3,
  //     quantity: 2
  //   }
  // ]

  // When
  // getTotalPrixProduct(..)

  // Then
    chai.assert.strictEqual(Cart.methods.getTotalCart(commandsProductResponse2), 11)
  })

  it('Should calculate good total for a product ', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/commandes').then(spy)
      moxios.wait(() => {
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandsProduct: commandsProductResponse2
          }
        }).then(
          response => {
            storeTest(response.data.commandsProduct)

            // When
            const wrapper = mount(Cart, {
              store,
              localVue
            })

            // Then
            chai.assert.include(wrapper.findAll('.card-header-title.total').at(0).text(), '1 produits')
            chai.assert.include(wrapper.findAll('.card-header-title.total').at(1).text(), '2 produits')

            done()
          })
      })
    })
  })

  it('Should calculate good total of products for all products ', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/commandes').then(spy)
      moxios.wait(() => {
        const spy = sinon.spy(Cart.methods, 'getTotalProduct')
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandsProduct: commandsProductResponse2
          }
        }).then(
          response => {
            storeTest(response.data.commandsProduct)

            // When
            const wrapper = mount(Cart, {
              store,
              localVue
            })
            chai.assert.strictEqual(spy.calledOnce, true)

            // Then
            console.log(wrapper.findAll('.to_pay.total_cart').at(0).text())
            chai.assert.include(wrapper.findAll('.to_pay.total_cart').at(0).text(), '3 produits')
            chai.assert.strictEqual(spy.calledOnce, true)
            spy.restore()

            done()
          })
      })
    })
  })

  it('Should getTotalProduct calculate good total ', () => {
    // Given
    // commandsProductResponse2 = [
    //   {
    //     description_produit: 'description',
    //     id_produit: '2',
    //     image_produit: 'image_url',
    //     marchand: {},
    //     nom_produit: 'PS5',
    //     prix_produit: 1,
    //     quantite_produit: 1,
    //     quantity: 1
    //   },
    //   {
    //     description_produit: 'description',
    //     id_produit: '1',
    //     image_produit: 'image_url',
    //     marchand: {},
    //     nom_produit: 'nom',
    //     prix_produit: 5,
    //     quantite_produit: 3,
    //     quantity: 2
    //   }
    // ]

    // When
    // getTotalPrixProduct(..)

    // Then
    chai.assert.strictEqual(Cart.methods.getTotalProduct(commandsProductResponse2), 3)
  })

  it('Should getData called at the component creation', () => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/commandes').then(spy)
      moxios.wait(() => {
        const spy = sinon.spy(Cart.methods, 'getData')
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandsProduct: commandsProductResponse2
          }
        }).then(
          response => {
            storeTest(response.data.commandsProduct)

            // When
            mount(Cart, {
              store,
              localVue
            })

            // Then
            chai.assert.strictEqual(spy.calledOnce, true)
            spy.restore()
          })
      })
    })
  })
})
function storeTest (cartProducts) {
  store = new Vuex.Store({
    mutations,
    state: {
      cartProducts: cartProducts
    }
  })
}
