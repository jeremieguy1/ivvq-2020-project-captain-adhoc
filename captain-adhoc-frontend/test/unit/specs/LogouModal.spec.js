import LogoutModal from '@/components/LogoutModal'
import { mount } from '@vue/test-utils'

import sinon from 'sinon'
import chai from 'chai'

describe('LogoutModal.vue', () => {
  it('Should trigger cancel method on cancel button click', () => {
    const wrapper = mount(LogoutModal)
    const spy = sinon.spy(LogoutModal.methods, 'cancel')
    wrapper.find('button.is-success').trigger('click')
    chai.assert.strictEqual(spy.calledOnce, false)
  })

  it('Should trigger logout method on cancel button click', () => {
    const wrapper = mount(LogoutModal)
    const spy = sinon.spy(LogoutModal.methods, 'logout')
    wrapper.find('button.is-danger').trigger('click')
    chai.assert.strictEqual(spy.calledOnce, false)
  })
})
