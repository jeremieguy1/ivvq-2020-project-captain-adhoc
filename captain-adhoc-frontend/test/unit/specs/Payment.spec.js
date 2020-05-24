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
    // window.localStorage = new LocalStorageMock()

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

})
