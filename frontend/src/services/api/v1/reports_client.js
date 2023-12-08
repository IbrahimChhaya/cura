import { param } from "jquery"

export default function ChatClient(instance) {
    this.apiInstance = instance

    this.getCommonChatFocus = (param) => {
        return this.apiInstance.get(`/Reports/getCommonChatFocus/${param.counsellorID}`).then((resp) => {
        return resp.data
        }).catch(() => {
        return []
        })
    }

    this.getNumCounsellorChatsForWeek = (param) => {
        return this.apiInstance.get(`/Reports/getNumCounsellorChatsForWeek/${param.counsellorID}`).then((resp) => {
        return resp.data
        }).catch(() => {
        return []
        })
    }

    this.getNumberOfNewPatientsThisMonth = (psychID) => {
        return this.apiInstance.get(`/Reports/getNumberOfNewPatientsThisMonth/${psychID}`).then((resp) => {
            return resp.data
        }).catch(() => {
            return []
        })
    }

    this.getPatientPercentIncrease = (psychID) => {
        return this.apiInstance.get(`/Reports/getPatientPercentIncrease/${psychID}`).then((resp) => {
            return resp.data
        }).catch(() => {
            return []
        })
    }

    this.getNumEachBookingPerPsych = (psychID) => {
        return this.apiInstance.get(`/Reports/getNumEachBookingPerPsych/${psychID}`).then((resp) => {
            return resp.data
        }).catch(() => {
            return []
        })
    }

    this.getNumBookingsThisMonth = (params) => {
		return this.apiInstance.post('/Reports/getNumBookingsThisMonth', params).then((resp) =>{
			return resp.data
		}).catch((error) => {
			return {}
		})
	}

    this.getBookingsPercentIncrease = (psychID) => {
        return this.apiInstance.get(`/Reports/getBookingsPercentIncrease/${psychID}`).then((resp) => {
            return resp.data
        }).catch(() => {
            return []
        })
    }
    this.getNumChildAnswersPerAnswer = (questionID) => {
        return this.apiInstance.get(`/Reports/getNumChildAnswersPerAnswer/${questionID}`).then((resp) => {
            return resp.data
        }).catch(() => {
            return []
        })
    }

    this.getAverageResponseTime = (param) => {
        return this.apiInstance.get(`/Chats/getCounsellorAverageResponseTime/${param.counsellorID}`).then((resp) => {
            return resp.data
        }).catch(() => {
            return {}
        })
    }

    this.getBookingsCountsPerMonth = (psychID) => {
        return this.apiInstance.get(`/Reports/getBookingsCountsPerMonth/${psychID}`).then((resp) => {
            return resp.data
        }).catch(() => {
            return {}
        })
    }

    this.getBookingsMonthlyAgeCounts = (psychID) => {
        return this.apiInstance.get(`/Reports/getBookingsMonthlyAgeCounts/${psychID}`).then((resp) => {
            return resp.data
        }).catch(() => {
            return {}
        })
    }

    this.getBookingsMonthlyFocusCounts = (psychID) => {
        return this.apiInstance.get(`/Reports/getBookingsMonthlyFocusCounts/${psychID}`).then((resp) => {
            return resp.data
        }).catch(() => {
            return {}
        })
    }

    this.getFocusCountsPerAge = (psychID) => {
        return this.apiInstance.get(`/Reports/getFocusCountsPerAge/${psychID}`).then((resp) => {
            return resp.data
        }).catch(() => {
            return {}
        })
    }

    this.getAllBookingsThisMonth = (typeCode) => {
        return this.apiInstance.get(`/Reports/getAllBookingsThisMonth/${typeCode}`).then((resp) => {
            return resp.data
        }).catch(() => {
            return {}
        })
    }

    this.getBookingsCountsPerMonthAdmin = () => {
        return this.apiInstance.get(`/Reports/getBookingsCountsPerMonthAdmin`).then((resp) => {
            return resp.data
        }).catch(() => {
            return {}
        })
    }

    this.numBookingsLastYearPerMonth = (psychID) => {
        return this.apiInstance.get(`/Reports/numBookingsLastYearPerMonth/${psychID}`).then((resp) => {
            return resp.data
        }).catch(() => {
            return {}
        })
    }

    this.getOnlyKids = () => {
        return this.apiInstance.get(`/Reports/getOnlyKids`).then((resp) => {
            return resp.data
        }).catch(() => {
            return {}
        })
    }

    this.getNumPairs = () => {
        return this.apiInstance.get(`/Reports/getNumPairs`).then((resp) => {
            return resp.data
        }).catch(() => {
            return {}
        })
    }

    this.getOnlyGuardians = () => {
        return this.apiInstance.get(`/Reports/getOnlyGuardians`).then((resp) => {
            return resp.data
        }).catch(() => {
            return {}
        })
    }

    this.getNumPairsLinked = () => {
        return this.apiInstance.get(`/Reports/getNumPairsLinked`).then((resp) => {
            return resp.data
        }).catch(() => {
            return {}
        })
    }

    this.getNumPairsNotLinked = () => {
        return this.apiInstance.get(`/Reports/getNumPairsNotLinked`).then((resp) => {
            return resp.data
        }).catch(() => {
            return {}
        })
    }

    this.getUserTypeRegistrationsPerMonth = (userType) => {
		return this.apiInstance.get(`/Reports/getUserTypeRegistrationsPerMonth/${userType}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getPopularTimes = (psychID) => {
		return this.apiInstance.get(`/Reports/getPopularTimes/${psychID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}
}