import Login from '@/components/Login'
import { mount, createLocalVue } from '@vue/test-utils'
import Vuelidate from 'vuelidate'
import axios from 'axios'
import moxios from 'moxios'
import sinon from 'sinon'

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
    expect(true, wrapper.vm.$v.username.$error)
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
    expect(false, wrapper.vm.$v.username.$error)
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
    expect(true, wrapper.vm.$v.password.$error)
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
    expect(false, wrapper.vm.$v.password.$error)
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
    expect(true, wrapper.vm.$v.$invalid)
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
    expect(false, wrapper.vm.$v.$invalid)
  })

  it('Should do an axios call on valid form', (done) => {
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
      expect(true, spy.calledOnce)
      done()
    })
  })
})
