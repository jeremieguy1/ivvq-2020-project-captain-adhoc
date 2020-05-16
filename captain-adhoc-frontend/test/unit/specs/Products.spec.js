import Vuex from 'vuex'
import Vue from 'vue'
import Products from '@/components/Products'
import { mount, createLocalVue } from '@vue/test-utils'
import { mutations, getters } from '@/store/index'
import moxios from 'moxios'
import axios from 'axios'
import sinon from 'sinon'
import chai from 'chai'

Vue.use(Vuex)

const localVue = createLocalVue()
localVue.use(Vuex)
let store
const productsResponse = [
  {
    description_produit: 'description',
    id_produit: '1',
    image_produit: 'image_url',
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
    image_produit: 'image_url',
    marchand: {},
    nom_produit: 'nom',
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

  it('Should be in error on username field when invalid', () => {
    // Given
    moxios.withMock(function () {
      moxios.wait(() => {
        const spy = sinon.spy(Products.methods, 'getProducts')
        axios.get('/produits').then()
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
