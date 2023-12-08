import apiClient from '../../services/apiClient'

const namespaced = true

const state = {
	loggedInUser: {}
}

const getters = {
	loggedInUser: state => {
		return state.loggedInUser || {}
	}
}

const mutations = {
	updateLoggedInUser(state, user) {
		state.loggedInUser = user
	}
}

const actions = {
	async loginUser(context, requestParams) {
		return apiClient.authentication.login(requestParams)
	},

	async registerUser(context, requestParams) {
		return apiClient.authentication.register(requestParams)
	},

	async registerPsychologist(context, psychInfo) {
		return apiClient.authentication.registerPsychologist(psychInfo)
	},

	async registerCounsellor(context, counsellorInfo) {
		return apiClient.authentication.registerCounsellor(counsellorInfo)
	},

	async updateAccount(context, accountInfo) {
		return apiClient.authentication.updateAccount(accountInfo)
	},

	async changePassword(context, passwords) {
		return apiClient.authentication.changePassword(passwords)
	},

	async fetchPsychologist(context, psychID) {
		return apiClient.authentication.fetchPsychologist(psychID)
	},

	async fetchCounsellor(context, counsellorID) {
		return apiClient.authentication.fetchCounsellor(counsellorID)
	},

	async updatePsychologist(context, psychInfo) {
		return apiClient.authentication.updatePsychologist(psychInfo)
	},

	async updateCounsellor(context, counsInfo) {
		return apiClient.authentication.updateCounsellor(counsInfo)
	},

	async removeAccount(context, accDetails) {
		return apiClient.authentication.removeAccount(accDetails)
	},

	async reactivateAccount(context, info) {
		return apiClient.authentication.reactivateAccount(info)
	},
	
}

export default {
	namespaced,
	state,
	getters,
	mutations,
	actions,
}
