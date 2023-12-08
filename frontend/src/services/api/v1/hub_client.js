export default function HubClient(instance){
	this.apiInstance = instance

    this.getChildFocusPoints = (userID) => {
		return this.apiInstance.get(`/hub/fetchChildFocusPoints/${userID}`).then((resp) => {
			return resp.data
		}).catch(() => {
			return []
		})
	}

	this.fetchChildFocusPoints = (userID) => {
		return this.apiInstance.get(`/hub/fetchChildFocusPoints/${userID}`).then((resp) => {
			return resp.data
		}).catch(() => {
			return []
		})
	}

	this.fetchAllFocusPoints = () => {
		return this.apiInstance.get(`/Hub/fetchAllFocusPoints/`).then((resp) => {
			return resp.data
		}).catch(() => {
			return []
		})
	}

	this.getRHubProblem = (problemID) => {
		return this.apiInstance.get(`/Hub/getRHubProblem/${problemID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.fetchResourceHubImages = (problemID) => {
		return this.apiInstance.get(`/Hub/fetchResourceHubImages/${problemID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}
	
	this.removeChildFocusPoint = (requestParams) => {
		return this.apiInstance.post('/Hub/removeChildFocusPoint/', requestParams).then((resp) => {
			return resp.data
		}).catch(() => {
			return {}
		})
	}

	this.assignChildFocusPoint = (requestParams) => {
		return this.apiInstance.post('/Hub/assignChildFocusPoint', requestParams).then((resp) => {
			return resp.data
		}).catch(() => {
			return {}
		})
	}

	this.fetchAllFocusResource = () => {
		return this.apiInstance.get(`/Hub/fetchAllFocusPoints/`).then((resp) => {
			return resp.data
		}).catch(() => {
			return []
		})
	}

    this.AddResourceHubProblem = (requestParams) => {
		return this.apiInstance.post('/Hub/AddResourceHubProblem', requestParams).then((resp) => {
			return resp.data
		}).catch(() => {
			return {}
		})
	}

    this.AddRHubProblemImage = (requestParams) => {
		return this.apiInstance.post('/Hub/AddRHubProblemImage', requestParams).then((resp) => {
			return resp.data
		}).catch(() => {
			return {}
		})
	}

    this.updateRHubProblem = (requestParams) => {
		return this.apiInstance.post('/Hub/updateRHubProblem', requestParams).then((resp) => {
			return resp.data
		}).catch(() => {
			return {}
		})
	}

    this.updateRHubProblemImage = (requestParams) => {
		return this.apiInstance.post('/Hub/updateRHubProblemImage', requestParams).then((resp) => {
			return resp.data
		}).catch(() => {
			return {}
		})
	}
}