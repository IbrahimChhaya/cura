import apiClient from '../../services/apiClient'

const namespaced = true

const state = {

}

const getters = {

}

const mutations = {

}

const actions = {
	async getAllChildPsychActivities(context, requestParams) {
		return apiClient.activity.getAllChildPsychActivities(requestParams)
	},
}

export default {
	namespaced,
	state,
	getters,
	mutations,
	actions,
}