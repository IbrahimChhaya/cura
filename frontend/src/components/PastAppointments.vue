<template>
    <div>
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-header border-0">
                        <div class="row align-items-center">
                            <div class="col">
                                <h3 class="mb-0">Past Appointment List</h3>
                            </div>
                            <div class="col">
                                <div class="nav nav-pills justify-content-end">
                                    <button type="button" class="btn btn-primary btn-sm" style="color:white;" @click="redirect('/#/editresource?ID=' + problemID)">View All Appointments</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="table-responsive">
                        <table class="table align-items-center table-flush">
                            <thead class="thead-light">
                                <tr>
                                    <th scope="col" class="sort" data-sort="name">Date</th>
                                    <th scope="col" class="sort" data-sort="budget">Time</th>
                                    <th scope="col" class="sort" data-sort="status">Child</th>
                                    <th scope="col" class="sort" data-sort="completion">Focus</th>
                                    <th scope="col" class="sort"></th>
                                </tr>
                            </thead>
                            <tbody class="list" id="patientlist">
                                <tr v-for="booking in pastBookings"
                                :key="booking.bookingID"
                                @click="redirect('/#/appointment?appointID=' + booking.bookingID + '&childID=' + booking.childID)"
                                class="cursorClick">
                                    <th scope="row">
                                        {{ booking.date }}
                                    </th>
                                    <td class="budget">
                                        {{ booking.time }}
                                    </td>
                                    <td class="budget">
                                        <div class="media align-items-center">
                                            <router-link class="avatar rounded-circle mr-3" :to="{name: 'Profile', query: {id: booking.childID }}">
                                                <img :src="booking.profilePicture" class="avatar rounded-circle">
                                            </router-link>
                                            <div class="media-body">
                                                <router-link :to="{name: 'Profile', query: {id: booking.childID }}">
                                                    <span class="name mb-0 text-sm" style="color: #32325d">{{ booking.child }}</span>
                                                </router-link>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <div class="keyword-group profile-keywords">
                                                <span
                                                v-if="booking.focus == 'Not Specified'" 
                                                class="btn-keyword" >
                                                    {{ booking.focus }}
                                                </span>
                                                <span v-else class="btn-keyword keyword-1">
                                                    {{ booking.focus }}
                                                </span>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
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
    </div>
</template>

<script>
import moment from 'moment'

export default {
    data: () => ({
        pastBookings: [],
    }),

    computed: {
        psych(){
            return JSON.parse(localStorage.getItem('currentUser'))
        },
    },

    async mounted() {
        await this.getAllPsychPreviousBookings()
    },

    methods: {
        redirect: function(link) {
            window.location.href = link;
        },

        async getPsychPastBookings() {

        },

        async GetPairChild(pairID) {
            const user = await this.$store.dispatch('users/GetPairChild', pairID)
            var child = {
                name: user.name,
                id: user.userID
            }
            return child
        },

        async getRHubProblem(problemID) {
            const problem = await this.$store.dispatch('hub/getRHubProblem', problemID)
            var name = problem.problem
            
            return name
        },

        async getUserAvatar(userID) {
			const user = await this.$store.dispatch('users/getUserById', userID)
            
            const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)

			return images(`./${user.profilePicture}`)
		},

        async getAllPsychPreviousBookings()
        {
            var intPsychID = parseInt(this.psych.userID)
            const bookings = await this.$store.dispatch('bookings/getAllPsychPreviousBookings', intPsychID)
            bookings.forEach(async (booking) => {

                var focus = ''
                if(booking.problemID == null)
                {
                    focus = 'Not Specified'
                }
                else
                {
                    focus = await this.getRHubProblem(booking.problemID)
                }
                var child = await this.GetPairChild(booking.pairID)
                var temp = {
                    bookingID: booking.bookingID,
                    date: moment(booking.date.substring(0, 10)).format("DD MMMM yyyy"),
                    time: moment(booking.date).hour(booking.time.hours).minutes(booking.time.minutes).format('HH:mm'),
                    child: child.name,
                    childID: child.id,
                    focus: focus,
                    profilePicture: await this.getUserAvatar(child.id)
                }

                this.pastBookings.unshift(temp)
                this.sortArray(this.pastBookings)
            });
        },

        sortArray(array) {
            var sortedArray = array.sort((a,b) => new moment(b.date).format('YYYYMMDD') - new moment(a.date).format('YYYYMMDD'))
            sortedArray.forEach(sArray => {
                sArray.date = moment(sArray.date).format('DD MMMM yyyy')
            })
            return sortedArray
        },
    },
}
</script>

<style scoped>
    .cursorClick {
        cursor: pointer;
    }
</style>