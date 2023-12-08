import apiClient from '../../services/apiClient'

const namespaced = true

const state = {

}

const getters = {

}

const mutations = {

}

const actions = {
	async GetChild(context, requestParams) {
		return apiClient.users.GetChild(requestParams)
	},
	
	async GetGuardian(context, requestParams) {
		return apiClient.users.GetGuardian(requestParams)
	},
	
	async GetPairChild(context, pairID) {
		return apiClient.users.GetPairChild(pairID)
	},

	async getUserById(context, userID) {
		return apiClient.users.getUserById(userID)
	},

    async getPendingAccounts(context) {
		return apiClient.users.getPendingAccounts()
	},

    async getRejectedAccounts(context) {
		return apiClient.users.getRejectedAccounts()
	},

    async fetchCounsellor(context, requestParams) {
		return apiClient.users.fetchCounsellor(requestParams)
	},

    async fetchPsychologist(context, requestParams) {
		return apiClient.users.fetchPsychologist(requestParams)
	},

    async ChangeUserStatus(context, requestParams) {
		return apiClient.users.ChangeUserStatus(requestParams)
	},

    async getAllPsychologists(context) {
		return apiClient.users.getAllPsychologists()
	},

    async getAllCounsellors(context) {
		return apiClient.users.getAllCounsellors()
	},

    async getNumPsychologists(context) {
		return apiClient.users.getNumPsychologists()
	},

    async getNumCounsellors(context) {
		return apiClient.users.getNumCounsellors()
	},

    async getNumGuardians(context) {
		return apiClient.users.getNumGuardians()
	},

    async getNumChild(context) {
		return apiClient.users.getNumChild()
	},
}

export default {
	namespaced,
	state,
	getters,
	mutations,
	actions,
}