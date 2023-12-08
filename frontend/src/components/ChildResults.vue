<template>
    <div>
        <v-app>
            <div class="row">
                <div class="col-xl-8">
                    <div class="card">
                        <div class="card-body">    
                            <div class="patient-overview">
                                <div class="leftGroup">
                                    <div class="card-body">
                                        <h2 style="color: white">
                                            <v-avatar color="white">
                                                <v-icon color="#FF9565">
                                                    mdi-format-list-checks
                                                </v-icon>
                                            </v-avatar>
                                            &nbsp;&nbsp;
                                            {{ test }}
                                        </h2>
                                        <div class="container">
                                            <div class="row">
                                                <div class="col textAlignCenter">
                                                    <div class="numberFont">{{ numQuestions }}</div>
                                                    <div>Questions</div>
                                                </div>
                                                <div class="col textAlignCenter">
                                                    <div class="numberFont">{{ child.score + '/' + testTotal }}</div>
                                                    <div>Total Score</div>
                                                </div>
                                                <div class="col textAlignCenter">
                                                    <div class="numberFont">{{ dateAssigned }}</div>
                                                    <div>Date Assigned</div>
                                                </div>
                                                <div class="col textAlignCenter">
                                                    <div class="numberFont">{{ dateCreated }}</div>
                                                    <div>Date Created</div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="chat-button">
                                            <button type="button" class="btn btn-outline-primary inverseButtons" @click="newRedirect('results', testID)">
                                                <span class="fas fa-arrow-left"></span>  Back to Results
                                            </button>
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
                                    <h3 class="mb-0">Question Report</h3>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <v-expansion-panels>
                                <v-expansion-panel
                                v-for="(item, index) in questions"
                                :key="index">
                                    <v-expansion-panel-header>
                                        {{ item.question }}
                                    </v-expansion-panel-header>
                                    <v-expansion-panel-content>
                                        <!-- <v-card style="text-align: center; color: #022F5B"> -->
                                            <!-- <v-card-text class="text-center" style="color: #022F5B"> -->
                                                <div class="row" style="color: #022F5B">
                                                    <div class="col">
                                                        <strong style="font-size: 18px">{{ child.name }}'s Answer:</strong>
                                                        <br/>
                                                        All Answers:
                                                    </div>
                                                    <div class="col">
                                                        <strong style="font-size: 18px">{{ selectedAnswers[index] }}</strong>
                                                        <div v-for="i in item.allAnswers" :key="i.answerID">
                                                            {{ i.answer}}
                                                        </div>
                                                    </div>
                                                </div>
                                            <!-- </v-card-text> -->
                                        <!-- </v-card> -->
                                    </v-expansion-panel-content>
                                </v-expansion-panel>
                            </v-expansion-panels>
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
                                    @click="redirect('/#/profile?id=' + child.ID)">
                                        <span class="fas fa-user"></span> View Profile
                                    </button>
                                    <button
                                    type="button"
                                    class="btn btn-outline-primary ignoreThisBitch"
                                    @click="redirect('/guardian?gurdianID=' + guardianID)"
                                    disabled>
                                        <span class="fas fa-user"></span> Guardian
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h3 class="mb-0">Others Tests Answered</h3>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <div v-if="otherTests.length > 0">
                                <v-simple-table style="color: #022f5b">
                                    <template v-slot:default>
                                        <tbody>
                                            <tr
                                            v-for="item in otherTests"
                                            :key="item.id"
                                            @click="redirect('/#/childresults?id=' + item.id)"
                                            class="cursorClick" >
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
                                <v-card-subtitle>Check back later once {{ child.name }} has completed more tests</v-card-subtitle>
                            </v-card>
                        </div>
                    </div>
                </div>
            </div>
        </v-app>
    </div>
</template>

<script>
import moment from "moment"

export default {
    data() {
        return {
            psychID: 0,

            child:
            {
                ID: 0,
                name: 'Nathan Drake',
                profilePicture: '',
                age: 'Not Specified',
                grade: 'Not Specified',
                lastAppointment: '13 September 2021',
                guardian: 'Gerrie Castro',
                guardianID: 0,
                score: 20,
            },

            testID: 0,
            standardTest: false,
            test: 'Depression Inventory',
            dateCreated: '01 May 2021',
            dateAssigned: '14 September 2021',
            numQuestions: 10,
            testTotal: 50,

            otherTests:
            [
                // {
                //     id: 1,
                //     name: 'CSC ST2',
                //     date: '14 August 2021'
                // },
                // {
                //     id: 2,
                //     name: 'CSC ST2',
                //     date: '14 August 2021'
                // },
            ],

            selectedAnswers: [],

            questions:
            [
                {
                    question: 'How are you?',
                    selectedAnswer: 'Peanut butter',
                    allAnswers: 
                    [
                        {
                            answerID: 1,
                            answer: 'Peanut butter',
                        },
                        {
                            answerID: 2,
                            answer: 'd',
                        },
                        {
                            answerID: 3,
                            answer: 'a',
                        },
                        {
                            answerID: 4,
                            answer: 'g',
                        }
                    ],
                },
            ],
        }
    },

    async mounted() {
        this.psychID = parseInt(JSON.parse(window.localStorage.getItem("currentUser")).userID)
        this.childTestID = parseInt(this.$route.query.id)
        await this.getChildTest()
        await this.getChild()
        await this.getTest()
        await this.getChildTestAnswers()
        await this.fetchAllChildPsychTests()
    },

    methods: {

        redirect: function(link) {
            window.location.href = link;
            window.location.reload();
        },

        async fetchAllChildPsychTests()
        {
            const testParams = {
                childID: this.child.ID,
                psychID: this.psychID 
            }

            const resp = await this.$store.dispatch('tests/fetchAllChildPsychTests', testParams)
            
            resp.forEach(async element => {
                var test = await this.getTestName(element.testID) 
                if(element.testID != 1 && element.testID != this.testID)
                {
                    var temp =
                    {
                        id: element.childTestID,
                        name: test.name,
                        date: moment(element.date).format('DD MMMM YYYY')
                    }
                    this.otherTests.push(temp)
                }
            });
        },

        async getTestName(testID)
        {
            return await this.$store.dispatch('tests/getTest', testID)
        },

        async getChildTest()
        {
            const childTest = await this.$store.dispatch('tests/getChildTest', this.childTestID)
            
            this.dateAssigned = moment(childTest.date).format('DD MMMM YYYY')
            this.child.ID = parseInt(childTest.childID)
            this.testID = childTest.testID
        },
        
        async getChild()
        {
            const childParams = {
                childID: this.child.ID,
                psychID: this.psychID 
            }
            
            const childUser = await this.$store.dispatch('users/GetChild', childParams)
            this.child.name = childUser.name
            
            const images    = require.context("../assets/img/", false, /\.(png|jpe?g|svg)$/)
            this.child.profilePicture = images(`./${childUser.profilePicture}`)

            // this.child.age = moment().diff(moment(childUser.dob), 'years')
            // this.child.grade = childUser.grade
            var dob
            if(childUser.dob != null)
            {
                dob = childUser.dob.substring(0, 10)
                this.child.age = moment().diff(dob, 'years');  
            }
            if(childUser.grade != null)
                this.child.grade = childUser.grade

            
            const childIDParams = {
                childID: this.child.ID, 
            }
            var guardian = await this.$store.dispatch('users/GetGuardian', childIDParams)
            this.child.guardian = guardian.name
            this.child.guardianID = guardian.userID

            var lastAppointment = await this.$store.dispatch('bookings/GetLastAppointmentDate', this.child.ID)
            this.child.lastAppointment = moment(lastAppointment).format('DD MMMM YYYY')
            if(this.child.lastAppointment == '01 January 0001')
            {
                this.child.lastAppointment = "N/A"
            }

            this.child.score = await this.$store.dispatch('tests/getTotalAnswerScore', this.childTestID)
        },

        newRedirect(page, id) {
            if(id != undefined)
            {
                this.$router.push({ path: page, query: { id: id } })
            }
        },

        async getTest()
        {
            const testParams = {
                testID: this.testID, 
            }
            const test = await this.$store.dispatch('tests/getTest', this.testID)
            this.test = test.name
            if(test.psychID == null)
            {
                this.standardTest = true
            }
            this.dateCreated = moment(test.dateCreated).format('DD MMMM yyyy')
            this.lastEdited = moment(test.lastEdited).format('DD MMMM yyyy')
            this.testTotal = test.total

            var questions = await this.$store.dispatch('tests/getTestQuestions', testParams)
            this.numQuestions = questions.length
            var tempQuestions = []
            
            questions.forEach(async (element) => {
                var tempAnswers = await this.getAnswers(element.questionID)
                var temp =
                {
                    question: element.question,
                    allAnswers: tempAnswers,
                }
                tempQuestions.push(temp)
            });
            this.questions = tempQuestions         
        },

        async getAnswers(id)
        {
            var tempAnswers = []
            const answers = await this.$store.dispatch('tests/getQuestionAnswers', id)
            
            answers.forEach(element => {
                var temp =
                {
                    answerID: element.answerID,
                    answer: element.answer,
                    position: element.position,
                    answerValue: element.answerValue
                }
                tempAnswers.push(temp)
            })
            return tempAnswers
        },

        async getChildTestAnswers()
        {
            const resp = await this.$store.dispatch('tests/getChildTestAnswersWithID', this.childTestID)
         
            //resp.sort((a, b) => parseInt(a.position) - parseInt(b.position))
            resp.forEach(element => {
                this.selectedAnswers.push(element.answer)
            });
        }
    }
}
</script>

<style scoped>

    .cursorClick
    {
        cursor: pointer;
    }

    .leftGroup
    {
        width: 100%;
    }

    .inverseButtons
    {
        color: var(--orange);
        background: white;
        border-radius: 50px;
    }

    .user-card {
        color: white;
        background: var(--orange);
        border-radius: 15px;
        padding: 10px 0px 0px 10px;
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
    .numberFont
    {
        font-size: 20px;
    }

    .textAlignCenter
    {
        text-align: center;
    }

    .leftGroup 
    {
        color: white;
        background: var(--orange);
        border-radius: 15px;
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
    
    @media only screen and (max-width: 1400px) {
        .ignoreThisBitch {
            width: auto;
        }
    }
</style>