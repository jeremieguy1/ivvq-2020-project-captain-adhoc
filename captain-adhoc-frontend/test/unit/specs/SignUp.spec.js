import SignUp from '@/components/SignUp'
import { mount, createLocalVue } from '@vue/test-utils'
import Vuelidate from 'vuelidate'
import moxios from 'moxios'
import axios from 'axios'
import sinon from 'sinon'
import chai from 'chai'

const localVue = createLocalVue()
localVue.use(Vuelidate)

describe('SignUp.vue', () => {
  beforeEach(() => {
    moxios.install(axios)
  })

  afterEach(() => {
    moxios.uninstall(axios)
  })

  it('Should be in error on username field when invalid', () => {
    // Given
    const wrapper = mount(SignUp, {
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
    const wrapper = mount(SignUp, {
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
    const wrapper = mount(SignUp, {
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
    const wrapper = mount(SignUp, {
      localVue
    })
    const passwordField = wrapper.find('input#password')

    // When
    passwordField.setValue('ValidPassword')

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.password.$error, false)
  })

  it('Should be in error on repeatPassword field when different from passaword one', () => {
    // Given
    const wrapper = mount(SignUp, {
      localVue
    })
    const passwordField = wrapper.find('input#password')
    const repeatPasswordField = wrapper.find('input#repeatPassword')
    // When
    passwordField.setValue('password')
    repeatPasswordField.setValue('notTheSame')

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.repeatPassword.$error, true)
  })

  it('Should have no errors on repeatPassword field when valid', () => {
    // Given
    const wrapper = mount(SignUp, {
      localVue
    })
    const passwordField = wrapper.find('input#password')
    const repeatPasswordField = wrapper.find('input#repeatPassword')

    // When
    passwordField.setValue('password')
    repeatPasswordField.setValue('password')

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.repeatPassword.$error, false)
  })

  it('Should be invalid when a field is', () => {
    // Given
    const wrapper = mount(SignUp, {
      localVue
    })
    const signUpForm = wrapper.find('form')

    // When
    signUpForm.trigger('submit.prevent')
    wrapper.vm.$forceUpdate()

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.$invalid, true)
  })

  it('Should be valid when all fields are', (done) => {
    // Given
    const wrapper = mount(SignUp, {
      localVue
    })
    const signUpForm = wrapper.find('form')
    wrapper.find('input#username').setValue('ValidUsername')
    wrapper.find('input#password').setValue('ValidPassword')
    wrapper.find('input#repeatPassword').setValue('ValidPassword')

    // When
    signUpForm.trigger('submit.prevent')
    wrapper.vm.$forceUpdate()

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.$invalid, false)
    done()
  })

  it('Should do an axios call on valid form', (done) => {
    // Given
    const wrapper = mount(SignUp, {
      localVue
    })
    const spy = sinon.spy(wrapper.vm, 'submit')
    const signUpForm = wrapper.find('form')
    wrapper.find('input#username').setValue('ValidUsername')
    wrapper.find('input#password').setValue('ValidPassword')
    wrapper.find('input#repeatPassword').setValue('ValidPassword')

    // When
    signUpForm.trigger('submit.prevent')
    wrapper.vm.$forceUpdate()

    // Then
    moxios.wait(() => {
      chai.assert.strictEqual(spy.calledOnce, true)
      done()
    })
  })
})
