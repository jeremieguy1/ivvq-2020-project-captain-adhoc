import PaymentCompleteModal from '@/components/PaymentCompleteModal'
import { mount, createLocalVue } from '@vue/test-utils'
import VueRouter from 'vue-router'
import sinon from 'sinon'
import chai from 'chai'

import CircularCountDownTimer from 'vue-circular-count-down-timer'

const localVue = createLocalVue()
localVue.use(VueRouter)
localVue.use(CircularCountDownTimer)

const parentComponentStub = {
  name: 'parentStub',
  methods: {
    close () {
      // Stub method
    }
  },
  template: '<div></div>'
}
describe('PaymentCompleteModal.vue', () => {
  it('Should call backToProducts method of PaymentCompleteModal', () => {
    const router = new VueRouter()
    const spy = sinon.spy(PaymentCompleteModal.methods, 'backToProducts')
    const wrapper = mount(PaymentCompleteModal, {
      parentComponent: parentComponentStub,
      localVue,
      router
    })
    wrapper.vm.backToProducts()
    chai.assert.strictEqual(spy.calledOnce, true)
  })
})
