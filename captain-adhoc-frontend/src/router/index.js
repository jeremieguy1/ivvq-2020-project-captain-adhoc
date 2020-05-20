import Vue from 'vue'
import Router from 'vue-router'

import Home from '@/components/Home'
import Login from '@/components/Login'
import SignUp from '@/components/SignUp'
import Cart from '@/components/Cart/Cart.vue'
import History from '@/components/History'
import Logout from '@/components/Logout'
import Inventory from '@/components/Inventory'
import Products from '@/components/Products'

Vue.use(Router)

export default new Router({
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
      path: '/log-out',
      name: 'Logout',
      component: Logout
    },
    {
      path: '/products',
      name: 'Products',
      component: Products
    },
    {
      path: '/inventory',
      name: 'Inventory',
      component: Inventory
    }
  ]
})
