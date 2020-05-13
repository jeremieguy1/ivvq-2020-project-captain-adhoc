import Navbar from '@/components/Navbar'
import { mount, createLocalVue } from '@vue/test-utils'
import VueRouter from 'vue-router'

const localVue = createLocalVue()
localVue.use(VueRouter)

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

    let wrapper = mount(Navbar, {
      attachTo: divParente,
      router
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

    let wrapper = mount(Navbar, {
      attachTo: divParente,
      router
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
})
