import Vuex from 'vuex'
import Vue from 'vue'
import VueRouter from 'vue-router'
import { mount, createLocalVue } from '@vue/test-utils'
import { mutations, getters } from '@/store/index'
import sinon from 'sinon'
import chai from 'chai'
import LogoutModal from '@/components/LogoutModal'

Vue.use(Vuex)
Vue.use(VueRouter)
const localVue = createLocalVue()
localVue.use(Vuex)
let store

const parentComponentStub = {
  name: 'parentStub',
  methods: {
    close () {
      // Stub method
    }
  },
  template: '<div></div>'
}

describe('LogoutModal.vue', () => {
  it('Should trigger cancel method on cancel button click', () => {
    // Given
    const spy = sinon.spy(LogoutModal.methods, 'cancel')
    const wrapper = mount(LogoutModal, {
      parentComponent: parentComponentStub
    })

    // When
    wrapper.vm.cancel()

    // Then
    chai.assert.strictEqual(spy.calledOnce, true)
  })

  it('Should trigger logout method on logout button click', () => {
    // Given
    const spy = sinon.spy(LogoutModal.methods, 'logout')
    storeTest()
    const router = new VueRouter()
    const wrapper = mount(LogoutModal, {
      parentComponent: parentComponentStub,
      localVue,
      store,
      router
    })
    // When
    wrapper.vm.logout()

    // Then
    chai.assert.strictEqual(spy.calledOnce, true)
  })
})

function storeTest () {
  store = new Vuex.Store({
    mutations,
    state: {
      isLoggedStore: false
    },
    getters
  })
}
