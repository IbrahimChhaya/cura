export default function NotesClient(instance){
	this.apiInstance = instance

    this.fetchAllChildsNotes = (params) => {
		return this.apiInstance.post('/Notes/fetchAllChildsNotes', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}

	this.saveNote = (params) => {
		return this.apiInstance.post('/Notes/saveNote', params).then((resp) =>{
			return resp.data
		}).catch((error) => {
			return {}
		})
	}

	this.getNote = (noteID) => {
		return this.apiInstance.get(`/notes/getNote/${noteID}`).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}
}