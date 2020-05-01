import Vue from 'vue'
import Home from '@/components/Home'
import axios from 'axios'
import moxios from 'moxios'
import chai from 'chai'

describe('Homme.vue', () => {
  it('should render correct contents', () => {
    const Constructor = Vue.extend(Home)
    const vm = new Constructor().$mount()
    chai.expect(vm).to.have.property('info')
  })

  it('just for a single spec', function (done) {
    // const Constructor = Vue.extend(Home)
    // const vm = new Constructor().$mount()
    // const fakeThis = {
    //   info: null
    // }
    // const getData = vm.methods.getData.bind(fakeThis)
    moxios.withMock(function () {
      let onFulfilled = sinon.spy()
      // getData.then(onFulfilled)
      axios.get('/test').then(onFulfilled)

      moxios.wait(function () {
        let request = moxios.requests.mostRecent()
        request.respondWith({
          status: 200,
          response: [
            ['6']
          ]
        }).then(function () {
          chai.expect(onFulfilled.called).to.equal(true)
          done()
        })
      })
    })
  })
})
