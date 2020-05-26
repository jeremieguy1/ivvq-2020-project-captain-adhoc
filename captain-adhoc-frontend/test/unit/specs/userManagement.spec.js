import UserManagment from '@/components/userManagment'
import moxios from 'moxios'

const user = [{
  id: 1,
  is_admin: false,
  mot_de_passe: 'motDepasse2',
  nom: "'mister'",
  nom_utilisateur: 'Hmister',
  prenom: 'citoyen'
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
