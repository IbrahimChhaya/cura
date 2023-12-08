export default function ActivityClient(instance){
	this.apiInstance = instance

    this.getAllChildPsychActivities = (params) => {
		return this.apiInstance.post('/Activity/getAllChildPsychActivities', params).then((resp) => {
			return resp.data
		}).catch((error) => {
			return []
		})
	}
}