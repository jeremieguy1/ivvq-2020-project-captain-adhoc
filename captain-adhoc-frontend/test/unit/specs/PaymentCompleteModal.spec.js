import PaymentCompleteModal from '@/components/PaymentCompleteModal'
import { mount } from '@vue/test-utils'

import sinon from 'sinon'
import chai from 'chai'

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
    const spy = sinon.spy(PaymentCompleteModal.methods, 'backToProducts')
    const wrapper = mount(PaymentCompleteModal, {
      parentComponent: parentComponentStub
    })
    wrapper.vm.backToProducts()
    chai.assert.strictEqual(spy.calledOnce, true)
  })
})
