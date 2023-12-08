export default function PatientsClient(instance) {
  this.apiInstance = instance

  this.getPsychPatients = (params) => {
    return this.apiInstance.get(`/Patient/getPsychPatients/${params.psychID}`).then((resp) => {
      return resp.data
    }).catch((error) => {
      return []
    })
  }

  this.getPossiblePsychPatients = (param) => {
    return this.apiInstance.get(`/Patient/findPotentialPatients/${param.psychID}`).then((resp) => {
      return resp.data
    }).catch((erorr) => {
      return []
    })
  }

  this.addPatient = (params) => {
    return this.apiInstance.post('/Patient/addPatient', params).then((resp) => {
      return resp.data
    }).catch((error) => {
      return {}
    })
  }

  this.removePatient = (params) => {
    return this.apiInstance.post('/Patient/removePatient', params).then((resp) => {
      return resp.data
    }).catch((error) => {
      return {}
    })
  }

    this.GetPsychologistPatients = (psychID) => {
        return this.apiInstance.get(`/Patient/GetPsychologistPatients/${psychID}`).then((resp) => {
            return resp.data
        }).catch((erorr) => {
            return []
        })
    }
}

