<template>
    <div>
        <v-app>
            <div class="newRow"> <!-- style="margin-bottom: -10rem;"> -->
                <div class="col-xl-12">
                    <div class="card">
                        <div class="card-body">    
                            <div class="patient-overview">
                                <div class="col-xl-5 leftGroup">
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
                                                    <br/>
                                                    <div class="numberFont">{{ testTotal }}</div>
                                                    <div>Total Score</div>
                                                </div>
                                                <div class="col textAlignCenter">
                                                    <div class="numberFont">{{ lastEdited }}</div>
                                                    <div>Last Edited</div>
                                                    <br/>
                                                    <div class="numberFont">{{ dateCreated }}</div>
                                                    <div>Date Created</div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="chat-button">
                                            <button type="button" class="btn btn-outline-primary inverseButtons" v-show="!standardTest" @click="newRedirect('test', testID)"><span class="fas fa-clipboard-check"></span>  Edit Test</button>
                                            <button type="button" class="btn btn-outline-primary inverseButtons" v-show="!standardTest" @click="redirect('/#/test?id=' + testID + '&dialog=y')"><v-icon small color="#FF9565">mdi-account-plus</v-icon>  View Assigned</button>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 offset-xl-1 textAlignCenter alignSelfCenter">
                                    <v-card color="#ff4066" dark>
                                        <v-container>
                                            <v-row>
                                                <v-col class="alignSelfCenter">
                                                    <v-icon>mdi-close-circle-outline</v-icon>
                                                </v-col>
                                                <v-col>
                                                    <div class="numberFont">{{ numIncomplete }}</div>
                                                    <div>Incomplete</div>
                                                </v-col>
                                            </v-row>
                                        </v-container>
                                    </v-card>
                                    <br/>
                                    <v-card color="#48cf48" dark>
                                        <v-container>
                                            <v-row>
                                                <v-col class="alignSelfCenter">
                                                    <v-icon>mdi-check-circle-outline</v-icon>
                                                </v-col>
                                                <v-col>
                                                    <div class="numberFont">{{ numCompleted}}</div>
                                                    <div>Completed</div>
                                                </v-col>
                                            </v-row>
                                        </v-container>
                                    </v-card>
                                    <br/>
                                    <v-card color="#a73ad6" dark v-show="!standardTest">
                                        <v-container>
                                            <v-row>
                                                <v-col class="alignSelfCenter">
                                                    <v-icon>mdi-account-plus</v-icon>
                                                </v-col>
                                                <v-col>
                                                    <div class="numberFont">{{ numAssigned }}</div>
                                                    <div>Assigned</div>
                                                </v-col>
                                            </v-row>
                                        </v-container>
                                    </v-card>
                                </div>
                                <div class="col-xl-4">
                                    <div id="chart">
                                        <apexchart type="donut" width="380" :options="pieChartOptions" :series="pieSeries"></apexchart>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="newRow">
                <div :class="testID == 1 ? 'col-xl-12' : 'col-xl-8'">
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
                                        <div id="chart">
                                            <apexchart type="bar" height="350" :options="item.chartOptions" :series="item.series"></apexchart>
                                        </div>
                                    </v-expansion-panel-content>
                                </v-expansion-panel>
                            </v-expansion-panels>
                        </div>
                    </div>
                </div>

                <div class="col-xl-4" v-if="testID != 1">
                    <div class="card">
                        <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h3 class="mb-0">Completed Tests</h3>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <v-simple-table style="color: #022F5B">
                                <template v-slot:default>
                                    <tbody>
                                        <tr
                                        v-for="item in completedTests"
                                        :key="item.childTestID"
                                        @click="redirect('/#/childresults?id=' + item.childTestID)"
                                        class="cursorClick" >
                                            <td>
                                                <v-avatar>
                                                    <img :src="item.childAvatar">
                                                </v-avatar>
                                            </td>
                                            <td>{{ item.childName }}</td>
                                            <td>{{ item.childTestDate }}</td>
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
    import moment from 'moment'

export default {

    data: function () {
        return {
            psychID: 0,

            testID: 0,
            standardTest: false,
            test: 'Depression Inventory',
            dateCreated: '01 May 2021',
            lastEdited: '14 September 2021',
            numQuestions: 10,
            testTotal: 50,
            numIncomplete: 5,
            numCompleted: 10,
            numAssigned: 15,

            tempAnswersText: [],

            questions:
            [
                {
                    //answers bar chart
                    question: 'How are you?',
                    answers: 
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
                            answer: '',
                        },
                        {
                            answerID: 4,
                            answer: '',
                        }
                    ],

                    series: [{
                        data: [0, 0, 0, 0]
                    }],
                    chartOptions: {
                        chart: {
                            type: 'bar',
                            height: 350
                        },
                        plotOptions: {
                            bar: {
                                horizontal: true,
                                distributed: true
                            }
                        },
                        dataLabels: {
                            enabled: false
                        },
                        legend: {
                            show: false
                        },
                        tooltip: {
                            enabled: false,
                        },
                        colors:['#F44336', '#E91E63', '#9C27B0', '#DC143C'],
                        xaxis: {
                            categories: ['I feel very happy', 'I feel happy', 'I feel ok', 'I feel sad'],
                        }
                    },
                },
                {
                    //answers bar chart
                    question: 'How are you?2',
                    answers: 
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
                            answer: '',
                        },
                        {
                            answerID: 4,
                            answer: '',
                        }
                    ],

                    series: [{
                        data: [0, 0, 0, 0]
                    }],
                    chartOptions: {
                        chart: {
                            type: 'bar',
                            height: 350
                        },
                        plotOptions: {
                            bar: {
                                horizontal: true,
                                distributed: true
                            }
                        },
                        dataLabels: {
                            enabled: false
                        },
                        legend: {
                            show: false
                        },
                        tooltip: {
                            enabled: false,
                        },
                        colors:['#F44336', '#E91E63', '#9C27B0', '#DC143C'],
                        tooltip: {
                            enabled: false,
                        },
                        xaxis: {
                            categories: ['I feel very ', 'I feel ', 'I  ok', 'I feel '],
                        }
                    },
                },
                {
                    //answers bar chart
                    question: 'How are you?3',
                    answers: 
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
                            answer: '',
                        },
                        {
                            answerID: 4,
                            answer: '',
                        }
                    ],

                    series: [{
                        //data: [400, 430, 448, 470]
                        data: [0, 0, 0, 0]
                    }],
                    chartOptions: {
                        chart: {
                            type: 'bar',
                            height: 350
                        },
                        plotOptions: {
                            bar: {
                                horizontal: true,
                                distributed: true
                            }
                        },
                        dataLabels: {
                            enabled: false
                        },
                        legend: {
                            show: false
                        },
                        tooltip: {
                            enabled: false,
                        },
                        colors:['#F44336', '#E91E63', '#9C27B0', '#DC143C'],
                        xaxis: {
                            categories: ['I feel  happy', 'I  happy', 'I feel ', 'I  sad'],
                        },
                    },
                }
            ],
            styleCompleted: { border: '3px solid limegreen'},
            styleIncomplete: { border: '3px solid crimson'},
            styleAssigned: { border: '3px solid darkviolet'},
            
            //test pie graph
            pieSeries: [],
            pieChartOptions: {
                chart: {
                    width: 380,
                    type: 'donut',
                },
                labels: ['Incomplete', 'Complete'],
                colors:['#ff4066', '#48cf48'],
                legend: {
                    show: false
                },
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
                }]
            },

            completedTests:
            [
                // {
                //     childTestID: 0,
                //     childAvatar: '',
                //     childName: 'Nathan Drake',
                //     childTestDate: '14 September 2021',
                // },
            ],
        }
    },
    
    async mounted() {
        this.testID = parseInt(this.$route.query.id)
        await this.getTest(this.testID)
        this.psychID = JSON.parse(window.localStorage.getItem('currentUser'))
        this.psychID = parseInt(this.psychID.userID)
        await this.getNumCompleted()
        await this.getNumIncomplete()
        await this.getNumAssignedPerTest()
        // this.pieSeries = [this.numIncomplete, this.numCompleted]
        this.pieSeries.push(this.numIncomplete)
        this.pieSeries.push(this.numCompleted)
        this.getCompletedTests()
    },

    methods: {

        redirect: function(link) {
            window.location.href = link;
            //redirect('/#/test?id=id&dialog=y')
        },

        newRedirect(page, id) {
            if(id != undefined)
            {
                this.$router.push({ path: page, query: { id: id } })
            }
        },

        async getNumAssignedPerTest()
        {
            const testParams = {
                testID: parseInt(this.testID), 
                psychID: this.psychID
            }
            this.numAssigned = await this.$store.dispatch('tests/getNumAssignedPerTest', testParams)
        },

        async getNumCompleted()
        {
            const testParams = {
                testID: parseInt(this.testID), 
                psychID: this.psychID
            }
            this.numCompleted = await this.$store.dispatch('tests/getNumResponses', testParams)
        },

        async getNumIncomplete()
        {
            const testParams = {
                testID: parseInt(this.testID), 
                psychID: this.psychID
            }
            this.numIncomplete = await this.$store.dispatch('tests/getNumIncomplete', testParams)
        },

        async getNumChildAnswersPerAnswer(questionID)
        {
            const resp = await this.$store.dispatch('reports/getNumChildAnswersPerAnswer', questionID)
            
            var tempSeries = 
            [{
                data: resp
            }]
            return tempSeries
        },

        getChartOptions()
        {
            var chartOptions = 
            {
                chart: {
                    type: 'bar',
                    height: 350
                },
                plotOptions: {
                    bar: {
                        horizontal: true,
                        distributed: true
                    }
                },
                dataLabels: {
                    enabled: false
                },
                legend: {
                    show: false
                },
                tooltip: {
                    enabled: false,
                },
                colors:['#F44336', '#E91E63', '#9C27B0', '#DC143C'],
                xaxis: {
                    categories: this.tempAnswersText,
                }
            }
            return chartOptions
        },

        async getTest(id)
        {
            const testParams = {
                testID: id, 
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
                        answers: tempAnswers,
                        series: await this.getNumChildAnswersPerAnswer(element.questionID),
                        chartOptions: this.getChartOptions(),
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
            this.tempAnswersText = []
            answers.forEach(element => {
                var temp =
                {
                    answerID: element.answerID,
                    answer: element.answer,
                    position: element.position,
                    answerValue: element.answerValue
                }
                tempAnswers.push(temp)
                this.tempAnswersText.push(element.answer)
            })
            tempAnswers.sort((a, b) => parseInt(a.position) - parseInt(b.position))
            return tempAnswers
        },

        async getChild(childID)
        {
            const childParams = {
                childID: childID,
                psychID: this.psychID 
            }

            const child = await this.$store.dispatch('users/GetChild', childParams)
            return child
        },

        async getCompletedTests()
        {
            const tests = await this.$store.dispatch('tests/getCompletedTests', this.testID)
            const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)

            tests.forEach(async element => {
                var child = await this.getChild(element.childID)
                
                var temp =
                {
                    childTestID: element.childTestID,
                    childAvatar: images(`./${child.profilePicture}`),
                    childName: child.name,
                    childTestDate: moment(element.date).format('DD MMMM YYYY'),
                }
                this.completedTests.push(temp)
            });
        }
    },
}
</script>

<style scoped>
    .newRow
    {
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -ms-flex-wrap: wrap;
        flex-wrap: wrap;
        -webkit-box-flex: 1;
        -ms-flex: 1 1 auto;
        margin: -12px;
    }

    .cursorClick
    {
        cursor: pointer;
    }

    .alignSelfCenter
    {
        align-self: center;
    }

    .blueText
    {
        color: #022F5B;
    }

    .inverseButtons
    {
        color: var(--orange);
        background: white;
        border-radius: 50px;
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
</style>