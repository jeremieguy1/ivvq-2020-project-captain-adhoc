import Vue from 'vue'
import Home from '@/components/Home'

import { mount } from '@vue/test-utils'

describe('Homme.vue', () => {
  it('should have the info propertie correct contents', () => {
    const vm = new Vue(Home).$mount()
    expect(vm).to.have.property('info')
  })

  it('just for a single spec', () => {
    let wrapper = mount(Home)

    const spy = sinon.spy(Home.methods, 'navigate')
    // console.log('1 ' + spy.navigate.calledOnce)
    expect(wrapper.vm.navigate).to.be.a('function')

    wrapper.find('button.login').trigger('click')
    console.log('2 ' + spy.calledOnce)
    wrapper.vm.navigate()
    Home.methods.navigate()
    console.log('3 ' + spy.calledOnce)
    expect(spy.calledOnce)

    // Home.methods.navigate.restore()
  })
})
