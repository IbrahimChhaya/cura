<template>
  <div v-if="counsellor || psychologist" class="container-fluid mt-4">
    <div class="row">
        <div class="col-lg-8 col-md-6">
        <div class="card">
            <h3 class="card-header">Practice Details</h3>
            <div class="card-body">
            <!-- Psychologist practice details -->
            <div v-show="isPsychologist">
                <div>
                <h4 class="account-h4">Address</h4>
                <input class="login-input" type="text" :placeholder="address" v-model="address">
                </div>
                <div>
                <h4 class="account-h4">Qualification</h4>
                <input class="login-input" type="text" :placeholder="qualification" v-model="qualification">
                </div>
                <div>
                <h4 class="account-h4">Practice number</h4>
                <input class="login-input" type="text" :placeholder="practiceNumber" v-model="practiceNumber">
                </div>
                <div>
                <h4 class="account-h4">Description</h4>
                <input class="login-input" type="text" :placeholder="description" v-model="description">
                </div>
                <div>
                <h4 class="account-h4">Speciality</h4>
                <input class="login-input" type="text" :placeholder="speciality" v-model="speciality">
                </div>
                <div>
                <h4 class="account-h4">Password</h4>
                <input class="login-input" type="Password" v-model="password">
                </div>
            </div>

            <!-- Counsellor practice details -->
            <div v-show="!isPsychologist">
                <div>
                <h4 class="account-h4">Practice Number</h4>
                <input class="login-input" type="text" :placeholder="counsellorPracNum" v-model="counsellorPracNum">
                </div>
                <div>
                <h4 class="account-h4">Highest Certification</h4>
                <input class="login-input" type="text" :placeholder="highestCert" v-model="highestCert">
                </div>
                <div>
                <h4 class="account-h4">Password</h4>
                <input class="login-input" type="Password" v-model="password">
                </div>
            </div>

            <p class="mb-0" :class="success ? 'text-success' : 'text-danger'" >{{ outcome }}</p>
            <button ID="Submit" class="btn no-shadow account-button" @click="isPsychologist ? updatePsych() : updateCounsellor()" :disabled="!detailsFilled">Update Details</button>

            </div>
        </div>
        </div>

        <div class="col-xl-4 float-left">
            <div class="card">
                <div class="card-header bg-transparent">
                    <div class="row align-items-center">
                        <div class="col">
                            <h5 class="h3 mb-0">Availability</h5>
                        </div>
                        <div class="col">
                            <div class="nav nav-pills justify-content-end">
                                <button type="button" class="btn btn-primary btn-sm" style="color:white;" @click="redirect('/#/editcalendar')">Edit Hours</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <v-simple-table style="color: #022F5B">
                        <template v-slot:default>
                        <tbody>
                            <tr v-for="(item, i) in calendar" :key="i">
                                <td>{{ item.dayOfWeek }}</td>
                                
                                <td v-if="item.single.closed">Closed</td>
                                <td v-else-if="item.single.start == null || item.single.end == null || item.single.start == '00:00'">
                                    No single sessions
                                </td>
                                <td v-else>{{ item.single.start + ' - ' + item.single.end}}</td>
                                
                                <td v-if="item.repeat.closed">Closed</td>
                                <td v-else-if="item.repeat.start == null || item.repeat.end == null || item.repeat.start == '00:00'">
                                    No repeat sessions
                                </td>
                                <td v-else>{{ item.repeat.start + ' - ' + item.repeat.end}}</td>
                            </tr>
                        </tbody>
                        </template>
                    </v-simple-table>
                </div>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
import moment from 'moment'

export default {    

  computed: {
    detailsFilled() {
      if(this.psychologist){
        return (!!this.address && !!this.qualification && !!this.practiceNumber && !!this.description && !!this.speciality && !!this.password)
      }

      return (!!this.counsellorPracNum && !!this.highestCert && !!this.password)
    }
  },

  data() {
    return {
      counsellor:   null,
      psychologist: null,

      address:        '',
      qualification:  '',
      practiceNumber: '',
      description:    '',
      speciality:     '',

      counsellorPracNum: '',
      highestCert:       '',

      isPsychologist: false,
      password:       '',
      outcome:        '',
      success:        false,

      snackbar:     false,
      snackbarText: 'Successfully updated',
      timeout:      5000,

        calendar:
        [
            {
                dayOfWeek: 'Mon',
                single: 
                {
                    closed: false,
                    start: '08:00',
                    end: '17:00'
                },
                repeat:
                {
                    closed: false,
                    start: '08:00',
                    end: '17:00'
                }
            },
            {
                dayOfWeek: 'Tue',
                single: 
                {
                    closed: false,
                    start: '08:00',
                    end: '17:00'
                },
                repeat:
                {
                    closed: false,
                    start: '08:00',
                    end: '17:00'
                }
            },
            {
                dayOfWeek: 'Wed',
                single: 
                {
                    closed: false,
                    start: '08:00',
                    end: '17:00'
                },
                repeat:
                {
                    closed: false,
                    start: '08:00',
                    end: '17:00'
                }
            },
            {
                dayOfWeek: 'Thu',
                single: 
                {
                    closed: false,
                    start: '08:00',
                    end: '17:00'
                },
                repeat:
                {
                    closed: false,
                    start: '08:00',
                    end: '17:00'
                }
            },
            {
                dayOfWeek: 'Fri',
                single: 
                {
                    closed: false,
                    start: '08:00',
                    end: '17:00'
                },
                repeat:
                {
                    closed: false,
                    start: '08:00',
                    end: '17:00'
                }
            },
            {
                dayOfWeek: 'Sat',
                single: 
                {
                    closed: false,
                    start: '08:00',
                    end: '17:00'
                },
                repeat:
                {
                    closed: false,
                    start: '08:00',
                    end: '17:00'
                }
            },
            {
                dayOfWeek: 'Sun',
                single: 
                {
                    closed: false,
                    start: '08:00',
                    end: '17:00'
                },
                repeat:
                {
                    closed: false,
                    start: '08:00',
                    end: '17:00'
                }
            },
            {
                dayOfWeek: 'PH',
                single: 
                {
                    closed: false,
                    start: '08:00',
                    end: '17:00'
                },
                repeat:
                {
                    closed: false,
                    start: '08:00',
                    end: '17:00'
                }
            },
        ]
    }
  },

  async mounted() {
    const user = JSON.parse(window.localStorage.getItem('currentUser'))
    this.isPsychologist = (user.userType === 'Psychologist')

    await this.fetchPractitioner(user)

    await this.updateCalendar()
  },

  methods: {
    async fetchPractitioner(user) {
      var resp

      if(this.isPsychologist) {
        const requestParam = {
          PsychID: user.userID
        }
        //get psychologist using loggedIn.userID
        resp = await this.$store.dispatch('auth/fetchPsychologist', requestParam)

        if(resp.psychID > 0){
          this.psychologist   = resp
          this.address        = this.psychologist.address
          this.qualification  = this.psychologist.qualification
          this.practiceNumber = this.psychologist.regNumber
          this.description    = this.psychologist.description
          this.speciality     = this.psychologist.speciality
        }
        else{
          window.alert("Could not find psychologist")
        }
      }
      else {
         const requestParam = {
          CounsellorID: user.userID
        }

        resp = await this.$store.dispatch('auth/fetchCounsellor', requestParam)
        if(resp.counsellorID > 0){
          this.counsellor        = resp
          this.counsellorPracNum = this.counsellor.practiceNum
          this.highestCert       = this.counsellor.highestCertificate
        }
        else{
          window.alert("Could not find counsellor")
        }
      }
    },

    async updatePsych() {
      const requestParams = {
        PsychID:       this.psychologist.psychID,
        Address:       this.address,
        Qualification: this.qualification,
        RegNumber:     this.practiceNumber,
        Description:   this.description,
        Speciality:    this.speciality
      }

      const resp = await this.$store.dispatch('auth/updatePsychologist', requestParams)

      this.outcome = resp
      if(resp.includes('Successfully')) {
        this.success = true
      }
    },

     async updateCounsellor() {
      const requestParams = {
        CounsellorID:       this.counsellor.counsellorID,
        PracticeNum:        this.counsellorPracNum,
        HighestCertificate: this.highestCert,
      }

      const resp = await this.$store.dispatch('auth/updateCounsellor', requestParams)

      this.outcome = resp
      if(resp.includes('Successfully')) {
        this.success = true
      }
    },

    redirect: function(link) {
      window.location.href = link;
    },

    async updateCalendar() {
        const user = JSON.parse(window.localStorage.getItem('currentUser'))
        const psychIDParam = {
            userID: user.userID
        }
        this.tempCalendars = await this.$store.dispatch('psychologists/getPsychologistCalendars', psychIDParam)
        for (let k = 0; k < 8; k++)
        {
            this.calendar[k].dayOfWeek = this.tempCalendars[k].dayOfWeek
            if(this.tempCalendars[k].singleStart == '2021-06-28T00:00:00')
            {
                this.calendar[k].single.start = null
            }
            else
            {
                this.calendar[k].single.start = this.formatTime(this.tempCalendars[k].singleStart)
            }

            if(this.tempCalendars[k].singleEnd == '2021-06-28T00:00:00')
            {
                this.calendar[k].single.end = null
            }
            else
            {
                this.calendar[k].single.end = this.formatTime(this.tempCalendars[k].singleEnd)
            }
            
            if(this.tempCalendars[k].repeatStart == '2021-06-28T00:00:00')
            {
                this.calendar[k].repeat.start = null
            }
            else
            {
                this.calendar[k].repeat.start = this.formatTime(this.tempCalendars[k].repeatStart)
            }
            
            if(this.tempCalendars[k].repeatEnd == '2021-06-28T00:00:00')
            {
                this.calendar[k].repeat.end = null
            }
            else
            {
                this.calendar[k].repeat.end = this.formatTime(this.tempCalendars[k].repeatEnd)
            }
            
            this.calendar[k].closed = this.tempCalendars[k].closed
        }
    },

    formatTime(dateTime) {
        return moment(dateTime).format('HH:mm')
    },

  },
}
</script>

<style scoped>

.login-input {
  background-color: #FBF5F2;
  border: none;
  border-radius: .375rem;
  padding: 12px 15px;
  margin-bottom: 8px;
  margin-top: 2px;
  width: 100%;
}
.account-h4 {
  font-family: inherit;
  font-weight: 600;
  line-height: 1.5;
  color: #32325d;
  margin-top: .5rem;
  margin-bottom: 0px;
}

.account-button {
  border-radius: .375rem;
  border: 1px solid #FF9565;
  background-color: #FF9565;
  color: #FFFFFF;
  font-size: 15px;
  font-weight: bolder;
  padding: 12px 45px;
  letter-spacing: 1px;
  text-transform: uppercase;
  transition: transform 80ms ease-in;
  width: 100%;
  margin-top: 10px;
}

    .account-button:active {
        transform: scale(0.95);
    }

    .account-button:focus {
        outline: none;
    }

    .account-button.ghost {
        background-color: transparent;
        border-color: #FFFFFF;
    }

.no-shadow {
    box-shadow: none;
}

</style>
