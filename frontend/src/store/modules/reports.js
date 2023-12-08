import apiClient from '../../services/apiClient'

const namespaced = true

const state = {

}

const getters = {

}

const mutations = {

}

const actions = {
  async getCommonChatFocus(context, param){
    return apiClient.reports.getCommonChatFocus(param)
  },

  async getNumCounsellorChatsForWeek(context, param){
    return apiClient.reports.getNumCounsellorChatsForWeek(param)
  },

  async getNumChildAnswersPerAnswer(context, questionID){
    return apiClient.reports.getNumChildAnswersPerAnswer(questionID)
  },

  async getNumberOfNewPatientsThisMonth(context, psychID){
    return apiClient.reports.getNumberOfNewPatientsThisMonth(psychID)
  },

  async getPatientPercentIncrease(context, psychID){
    return apiClient.reports.getPatientPercentIncrease(psychID)
  },

  async getNumEachBookingPerPsych(context, psychID){
    return apiClient.reports.getNumEachBookingPerPsych(psychID)
  },

  async getNumBookingsThisMonth(context, params){
    return apiClient.reports.getNumBookingsThisMonth(params)
  },

  async getBookingsPercentIncrease(context, params){
    return apiClient.reports.getBookingsPercentIncrease(params)
  },
  
  async getAverageResponseTime(context, param){
    return apiClient.reports.getAverageResponseTime(param)
  },

  async getBookingsCountsPerMonth(context, psychID){
    return apiClient.reports.getBookingsCountsPerMonth(psychID)
  },

  async getBookingsMonthlyAgeCounts(context, psychID){
    return apiClient.reports.getBookingsMonthlyAgeCounts(psychID)
  },

  async getBookingsMonthlyFocusCounts(context, psychID){
    return apiClient.reports.getBookingsMonthlyFocusCounts(psychID)
  },

  async getFocusCountsPerAge(context, psychID){
    return apiClient.reports.getFocusCountsPerAge(psychID)
  },

  async getAllBookingsThisMonth(context, typeCode){
    return apiClient.reports.getAllBookingsThisMonth(typeCode)
  },

  async getBookingsCountsPerMonthAdmin(context){
    return apiClient.reports.getBookingsCountsPerMonthAdmin()
  },

  async numBookingsLastYearPerMonth(context, psychID){
    return apiClient.reports.numBookingsLastYearPerMonth(psychID)
  },

  async getOnlyKids(context){
    return apiClient.reports.getOnlyKids()
  },

  async getOnlyGuardians(context){
    return apiClient.reports.getOnlyGuardians()
  },

  async getNumPairs(context){
    return apiClient.reports.getNumPairs()
  },

  async getNumPairsLinked(context){
    return apiClient.reports.getNumPairsLinked()
  },

  async getNumPairsNotLinked(context){
    return apiClient.reports.getNumPairsNotLinked()
  },

  async getUserTypeRegistrationsPerMonth(context, userType){
    return apiClient.reports.getUserTypeRegistrationsPerMonth(userType)
  },

  async getPopularTimes(context, psychID){
    return apiClient.reports.getPopularTimes(psychID)
  },
}

export default {
  namespaced,
  state,
  getters,
  mutations,
  actions,
}