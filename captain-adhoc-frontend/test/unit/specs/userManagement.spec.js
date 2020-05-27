import UserManagment from '@/components/userManagment'
import moxios from 'moxios'

const user = [{
  idMember: 1,
  isAdmin: false,
  password: 'motDepasse2',
  lastName: "'mister'",
  userName: 'Hmister',
  firstName: 'citoyen'
}]

describe('UserManagment.js', () => {
  beforeEach(() => {
    moxios.install()
  })

  afterEach(() => {
    moxios.uninstall()
  })

  it('Should axios get default catch with error code', (done) => {
    // Given

    // When
    UserManagment.getUser()

    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 200,
        response: {
          data: user
        }
      }).then(() => {
        done()
      })
    })
  })
})
