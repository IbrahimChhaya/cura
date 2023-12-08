import apiClient from '../../services/apiClient'

const namespaced = true

const state = {

}

const getters = {

}

const mutations = {

}

const actions = {
	async fetchAllChildsNotes(context, requestParams) {
		return apiClient.notes.fetchAllChildsNotes(requestParams)
	},

	async saveNote(context, requestParams) {
		return apiClient.notes.saveNote(requestParams)
	},

	async getNote(context, requestParams) {
		return apiClient.notes.getNote(requestParams)
	},
}

export default {
	namespaced,
	state,
	getters,
	mutations,
	actions,
}