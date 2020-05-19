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
    quantity: 1
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
    quantity: 1
  },
  {
    description_produit: 'description',
    id_produit: '1',
    image_produit: 'image_url',
    marchand: {},
    nom_produit: 'nom',
    prix_produit: 5,
    quantite_produit: 3,
    quantity: 2
  }
]
describe('Cart.vue', () => {
  beforeEach(() => {
    moxios.install(axios)
  })

  afterEach(() => {
    moxios.uninstall(axios)
  })

  it('Should call getProductsCart  at the component creation', (done) => {
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
            mount(Cart, {
              store,
              localVue
            })

            // Then
            chai.assert.strictEqual(spy.calledOnce, true)
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
      //  const spy = sinon.spy(Cart.methods, 'getProductsCart')

        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            commandsProduct: []
          }
        }).then(
          response => {
            storeTest(response.data.commandsProduct)
            const wrapper = mount(Cart, {
              store,
              localVue
            })

            chai.assert.strictEqual(spy.calledOnce, true)

            var oui = wrapper.findAll('.section.info').selector
            chai.assert.equal('.section.info', oui)
            console.log('oui')
            // Then
            console.log(store.state.cartProducts)

            chai.assert.isEmpty(store.state.cartProducts)
            console.log('oui2')

            //       console.log(wrapper.findAll('.card-header')[0].)

            //    chai.assert.include(wrapper.findAll('.section.info'), 'nhtbgrvfecdzs')
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

            console.log('paybox')
            console.log(wrapper.findAll('.section.info')[0])

            console.log(wrapper.findAll('.section.info').selector)
            var oui = wrapper.findAll('.section.info').selector
            chai.assert.equal('.section.info', oui)
            console.log('paybox 1')
            // Then
            console.log(store.state.cartProducts)

            chai.assert.isNotEmpty(store.state.cartProducts)
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

            //Then
            chai.assert.include(wrapper.findAll('.card-header-title.total').at(0).text(), '1$')
            chai.assert.include(wrapper.findAll('.card-header-title.total').at(1).text(), '10$')
            chai.assert.strictEqual(spy.calledTwice, true)

            done()
          })
      })
    })
  })

  it('Should getTotalPrixProduct calculate good total ', (done) => {

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
    //getTotalPrixProduct(..)

    //Then
    chai.assert.strictEqual(Cart.methods.getTotalPrixProduct(commandsProductResponse2[0]), 1)
    chai.assert.strictEqual(Cart.methods.getTotalPrixProduct(commandsProductResponse2[1]), 10)

    done()
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
/*
  it('Should not shrink the header on scroll if scroll is not enough on bot', () => {
    // Given
    const wrapper = mount(Products, {
      store,
      localVue
    })

    wrapper.setData({
      shrinkHeader: true
    })
    window.pageYOffset = 50

    // When
    wrapper.vm.onScroll()

    // Then
    chai.assert.strictEqual(wrapper.vm.shrinkHeader, true)
  })

  it('Should shrink the header on scroll if scroll is enough on bot', () => {
    // Given
    const wrapper = mount(Products, {
      store,
      localVue
    })

    wrapper.setData({
      shrinkHeader: true
    })
    window.pageYOffset = 60

    // When
    wrapper.vm.onScroll()

    // Then
    chai.assert.strictEqual(wrapper.vm.shrinkHeader, false)
  })

  it('Should not shrink header on scroll if scroll is already on top', () => {
    // Given
    const wrapper = mount(Products, {
      store,
      localVue
    })

    wrapper.setData({
      shrinkHeader: true
    })
    window.pageYOffset = 0

    // When
    wrapper.vm.onScroll()

    // Then
    chai.assert.strictEqual(wrapper.vm.shrinkHeader, true)
  })

  it('Should deplay the product in detail', () => {
    // Given
    const spy = sinon.spy(Products.methods, 'openDetails')
    const wrapper = mount(Products, {
      data () {
        return {
          products: productsResponse
        }
      },
      store,
      localVue
    })
    const card = wrapper.find('.card')

    // When
    card.trigger('click')

    // Then
    chai.assert.strictEqual(spy.calledOnce, true)
    spy.restore()
  })

  it('Should deplay the product in detail', () => {
    // Given
    const spy = sinon.spy(Products.methods, 'openDetails')
    const wrapper = mount(Products)
    wrapper.vm.openDetails(product)
    chai.assert.strictEqual(spy.calledOnce, true)
  })
})

function storeTest (product) {
  store = new Vuex.Store({
    mutations,
    state: {
      products: product
    },
    getters
  })
}
*/
