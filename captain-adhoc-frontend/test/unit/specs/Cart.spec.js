import Vuex from 'vuex'
import Cart from '@/components/Cart/Cart'
import { mount, createLocalVue } from '@vue/test-utils'
import { mutations } from '@/store/index'
import moxios from 'moxios'
import axios from 'axios'
import sinon from 'sinon'
import chai from 'chai'
import Vuelidate from 'vuelidate'
import VueRouter from 'vue-router'

const localVue = createLocalVue()
localVue.use(Vuelidate)
localVue.use(VueRouter)

let store
const commandsProductResponse1 = [
  {
    description_produit: 'description',
    id_produit: '1',
    image_produit: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
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
    image_produit: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
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
    image_produit: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
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
      axios.get('/produits').then(spy)
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
      axios.get('/produits').then(spy)
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
      axios.get('/produits').then(spy)
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
      axios.get('/produits').then(spy)
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
      axios.get('/produits').then(spy)
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
            chai.assert.include(wrapper.findAll('.to-pay.total-cart').at(0).text(), '11€')
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
      axios.get('/produits').then(spy)
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
            chai.assert.include(wrapper.findAll('.card-header-title.total').at(0).text(), '1 produit')
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
      axios.get('/produits').then(spy)
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

            // Then
            chai.assert.include(wrapper.findAll('.to-pay.total-cart').at(0).text(), '3 produits')
            // twice because getTotalProductis called during if
            chai.assert.strictEqual(spy.calledTwice, true)
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

  it('Should update the quantity', () => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/produits').then(spy)
      moxios.wait(() => {
        const spy = sinon.spy(Cart.methods, 'updateQuantity')
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

            const card = wrapper.find('.box-shadow.updateQuantity')

            // When
            card.trigger('click')

            // Then
            chai.assert.strictEqual(spy.calledOnce, true)
            spy.restore()
          })
      })
    })
  })

  it('Should go to pay', () => {
    // Given
    const router = new VueRouter()
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/produits').then(spy)
      moxios.wait(() => {
        const spy = sinon.spy(Cart.methods, 'payCart')
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
              router,
              localVue
            })

            const card = wrapper.find('.to-pay.box-shadow')

            // When
            card.trigger('click')

            // Then
            chai.assert.strictEqual(spy.calledOnce, true)
            spy.restore()
          })
      })
    })
  })

  it('Should update the quantity', () => {
    // Given
    const router = new VueRouter()
    const spy = sinon.spy(Cart.methods, 'payCart')
    storeTest(commandsProductResponse2)
    const wrapper = mount(Cart, {
      store,
      localVue,
      router
    })
    wrapper.vm.payCart()
    spy.restore()
  })

  it('Should be unfold after a clicked when it was fold', () => {
    // Given
    moxios.withMock(function () {
      moxios.wait(() => {
        axios.get('/produits').then()
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandsProduct: commandsProductResponse2
          }
        }).then(
          response => {
            storeTest(response.data.commandsProduct)
            const wrapper = mount(Cart, {
              store,
              localVue
            })

            // When
            wrapper.findAll('header').at(0).trigger('click')

            // Then
            chai.assert.strictEqual(store.state.cartProducts[0].display, true)
            chai.assert.strictEqual(store.state.cartProducts[1].display, false)
          })
      })
    })
  })

  it('Should be fold after a clicked when it was unfold', () => {
    // Given
    moxios.withMock(function () {
      moxios.wait(() => {
        axios.get('/produits').then()
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandsProduct: commandsProductResponse2
          }
        }).then(
          response => {
            storeTest(response.data.commandsProduct)
            const wrapper = mount(Cart, {
              store,
              localVue
            })

            // When
            wrapper.findAll('header').at(1).trigger('click')

            // Then
            chai.assert.strictEqual(store.state.cartProducts[0].display, true)
            chai.assert.strictEqual(store.state.cartProducts[1].display, true)
          })
      })
    })
  })

  it('Should be in error on code field when invalid', () => {
    // Given
    moxios.withMock(function () {
      moxios.wait(() => {
        axios.get('/produits').then()
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandsProduct: commandsProductResponse2
          }
        }).then(
          response => {
            storeTest(response.data.commandsProduct)
            const wrapper = mount(Cart, {
              store,
              localVue
            })

            const codeField = wrapper.find('input#code')

            // When
            codeField.setValue('X')

            // Then
            chai.assert.strictEqual(wrapper.vm.$v.code.$error, true)
          })
      })
    })
  })

  it('Should have no errors on code field when valid', () => {
    // Given
    moxios.withMock(function () {
      moxios.wait(() => {
        axios.get('/produits').then()
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandsProduct: commandsProductResponse2
          }
        }).then(
          response => {
            storeTest(response.data.commandsProduct)
            const wrapper = mount(Cart, {
              store,
              localVue
            })
            const codeField = wrapper.find('input#code')

            // When
            codeField.setValue('CODE2018')

            // Then
            chai.assert.strictEqual(wrapper.vm.$v.code.$error, false)
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
