import Vue  from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import auth     		 from './modules/auth.js'
import bookings 		 from './modules/bookings.js'
import patients 		 from './modules/patients.js'
import psychologists from './modules/psychologist.js'
import users				 from './modules/users.js'
import reports 			 from './modules/reports.js'
import hub 	 				 from './modules/hub.js'
import notes 				 from './modules/notes.js'
import tests 				 from './modules/tests.js'
import activity 		 from './modules/activity.js'
import chat 				 from './modules/chat.js'

export default new Vuex.Store({
	modules: {
		auth,
		bookings,
		patients,
		psychologists,
		users,
		hub,
		notes,
		tests,
		activity,
		chat,
		reports,
	}

})
