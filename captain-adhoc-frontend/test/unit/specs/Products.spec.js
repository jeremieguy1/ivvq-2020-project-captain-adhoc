import Vuex from 'vuex'
import Vue from 'vue'
import Products from '@/components/Products'
import { mount, createLocalVue } from '@vue/test-utils'
import { mutations, getters } from '@/store/index'
import moxios from 'moxios'
import axios from 'axios'
import sinon from 'sinon'
import chai from 'chai'
import Buefy from 'buefy'

Vue.use(Vuex)
Vue.use(Buefy)

const localVue = createLocalVue()
localVue.use(Vuex)
localVue.use(Buefy)
let store
const productsResponse = [
  {
    description_produit: 'description',
    id_produit: '1',
    image_produit: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
    marchand: {},
    nom_produit: 'nom',
    prix_produit: 1,
    quantite_produit: 1
  }
]

const product =
  {
    description_produit: 'description',
    id_produit: '1',
    image_produit: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
    marchand: {},
    nom_produit: 'nom',
    prix_produit: 1,
    quantite_produit: 1
  }

const productDifferent =
  {
    description_produit: 'description',
    id_produit: '3',
    image_produit: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
    marchand: {},
    nom_produit: 'OtherName',
    prix_produit: 1,
    quantite_produit: 1
  }

describe('Products.vue', () => {
  beforeEach(() => {
    moxios.install(axios)
  })

  afterEach(() => {
    moxios.uninstall(axios)
  })

  it('Should be in error on username field when invalid', (done) => {
    // Given
    moxios.withMock(function () {
      const spy = sinon.spy()
      axios.get('/products').then(spy)
      moxios.wait(() => {
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            products: productsResponse
          }
        }).then(
          response => {
            storeTest(response.data.products)

            // When
            mount(Products, {
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

  it('Should not shrink header on scroll if scroll is near on top', () => {
    // Given
    const wrapper = mount(Products, {
      store,
      localVue
    })

    wrapper.setData({
      shrinkHeader: true
    })
    window.pageYOffset = 9

    // When
    wrapper.vm.onScroll()

    // Then
    chai.assert.strictEqual(wrapper.vm.shrinkHeader, true)
  })

  it('Should not shrink header on scroll if scroll is on top', () => {
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
    expect(true, wrapper.vm.shrinkHeader)
  })

  it('Should add the product to cart', () => {
    // Given
    const spy = sinon.spy(Products.methods, 'addToCart')
    const wrapper = mount(Products)
    wrapper.vm.addToCart(product)
    chai.assert.strictEqual(spy.calledOnce, true)
    spy.restore()
  })

  it('Should add the product not find to cart', () => {
    // Given
    const spy = sinon.spy(Products.methods, 'addToCart')
    const wrapper = mount(Products)
    wrapper.vm.addToCart(productDifferent)
    chai.assert.strictEqual(spy.calledOnce, true)
    spy.restore()
  })

  it('Should display the product in detail method', () => {
    // Given
    const wrapper = mount(Products, {
      localVue
    })
    const spy = sinon.spy(wrapper.vm, 'openDetailsModal')
    wrapper.vm.openDetailsModal(product)
    chai.assert.strictEqual(spy.calledOnce, true)
  })

  it('Should not detect scroll', () => {
    // Given
    const spy = sinon.spy(Products.methods, 'onScroll')
    const wrapper = mount(Products, {localVue})

    // When
    wrapper.vm.$destroy()
    window.pageYOffset = 60

    // Then
    chai.assert.strictEqual(spy.called, false)
    spy.restore()
  })

  it('Should axios get products', (done) => {
    // Given
    storeTest(productsResponse)
    const wrapper = mount(Products, {
      store,
      localVue
    })
    const spy = sinon.spy(wrapper.vm, 'getProducts')

    // When
    wrapper.vm.getProducts()

    // Then
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 200,
        response: {
          data: productsResponse
        }
      }).then(() => {
        chai.assert.strictEqual(spy.calledOnce, true)
        done()
      })
    })
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
