<template>
    <div>
        <div class="container-fluid mt-2">
            <div class="row">
                <div class="col">
                <div class="card">
                    <!-- Card header -->
                    <div class="card-header border-0">
                    <h3 class="mb-0">Patient List</h3>
                    </div>
                    <!-- Light table -->
                    <div class="table-responsive" v-if="hasBookings">
                    <table class="table align-items-center table-flush">
                        <thead class="thead-light">
                        <tr>
                            <th scope="col" class="sort" data-sort="name">Child</th>
                            <th scope="col" class="sort" data-sort="budget">Last Appointment</th>
                            <th scope="col" class="sort" data-sort="status">Next Appointment</th>
                            <th scope="col">Guardian</th>
                            <th scope="col" class="sort" data-sort="completion">Focus</th>
                            <!-- <th scope="col"></th> -->
                        </tr>
                        </thead>
                        <tbody class="list" id="patientlist">
                        <tr v-for="patient in psychPatients" :key="patient.patientInfo.userID" class="cursorClick">
                            <th scope="row">
                            <div class="media align-items-center">
                                <router-link class="avatar rounded-circle mr-3" :to="{name: 'Profile', query: {id: patient.patientInfo.userID}}">
                                    <img :src="getUserAvatar(patient.patientInfo)" class="avatar rounded-circle">
                                </router-link>
                                <!-- <a href="#" class="avatar rounded-circle mr-3">
                                    <img :src="getUserAvatar(patient.patientInfo)" class="avatar rounded-circle">
                                </a> -->
                                <div class="media-body">
                                    <router-link :to="{name: 'Profile', query: {id: patient.patientInfo.userID}}">
                                        <span class="name mb-0 text-sm" style="color: #32325d">{{ patient.patientInfo.name }}</span>
                                    </router-link>
                                <!-- <span class="name mb-0 text-sm">{{ patient.patientInfo.name }}</span> -->
                                </div>
                            </div>
                            </th>
                            <td class="budget">
                            {{ formatDate(patient.lastAppointment) }}
                            </td>
                            <td class="budget">
                            {{ formatDate(patient.nextAppointment) }}
                            </td>
                            <td>
                            <a href="#" class="avatar rounded-circle mr-3" data-toggle="tooltip" data-original-title="Tenille Drake">
                                <img :src="getUserAvatar(patient.guardian)" alt="Image placeholder" class="avatar rounded-circle">
                                </a>
                            </td>
                            <td>
                            <div class="d-flex align-items-center">
                                <div class="keyword-group profile-keywords" v-if="patient.focusPoints.length">
                                    <span class="btn-keyword"
                                     :class="'keyword-' + getPointColour(i + 1)" 
                                     v-for="(point, i) in patient.focusPoints" 
                                     :key="point.id">
                                        {{point.substring(0,1)}}
                                     </span>

                                </div>
                                <div class="keyword-group profile-keywords" v-if="!patient.focusPoints.length">
                                    <span class="btn-keyword">Not Specified</span>
                                </div>
                            </div>
                            </td>
                            <!-- <td> -->
                            <!-- <div class="dropdown">
                                <a class="btn btn-sm btn-icon-only text-light" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="fas fa-ellipsis-v"></i>
                                </a>
                                <div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow">
                                <a class="dropdown-item" href="#">Action</a>
                                <a class="dropdown-item" href="#">Another action</a>
                                <a class="dropdown-item" href="#">Something else here</a>
                                </div>
                            </div> -->
                            <!--<button class="btn btn-primary"
                                @click="removePatient(patient)"
                            >Remove Patient</button>
                            </td>-->
                        </tr>
                        </tbody>
                    </table>
                    </div>

                    <div class="card-body" v-else style="text-align: center;">
                        <div>
                            <span class="fas fa-users fa-5x" style="color: #FF9565"></span>
                        </div>
                        <v-card-title style="justify-content: center; color: #022F5B">Nothing here yet!</v-card-title>
                        <v-card-subtitle>Once you start getting patients, you'll be able to see them here</v-card-subtitle>
                    </div>

                    <!-- Card footer -->
                    <div class="card-footer py-4" v-if="hasBookings">
                    <nav aria-label="...">
                        <ul class="pagination justify-content-end mb-0">
                        <li class="page-item disabled">
                            <a class="page-link" href="#" tabindex="-1">
                            <i class="fas fa-angle-left"></i>
                            <span class="sr-only">Previous</span>
                            </a>
                        </li>
                        <li class="page-item active">
                            <a class="page-link" href="#">1</a>
                        </li>
                        <!--<li class="page-item">
                            <a class="page-link" href="#">2 <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>-->
                        <li class="page-item disabled">
                            <a class="page-link" href="#">
                            <i class="fas fa-angle-right"></i>
                            <span class="sr-only">Next</span>
                            </a>
                        </li>
                        </ul>
                    </nav>
                    </div>
                </div>
                </div>
            </div>

            <!-- <div class="row">
                <div class="col">
                    <div class="card">
                    <div class="card-header border-0">
                    <h3 class="mb-0">Potential Patients List</h3>
                    </div>

                    <div class="table-responsive">
                        <table class="table align-items-center table-flush">
                        <thead class="thead-light">
                        <tr>
                            <th scope="col" class="sort" data-sort="name">Child</th>
                            <th scope="col" class="sort" data-sort="budget">Last Appointment</th>
                            <th scope="col" class="sort" data-sort="status">Next Appointment</th>
                            <th scope="col">Guardian</th>
                            <th scope="col" class="sort" data-sort="completion">Focus</th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <tbody class="list" id="patientlist">
                        <tr v-for="patient in possiblePsychPatients" :key="patient.patientInfo.userID">
                            <th scope="row">
                            <div class="media align-items-center">
                                <router-link class="avatar rounded-circle mr-3" :to="{name: 'Profile', query: {id: patient.patientInfo.userID}}">
                                    <img :src="getUserAvatar(patient.patientInfo)" class="avatar rounded-circle">
                                </router-link>

                                <div class="media-body">
                                    <router-link :to="{name: 'Profile', query: {id: patient.patientInfo.userID}}">
                                        <span class="name mb-0 text-sm" style="color: #32325d">{{ patient.patientInfo.name }}</span>
                                    </router-link>
                                </div>
                            </div>
                            </th>
                            <td class="budget">
                            {{ formatDate(patient.lastAppointment) }}
                            </td>
                            <td class="budget">
                            {{ formatDate(patient.nextAppointment) }}
                            </td>
                            <td>
                            <a href="#" class="avatar rounded-circle mr-3" data-toggle="tooltip" data-original-title="Tenille Drake">
                                <img :src="getUserAvatar(patient.guardian)" alt="Image placeholder" class="avatar rounded-circle">
                                </a>
                            </td>
                            <td>
                            <div class="d-flex align-items-center">
                                <div class="keyword-group profile-keywords" v-if="patient.focusPoints.length">
                                    <span class="btn-keyword"
                                     :class="'keyword-' + getPointColour(i + 1)" 
                                     v-for="(point, i) in patient.focusPoints" 
                                     :key="point.id">
                                        {{point.substring(0,1)}}
                                     </span>

                                </div>
                                <div class="keyword-group profile-keywords" v-if="!patient.focusPoints.length">
                                    <span class="btn-keyword">Not Specified</span>
                                </div>
                            </div>
                            </td>
                            <td>
                                <button class="btn btn-primary" 
                                @click="addPatient(patient)"
                                >Add Patient</button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    </div>
                    

                    <div class="card-footer py-4">
                    <nav aria-label="...">
                        <ul class="pagination justify-content-end mb-0">
                        <li class="page-item disabled">
                            <a class="page-link" href="#" tabindex="-1">
                            <i class="fas fa-angle-left"></i>
                            <span class="sr-only">Previous</span>
                            </a>
                        </li>
                        <li class="page-item active">
                            <a class="page-link" href="#">1</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="#">2 <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item disabled">
                            <a class="page-link" href="#">
                            <i class="fas fa-angle-right"></i>
                            <span class="sr-only">Next</span>
                            </a>
                        </li>
                        </ul>
                    </nav>
                    </div>
                </div>
                </div>
                
            </div> -->
        </div>

    </div>
</template>

<script>
import moment from 'moment'

export default {

    data: () => ({
        hasBookings: true,
    }),

    computed: {
        psych(){
            return JSON.parse(localStorage.getItem('currentUser'))
        },

        psychPatients() {
            return this.$store.getters['patients/currentPatients']
        },

        possiblePsychPatients() {
            return this.$store.getters['patients/potentialPatients']
        },
    },

    async mounted() {
        await this.checkAnyBookings()
        await this.getPsychPatients()
        await this.getPossiblePsychPatients()
    },

    methods: {

        async getPsychPatients() {
            const requestParam = {
                psychID: this.psych.userID
            }

            await this.$store.dispatch('patients/getPsychPatients', requestParam)
        },

        async getPossiblePsychPatients() {
            const requestParams = {
                psychID: this.psych.userID
            }

            await this.$store.dispatch('patients/getPossiblePsychPatients', requestParams)
        },

        async addPatient(patient) {
            await this.$store.dispatch('patients/addPatient', { patient: patient, psychID: this.psych.userID })
        },

        async removePatient(patient) {
            await this.$store.dispatch('patients/removePatient', { patient: patient, psychID: this.psych.userID})
        },

        getUserAvatar(user) {
			const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)

			return images(`./${user.profilePicture}`)
		},

        formatDate(date) {
            var minDate = moment.utc("0001-01-01").format('DD MMMM yyyy')
            
            let formattedDate = moment(date).format('DD MMMM yyyy')
           
            if(minDate === formattedDate){
                return 'N/A' 
            } 
            else
                return formattedDate
        },

        formatPatientFocusPoints(points){
            if(points == null){
                return 'Not Specified'
            }
            else{
                return points.map((point) => {
                    point = point.substring(0,1)
                })
            }
        },

        getPointColour(index){
            return index % 5
        },

        async checkAnyBookings()
        {
            var psychID = parseInt(this.psych.userID)
            this.hasBookings = await this.$store.dispatch('bookings/checkAnyBookings', psychID)
        },
    },
}
</script>

<style scoped>
    
    .cursorClick
    {
        cursor: pointer;
    }

    .mt-6
    {
        margin-top: 2.5rem !important;
    }

</style>