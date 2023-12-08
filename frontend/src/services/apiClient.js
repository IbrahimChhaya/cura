import axios from 'axios'

import AuthenticationClient from './api/v1/authentication_client'
import BookingsClient 			from './api/v1/bookings_client'
import PatientsClient 			from './api/v1/patients_client'
import PsychologistClient 	from './api/v1/psychologist_client'
import UserClient 					from './api/v1/user_client'
import HubClient 						from './api/v1/hub_client'
import NotesClient 					from './api/v1/notes_client'
import TestsClient 					from './api/v1/tests_client'
import ActivityClient 			from './api/v1/activity_client'
import ChatClient 					from './api/v1/chat_client'
import ReportsClient 				from './api/v1/reports_client'

const apiInstance = axios.create({
	baseURL: "http://localhost:61135/api",
	json:    true,
})

const client = {
	authentication: new AuthenticationClient(apiInstance),
	bookings:       new BookingsClient(apiInstance),
	patients:       new PatientsClient(apiInstance),
	psychologist:   new PsychologistClient(apiInstance),
	users:          new UserClient(apiInstance),
	hub:            new HubClient(apiInstance),
	notes:          new NotesClient(apiInstance),
	tests:          new TestsClient(apiInstance),
	activity:       new ActivityClient(apiInstance),
	chat:           new ChatClient(apiInstance),
	reports:        new ReportsClient(apiInstance),
}

export default client