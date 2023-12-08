import apiClient from '../../services/apiClient'

const namespaced = true

const state = {

}

const getters = {

}

const mutations = {

}

const actions = {
	async getPsychologistCalendars(context, requestParams) {
		return apiClient.psychologist.getPsychologistCalendars(requestParams)
	},
	async updatePsychCalendar(context, requestParams) {
		return apiClient.psychologist.updatePsychCalendar(requestParams)
	},
}

export default {
	namespaced,
	state,
	getters,
	mutations,
	actions,
}