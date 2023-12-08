<template>
    <div>
        <v-app>
            <div class="row no-gutters alertMargin" v-if="update == 0 || update == 1">
                <div class="col-xl-12">
                    <v-alert
                    type="success"
                    dismissible
                    class="offset-md-4"
                    dense
                    width="200"
                    v-if="update == 0"
                    max-height="70">
                        Successfully saved
                    </v-alert>
                    <v-alert
                    type="success"
                    dismissible
                    class="offset-md-4"
                    dense
                    width="200"
                    v-if="update == 1"
                    max-height="70">
                        Successfully updated
                    </v-alert>
                </div>
            </div>
            <div class="row">
                <div class="col-xl-8">
                    <v-form
                    ref="form"
                    :disabled="standardTest && admin == false"
                    v-model="valid">
                        <div class="card">
                            <div class="card-body">    
                                <div class="card-header bg-transparent" style="border: none">
                                    <div class="row align-items-center">
                                        <div class="col">
                                            <h5 class="h3 mb-0">Test name</h5>
                                            <v-text-field
                                            id="vstep1"
                                            placeholder="Test Name"
                                            color="#FF9565"
                                            required
                                            v-model="test"
                                            :rules="[rules.required]">
                                            </v-text-field>
                                        </div>
                                        <div class="col-xl-2">
                                            <h5 class="h3 mb-0">Total Score</h5>
                                            <!-- <div style="text-align: center;">{{ testTotal }}</div> -->
                                            <v-text-field
                                            color="#FF9565"
                                            class="questionValue totalScoreField"
                                            v-model="testTotal"
                                            readonly 
                                            :disabled="disabledScore">
                                            </v-text-field>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="card">
                            <div class="card-header bg-transparent" style="border: none">
                                <div class="row align-items-center">
                                    <div class="col">
                                        <h5 class="h3 mb-0">Questions</h5>
                                    </div>    
                                    <div class="col text-right">
                                        <button type="button" class="btn btn-sm btn-primary" @click="addQuestion()" :disabled="standardTest && admin == false"><i class="fas fa-plus"></i> Add Question</button>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body">
                                <v-expansion-panels v-model="questions" id="vstep2">
                                    <draggable 
                                    tag="v-expansion-panels" 
                                    v-model="questions"
                                    :component-data="getComponentData()">
                                        <v-expansion-panel 
                                        v-for="(item, index) in questions" 
                                        :key="item.questionID" 
                                        transition="fade-transition">
                                            <v-expansion-panel-header hide-actions>
                                                <div class="questionTextFields">
                                                    <v-text-field
                                                    :label="'Question ' + (index + 1)"
                                                    :placeholder="'Question ' + (index + 1)"
                                                    color="#FF9565"
                                                    v-model="item.question"
                                                    solo
                                                    full-width
                                                    :rules="[rules.required]"
                                                    @click.stop
                                                    @keyup.prevent>
                                                    <template v-slot:prepend>
                                                        <v-icon>
                                                            mdi-cursor-move
                                                        </v-icon>
                                                    </template>
                                                    <template v-slot:append-outer>
                                                        <v-icon
                                                        color="red"
                                                        :disabled="standardTest && admin == false"
                                                        @click="removeQuestion(index, item.questionID)">
                                                            mdi-trash-can-outline
                                                        </v-icon>
                                                    </template>
                                                    </v-text-field>
                                                </div>
                                            </v-expansion-panel-header>
                                            <v-expansion-panel-content class="centerAnswers">
                                                <h3 class="mb-2">Answers</h3>
                                                <v-card 
                                                width="93%"
                                                class="centerAnswers"
                                                flat >
                                                    <draggable :list="item.answers" group="cards">
                                                        <v-card
                                                        class="answerTextFields"
                                                        flat
                                                        v-for="(answerItem, i) in item.answers" 
                                                        :key="i"
                                                        transition="fade-transition">
                                                            <v-row>
                                                                <v-text-field 
                                                                v-model="answerItem.answer"
                                                                ref="answerText"
                                                                solo
                                                                clearable
                                                                required
                                                                :rules="[rules.required]"
                                                                color="#FF9565"
                                                                :label="'Answer ' + (i + 1)"
                                                                :placeholder="'Answer ' + (i + 1)">
                                                                    <template v-slot:prepend>
                                                                        <v-icon class="moveCursor">
                                                                            mdi-cursor-move
                                                                        </v-icon>
                                                                    </template>
                                                                </v-text-field>
                                                                &nbsp;&nbsp;
                                                                <v-card
                                                                flat
                                                                width="15%">
                                                                    <v-text-field
                                                                    v-model="answerItem.answerValue"
                                                                    solo
                                                                    type="tel"
                                                                    class="questionValue"
                                                                    readonly
                                                                    :disabled="disabledScore">
                                                                        <template v-slot:append>
                                                                            <v-icon
                                                                            color="#FF9565"
                                                                            :disabled="answerItem.answerValue == 5 || disabledScore" 
                                                                            @click="increment(answerItem.answerValue, index, i)">
                                                                                mdi-plus
                                                                            </v-icon>
                                                                        </template>
                                                                        <template v-slot:prepend-inner>
                                                                            <v-icon
                                                                            color="#FF9565"
                                                                            :disabled="answerItem.answerValue == 0 || disabledScore" 
                                                                            @click="decrement(answerItem.answerValue, index, i)">
                                                                                mdi-minus
                                                                            </v-icon>
                                                                        </template>
                                                                    </v-text-field>
                                                                </v-card>
                                                            </v-row>
                                                        </v-card>
                                                    </draggable>
                                                </v-card>
                                            </v-expansion-panel-content>
                                        </v-expansion-panel>
                                    </draggable>
                                </v-expansion-panels>
                            </div>
                        </div>
                    </v-form>
                </div>

                <div class="col-xl-4">
                    <div class="card" >
                        <div class="card-body">
                            <h2 class="h2 mb-5">Test Summary</h2>
                            <div class="row marginTop5" id="vstep3">
                                <div class="col centerCol">
                                    <span class="numberBackground">{{ questions.length }}</span> 
                                    <div>Questions</div>
                                </div>
                                <div class="col centerCol" v-show="admin == false">
                                    <span class="numberBackground">{{ numResponses }}</span> 
                                    <div>Responses</div>
                                    <span v-show="newTest == false && numResponses != 0 && admin == false" class="viewResults cursorClick" @click="newRedirect('results', testID)"><v-icon>mdi-eye</v-icon> View Results</span>
                                </div>
                                <!-- redirect elsewhere for mood tracker results -->
                                <div v-show="moodTracker == false" class="col centerCol">
                                    <span class="numberBackground">{{ numAssigned }}</span> 
                                    <div>Assigned</div>
                                    <v-dialog
                                    v-model="dialog"
                                    width="500" >
                                        <template v-slot:activator="{ on, attrs }">
                                            <span
                                            v-show="newTest == false && admin == false" 
                                            class="viewResults cursorClick"
                                            @click="getChildTestAssignedStatus()"
                                            v-bind="attrs"
                                            v-on="on">
                                                <v-icon>mdi-account-plus</v-icon>
                                                Assign to Child
                                            </span>
                                        </template>
                                        <v-card>
                                            <div class="text-center">
                                                <br/>
                                                <h2>Assign Test to Child</h2>
                                            </div>
                                            <v-col
                                            cols="12" >
                                                <v-text-field
                                                    ref="search"
                                                    v-model="search"
                                                    full-width
                                                    hide-details
                                                    label="Search"
                                                    single-line >
                                                </v-text-field>
                                            </v-col>
                                            <v-list style="max-height: 214px" class="overflow-y-auto">
                                                <template v-for="item in categories">
                                                    <v-list-item
                                                    :key="item.childID"
                                                    @click="item.isAssigned = !item.isAssigned">
                                                        <v-checkbox
                                                            v-model="item.isAssigned"
                                                            color="#FF9565"
                                                            @change="item.isAssigned = !item.isAssigned">
                                                        </v-checkbox>
                                                        <v-list-item-avatar>
                                                            <v-img :src="item.profilePicture"></v-img>
                                                        </v-list-item-avatar>
                                                        &nbsp;&nbsp;
                                                        <v-list-item-title v-text="item.childName"></v-list-item-title>
                                                    </v-list-item>
                                                </template>
                                            </v-list>
                                            <v-divider></v-divider>

                                            <v-card-actions>
                                                <v-spacer></v-spacer>
                                                    <button type="button" class="btn btn-secondary" @click="dialog = false">Close</button>
                                                    <button type="button" class="btn btn-primary" @click="saveAssign()">Save</button>
                                            </v-card-actions>
                                        </v-card>
                                    </v-dialog>
                                </div>
                            </div>
                            <v-divider></v-divider>
                            <div class="row">
                                <div class="col centerCol">
                                    <h3 class="mb-5" style="color:#FF9565">Date Created</h3> 
                                    <span class="">{{ dateCreated }}</span>
                                </div>
                                <div class="col centerCol">
                                    <h3 class="mb-5" style="color:#FF9565">Last Edit</h3> 
                                    <span class="">{{ dateEdited + timeEdited}}</span>
                                </div>
                            </div> 
                            <v-divider></v-divider>
                            <div class="mb-5 centerCol">
                                <h3 style="color:#FF9565">Actions</h3>
                                <br/>
                                <div class="row justifyCenter">
                                    <!-- <button type="button" class="btn btn-sm btn-outline-primary ignoreThisBitch" :disabled="newTest"><span class="far fa-clipboard"></span> Duplicate</button> -->
                                    <button type="button" class="btn btn-sm btn-outline-primary ignoreThisBitch" :disabled="standardTest || disabledScore" @click="disableScore()"><span class="far fa-star"></span> Disable Score</button>
                                    <button type="button" class="btn btn-sm btn-outline-primary ignoreThisBitch" disabled><span class="far fa-clipboard"></span> Duplicate</button>
                                    <button type="button" class="btn btn-sm btn-outline-primary ignoreThisBitch" :disabled="standardTest && admin == false" @click="deleteTest(testID)"><span class="far fa-trash-alt"></span> Delete</button>
                                </div>
                            </div>
                            <v-divider></v-divider>
                            <div class="row justifyCenter">
                                <button type="button" class="btn btn-block btn-primary" @click="saveChanges()" :disabled="(standardTest || valid == false) && admin == false">Save Changes</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </v-app>
        <v-tour name="myTour" :steps="steps" :callbacks="myCallbacks"></v-tour>

    </div>
</template>

<script>
    import moment from 'moment'
    import draggable from 'vuedraggable';

export default {

    data: function () {
        return {
            disabledScore: false,
            numAssigned: 0,
            search: '',
            selected: [],
            dialog: false,
            moodTracker: false,
            newTest: false,
            standardTest: false,
            update: '',
            numResponses: 0,
            bpm: 0,
            psychID: 0,
            testID: 0,
            test: '',
            testTotal: 0,
            dateCreated: '15 October 2020',
            dateEdited: '14 August 2021',
            timeEdited: '12:24',

            formHasErrors: false,
            valid: false,
            rules: 
            {
                required: value => !!value || 'Required.',
            },

            drag: false,
            
            questions:
            [
                // {
                //     questionID: 1,
                //     question: 'safaw',
                //     position: 3,
                //     answers: 
                //     [
                //         {
                //             answerID: 1,
                //             answer: 'Peanut butter',
                //             position: 1,
                //             answerValue: 5,
                //         },
                //         {
                //             answerID: 2,
                //             answer: 'd',
                //             position: 2,
                //             answerValue: 5,
                //         },
                //         {
                //             answerID: 3,
                //             answer: '',
                //             position: 3,
                //             answerValue: 5,
                //         },
                //         {
                //             answerID: 4,
                //             answer: '',
                //             position: 4,
                //             answerValue: 5,
                //         }
                //     ],
                // },
            ],

            childrenList:
            [
                // {
                //     childID: 0,
                //     childName: 'Nathan',
                //     profilePicture: 'https://cdn.vuetifyjs.com/images/john.jpg',
                //     isAssigned: false,
                // },
                // {
                //     childID: 1,
                //     childName: 'Ibrahim',
                //     profilePicture: 'https://cdn.vuetifyjs.com/images/john.jpg',
                //     isAssigned: true,
                // },
                // {
                //     childID: 2,
                //     childName: 'IbrahimAlso',
                //     profilePicture: 'https://cdn.vuetifyjs.com/images/john.jpg',
                //     isAssigned: false,
                // }
            ],

            admin: false,

            steps: 
            [
                {
                    target: '#vstep1',  // We're using document.querySelector() under the hood
                    content: `Here you'll be able to create a custom test`,
                    params: {
                        placement: 'bottom',
                    }
                },
                {
                    target: '#vstep2',  // We're using document.querySelector() under the hood
                    content: `Add questions and their answers here`,
                    params: {
                        placement: 'top',
                    }
                },
                {
                    target: '#vstep3',  // We're using document.querySelector() under the hood
                    content: `You'll be able to assign the test to children and view responses once saved`,
                    params: {
                        placement: 'bottom',
                    }
                },
            ],

            myCallbacks: {
                onFinish: this.endTour,
            },
        }
    },
    
    name: "hello",

    components: {
        draggable,
    },

    watch: {
      selected () {
        this.search = ''
      },
    },

    computed: {
        dragOptions() {
            return {
                animation: 200,
                group: "description",
                disabled: false,
                ghostClass: "ghost"
            };
        },
        categories () {
            const search = this.search.toLowerCase()

            if (!search) return this.childrenList

            return this.childrenList.filter(item => {
                const text = item.childName.toLowerCase()
                return text.indexOf(search) > -1
            })
        },

        update()
        {
            return this.$route.query.update
        },
    },

    async mounted() {
        this.testID = this.$route.query.id
        if(parseInt(this.testID) == 0)
        {
            this.dateCreated = moment().format('DD MMMM YYYY')
            this.dateEdited = this.dateCreated
            this.timeEdited = ''
            this.addQuestion()
            this.newTest = true
        }
        if(parseInt(this.testID) == 1)
            this.moodTracker = true

        this.update = this.$route.query.update
        await this.getTest(this.testID)
        this.psychID = JSON.parse(window.localStorage.getItem('currentUser'))
        if(this.psychID.userType == 'Admin')
        {
            this.admin = true
        }
        else
            this.psychID = parseInt(this.psychID.userID)
        
        await this.getNumResponses()
        this.questions.sort((a, b) => parseInt(a.position) - parseInt(b.position))
        //await this.getNumAssignedPerTest()
        if(!this.standardTest && this.testID != 0)
            await this.getChildTestAssignedStatus()

        if(this.$route.query.dialog == 'y')
        {
            this.dialog = true
            this.getChildTestAssignedStatus()
        }

        var tourBool = this.$route.query.tour
        if(tourBool)
        {   
            this.$tours['myTour'].start()
        }
    },

    methods: {

        endTour()
        {
            this.redirect('/#/resourcehub?tour=true')
        },

        async getNumAssignedStandardTest(testID)
        {
            this.numAssigned = await this.$store.dispatch('tests/getNumAssignedStandardTest', testID)
        },

        redirect: function(link) {
            window.location.href = link;
        },

        disableScore()
        {
            this.disabledScore = true
            this.testTotal = 0
            this.questions.forEach(element => {
                element.answers.forEach(aElement => {
                    aElement.answerValue = 0
                });
            });
        },

        async getNumAssignedPerTest()
        {
            const testParams = {
                testID: parseInt(this.testID), 
                psychID: this.psychID
            }
            if(this.admin == true)
                this.numAssigned = await this.$store.dispatch('tests/getNumAssignedStandardTest', testParams.testID)
            else
                this.numAssigned = await this.$store.dispatch('tests/getNumAssignedPerTest', testParams)
                debugger
        },

        async saveAssign()
        {
            var assignParams = {}
            
            this.childrenList.forEach(async element => {
                assignParams =
                {
                    testID: parseInt(this.testID),
                    childID: parseInt(element.childID),
                    status: element.isAssigned
                }
                const resp = await this.$store.dispatch('tests/assignChildToTest', assignParams)
            })
            this.dialog = false
            //await this.getNumAssignedPerTest()
            await this.getChildTestAssignedStatus()
        },

        async getChildTestAssignedStatus()
        {
            const testParams = {
                testID: parseInt(this.testID), 
                psychID: this.psychID
            }
            const resp = await this.$store.dispatch('tests/getChildTestAssignedStatus', testParams)
        
            this.childrenList = []
            this.numAssigned = 0
            const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)
            resp.forEach(element => {
                if(element.status == true)
                {
                    this.numAssigned++
                }
                var temp =
                {
                    childID: element.child.userID,
                    childName: element.child.name,
                    profilePicture: images(`./${element.child.profilePicture}`),
                    isAssigned: element.status,
                }
                this.childrenList.push(temp)
            });
        },

        newRedirect(page, paramValue, update) {
            if(paramValue != undefined)
            {
                this.$router.push({ path: page, query: { id: paramValue, update: update } })
            }
        },

        async getNumResponses()
        {
            const testParams = {
                testID: parseInt(this.testID), 
                psychID: this.psychID
            }
            this.numResponses = await this.$store.dispatch('tests/getNumResponses', testParams)
        },

        decrement(value, questionIndex, answerIndex)
        {
            if(value != 0)
            {
                this.questions[questionIndex].answers[answerIndex].answerValue--
                this.testTotal--
            }
        },
        increment(value, questionIndex, answerIndex)
        {
            if(value != 5)
            {
                this.questions[questionIndex].answers[answerIndex].answerValue++
                this.testTotal++
            }
        },

        async getTest(id)
        {
            const testParams = {
                testID: parseInt(id), 
            }
            if(id != 0)
            {
                const test = await this.$store.dispatch('tests/getTest', id)
                this.test = test.name
                if(test.psychID == null)
                {
                    this.standardTest = true
                }
                this.dateCreated = moment(test.dateCreated).format('DD MMMM yyyy')
                this.dateEdited = moment(test.lastEdited).format('DD MMMM yyyy')
                this.timeEdited = ' at ' + moment(test.lastEdited).format('HH:mm')
                this.testTotal = test.total

                var questions = await this.$store.dispatch('tests/getTestQuestions', testParams)
                var tempQuestions = []
                
                questions.forEach(async (element) => {
                    var tempAnswers = await this.getAnswers(element.questionID)
                    var temp =
                    {
                        questionID: element.questionID,
                        question: element.question,
                        answers: tempAnswers,
                        position: element.position
                    }
                    tempQuestions.push(temp)
                });
                this.questions = tempQuestions
            }
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
            tempAnswers.sort((a, b) => parseInt(a.position) - parseInt(b.position))
            
            return tempAnswers
        },

        async saveChanges()
        {
            this.$refs.form.validate()
            if(this.admin == true)
                this.psychID = null

            if(this.valid)
            {
                if(this.testID == 0)
                {
                    //add new test
                    const addParams = 
                    {
                        test: 
                        {
                            testID: parseInt(this.testID),
                            name: this.test,
                            total: this.testTotal,
                            psychID: this.psychID,
                            dateCreated: moment().format(),
                            lastEdited: moment().format()
                        },
                        questionAnswers: this.getUpdateQuestions()
                    }
                    
                    const addTest = await this.$store.dispatch('tests/addAnswers', addParams)
                    
                    this.redirect('/#/test?id=' + addTest + '&update=0')
                    window.location.reload();

                }
                else
                {
                    //update
                    const updateParams = 
                    {
                        test: 
                        {
                            testID: parseInt(this.testID),
                            name: this.test,
                            total: this.testTotal,
                            psychID: this.psychID,
                            dateCreated: "2021-09-08T13:21:36.298Z",
                            lastEdited: moment().format()
                        },
                        questionAnswers: this.getUpdateQuestions()
                    }
                    
                    const updateTest = await this.$store.dispatch('tests/updateTest', updateParams)
                    //this.newRedirect('test', this.testID, 1)
                    this.redirect('/#/test?id=' + this.testID + '&update=1')
                    window.location.reload();
                }
            }
        },

        getUpdateQuestions()
        {
            var questionsArray = []
            this.questions.forEach((element, index) => {
                var temp =
                {
                    question:
                    {
                        questionID: element.questionID,
                        question: element.question,
                        testID: parseInt(this.testID),
                        position: index,
                    },
                    answers: this.getUpdateAnswers(index, element.questionID)
                }
                questionsArray.push(temp)
            })
            
            return questionsArray
        },

        getUpdateAnswers(questionIndex, questionID)
        {
            var tempAnswers = []
            this.questions[questionIndex].answers.forEach((element, index) => {
                var temp =
                {
                    answerID: element.answerID,
                    answer: element.answer,
                    answerValue: element.answerValue,
                    questionID: questionID,
                    position: index,
                }
                tempAnswers.push(temp)
            })
            
            return tempAnswers
        },

        async removeQuestion(index, id)
        {
            this.questions.splice(index, 1)
            await this.deleteQuestion(id)
        },

        async deleteQuestion(questionID)
        {
            const deleteQuestion = await this.$store.dispatch('tests/deleteQuestion', questionID)
        },

        async deleteTest(testID)
        {
            if(testID == 0)
            {
                this.newRedirect('tests')
            }
            else
            {
                
                const deleteTest = await this.$store.dispatch('tests/deleteTest', testID)
                
                this.redirect('/#/tests')
                //this.newRedirect('tests')
            }
        },

        addQuestion()
        {
            var temp =
            {
                questionID: 0,
                question: '',
                answers: 
                [
                    {
                        answerID: 1,
                        answer: '',
                        answerValue: 0
                    },
                    {
                        answerID: 2,
                        answer: '',
                        answerValue: 0
                    },
                    {
                        answerID: 3,
                        answer: '',
                        answerValue: 0
                    },
                    {
                        answerID: 4,
                        answer: '',
                        answerValue: 0
                    }
                ],
            }
            this.questions.push(temp)
        },

        getComponentData() {
            return {
                props: {
                    popout: true,
                }
            };
        }
    },
}
</script>

<style scoped>

    @media only screen and (min-width: 1600px) {
        .alertMargin {
            margin-bottom: -12rem;
        }
    }

    .totalScoreField  >>> .v-input__slot::before
    {
        border-style: none !important;
    }

    .totalScoreField  >>> .v-input__slot::before :active
    {
        border-style: none !important;
    }

    .moveCursor
    {
        cursor: move;
    }

    .questionValue >>> input {
        text-align: center;
    }

    .centerAnswers
    {
        text-align: center;
        text-align: -webkit-center;
    }

    .centerAnswers h3
    {
        text-align: start;
    }

    .questionTextFields
    {
        display: inline-block;
    }
    
    .list-group-item {
        cursor: move;
    }
    .list-group-item i {
        cursor: pointer;
    }

    .flip-list-move {
        transition: transform 0.5s;
    }
    .no-move {
        transition: transform 0s;
    }
    .ghost {
        opacity: 0.5;
        background: #c8ebfb;
    }

    .marginTop5
    {
        margin-top: 3rem;
    }

    .justifyCenter
    {
        justify-content: center;
    }

    .cursorClick
    {
        cursor: pointer;
    }

    .centerCol
    {
        text-align: center;
    }

    .viewResults
    {
        font-size: 12px;
        color: black;
        display: block;
        margin-top: 10px;
    }

    .numberBackground
    {
        color: white;
        background-color: #FF9565;
        border-radius: 5px;
        padding: 5px 15px;
    }

    .theme--light.v-application {
        background: #FBF5F2;
    }
    
    .v-application--wrap {
        min-height: 0;
    }
</style>