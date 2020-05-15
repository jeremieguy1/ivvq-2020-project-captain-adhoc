<template >
  <div>
    <div class="hero is-info">
      <div class="hero-body">
        <div  class="columns" style="position:relative">
          <div class="column">
            <figure class="image is-4by5">
              <img src="../assets/rickH.png"/>
            </figure>
            <figure class="image is-128x128" style=" bottom: 2rem; margin-left: auto; display: none">
              <img src="../assets/rickH.png"/>
            </figure>
          </div>
          <div class="column is-half">
            <div class="level-item ">
              <h1 class="title is-1">Captain Escroc Market</h1>
            </div>
            <vueper-slides class="no-shadow" fade :touchable="false" autoplay arrows-inside bullets-outside transition-speed="250">
              <template v-slot:arrow-left>
                <figure class="image is-32x32">
                  <img src="../assets/LeftArrow.png"/>
                </figure>
              </template>
              <template v-slot:arrow-right>
                <figure class="image is-32x32">
                  <img src="../assets/RightArrow.png"/>
                </figure>
              </template>
              <vueper-slide id="slidesInside"
                            v-for="(slide, i) in slides"
                            :key="i"
                            :image="slide.image"
                            :title="slide.title"
                            :content="slide.content"/>
            </vueper-slides>
            <div class="content is-medium">
              <div class="containjeuxExlu column is-narrow">
                <div class="jeuxExlu column is-narrow has-text-centered ">
                  <p>Le seul site où vous pouvez trouver des consoles de jeux exclusives !</p>
                </div>
              </div>
            </div>
          </div>
          <div class="column ">
            <figure class="image is-4by5">
              <img src="../assets/Tom_Nook.png"/>
            </figure>
            <figure class="image is-128x128" style=" bottom: 3rem; margin-right: auto; display: none">
              <img src="../assets/Tom_Nook.png"/>
            </figure>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
import { VueperSlides, VueperSlide } from 'vueperslides'
import 'vueperslides/dist/vueperslides.css'

export default {
  components: { VueperSlides, VueperSlide },
  created: function () {
    window.addEventListener('scroll', this.scrollFunction)
  },
  name: 'Home',
  data: () => ({
    pauseOnHover: false,
    autoPlaying: true,
    internalAutoPlaying: true,
    slides: [
      {
        title: '',
        content: '',
        image: require('@/assets/ps5_large.png')
      },
      {
        title: '',
        content: '',
        image: require('@/assets/cyberbox_large.png')
      }
    ]
  }),
  methods: {
    scrollFunction () {
      if (document.body.scrollTop > 50 || document.documentElement.scrollTop > 50) {
        document.getElementsByClassName('no-shadow')[0].style.display = 'none'
        document.getElementsByClassName('image is-4by5')[0].style.display = 'none'
        document.getElementsByClassName('image is-4by5')[1].style.display = 'none'
        document.getElementsByClassName('image image is-128x128')[0].style.display = 'block'
        document.getElementsByClassName('image image is-128x128')[1].style.display = 'block'
      } else {
        document.getElementsByClassName('no-shadow')[0].style.display = 'block'
        document.getElementsByClassName('image is-4by5')[0].style.display = 'block'
        document.getElementsByClassName('image is-4by5')[1].style.display = 'block'
        document.getElementsByClassName('image image is-128x128')[0].style.display = 'none'
        document.getElementsByClassName('image image is-128x128')[1].style.display = 'none'
      }
    }
  }
}

</script>

<style type ="text/css" >
  .hero {
    color: black;
    text-align: center;
    font-weight: bold;
    position: fixed; /* Fixed position - sit on top of the page */
    width: 100%; /* Full width */
  }

  .containjeuxExlu {
    display: flex;
    justify-content: center;
    align-items: flex-end;
  }

  .jeuxExlu {
    position: relative;
    padding-top: 4rem;
  }

  .subtitle {
    padding-top: 0rem;
  }

  .columns {
    padding-top: 0.625rem;
    transition: height 1s ease-in-out;
  }

  #slidesInside {
    position: absolute;
    bottom: 0rem;
  }

  .no-shadow {
    font-size: 3.125rem;
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
