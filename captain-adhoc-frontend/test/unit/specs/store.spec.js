import Vuex from 'vuex'
import { mount, createLocalVue } from '@vue/test-utils'
import { mutations, getters } from '@/store/index'
import moxios from 'moxios'
import axios from 'axios'
import chai from 'chai'
import Vuelidate from 'vuelidate'
import sinon from 'sinon'
var MockAdapter = require('axios-mock-adapter')
const mock = new MockAdapter(axios)

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

const commandsProductResponse = [
  {
    description_produit: 'description',
    id_produit: '1',
    image_produit: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
    marchand: {},
    nom_produit: 'CyberboX',
    prix_produit: 1,
    quantite_produit: 1,
    quantity: 1,
    display: true
  }
]

const quantityListProducts = [
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
    display: false,
    quantity: 0
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
    display: false,
    quantity: 0
  }
]

const quantityListProductsChange = [
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
    display: false,
    quantity: 0
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
    display: false,
    quantity: 50
  }
]

const quantityProduct = {
  nom_produit: 'CyberboX',
  quantity: 50
}

const user = [{
  id: 1,
  is_admin: false,
  mot_de_passe: 'motDepasse2',
  nom: "'mister'",
  nom_utilisateur: 'Hmister',
  prenom: 'citoyen'
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
    const spy = sinon.spy(mutations, 'getData')

    // When
    getData({})

    // Then
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 200,
        response: commandsProductResponse
      }).then(() => {
        chai.assert.strictEqual(spy.calledOnce, true)
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
