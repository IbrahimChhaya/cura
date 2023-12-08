export default function AuthenticationClient(instance) {
	this.apiInstance = instance

	this.login = (params) => {
		return this.apiInstance.post('/User/login', params).then((resp) => {
			return resp.data	
		}).catch((error) => {
			return {}
		})
	}

	this.register = (params) => {
		return this.apiInstance.post('/User/Register', params).then((resp) =>{
			return resp.data
		}).catch((error) => {
			return {}
		})
	}

	this.registerPsychologist = (psychInfo) => {
		return this.apiInstance.post('/User/RegisterPsychologist', psychInfo).then((resp) => {
			return resp.data
		}).catch((error) => {
			return {}
		})
	}

	this.registerCounsellor = (counsellorInfo) => {
		return this.apiInstance.post('/User/RegisterCounsellor', counsellorInfo).then((resp) => {
			return resp.data
		}).catch((error) => {
			return {}
		})
	}

	this.updateAccount = (accountDetails) => {
		return this.apiInstance.post('/User/UpdateUserDetails', accountDetails).then((resp) => {
			return resp.data
		}).catch((error) => {
			return {}
		})
	}

	this.changePassword = (passwords) => {
		return this.apiInstance.post('/User/ChangePassword', passwords).then((resp) => {
			return resp.data
		}).catch((error) => {
			return {}
		})
	}

	this.fetchPsychologist = (param) => {
		return this.apiInstance.post('/User/fetchPsychologist', param).then((resp) => {
			return resp.data
		}).catch((error) => {
			return {}
		})
	}

	this.fetchCounsellor = (param) => {
		return this.apiInstance.post('/User/fetchCounsellor', param).then((resp) => {
			return resp.data
		}).catch((error) => {
			return {}
		})
	}

	this.updatePsychologist = (newPsych) => {
		return this.apiInstance.post('/User/updatePsychologistInfo', newPsych).then((resp) => {
			return resp.data
		}).catch((error) => {
			return ''
		})
	}

	this.updateCounsellor = (newCouns) => {
		return this.apiInstance.post('/User/updateCounsellorInfo', newCouns).then((resp) => {
			return resp.data
		}).catch((error) => {
			return ''
		})
	}

	this.removeAccount = (accDetails) => {
		return this.apiInstance.post('/User/removeAccount', accDetails).then((resp) => {
			return resp.data
		}).catch((error) => {
			return ''
		})
	}

	this.reactivateAccount = (accDetails) => {
		return this.apiInstance.post('/User/reactivateAccount', accDetails).then((resp) => {
			return resp.data
		}).catch((error) => {
			return {}
		})
	}
}