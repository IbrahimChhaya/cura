<template>
  <div id="app">
    <SideNav v-if="!isLogin" />

    <div :class="{'app-container': !isLogin}" class="container-fluid">
      <TopNav v-if="!isLogin" />
      <router-view/>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
import TopNav  from '@/components/TopNav'
import SideNav from '@/components/SideNav'

import { MdButton, MdContent, MdTabs } from 'vue-material/dist/components'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'

Vue.use(MdTabs)
Vue.use(MdButton)
Vue.use(MdContent)

export default {
  name: 'App',

  components: {
    SideNav,
    TopNav,
  },

  computed: {
    isLogin() {
      return this.$route.name === "Login"
    }
  },

  mounted() {
    const user = JSON.parse(window.localStorage.getItem('currentUser')) || {}

    this.$store.commit('auth/updateLoggedInUser', user)
  },

}
</script>

<style>
  @import './assets/css/argon.css';
  @import './assets/css/style.css';
  @import './assets/vendor/@fortawesome/fontawesome-free/css/all.min.css';
  @import './assets/vendor/nucleo/css/nucleo.css';

  :root {
      --main-color: #FBF5F2;
      --orange: #FF9965;
      --text-blue: #022F5B;
      --text-grey: #8390A2;
  }

  @font-face {
      font-family: 'Segoe UI Semibold';
      src: url('./assets/fonts/seguisb.eot');
      src: url('./assets/fonts/seguisb.eot?#iefix') format('embedded-opentype'), url('./assets/fonts/seguisb.woff2') format('woff2'), url('./assets/fonts/seguisb.woff') format('woff'), url('./assets/fonts/seguisb.ttf') format('truetype'), url('./assets/fonts/seguisb.svg#Segoe UI Semibold') format('svg');
  }

  #app {
    font-family: 'Segoe UI Semibold', Tahoma, Geneva, Verdana, sans-serif;
    -webkit-font-smoothing:  antialiased;
    -moz-osx-font-smoothing: grayscale;
    color:                   var(--text-blue);
  }

  .app-container {
    width:    calc(100vw - 260px);
    position: absolute;
    left:     244px;
  }

  .md-theme-default a:not(.md-button) {
    color: #022F5B;
  }

  h1, h2, h3, h4, h5, h6, .h1, .h2, .h3, .h4, .h5, .h6 {
    color: #022F5B;
  }

  .ql-editor {
    color: #022F5B;
  }

  .ql-picker-label {
    color: #022F5B;
  }

  .ql-editor:focus {
    color: var(--text-blue);
  }

  .ql-snow .ql-picker.ql-expanded .ql-picker-options {
    z-index: 2;
  }

  .ql-snow .ql-tooltip {
    z-index: 9999;
  }

  .v-application--is-ltr .v-expansion-panel-header {
    color: #022F5B;
  }
</style>
