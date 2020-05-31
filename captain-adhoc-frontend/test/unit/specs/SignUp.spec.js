import SignUp from '@/components/SignUp'
import { mount, createLocalVue } from '@vue/test-utils'
import Vuelidate from 'vuelidate'
import moxios from 'moxios'
import axios from 'axios'
import sinon from 'sinon'
import chai from 'chai'
import VueRouter from 'vue-router'

const localVue = createLocalVue()
localVue.use(Vuelidate)
localVue.use(VueRouter)

describe('SignUp.vue', () => {
  beforeEach(() => {
    moxios.install()
  })

  afterEach(() => {
    moxios.uninstall()
  })

  it('Should be in error on firstname field when invalid', () => {
    // Given
    const wrapper = mount(SignUp, {
      localVue
    })
    const firstnameField = wrapper.find('input#firstname')

    // When
    firstnameField.setValue('X')

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.firstname.$error, true)
  })

  it('Should have no errors on firstname field when valid', () => {
    // Given
    const wrapper = mount(SignUp, {
      localVue
    })
    const firstnameField = wrapper.find('input#firstname')

    // When
    firstnameField.setValue('ValidFirstnameé')

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.firstname.$error, false)
  })

  it('Should be in error on lastname field when invalid', () => {
    // Given
    const wrapper = mount(SignUp, {
      localVue
    })
    const lastnameField = wrapper.find('input#lastname')

    // When
    lastnameField.setValue('X')

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.lastname.$error, true)
  })

  it('Should have no errors on lastname field when valid', () => {
    // Given
    const wrapper = mount(SignUp, {
      localVue
    })
    const lastnameField = wrapper.find('input#lastname')

    // When
    lastnameField.setValue('ValidLastnameé')

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.lastname.$error, false)
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
    wrapper.find('input#firstname').setValue('ValidFirstname')
    wrapper.find('input#lastname').setValue('ValidLastname')
    wrapper.find('input#username').setValue('ValidUsername')
    wrapper.find('input#password').setValue('ValidPassword')
    wrapper.find('input#repeatPassword').setValue('ValidPassword')

    // When
    signUpForm.trigger('submit.prevent')
    wrapper.vm.$forceUpdate()

    // Then
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 200,
        response: {
          data: ''
        }
      }).then(() => {
        // Then
        chai.assert.strictEqual(wrapper.vm.$v.$invalid, false)
        done()
      })
    })
  })

  it('Should do an axios call on valid form', (done) => {
    // Given
    const wrapper = mount(SignUp, {
      localVue
    })
    const signUpForm = wrapper.find('form')
    wrapper.find('input#firstname').setValue('ValidFirstname')
    wrapper.find('input#lastname').setValue('ValidLastname')
    wrapper.find('input#username').setValue('ValidUsername')
    wrapper.find('input#password').setValue('ValidPassword')
    wrapper.find('input#repeatPassword').setValue('ValidPassword')

    // When
    signUpForm.trigger('submit.prevent')
    wrapper.vm.$forceUpdate()

    // When
    moxios.withMock(function () {
      let spy = sinon.spy()
      axios.post('/members').then(spy)
      // Then
      moxios.wait(() => {
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: {
            data: ''
          }
        }).then((response) => {
          // Then
          chai.assert.strictEqual(response.status, 200)
          done()
        })
      })
    })
  })

  it('Should axios get catch with 403 error', (done) => {
    // Given
    const wrapper = mount(SignUp, {
      localVue
    })
    const spy = sinon.spy(wrapper.vm, 'submit')
    const signUpForm = wrapper.find('form')
    wrapper.find('input#firstname').setValue('ValidFirstname')
    wrapper.find('input#lastname').setValue('ValidLastname')
    wrapper.find('input#username').setValue('ValidUsername')
    wrapper.find('input#password').setValue('ValidPassword')
    wrapper.find('input#repeatPassword').setValue('ValidPassword')

    // When
    signUpForm.trigger('submit.prevent')
    wrapper.vm.$forceUpdate()

    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 403,
        response: {
          data: ''
        }
      }).then(() => {
        chai.assert.strictEqual(spy.calledOnce, true)
        spy.restore()
        done()
      })
    })
  })

  it('Should axios get catch with 409 error', (done) => {
    // Given
    const wrapper = mount(SignUp, {
      localVue
    })
    const spy = sinon.spy(wrapper.vm, 'submit')
    const signUpForm = wrapper.find('form')
    wrapper.find('input#firstname').setValue('ValidFirstname')
    wrapper.find('input#lastname').setValue('ValidLastname')
    wrapper.find('input#username').setValue('ValidUsername')
    wrapper.find('input#password').setValue('ValidPassword')
    wrapper.find('input#repeatPassword').setValue('ValidPassword')

    // When
    signUpForm.trigger('submit.prevent')
    wrapper.vm.$forceUpdate()

    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 409,
        response: {
          data: ''
        }
      }).then(() => {
        chai.assert.strictEqual(spy.calledOnce, true)
        spy.restore()
        done()
      })
    })
  })

  it('Should axios get default catch with error code', (done) => {
    // Given
    const wrapper = mount(SignUp, {
      localVue
    })
    const spy = sinon.spy(wrapper.vm, 'submit')
    const signUpForm = wrapper.find('form')
    wrapper.find('input#firstname').setValue('ValidFirstname')
    wrapper.find('input#lastname').setValue('ValidLastname')
    wrapper.find('input#username').setValue('ValidUsername')
    wrapper.find('input#password').setValue('ValidPassword')
    wrapper.find('input#repeatPassword').setValue('ValidPassword')

    // When
    signUpForm.trigger('submit.prevent')
    wrapper.vm.$forceUpdate()

    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 400,
        response: {
          data: ''
        }
      }).then(() => {
        chai.assert.strictEqual(spy.calledOnce, true)
        spy.restore()
        done()
      })
    })
  })
})
