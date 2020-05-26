import VueRouter from 'vue-router'
import Vue from 'vue'
import router from '@/router/index'

Vue.use(VueRouter)

describe('Router', () => {
  it('should component be Login when no authentification', () => {
    router.push('/inventory')
    expect(router.history.current.path).to.eql('/log-in')
  })

  it('should component be inventory when authentification', () => {
    localStorage.setItem('Authorization', true)
    router.push('/inventory')
    expect(router.history.current.path).to.eql('/inventory')
    localStorage.removeItem('Authorization')
  })

  it('should component be Home', () => {
    router.push('/Home')
    expect(router.history.current.path).to.eql('/Home')
  })
})
