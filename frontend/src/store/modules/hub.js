import apiClient from '../../services/apiClient'

const namespaced = true

const state = {
	allFocusPoints:                   [],
	childFocusPoints:                 [],
	focusPointsAvailableForSelection: [],
}

const getters = {
	allFocusPoints: state => {
		return state.allFocusPoints || []
	},

	childFocusPoints: state => {
		return state.childFocusPoints || []
	},

	focusPointsAvailableForSelection: state => {
		return state.focusPointsAvailableForSelection || []
	}
}

const mutations = {
	updateAllFocusPoints(state, pointsList){
		state.allFocusPoints = pointsList
	},

	updateChildFocusPoints(state, pointsList){
		state.childFocusPoints = pointsList
	},

	updateFocusPointsAvailableForSelection(state, pointsList){
		state.focusPointsAvailableForSelection = pointsList
	},
}

const actions = {
	async fetchChildFocusPoints(context, userID) {
		const resp = await apiClient.hub.fetchChildFocusPoints(userID)
		
		context.commit('updateChildFocusPoints', resp)
	},
	
	async fetchAllFocusPoints(context) {
		const resp = await apiClient.hub.fetchAllFocusPoints()

		context.commit('updateAllFocusPoints', resp)

		const allPoints        = context.getters.allFocusPoints
		const childPoints      = context.getters.childFocusPoints

		const selectablePoints = allPoints.filter((point) => !childPoints.find((sPoint) => point.problemID === sPoint.problemID))

		context.commit('updateFocusPointsAvailableForSelection', selectablePoints)
	},

	async removeFocusPoint(context, {childID, problem}){
		const requestParams = {
			problemID: problem.problemID,
			userID:    childID
		}

		const resp = await apiClient.hub.removeChildFocusPoint(requestParams)

		if(resp != undefined){
			if(resp === 0){
				const childPoints      = context.getters.childFocusPoints
				const selectablePoints = context.getters.focusPointsAvailableForSelection

				selectablePoints.push(problem)

				context.commit('updateFocusPointsAvailableForSelection', selectablePoints)

				//find index of point removed
				const index = childPoints.indexOf(problem)
				childPoints.splice(index, 1)

				context.commit('updateChildFocusPoints', childPoints)
			} else {
				//cater for -1 and -2 response

				// pop up a toast notification with a generic response 
				// or handle both cases explicitly with an appropriate message for each case
			}
		}
	},

	async addChildFocusPoint(context, {childID, problem}){
		const requestParams = {
			problemID: problem.problemID,
			userID:    childID
		}

		const resp = await apiClient.hub.assignChildFocusPoint(requestParams)

		if(resp != undefined && resp.bridgeID > 0){
			const childPoints = context.getters.childFocusPoints
			const selectablePoints = context.getters.focusPointsAvailableForSelection

			childPoints.push(problem)
			context.commit('updateChildFocusPoints', childPoints)

			//find index of point in selectable points
			const index = selectablePoints.indexOf(problem)
			selectablePoints.splice(index, 1)

			context.commit('updateFocusPointsAvailableForSelection', selectablePoints)
		}

	},

	async getRHubProblem(context, problemID) {
		return apiClient.hub.getRHubProblem(problemID)
	},

	async fetchResourceHubImages(context, problemID) {
		return apiClient.hub.fetchResourceHubImages(problemID)
	},

	async assignChildFocusPoint(context, requestParams) {
		return apiClient.hub.assignChildFocusPoint(requestParams)
	},

	async fetchAllFocusResource(context, requestParams) {
		return apiClient.hub.fetchAllFocusResource(requestParams)
	},
	
	async getChildFocusPoints(context, requestParams) {
		return apiClient.hub.getChildFocusPoints(requestParams)
	},

    async AddResourceHubProblem(context, requestParams) {
		return apiClient.hub.AddResourceHubProblem(requestParams)
	},

    async AddRHubProblemImage(context, requestParams) {
		return apiClient.hub.AddRHubProblemImage(requestParams)
	},

    async updateRHubProblem(context, requestParams) {
		return apiClient.hub.updateRHubProblem(requestParams)
	},

    async updateRHubProblemImage(context, requestParams) {
		return apiClient.hub.updateRHubProblemImage(requestParams)
	},
}

export default {
	namespaced,
	state,
	getters,
	mutations,
	actions,
}