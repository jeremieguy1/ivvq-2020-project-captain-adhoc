import Login from '@/components/Login'
import { mount, createLocalVue } from '@vue/test-utils'
import Vuelidate from 'vuelidate'
import axios from 'axios'
import moxios from 'moxios'
import sinon from 'sinon'
import chai from 'chai'

const localVue = createLocalVue()
localVue.use(Vuelidate)

describe('Login.vue', () => {
  beforeEach(() => {
    moxios.install(axios)
  })

  afterEach(() => {
    moxios.uninstall(axios)
  })

  it('Should be in error on username field when invalid', () => {
    // Given
    const wrapper = mount(Login, {
      localVue
    })
    const usernameField = wrapper.find('input#username')

    // When
    usernameField.setValue('X')

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.username.$error, true)
  })

  it('Should have no errors on username field when valid', () => {
    // Given
    const wrapper = mount(Login, {
      localVue
    })
    const usernameField = wrapper.find('input#username')

    // When
    usernameField.setValue('Valid')

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.username.$error, false)
  })

  it('Should be in error on password field when invalid', () => {
    // Given
    const wrapper = mount(Login, {
      localVue
    })
    const passwordField = wrapper.find('input#password')

    // When
    passwordField.setValue('X')

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.password.$error, true)
  })

  it('Should have no errors on password field when valid', () => {
    // Given
    const wrapper = mount(Login, {
      localVue
    })
    const passwordField = wrapper.find('input#password')

    // When
    passwordField.setValue('ValidPassword')

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.password.$error, false)
  })

  it('Should be invalid when a field is', () => {
    // Given
    const wrapper = mount(Login, {
      localVue
    })
    const loginForm = wrapper.find('form')

    // When
    loginForm.trigger('submit.prevent')
    wrapper.vm.$forceUpdate()

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.$invalid, true)
  })

  it('Should be valid when all fields are', () => {
    // Given
    const wrapper = mount(Login, {
      localVue
    })
    const loginForm = wrapper.find('form')
    wrapper.find('input#username').setValue('ValidUsername')
    wrapper.find('input#password').setValue('ValidPassword')

    // When
    loginForm.trigger('submit.prevent')
    wrapper.vm.$forceUpdate()

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.$invalid, false)
  })

  it('Should do an axios call on valid form Login', (done) => {
    // Given
    const wrapper = mount(Login, {
      localVue
    })
    const spy = sinon.spy(wrapper.vm, 'submit')
    const loginForm = wrapper.find('form')
    wrapper.find('input#username').setValue('ValidUsername')
    wrapper.find('input#password').setValue('ValidPassword')
    // When
    loginForm.trigger('submit.prevent')
    wrapper.vm.$forceUpdate()

    // Then
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 200,
        headers: {
          authorization: 'authorization'
        }
      }).then(() => {
        chai.assert.strictEqual(spy.calledOnce, true)
        done()
      })
    })
  })

  it('Should axios get catch with 403 error', (done) => {
    // Given
    const wrapper = mount(Login, {
      localVue
    })
    const spy = sinon.spy(wrapper.vm, 'submit')
    const loginForm = wrapper.find('form')
    wrapper.find('input#username').setValue('ValidUsername')
    wrapper.find('input#password').setValue('ValidPassword')

    // When
    loginForm.trigger('submit.prevent')
    wrapper.vm.$forceUpdate()

    // Then
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 403,
        headers: {
          authorization: 'authorization'
        }
      }).then(() => {
        chai.assert.strictEqual(spy.calledOnce, true)
        done()
      })
    })
  })

  it('Should axios get default catch with error code', (done) => {
    // Given
    const wrapper = mount(Login, {
      localVue
    })
    const spy = sinon.spy(wrapper.vm, 'submit')
    const loginForm = wrapper.find('form')
    wrapper.find('input#username').setValue('ValidUsername')
    wrapper.find('input#password').setValue('ValidPassword')

    // When
    loginForm.trigger('submit.prevent')
    wrapper.vm.$forceUpdate()

    // Then
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 400,
        headers: {
          authorization: 'authorization'
        }
      }).then(() => {
        chai.assert.strictEqual(spy.calledOnce, true)
        done()
      })
    })
  })
})
