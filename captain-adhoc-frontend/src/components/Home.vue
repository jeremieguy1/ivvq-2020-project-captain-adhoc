<template>
  <div>
    <div class="hero is-small is-primary">
      <div class="hero-body">
        <div class="columns" style="position:relative">
          <div id="templateRick" class="column ">
            <figure class="image is-4by5">
              <img src="../assets/rickH.png" />
            </figure>
          </div>
        <div class="column is-half">
          <div class="level-item">
            <h2 id="welcome" class="subtitle is-5">Welcome to the</h2>
          </div>
          <div class="level-item ">
            <h1 class="title is-1">Captain Escroc Market</h1>
          </div>
          <vueper-slides id="slides" class="no-shadow" arrows-inside bullets-outside transition-speed="250">

          <!--  <template v-slot:arrow-left>
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="black" width="96px" height="96px"><path d="M0 0h24v24H0z" fill="none"/><path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 14.5v-9l6 4.5-6 4.5z"/>
              </svg>
            </template>

            <template v-slot:arrow-right>
              <i class="icon icon-arrow-right" />
              <figure class="image is-4by5">
                <img src="../assets/RightArrow.png" />
              </figure>
            </template>
                        <template v-slot:arrow-right>
              <i class="icon icon-arrow-right" />
              <figure class="image is-32x32">
                <img src="../assets/RightArrow.png" />
              </figure>
            </template>-->

            <vueper-slide id="slidesInside"
              v-for="(slide, i) in slides"
              :key="i"
              :image="slide.image"
              :title="slide.title"
              :content="slide.content"
              :style="'background-color: ' + ['#ff5252', '#42b983'][i % 2]" />

          </vueper-slides>
          <div class="content is-medium " >
            <div  id="container" class="column is-narrow has-text-centered " >
              <div id="jeuxExlu" class="column is-narrow has-text-centered " >
                <p>Le seul site où vous pouvez trouver des consoles de jeux exclusives !</p>
              </div>
            </div>
          </div>
        </div>
          <div id="templateNook" class="column ">
            <figure class="image is-4by5">
              <img src="../assets/Tom_Nook.png" />
            </figure>
          </div>
      </div>
      </div>
    </div>
    <div class="container">
      <div class="content is-medium">
        <div class="column is-narrow has-text-centered">
          <p><br/>Alors, envie d'une escroquerie ? Connectez-vous ou alors rejoignez-nous !</p>
        </div>
      </div>
    </div>
    <section class="section">
      <div class="container">
        <div class="is-flex level is-vcentered">
          <div class="level-item">
            <div class="buttons is-flex">
              <button class="button is-primary is-fullwidth login"
                      v-on:click="navigate">Login</button>
              <button class="button is-primary is-fullwidth">Sign up</button>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import router from '../router'
import { VueperSlides, VueperSlide } from 'vueperslides'
import 'vueperslides/dist/vueperslides.css'

export default {
  components: { VueperSlides, VueperSlide },

  name: 'Home',
  data: () => ({
                 pauseOnHover: false,
                 autoPlaying: true,
                 internalAutoPlaying: true,
                 slides: [
                   {
                     title: 'El Teide Volcano, Spain',
                     content: 'Photo by Max Rive',
                     // You can also provide a URL for the image'../assets/Tom_Nook.png'.
                     image: require('@/assets/ps5.png')
                   },
                   {
                     title: 'El Teide Volcano, Spain',
                     content: 'Photo by Max Rive',
                     // You can also provide a URL for the image'../assets/Tom_Nook.png'.
                     image: require('@/assets/cyberbox.png')
                   }
                 ]
               }),
  methods: {
    navigateToLogin() {

      router.push({name: 'Login'})
    }
  }
}

</script>

<style type ="text/css" >

#container {
    display: flex;
    justify-content: center;
    align-items: flex-end;
    padding-top: 60px;
  }
  #jeuxExlu {
    position:absolute;
    bottom: 0;
  }
  .columns {
    margin: -35px 0;
    padding: 0;
  }
  #welcome {
    padding-top: 30px;
  }
  #templateNook {
    padding-top: 150px;
  }
  #templateRick {
    padding-top: 150px;
  }
  @keyframes slidy {
    0% {
      left: 0%;
    }
    20% {
      left: 0%;
    }
    25% {
      left: -100%;
    }
    45% {
      left: -100%;
    }
    50% {
      left: -200%;
    }
    70% {
      left: -200%;
    }
    75% {
      left: -300%;
    }
    95% {
      left: -300%;
    }
    100% {
      left: -400%;
    }
  }

  div#slider {
    overflow: hidden;
  }

  div#slider figure img {
    width: 20%;
    float: left;
  }

  div#slider figure {
    position: relative;
    width: 500%;
    margin: 0;
    left: 0;
    text-align: left;
    font-size: 0;
    animation: 30s slidy infinite;
  }
#slidesInside {
  padding-top: 30px;
}
.ex--center-mode {
  width: 600px;
  max-width: 100%;
  margin: auto;
}
.vueperslides__progress {
  background: rgba(0, 0, 0, 0.25);
  color: #ff5252;
}
#slides {
  padding-top: 30px;
}
.vueperslides__bullet .default {
  background-color: rgba(0, 0, 0, 0.3);
  border: none;
  box-shadow: none;
  transition: 0.3s;
  width: 16px;
  height: 16px;
}

.vueperslides__bullet--active .default {background-color: #ffffff;}

.vueperslides__bullet span {
  display: block;
  color: #FCE1E5;
  font-size: 10px;
  opacity: 0.8;
}
.vueperslides--fixed-height { height: border-box; }

.vueperslides__arrow svg { stroke-width: 1}
</style>
