export default function UserClient(instance) {
	this.apiInstance = instance

	this.GetChild = (params) => {
		return this.apiInstance.post('/User/GetChild', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.GetGuardian = (params) => {
		return this.apiInstance.get(`/User/GetGuardian/${params.childID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.GetPairChild = (pairID) => {
		return this.apiInstance.get(`/User/GetPairChild/${pairID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.getUserById = (userID) => {
		return this.apiInstance.get(`/User/getUserById/${userID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getPendingAccounts = () => {
		return this.apiInstance.get(`/User/getPendingAccounts`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getRejectedAccounts = () => {
		return this.apiInstance.get(`/User/getRejectedAccounts`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getRejectedAccounts = () => {
		return this.apiInstance.get(`/User/getRejectedAccounts`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.fetchCounsellor = (params) => {
		return this.apiInstance.post('/User/fetchCounsellor', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.fetchPsychologist = (params) => {
		return this.apiInstance.post('/User/fetchPsychologist', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.ChangeUserStatus = (params) => {
		return this.apiInstance.post('/User/ChangeUserStatus', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getAllPsychologists = () => {
		return this.apiInstance.get(`/User/getAllPsychologists`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getAllCounsellors = () => {
		return this.apiInstance.get(`/User/getAllCounsellors`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getNumPsychologists = () => {
		return this.apiInstance.get(`/User/getNumPsychologists`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getNumCounsellors = () => {
		return this.apiInstance.get(`/User/getNumCounsellors`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getNumGuardians = () => {
		return this.apiInstance.get(`/User/getNumGuardians`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getNumChild = () => {
		return this.apiInstance.get(`/User/getNumChild`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}
}