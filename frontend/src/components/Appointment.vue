<template>
    <div>
        <v-app>
            <div class="row">
                <div class="col-xl-8">
                    <!-- <div class="card">
                         <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col">
                                <h3 class="mb-0">Appointment on {{ booking.date }}</h3>
                                </div>
                            </div>
                        </div>
                        <div class="card-body"> -->
                            <v-container>
                                <v-row>
                                    <v-col cols="2.5">
                                        <v-card
                                        height="100"
                                        style="text-align: center;"
                                        elevation="1">
                                            <v-card-title class="text-h5" style="justify-content: center;">
                                                <span class="far fa-calendar" style="color: #048A1A;"></span>
                                            </v-card-title>
                                            <v-card-text>
                                                <div class="calendarCard" style="margin-top: -7px;">
                                                    {{ booking.date }}
                                                </div>
                                            </v-card-text>
                                        </v-card>
                                    </v-col>
                                    <v-col cols="2.5">
                                        <v-card
                                        flat 
                                        height="100"
                                        style="text-align: center;"
                                        elevation="1">
                                            <v-card-title class="text-h5" style="justify-content: center;">
                                                <span class="far fa-clock" style="color: blue;"></span>
                                            </v-card-title>
                                            <v-card-text>
                                                <div>
                                                    {{ booking.time }}
                                                </div>
                                            </v-card-text>
                                        </v-card>
                                    </v-col>
                                    <v-col cols="2.5">
                                        <v-card
                                        flat 
                                        height="100"
                                        style="text-align: center;"
                                        elevation="1">
                                            <v-card-title class="text-h5" style="justify-content: center;">
                                                <span class="fas fa-crosshairs" style="color: red"></span>
                                            </v-card-title>
                                            <v-card-text>
                                                <v-dialog
                                                v-model="dialog"
                                                width="400">
                                                    <template v-slot:activator="{ on, attrs }">
                                                        <v-chip
                                                        v-if="booking.focus != 'Not Specified'"
                                                        color="#11cdef"
                                                        outlined
                                                        label
                                                        v-bind="attrs"
                                                        v-on="on">
                                                            {{ booking.focus }}
                                                            <v-icon right>
                                                                mdi-pencil
                                                            </v-icon>
                                                        </v-chip>
                                                        <v-chip
                                                        v-else
                                                        outlined
                                                        label                                               
                                                        v-bind="attrs"
                                                        v-on="on">
                                                            {{ booking.focus }}
                                                            <v-icon right>
                                                                mdi-pencil
                                                            </v-icon>
                                                        </v-chip>
                                                    </template>
                                                    <v-card>
                                                        <div class="text-center">
                                                            <br/>
                                                            <h2>
                                                                Select Focus Point
                                                            </h2>
                                                        </div>
                                                        <v-container class="py-0">
                                                            <v-row
                                                                align="center"
                                                                justify="start" >
                                                                <div
                                                                class="noFocusPosition" 
                                                                v-if="selections[0] == undefined">
                                                                    <v-chip outlined label>No Focus Specified</v-chip>
                                                                </div>
                                                                <v-col
                                                                v-for="(selection, i) in selections"
                                                                :key="selection.problemID"
                                                                class="shrink" >
                                                                   <v-chip
                                                                        :disabled="loading"
                                                                        close
                                                                        outlined
                                                                        label
                                                                        color="#11cdef"
                                                                        @click:close="selected.splice(i, 1); allPointsSelected = false; allSelected = false"
                                                                    >
                                                                        {{ selection.problemName }}
                                                                    </v-chip>
                                                                </v-col>

                                                                <v-col
                                                                v-if="!allSelected && !allPointsSelected"
                                                                cols="12">
                                                                    <v-text-field
                                                                        ref="search"
                                                                        v-model="search"
                                                                        full-width
                                                                        hide-details
                                                                        label="Search"
                                                                        single-line >
                                                                    </v-text-field>
                                                                </v-col>
                                                            </v-row>
                                                        </v-container>

                                                        <v-divider v-if="!allSelected && !allPointsSelected"></v-divider>

                                                        <v-list v-if="!allSelected && !allPointsSelected" style="max-height: 169px" class="overflow-y-auto">
                                                            <template v-for="item in categories">
                                                                <v-list-item
                                                                v-if="!selected.includes(item)"
                                                                :key="item.problemID"
                                                                :disabled="loading"
                                                                @click="selected.push(item)" >
                                                                <v-list-item-title v-text="item.problemName"></v-list-item-title>
                                                                </v-list-item>
                                                            </template>
                                                        </v-list>
                                                        <v-divider></v-divider>

                                                        <v-card-actions>
                                                            <v-spacer></v-spacer>
                                                                <button type="button" class="btn btn-secondary" @click="dialog = false">Close</button>
                                                                <button type="button" class="btn btn-primary" @click="saveFocus()">Save</button>
                                                        </v-card-actions>
                                                    </v-card>
                                                </v-dialog>
                                            </v-card-text>
                                        </v-card>
                                    </v-col>
                                    <v-col cols="2.5">
                                        <v-card
                                        flat 
                                        height="100"
                                        style="text-align: center;"
                                        elevation="1">
                                            <v-card-title class="text-h5" style="justify-content: center;">
                                                <img src="@/assets/img/newTicketOrange.svg" width="24">
                                            </v-card-title>
                                                <div>
                                                    <!-- {{ booking.cancelled }} -->
                                                    <v-select
                                                    v-model="booking.cancelled"
                                                    :items="statusTypes"
                                                    label="Status"
                                                    dense
                                                    flat
                                                    solo 
                                                    @change="changeBookingStatus()">
                                                    </v-select>
                                                </div>
                                        </v-card>
                                    </v-col>
                                    <v-col cols="2.5">
                                        <v-card
                                        flat 
                                        height="100"
                                        style="text-align: center;"
                                        elevation="1">
                                            <v-card-title class="text-h5" style="justify-content: center;">
                                                <img src="@/assets/img/sessionTypePink.svg" height="24" width="24">
                                            </v-card-title>
                                            <v-card-text>
                                                <div>
                                                    {{ booking.type }} Session
                                                </div>
                                            </v-card-text>
                                        </v-card>
                                    </v-col>
                                </v-row>
                            </v-container>
                        <!-- </div>
                    </div> -->
                    <div class="card">
                        <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h3 class="mb-0">Notes</h3>
                                </div>
                                <div class="col">
                                    <div class="nav nav-pills justify-content-end">
                                        <button type="button" class="btn btn-primary btn-sm" style="color:white;"
                                        @click="openEditor = !openEditor">
                                            <div v-if="openEditor === true">
                                                Cancel Note
                                            </div>
                                            <div v-else>
                                                Add Note
                                            </div>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <div v-show="openEditor">  
                                <quill-editor ref="myTextEditor"
                                    v-model="content"
                                    :options="editorOption">
                                </quill-editor>
                                <br/>
                                
                                <v-card-actions v-show="content != ''">
                                    <v-spacer></v-spacer>
                                    <button type="button" class="btn btn-secondary" @click="openEditor = false">Cancel</button>
                                    <button type="button" class="btn btn-primary" @click="saveNotes()">Save</button>
                                </v-card-actions>
                                <v-divider></v-divider>
                            </div>

                            <v-card-text class="py-0" v-show="nothingHere == false">
                                <v-timeline 
                                dense >
                                    <v-slide-x-reverse-transition
                                    group
                                    >
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
                            </v-card-text>
                            <v-card 
                            v-show="nothingHere == true && openEditor != true" 
                            class="justify-center"
                            flat
                            style="text-align: center">
                                <span class="fas fa-file-medical fa-5x" style="color: #FF9565"></span>
                                <v-card-title style="justify-content: center; color: #022F5B">Nothing here yet!</v-card-title>
                                <v-card-subtitle>Add a new note by clicking the Add Note button above</v-card-subtitle>
                            </v-card>
                        </div>
                    </div>
                </div>

                <div class="col-xl-4">
                    <div class="card">
                        <div class="card-body">
                            <div class="user-card">
                                <div class="nextProfile">
                                    <img :src="child.profilePicture" alt="" class="avatar rounded-circle" style="width: 60px; height: 60px;">                                    
                                    <div style="padding: 10px;">
                                        <h3 style="color: white;">{{ child.name }}</h3>
                                    </div>
                                </div>

                                <div class="container">
                                    <div class="row">
                                        <div class="col-sm">
                                            <div class="single-detail">
                                                <div class="details">
                                                    <strong>Age</strong>
                                                    <br/>
                                                    <span>{{ child.age }}</span>
                                                </div>
                                            </div>
                                            <div class="single-detail">    
                                                <div class="details">
                                                    <strong>Guardian</strong>
                                                    <br/>
                                                    <span>{{ child.guardian }}</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm">    
                                            <div class="single-detail">    
                                                <div class="details">
                                                    <strong>Grade</strong>
                                                    <br/>
                                                    <span>{{ child.grade }}</span>
                                                </div>
                                            </div>
                                            <div class="single-detail">    
                                                <div class="details">
                                                    <strong>Last Appointment</strong>
                                                    <br/>
                                                    <span>{{ child.lastAppointment}}</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="chat-button">
                                    <button type="button" class="btn btn-outline-primary ignoreThisBitch" @click="redirect('/#/profile?id=' + child.childID)"><span class="fas fa-user"></span>  View Profile</button>
                                    <button type="button" class="btn btn-outline-primary ignoreThisBitch" @click="chatRedirect(child.childID)"><span class="far fa-comment-alt"></span>  Chat</button>
                                    <!-- <button type="button" class="btn btn-outline-primary ignoreThisBitch" @click="redirect('/guardian?gurdianID=' + guardianID)"><span class="fas fa-user"></span>  Guardian</button> -->
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h3 class="mb-0">Video Meeting</h3>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            To create a video meeting, visit <a href="https://zoom.us/" target="_blank" style="color: blue">zoom.us <v-icon color="blue">mdi-open-in-new</v-icon></a>
                            <v-card flat width="400" class="mt-2">
                                <v-form>
                                    <v-textarea
                                    name="input-7-1"
                                    :value="zoomInvitation"
                                    v-model="zoomInvitation"
                                    label="Zoom Invitation"
                                    hint="Copy Invitation Here"
                                    outlined
                                    color="#FF9565"
                                    flat
                                    rows="2"
                                    @change="onInvitationPaste()">
                                    </v-textarea>
                                    <v-card-actions>
                                        <v-spacer></v-spacer>
                                        
                                        <v-dialog
                                        v-model="helpDialog"
                                        width="500">
                                            <template v-slot:activator="{ on, attrs }">
                                                <button type="button" class="btn btn-secondary" v-on="on" v-bind="attrs">Help</button>
                                            </template>

                                            <v-card>
                                                <div class="text-center">
                                                            <br/>
                                                            <h2>
                                                                How to Setup Zoom Meeting
                                                            </h2>
                                                        </div>
                                                <v-card-text>
                                                    <v-carousel 
                                                    v-model="carModel"
                                                    show-arrows-on-hover="true">
                                                        <v-carousel-item
                                                        v-for="(item, i) in zoomHelp"
                                                        :key="i">
                                                            <img :src="item.image" :height="item.size">
                                                        </v-carousel-item>
                                                    </v-carousel>
                                                </v-card-text>

                                                <v-card-actions>
                                                    <v-spacer></v-spacer>
                                                    <button type="button" class="btn btn-primary" @click="helpDialog = false">Close</button>
                                                </v-card-actions>
                                            </v-card>
                                        </v-dialog>

                                        <button type="button" class="btn btn-primary" @click="saveVideo()">Save</button>
                                    </v-card-actions>
                                </v-form>
                            </v-card>
                        </div>
                    </div>
                </div>
            </div>
            <v-snackbar
            v-model="snackbar"
            :timeout="timeout">
            {{ text }}

            <template v-slot:action="{ attrs }">
                <v-btn
                color="#FF9565"
                text
                v-bind="attrs"
                @click="snackbar = false"
                >
                Close
                </v-btn>
            </template>
            </v-snackbar>
        </v-app>
    </div>
</template>

<script>
    import moment from 'moment'

    const toolbarOptions = [
        [{ 'size': ['small', false, 'large', 'huge'] }],
        ['bold', 'italic', 'underline', 'strike', { 'color': []}], 
        [{ 'list': 'ordered'}, { 'list': 'bullet' }, { 'align': [] }],
        ['clean'], 
    ];
export default {

    data () {
        return {
            carModel: 0,
            helpDialog: false,
            zoomInvitation: '',
            meetingID: '',
            meetingPass: '',
            zoomHelp:
            [
                {
                    image: 'zoomTut.png',
                    size: 300
                },
                {
                    image: 'pasteTut.png',
                    size: 100
                },
            ],

            statusTypes: ['Attended', 'Missed', 'Future', 'Cancelled'],
            snackbar: false,
            text: '',
            timeout: 2000,

            focusPoints: 
            [
                // {
                //     problemID: 4,
                //     problemName: 'Insecurity',
                //     problemColour: '#11cdef',
                // },
                // {
                //     problemID: 5,
                //     problemName: 'Anxiety',
                //     problemColour: '#11cdef',
                // },
                // {
                //     problemID: 6,
                //     problemName: 'Depression',
                //     problemColour: '#11cdef',
                // },
            ],
            loading: false,
            search: '',
            selected: [],

            dialog: false,
            content: '',
            editorOption: {
                placeholder: 'Add new note...',
                modules: {
                    toolbar: toolbarOptions,
                }
            },
            openEditor: false,

            notes: 
            [
                // {
                //     id: 1,
                //     color: '#0FAB2C',
                //     icon: 'mdi-calendar',
                //     text: '<h2 style="color: white;">Appointment on 14 April 2021</h2> Pretty much. Even drove from Olifants to the Orpen gate just to get signal to do one <h5 style="color: white; opacity: 0.6;">Written on 14 April 2021</h5>',
                // },
            ],
            booking: 
            {
                id: '',
                date: '',
                time: '',
                focus: '',
                cancelled: '',
                type: '',
                noteID: '',
            },
            
            child: 
            {
                childID: '',
                name: 'Nathan Drake',
                profilePicture: '',
                age: 'Not Specified',
                grade: 'Not Specified',
                guardianID: '1',
                guardian: 'Gerrie Castro',
                lastAppointment: 'N/A',
            },

            iconsColours: 
            [
                {
                    type: 'Appointment',
                    icon: 'mdi-calendar',
                    color: '#0FAB2C'
                },
            ],
            
            colors: ['#2196F3', '#fb6340', '#11cdef', '#2dce89', '#f5365c'],
            nothingHere: false,
            selectedFocus: '',
            allPointsSelected: false,
        }
    },
    async mounted() {
        const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)
        this.zoomHelp.forEach(element => {
            element.image =  images(`./${element.image}`)
        });
        
        await this.getChildProfile()
        await this.getAppointment()
        await this.fetchAllFocusPoints()
    },

    computed: {
      allSelected () {
        return this.selected.length === 1
      },
      categories () {
        const search = this.search.toLowerCase()

        if (!search) return this.focusPoints

        return this.focusPoints.filter(item => {
          const text = item.problemName.toLowerCase()

          return text.indexOf(search) > -1
        })
      },
      selections () {
        const selections = []

        for (const selection of this.selected) {
          selections.push(selection)
        }

        return selections
      },
    },

    watch: {
      selected () {
        this.search = ''
      },
    },

    methods: {

        async saveVideo()
        {
            const bookingParams =
            {
                bookingID: this.booking.id,
                meetingID: this.meetingID,
                meetingPassword: this.meetingPass
            }
            const resp = await this.$store.dispatch('bookings/saveVirtualSession', bookingParams)
            this.text = 'Zoom Invitation Saved'
            this.snackbar = true
        },

        onInvitationPaste()
        {
            var meetingIDpos = this.zoomInvitation.search('Meeting ID:') + 12
            var meetingPassPos = this.zoomInvitation.search('Passcode:')
            var diff = meetingPassPos - meetingIDpos
            this.meetingID = this.zoomInvitation.substr(meetingIDpos, diff)
            this.meetingPass = this.zoomInvitation.substr(meetingPassPos + 10)
            
            this.meetingID = this.meetingID.replace(/\s+/g, '')
            this.meetingPass = this.meetingPass.replace(/\s+/g, '')
            
            this.zoomInvitation = 'Meeting ID: ' + this.meetingID + '\n' + 'Passcode: ' + this.meetingPass
            //this.text = 'Zoom Invitation Saved'
            //this.snackbar = true
        },

        async changeBookingStatus()
        {
            const bookingParams =
            {
                bookingID: this.booking.id,
                cancelled: this.booking.cancelled
            }
            const resp = await this.$store.dispatch('bookings/changeBookingStatus', bookingParams)
            this.text = 'Appointment Status Updated'
            this.snackbar = true

        },

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

        async saveFocus() {
            var bookingFocusParam = {}

            if(this.selections[0] == undefined)
            {
                bookingFocusParam = {
                    bookingID: this.booking.id,
                    problemID: null,
                }
                this.booking.focus = "Not Specified"
            }
            else
            {
                bookingFocusParam = {
                    bookingID: this.booking.id,
                    problemID: this.selected[0].problemID,
                }
                this.booking.focus = this.selected[0].problemName
            }
            const resp = await this.$store.dispatch('bookings/addFocusPointToBooking', bookingFocusParam)
            this.dialog = false
        },

        next () {
            this.loading = true

            setTimeout(() => {
                this.search = ''
                this.selected = []
                this.loading = false
            }, 2000)
        },

        redirect: function(link) {
            window.location.href = link;
        },

        async getAppointment() {
            const urlAppointmentID = parseInt(this.$route.query.appointID) 
            const appointment = await this.$store.dispatch('bookings/getAppointmentByID', urlAppointmentID)
            this.booking.id = appointment.bookingID
            this.booking.date = moment(appointment.date.substring(0, 10)).format('DD MMMM YYYY')
            this.booking.time = moment(appointment.date).hour(appointment.time.hours).minutes(appointment.time.minutes).format('HH:mm')
            this.booking.cancelled = appointment.cancelled
            this.booking.type = appointment.type
            this.meetingID = appointment.meetingID
            this.meetingPass = appointment.meetingPassword

            if(this.meetingID != null)
                this.zoomInvitation = 'Meeting ID: ' + this.meetingID + '\n' + 'Passcode: ' + this.meetingPass
            else
                this.zoomInvitation = ''

            this.booking.focus = await this.getRHubProblem(appointment.problemID)
            if(this.booking.focus != "Not Specified")
            {
                var tempSelection = {
                    problemID: appointment.problemID,
                    problemName: this.booking.focus,
                    problemColour: '#11cdef'
                }
                this.selections.push(tempSelection)
                this.selectedFocus = tempSelection.problemID
                this.allPointsSelected = true
            }
            await this.getNote(appointment.noteID)
        },

        async getNote(noteID) {
            if(noteID == null)
            {
                this.nothingHere = true
            }
            else
            {
                this.nothingHere = false
                const note = await this.$store.dispatch('notes/getNote', noteID)
                var temp = {
                    id: note.noteID,
                    color: this.iconsColours[0].color,
                    icon: this.iconsColours[0].icon,
                    text: note.feedback 
                }
                this.notes.unshift(temp)
            }
        },

        async saveNotes() {
            const user = JSON.parse(window.localStorage.getItem('currentUser'))

            var note = ''
            //var date = moment().format('DD MMMM YYYY, h:mm:ss a');
            note = '<h2 style="color: white;">Appointment on ' + this.booking.date + '</h2> ' + this.content + ' <h5 style="color: white; opacity: 0.6;">Written on ' + moment().format('DD MMMM YYYY'); + '</h5>'
            const requestParams = {
                Feedback: note,
                DateCreated: "2021-07-19T23:08:37.102Z",
                Type: "Appointment",
                PsychID: user.userID,
                ChildID: parseInt(this.child.childID),
                OtherTableID: this.booking.id
            }
            debugger
            const resp = await this.$store.dispatch('notes/saveNote', requestParams)
            
            var temp = {
                id: resp.noteID,
                color: this.iconsColours[0].color,
                icon: this.iconsColours[0].icon,
                text: resp.feedback 
            }
            this.notes.unshift(temp)
            this.content = ''
            this.openEditor = false
            this.nothingHere = false
        },

        async getChildProfile() {
            const urlChildID = this.$route.query.childID
            this.child.childID = urlChildID
            const psych = JSON.parse(window.localStorage.getItem('currentUser'))

            const childIDParam = {
				childID: parseInt(urlChildID),
                psychID: psych.userID
			}
            
            const childUser = await this.$store.dispatch('users/GetChild', childIDParam)
            this.child.name = childUser.name
			const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)
            this.child.profilePicture = images(`./${childUser.profilePicture}`)

           var dob;
            if(childUser.dob != null){
                dob = childUser.dob.substring(0, 10)
                this.child.age = moment().diff(dob, 'years');  
            }
            // else
            //     // dob = "Not Specified"
            //     this.child.age = 'Not Specified'

            
            if(childUser.grade != null)
                this.child.grade = childUser.grade
            
            this.GetGuardian(urlChildID)
            this.getLastAppointment(urlChildID)
        },

        async getLastAppointment(childUserID) {
            var last = await this.$store.dispatch("bookings/GetLastAppointmentDate", childUserID)
            
            this.child.lastAppointment = this.formatDate(last)
        },

        formatDate(date) 
        {
            var minDate = moment.utc("0001-01-01").format('DD MMMM yyyy')
                
            let formattedDate = moment(date).format('DD MMMM yyyy')
            
            if(minDate === formattedDate)
            {
                return 'N/A' 
            } 
            else
                return formattedDate
        },

        async GetGuardian(childUserID) {
            const childIDParam = {
				childID: childUserID
			}
            const guardian = await this.$store.dispatch('users/GetGuardian', childIDParam)
            this.child.guardian = guardian.name
            this.child.guardianID = guardian.userID
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

        async fetchAllFocusPoints() {
            const points = await this.$store.dispatch('hub/fetchAllFocusResource')
            points.forEach((point, index) => {
                var k = index
                if(k == 4)
                {
                    k = 0
                }
                var temp = {
                    problemID: point.problemID,
                    problemName: point.problem,
                    problemColour: this.colors[k],
                }
                this.focusPoints.push(temp)   
            })
        },
    }
}
</script>

<style scoped>
    .theme--light.v-application {
        background: #FBF5F2;
    }
    
    .v-application--wrap {
        min-height: 0;
    }

    .user-card {
        color: white;
        background: var(--orange);
        border-radius: 15px;
        padding: 10px 0px 0px 10px;
    }

    .user-card-details {
        padding-bottom: 2rem;
    }

    .ignoreThisBitch {
        bottom: .5rem;
        color: var(--orange);
        background: white;
        width: 9.8rem;
        border-radius: 50px;
    }

    .chat-button {
        padding-bottom: 0.5rem;
    }

    .single-detail {
        padding-bottom: 0.5rem;
    }

    .nextProfile {
        padding-bottom: 0rem;
    }

    .theme--light.v-input, .theme--light.v-input input, .theme--light.v-input textarea {
        color: #022F5B !important;
    }

    .theme--light.v-label {
        color: rgba(2, 47, 91, .6);
    }

    .ql-picker-label {
        color: #022F5B;
    }

    .v-list-item-group .v-list-item--active .circle {
        background-color: #FF9565;
    }

    .theme--light.v-list-item:not(.v-list-item--active):not(.v-list-item--disabled) {
        color: #022F5B !important;
    }
    
    html {
        font-family: 'Segoe UI Semibold', Tahoma, Geneva, Verdana, sans-serif;
    }

    .noFocusPosition
    {
        padding-top: 15px;
        margin-bottom: -30px;
        padding-left: 18px;
    }

    @media only screen and (max-width: 1400px) {
        .ignoreThisBitch {
            width: auto;
        }
    }

    @media only screen and (max-width: 1400px) {
        .calendarCard
        {
            font-size: 12px;
        }
    }

</style>