import apiClient from '../../services/apiClient'

const namespaced = true

const state = {

}

const getters = {

}

const mutations = {

}

const actions = {
	async getTestsDoneWithPsych(context, requestParams) {
		return apiClient.tests.getTestsDoneWithPsych(requestParams)
	},

	async getWeeklyMoodTrend(context, childID) {
		return apiClient.tests.getWeeklyMoodTrend(childID)
	},

    async getPsychTests(context, psychID) {
		return apiClient.tests.getPsychTests(psychID)
	},

    async getTestQuestions(context, requestParams) {
		return apiClient.tests.getTestQuestions(requestParams)
	},

    async getQuestionAnswers(context, requestParams) {
		return apiClient.tests.getQuestionAnswers(requestParams)
	},

    async getTest(context, testID) {
		return apiClient.tests.getTest(testID)
	},

    async updateTest(context, requestParams) {
		return apiClient.tests.updateTest(requestParams)
	},

    async addAnswers(context, requestParams) {
		return apiClient.tests.addAnswers(requestParams)
	},

    async getNumResponses(context, testID) {
		return apiClient.tests.getNumResponses(testID)
	},

    async deleteQuestion(context, questionID) {
		return apiClient.tests.deleteQuestion(questionID)
	},

    async deleteTest(context, testID) {
		return apiClient.tests.deleteTest(testID)
	},

    async getNumQuestions(context, requestParams) {
		return apiClient.tests.getNumQuestions(requestParams)
	},

    async getNumAssignedPerTest(context, requestParams) {
		return apiClient.tests.getNumAssignedPerTest(requestParams)
	},

    async getNumIncomplete(context, requestParams) {
		return apiClient.tests.getNumIncomplete(requestParams)
	},

    async getChildTestAssignedStatus(context, requestParams) {
		return apiClient.tests.getChildTestAssignedStatus(requestParams)
	},

    async assignChildToTest(context, requestParams) {
		return apiClient.tests.assignChildToTest(requestParams)
	},

    async getCompletedTests(context, testID) {
		return apiClient.tests.getCompletedTests(testID)
	},

    async getTotalAnswerScore(context, childTestID) {
		return apiClient.tests.getTotalAnswerScore(childTestID)
	},

    async getChildTestAnswersWithID(context, childTestID) {
		return apiClient.tests.getChildTestAnswersWithID(childTestID)
	},

    async getChildTest(context, childTestID) {
		return apiClient.tests.getChildTest(childTestID)
	},

    async fetchAllChildPsychTests(context, params) {
		return apiClient.tests.fetchAllChildPsychTests(params)
	},

    async getAverageMoodPerWeek(context, childID) {
		return apiClient.tests.getAverageMoodPerWeek(childID)
	},

    async getPsychTestsList(context, psychID) {
		return apiClient.tests.getPsychTestsList(psychID)
	},

    async getStandardTests(context) {
		return apiClient.tests.getStandardTests()
	},

    async getNumAssignedStandardTest(context, testID ) {
		return apiClient.tests.getNumAssignedStandardTest(testID)
	},

    async getMoodAnalysis(context, childID ) {
		return apiClient.tests.getMoodAnalysis(childID)
	},

    async getYearlyMoodAverage(context, childID ) {
		return apiClient.tests.getYearlyMoodAverage(childID)
	},
}

export default {
	namespaced,
	state,
	getters,
	mutations,
	actions,
}