import ProductDetailModal from '@/components/ProductDetailModal'
import { mount } from '@vue/test-utils'

import sinon from 'sinon'
import chai from 'chai'

const productToAdd =  {
  productDescription: 'description',
  idProduct: '1',
  productPicture: 'https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg',
  marchand: {},
  productName: 'nom',
  productPrice: 1,
  productQuantity: 1
}

const parentComponentStub = {
  name: 'parentStub',
  methods: {
    close () {
      // Stub method
    }
  },
  template: '<div></div>'
}
describe('ProductDetailModal.vue', () => {
  it('Should call addToCart method of ProductDetailModal', () => {
    const spy = sinon.spy(ProductDetailModal.methods, 'cancel')
    const wrapper = mount(ProductDetailModal, {
      propsData: {
        product: productToAdd
      },
      parentComponent: parentComponentStub
    })
    wrapper.vm.cancel()
    chai.assert.strictEqual(spy.calledOnce, true)
  })

  it('Should call cancel method of ProductDetailModal', () => {
    const spy = sinon.spy(ProductDetailModal.methods, 'addToCart')
    const wrapper = mount(ProductDetailModal, {
      propsData: {
        product: productToAdd
      },
      parentComponent: parentComponentStub
    })
    wrapper.vm.addToCart(productToAdd)
    chai.assert.strictEqual(spy.calledOnce, true)
  })
})
