import Vuex from 'vuex'
import { mount, createLocalVue } from '@vue/test-utils'
import { mutations, getters } from '@/store/index'
import moxios from 'moxios'
import axios from 'axios'
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

const commandsProductResponse = [
  {
    productDescription: 'description',
    idProduct: '1',
    productPicture: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
    marchand: {},
    productName: 'CyberboX',
    productPrice: 1,
    productQuantity: 1,
    quantity: 1
  }
]

const quantityListProducts = [
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
    display: false,
    quantity: 0
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
    display: false,
    quantity: 0
  }
]

const quantityListProductsChange = [
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
    display: false,
    quantity: 0
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
    display: false,
    quantity: 50
  }
]

const quantityProduct = {
  productName: 'CyberboX',
  quantity: 50
}

const quantityProduct0 = {
  productName: 'CyberboX',
  quantity: '0'
}

const user = [{
  idMember: 1,
  isAdmin: false,
  password: 'motDepasse2',
  lastName: "'mister'",
  userName: 'Hmister',
  firstName: 'citoyen'
}]

const stubComponent = {
  name: 'parentStub',
  template: '<div></div>'
}

describe('Store.js', () => {
  beforeEach(() => {
    moxios.install(axios)
    mock.restore()
  })

  afterEach(() => {
    moxios.uninstall(axios)
    mock.reset()
  })

  it('Should update products', () => {
    // Given
    storeTest(listProductsResponse)
    const wrapper = mount(stubComponent, {
      store,
      localVue
    })

    // When
    wrapper.vm.$store.commit('storeProducts', listProductsResponse)
    // Then
    chai.assert.equal(wrapper.vm.$store.state.products, listProductsResponse)
  })

  it('Should update cartProducts', () => {
    // Given
    storeTest(listProductsResponse)
    const wrapper = mount(stubComponent, {
      store,
      localVue
    })

    // When
    wrapper.vm.$store.commit('cartProducts', listProductsResponse)
    // Then
    chai.assert.equal(wrapper.vm.$store.state.cartProducts, listProductsResponse)
  })

  it('Should update userStore', () => {
    // Given
    storeTest(listProductsResponse, user)
    const wrapper = mount(stubComponent, {
      store,
      localVue
    })

    // When
    wrapper.vm.$store.commit('setCurrentUser', user)

    // Then
    chai.assert.equal(wrapper.vm.$store.state.userStore, user)
  })

  it('Should update isMercant', () => {
    // Given
    storeTest(listProductsResponse, user)
    const wrapper = mount(stubComponent, {
      store,
      localVue
    })

    // When
    wrapper.vm.$store.commit('setMercant', user)

    // Then
    chai.assert.equal(wrapper.vm.$store.state.isMercant, true)
  })

  it('Should update quantity products', () => {
    // Given
    storeTest(quantityListProducts, user)
    const wrapper = mount(stubComponent, {
      store,
      localVue
    })

    // When
    wrapper.vm.$store.commit('updateQuantity', quantityProduct)

    // Then
    chai.assert.equal(wrapper.vm.$store.state.cartProducts[1].quantity, quantityListProductsChange[1].quantity)
  })

  it('Should splice products with 0 quantity ', () => {
    // Given
    storeTest(quantityListProducts, user)
    const wrapper = mount(stubComponent, {
      store,
      localVue
    })

    // When
    wrapper.vm.$store.commit('updateQuantity', quantityProduct0)

    // Then
    chai.assert.equal(wrapper.vm.$store.state.cartProducts.length, 1)
  })

  it('Should get allProducts', () => {
    // Given
    storeTest(listProductsResponse, user)
    const wrapper = mount(stubComponent, {
      store,
      localVue
    })

    // When
    const getProducts = wrapper.vm.$store.getters.allProducts

    // Then
    chai.assert.equal(getProducts, listProductsResponse)
  })

  it('Should get isMercant', () => {
    // Given
    storeTest(listProductsResponse, user)
    const wrapper = mount(stubComponent, {
      store,
      localVue
    })

    // When
    const getisMercant = wrapper.vm.$store.getters.isMercant

    // Then
    chai.assert.equal(getisMercant, false)
  })

  it('Should getProductsCart and update quantity to display when quantity local > quantity get', (done) => {
    // Given
    storeTest(commandsProductResponse, user)
    const { getData } = mutations

    // When
    getData({})

    // Then
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 200,
        response: commandsProductResponse
      }).finally(() => {
        done()
      })
    })
  })
})

function storeTest (products, user) {
  store = new Vuex.Store({
    mutations,
    state: {
      inventoryProducts: products,
      cartProducts: products,
      userStore: user,
      products: products,
      isMercant: false,
      produits: products
    },
    getters
  })
}
