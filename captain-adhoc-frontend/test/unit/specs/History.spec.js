import History from '@/components/History'
import { mount, createLocalVue } from "@vue/test-utils"
// mutations.spec.js
import { expect } from 'chai'
import { mutations } from '@/components/History'
import Vuex from "vuex"
import * as jest from 'mocha';

// destructure assign `mutations`


const localVue = createLocalVue()
localVue.use(Vuex)

const store = new Vuex.Store({
  mutations,
  state: {
    commandes: [
      {
        id_commande: 4,
        date_commande: '21/20/2020',
        code: 'code',
        commandeProduitsList: [
          {
            id_commandeProduit: 7,
            produit: {
              id_produit: 2,
              quantite_produit: 15,
              nom_produit: 'produit1',
              description_produit: 'description1',
              image_produit: 'image1',
              prix_produit: 1,
              marchand: {
                id_marchand: 1,
                identifiant_marchand: 'marchand1'
              }
            },
            quantite_commande_produit: 4
          }
        ],
        acheteur: null
      },
      {
        id_commande: 5,
        date_commande: '22/20/2020',
        code: 'code',
        commandeProduitsList: [
          {
            id_commandeProduit: 8,
            produit: {
              id_produit: 2,
              quantite_produit: 15,
              nom_produit: 'produit1',
              description_produit: 'description1',
              image_produit: 'image1',
              prix_produit: 1,
              marchand: {
                id_marchand: 1,
                identifiant_marchand: 'marchand1'
              }
            },
            quantite_commande_produit: 0
          },
          {
            id_commandeProduit: 9,
            produit: {
              id_produit: 3,
              quantite_produit: 16,
              nom_produit: 'produit2',
              description_produit: 'description2',
              image_produit: 'image2',
              prix_produit: 2,
              marchand: {
                id_marchand: 1,
                identifiant_marchand: 'marchand1'
              }
            },
            quantite_commande_produit: 0
          }
        ],
        acheteur: null
      },
      {
        id_commande: 6,
        date_commande: '23/20/2020',
        code: 'code',
        commandeProduitsList: [
          {
            id_commandeProduit: 10,
            produit: {
              id_produit: 3,
              quantite_produit: 16,
              nom_produit: 'produit2',
              description_produit: 'description2',
              image_produit: 'image2',
              prix_produit: 2,
              marchand: {
                id_marchand: 1,
                identifiant_marchand: 'marchand1'
              }
            },
            quantite_commande_produit: 0
          }
        ],
        acheteur: null
      }
    ]
  }
})

describe('History.vue', () => {
  it('Should calculate good total ', () => {
    // Given
    const wrapper = mount(History, {
      store,
      localVue
    })
    // When
    /*
      Commande 1:
            quantite_produit: 15,
            prix_produit: 1,
            total = 15 * 1 = 15
     */
    //Then
    expect(wrapper.find(".card-header-title.total").text()).equal("15$")

    // When
    /*
      Commande 2:
            quantite_produit: 15,
            prix_produit: 1,
            total1 = 15 * 1 = 15
            quantite_produit: 16,
            prix_produit: 2,
            total = 16 * 2 = 32
            total = total1 + total2 = 15 + 32 = 47
     */
    //Then
    expect(wrapper.findAll(".card-header-title.total").at(1).text()).equal("47$")

    // When
    /*
        Commande 3:
           quantite_produit: 16,
           prix_produit: 2,
           total = 16 * 2 = 32
    */
    //Then
    expect(wrapper.findAll(".card-header-title.total").at(2).text()).equal("32$")
  })

  it('Should display simple data ', () => {

    const wrapper = mount(History, {
      store,
      localVue
    })
    /*
      Commande 1:
        date_commande: '21/20/2020',
     */
    expect(wrapper.findAll(".card-header-title.date").at(0).text()).equal("21/20/2020")

    /*
      Commande 2:
        date_commande: '22/20/2020',
     */
    expect(wrapper.findAll(".card-header-title.date").at(1).text()).equal("22/20/2020")

    /*
      Commande 3:
        date_commande: '23/20/2020',
    */
    expect(wrapper.findAll(".card-header-title.date").at(2).text()).equal("23/20/2020")

  })
})
