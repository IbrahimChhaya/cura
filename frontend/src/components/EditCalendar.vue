<template>
    <div>
        <v-app>
        <div class="card">
            <div class="card-header bg-transparent">
                <div class="row align-items-center">
                    <div class="col">
                        <h5 class="h3 mb-0">Calendar Availability</h5>
                    </div>
                    <div class="col">
                        <div class="nav nav-pills justify-content-end">
                            <v-dialog
                            v-model="dialog"
                            width="500" >
                            <template v-slot:activator="{ on, attrs }">
                                <button type="button" class="btn btn-primary btn-sm" v-bind="attrs" v-on="on">
                                    <v-icon
                                        left
                                        dark >
                                        mdi-pencil
                                    </v-icon>
                                    Edit All Hours
                                </button>
                            </template>
                            <v-card>
                                <div class="text-center">
                                    <br/>
                                    <h2>
                                        Select days & time
                                    </h2>
                                    <v-chip
                                        :outlined="isActive(changeColorM)"
                                        pill
                                        large
                                        dark
                                        color="#FF9565"
                                        v-on:click="changeColorM = !changeColorM; daySelected[0] = !daySelected[0]" >
                                            M
                                    </v-chip>
                                    <v-chip
                                        :outlined="isActive(changeColorT)"
                                        pill
                                        large
                                        dark
                                        color="#FF9565"
                                        @click="changeColorT = !changeColorT; daySelected[1] = !daySelected[1]" >
                                            T
                                    </v-chip>
                                    <v-chip  id="vstep2"
                                        :outlined="isActive(changeColorW)"
                                        pill
                                        large
                                        dark
                                        @click="changeColorW = !changeColorW; daySelected[2] = !daySelected[2]"
                                        color="#FF9565" >
                                            W
                                    </v-chip>
                                    <v-chip
                                        :outlined="isActive(changeColorTh)"
                                        pill
                                        large
                                        dark
                                        color="#FF9565"
                                        @click="changeColorTh = !changeColorTh; daySelected[3] = !daySelected[3]"  >
                                            T
                                    </v-chip>
                                    <v-chip
                                        :outlined="isActive(changeColorF)"
                                        pill
                                        large
                                        dark
                                        color="#FF9565"
                                        @click="changeColorF = !changeColorF; daySelected[4] = !daySelected[4]" >
                                            F
                                    </v-chip>
                                    <v-chip
                                        :outlined="isActive(changeColorS)"
                                        pill
                                        large
                                        dark
                                        color="#FF9565"
                                        @click="changeColorS = !changeColorS; daySelected[5] = !daySelected[5]" >
                                            S
                                    </v-chip>
                                    <v-chip
                                        :outlined="isActive(changeColorSu)"
                                        pill
                                        large
                                        dark
                                        color="#FF9565"
                                        @click="changeColorSu = !changeColorSu; daySelected[6] = !daySelected[6]" >
                                            S
                                    </v-chip>
                                    <v-chip
                                        :outlined="isActive(changeColorPH)"
                                        pill
                                        large
                                        dark
                                        color="#FF9565"
                                        @click="changeColorPH = !changeColorPH; daySelected[7] = !daySelected[7]" >
                                            PH
                                    </v-chip>
                                    <br/>
                                    <br/>
                                    <v-container fluid>
                                        <v-row justify="space-around">
                                            <v-col cols="12" sm="6" md="6" >
                                                <v-radio-group 
                                                mandatory 
                                                v-model="radios"
                                                v-show="!closed" >
                                                    <v-radio
                                                        label="Both"
                                                        value="both"
                                                        color="#FF9565"
                                                    ></v-radio>
                                                    <v-radio
                                                        id="vstep3"
                                                        label="Only single sessions"
                                                        value="onlySingle"
                                                        color="#FF9565"
                                                    ></v-radio>
                                                    <v-radio
                                                        label="Only repeat sessions"
                                                        value="onlyRepeat"
                                                        color="#FF9565"
                                                    ></v-radio>
                                                </v-radio-group>
                                            </v-col>
                                            <v-col cols="12" sm="6" md="6" >
                                                <v-checkbox
                                                    v-model="closed"
                                                    label="Closed"
                                                    color="#FF9565" >
                                                </v-checkbox>
                                            </v-col>
                                        </v-row>
                                    </v-container>
                                    <v-card-text id="vstep4">
                                        <v-row v-show="((!closed) && (radios != 'onlyRepeat'))" id="hide">
                                            <v-col cols="11" sm="5">
                                                <v-dialog
                                                    ref="dialog"
                                                    v-model="modal1"
                                                    :return-value.sync="sStartTime"
                                                    persistent
                                                    class="d-none"
                                                    width="500px"
                                                >
                                                    <template v-slot:activator="{ on, attrs }">
                                                    <v-text-field
                                                        v-model="sStartTime"
                                                        label="Single sessions start"
                                                        prepend-icon="mdi-clock-time-four-outline"
                                                        readonly
                                                        color="#FF9565"
                                                        v-bind="attrs"
                                                        v-on="on"
                                                    ></v-text-field>
                                                    </template>
                                                    <v-card-title class="white">
                                                        <h1>Single Session Start Time</h1>
                                                    </v-card-title>
                                                    <v-time-picker
                                                        v-if="modal1"
                                                        v-model="sStartTime"
                                                        format="24hr"
                                                        full-width
                                                        landscape
                                                        color="#FF9565"
                                                    >
                                                    <v-spacer></v-spacer>
                                                    <v-btn
                                                        text
                                                        color="primary"
                                                        @click="modal1 = false"
                                                    >
                                                        Cancel
                                                    </v-btn>
                                                    <v-btn
                                                        text
                                                        color="primary"
                                                        @click="$refs.dialog.save(sStartTime)"
                                                    >
                                                        OK
                                                    </v-btn>
                                                    </v-time-picker>
                                                </v-dialog>
                                            </v-col>
                                            <v-col cols="11" sm="5">
                                                <v-dialog
                                                    ref="dialog2"
                                                    v-model="modal2"
                                                    :return-value.sync="sEndTime"
                                                    persistent
                                                    width="500px"
                                                >
                                                    <template v-slot:activator="{ on, attrs }">
                                                    <v-text-field
                                                        v-model="sEndTime"
                                                        label="Single sessions end"
                                                        prepend-icon="mdi-clock-time-four-outline"
                                                        readonly
                                                        color="#FF9565"
                                                        v-bind="attrs"
                                                        v-on="on"
                                                    ></v-text-field>
                                                    </template>
                                                    <v-card-title class="white">
                                                        <h1>Single Session End Time</h1>
                                                    </v-card-title>
                                                    <v-time-picker
                                                        v-if="modal2"
                                                        v-model="sEndTime"
                                                        format="24hr"
                                                        full-width
                                                        landscape
                                                        color="#FF9565"
                                                    >
                                                    <v-spacer></v-spacer>
                                                    <v-btn
                                                        text
                                                        color="primary"
                                                        @click="modal2 = false"
                                                    >
                                                        Cancel
                                                    </v-btn>
                                                    <v-btn
                                                        text
                                                        color="primary"
                                                        @click="$refs.dialog2.save(sEndTime)"
                                                    >
                                                        OK
                                                    </v-btn>
                                                    </v-time-picker>
                                                </v-dialog>
                                            </v-col>
                                        </v-row>
                                        <v-row v-show="((!closed) && (radios != 'onlySingle'))">
                                            <v-col cols="11" sm="5">
                                                <v-dialog
                                                    ref="dialog3"
                                                    v-model="modal3"
                                                    :return-value.sync="rStartTime"
                                                    persistent
                                                    class="d-none"
                                                    width="500px"
                                                >
                                                    <template v-slot:activator="{ on, attrs }">
                                                    <v-text-field
                                                        v-model="rStartTime"
                                                        label="Repeat sessions start"
                                                        prepend-icon="mdi-clock-time-four-outline"
                                                        readonly
                                                        color="#FF9565"
                                                        v-bind="attrs"
                                                        v-on="on"
                                                    ></v-text-field>
                                                    </template>
                                                    <v-card-title class="white">
                                                        <h1>Repeat Session Start Time</h1>
                                                    </v-card-title>
                                                    <v-time-picker
                                                        v-if="modal3"
                                                        v-model="rStartTime"
                                                        format="24hr"
                                                        full-width
                                                        landscape
                                                        color="#FF9565"
                                                    >
                                                    <v-spacer></v-spacer>
                                                    <v-btn
                                                        text
                                                        color="primary"
                                                        @click="modal3 = false"
                                                    >
                                                        Cancel
                                                    </v-btn>
                                                    <v-btn
                                                        text
                                                        color="primary"
                                                        @click="$refs.dialog3.save(rStartTime)"
                                                    >
                                                        OK
                                                    </v-btn>
                                                    </v-time-picker>
                                                </v-dialog>
                                            </v-col>
                                            <v-col cols="11" sm="5">
                                                <v-dialog
                                                    ref="dialog4"
                                                    v-model="modal4"
                                                    :return-value.sync="rEndTime"
                                                    persistent
                                                    width="500px"
                                                >
                                                    <template v-slot:activator="{ on, attrs }">
                                                    <v-text-field
                                                        v-model="rEndTime"
                                                        label="Repeat sessions end"
                                                        prepend-icon="mdi-clock-time-four-outline"
                                                        readonly
                                                        color="#FF9565"
                                                        v-bind="attrs"
                                                        v-on="on"
                                                    ></v-text-field>
                                                    </template>
                                                    <v-card-title class="white">
                                                        <h1>Repeat Session End Time</h1>
                                                    </v-card-title>
                                                    <v-time-picker
                                                        v-if="modal4"
                                                        v-model="rEndTime"
                                                        format="24hr"
                                                        full-width
                                                        landscape
                                                        color="#FF9565"
                                                    >
                                                    <v-spacer></v-spacer>
                                                    <v-btn
                                                        text
                                                        color="primary"
                                                        @click="modal4 = false"
                                                    >
                                                        Cancel
                                                    </v-btn>
                                                    <v-btn
                                                        text
                                                        color="primary"
                                                        @click="$refs.dialog4.save(rEndTime)"
                                                    >
                                                        OK
                                                    </v-btn>
                                                    </v-time-picker>
                                                </v-dialog>
                                            </v-col>
                                        </v-row>
                                    </v-card-text>
                                </div>
                                <v-card-actions>
                                    <v-col class="text-right">
                                        <button type="button" class="btn btn-secondary" @click="dialog = false">Close</button>
                                        <button type="button" class="btn btn-primary" @click="saveChanges(false)">Save</button>
                                        <button type="button" class="btn btn-primary" @click="saveChanges(true)">Save and Close</button>
                                    </v-col>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card-body">
                <v-simple-table>
                    <template v-slot:default>
                        <thead>
                            <tr>
                                <th class="text-left" id="vstep1">
                                    Day of the Week
                                </th>
                                <th class="text-left">
                                    Single Sessions
                                </th>
                                <th class="text-left">
                                    Repeat Sessions
                                </th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr
                            v-for="item in bookingDays"
                            :key="item.day">
                                <td>
                                    {{ item.day }}
                                </td>
                                <td v-if="item.closed">
                                    Closed
                                </td>
                                <td v-else-if="item.sStartTime == null || item.sEndTime == null || item.sStartTime == '00:00'">
                                    No single sessions
                                </td>
                                <td v-else>
                                    {{ item.sStartTime }} - {{ item.sEndTime }}
                                </td>
                                <td v-if="item.closed">
                                    Closed
                                </td>
                                <td v-else-if="item.rStartTime == null || item.rEndTime == null || item.rStartTime == '00:00'">
                                    No repeat sessions
                                </td>
                                <td v-else>
                                    {{ item.rStartTime }} - {{ item.rEndTime }}
                                </td>
                                <td>
                                    <v-icon
                                    @click="onEditClick(item.id)">
                                        mdi-pencil
                                    </v-icon>
                                </td>
                            </tr>
                        </tbody>
                    </template>
                </v-simple-table>
            </div>
            
            <v-snackbar
                v-model="snackbar"
                :timeout="timeout"
                >
                {{ snackbarText }}

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
        </div>
        </v-app>
        <v-tour name="myTour" :steps="steps" :callbacks="myCallbacks"></v-tour>
    </div>
</template>

<script>
import moment from 'moment'

  export default {
    //name: 'daySelector',
    
    data: function () {
        return {
            daySelected: [],
            changeColorM: false,
            changeColorT: false,
            changeColorW: false,
            changeColorTh: false,
            changeColorF: false,
            changeColorS: false,
            changeColorSu: false,
            changeColorPH: false,
            sStartTime: "00:00",
            sEndTime: "00:00",
            rStartTime: "00:00",
            rEndTime: "00:00",
            modal1: false,
            modal2: false,
            modal3: false,
            modal4: false,
            dialog: false,
            closed: false,
            save: true,
            radios: null,
            tempCalendars: [],
            snackbar: false,
            snackbarText: 'Start time cannot be later than end time',
            timeout: 5000,

            bookingDays: [
            {
                id: 0,
                day: 'Monday',
                sStartTime: '08:00',
                sEndTime: '08:00',
                rStartTime: '08:00',
                rEndTime: '08:00',
                closed: false,
            },
            {
                id: 1,
                day: 'Tuesday',
                sStartTime: '08:00',
                sEndTime: '08:00',
                rStartTime: '08:00',
                rEndTime: '08:00',
                closed: true,
            },
            {
                id: 2,
                day: 'Wednesday',
                sStartTime: '08:00',
                sEndTime: null,
                rStartTime: '08:00',
                rEndTime: '08:00',
                closed: false,
            },
            {
                id: 3,
                day: 'Thursday',
                sStartTime: '08:00',
                sEndTime: '08:00',
                rStartTime: '08:00',
                rEndTime: '08:00',
                closed: false,
            },
            {
                id: 4,
                day: 'Friday',
                sStartTime: '08:00',
                sEndTime: '08:00',
                rStartTime: '08:00',
                rEndTime: '08:00',
                closed: false,
            },
            {
                id: 5,
                day: 'Saturday',
                sStartTime: '08:00',
                sEndTime: '08:00',
                rStartTime: '08:00',
                rEndTime: '08:00',
                closed: false,
            },
            {
                id: 6,
                day: 'Sunday',
                sStartTime: '08:00',
                sEndTime: '08:00',
                rStartTime: '08:00',
                rEndTime: '08:00',
                closed: false,
            },
            {
                id: 7,
                day: 'Public Holidays',
                sStartTime: '08:00',
                sEndTime: '08:00',
                rStartTime: null,
                rEndTime: '08:00',
                closed: false,
            },
            ],

            steps: 
            [
                {
                    target: '#vstep1',  // We're using document.querySelector() under the hood
                    content: `Here you'll be able to set your availability per day for both single and repeat sessions`,
                    params: {
                        placement: 'right',
                        enableScrolling: false,
                    }
                },
                {
                    target: '#vstep2',  // We're using document.querySelector() under the hood
                    content: `Select which days you'd like to edit`,
                    params: {
                        placement: 'bottom',
                    }
                },
                {
                    target: '#vstep3',  // We're using document.querySelector() under the hood
                    content: `What type of sessions you'd like or if you're closed`,
                    params: {
                        placement: 'bottom',
                    }
                },
                {
                    target: '#vstep4',  // We're using document.querySelector() under the hood
                    content: `And the times for each session type`,
                    params: {
                        placement: 'top',
                    }
                },
            ],

            myCallbacks: {
                onFinish: this.endTour,
                onNextStep: this.openModal,
            },
        }
    },

    async mounted() {
        await this.updateCalendar()
        var tourBool = this.$route.query.tour
        if(tourBool)
        {   
            this.$tours['myTour'].start()
        }
    },

    methods: {

        openModal(currentStep)
        {
            if(currentStep == 0)
                this.onEditClick(2)
        },

        endTour()
        {
            this.redirect('/#/tests?tour=true')
        },

        redirect: function(link) {
            window.location.href = link;
        },

        onEditClick(itemID) 
        {
            this.dialog = true
            this.daySelected[itemID] = true
            
            switch (itemID) 
            {
                case 0:
                    this.changeColorM = true;
                    break;
                case 1:
                    this.changeColorT = true;
                    break;
                case 2:
                    this.changeColorW = true;
                    break;
                case 3:
                    this.changeColorTh = true;
                    break;
                case 4:
                    this.changeColorF = true;
                    break;
                case 5:
                    this.changeColorS = true;
                    break;
                case 6:
                    this.changeColorSu = true;
                    break;
                case 7:
                    this.changeColorPH = true;
                    break;
            }
        },

        isActive: function(changeColor) {
            if(changeColor == false)
            {
                this.save = false
                return true
            }
            else
            {
                return false;
            }
        },

        saveChanges: function(close) {
            var proceed;
            proceed = true
            if(this.radios == "onlyRepeat")
            {
                this.sStartTime = null;
                this.sEndTime = null;
            }
            else if(this.radios == "onlySingle")
            {
                this.rStartTime = null;
                this.rEndTime = null;
            }
            if(this.closed == true)
            {
                proceed = true
            }
            else
            {
            
                if(this.radios == "both")
                {
                    var ssHour = parseInt(this.sStartTime.substring(0, 2));
                    var ssMin = parseInt(this.sStartTime.substring(3, 5));
                    
                    var seHour = parseInt(this.sEndTime.substring(0, 2))
                    var seMin = parseInt(this.sStartTime.substring(3, 5));

                    var rsHour = parseInt(this.rStartTime.substring(0, 2));
                    var rsMin = parseInt(this.rStartTime.substring(3, 5));

                    var reHour = parseInt(this.rEndTime.substring(0, 2))
                    var reMin = parseInt(this.rStartTime.substring(3, 5));
                    
                    if(((rsHour >= ssHour) && (rsHour < seHour)) || ((reHour > ssHour) && (reHour <= seHour)))
                    {
                        this.snackbarText = "Repeat session times clash with single session times"
                        this.snackbar = true
                        proceed = false
                    }
                    else if(((ssHour >= rsHour) && (ssHour < reHour)) || ((seHour > rsHour) && (seHour <= reHour)))
                    {
                        this.snackbarText = "Single session times clash with repeat session times"
                        this.snackbar = true
                        proceed = false
                    }
                }
                if((this.radios == "both") || (this.radios == "onlySingle"))
                {        
                    var ssHour = parseInt(this.sStartTime.substring(0, 2));
                    var ssMin = parseInt(this.sStartTime.substring(3, 5));
                    
                    var seHour = parseInt(this.sEndTime.substring(0, 2))
                    var seMin = parseInt(this.sStartTime.substring(3, 5));
                
                    if((ssHour > seHour) || ((ssHour == seHour) && (ssMin > seMin)))
                    {
                        this.snackbar = true
                        proceed = false
                    }
                    else if((ssHour == seHour) && (ssMin == seMin))
                    {
                        this.snackbarText = 'Single start and end time cannot be the same'
                        this.snackbar = true;
                        proceed = false
                    }
                }
                if((this.radios == "both") || (this.radios == "onlyRepeat"))
                {
                    var rsHour = parseInt(this.rStartTime.substring(0, 2));
                    var rsMin = parseInt(this.rStartTime.substring(3, 5));

                    var reHour = parseInt(this.rEndTime.substring(0, 2))
                    var reMin = parseInt(this.rStartTime.substring(3, 5));

                    if((rsHour > reHour) || ((rsHour == reHour) && (rsMin > reMin)))
                    {
                        this.snackbar = true
                        proceed = false
                    }
                    else if((rsHour == reHour) && (rsMin == reMin))
                    {
                        this.snackbarText = "Repeat start and end time cannot be the same"
                        this.snackbar = true;
                        proceed = false
                    }
                }
            }
            if(this.changeColorM == false && this.changeColorT == false && this.changeColorW == false && this.changeColorTh == false && 
                this.changeColorF == false && this.changeColorS == false && this.changeColorSu == false && this.changeColorPH == false)
            {
                this.snackbarText = "Please select a day"
                this.snackbar = true
                proceed = false
            }
            if(proceed)
            {
                var k;    
                for (k = 0; k < 8; k++)
                {
                    if(this.daySelected[k])
                    {
                        this.bookingDays[k].sStartTime = this.sStartTime;
                        this.bookingDays[k].sEndTime = this.sEndTime;
                        this.bookingDays[k].rStartTime = this.rStartTime;
                        this.bookingDays[k].rEndTime = this.rEndTime;
                        this.bookingDays[k].closed = this.closed;
                    }
                }
                this.saveCalendars();
                this.initialiseVars();
                if(close == true)
                {
                    this.dialog = false
                }
            }
        },

        initialiseVars() {
            this.closed = false;
            this.changeColorM = false;
            this.changeColorT = false;
            this.changeColorW = false;
            this.changeColorTh = false;
            this.changeColorF = false;
            this.changeColorS = false;
            this.changeColorSu = false;
            this.changeColorPH = false;
            this.sStartTime = "00:00";
            this.sEndTime = "00:00";
            this.rStartTime = "00:00";
            this.rEndTime = "00:00";
            this.radios = "both";
            for (let k = 0; k < 8; k++)
            {
                this.daySelected[k] = false;
            }
        },

        async updateCalendar() {
            const user = JSON.parse(window.localStorage.getItem('currentUser'))
            const psychIDParam = {
				userID: user.userID
			}
            this.tempCalendars = await this.$store.dispatch('psychologists/getPsychologistCalendars', psychIDParam)
            for (let k = 0; k < 8; k++)
            {
                this.bookingDays[k].day = this.tempCalendars[k].dayOfWeek
                if(this.tempCalendars[k].singleStart == '2021-06-28T00:00:00')
                {
                    this.bookingDays[k].sStartTime = null
                }
                else
                {
                    this.bookingDays[k].sStartTime = this.formatTime(this.tempCalendars[k].singleStart)
                }

                if(this.tempCalendars[k].singleEnd == '2021-06-28T00:00:00')
                {
                    this.bookingDays[k].sEndTime = null
                }
                else
                {
                    this.bookingDays[k].sEndTime = this.formatTime(this.tempCalendars[k].singleEnd)
                }
                
                if(this.tempCalendars[k].repeatStart == '2021-06-28T00:00:00')
                {
                    this.bookingDays[k].rStartTime = null
                }
                else
                {
                    this.bookingDays[k].rStartTime = this.formatTime(this.tempCalendars[k].repeatStart)
                }
                
                if(this.tempCalendars[k].repeatEnd == '2021-06-28T00:00:00')
                {
                    this.bookingDays[k].rEndTime = null
                }
                else
                {
                    this.bookingDays[k].rEndTime = this.formatTime(this.tempCalendars[k].repeatEnd)
                }
                
                this.bookingDays[k].closed = this.tempCalendars[k].closed
            }
        },

        formatTime(dateTime) {
			return moment(dateTime).format('HH:mm')
		},

        async saveCalendars() {
            for (let k = 0; k < 8; k++)
            {
                if(this.bookingDays[k].sStartTime == null)
                {
                    this.tempCalendars[k].singleStart = '2021-06-28T00:00:00'
                }
                else
                {
                    this.tempCalendars[k].singleStart = '2021-06-28T' + this.bookingDays[k].sStartTime + ':00'
                }
                if(this.bookingDays[k].sEndTime == null)
                {
                    this.tempCalendars[k].singleEnd = '2021-06-28T00:00:00'
                }
                else
                {
                    this.tempCalendars[k].singleEnd = '2021-06-28T' + this.bookingDays[k].sEndTime + ':00'
                }
                if(this.bookingDays[k].rStartTime == null)
                {
                    this.tempCalendars[k].repeatStart = '2021-06-28T00:00:00'
                }
                else
                {
                    this.tempCalendars[k].repeatStart = '2021-06-28T' + this.bookingDays[k].rStartTime + ':00'
                }
                if(this.bookingDays[k].rEndTime == null)
                {
                    this.tempCalendars[k].repeatEnd = '2021-06-28T00:00:00'
                }
                else
                {
                    this.tempCalendars[k].repeatEnd = '2021-06-28T' + this.bookingDays[k].rEndTime + ':00'
                }
                // this.tempCalendars[k].singleStart = '2021-06-28T' + this.bookingDays[k].sStartTime + ':00'
                // this.tempCalendars[k].singleEnd = '2021-06-28T' + this.bookingDays[k].sEndTime + ':00'
                // this.tempCalendars[k].repeatStart = '2021-06-28T' + this.bookingDays[k].rStartTime + ':00'
                // this.tempCalendars[k].repeatEnd = '2021-06-28T' + this.bookingDays[k].rEndTime + ':00'
                this.tempCalendars[k].closed = this.bookingDays[k].closed
                
                const resp = await this.$store.dispatch('psychologists/updatePsychCalendar', this.tempCalendars[k])
            }
        }
    },

    created: function() {
        for (let k = 0; k < 8; k++)
        {
            this.daySelected[k] = false;
        }
    }
}
</script>

<style scoped>
    .v-chip.v-size--large {
        width: 54px;
        justify-content: center;
    }
    
    .custom-control {
        padding-top: 1rem;
    }
    
    .hide {
        visibility: hidden;
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

    body
    {
        overflow: hidden!important;
    }

</style>