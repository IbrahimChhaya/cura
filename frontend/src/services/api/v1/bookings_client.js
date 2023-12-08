export default function BookingsClient(instance){
	this.apiInstance = instance

	this.getUpcomingBookings = (params) => {
		return this.apiInstance.get(`/Bookings/GetUpcomingBookings/${params.userID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.getBookingsPatients = (params) => {
		return this.apiInstance.post('/Bookings/getBookingsPatients', params.bookings).then((resp) => {
			//debugger
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.getBookingParent = (childID) => {
		return this.apiInstance.get(`/User/GetGuardian/${childID.userID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return {}
		})
	}

	this.getLastAppointment = (childID) => {
		return this.apiInstance.get(`/Bookings/GetLastAppointmentDate/${childID.userID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return {}
		})
	}

	this.getPsychBookings = (psychID) => {
		return this.apiInstance.get(`/Bookings/getPsychBookings/${psychID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.getPsychCalendar = (psychID) => {
		return this.apiInstance.get(`/Bookings/GetPsychCalendar/${psychID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.getNumberOfPastBookings = (userID) => {
		return this.apiInstance.get(`/Bookings/getNumberOfPastBookings/${userID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.getNumberOfUpcomingBookings = (userID) => {
		return this.apiInstance.get(`/Bookings/getNumberOfUpcomingBookings/${userID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.GetLastAppointmentDate = (userID) => {
		return this.apiInstance.get(`/Bookings/GetLastAppointmentDate/${userID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.getAllUpcomingBookings = (userID) => {
		return this.apiInstance.get(`/Bookings/getAllUpcomingBookings/${userID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.GetUserBookings = (userID) => {
		return this.apiInstance.get(`/Bookings/GetUserBookings/${userID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.getUserBookingsWithPsych = (params) => {
		return this.apiInstance.post('/Bookings/getUserBookingsWithPsych', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.getAllPsychPreviousBookings = (psychID) => {
		return this.apiInstance.get(`/Bookings/getAllPsychPreviousBookings/${psychID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.getAppointmentByID = (bookingID) => {
		return this.apiInstance.get(`/Bookings/getAppointmentByID/${bookingID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.addFocusPointToBooking = (params) => {
		return this.apiInstance.post('/Bookings/addFocusPointToBooking', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.changeBookingStatus = (params) => {
		return this.apiInstance.post('/Bookings/changeBookingStatus', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.saveVirtualSession = (params) => {
		return this.apiInstance.post('/Bookings/saveVirtualSession', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

    this.checkAnyBookings = (psychID) => {
		return this.apiInstance.get(`/Bookings/checkAnyBookings/${psychID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}
}