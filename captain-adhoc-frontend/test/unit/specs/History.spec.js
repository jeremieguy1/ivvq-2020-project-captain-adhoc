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
    idPurchase: 4,
    display: false,
    purchaseDate: '2020-05-21T13:12:09.918+0000',
    code: 'code',
    purchaseProductList: [
      {
        idPurchaseProduct: 7,
        product: {
          idProduct: 2,
          productQuantity: 25,
          productName: 'produit1',
          productDescription: 'description1',
          productPicture: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
          productPrice: 1,
          marchand: {
            id_marchand: 1,
            identifiant_marchand: 'marchand1'
          }
        },
        purchaseProductQuantity: 15
      }
    ],
    member: null
  },
  {
    idPurchase: 5,
    display: true,
    purchaseDate: '2020-05-22T13:12:09.918+0000',
    code: 'code',
    purchaseProductList: [
      {
        idPurchaseProduct: 8,
        product: {
          idProduct: 2,
          productQuantity: 20,
          productName: 'produit1',
          productDescription: 'description1',
          productPicture: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
          productPrice: 1,
          marchand: {
            id_marchand: 1,
            identifiant_marchand: 'marchand1'
          }
        },
        purchaseProductQuantity: 15
      },
      {
        idPurchaseProduct: 9,
        product: {
          idProduct: 3,
          productQuantity: 16,
          productName: 'produit2',
          productDescription: 'description2',
          productPicture: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
          productPrice: 2,
          marchand: {
            id_marchand: 1,
            identifiant_marchand: 'marchand1'
          }
        },
        purchaseProductQuantity: 16
      }
    ],
    member: null
  },
  {
    idPurchase: 6,
    display: true,
    purchaseDate: '2020-05-23T13:12:09.918+0000',
    code: '',
    purchaseProductList: [
      {
        idPurchaseProduct: 10,
        product: {
          idProduct: 3,
          productQuantity: 20,
          productName: 'produit2',
          productDescription: 'description2',
          productPicture: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
          productPrice: 2,
          marchand: {
            id_marchand: 1,
            identifiant_marchand: 'marchand1'
          }
        },
        purchaseProductQuantity: 16
      }
    ],
    member: null
  }
]

describe('History.vue', () => {
  beforeEach(() => {
    moxios.install(axios)
  })

  afterEach(() => {
    moxios.uninstall(axios)
  })

  it('Should calculate good total ', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/commandes').then(spy)
      moxios.wait(() => {
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
              purchase 1:
                    purchaseProductQuantity: 15,
                    productPrice: 1,
                    total = 15 * 1 = 15
             */
            // Then
            chai.assert.strictEqual(wrapper.findAll('.card-header-title.total').at(0).text(), '15€')

            // When
            /*
              purchase 2:
                    purchaseProductQuantity: 15,
                    productPrice: 1,
                    total1 = 15 * 1 = 15
                    purchaseProductQuantity: 16,
                    productPrice: 2,
                    total = 16 * 2 = 32
                    total = total1 + total2 = 15 + 32 = 47
             */
            // Then
            chai.assert.strictEqual(wrapper.findAll('.card-header-title.total').at(1).text(), '47€')

            // When
            /*
                purchase 3:
                   purchaseProductQuantity: 16,
                   productPrice: 2,
                   total = 16 * 2 = 32
            */
            // Then
            chai.assert.strictEqual(wrapper.findAll('.card-header-title.total').at(2).text(), '32€')
            done()
          })
      })
    })
  })

  it('Should getTotalPrix calculate good total ', () => {
    // Given
    const spy = sinon.spy(History.methods, 'getTotalPrix')
    let purchase = {
      purchaseProductList: [
        {
          product: {
            productPrice: 5
          },
          purchaseProductQuantity: 6
        },
        {
          product: {
            productPrice: 4
          },
          purchaseProductQuantity: 36
        }
      ]
    }

    // When
    let total = History.methods.getTotalPrix(purchase)

    // Then
    chai.assert.strictEqual(total, 174)
    chai.assert.strictEqual(spy.calledOnce, true)
  })

  it('Should display simple data ', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/commandes').then(spy)
      moxios.wait(() => {
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
              purchase 1:
                purchaseDate: '2020-05-21T13:12:09.918+0000',
             */
            chai.assert.strictEqual(wrapper.findAll('.card-header-title.date').at(0).text(), '2020-05-21 13:12')

            /*
              purchase 2:
                purchaseDate: '2020-05-22T13:12:09.918+0000',
             */
            chai.assert.strictEqual(wrapper.findAll('.card-header-title.date').at(1).text(), '2020-05-22 13:12')

            /*
              purchase 3:
                purchaseDate: '2020-05-23T13:12:09.918+0000',
            */
            chai.assert.strictEqual(wrapper.findAll('.card-header-title.date').at(2).text(), '2020-05-23 13:12')
            done()
          })
      })
    })
  })

  it('Should call store action displayContent when button is clicked', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/commandes').then(spy)
      moxios.wait(() => {
        const spy = sinon.spy(History.methods, 'displayContent')

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

  it('Should be unfold after a clicked when it was fold', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/commandes').then(spy)
      moxios.wait(() => {
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
            chai.assert.strictEqual(store.state.commandes[1].display, false)
            chai.assert.strictEqual(store.state.commandes[2].display, true)
            done()
          })
      })
    })
  })

  it('Should be fold after a clicked when it was unfold', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/commandes').then(spy)
      moxios.wait(() => {
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
            chai.assert.strictEqual(store.state.commandes[1].display, true)
            chai.assert.strictEqual(store.state.commandes[2].display, true)
            done()
          })
      })
    })
  })

  it('Should show the code when there is a code', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/commandes').then(spy)
      moxios.wait(() => {
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
            // for purchase 1 code: 'code'
            wrapper.findAll('header').at(0).trigger('click')

            // Then
            chai.assert.strictEqual(wrapper.findAll('.codeToDisplay').at(0).text(), 'Code code')
            done()
          })
      })
    })
  })

  it('Should not show the code when there is no code', (done) => {
    // Given
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.get('/commandes').then(spy)
      moxios.wait(() => {
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

  it('Should give (n/a) from null date', () => {
    // Given
    submit(respond)
    const wrapper = mount(History, {
      store,
      localVue
    })
    // When
    const dateReturn = wrapper.vm.date('')
    wrapper.vm.$forceUpdate()

    // Then
    chai.assert.strictEqual(dateReturn, '(n/a)')
  })

  it('Should give a date from conform date', () => {
    // Given
    submit(respond)
    const wrapper = mount(History, {
      store,
      localVue
    })
    // When
    const dateReturn = wrapper.vm.date('2020-10-09T13:12:20.918+0000')
    wrapper.vm.$forceUpdate()

    // Then
    chai.assert.strictEqual(dateReturn, '2020-10-09 13:12')
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
