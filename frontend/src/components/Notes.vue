<template>
  <div>
    <v-app>
      <div class="row">
        <div class="col-xl-8">
          <div class="card">
            <div class="card-header">
              <div class="row align-items-center">
                <div class="col">
                  <h3 class="mb-0">Notes</h3>
                </div>
                <div class="col">
                  <div class="nav nav-pills justify-content-end">
                    <button
                      type="button"
                      class="btn btn-primary btn-sm"
                      style="color: white"
                      @click="openEditor = !openEditor"
                    >
                      <div v-if="openEditor === true">Cancel Note</div>
                      <div v-else>Add Note</div>
                    </button>

                  </div>
                </div>
              </div>
            </div>
            <div class="card-body">
              <div v-show="openEditor">
                <quill-editor
                  ref="myTextEditor"
                  v-model="content"
                  :options="editorOption"
                >
                </quill-editor>
                <br />
                <div v-show="content != ''">
                  <v-expansion-panels v-model="panel">
                    <v-expansion-panel>
                      <v-expansion-panel-header
                        >Select Note Type</v-expansion-panel-header
                      >
                      <v-expansion-panel-content>
                        <v-item-group mandatory>
                          <v-container>
                            <v-row>
                              <v-col cols="12" md="3">
                                <v-item>
                                  <v-card
                                    :color="appointActive ? '#FF9565' : 'white'"
                                    class="d-flex align-center"
                                    dark
                                    height="100"
                                    @click="
                                      appointActive = true
                                      testActive = false
                                      activityActive = false
                                      feedbackActive = false
                                      panel = 1
                                      noteType = 'Appointment'
                                    "
                                  >
                                    <div
                                      class="text-h6 flex-grow-1 text-center"
                                      :style="
                                        appointActive
                                          ? 'color:white'
                                          : 'color:#FF9565'
                                      "
                                    >
                                      Appointment
                                    </div>
                                  </v-card>
                                </v-item>
                              </v-col>
                              <v-col cols="12" md="3">
                                <v-item>
                                  <v-card
                                    :color="testActive ? '#FF9565' : 'white'"
                                    class="d-flex align-center"
                                    dark
                                    height="100"
                                    @click="
                                      appointActive = false
                                      testActive = true
                                      activityActive = false
                                      feedbackActive = false
                                      panel = 1
                                      noteType = 'Test'
                                    "
                                  >
                                    <div
                                      class="text-h6 flex-grow-1 text-center"
                                      :style="
                                        testActive
                                          ? 'color:white'
                                          : 'color:#FF9565'
                                      "
                                    >
                                      Test
                                    </div>
                                  </v-card>
                                </v-item>
                              </v-col>
                              <v-col cols="12" md="3">
                                <v-item>
                                  <v-card
                                    :color="
                                      activityActive ? '#FF9565' : 'white'
                                    "
                                    class="d-flex align-center"
                                    dark
                                    height="100"
                                    @click="
                                      appointActive = false
                                      testActive = false
                                      activityActive = true
                                      feedbackActive = false
                                      panel = 1
                                      noteType = 'Activity'
                                    "
                                  >
                                    <div
                                      class="text-h6 flex-grow-1 text-center"
                                      :style="
                                        activityActive
                                          ? 'color:white'
                                          : 'color:#FF9565'
                                      "
                                    >
                                      Activity
                                    </div>
                                  </v-card>
                                </v-item>
                              </v-col>
                              <v-col cols="12" md="3">
                                <v-item>
                                  <v-card
                                    :color="
                                      feedbackActive ? '#FF9565' : 'white'
                                    "
                                    class="d-flex align-center"
                                    dark
                                    height="100"
                                    @click="
                                      appointActive = false
                                      testActive = false
                                      activityActive = false
                                      feedbackActive = true
                                      noteType = 'Feedback'">
                                    <div
                                      class="text-h6 flex-grow-1 text-center"
                                      :style="
                                        feedbackActive ? 'color:white' : 'color:#FF9565'">
                                      Feedback to Guardian
                                    </div>
                                  </v-card>
                                </v-item>
                              </v-col>
                            </v-row>
                          </v-container>
                        </v-item-group>
                      </v-expansion-panel-content>
                    </v-expansion-panel>
                    <v-expansion-panel
                      v-show="
                        appointActive === true ||
                        testActive === true ||
                        activityActive === true
                      "
                    >
                      <v-expansion-panel-header
                        >Select {{ noteType }}</v-expansion-panel-header
                      >
                      <v-expansion-panel-content>
                        <v-card
                          class="scroll"
                          max-height="240"
                          v-show="appointActive"
                        >
                          <v-list dense>
                            <v-list-item-group
                              v-model="appointmentSelected"
                              mandatory
                              color="#FF9565"
                            >
                              <v-list-item
                                v-for="(item, i) in appointments"
                                :key="i"
                              >
                                <v-list-item-content>
                                  <div class="appointment-card">
                                    <div class="circle-line">
                                      <div class="circle"></div>
                                    </div>
                                    <div class="textNextLine">
                                      {{ item.date }}
                                    </div>
                                    <span class="grey-line"></span>
                                    <div class="textNextLine">
                                      {{ item.time }}
                                    </div>
                                    <span class="grey-line"></span>
                                    <div class="consultation-type">
                                        <h6 class="text-uppercase text-muted ls-1 mb-1">Focus</h6>
                                        <v-chip
                                        v-if="item.focus != 'Not Specified'"
                                        color="#11cdef"
                                        outlined
                                        label >
                                            {{ item.focus }}
                                        </v-chip>
                                        <v-chip
                                        v-else
                                        outlined
                                        label >
                                            {{ item.focus }}
                                        </v-chip>
                                    </div>
                                  </div>
                                </v-list-item-content>
                              </v-list-item>
                            </v-list-item-group>
                          </v-list>
                        </v-card>
                      </v-expansion-panel-content>
                    </v-expansion-panel>
                  </v-expansion-panels>
                </div>

                <br />
                <v-card-actions
                  v-show="
                    appointActive === true ||
                    testActive === true ||
                    activityActive === true ||
                    feedbackActive === true
                  "
                >
                  <v-spacer></v-spacer>
                  <button
                    type="button"
                    class="btn btn-secondary"
                    @click="openEditor = false"
                  >
                    Cancel
                  </button>
                  <button
                    type="button"
                    class="btn btn-primary"
                    @click="saveNotes()"
                  >
                    Save
                  </button>
                </v-card-actions>
                <!-- <div class="text-right"  v-show="content != ''">
                                <button type="button" class="btn btn-secondary" @click="openEditor = false">Cancel</button>
                                <button type="button" class="btn btn-primary">Save</button>
                            </div> -->
                <v-divider></v-divider>
              </div>

              <v-card-text class="py-0" v-show="nothingHere == false">
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
                  <img
                    :src="child.profilePicture"
                    alt=""
                    class="avatar rounded-circle"
                    style="width: 60px height: 60px"
                  />
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
                    @click="redirect('/#/profile?id=' + child.id)"
                  >
                    <span class="fas fa-user"></span> View Profile
                  </button>
                <!--  <button
                    type="button"
                    class="btn btn-outline-primary ignoreThisBitch"
                    @click="redirect('/guardian?gurdianID=' + guardianID)"
                    disabled
                  >
                    <span class="fas fa-user"></span> Chat
                  </button>
                </div> -->
                <!-- <div class="chat-button"> -->
                    <button type="button" class="btn btn-outline-primary ignoreThisBitch" @click="chatRedirect(child.id)"><span class="far fa-comment-alt"></span>  Chat</button>
                </div>
              </div>
            </div>
          </div>

          <div class="card">
            <div class="card-header">
              <div class="row align-items-center">
                <div class="col">
                  <h3 class="mb-0">Focus Points</h3>
                </div>
              </div>
            </div>
            <div class="card-body">
              <v-container class="py-0">
                <v-row align="center" justify="start">
                  <v-col
                    v-for="(selection, i) in childFocusPoints"
                    :key="selection.id"
                    class="shrink"
                  >
                    <v-chip
                      :color="colors[i]"
                      close
                      outlined
                      label
                      @click:close="removeFromSelectedFocusPoints(selection)"
                    >
                      {{ selection.problem }}
                    </v-chip>
                  </v-col>

                  <v-col v-if="this.selectablePoints.length" cols="12">
                    <v-text-field
                      ref="search"
                      v-model="search"
                      full-width
                      hide-details
                      label="Search"
                      style="color: #ff9565"
                      single-line
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>

              <v-list style="max-height: 169px" class="overflow-y-auto">
                <template v-for="item in filteredAvailableSelections">
                  <v-list-item
                    :key="item.id"
                    :disabled="loading"
                    @click="addToSelectedFocusPoints(item)"
                  >
                    <v-list-item-title
                      v-text="item.problem"
                      style="color: #022f5b"
                    ></v-list-item-title>
                  </v-list-item>
                </template>
              </v-list>
            </div>
          </div>

          <div class="card" hidden>
            <div class="card-header bg-transparent">
              <div class="row align-items-center">
                <div class="col">
                  <h5 class="h3 mb-0">Documents</h5>
                </div>
                <div class="col">
                  <div class="nav nav-pills justify-content-end">
                    <button
                      type="button"
                      class="btn btn-primary btn-sm"
                      style="color: white"
                      disabled
                    >
                      See All
                    </button>
                  </div>
                </div>
              </div>
            </div>
            <div class="card-body">
              <v-simple-table style="color: #022f5b">
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
          </div>
        </div>
      </div>
    </v-app>
  </div>
</template>

<script>
import moment from "moment"
import FuzzySearch from 'fuzzy-search'

export default {
  data() {
    return {
        nothingHere: false,

      child: {
        id: 0,
        name:            "Nathan Drake",
        profilePicture:  "",
        age:             "Not Specified",
        grade:           "Not Specified",
        guardianID:      "1",
        guardian:        "Gerrie Castro",
        lastAppointment: "N/A",
      },

      noteType: "",
      panel:    0,

      appointments: 
      [
        // {
        //   id:    2,
        //   date:  "24 April 2021",
        //   time:  "09:00",
        //   focus: "Depression",
        // },
      ],
      appointmentSelected: 0,
      appointActive:       false,
      testActive:          false,
      activityActive:      false,
      feedbackActive:      false,
      step:                1,
      content:             "",
      editorOption:        {
        placeholder: "Add new note...",
        modules:     {
          toolbar: [
            [
              {
                size: ["small", false, "large", "huge"]
              }
            ],
            [
              "bold", "italic", "underline", "strike",
              {
                color: []
              }
            ],
            [
              { list: "ordered" },
              { list: "bullet" },
              { align: [] }
            ],
            ["clean"],
          ],
        },
      },
      openEditor: false,
      dialog:     false,

      tempFocus: [],

      colors:   ["#2196F3", "#fb6340", "#11cdef", "#2dce89", "#f5365c"],
      loading:  false,
      search:   "",

      filteredAvailableSelections: [],

      iconsColours: [
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

      //interval: null,
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
      nonce:     2,

      documents: 
      [
        // {
        //   name: "Daily Mood Tracker",
        //   date: "07 August 2021",
        // },
      ],

      tempDocs: [],

      someArray: [],
    }
  },

  computed: {
    childFocusPoints() {
      return this.$store.getters['hub/childFocusPoints']
    },

    selectablePoints() {
      return this.$store.getters['hub/focusPointsAvailableForSelection']
    },
  },

  watch: {
    search(value) {
      this.filteredAvailableSelections = (value === "") ? this.selectablePoints : this.filterAvailableSelections(value)
    },

    selectablePoints() {
      this.filteredAvailableSelections = this.selectablePoints
    }
  },

  async mounted() {
    await this.getChildProfile()
    await this.fetchChildFocusPoints()
    await this.fetchAllFocusPoints()
    
    await this.getNotes()
    await this.getAppointments()
    await this.getTestsDoneWithPsych()
    await this.getAllChildPsychActivities()
    var docs = this.sortArray(this.tempDocs)
    this.documents = docs
  },

  methods: {
    async chatRedirect(childID){
			await this.$store.dispatch('chat/setChattingChild', parseInt(childID))

			this.$router.push({path: 'PsychologistChat'})

		},

    async getTestsDoneWithPsych() {
        const user = JSON.parse(window.localStorage.getItem('currentUser'))
        const urlChildID   = this.$route.query.childID
        this.child.id = urlChildID
        var intChildID = parseInt(urlChildID)

        const childTestParam = {
            childID: intChildID,
            psychID: user.userID 
        }

        const tests = await this.$store.dispatch('tests/getTestsDoneWithPsych', childTestParam)
        if(tests != "")
        {
            
            tests.forEach((test) => {
                var testDate = test.dateCompleted.substring(0, 10)
                testDate = moment(testDate).format('DD MMMM yyyy');
                
                var temp = {
                    id: test.childTestID,
                    name: test.testName,
                    date: testDate,
                }
                
                this.tempDocs.push(temp)
            })
        }
    },

    async getAllChildPsychActivities() {
        const user = JSON.parse(window.localStorage.getItem('currentUser'))
        const urlChildID   = this.$route.query.childID
        var intChildID = parseInt(urlChildID)

        const childActivityParam = {
            childID: intChildID,
            psychID: user.userID 
        }

        const activities = await this.$store.dispatch('activity/getAllChildPsychActivities', childActivityParam)
        
        if(activities != "")
        {
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
        }
    },

    sortArray(array) {
        var sortedArray = array.sort((a,b) => new moment(b.date).format('YYYYMMDD') - new moment(a.date).format('YYYYMMDD'))
        sortedArray.forEach(sArray => {
            sArray.date = moment(sArray.date).format('DD MMMM yyyy')
        })
        return sortedArray
    },

    //refactor this to use router
    redirect: function (link) {
      window.location.href = link
    },

    filterAvailableSelections(value) {
      const searcher = new FuzzySearch(this.selectablePoints, ["problem"], {
        caseSensitive: false,
        sort:          true  
      })

      return searcher.search(value)
    },

    async getNotes() {
        const user         = JSON.parse(window.localStorage.getItem("currentUser"))
        const urlChildID   = this.$route.query.childID
        const intChildID   = parseInt(urlChildID)
        const requestParam = {
            ChildID: intChildID,
            PsychID: user.userID,
        }

        const tempNotes = await this.$store.dispatch("notes/fetchAllChildsNotes", requestParam)
        
        if(tempNotes.length == 0)
        {
            this.nothingHere = true
        }
        else
        {
            this.nothingHere = false
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
    },

    getIconColour(type) {
      var iconColour = {
        icon:  "",
        color: "",
      }

      if (type == "Appointment") {
        iconColour.color = this.iconsColours[0].color
        iconColour.icon  = this.iconsColours[0].icon
      } else if (type == "Test") {
        iconColour.color = this.iconsColours[1].color
        iconColour.icon  = this.iconsColours[1].icon
      } else if (type == "Activity") {
        iconColour.color = this.iconsColours[2].color
        iconColour.icon  = this.iconsColours[2].icon
      } else if (type == "Feedback") {
        iconColour.color = this.iconsColours[3].color
        iconColour.icon  = this.iconsColours[3].icon
      }

      return iconColour
    },

    async saveNotes() {
      const user       = JSON.parse(window.localStorage.getItem("currentUser"))
      const urlChildID = this.$route.query.childID
      const intChildID = parseInt(urlChildID)

      let id   = 0
      let note = this.content

      if (this.noteType == "Appointment") {
        id = this.appointments[this.appointmentSelected].id
        //var date = moment().format('DD MMMM YYYY, h:mm:ss a')
        note =
          '<h2 style="color: white">' +
          this.noteType +
          " on " +
          this.appointments[this.appointmentSelected].date +
          "</h2> " +
          this.content +
          ' <h5 style="color: white; opacity: 0.6">Written on ' +
          moment().format("DD MMMM YYYY")
        +"</h5>"
      }
      if (this.noteType == "Test") {
        note =
          '<h2 style="color: white">[TestType?] on 14 April 2021</h2> ' +
          this.content +
          ' <h5 style="color: white; opacity: 0.6">Written on ' +
          moment().format("DD MMMM YYYY")
        +"</h5>"
      }
      if (this.noteType == "Activity") {
        note =
          '<h2 style="color: white">[ActivityType?] on 14 April 2021</h2> ' +
          this.content +
          ' <h5 style="color: white; opacity: 0.6">Written on ' +
          moment().format("DD MMMM YYYY")
        +"</h5>"
      }
      const requestParams = {
        Feedback:     note,
        DateCreated:  "2021-07-19T23:08:37.102Z",
        Type:         this.noteType,
        PsychID:      user.userID,
        ChildID:      intChildID,
        OtherTableID: id,
      }
      const resp = await this.$store.dispatch("notes/saveNote", requestParams)
      var iconColour = this.getIconColour(resp.type)
     
      var temp = {
        id:    resp.noteID,
        color: iconColour.color,
        icon:  iconColour.icon,
        text:  resp.feedback,
      }
      this.notes.unshift(temp)
      this.content = ""
      this.openEditor = false
      this.nothingHere = false
    },

    isDisabled() {
      if (
        (this.appointActive == false) &
        (this.testActive == false) &
        (this.activityActive == false) &
        (this.feedbackActive == false)
      ) {
        return true
      }
    },

    next() {
      this.loading = true

      setTimeout(() => {
        this.search = ""
        this.selected = []
        this.loading = false
      }, 2000)
    },

    async getChildProfile() {
      const user = JSON.parse(window.localStorage.getItem("currentUser"))

      const urlChildID = parseInt(this.$route.query.childID)

      const childIDParam = {
        childID: urlChildID,
        psychID: user.userID
      }

      const childUser = await this.$store.dispatch("users/GetChild",childIDParam)

      const images    = require.context("../assets/img/", false, /\.(png|jpe?g|svg)$/)
      //var dob       = childUser.dob.substring(0, 10)

      this.child.name           = childUser.name
      this.child.profilePicture = images(`./${childUser.profilePicture}`)
      
        var dob
        if(childUser.dob != null)
        {
            dob = childUser.dob.substring(0, 10)
            this.child.age = moment().diff(dob, 'years');  
        }

        
        if(childUser.grade != null)
            this.child.grade = childUser.grade
    //   this.child.age            = moment().diff(dob, "years")
    //   this.child.grade          = childUser.grade

      this.GetGuardian(urlChildID)
      this.getLastAppointment(urlChildID)
    },

    async GetGuardian(childUserID) {
        const childIDParam = {
            childID: childUserID,
        }
        const guardian = await this.$store.dispatch("users/GetGuardian", childIDParam)

        this.child.guardian   = guardian.name
        this.child.guardianID = guardian.userID
    },

    async getLastAppointment(childUserID) {
        var last = await this.$store.dispatch("bookings/GetLastAppointmentDate", childUserID)
        
        this.child.lastAppointment = this.formatDate(last)
    },

    async fetchAllFocusPoints() {
      await this.$store.dispatch("hub/fetchAllFocusPoints")
    },

    async fetchChildFocusPoints() {
      await this.$store.dispatch('hub/fetchChildFocusPoints', parseInt(this.$route.query.childID))
    },

    async removeFromSelectedFocusPoints(point) {
      await this.$store.dispatch('hub/removeFocusPoint', { childID: parseInt(this.$route.query.childID), problem: point })
    },

    async addToSelectedFocusPoints(point) {
      await this.$store.dispatch('hub/addChildFocusPoint', { childID: parseInt(this.$route.query.childID), problem: point })
    },

    async getAppointments() {
        const user = JSON.parse(window.localStorage.getItem("currentUser"))
        const urlChildID   = this.$route.query.childID
        const intChildID   = parseInt(urlChildID)
        const childIDParam = {
            childID: intChildID,
            psychID: user.userID
        }
        const appointments = await this.$store.dispatch("bookings/getUserBookingsWithPsych", childIDParam)

        appointments.forEach(async (appoint) => {
            if(appoint.cancelled != "Cancelled")
            {
                var date = moment(appoint.date.substring(0, 10)).format("DD MMMM yyyy")
                var time = moment(appoint.date).hour(appoint.time.hours).minutes(appoint.time.minutes).format('HH:mm')
                
                var focus = ''
                if(appoint.problemID == null)
                {
                    focus = 'Not Specified'
                }
                else
                {
                    focus = await this.getRHubProblem(appoint.problemID)
                }
                
                var temp = {
                    id: appoint.bookingID,
                    date: date,
                    time: time,
                    focus: focus,
                }
                this.appointments.unshift(temp)          
            }
        })
        this.appointments = this.sortArray(this.appointments)
    },

    async getRHubProblem(problemID) {
      const problem = await this.$store.dispatch('hub/getRHubProblem', problemID)
      var name = problem.problem
      
      return name
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
    },
}
</script>

<style scoped>
    .user-card {
        color: white;
        background: var(--orange);
        border-radius: 15px;
        padding: 10px 0px 0px 10px;
    }

    .user-card-details {
        padding-bottom: 2rem;
    }

    .theme--light.v-application {
        background: #fbf5f2;
    }

    .v-application--wrap {
        min-height: 0;
    }

    .ignoreThisBitch {
        bottom: 0.5rem;
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

    .theme--light.v-input,
    .theme--light.v-input input,
    .theme--light.v-input textarea {
        color: #022f5b;
    }

    .theme--light.v-label {
        color: rgba(2, 47, 91, 0.6);
    }

    .ql-picker-label {
        color: #022f5b;
    }

    .textNextLine {
        margin: auto;
    }

    .scroll {
        overflow-y: auto;
    }

    .v-list-item-group .v-list-item--active .circle {
        background-color: #ff9565;
    }

    /* .v-list-item-group .v-list-item--active .textNextLine {
            color: #022F5B
        }
        .v-list-item-group .v-list-item--active .textNextLine {
            color: #022F5B
        } */

    .theme--light.v-list-item:not(.v-list-item--active):not(.v-list-item--disabled) {
        color: #022f5b !important;
    }

    html {
        font-family: "Segoe UI Semibold", Tahoma, Geneva, Verdana, sans-serif;
    }

    @media only screen and (max-width: 1400px) {
        .ignoreThisBitch {
            width: auto;
        }
    }
</style>