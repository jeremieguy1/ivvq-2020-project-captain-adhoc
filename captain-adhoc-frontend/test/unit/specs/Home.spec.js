import Home from '@/components/Home'
import { mount } from '@vue/test-utils'

import sinon from 'sinon'
import chai from 'chai'

describe('Home.vue', () => {
  it('Should display components at the beginning', () => {
    // Given
    const wrapper = mount(Home, {
    })

    // When
    chai.assert.equal(wrapper.findAll('.image.is-4by5').at(0).selector, '.image.is-4by5')
    chai.assert.equal(wrapper.findAll('.image.is-4by5').at(1).selector, '.image.is-4by5')
    chai.assert.equal(wrapper.findAll('.image.is-128x128').at(0).selector, '.image.is-128x128')
    chai.assert.equal(wrapper.findAll('.image.is-128x128').at(1).selector, '.image.is-128x128')
    chai.assert.equal(wrapper.find('.no-shadow').selector, '.no-shadow')
    chai.assert.equal(wrapper.find('.content.is-medium').selector, '.content.is-medium')
  })

  it('Should scrollFunction be called when scroll', () => {
    // Given
    // Création du DOM
    const divParente = document.createElement('div')

    const divImage4by51 = document.createElement('figure')
    divImage4by51.classList.add('image', 'is-4by5')

    const divImage4by52 = document.createElement('figure')
    divImage4by52.classList.add('image', 'is-4by5')

    const divImage128x1 = document.createElement('figure')
    divImage128x1.classList.add('image', 'is-128x128')

    const divImage128x2 = document.createElement('figure')
    divImage128x2.classList.add('image', 'is-128x128')

    const divnoShadow = document.createElement('vueper-slides')
    divnoShadow.classList.add('no-shadow')

    document.body.appendChild(divParente)

    const spy = sinon.spy(Home.methods, 'scrollFunction')
    mount(Home, {
      attachTo: divParente
    })

    // When
    window.dispatchEvent(new CustomEvent('scroll', { detail: 0 }))

    // Then
    chai.assert.strictEqual(spy.calledOnce, true)
    spy.restore()
  })

  it('Should scroll', () => {
    // Given
    const wrapper = mount(Home)

    // When
    window.pageYOffset = 55
    wrapper.vm.scrollFunction()

    // Then
    chai.assert.equal(wrapper.findAll('.image.is-4by5').at(0).selector, '.image.is-4by5')
    chai.assert.equal(wrapper.findAll('.image.is-4by5').at(1).selector, '.image.is-4by5')
    chai.assert.equal(wrapper.findAll('.image.is-128x128').at(0).selector, '.image.is-128x128')
    chai.assert.equal(wrapper.findAll('.image.is-128x128').at(1).selector, '.image.is-128x128')
    chai.assert.equal(wrapper.find('.no-shadow').selector, '.no-shadow')
    chai.assert.equal(wrapper.find('.content.is-medium').selector, '.content.is-medium')
  })

})
