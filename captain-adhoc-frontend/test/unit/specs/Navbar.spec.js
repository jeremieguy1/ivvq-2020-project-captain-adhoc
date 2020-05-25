import Navbar from '@/components/Navbar'
import { mount, createLocalVue } from '@vue/test-utils'
import VueRouter from 'vue-router'
import { mutations, getters } from '@/store/index'
import Vuex from 'vuex'
import Vue from 'vue'
import sinon from 'sinon'
import chai from 'chai'

Vue.use(Vuex)

const localVue = createLocalVue()
localVue.use(VueRouter)
localVue.use(Vuex)
let store

const user = [{
  id: 1,
  is_admin: false,
  mot_de_passe: 'motDepasse2',
  nom: "'mister'",
  nom_utilisateur: 'Hmister',
  prenom: 'citoyen'
}]

const userModify = [{
  id: 2,
  is_admin: true,
  mot_de_passe: 'motDepasse1',
  nom: "'mister'",
  nom_utilisateur: 'Hmister',
  prenom: 'citoyen'
}]

describe('Navbar.vue', () => {
  it("Should set attributes on click 'Burger'", () => {
    const router = new VueRouter()
    const spy = sinon.spy()

    const divParente = document.createElement('div')

    // Div burger
    const divBurger = document.createElement('div')
    divBurger.classList.add('burger')
    divBurger.setAttribute('dataset', 'navbar-menu')

    // Div Navbar-menu
    const divTarget = document.createElement('div')
    divTarget.setAttribute('id', 'navbar-menu')

    divParente.appendChild(divBurger)
    divParente.appendChild(divTarget)

    document.body.appendChild(divParente)

    storeTest(true)
    let wrapper = mount(Navbar, {
      attachTo: divParente,
      localVue,
      router,
      store
    })

    spy.bind(Navbar, 'burgerClick')

    const burger = wrapper.find('.burger')
    burger.trigger('click')

    expect(true, spy.calledOnce)
  })

  it('Should toggle the properties on burgerClick', () => {
    // Given
    // HTML element to add
    const divParente = document.createElement('div')

    // Div burger
    const divBurger = document.createElement('div')
    divBurger.classList.add('burger')
    divBurger.setAttribute('dataset', 'navbar-menu')

    // Div Navbar-menu
    const divTarget = document.createElement('div')
    divTarget.setAttribute('id', 'navbar-menu')

    // Div buttons (child of menu)
    const divButtons = document.createElement('div')
    divButtons.classList.add('buttons')

    divTarget.appendChild(divButtons)

    // Div navbar
    const divNavbar = document.createElement('div')
    divNavbar.classList.add('navbar')

    divParente.appendChild(divBurger)
    divParente.appendChild(divTarget)
    divParente.appendChild(divNavbar)

    document.body.appendChild(divParente)

    const router = new VueRouter()

    storeTest(true)
    let wrapper = mount(Navbar, {
      attachTo: divParente,
      localVue,
      router,
      store
    })

    const burger = document.querySelector('.burger')
    const navMenu = document.querySelector('#navbar-menu')
    const buttons = navMenu.querySelector('.buttons')
    const navbar = document.querySelector('.navbar')
    // When
    wrapper.vm.burgerClick()

    // Then
    expect(true, burger.classList.contains('is-active'))
    expect(true, navMenu.classList.contains('is-active'))
    expect(true, buttons.classList.contains('animated'))
    expect(true, buttons.classList.contains('slideInLeft'))
    expect(true, navbar.classList.contains('is-mobile'))
  })

  it('Should no watch anymore', () => {
    // Given
    const router = new VueRouter()

    const divParente = document.createElement('div')

    // Div burger
    const divBurger = document.createElement('div')
    divBurger.classList.add('burger')
    divBurger.setAttribute('dataset', 'navbar-menu')

    // Div Navbar-menu
    const divTarget = document.createElement('div')
    divTarget.setAttribute('id', 'navbar-menu')

    divParente.appendChild(divBurger)
    divParente.appendChild(divTarget)

    document.body.appendChild(divParente)

    storeTest(true)
    let wrapper = mount(Navbar, {
      attachTo: divParente,
      localVue,
      router,
      store
    })
    // When
    wrapper.vm.$destroy()
  })

  it('Should call forceRerender and add 1 to navKey', () => {
    // Given
    const router = new VueRouter()

    const divParente = document.createElement('div')

    // Div burger
    const divBurger = document.createElement('div')
    divBurger.classList.add('burger')
    divBurger.setAttribute('dataset', 'navbar-menu')

    // Div Navbar-menu
    const divTarget = document.createElement('div')
    divTarget.setAttribute('id', 'navbar-menu')

    divParente.appendChild(divBurger)
    divParente.appendChild(divTarget)

    document.body.appendChild(divParente)

    storeTest(true)
    let wrapper = mount(Navbar, {
      attachTo: divParente,
      localVue,
      router,
      store
    })
    const spy = sinon.spy(wrapper.vm, 'forceRerender')

    // When
    wrapper.vm.forceRerender()

    // Then
    chai.assert.strictEqual(spy.calledOnce, true)
    chai.assert.strictEqual(wrapper.vm.$data.navKey, 1)
  })

  it('Should call openLogoutModal ', () => {
    // Given
    const router = new VueRouter()

    const divParente = document.createElement('div')

    // Div burger
    const divBurger = document.createElement('div')
    divBurger.classList.add('burger')
    divBurger.setAttribute('dataset', 'navbar-menu')

    // Div Navbar-menu
    const divTarget = document.createElement('div')
    divTarget.setAttribute('id', 'navbar-menu')

    divParente.appendChild(divBurger)
    divParente.appendChild(divTarget)

    document.body.appendChild(divParente)

    storeTest(true)
    let wrapper = mount(Navbar, {
      attachTo: divParente,
      localVue,
      router,
      store
    })
    const spy = sinon.spy(wrapper.vm, 'openLogoutModal')

    // When
    wrapper.vm.openLogoutModal()

    // Then
    chai.assert.strictEqual(spy.calledOnce, true)
  })

  it('Should update userStore isLoggedStore', () => {
    // Given
    const router = new VueRouter()

    const divParente = document.createElement('div')

    // Div burger
    const divBurger = document.createElement('div')
    divBurger.classList.add('burger')
    divBurger.setAttribute('dataset', 'navbar-menu')

    // Div Navbar-menu
    const divTarget = document.createElement('div')
    divTarget.setAttribute('id', 'navbar-menu')

    divParente.appendChild(divBurger)
    divParente.appendChild(divTarget)

    document.body.appendChild(divParente)

    storeTestUserStore(true, user)
    let wrapper = mount(Navbar, {
      attachTo: divParente,
      localVue,
      router,
      store
    })

    // When
    wrapper.vm.$store.state.userStore = userModify
    wrapper.vm.$store.state.isLoggedStore = false

    // Then
    chai.assert.strictEqual(wrapper.vm.$store.getters.isLoggedStore, false)
    chai.assert.strictEqual(wrapper.vm.$store.getters.userStore, userModify)
  })
})

function storeTest (isLoggedStore) {
  store = new Vuex.Store({
    mutations,
    state: {
      isLoggedStore: isLoggedStore
    },
    getters
  })
}
function storeTestUserStore (isLoggedStore, userStore) {
  store = new Vuex.Store({
    mutations,
    state: {
      isLoggedStore: isLoggedStore,
      userStore: userStore
    },
    getters
  })
}
