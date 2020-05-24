import Payment from '@/components/Payment'
import { mount, createLocalVue } from '@vue/test-utils'
import Vuelidate from 'vuelidate'
import moxios from 'moxios'
import axios from 'axios'
import sinon from 'sinon'
import chai from 'chai'

const localVue = createLocalVue()
localVue.use(Vuelidate)

describe('Payment.vue', () => {
  beforeEach(() => {
    moxios.install(axios)
    window.localStorage.setItem('commandToPay', JSON.stringify({
      code: 'CODE2020',
      products: [
        {
          id_produit: 1,
          nom_produit: 'PS5',
          quantity: 5
        }
      ],
      totalPrice: 5,
      totalProducts: 5
    }
    ))
  })

  afterEach(() => {
    moxios.uninstall(axios)
    localStorage.removeItem('commandToPay')
  })

  it('Should be in error on credit-card field when invalid', () => {
    // Given
    const wrapper = mount(Payment, {
      localVue
    })

    const numberCartField = wrapper.find('input#credit-card')
    // When
    numberCartField.setValue('1')

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.numberCart.$error, true)
  })

  it('Should have no errors on credit-card field when valid', () => {
    // Given
    const wrapper = mount(Payment, {
      localVue
    })
    const numberCartField = wrapper.find('input#credit-card')

    // When
    numberCartField.setValue('4984421209470251')

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.numberCart.$error, false)
  })

  it('Should validPayment return false with a wrong card number', () => {
    // Given
    const wrapper = mount(Payment, {
      localVue
    })
    const spy = sinon.spy(wrapper.vm, 'validPayment')

    // When
    const valid = wrapper.vm.validPayment('4984421209470250')

    // Then
    chai.assert.strictEqual(spy.calledOnce, true)
    chai.assert.strictEqual(valid, false)
  })

  it('Should validPayment return true with a valid card number', () => {
    // Given
    const wrapper = mount(Payment, {
      localVue
    })
    const spy = sinon.spy(wrapper.vm, 'validPayment')

    // When
    const valid = wrapper.vm.validPayment('4984421209470251')

    // Then
    chai.assert.strictEqual(spy.calledOnce, true)
    chai.assert.strictEqual(valid, true)
  })

  it('Should be in error on cvc field when invalid', () => {
    // Given
    const wrapper = mount(Payment, {
      localVue
    })

    const numberCartField = wrapper.find('input#cvc')
    // When
    numberCartField.setValue('1')

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.cvc.$error, true)
  })

  it('Should have no errors on cvc field when valid', () => {
    // Given
    const wrapper = mount(Payment, {
      localVue
    })
    const numberCartField = wrapper.find('input#cvc')

    // When
    numberCartField.setValue('689')

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.cvc.$error, false)
  })

  it('Should be invalid when a field is', () => {
    // Given
    const wrapper = mount(Payment, {
      localVue
    })
    const paymentSection = wrapper.find('.section.to-pay')

    // When
    paymentSection.trigger('click')
    wrapper.vm.$forceUpdate()

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.$invalid, true)
  })

  it('Should be valid when all fields are', (done) => {
    // Given
    const wrapper = mount(Payment, {
      localVue
    })
    const paymentSection = wrapper.find('.section.to-pay')
    wrapper.find('input#credit-card').setValue('4984421209470251')
    wrapper.find('input#cvc').setValue('323')

    // When
    paymentSection.trigger('click')
    wrapper.vm.$forceUpdate()

    // Then
    chai.assert.strictEqual(wrapper.vm.$v.$invalid, false)
    done()
  })

  it('Should do an axios call on valid form', (done) => {
    // Given
    const wrapper = mount(Payment, {
      localVue
    })
    const spy = sinon.spy(wrapper.vm, 'submitPayment')
    const paymentSection = wrapper.find('.button.to-pay')
    wrapper.find('input#credit-card').setValue('4984421209470251')
    wrapper.find('input#cvc').setValue('323')
    wrapper.setData({
      year: 2020,
      month: 'Janvier'
    })

    // When
    paymentSection.trigger('click')
    wrapper.vm.$forceUpdate()

    // Then
    moxios.wait(() => {
      chai.assert.strictEqual(spy.calledOnce, true)
      done()
    })
  })

  it('Should not do an axios call on invalid expiration date form', (done) => {
    // Given
    const wrapper = mount(Payment, {
      localVue
    })
    const spy = sinon.spy(wrapper.vm, 'submitPayment')
    const paymentSection = wrapper.find('.button.to-pay')
    wrapper.find('input#credit-card').setValue('4984421209470251')
    wrapper.find('input#cvc').setValue('323')
    wrapper.setData({
      year: null,
      month: null
    })

    // When
    paymentSection.trigger('click')
    wrapper.vm.$forceUpdate()

    // Then
    moxios.wait(() => {
      chai.assert.strictEqual(spy.calledOnce, true)
      done()
    })
  })

  it('Should not do an axios call on invalid number card and expiration date form', (done) => {
    // Given
    const wrapper = mount(Payment, {
      localVue
    })
    const spy = sinon.spy(wrapper.vm, 'submitPayment')
    const paymentSection = wrapper.find('.button.to-pay')
    wrapper.find('input#credit-card').setValue('498')
    wrapper.find('input#cvc').setValue('323')
    wrapper.setData({
      year: null,
      month: null
    })

    // When
    paymentSection.trigger('click')
    wrapper.vm.$forceUpdate()

    // Then
    moxios.wait(() => {
      chai.assert.strictEqual(spy.calledOnce, true)
      done()
    })
  })
})
