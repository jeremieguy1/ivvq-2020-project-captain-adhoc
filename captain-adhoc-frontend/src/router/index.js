import Vue from 'vue'
import Router from 'vue-router'

import Home from '@/components/Home'
import Login from '@/components/Login'
import SignUp from '@/components/SignUp'
import Cart from '@/components/Cart'
import History from '@/components/History'
import Products from '@/components/Products'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      redirect: {
        name: 'Home'
      }
    },
    {
      path: '/home',
      name: 'Home',
      component: Home
    },
    {
      path: '/log-in',
      name: 'Login',
      component: Login
    },
    {
      path: '/sign-up',
      name: 'SignUp',
      component: SignUp
    },
    {
      path: '/cart',
      name: 'Cart',
      component: Cart
    },
    {
      path: '/purchases-history',
      name: 'History',
      component: History
    },
    {
      path: '/products',
      name: 'Products',
      component: Products
    }
  ]
})

router.beforeEach((to, from, next) => {
  if ((to.name !== 'Login' && to.name !== 'SignUp' && to.name !== 'Home') && !localStorage.getItem('Authorization')) {
    next({ name: 'Login' })
  } else {
    next()
  }
})

export default router
