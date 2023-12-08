// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue       from 'vue'
import App       from './App'
import router    from './router'
import store     from './store'
import PortalVue from 'portal-vue'
import FullCalendar from 'vue-full-calendar'

import { BootstrapVue, IconsPlugin }  from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import VueDatePicker from '@mathieustan/vue-datepicker';
import '@mathieustan/vue-datepicker/dist/vue-datepicker.min.css';
import VueHorizontal from 'vue-horizontal';

//vue-material
import { MdButton, MdContent, MdTabs } from 'vue-material/dist/components'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css' //consider removing vue-material
//vuetify material
import vuetify from '@/plugins/vuetify' // path to vuetify export
import Vuetify from 'vuetify'
//apex charts
import '@/plugins/apexcharts'
//quill text editor
import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css' // import styles
import 'quill/dist/quill.snow.css' // for snow theme
import 'quill/dist/quill.bubble.css' // for bubble theme
//vue animated number
import VueNumber from 'vue-number-animation'
//vue tour
import VueTour from 'vue-tour'

//use vue material
Vue.use(MdTabs)
Vue.use(MdButton)
Vue.use(MdContent)
//use quill editor
Vue.use(VueQuillEditor)
//use animated number
Vue.use(VueNumber)

Vue.use(PortalVue)
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(FullCalendar)
Vue.use(VueDatePicker)
Vue.use(VueHorizontal)
//use vue tour
Vue.use(VueTour)

const vuetifyOptions = { }

Vue.config.productionTip = false

require('vue-select-image/dist/vue-select-image.css')
//vue tour css
require('vue-tour/dist/vue-tour.css')

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  vuetify: new Vuetify(vuetifyOptions),
  components: { App },
  template: '<App/>'
})
