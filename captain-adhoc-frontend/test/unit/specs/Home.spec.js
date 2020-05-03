import Home from '@/components/Home'
import { mount } from '@vue/test-utils'

describe('Homme.vue', () => {
  it("Should have the 'info' property as 'null' by default", () => {
    let wrapper = mount(Home)
    expect(wrapper.vm).to.have.property('info')

    expect(null, wrapper.vm.info)
  })

  it("Should call navigate method on click 'Login'", () => {
    let navigate = sinon.spy()
    let wrapper = mount(Home,
      {
        methods: {
          navigate
        }
      })

    expect(wrapper.vm.navigate).to.be.a('function')

    expect(false, navigate.calledOnce)

    wrapper.find('button.login').trigger('click')
    expect(true, navigate.calledOnce, 'Called once')
  })
})
