<template>
<v-app>
	<div class="main-content">
		<!-- <div class="container-fluid mt-6"> -->
			<div class="row">
				<div class="col-xl-8">
					<div class="card" id="vstep1">
						<div class="card-header bg-transparent">
							<div class="row align-items-center">
								<div class="col">
									<h5 class="h3 mb-0">Upcoming Appointments</h5>
								</div>
								<div class="col">
									<div class="nav nav-pills justify-content-end">
										<button type="button" class="btn btn-primary btn-sm" @click="newRedirect('appointments', '')" :disabled="!analyticsAlert">See All</button>
									</div>
								</div>
							</div>
						</div>
						<div v-if="formattedBookings.length > 0" class="card-body" id="BookingDates">
							<div 
                            class="appointment-card cursorClick" 
                            v-for="booking in formattedBookings" 
                            :key="booking.bookingID" 
                            @click="redirect('/#/appointment?appointID=' + booking.bookingID + '&childID=' + booking.patient.userID)">
								<div class="circle-line">
									<div class="circle">
									</div>
								</div>

								<span class="text-nowrap">{{ formatBookingDate(booking) }}</span>
								<span class="grey-line"></span>
								<img :src="getUserAvatar(booking)" class="avatar rounded-circle">
								<span v-if="booking.patient">{{ booking.patient.name }}</span>
								<span class="grey-line"></span>

								<div class="consultation-type">
									<h6 class="text-uppercase text-muted ls-1 mb-1">Consultation Type</h6>
									{{ booking.type }}
								</div>
								<a class="note-btn"><i class="fas fa-file-medical"></i><span>Note</span></a>
							</div>
						</div>
                        <div v-else class="card-body" style="position: relative">
                            <div class="appointment-card" v-if="hasNoBookings && firstTime && !tourDone">
                            	<div class="circle-line">
									<div class="circle">
									</div>
								</div>
								<span class="text-nowrap">{{ defaultDate }}</span>
								<span class="grey-line"></span>
								<img src="../assets/img/child2.png" class="avatar rounded-circle">
								<span>Cathy Smith</span>
								<span class="grey-line"></span>

								<div class="consultation-type">
									<h6 class="text-uppercase text-muted ls-1 mb-1">Consultation Type</h6>
									Single
								</div>
								<span class="note-btn"><i class="fas fa-file-medical"></i><span>Note</span></span>
							</div>
                            <v-card 
                            class="justify-center mt-2"
                            flat
                            style="text-align: center;"
                            v-if="hasNoBookings && tourDone">
                                <span class="far fa-calendar-alt fa-5x" style="color: #FF9565"></span>
                                <v-card-title style="justify-content: center; color: #022F5B">Nothing here yet!</v-card-title>
                                <v-card-subtitle>Check back later to see if any appointments have been made</v-card-subtitle>
                            </v-card>
						</div>
					</div>

					<div class="card">
						<div class="card-header" id="vstep3">
							<div class="row align-items-center">
								<div class="col">
									<h3 class="mb-0">Analytics</h3>
								</div>
								<div class="col text-right">
									<!-- <a href="#!" class="btn btn-sm btn-primary" style="color: white;" disabled>See all</a> -->
									<button type="button" class="btn btn-primary btn-sm" @click="redirect('/#/reports')" :disabled="!analyticsAlert">See All</button>
								</div>
							</div>
						</div>
                        <div class="container">
                            {{ startDate + ' - ' + endDate}}
                            <div class="row">
                                <div class="col-xl-8">
                                    <span v-if="noData" class="newBlurText">No Data</span>
                                    <div id="chart">
                                        <apexchart type="donut" height="350" :options="chartOptions" :series="series"></apexchart>
                                    </div>                                
                                </div>

                                <div class="col-xl-4" style="align-self: center;">
                                    <div class="row mb-5">
                                        <v-card
                                        color="#ff9090"
                                        dark
                                        width="50"
                                        height="50"
                                        style="border-radius: 15px">
                                            <v-card-text class="text-center cardStatText">{{ numNewPatients }}</v-card-text>
                                        </v-card>
                                        <div class="ml-2">
                                            <div>New Patients</div>
                                            <div
                                            class="percentStat"
                                            :style="percNewPatients.substring(0, 1) == '-' ? 'color: red;' : percNewPatients.substring(0, 1) == '+' ? 'color: limegreen;' : '' ">
                                                {{ percNewPatients }}
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row mt-5">
                                        <v-card
                                        color="#c56eff"
                                        dark
                                        width="50"
                                        height="50"
                                        style="border-radius: 15px">
                                            <v-card-text class="text-center cardStatText">{{ numNewBookings }}</v-card-text>
                                        </v-card>
                                        <div class="ml-2">
                                            <div>New Appointments</div>
                                            <div
                                            class="percentStat"
                                            :style="percNewBookings.substring(0, 1) == '-' ? 'color: red;' : percNewBookings.substring(0, 1) == '+' ? 'color: limegreen;' : '' ">
                                                {{ percNewBookings }}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
					</div>
				</div>

				<div class="col-xl-4 float-left">
					<div class="card" id="vstep2">
						<div class="card-header bg-transparent">
							<div class="row align-items-center">
                                <div class="col">
                                    <h5 class="h3 mb-0">Next Patient</h5>
                                </div>
                                <div class="col">
                                    <div class="nav nav-pills justify-content-end">
                                        <button type="button" class="btn btn-primary btn-sm" @click="newRedirect('profile', nextPatient.userID)" :disabled="!analyticsAlert">View Profile</button>
                                    </div>
                                </div>
							</div>
						</div>
						<div class="card-body">
							<div class="patient">
								<div class="nextProfile" v-if="!tourDone || !hasNoBookings">
									<div id="nextImage">
										<img v-if="nextBooking" :src="getUserAvatar(nextBooking)" alt="" class="avatar rounded-circle" style="width: 60px; height: 60px;">
                                        <img v-else src="../assets/img/child2.png" alt="" class="avatar rounded-circle" style="width: 60px; height: 60px;">
									</div>
									<div style="padding: 10px">
										<h3 v-if="nextPatient" id="nextPatientName">{{nextPatient.name}}</h3>
                                        <h3 v-else>Cathy Smith</h3>
									</div>
								</div>
                                
                                <div v-if="!tourDone || !hasNoBookings">
                                    <div v-if="nextPatient" class="patient-body" id="nextPatient">
                                        <div class="details">
                                            <strong>Age</strong>
                                            <br/>
                                            <span class="next-patient-details">{{ age }}</span>
                                        </div>
                                        <div class="details">
                                            <strong>Grade</strong>
                                            <br/>
                                            <span class="next-patient-details">{{ nextPatient.grade }}</span>
                                        </div>
                                        <div class="details">
                                            <strong>Guardian</strong>
                                            <br/>
                                            <span v-if="nextPatientParent" class="next-patient-details">{{ nextPatientParent.name || 'Unknown' }}</span>
                                        </div>
                                        <div class="details">
                                            <strong>Date Registered</strong>
                                            <br/>
                                            <span  v-if="nextPatient" class="next-patient-details">{{ formatDate(nextPatient.dateRegistered)  }}</span>
                                        </div>
                                        <div class="details">
                                            <strong>Last Appointment</strong>
                                            <br/>
                                            <span  v-if="nextPatientPrevBooking" class="next-patient-details">{{ formatDate(nextPatientPrevBooking) }}</span>
                                        </div>
                                    </div>

                                    <div v-else class="patient-body">
                                        <div class="details">
                                            <strong>Age</strong>
                                            <br/>
                                            <span class="next-patient-details">10</span>
                                        </div>
                                        <div class="details">
                                            <strong>Grade</strong>
                                            <br/>
                                            <span class="next-patient-details">5</span>
                                        </div>
                                        <div class="details">
                                            <strong>Guardian</strong>
                                            <br/>
                                            <span class="next-patient-details">Abdullah Smith</span>
                                        </div>
                                        <div class="details">
                                            <strong>Date Registered</strong>
                                            <br/>
                                            <span class="next-patient-details">13 October 2021</span>
                                        </div>
                                        <div class="details">
                                            <strong>Last Appointment</strong>
                                            <br/>
                                            <span class="next-patient-details">16 October 2021</span>
                                        </div>
                                    </div>
                                </div>

                                <v-card 
                                class="justify-center mt-2"
                                flat
                                style="text-align: center;"
                                v-if="nextPatient == null && tourDone">
                                    <span class="fas fa-users fa-5x" style="color: #FF9565"></span>
                                    <v-card-title style="justify-content: center; color: #022F5B">Nothing here yet!</v-card-title>
                                    <v-card-subtitle>Check back later to see if any appointments have been made</v-card-subtitle>
                                </v-card>

                                <div class="keyword-group" style="text-align: center" >
                                    <span class="btn-keyword"
                                        v-for="(point, i) in nextPatientFocusPoints" 
                                        :key="point.id"
                                        :class="'keyword-' + (i + 1)">
                                            {{point.problem}}
                                    </span>

                                    <!-- <span class="btn-keyword keyword-2">Anxiety</span>
                                    <span class="btn-keyword keyword-3">ADHD</span>
                                    <span class="btn-keyword keyword-4">Bullying</span> -->
                                </div>

								<div class="keyword-group orange-buttons" style="text-align: center" v-if="!tourDone || !hasNoBookings">
									<button type="button" class="btn btn-primary" disabled><span class="fas fa-phone-alt"></span>  Contact Guardian</button>
									<button type="button" class="btn btn-outline-primary" disabled><span class="far fa-file-alt"></span>  Documents</button>
									<button type="button" class="btn btn-outline-primary" :disabled="nextPatient == null" @click="chatRedirect(nextPatient.userID)"><span class="far fa-comment-alt"></span>  Chat</button>
								</div>
							</div>
						</div>
					</div>
					<div class="card" id="vstep4">
						<div class="card-header">
							<div class="row align-items-center">
								<div class="col">
									<h3 class="mb-0">Chats</h3>
								</div>
								<div class="col text-right">
									<button type="button" class="btn btn-primary btn-sm" disabled hidden>See All</button>
								</div>
							</div>
						</div>

						<div class="table-responsive stop-scroll">
							<table class="table align-items-center table-flush">
								<tbody v-if="chatList.length != 0">
									<tr v-for="item in chatList" :key="item.id" class="cursorClick">
										<td class="td" @click="newRedirect('PsychologistChat', item.chatterPerson.userID)">
											<div class="chat-text">
												<img alt="Image placeholder" :src="getUserPfp(item.chatterPerson)" class="avatar rounded-circle">
												<div class="name-chat">
													<strong class="chatName">{{ item.chatterPerson.name }}</strong>
													<span class="messageDate">{{ formatChatListDate(item.lastMessage.date) }}</span>
													<div class="crop">
														<span class="chat-message">{{ item.lastMessage.message }}</span>
													</div>
													<span class="badge mbadge" hidden>3</span>
												</div>
											</div>
										</td>
									</tr>
								</tbody>
                                <!-- <tbody v-else>
									<tr>
										<td class="td">
											<div class="chat-text">
												<img alt="Image placeholder" src="../assets/img/child2.png" class="avatar rounded-circle">
												<div class="name-chat">
													<strong class="chatName">Cathy Smith</strong>
													<span class="messageDate">2021/10/14</span>
													<div class="crop">
														<span class="chat-message">Recent chats will be here</span>
													</div>
													<span class="badge mbadge" hidden>3</span>
												</div>
											</div>
										</td>
									</tr>
                                </tbody> -->
                                <v-card 
                                v-else 
                                class="justify-center mt-2"
                                flat
                                style="text-align: center">
                                    <span class="far fa-comment-alt fa-5x" style="color: #FF9565"></span>
                                    <v-card-title style="justify-content: center; color: #022F5B">Nothing here yet!</v-card-title>
                                    <v-card-subtitle>Check back later to see if anyone has messaged</v-card-subtitle>
                                </v-card>
							</table>
						</div>
					</div>
				</div>
			</div>
      <v-tour name="myTour" :steps="steps" :callbacks="myCallbacks"></v-tour>
	</div>
</v-app>
</template>

<script>
import moment from 'moment'

export default {
        name: 'my-tour',

	data() {
		return {
      startDate:       '13 August',
      endDate:         '13 September 2021',
      numNewPatients:  0,
      percNewPatients: '0%',
      numNewBookings:  0,
      percNewBookings: '0%',

			bookings:               [],
			bookingsPatients:       [],
			formattedBookings:      [],
			nextBooking:            null,
			nextPatient:            null,
			nextPatientParent:      null,
			nextPatientPrevBooking: null,

      colors:   ["#2196F3", "#fb6340", "#11cdef", "#2dce89", "#f5365c"],

      attendedMeetings:
      [
        {
          weekStartDate: '2021-09-12',
          count: 0,
        }
      ],

      series: [],
      chartOptions: {
          chart: 
          {
            type: 'donut',
            width: 380,
            toolbar: {
              show: false,
            },
          },
          labels: ['Attended', 'Cancelled', 'Missed'],
          responsive: [{
            breakpoint: 480,
            options: {
              chart: {
                width: 200
              },
              legend: {
                position: 'bottom'
              }
            }
          }],
          legend: {
            position: 'bottom'
          },
          tooltip: {
            enabled: true,
          }, 
          colors:['#FF9565', '#11D6FF', '#F44165'],
          noData: {
            text: 'No Data',
          }
      },

      colors:   ["#2196F3", "#fb6340", "#11cdef", "#2dce89", "#f5365c"],

			chatList: [],
            defaultDate: '',
            analyticsAlert: false,

            steps: 
            [
                {
                    target: '#vstep1',  // We're using document.querySelector() under the hood
                    content: `Here's where you'll be able to see your upcoming appointments`,
                    params: {
                        placement: 'right',
                    }
                },
                {
                    target: '#vstep2',
                    content: `Here you'll be able to see details on the next patient`,
                    params: {
                        placement: 'left'
                    }
                },
                {
                    target: '#vstep3',
                    content: 'A general overview on analytics can be found here',
                    params: {
                        placement: 'top'
                    }
                },
                {
                    target: '#vstep4',
                    content: 'Recent chats will be down here',
                    params: {
                        placement: 'top'
                    }
                }
            ],
            firstTime: true,
            hasNoBookings: true,
            tourDone: false,
            myCallbacks: {
                onSkip: this.skipTour,
                onFinish: this.endTour,
                //onNextStep: this.otherPageTours,
            },
            noData: false,
		}
	},

	computed: {
		nextPatientFocusPoints(){
			return this.$store.getters['hub/childFocusPoints']
		},

		age() {
			if(this.nextPatient.dob == null) return 'Not Specified'

			return moment().diff(moment(this.nextPatient.dob), 'years')
		},
	},

	async mounted() {
        
		const user = JSON.parse(window.localStorage.getItem('currentUser'))

		await this.getUpcomingBookings(user.userID)
		await this.getBookingsPatients(this.bookings)
		
        if(this.nextPatient != null)
        {
            await this.getNextPatientParent(this.nextPatient.userID)
            await this.getNextPatientPrevBooking(this.nextPatient.userID)
            await this.getNextPatientFocusPoints(this.nextPatient.userID)
        }   
		await this.getNumberOfNewPatientsThisMonth(user.userID)
		//await this.getPatientPercentIncrease(user.userID)
		await this.getNumBookingsThisMonth(user.userID)
		await this.getBookingsPercentIncrease(user.userID)
        await this.checkAnyBookings(user.userID)

		await this.getPsychChatList(user.userID)

        this.defaultDate = moment().add(1, 'hours').format('DD MMM YYYY HH:00')
        if(this.analyticsAlert)
            await this.getNumEachBookingPerPsych(user.userID)

        if(!this.analyticsAlert)
        {
            this.startDate = moment().subtract(30, 'days').format('DD MMMM')
            this.endDate = moment().format('DD MMMM YYYY')
        }


        if(this.firstTime)
            this.percNewPatients = 'No Change'

        this.checkCookie(user.userID)

        
        if(this.series[0] == 0 && this.series[1] == 0 && this.series[2] == 0)
            this.noData = true
	},

	methods: {

        checkCookie(psychID)
        {
            if(localStorage.getItem('firstTime' + psychID) != psychID) 
            {
                if(this.hasNoBookings)
                {
                    this.tourDone = false
                    this.firstTime = true
                    //this.series = []
                    this.series.push(1)
                    this.series.push(1)
                    this.series.push(1)
                    this.$tours['myTour'].start()
                }
                localStorage.setItem('firstTime' + psychID, psychID)
            }
            else
            {
                this.firstTime = false
                this.tourDone = true
            }
        },

        async checkAnyBookings(psychID)
        {
            this.hasNoBookings = !(await this.$store.dispatch('bookings/checkAnyBookings', psychID))
            this.analyticsAlert = await this.$store.dispatch('bookings/checkAnyBookings', psychID)
        },

        skipTour()
        {
            this.tourDone = true
            this.series = []
            this.series.push(0)
            this.series.push(0)
            this.series.push(0) 
        },

        endTour()
        {
            this.skipTour()
            this.redirect('/#/calendar?tour=true')
        },

        redirect: function(link) {
            window.location.href = link;
        },

      async getNumBookingsThisMonth(psychID)
      {
        const requestParams = {
          dateNow: "1999-06-15",
          psychID: psychID
        }
        this.numNewBookings = await this.$store.dispatch('reports/getNumBookingsThisMonth', requestParams)
      },

      async getBookingsPercentIncrease(psychID)
      {
        var percNewBookings = await this.$store.dispatch('reports/getBookingsPercentIncrease', psychID)
        
        if(percNewBookings > 0)
        {
          this.percNewBookings = '+' + percNewBookings + '%'
        }
        else if(percNewBookings == 0)
        {
          this.percNewBookings = 'No Change'
        }
        else
          this.percNewBookings = percNewBookings + '%'
      },


      async getNumEachBookingPerPsych(psychID)
      {
        const resp = await this.$store.dispatch('reports/getNumEachBookingPerPsych', psychID)
        
        this.series.push(resp.attended)
        this.series.push(resp.cancelled)
        this.series.push(resp.missed)
        this.endDate = moment(resp.startDate).format('DD MMMM YYYY')
        this.startDate = moment(resp.endDate).format('DD MMMM')
      },

		newRedirect(page, paramValue) {
			if(paramValue != undefined)
			{
				this.$router.push({ path: page, query: { id: paramValue } })
			}
		},

		async chatRedirect(childID){
			await this.$store.dispatch('chat/setChattingChild', childID)

			this.$router.push({path: 'PsychologistChat'})

		},

		async getUpcomingBookings(param) {
			const requestParams = {
				userID: param
			}

		const tempBookingsList = await this.$store.dispatch('bookings/getUpcomingBookings', requestParams)

		if(tempBookingsList.length > 1){
				this.bookings = tempBookingsList.slice(0, 4)
			}
			else{
				this.bookings = tempBookingsList
			}


			this.nextBooking = this.bookings[0]
		},

		async getBookingsPatients(bookingsList) {
			const requestParam = {
				bookings: bookingsList
			}

			this.bookingsPatients = await this.$store.dispatch('bookings/getBookingsPatients', requestParam)
            
            if(this.bookingsPatients.length != 0)
            {
                this.nextPatient      = this.bookingsPatients[0]
                if(this.nextPatient.grade == null)
                    this.nextPatient.grade = "Not Specified"
                
                this.popualteUpcomingBookings()
            }
		},

		async getNextPatientParent(patientID) {
			const patientIDParam = {
				userID: patientID
			}

			this.nextPatientParent = await this.$store.dispatch('bookings/getBookingParent', patientIDParam)
		},

		async getNextPatientPrevBooking(childID) {
			const requestParam = {
				userID: childID
			}

			this.nextPatientPrevBooking = await this.$store.dispatch('bookings/getLastAppointment', requestParam)
		},

		async getNextPatientFocusPoints(childID){
			await this.$store.dispatch('hub/fetchChildFocusPoints', childID)
		},

		async getPsychChatList(userID) {
			const requestParam = {
				psychID: userID
			}

			const tempChatList = await this.$store.dispatch('chat/getPsychHomeChatList', requestParam)

			if(tempChatList.length > 1){
				this.chatList = tempChatList.slice(0, 5)
			}
			else{
				this.chatList = tempChatList
			}

		},

		popualteUpcomingBookings() {
			this.formattedBookings = this.bookings.map((booking, index) => {
				booking.patient = this.bookingsPatients[index]

				return booking
			})
		},

		getUserAvatar(booking) {
			if(booking == null || booking.patient == null) return ''

			const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)

			return images(`./${booking.patient.profilePicture}`)
		},

		formatBookingDate(booking) {
			return moment(booking.date).hour(booking.time.hours).minutes(booking.time.minutes).format('DD MMM YYYY HH:mm')
		},

		getUserPfp(user){
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

    async getNumberOfNewPatientsThisMonth(psychID)
    {
      this.numNewPatients = await this.$store.dispatch('reports/getNumberOfNewPatientsThisMonth', psychID)
    },

    async getPatientPercentIncrease(psychID)
    {
      var percNewPatients = await this.$store.dispatch('reports/getPatientPercentIncrease', psychID)
      
      if(percNewPatients > 0)
      {
        this.percNewPatients = '+' + percNewPatients + '%'
      }
      else if(percNewPatients == 0)
      {
        this.percNewPatients = 'No Change'
      }
      else
        this.percNewPatients = percNewPatients + '%'
    },

		formatChatListDate(date){
      const currentDate  = moment().format('DD MMMM yyyy')
      const messsageDate = moment(date).format('DD MMMM yyyy')

			if(currentDate == messsageDate){
        return moment(date).format('HH:mm')
      }
      else{
        return moment(date).format('yyyy/MM/DD')
      }
    },

	},

}
</script>

<style scoped>

    .cursorClick
    {
        cursor: pointer;
    }

    .cardStatText
    {
        color: white !important;
        font-size: 16px;
    }

    .percentStat
    {
        font-size: medium;
    }

	.avatar {
    font-size: 1rem;
    display: -webkit-inline-box;
    display: -ms-inline-flexbox;
    display: inline-flex;
    width: 48px;
    height: 48px;
    color: #fff;
    border-radius: .375rem;
    background-color: #adb5bd;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    height: 48px;
	}

	.td {
		padding-bottom: 0px;
	}

	.chatName {
		font-size: medium;
	}

	.crop {
		width:         100%;
		overflow:      hidden;
		text-overflow: ellipsis;
		padding-top:   5px;
	}

	.messageDate {
		float:       right;
		font-size:   x-small;
		padding-top: 7px;
	}

	.name-chat {
		width:        100%;
		padding-left: 10px;
	}

    .v-application--wrap {
        min-height: 0;
    }

    .theme--light.v-application {
        background: #FBF5F2;
    }

    .v-application {
        min-height: 0;
    }

    .centerAlert
    {
        text-align: -webkit-center; 
        text-align: -moz-center;
    }

    .newBlur
    {
        filter: blur(8px);
    }

    .newBlurText
    {
        position: absolute;
        top: 50%; left: 50%;
        transform: translate(-50%,-50%);
    }

</style>
