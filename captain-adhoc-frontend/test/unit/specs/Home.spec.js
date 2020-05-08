import Home from '@/components/Home'
import { mount } from '@vue/test-utils'

describe('Homme.vue', () => {
  it("Should call navigate method on click 'Login'", () => {
    let navigateToLoginSpy = sinon.spy()
    let wrapper = mount(Home)

    navigateToLoginSpy.bind(Home, 'navigateToLogin')

    expect(wrapper.vm.navigateToLogin).to.be.a('function')

    expect(false, navigateToLoginSpy.calledOnce)

    wrapper.find('button.login').trigger('click')
    expect(true, navigateToLoginSpy.calledOnce, 'Called once')
  })
})
