<style>
    .v-application--wrap {
        min-height: 0;
    }    
</style>
<template>
	<div class="main-content">
        <v-app>
            <div class="row">
                <div class="col-xl-8">
                    <div class="card">
                        <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h3 class="mb-0">Analytics</h3>
                                </div>
                            </div>
                        </div>
                        <div class="container">
                            <div class="row">
                                <div class="col">
                                    <div class="row">
                                        <div class="col-lg-1 blobAxis">
                                            <img src="../assets/img/blobgrin.png" width="50" height="50">
                                            <img src="../assets/img/blobsmile.png" width="50" height="50">
                                            <img src="../assets/img/blobneutral.png" width="50" height="50">
                                            <img src="../assets/img/blobsad.png" width="50" height="50">
                                            <img src="../assets/img/blobsob.png" width="50" height="50">
                                        </div>
                                        <div id="chart" class="col-lg-11 chart-div" style="width=230px;" >
                                            <!-- <apexchart type="line" height="350" :options="chartOptions" :series="series"></apexchart> -->
                                            <apexchart type="line" height="350" :options="moodChartOptions" :series="moodSeries"></apexchart>
                                        </div>
                                    </div>                                
                                </div>
                                <!-- <div class="col-lg-6">
                                    <div id="your-component">
                                        <p style="font-size: 20px; text-align: center;">Attendance</p>
                                        <calendar
                                        :eventCategories="eventCategories"
                                        :events="appointments"
                                        ref="calendar"
                                        firstDayOfWeek=2
                                        />
                                    </div>
                                </div> -->
                            </div>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h3 class="mb-0">Analysis</h3>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="container">
                                <div class="row">
                                    <!-- <div class="col-xl-6">
                                        <div>
                                            Average weekday mood: 
                                            <img :src="dayBlob" width="50" height="50" v-if="dayBlob != 'N/A'">
                                            <span v-else>No data</span>
                                        </div>
                                        <div>
                                            Average weekend mood: 
                                            <img :src="endBlob" width="50" height="50" v-if="endBlob != 'N/A'">
                                            <span v-else>No data</span>
                                        </div>
                                        <div>
                                            Analysis: {{ moodAnalysis }}
                                        </div> 
                                    </div>    -->
                                    <div class="col-xl-4 textAlignCenter alignSelfCenter">
                                        <v-card color="#ff4066" dark>
                                            <v-container>
                                                <v-row>
                                                    <v-col>
                                                        <div>Average weekday mood</div>
                                                    </v-col>
                                                    <v-col class="alignSelfCenter">
                                                        <img :src="dayBlob" width="50" height="50" v-if="dayBlob != 'N/A'">
                                                        <span v-else>No data</span>
                                                    </v-col>
                                                </v-row>
                                            </v-container>
                                        </v-card>
                                        <br/>
                                        <v-card color="#48cf48" dark>
                                            <v-container>
                                                <v-row>
                                                    <v-col>
                                                        <div>Average weekend mood</div>
                                                    </v-col>
                                                    <v-col class="alignSelfCenter">
                                                        <img :src="endBlob" width="50" height="50" v-if="endBlob != 'N/A'">
                                                        <span v-else>No data</span>
                                                    </v-col>
                                                </v-row>
                                            </v-container>
                                        </v-card>
                                        <br/>
                                        <v-card color="#a73ad6" dark>
                                            <v-container>
                                                <v-row>
                                                    <v-col>
                                                        <div>{{ moodAnalysis }}</div>
                                                    </v-col>
                                                    <!-- <v-col class="alignSelfCenter">
                                                        <v-icon>mdi-account-plus</v-icon>
                                                    </v-col> -->
                                                </v-row>
                                            </v-container>
                                        </v-card>
                                    </div>
                                    <div class="col-xl-8">
                                        <div v-if="notes.length > 0">
                                            <h3 style="text-align: center;">Most Recent Note</h3>
                                            <v-timeline dense>
                                                <v-slide-x-reverse-transition group>
                                                    <v-timeline-item
                                                    v-for="item in notes"
                                                    :key="item.id"
                                                    :color="item.color"
                                                    small
                                                    fill-dot
                                                    >
                                                    <v-alert
                                                        :value="true"
                                                        :color="item.color"
                                                        :icon="item.icon"
                                                        class="white--text"
                                                    >
                                                        <div v-html="item.text"></div>
                                                    </v-alert>
                                                    </v-timeline-item>
                                                </v-slide-x-reverse-transition>
                                            </v-timeline>
                                        </div>
                                        <v-card 
                                        v-else 
                                        class="justify-center mt-2"
                                        flat
                                        style="text-align: center">
                                            <span class="fas fa-file-medical fa-5x" style="color: #FF9565"></span>
                                            <v-card-title style="justify-content: center; color: #022F5B">Nothing here yet!</v-card-title>
                                            <v-card-subtitle>You'll see recent notes here once you add them on the Notes page</v-card-subtitle>
                                        </v-card>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-4 float-left">
                    <div class="card">
                        <div class="card-body">
                            <div class="user-card">
                                <div class="nextProfile">
                                    <img
                                    :src="child.profilePicture"
                                    alt=""
                                    class="avatar rounded-circle"
                                    style="width: 60px height: 60px"/>
                                    <div style="padding: 10px">
                                        <h3 style="color: white">{{ child.name }}</h3>
                                    </div>
                                </div>

                                <div class="container">
                                    <div class="row">
                                        <div class="col-sm">
                                            <div class="single-detail">
                                                <div class="details">
                                                    <strong>Age</strong>
                                                    <br />
                                                    <span>{{ child.age }}</span>
                                                </div>
                                            </div>
                                            <div class="single-detail">
                                                <div class="details">
                                                    <strong>Guardian</strong>
                                                    <br />
                                                    <span>{{ child.guardian }}</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm">
                                            <div class="single-detail">
                                                <div class="details">
                                                    <strong>Grade</strong>
                                                    <br />
                                                    <span>{{ child.grade }}</span>
                                                </div>
                                            </div>
                                            <div class="single-detail">
                                                <div class="details">
                                                    <strong>Last Appointment</strong>
                                                    <br />
                                                    <span>{{ child.lastAppointment }}</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="chat-button">
                                    <button
                                    type="button"
                                    class="btn btn-outline-primary ignoreThisBitch"
                                    @click="newRedirect('profile', child.id)">
                                        <span class="fas fa-user"></span> View Profile
                                    </button>
                                    <!-- <button
                                    type="button"
                                    class="btn btn-outline-primary ignoreThisBitch"
                                    @click="redirect('/guardian?gurdianID=' + guardianID)"
                                    disabled>
                                        <span class="fas fa-user"></span> Guardian
                                    </button> -->
                                    <!-- <button type="button" class="btn btn-outline-primary ignoreThisBitch" @click="redirect('/#/psychologistChat?id=' + child.id)"><span class="far fa-comment-alt"></span>  Chat</button> -->
                                    <button type="button" class="btn btn-outline-primary ignoreThisBitch" @click="redirect('/#/notes?childID=' + child.id)"><span class="fas fa-file-medical"></span>  Notes</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-header bg-transparent">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h5 class="h3 mb-0">Tests</h5>
                                </div>
                                <div class="col">
                                    <div class="nav nav-pills justify-content-end">
                                        <button type="button" class="btn btn-primary btn-sm" style="color:white;" disabled>See All</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <div v-if="documents.length > 0">
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
                                <v-card-subtitle>Check back later once {{ child.name }} has completed a test</v-card-subtitle>
                            </v-card>
                        </div>
                    </div>
                </div>
            </div>
        </v-app>
	</div>
</template>

<script>
    import { Calendar } from 'vue-sweet-calendar'
    import 'vue-sweet-calendar/dist/SweetCalendar.css'
    import moment from 'moment'

export default {

    name: 'YourComponentName',

	data() {
		return {

            documents: 
            [
                // {
                //   id: 0,
                //   name: 'Family Drawing',
                //   date: '14 April 2021',
                // },
            ],

            today: '',
            dayLastMonth: '',

            dayBlob: '',
            endBlob: '',
            analysisBlobDay: '',
            analysisBlobEnd: '',
            moodAnalysis: '',

            child: 
            {
                id: 0,
                name:            "Nathan Drake",
                profilePicture:  "",
                age:             "Not Specified",
                grade:           "Not Specified",
                guardianID:      "1",
                guardian:        "Gerrie Castro",
                lastAppointment: "N/A",
            },
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

            series: [{
                name: "Moods",
                data: [0, 0, 0, 0, 0, 0, 0]
            }],
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
                    text: 'Weekly Mood Average',
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

            
            moodSeries: [{
                name: 'Mood',
                data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
            }],
            moodChartOptions: {
                chart: 
                {
                    height: 350,
                    type: 'line',
                    endingShape: 'rounded',
                    zoom: {
                        enabled: false
                    },
                    toolbar: {
                        show: false,
                    },
                },
                stroke: 
                {
                    curve: 'smooth'
                },
                dataLabels: {
                    enabled: false,
                },
                tooltip: {
                    enabled: false,
                },
                title:
                {
                    text: 'Mood Average per Month',
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
                    categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
                    position: 'top',
                    tooltip: {
                        enabled: true,
                    }
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

            notes: 
            [
                // {
                //     id:    1,
                //     color: 'success',
                //     icon:  'mdi-calendar',
                //     text:  '14 April 2021 Appointment <br/> Pretty much. Even drove from Olifants to the Orpen gate just to get signal to do one of the assessments🙈😂 but rather in house where I can focus on one system at a time for dedicated clients instead of starting a  project with one client, then moving ck up where the last team was to finish the system 🙈😂',
                // },
            ],
            tempNotes: [],
            tempDocs: [],

            iconsColours: 
            [
                {
                    type:  "Appointment",
                    icon:  "mdi-calendar",
                    color: "#0FAB2C",
                },
                {
                    type:  "Test",
                    icon:  "mdi-format-list-checks",
                    color: "#9C4EE4",
                },
                {
                    type:  "Activity",
                    icon:  "mdi-drawing-box",
                    color: "#5172F5",
                },
                {
                    type:  "Feedback",
                    icon:  "mdi-comment-quote",
                    color: "success",
                },
            ],
      	}
	},

    components: {
        Calendar // Registering Component
    },

	computed: {
		
	},

	async mounted() {
		const user = JSON.parse(window.localStorage.getItem('currentUser'))
        const psychID = parseInt(user.userID)
        await this.getChildProfile()
        await this.GetUserBookings(this.child.id)
        await this.getMoodAnalysis(this.child.id)
        await this.getWeeklyMoodTrend(this.child.id)

        this.today = moment().format('DD MMMM YYYY')
        this.dayLastMonth = moment().subtract(30, 'days').format('DD MMMM')

        await this.getYearlyMoodAverage(this.child.id)

        this.getTestsDoneWithPsych(this.child.id)
        var docs = this.sortArray(this.tempDocs)
        this.documents = docs
        await this.getNotes()
	},

	methods: {

        redirect: function (link) {
            window.location.href = link
        },

        async getNotes() {
            const user         = JSON.parse(window.localStorage.getItem("currentUser"))
            const urlChildID   = this.$route.query.id
            const intChildID   = parseInt(urlChildID)
            const requestParam = {
                ChildID: intChildID,
                PsychID: user.userID,
            }

            const tempNotes = await this.$store.dispatch("notes/fetchAllChildsNotes", requestParam)

            if(tempNotes == undefined)
            {
                //this.nothingHere = true
            }
            else
            {
                //this.nothingHere = false
                tempNotes.forEach((note) => {
                    var color = ""
                    var icon  = ""

                    var iconColour = this.getIconColour(note.type)
                        color      = iconColour.color
                        icon       = iconColour.icon

                    var temp = {
                    id:    note.noteID,
                    color: color,
                    icon:  icon,
                    text:  note.feedback,
                    }

                    this.notes.unshift(temp)
                })
            }
            this.notes = this.notes.slice(0, 1)
        },

        getIconColour(type) {
            var iconColour = {
                icon:  "",
                color: "",
            }

            if (type == "Appointment") 
            {
                iconColour.color = this.iconsColours[0].color
                iconColour.icon  = this.iconsColours[0].icon
            } 
            else if (type == "Test") 
            {
                iconColour.color = this.iconsColours[1].color
                iconColour.icon  = this.iconsColours[1].icon
            } 
            else if (type == "Activity") 
            {
                iconColour.color = this.iconsColours[2].color
                iconColour.icon  = this.iconsColours[2].icon
            } 
            else if (type == "Feedback") 
            {
                iconColour.color = this.iconsColours[3].color
                iconColour.icon  = this.iconsColours[3].icon
            }

            return iconColour
        },

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

        sortArray(array) {
            var sortedArray = array.sort((a,b) => new moment(b.date).format('YYYYMMDD') - new moment(a.date).format('YYYYMMDD'))
            sortedArray.forEach(sArray => {
                sArray.date = moment(sArray.date).format('DD MMMM yyyy')
            })
            return sortedArray
        },

        async getYearlyMoodAverage(childID)
        {
            const moodValues = await this.$store.dispatch('tests/getYearlyMoodAverage', childID)
            
            var tempData = []
            moodValues.forEach(element => {
                tempData.push(element.moodAvg)
            });
            this.moodSeries = [{
                data: tempData
            }]
        },

        async getMoodAnalysis(childUserID)
        {
            const resp = await this.$store.dispatch("tests/getMoodAnalysis", childUserID)
            
            this.analysisBlobDay = resp.weekDayAvg
            this.analysisBlobEnd = resp.weekEndAvg
            this.moodAnalysis = resp.analysis
            var roundedDay
            var roundedEnd

            
            if(this.analysisBlobDay != 0)
                roundedDay = Math.round(this.analysisBlobDay)
            else
                roundedDay = this.analysisBlobDay

            
            if(this.analysisBlobEnd != 0)
                roundedEnd = Math.round(this.analysisBlobEnd)
            else
                roundedEnd = this.analysisBlobEnd
            
            this.dayBlob = this.getBlob(roundedDay)
            this.endBlob = this.getBlob(roundedEnd)
        },

        getBlob(roundedInt)
        {            
            var moodBlob = ''
            switch(roundedInt) {
                case 0:
                    moodBlob = 'N/A'
                    break;
                case 1:
                    moodBlob = 'blobsob.png'
                    break;
                case 2:
                    moodBlob = 'blobsad.png'
                    break;
                case 3:
                    moodBlob = 'blobneutral.png'
                    break;
                case 4:
                    moodBlob = 'blobsmile.png'
                    break;
                case 5:
                    moodBlob = 'blobgrin.png'
                    break;
            }
            
            const images = require.context("../assets/img/", false, /\.(png|jpe?g|svg)$/)
            if(roundedInt != 0)
                return images(`./${moodBlob}`)
            else
                return moodBlob
        },

        async getLastAppointment(childUserID) {
            var last = await this.$store.dispatch("bookings/GetLastAppointmentDate", childUserID)
            
            this.child.lastAppointment = this.formatDate(last)
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

        formatDate(date) 
        {
            var minDate = moment.utc("0001-01-01").format('DD MMMM yyyy')
                
            let formattedDate = moment(date).format('DD MMMM yyyy')
            
            if(minDate === formattedDate){
            return 'N/A' 
            } 
            else
            return formattedDate
        },

        newRedirect(page, paramValue)
        {
            if(paramValue != undefined)
            {
                this.$router.push({ path: page, query: { id: paramValue } })
            }
        },

        goToday() {
            this.$refs.calendar.goToday()
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

        async getChildProfile() {
            const user = JSON.parse(window.localStorage.getItem("currentUser"))

            const urlChildID = parseInt(this.$route.query.id)
            this.child.id = urlChildID

            const childIDParam = {
                childID: urlChildID,
                psychID: user.userID
            }
            
            const childUser = await this.$store.dispatch("users/GetChild",childIDParam)

            const images    = require.context("../assets/img/", false, /\.(png|jpe?g|svg)$/)
            //const dob       = childUser.dob.substring(0, 10)

            this.child.name           = childUser.name
            this.child.profilePicture = images(`./${childUser.profilePicture}`)
            // this.child.age            = moment().diff(dob, "years")
            // this.child.grade          = childUser.grade

            var dob
            if(childUser.dob != null)
            {
                dob = childUser.dob.substring(0, 10)
                this.child.age = moment().diff(dob, 'years');  
            }
            if(childUser.grade != null)
                this.child.grade = childUser.grade

            this.GetGuardian(urlChildID)
            this.getLastAppointment(urlChildID)
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

        async GetGuardian(childUserID) {
            const childIDParam = {
                childID: childUserID
            }

            const guardian = await this.$store.dispatch('users/GetGuardian', childIDParam)
            
            this.child.guardian = guardian.name 
            this.child.guardianID = guardian.userID
        },
    },
}
</script>

<style scoped>
    .yearBlobs
    {
        float: left;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        height: 307px;
        padding-top: 60px;
        padding-bottom: 20px;
        padding-left: 70px;
        padding-right: 0px;
    }

    .ignoreThisBitch {
        bottom: 0.5rem;
        color: var(--orange);
        background: white;
        width: 9.8rem;
        border-radius: 50px;
    }

    .user-card {
        color: white;
        background: var(--orange);
        border-radius: 15px;
        padding: 10px 0px 0px 10px;
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
        padding-right: 0px;
    }

    .chart-div {
      padding-left: 5px;
      padding-right: 10px;
    }

    .cursorClick {
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

    .theme--light.v-application {
        background: #FBF5F2;
    }
    
    .v-application--wrap {
        min-height: 0;
    }

</style>
