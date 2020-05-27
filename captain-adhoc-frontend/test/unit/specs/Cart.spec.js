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
    productDescription: 'description',
    idProduct: '1',
    productPicture: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
    productName: 'CyberboX',
    productPrice: 1,
    productQuantity: 1,
    quantity: 1,
    display: true
  }
]

const commandsProductResponse2 = [
  {
    productDescription: 'description',
    idProduct: '2',
    productPicture: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
    productName: 'PS5',
    productPrice: 1,
    productQuantity: 1,
    quantity: 1,
    display: false
  },
  {
    productDescription: 'description1',
    idProduct: '1',
    productPicture: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
    productName: 'CyberboX',
    productPrice: 5,
    productQuantity: 3,
    quantity: 2,
    display: false
  }
]

const product =
  {
    productDescription: 'description',
    idProduct: '1',
    productPicture: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
    marchand: {},
    productName: 'nom',
    productPrice: 1,
    productQuantity: 1
  }

describe('Cart.vue', () => {
  beforeEach(() => {
    moxios.install(axios)
  })

  afterEach(() => {
    moxios.uninstall(axios)
  })

  it('Should display info if no products has chosen', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/products').then(spy)
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
      axios.get('/products').then(spy)
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
      axios.get('/products').then(spy)
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
    //     productDescription: 'description',
    //     idProduct: '2',
    //     productPicture: 'image_url',
    //     marchand: {},
    //     productName: 'PS5',
    //     productPrice: 1,
    //     productQuantity: 1,
    //     quantity: 1
    //   },
    //   {
    //     productDescription: 'description',
    //     idProduct: '1',
    //     productPicture: 'image_url',
    //     marchand: {},
    //     productName: 'nom',
    //     productPrice: 5,
    //     productQuantity: 3,
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
      axios.get('/products').then(spy)
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
    //     productDescription: 'description',
    //     idProduct: '2',
    //     productPicture: 'image_url',
    //     marchand: {},
    //     productName: 'PS5',
    //     productPrice: 1,
    //     productQuantity: 1,
    //     quantity: 1
    //   },
    //   {
    //     productDescription: 'description',
    //     idProduct: '1',
    //     productPicture: 'image_url',
    //     marchand: {},
    //     productName: 'nom',
    //     productPrice: 5,
    //     productQuantity: 3,
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
    storeTest(commandsProductResponse2)

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

  it('Should calculate good total of products for all products ', (done) => {
    // Given
    storeTest(commandsProductResponse2)

    // When
    const wrapper = mount(Cart, {
      store,
      localVue
    })

    // Then
    chai.assert.include(wrapper.findAll('.to-pay.total-cart').at(0).text(), '3 produits')
    done()
  })

  it('Should getTotalProduct calculate good total ', () => {
    // Given
    // commandsProductResponse2 = [
    //   {
    //     productDescription: 'description',
    //     idProduct: '2',
    //     productPicture: 'image_url',
    //     marchand: {},
    //     productName: 'PS5',
    //     productPrice: 1,
    //     productQuantity: 1,
    //     quantity: 1
    //   },
    //   {
    //     productDescription: 'description',
    //     idProduct: '1',
    //     productPicture: 'image_url',
    //     marchand: {},
    //     productName: 'nom',
    //     productPrice: 5,
    //     productQuantity: 3,
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

  it('Should update the quantity', () => {
    // Given
    const router = new VueRouter()
    const spy = sinon.spy(Cart.methods, 'updateQuantity')
    storeTest(commandsProductResponse2)
    const wrapper = mount(Cart, {
      store,
      localVue,
      router
    })
    wrapper.vm.updateQuantity(product)
    chai.assert.strictEqual(spy.calledOnce, true)
    spy.restore()
  })

  it('Should be unfold after a clicked when it was fold', () => {
    // Given
    storeTest(commandsProductResponse2)
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

  it('Should be fold after a clicked when it was unfold', () => {
    // Given
    storeTest(commandsProductResponse2)
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

  it('Should be in error on code field when invalid', (done) => {
    // Given
    storeTest(commandsProductResponse2)
    const wrapper = mount(Cart, {
      store,
      localVue
    })

    const codeField = wrapper.find('input#code')

    // When
    codeField.setValue('X')

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.code.$error, true)
    done()
  })

  it('Should have no errors on code field when valid', () => {
    // Given
    storeTest(commandsProductResponse2)
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

  it('Should getProductsCart and update quantity to display when quantity local < quantity get', (done) => {
    // Given
    storeTest(commandsProductResponse1)
    const wrapper = mount(Cart, {
      store,
      localVue
    })

    window.localStorage.setItem('commandsProduct', JSON.stringify(commandsProductResponse1))

    const spy = sinon.spy(wrapper.vm, 'getProductsCart')

    // When
    wrapper.vm.getProductsCart()

    // Then
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 200,
        response: {
          data: {
            productDescription: 'description1',
            idProduct: '1',
            productPicture: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
            marchand: {},
            productName: 'CyberboX',
            productPrice: 5,
            productQuantity: 3,
            quantity: 2,
            display: false
          }
        }
      }).then(() => {
        chai.assert.strictEqual(spy.calledOnce, true)
        done()
      })
    })
  })

  it('Should getProductsCart and set localstorage commandsProduct when there is no product in localstorage', (done) => {
    // Given
    storeTest(commandsProductResponse1)
    const wrapper = mount(Cart, {
      store,
      localVue
    })

    window.localStorage.setItem('commandsProduct', JSON.stringify([]))

    const spy = sinon.spy(wrapper.vm, 'getProductsCart')

    // When
    wrapper.vm.getProductsCart()

    // Then
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 200,
        response: {
          data: {
            productDescription: 'description1',
            idProduct: '1',
            productPicture: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
            marchand: {},
            productName: 'CyberboX',
            productPrice: 5,
            productQuantity: 3,
            quantity: 2,
            display: false
          }
        }
      }).then(() => {
        chai.assert.strictEqual(spy.calledOnce, true)
        done()
      })
    })
  })

  it('Should getProductsCart and don\'t  check when quantityProduct response is <= 0', (done) => {
    // Given
    storeTest(commandsProductResponse1)
    const wrapper = mount(Cart, {
      store,
      localVue
    })

    window.localStorage.setItem('commandsProduct', JSON.stringify([]))

    const spy = sinon.spy(wrapper.vm, 'getProductsCart')

    // When
    wrapper.vm.getProductsCart()

    // Then
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 200,
        response: {
          data: {
            productDescription: 'description1',
            idProduct: '1',
            productPicture: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
            marchand: {},
            productName: 'CyberboX',
            productPrice: 5,
            productQuantity: 0,
            quantity: 2,
            display: false
          }
        }
      }).then(() => {
        chai.assert.strictEqual(spy.calledOnce, true)
        done()
      })
    })
  })

  it('Should getProductsCart and don\'t check when product quantity in localstorage response is <= 0', (done) => {
    // Given
    storeTest(commandsProductResponse1)
    const wrapper = mount(Cart, {
      store,
      localVue
    })

    window.localStorage.setItem('commandsProduct', JSON.stringify([{
      productDescription: 'description',
      idProduct: '1',
      productPicture: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
      productName: 'CyberboX',
      productPrice: 1,
      productQuantity: 1,
      quantity: 0,
      display: true
    }]))

    const spy = sinon.spy(wrapper.vm, 'getProductsCart')

    // When
    wrapper.vm.getProductsCart()

    // Then
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 200,
        response: {
          data: {
            productDescription: 'description1',
            idProduct: '1',
            productPicture: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
            marchand: {},
            productName: 'CyberboX',
            productPrice: 5,
            productQuantity: 3,
            quantity: 2,
            display: false
          }
        }
      }).then(() => {
        chai.assert.strictEqual(spy.calledOnce, true)
        done()
      })
    })
  })

  it('Should getProductsCart and don\'t check when product name localstorage is not the same than a product name response', (done) => {
    // Given
    storeTest(commandsProductResponse1)
    const wrapper = mount(Cart, {
      store,
      localVue
    })

    window.localStorage.setItem('commandsProduct', JSON.stringify(commandsProductResponse1))

    const spy = sinon.spy(wrapper.vm, 'getProductsCart')

    // When
    wrapper.vm.getProductsCart()

    // Then
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 200,
        response: {
          data: {
            productDescription: 'description1',
            idProduct: '1',
            productPicture: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
            marchand: {},
            productName: 'MadBox',
            productPrice: 5,
            productQuantity: 3,
            quantity: 2,
            display: false
          }
        }
      }).then(() => {
        chai.assert.strictEqual(spy.calledOnce, true)
        done()
      })
    })
  })

  it('Should getProductsCart and update quantity to display when quantity local > quantity get', (done) => {
    // Given
    storeTest(commandsProductResponse1)
    const wrapper = mount(Cart, {
      store,
      localVue
    })

    window.localStorage.setItem('commandsProduct', JSON.stringify(commandsProductResponse1))

    const spy = sinon.spy(wrapper.vm, 'getProductsCart')

    // When
    wrapper.vm.getProductsCart()

    // Then
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 200,
        response: {
          data: {
            productDescription: 'description1',
            idProduct: '1',
            productPicture: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
            marchand: {},
            productName: 'CyberboX',
            productPrice: 5,
            productQuantity: 1,
            quantity: 2,
            display: false
          }
        }
      }).then(() => {
        chai.assert.strictEqual(spy.calledOnce, true)
        done()
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
