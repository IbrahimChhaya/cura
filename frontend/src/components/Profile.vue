<template>
    <div>
        <div class="row">
            <div class="col-xl-8">
                <div class="card">
                    <div class="card-body">    
                        <div class="patient-overview">
                            <div class="left-group">
                                <div class="nextProfile">
                                    <img :src="childPP" alt="" class="avatar rounded-circle" style="width: 60px; height: 60px;">                                    
                                    <div style="padding: 10px;">
                                        <h3 style="color: white;">{{ childName }}</h3>
                                    </div>
                                </div>

                                <div class="app-details">
                                    <div class="app-upcoming">
                                        <div class="app-number">
                                            <h1 style="color: white">{{ numUpcoming }}</h1>
                                        </div>
                                        <div class="app-time">
                                            UPCOMING
                                        </div>
                                    </div>

                                    <div class="app-past">
                                        <div class="app-number">
                                            <h1 style="color: white">{{ numPast }}</h1>
                                        </div>
                                        <div class="app-time">
                                            PAST
                                        </div>
                                    </div>
                                </div>

                                <div class="chat-button">
                                    <button type="button" class="btn btn-outline-primary ignoreThisBitch" @click="chatRedirect(childID)"><span class="far fa-comment-alt"></span>  Chat</button>
                                </div>
                            </div>
                        
                            <div class="right-group">
                                <div class="container">
                                    <div class="row">
                                        <div class="col-sm">
                                            <div class="details">
                                                <strong>Age</strong>
                                                <br/>
                                                <span class="next-patient-details">{{ Age }}</span>
                                            </div>
                                        </div>
                                        <div class="col-sm">    
                                            <div class="details">
                                                <strong>Grade</strong>
                                                <br/>
                                                <span class="next-patient-details">{{ Grade }}</span>
                                            </div>
                                        </div>    
                                        <div class="col-sm">
                                            <div class="details">
                                                <strong>Guardian</strong>
                                                <br/>
                                                <span class="next-patient-details">{{ Guardian }}</span>
                                            </div>
                                        </div>    
                                        <div class="col-sm">
                                            <div class="details">
                                                <strong>Last Appointment</strong>
                                                <br/>
                                                <span class="next-patient-details">{{ LastAppointment}}</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="keyword-group profile-keywords">
                                    <!-- <span class="btn-keyword" 
                                    v-bind:style="{ color: item.problemColour, borderColor: item.problemColour }"
                                    v-for="item in focusPoints"
                                    :key="item.problemID">
                                        {{ item.problemName }}
                                    </span> -->
                                    <span class="btn-keyword"
                                        v-for="(item, i) in focusPoints" 
                                        :key="item.problemID"
                                        :class="'keyword-' + (i + 1)">
                                        {{item.problemName}}
                                    </span>
                                    <!-- <span class="btn-keyword keyword-2">Anxiety</span>
                                    <span class="btn-keyword keyword-3">ADHD</span>
                                    <span class="btn-keyword keyword-4">Bullying</span> -->
                                    <div class="edit-profile-btn orange-buttons">
                                        <button type="button" class="btn btn-primary" @click="redirect('/#/notes?childID=' + childID)"><span class="fas fa-file-medical"></span>  Notes</button>
                                        <button type="button" class="btn btn-primary" @click="redirect('/#/guardian?gurdianID=' + guardianID)" disabled><span class="fas fa-user"></span>  View Guardian</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card">
                    <div class="card-header">
                        <div class="row align-items-center">
                            <div class="col">
                                <h3 class="mb-0">Analytics</h3>
                            </div>
                            <div class="col text-right">
                                <button type="button" class="btn btn-sm btn-primary" @click="newRedirect('childreports', childID)">See all</button>
                            </div>
                        </div>
                    </div>
                    <div class="container">
                        <div class="row">
                            <div class="col">
                                <div class="row">
                                    <div class="col-lg-2 blobAxis">
                                        <img src="../assets/img/blobgrin.png" width="50" height="50">
                                        <img src="../assets/img/blobsmile.png" width="50" height="50">
                                        <img src="../assets/img/blobneutral.png" width="50" height="50">
                                        <img src="../assets/img/blobsad.png" width="50" height="50">
                                        <img src="../assets/img/blobsob.png" width="50" height="50">
                                    </div>
                                    <div id="chart" class="col-lg-10 chart-div" style="width=230px;" >
                                        <apexchart type="line" height="350" :options="chartOptions" :series="series"></apexchart>
                                    </div>
                                </div>                                
                            </div>
                            <div class="col-lg-6">
                                <div id="your-component">
                                    <p style="font-size: 20px; text-align: center;">Attendance</p>
                                    <!-- Using Component -->
                                    <calendar
                                    :eventCategories="eventCategories"
                                    :events="appointments"
                                    ref="calendar"
                                    firstDayOfWeek=2
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-xl-4 float-left">
                <div class="card">
                    <div class="card-header bg-transparent">
                        <div class="row align-items-center">
                            <div class="col">
                                <h5 class="h3 mb-0">Tests</h5>
                            </div>
                            <div class="col">
                                <div class="nav nav-pills justify-content-end">
                                    <button type="button" class="btn btn-primary btn-sm" style="color:white;" hidden>See All</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body" v-if="documents.length > 0">
                        <v-simple-table style="color: #022F5B">
                            <template v-slot:default>
                            <tbody>
                                <tr
                                v-for="item in documents"
                                :key="item.id"
                                @click="redirect('#')"
                                class="cursorClick"
                                >
                                <td>{{ item.name }}</td>
                                <td>{{ item.date }}</td>
                                </tr>
                            </tbody>
                            </template>
                        </v-simple-table>
                    </div>    
                    <v-card 
                    v-else 
                    class="justify-center mt-2"
                    flat
                    style="text-align: center">
                        <span class="fas fa-clipboard-check fa-5x" style="color: #FF9565"></span>
                        <v-card-title style="justify-content: center; color: #022F5B">Nothing here yet!</v-card-title>
                        <v-card-subtitle>Check back later to see if {{ childName }} has completed any tests</v-card-subtitle>
                    </v-card>
                </div>

                <div class="card">
                    <div class="card-header">
                        <div class="row align-items-center">
                            <div class="col">
                                <h3 class="mb-0">Upcoming Appointments</h3>
                            </div>
                            <div class="col text-right">
                                <a href="#!" class="btn btn-sm btn-primary" style="color: white;" @click="newRedirect('appointments', '')" hidden>See all</a>
                            </div>
                        </div>
                    </div>

                    <div class="card-body hoverColour" v-if="upcoming.length > 0">
                        <div class="appointment-card cursorClick"
                        v-for="item in upcoming.slice(0,3)"
                        :key="item.appointID"
                        @click="redirect('/#/appointment?appointID=' + item.appointID + '&childID=' + childID)">
                            <div class="circle-line">
                                <div class="circle">
                                                
                                </div>
                            </div>
                            <div class="appointDate">
                                {{ item.DayMonth + ' ' + item.Year }}  
                            </div>
                            <span class="grey-line"></span>
                            <div class="consultation-type">
                                <h6 class="text-uppercase text-muted ls-1 mb-1">Focus</h6>
                                <!-- <v-chip
                                    color="#11cdef"
                                    outlined
                                    label >
                                        {{ item.focus }}
                                </v-chip> -->
                                <span
                                v-if="item.focus == 'Not Specified'" 
                                class="btn-keyword appointChip">
                                        {{ item.focus }}
                                </span>
                                <span v-else class="btn-keyword keyword-1">
                                        {{ item.focus }}
                                </span>
                            </div>
                            <span class="grey-line grey-lineSecondary"></span>
                            <a :href="'/appointment?ID=' + item.appointID + '&note=true'" class="note-btn goRight"><i class="fas fa-file-medical"></i><span>Note</span></a>
                        </div>

                        <!-- <div class="appointment-card">
                            <div class="circle-line">
                                <div class="circle">
                                                
                                </div>
                            </div>
                            <div class="appointDate">
                                88 Sep 
                            </div>
                            <div class="appointYear">
                                9999
                            </div>                            
                            <span class="grey-line"></span>
                            <div class="consultation-type">
                                <h6 class="text-uppercase text-muted ls-1 mb-1">Focus</h6>
                                Depression
                            </div>
                            <span class="grey-line grey-lineSecondary"></span>
                            <a class="note-btn goRight"><i class="fas fa-file-medical"></i><span>Note</span></a>
                        </div> -->
                    </div>
                    <v-card 
                    v-else 
                    class="justify-center mt-2"
                    flat
                    style="text-align: center">
                        <span class="far fa-calendar-alt fa-5x" style="color: #FF9565"></span>
                        <v-card-title style="justify-content: center; color: #022F5B">Nothing here yet!</v-card-title>
                        <v-card-subtitle>Check back later to see if {{ childName }} has any upcoming appointments</v-card-subtitle>
                    </v-card>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import { Calendar } from 'vue-sweet-calendar'
    import 'vue-sweet-calendar/dist/SweetCalendar.css'
    import moment from 'moment'

export default {

    name: 'YourComponentName',

    data: function () {
        return {
            eventCategories: 
            [
                {
                id: 1,
                title: 'Personal',
                textColor: 'white',
                backgroundColor: 'Green'
                },
                {
                id: 2,
                title: 'Personal',
                textColor: 'white',
                backgroundColor: 'Red'
                },
                {
                id: 3,
                title: 'Personal',
                textColor: 'white',
                backgroundColor: 'Blue'
                },
            ],
            upcoming: 
            [
                // {
                //     appointID: 0,
                //     DayMonth: '24 Sep',
                //     Year: '2021',     
                //     focus: 'depression',   
                // }
            ],
            appointments: 
            [
                // {
                //     start: '2021-07-01',
                //     end: '2021-07-01',
                //     repeat: 'never',
                //     categoryId: 2
                // },
            ],

            series: 
            [
                {
                name: "Moods",
                data: [0, 0, 0, 0, 0, 0, 0]
                }
            ],
            chartOptions: {
                chart: 
                {
                    redrawOnParentResize: true,
                    // height: 350,
                    width: '100%',
                    type: 'line',
                    zoom: {
                        enabled: false
                    },
                    toolbar: {
                        show: false,
                    },
                },
                dataLabels: 
                {
                    enabled: false
                },
                stroke: 
                {
                    curve: 'smooth'
                },
                tooltip: {
                    enabled: false,
                },
                title: {
                    text: 'Weekly Mood Trend',
                    align: 'center',
                    offsetX: -10,
                    style: {
                        fontSize:  '20px',
                        fontWeight:  'bold',
                        fontFamily: 'Segoe UI Semibold',
                        color: '#022F5B'
                    },
                },
                grid: {
                row: {
                    colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
                    opacity: 0.5
                },
                },
                xaxis: {
                rotate: -50,
                offsetX: 5,
                // hideOverLappingLabels: true,
                categories: ['25/06', '03/07', '10/07', '17/07', '18/07', '19/07', '20/07'],
                },
                yaxis: {
                show: false,
                max: 5,
                tickAmount: 5,
                },
                colors:['#F44336', '#E91E63', '#9C27B0'],
                grid: {
                row: {
                    colors: ['white']
                },
                },
            },

            documents: 
            [
                // {
                //   id: 0,
                //   name: 'Family Drawing',
                //   date: '14 April 2021',
                // },
            ],

            childID: '',
            childName: 'Nathan Drake',
            childPP: '',
            Age: 'Not Specified',
            Grade: 'Not Specified',
            Guardian: '',
            LastAppointment: '',
            guardianID: '',
            childUser: [],
            numUpcoming: 5,
            numPast: 16,
            colors: ['#2196F3', '#fb6340', '#11cdef', '#2dce89', '#f5365c'],
            focusPoints: 
            [
                // {
                //     problemID: 4,
                //     problemName: 'Insecurity',
                //     problemColour: '#11cdef',
                // },
            ],
            tempDocs: [],
        }
    },
    
  components: {
    Calendar // Registering Component
  },

  async mounted() {
    await this.getChildProfile()
  },

  methods: {

    newRedirect(page, paramValue) {
      if(paramValue != undefined)
      {
        this.$router.push({ path: page, query: { id: paramValue } })
      }
    },

    async chatRedirect(childID){
        await this.$store.dispatch('chat/setChattingChild', parseInt(childID))
        
        this.$router.push({path: 'PsychologistChat'})
    },
    
    redirect: function(link) {
      window.location.href = link;
    },
    
    goToday() {
      this.$refs.calendar.goToday()
    },

    async getChildProfile() {
      const urlChildID = this.$route.query.id
      
      this.childID = urlChildID
      const user = JSON.parse(window.localStorage.getItem('currentUser'))
      const childIDParam = {
        childID: parseInt(urlChildID),
        psychID: user.userID
      }
      this.childUser = await this.$store.dispatch('users/GetChild', childIDParam)
      this.childName = this.childUser.name
      const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)
      this.childPP = images(`./${this.childUser.profilePicture}`)
      
    //   var dob = this.childUser.dob.substring(0, 10)
    //   this.Age = moment().diff(dob, 'years');
    //   this.Grade = this.childUser.grade

      var dob
        if(this.childUser.dob != null)
        {
            dob = this.childUser.dob.substring(0, 10)
            this.Age = moment().diff(dob, 'years');  
        }
        if(this.childUser.grade != null)
            this.Grade = this.childUser.grade
      
      this.GetGuardian(urlChildID)
      this.getAppointmentNumbers(urlChildID)
      this.getLastAppointment(urlChildID)
      this.getFocusPoints(urlChildID)
      this.GetUserBookings(urlChildID)
      this.getAllUpcomingBookings(urlChildID)
      this.getTestsDoneWithPsych(urlChildID)
      this.getAllChildPsychActivities(urlChildID)
      var docs = this.sortArray(this.tempDocs)
      this.documents = docs
      this.getWeeklyMoodTrend(urlChildID)
      //this.resizeArray(this.documents)
    },

    // resizeArray(array)
    // {
    //     debugger
    //     array.splice(0, 3)
    // },

    async getTestsDoneWithPsych(childUserID) {
      const user       = JSON.parse(window.localStorage.getItem('currentUser'))
      var   intChildID = parseInt(childUserID)

      const childTestParam = {
        childID: intChildID,
        psychID: user.userID 
      }

      const tests = await this.$store.dispatch('tests/getTestsDoneWithPsych', childTestParam)
      
      tests.forEach((test) => {
        var testDate = test.dateCompleted.substring(0, 10)
        testDate = moment(testDate).format('DD MMMM yyyy');
        
        if(test.testName != "Mood Tracker")
        {
            var temp = {
            id: test.childTestID,
            name: test.testName,
            date: testDate,
            }
        
            this.tempDocs.push(temp)
        }
      })
    },

    async getAllChildPsychActivities(childUserID) {
      const user = JSON.parse(window.localStorage.getItem('currentUser'))
      var intChildID = parseInt(childUserID)

      const childActivityParam = {
        childID: intChildID,
        psychID: user.userID 
      }

      const activities = await this.$store.dispatch('activity/getAllChildPsychActivities', childActivityParam)

      activities.forEach((activity) => {
        var activityDate = activity.date.substring(0, 10)
        activityDate = moment(activityDate).format('DD MMMM yyyy');

        var temp = {
          id: activity.childTestID,
          name: activity.activityName,
          date: activityDate,
        }
        this.tempDocs.push(temp)
      })
    },

    sortArray(array) {
        var sortedArray = array.sort((a,b) => new moment(b.date).format('YYYYMMDD') - new moment(a.date).format('YYYYMMDD'))
        sortedArray.forEach(sArray => {
            sArray.date = moment(sArray.date).format('DD MMMM yyyy')
        })
        return sortedArray
    },

    async GetGuardian(childUserID) {
      const childIDParam = {
        childID: childUserID
      }
        const guardian = await this.$store.dispatch('users/GetGuardian', childIDParam)
        this.Guardian = guardian.name
        this.guardianID = guardian.userID
    },

    async getAppointmentNumbers(childUserID)
    {
      this.numPast     = await this.$store.dispatch('bookings/getNumberOfPastBookings', childUserID)
      this.numUpcoming = await this.$store.dispatch('bookings/getNumberOfUpcomingBookings', childUserID)
    },

    async getLastAppointment(childUserID)
    {
      var last = await this.$store.dispatch('bookings/GetLastAppointmentDate', childUserID)
      this.LastAppointment = this.formatDate(last)
    },

    async getFocusPoints(childUserID) {
      
      childUserID = parseInt(childUserID)
      const Points = await this.$store.dispatch('hub/getChildFocusPoints', childUserID)
      Points.forEach((point, index) => {
            
        var temp = {
          problemID: point.problemID,
          problemName: point.problem,
          problemColour: this.colors[index],
        }
        this.focusPoints.push(temp)
      })
    },

    async GetUserBookings(childUserID) {
        const bookings = await this.$store.dispatch('bookings/GetUserBookings', childUserID)
        bookings.forEach((booking) => {
            var date = booking.date.substring(0, 10)
            var id = 0
            if(booking.cancelled == 'Attended')
            {
                id = 1
            }
            else if(booking.cancelled == 'Future')
            {
                id = 3
            }
            else
            {
                id = 2
            }
            var temp = {
                start: date, 
                end: date, 
                repeat: 'never', 
                categoryId: id
            }
            this.appointments.push(temp)
        })
    },

    async getAllUpcomingBookings(childUserID)
    {
        const bookings = await this.$store.dispatch('bookings/getAllUpcomingBookings', childUserID)
        bookings.forEach(async (booking) => {
            
            var date = booking.bookingInfo.date.substring(0, 10)
            var dayMonth = moment(date).format("DD MMM")
            var year = moment(date).format("YYYY")
            
            var temp = {
                appointID: booking.bookingInfo.bookingID, 
                DayMonth: dayMonth, 
                Year: year,
                focus: await this.getRHubProblem(booking.bookingInfo.problemID)
            }
            this.upcoming.push(temp)
        })
    },

    async getRHubProblem(problemID) {
        var name = ''
        
        if(problemID != null)
        {
            
            const problem = await this.$store.dispatch('hub/getRHubProblem', problemID)
            
            name = problem.problem
        }
        else
        {
            name = "Not Specified"
        }
        return name
    },

    async getWeeklyMoodTrend(childUserID) {
        var intChildID = parseInt(childUserID)
        
        const moodTrend = await this.$store.dispatch('tests/getWeeklyMoodTrend', intChildID)
        
        var newData = []
        var days = []
        moodTrend.forEach((mood) => {
            var tempDate = mood.date.substring(0, 10)
            days.push(moment(tempDate).format("DD/MM"))
            newData.push(mood.moodValue)
        })
        this.chartOptions = {...this.chartOptions, ...{
            xaxis: {
                categories: days
            }
        }}
        this.series = [{
            data: newData
        }]
    },

    formatDate(date) {
          var minDate = moment.utc("0001-01-01").format('DD MMMM yyyy')
            
            let formattedDate = moment(date).format('DD MMMM yyyy')
            
            if(minDate === formattedDate){
                return 'N/A' 
            } 
            else
                return formattedDate
		}
  },
}
</script>

<style scoped>
    .cursorClick {
        cursor: pointer;
    }
    
    .hoverColour :hover{
        background-color: #eeeeee;
    }

    .circle {
        width: 12px;
        height: 12px;
        left: calc(50% - 6px);
        top: 40%;
    }

    .circle-line {
        width: 2.5px;
        height: 80px;
        margin: 0;
    }

    .appointYear {
        display: contents;
    }

    .appointDate {
        align-self: center;
        white-space: nowrap
    }

    .grey-line {
        height: 35px;
    }

    .grey-lineSecondary {
        margin-right: -50%;
    }

    .consultation-type {
        min-width: 100%;
    }

    .goRight {
        margin: auto -80px auto auto;
    }

    @media only screen and (max-width: 1600px) {
        .goRight {
            margin: auto -50px auto auto;
        }
    }

    @media only screen and (max-width: 1600px) {
        .btn-keyword {
            padding: 0.625rem 0.8rem;
        }
    }

    @media only screen and (max-width: 1400px) {
        .btn-keyword
        {
            line-height: 1;
            padding: 0.625rem 0.5em;
        }
    }

    .btn-keyword
    {
        line-height: 0.8;
    }

    .ignoreThisBitch {
        bottom: .5rem;
        color: var(--orange);
        background: white;
        width: 7rem;
        border-radius: 50px;
        border: none;
    }

    .leftGraph {
        width: 50%;
        float: left;
    }

    .rightGraph {
        width: 50%;
        float: right;
    }

    .col .blobAxis {
        float: left;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        height: 307px;
        padding-top: 60px;
        padding-bottom: 20px;
        padding-left: 30px;
        padding-right: 0px;
    }

    .chart-div {
      padding-left: 5px;
      padding-right: 10px;
    }

    .appointment-card
    {
        padding-left: 0px;
        grid-template-columns: repeat(7, 1fr);
    }
</style>