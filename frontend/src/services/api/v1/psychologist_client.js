export default function PsychologistClient(instance){
	this.apiInstance = instance

	this.getPsychologistCalendars = (params) => {
		return this.apiInstance.get(`/Bookings/getPsychologistCalendars/${params.userID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.updatePsychCalendar = (params) => {
		return this.apiInstance.post('/Psychologist/updatePsychCalendar', params).then((resp) =>{
			
			return resp.data
		}).catch((error) => {
			return {}
		})
	}
}
