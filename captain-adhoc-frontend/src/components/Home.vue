<template >
<div v-if="authenticated">
  <div id="header" class="hero is-primary" >
    <div class="hero-body">
      <div class="columns" style="position:relative">
        <div class="column ">
          <figure class="image is-4by5">
            <img src="../assets/rickH.png" />
          </figure>
        </div>
        <div class="column is-half">
          <div class="level-item ">
            <h1 class="title is-1">Captain Escroc Market</h1>
          </div>
          <vueper-slides class="no-shadow" fade :touchable="false" autoplay arrows-inside bullets-outside transition-speed="250" >
            <template v-slot:arrow-left>
              <figure class="image is-32x32">
                <img src="../assets/LeftArrow.png" />
              </figure>
            </template>
            <template v-slot:arrow-right>
              <figure class="image is-32x32">
                <img src="../assets/RightArrow.png" />
              </figure>
            </template>
            <vueper-slide id="slidesInside"
              v-for="(slide, i) in slides"
              :key="i"
              :image="slide.image"
              :title="slide.title"
              :content="slide.content"/>
            </vueper-slides>
          </div>
          <div class="column ">
            <figure class="image is-4by5">
              <img src="../assets/Tom_Nook.png" />
            </figure>
          </div>
        </div>
      <div class="columns" style="position:relative">
        <div class="column " >
          <figure class="image is-32x32" >
            <img src="../assets/LeftArrow.png" />
          </figure>
        </div>
        <div class="column" >
          <p class="doShopping" >Faites votre shopping !!&nbsp;</p>
          </div>
        <div class="column " style="margin-right: 10rem">
          <figure class="image is-32x32">
            <img src="../assets/LeftArrow.png" />
          </figure>
        </div>
        </div>
        </div>
    </div>
  <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
  <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
  <div class="container">
    <div class="content is-medium">
      <div class="column is-narrow has-text-centered">
        <p><br/>Alors, envie d'une escroquerie ? Connectez-vous ou alors rejoignez-nous !</p>
      </div>
    </div>
  </div>
  <div class="container">
    <div class="is-flex level is-vcentered">
      <div class="level-item">
        <div class="buttons is-flex">
          <button class="button is-primary is-fullwidth login"
                  v-on:click="navigateToLogin">Login</button>
          <button class="button is-primary is-fullwidth">Sign up</button>
        </div>
      </div>
    </div>
  </div>
  <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
  <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
  </div>
  <div v-else >
    <div class="hero is-small is-primary" >
     <div class="hero-body">
       <div class="columns" style="position:relative">
          <div class="column ">
            <figure class="image is-4by5">
              <img src="../assets/rickH.png" />
            </figure>
          </div>
          <div class="column is-half">
            <div class="level-item">
              <h2 class="subtitle is-5">Welcome to the</h2>
            </div>
            <div class="level-item ">
              <h1 class="title is-1">Captain Escroc Market</h1>
            </div>
            <vueper-slides class="no-shadow" fade :touchable="false" autoplay arrows-inside bullets-outside transition-speed="250" >
              <template v-slot:arrow-left>
                <figure class="image is-32x32">
                  <img src="../assets/LeftArrow.png" />
                </figure>
              </template>
              <template v-slot:arrow-right>
                <figure class="image is-32x32">
                  <img src="../assets/RightArrow.png" />
                </figure>
              </template>
              <vueper-slide id="slidesInside"
                v-for="(slide, i) in slides"
                :key="i"
                :image="slide.image"
                :title="slide.title"
                :content="slide.content"/>
            </vueper-slides>
            <div class="content is-medium " >
              <div class="containjeuxExlu column is-narrow" >
                <div class="jeuxExlu column is-narrow has-text-centered " >
                  <p>Le seul site où vous pouvez trouver des consoles de jeux exclusives !</p>
                </div>
              </div>
            </div>
          </div>
          <div class="column ">
            <figure class="image is-4by5">
              <img src="../assets/Tom_Nook.png" />
            </figure>
          </div>
        </div>
     </div>
    </div>
    <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
    <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
    <div class="container">
      <div class="content is-medium">
        <div class="column is-narrow has-text-centered">
          <p><br/>Alors, envie d'une escroquerie ? Connectez-vous ou alors rejoignez-nous !</p>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="is-flex level is-vcentered">
        <div class="level-item">
          <div class="buttons is-flex">
            <button class="button is-primary is-fullwidth login"
                    v-on:click="navigateToLogin">Login</button>
            <button class="button is-primary is-fullwidth">Sign up</button>
          </div>
        </div>
      </div>
    </div>
    <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>

  </div>
</template>

<script>
import router from '../router'
import { VueperSlides, VueperSlide } from 'vueperslides'
import 'vueperslides/dist/vueperslides.css'
var auth;
window.onscroll = function () { scrollFunction() }
function scrollFunction () {
  console.log( auth)
  if (auth) {
    if (document.body.scrollTop > 50 || document.documentElement.scrollTop > 50) {
      document.getElementsByClassName('no-shadow')[0].style.display = 'none'
      document.getElementsByClassName('image is-4by5')[0].style.display = 'none'
      document.getElementsByClassName('image is-4by5')[1].style.display = 'none'
      document.getElementsByClassName('doShopping')[0].style.fontSize = '0rem'
    } else {
      document.getElementsByClassName('doShopping')[0].style.fontSize = '2.125rem'
      document.getElementsByClassName('image is-4by5')[0].style.display = 'block'
      document.getElementsByClassName('image is-4by5')[1].style.display = 'block'
      document.getElementsByClassName('no-shadow')[0].style.display = 'block'
    }
  }
}

export default {
  components: { VueperSlides, VueperSlide },
  created: function () { auth = this.authenticated
    console.log("ii")},
  name: 'Home',

  data: () => ({
    pauseOnHover: false,
    autoPlaying: true,
    internalAutoPlaying: true,
    authenticated :false,

    slides: [
      {
        title: '',
        content: '',
        // You can also provide a URL for the image'../assets/Tom_Nook.png'.
        image: require('@/assets/ps5_large.png')
      },
      {
        title: '',
        content: '',
        // You can also provide a URL for the image'../assets/Tom_Nook.png'.
        image: require('@/assets/cyberbox_large.png')
      }
    ]
  }),
  methods: {
    navigateToLogin () {
      if (auth) {
        document.getElementsByClassName('no-shadow')[0].style.display = 'block'
        auth = false
        this.authenticated = false
      } else {
        document.getElementsByClassName('no-shadow')[0].style.display = 'block'
        auth = true
        this.authenticated = true

      }
     // router.push({name: 'Login'})
    }
  }
}

</script>

<style type ="text/css" >
.hero {
  color: black;
  text-align: center;
  font-weight: bold;
  font-size: 2.125rem;
  position: fixed; /* Fixed position - sit on top of the page */
  width: 100%; /* Full width */
}
.doShopping {

  transition: 0.75s; /* Add a transition effect (when scrolling - and font size is decreased) */
}

.containjeuxExlu {
  display: flex;
  justify-content: center;
  align-items: flex-end;
}

.jeuxExlu {
  position:absolute;
  bottom: 0rem;
}

.subtitle {
  padding-top: 0rem;
}

.columns {
  padding-top: 0.625rem;
    transition: height 1s ease-in-out;

}

.buttons {
  padding-top: 1.25rem;
}

#slidesInside {
  position: absolute; bottom: 0rem
}

.no-shadow {
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.vueperslides__bullet .default {
  background-color: rgba(0, 0, 0, 0.3);
  border: none;
  box-shadow: none;
  transition: 0.3s;
  width: 1rem;
  height: 1rem;
}

.vueperslides__bullet--active .default {background-color: #ffffff;}

.vueperslides__bullet span {
  display: block;
  color: #FCE1E5;
  font-size: 0.625rem;
  opacity: 0.8;
}
.vueperslides__arrow svg { stroke-width: 1}
</style>
