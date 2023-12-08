<style>
    .v-application--wrap {
        min-height: 0;
    }    
</style>
<template>
    <div>
        <v-app>
            <v-alert
            type="info">
                Disclaimer: Tests are not formal assessments
            </v-alert>
        </v-app>
        <div class="container-fluid mt-2">
            <div class="row">
                <div class="col">
                <div class="card">
                    <div class="card-header">
                        <div class="row align-items-center" id="vstep1">
                            <div class="col">
                                <h3 class="mb-0">Test List</h3>
                            </div>
                            <div class="col text-right">
                                <button type="button" class="btn btn-primary btn-sm" @click="newRedirect('test', 0)">New Test</button>
                            </div>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table align-items-center table-flush">
                            <thead class="thead-light">
                            <tr>
                                <th scope="col" class="sort" data-sort="name">Name</th>
                                <th scope="col" class="sort" data-sort="budget">Questions</th>
                                <th scope="col" class="sort" data-sort="status">{{ responsesHeading }}</th>
                                <th scope="col">Last Edited</th>
                                <th scope="col">Date Created</th>
                            </tr>
                            </thead>
                            <tbody class="list cursorClick">
                                <tr
                                class="cursorClick"
                                v-for="item in testList" 
                                :key="item.testID"
                                @click="newRedirect('test', item.testID)">
                                    <th scope="row">
                                        <span class="name mb-0 text-sm" style="color: #32325d">{{ item.testName }}</span>
                                    </th>
                                    <td class="budget">
                                        {{ item.questions }}
                                    </td>
                                    <td class="budget">
                                        {{ item.responses }}
                                    </td>
                                    <td>
                                        {{ item.lastEdited }}
                                    </td>
                                    <td>
                                        {{ item.dateCreated }}
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- Card footer -->
                    <div class="card-footer py-4">
                    <nav aria-label="...">
                        <ul class="pagination justify-content-end mb-0">
                        <li class="page-item disabled">
                            <a class="page-link" href="#" tabindex="-1">
                                <i class="fas fa-angle-left"></i>
                                <span class="sr-only">Previous</span>
                            </a>
                        </li>
                        <li class="page-item active">
                            <a class="page-link" href="#">1</a>
                        </li>
                        <!--<li class="page-item">
                            <a class="page-link" href="#">2 <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>-->
                        <li class="page-item disabled">
                            <a class="page-link" href="#">
                                <i class="fas fa-angle-right"></i>
                                <span class="sr-only">Next</span>
                            </a>
                        </li>
                        </ul>
                    </nav>
                    </div>
                </div>
                </div>
            </div>
        </div>
        <v-tour name="myTour" :steps="steps" :callbacks="myCallbacks"></v-tour>

    </div>
</template>

<script>
import moment from 'moment'

export default {

    data() {
		return {
            psychID: 0,
            testList:
            [
                // {
                //     testID: 0,
                //     testName: 'Depression Index',
                //     questions: 20,
                //     responses: 12,
                //     lastEdited: '14 September 2021',
                //     dateCreated: '14 July 2021'
                // },
                // {
                //     testID: 1,
                //     testName: 'Something Else',
                //     questions: 20,
                //     responses: 12,
                //     lastEdited: '11 May 2021',
                //     dateCreated: '11 June 2021'
                // }
            ],
            temp: [],
            responsesHeading: 'Responses',

            steps: 
            [
                {
                    target: '#vstep1',  // We're using document.querySelector() under the hood
                    content: `The Tests page allows you to see all standard and custom tests`,
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

    async mounted() {
        const user = JSON.parse(window.localStorage.getItem('currentUser'))
        if(user.userType == 'Admin')
            await this.getStandardTests()
        else
        {
            this.psychID = parseInt(user.userID)
            await this.getPsychTestsList()
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
            this.redirect('/#/test?id=0&tour=true')
        },

        redirect: function(link) {
            window.location.href = link;
        },

        newRedirect(page, paramValue) {
            if(paramValue != undefined)
            {
                this.$router.push({ path: page, query: { id: paramValue } })
            }
        },

        async getPsychTestsList()
        {
            const resp = await this.$store.dispatch('tests/getPsychTestsList', this.psychID)
            
            resp.forEach(element => {
                
                if(element.test.status == "Active")
                {
                    var temp =
                    {
                        testID: element.test.testID,
                        testName: element.test.name,
                        questions: element.numQuestions,
                        responses: element.responses,
                        lastEdited: moment(element.test.lastEdited).format('DD MMMM yyyy'),
                        dateCreated: moment(element.test.dateCreated).format('DD MMMM yyyy')
                    }
                    this.testList.push(temp)
                }
            });
        },

        async getStandardTests()
        {
            this.responsesHeading = 'Assigned'
            const resp = await this.$store.dispatch('tests/getStandardTests')
            resp.forEach(element => {
                
                if(element.test.status == "Active")
                {
                    var temp =
                    {
                        testID: element.test.testID,
                        testName: element.test.name,
                        questions: element.numQuestions,
                        responses: element.responses,
                        lastEdited: moment(element.test.lastEdited).format('DD MMMM yyyy'),
                        dateCreated: moment(element.test.dateCreated).format('DD MMMM yyyy')
                    }
                    this.testList.push(temp)
                }
            });
        },
    },
}
</script>

<style scoped>
    .cursorClick
    {
        cursor: pointer;
    }

    .theme--light.v-application {
        background: #FBF5F2;
    }
    
    .minHeight {
        min-height: 0;
    }
</style>