import apiClient from '../../services/apiClient'

const namespaced = true

const state = {

}

const getters = {

}

const mutations = {

}

const actions = {
	async getUpcomingBookings(context, requestParams) {
		return apiClient.bookings.getUpcomingBookings(requestParams)
	},

	async getBookingsPatients(context, requestParams) {
		return apiClient.bookings.getBookingsPatients(requestParams)
	},

	async getBookingParent(context, childID) {
		return apiClient.bookings.getBookingParent(childID)
	},

	async getLastAppointment(context, childID) {
		return apiClient.bookings.getLastAppointment(childID)
	},

	async getPsychCalendar(context, psychID) {
		return apiClient.bookings.getPsychCalendar(psychID)
	},

	async getPsychBookings(context, psychID) {
		return apiClient.bookings.getPsychBookings(psychID)
	},

	async getNumberOfPastBookings(context, userID) {
		return apiClient.bookings.getNumberOfPastBookings(userID)
	},

	async getNumberOfUpcomingBookings(context, userID) {
		return apiClient.bookings.getNumberOfUpcomingBookings(userID)
	},

	async GetLastAppointmentDate(context, userID) {
		return apiClient.bookings.GetLastAppointmentDate(userID)
	},

	async getAllUpcomingBookings(context, userID) {
		return apiClient.bookings.getAllUpcomingBookings(userID)
	},

	async GetUserBookings(context, userID) {
		return apiClient.bookings.GetUserBookings(userID)
	},

	async getUserBookingsWithPsych(context, requestParams) {
		return apiClient.bookings.getUserBookingsWithPsych(requestParams)
	},

	async getAllPsychPreviousBookings(context, psychID) {
		return apiClient.bookings.getAllPsychPreviousBookings(psychID)
	},

	async getAppointmentByID(context, bookingID) {
		return apiClient.bookings.getAppointmentByID(bookingID)
	},

	async addFocusPointToBooking(context, requestParams) {
		return apiClient.bookings.addFocusPointToBooking(requestParams)
	},

    async changeBookingStatus(context, requestParams) {
		return apiClient.bookings.changeBookingStatus(requestParams)
	},

    async saveVirtualSession(context, requestParams) {
		return apiClient.bookings.saveVirtualSession(requestParams)
	},

    async checkAnyBookings(context, psychID) {
		return apiClient.bookings.checkAnyBookings(psychID)
	},
}

export default {
	namespaced,
	state,
	getters,
	mutations,
	actions,
}