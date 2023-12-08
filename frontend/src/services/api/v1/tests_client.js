export default function TestClient(instance){
	this.apiInstance = instance

    this.getTestsDoneWithPsych = (params) => {
		return this.apiInstance.post('/Tests/getTestsDoneWithPsych', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.getWeeklyMoodTrend = (childID) => {
		return this.apiInstance.get(`/Tests/getWeeklyMoodTrend/${childID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getPsychTests = (psychID) => {
		return this.apiInstance.get(`/Tests/getPsychTests/${psychID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getTestQuestions = (params) => {
		return this.apiInstance.post('/Tests/getTestQuestions', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getQuestionAnswers = (questionID) => {
		return this.apiInstance.get(`/Tests/getQuestionAnswers/${questionID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getTest = (testID) => {
		return this.apiInstance.get(`/Tests/getTest/${testID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.updateTest = (params) => {
		return this.apiInstance.post('/Tests/updateTest', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.addAnswers = (params) => {
		return this.apiInstance.post('/Tests/addAnswers', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getNumResponses = (params) => {
		return this.apiInstance.post('/Tests/getNumResponses', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.deleteQuestion = (testID) => {
		return this.apiInstance.get(`/Tests/deleteQuestion/${questionID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.deleteTest = (testID) => {
		return this.apiInstance.get(`/Tests/deleteTest/${testID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getNumQuestions = (testID) => {
		return this.apiInstance.get(`/Tests/getNumQuestions/${testID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getNumAssignedPerTest = (params) => {
		return this.apiInstance.post('/Tests/getNumAssignedPerTest', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getNumIncomplete = (params) => {
		return this.apiInstance.post('/Tests/getNumIncomplete', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getChildTestAssignedStatus = (params) => {
		return this.apiInstance.post('/Tests/getChildTestAssignedStatus', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.assignChildToTest = (params) => {
		return this.apiInstance.post('/Tests/assignChildToTest', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}
    
    this.getCompletedTests = (testID) => {
		return this.apiInstance.get(`/Tests/getCompletedTests/${testID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getTotalAnswerScore = (childTestID) => {
		return this.apiInstance.get(`/Tests/getTotalAnswerScore/${childTestID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getChildTestAnswersWithID = (childTestID) => {
		return this.apiInstance.get(`/Tests/getChildTestAnswersWithID/${childTestID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getChildTest = (childTestID) => {
		return this.apiInstance.get(`/Tests/getChildTest/${childTestID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.fetchAllChildPsychTests = (params) => {
		return this.apiInstance.post('/Tests/fetchAllChildPsychTests', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getAverageMoodPerWeek = (childID) => {
		return this.apiInstance.get(`/Tests/getAverageMoodPerWeek/${childID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getPsychTestsList = (psychID) => {
		return this.apiInstance.get(`/Tests/getPsychTestsList/${psychID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getStandardTests = () => {
		return this.apiInstance.get(`/Tests/getStandardTests`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getNumAssignedStandardTest = (testID) => {
		return this.apiInstance.get(`/Tests/getNumAssignedStandardTest/${testID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getMoodAnalysis = (childID) => {
		return this.apiInstance.get(`/Tests/getMoodAnalysis/${childID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.getYearlyMoodAverage = (childID) => {
		return this.apiInstance.get(`/Tests/getYearlyMoodAverage/${childID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}
}