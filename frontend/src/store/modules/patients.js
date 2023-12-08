import apiClient from '../../services/apiClient'

const namespaced = true

const state = {
  currentPatients:   {},
  potentialPatients: [],
}

const getters = {
  currentPatients: state => {
    return state.currentPatients.patients || []
  },

  potentialPatients: state => {
    return state.potentialPatients || []
  },

  shouldFetchPatients: (state) => () => {
    const patientsList = state.currentPatients

    if (patientsList.patients == undefined) return true

    const ttlInMinutes = 5
    const timeNow      = new Date()
    const timeCaptured = new Date(patientsList.timeFetched)
    const ageOfData    = (timeNow.getMinutes() - timeCaptured.getMinutes())

    return ageOfData >= ttlInMinutes
  }
}

const mutations = {
  updateCurrentPatients(state, patientsList) {
    state.currentPatients = patientsList
  },

  updatePotentialPatients(state, patientsList) {
    state.potentialPatients = patientsList
  },
}

const actions = {
    
    async GetPsychologistPatients(context, psychID) {
		return apiClient.patients.GetPsychologistPatients(psychID)
	},

  async getPsychPatients(context, psychID){
    const isAlreadyStored = (context.getters.currentPatients.length > 0)
    const expired         = context.getters.shouldFetchPatients()

    if (!isAlreadyStored || expired) {
      const resp = await apiClient.patients.getPsychPatients(psychID)

      const payload = {
        timeFetched: new Date(),
        patients:    resp
      }
  
      context.commit('updateCurrentPatients', payload)
    }
  },

  async getPossiblePsychPatients(context, psychID) {
    const resp = await apiClient.patients.getPossiblePsychPatients(psychID)

    context.commit('updatePotentialPatients', resp)
  },

  async addPatient(context, { patient, psychID }) {
    const requestParams = {
      psychID:    psychID,
      childID:    patient.patientInfo.userID,
      guardianID: patient.guardian.userID
    }

    const resp = await apiClient.patients.addPatient(requestParams)

    if (resp != undefined && resp.psychID === psychID) {
      const current   = context.getters.currentPatients
      const potential = context.getters.potentialPatients

      current.unshift(patient)  //append newly added patient to the front of the currentPatients array
      const payload = {
        timeFetched: new Date(),
        patients:    current
      }
  
      context.commit('updateCurrentPatients', payload)

      //find index of patient that was just added
      const index = potential.findIndex((pat) => pat.patientInfo.userID === patient.patientInfo.userID)
      potential.splice(index, 1)  //remove patient that was added from potentialPatients array

      context.commit('updatePotentialPatients', potential)
    }
  },

  async removePatient(context, {patient, psychID}) {
    const requestParams = {
      psychID:    psychID,
      childID:    patient.patientInfo.userID,
      guardianID: patient.guardian.userID
  }
    const resp = await apiClient.patients.removePatient(requestParams)

    if(resp != undefined && resp.psychID === psychID) {
      const current = context.getters.currentPatients
      const potential = context.getters.potentialPatients

      //add the patient to the potential patients list
      potential.unshift(patient)
      context.commit('updatePotentialPatients', potential)

      //get index of patient that was removed
      const index = current.findIndex((pat) => pat.patientInfo.userID === patient.patientInfo.userID)
      current.splice(index, 1) //remove the patient from current patients list

      const payload = {
        timeFetched: new Date(),
        patients:    current
      }

      context.commit('updateCurrentPatients', payload)

    }

  },
}

export default {
  namespaced,
  state,
  getters,
  mutations,
  actions
}