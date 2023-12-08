<template>
    <div>
        <v-app>
            <div class="row">
                <div class="col-xl-8">
                    <div class="card">
                        <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h3 class="mb-0">{{ problemName }}</h3>
                                </div>
                                <div class="col" v-show="admin">
                                    <div class="nav nav-pills justify-content-end">
                                        <button type="button" class="btn btn-primary btn-sm" style="color:white;" @click="redirect('/#/editresource?ID=' + problemID)">Edit Problem</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div 
                        class="card-body text-center"
                        v-for="item in problems"
                        :key="item.id">
                            <img :src="item.image" width="400">
                            <br/>

                            <br/>
                            <div style="text-align: justify;">
                                {{ item.description }}
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-4">
                    <div class="card">
                        <div class="card-header bg-transparent">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h5 class="h3 mb-0">Other Mental Health Issues</h5>
                                </div>
                                <div class="col">
                                    <div class="nav nav-pills justify-content-end">
                                        <button type="button" class="btn btn-primary btn-sm" style="color:white;" @click="redirect('/#/resourcehub')">See All</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <v-container>
                                <v-row>
                                    <v-col cols="6"
                                    v-for="item in tempRelated"
                                    :key="item.problemID">                        
                                        <v-card
                                        class="text-center roundedCard"
                                        outlined
                                        style="min-height: 190px">
                                        <!-- tried href in card, old redirect(), newRedirect(), router link -->
                                            <img :src="item.problemImg" class="relatedImg">
                                            <v-card-title class="problemMiniCards"> 
                                                <h2>{{ item.problemName }}</h2>
                                            </v-card-title>
                                        </v-card>
                                    </v-col>
                                </v-row>
                            </v-container>
                        </div>
                    </div>
                </div>
            </div>
        </v-app>
    </div>
</template>

<script>

export default {

    data () {
      return {  
            problemName: '',

            problems: 
            [
                // {
                //     id: '',
                //     image: '',
                //     description: '',
                // },
            ],

            tempRelated: [],
            related: [],
            problemID: 0,
            admin: false,
        }
    },
    async mounted() {
        await this.fetchResourceHubImages()
        await this.fetchAllFocusPoints()
        await this.getRHubProblemImage()
        this.problemID = this.$route.query.ID
        if(JSON.parse(window.localStorage.getItem('currentUser')).userType == 'Admin')
            this.admin = true
    },

    methods: {

        newRedirect(page, paramValue) {
            if(paramValue != undefined)
            {
                this.$router.push({ path: page, query: { ID: paramValue } })
            }
        },

        redirect: function(link) {
            
            window.location.href = link;
        },

        async fetchResourceHubImages() {
            var urlProblemID = this.$route.query.ID
            const problemImages = await this.$store.dispatch('hub/fetchResourceHubImages', urlProblemID)
            const image = require.context('../assets/img/RHub/', false, /\.(png|jpe?g|svg)$/)
            var temp

            problemImages.forEach(async (problem) => {
                
                temp = {
                    id: problem.imageID,
                    image: image(`./${problem.fileName}`),
                    description: problem.description
                }
                this.problems.push(temp)
            })
            this.problemName = await this.getRHubProblemName()
        },

        async getRHubProblemName() {
            var urlProblemID = this.$route.query.ID
            const problem = await this.$store.dispatch('hub/getRHubProblem', urlProblemID)    
            return problem.problem
        },

        async fetchAllFocusPoints() {
            var urlProblemID = this.$route.query.ID
            const problemList = await this.$store.dispatch('hub/fetchAllFocusResource')
            var temp
            problemList.forEach((point) => {
                
                if(point.problemID != urlProblemID)
                {
                    temp = {
                        problemID: point.problemID,
                        problemName: point.problem,
                        problemImg: '',
                    }
                    this.tempRelated.push(temp)
                }
                
            })

            this.tempRelated.splice(4, this.tempRelated.length - 4)
            //this.related = problemList.filter
            //this.related = problemList
            // for(let k = 0; k < 4; k++)
            // {
            //     Vue.set(this.related, k, this.tempRelated[k])
            //     //this.related[k] = this.tempRelated[k]
            // }


            
        },

        async getRHubProblemImage() {
            const images = require.context('../assets/img/RHub/', false, /\.(png|jpe?g|svg)$/)
            
            this.tempRelated.forEach(async (point) => {
                const problem = await this.$store.dispatch('hub/getRHubProblem', point.problemID)
                
                point.problemImg = images(`./${problem.titleImage}`)
            })
        },
    },
}
</script>

<style scoped>
    .theme--light.v-application {
        background: #FBF5F2;
    }
    
    .v-application--wrap {
        min-height: 0;
    }

    .roundedCard {
        border-radius: 33px;
    }

    .problemMiniCards {
        word-break: break-word;
        justify-content: center;
    }
    /* .roundedCard :hover{
        background-color: #eeeeee;
    } */

    .relatedImg
    {
        max-height: 85px;
        width: auto;
        height: auto;
    }
</style>