<template>
    <div class="content">
        <v-app>
            <v-card class="rounded-0"
            flat
            color="#FF9565">
                <v-container>
                    <v-row justify="center">
                        <v-col md="6" class="col-padding" id="vstep2">
                            <h1 class="resourceHeading">Resource Hub</h1>
                            <v-text-field
                                id="vstep1"
                                v-model="search"
                                append-icon="mdi-magnify"
                                label="Search Resource Hub"
                                color="#022F5B"
                                single-line
                                rounded
                                solo
                            ></v-text-field>
                        </v-col>
                    </v-row>
                </v-container>
            </v-card>
            <br/>
            <v-container>
                <v-row>
                    <v-col md="3" v-if="admin">
                        <v-card 
                        class="text-center roundedCard"
                        outlined
                        width="75%"
                        @click="redirect('/#/editresource?ID=0')">
                            <!-- <img src="@/assets/img/vectorpaint.svg" width="125" height="125" style="color: #FF9565"> -->
                            <v-icon size="125" color="#FF9565">
                                mdi-plus-circle-outline
                            </v-icon>
                            <v-card-title class="plsCenter problemMiniCards"> 
                                <h2>Add New</h2>
                            </v-card-title>
                        </v-card>
                    </v-col>

                    <v-col md="3">
                        <v-card 
                        class="text-center roundedCard"
                        outlined
                        width="75%"
                        @click="redirect('/#/locations?id=0')">
                            <img src="@/assets/img/vectorpaint.svg" width="125" height="125" style="color: #FF6148">
                            <v-card-title class="plsCenter problemMiniCards"> 
                                <h2>Locations</h2>
                            </v-card-title>
                        </v-card>
                    </v-col>

                    <v-col md="3"
                    v-for="item in searching"
                    :key="item.problemID">
                        <v-card 
                        class="text-center roundedCard"
                        outlined
                        width="75%"
                        style="min-height: 190px"
                        @click="redirect('/#/problem?ID=' + item.problemID)">
                            <img :src="item.problemImg" width="125" height="125">
                            <v-card-title class="plsCenter problemMiniCards"> 
                                <h2>{{ item.problemName }}</h2>
                            </v-card-title>
                        </v-card>
                    </v-col>
                </v-row>
            </v-container>
            <div v-if="isEmpty()">
                <br/>
                <v-container>
                    <v-row justify="center">
                        <v-col md="6">
                            <h1 style="text-align: center;">Can't find what you're looking for? Let us know so we can add it</h1>
                        </v-col>
                    </v-row>
                </v-container>
            </div>
        </v-app>
        <v-tour name="myTour" :steps="steps" :callbacks="myCallbacks"></v-tour>
    </div>
</template>

<script>

  export default {
    
    data () {
      return {
            
            problems: 
            [
                // {
                //     problemID: '0',
                //     problemName: 'Depression',
                //     problemImg: 'blobsad.png',
                // },
            ],
            search: '',
            admin: false,

            steps: 
            [
                {
                    target: '#vstep1',  // We're using document.querySelector() under the hood
                    content: `The Resource Hub features information on different kinds of mental health issues`,
                    params: {
                        placement: 'bottom',
                        enableScrolling: false,
                    }
                },
                {
                    target: '#vstep8',  // We're using document.querySelector() under the hood
                    content: `Here you'll be able to edit your profile, your practice, and logout`,
                    params: {
                        placement: 'left',
                        enableScrolling: false,
                    }
                },
                {
                    target: '#vstep8',  // We're using document.querySelector() under the hood
                    content: `Thank you for using cura`,
                    params: {
                        placement: 'left',
                        enableScrolling: false,
                    }
                },
            ],
        }
    },

    async mounted() {
        if(JSON.parse(window.localStorage.getItem('currentUser')).userType == 'Admin')
            this.admin = true
        await this.fetchAllFocusPoints()
        await this.getRHubProblem()

        var tourBool = this.$route.query.tour
        if(tourBool)
        {   
            this.$tours['myTour'].start()
        }
    },

    computed: {
      keywords () {
        if (!this.search) return []

        const keywords = []

        for (const search of this.searching) {
          keywords.push(search.keyword)
        }

        return keywords
      },
      searching () {
        
        if (!this.search) return this.problems
        
        const search = this.search.toLowerCase()

        return this.problems.filter(item => {
          const text = item.problemName.toLowerCase()

          return text.indexOf(search) > -1
        })
      },
    },

    methods: {

        isEmpty() {
            if(this.searching.length == 0)
            {
                return false
            }
        },

        redirect: function(link) {
            window.location.href = link;
        },

        async fetchAllFocusPoints() {
            const problemList = await this.$store.dispatch('hub/fetchAllFocusResource')
            
            problemList.forEach((point) => {
                
                var temp = {
                    problemID: point.problemID,
                    problemName: point.problem,
                    problemImg: '',
                }
                this.problems.push(temp)
            })
        },

        // async fetchResourceHubImages(problemID) {
        //     const problem = await this.$store.dispatch('hub/fetchResourceHubImages', problemID)
        //     const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)

        //     problems.forEach((point) => {
        //         if(point.problemID == problemID)
        //         {
        //             point.problemImg = images(`./${problem.titleImage}`)
        //         }
        //     })
        // },

        async getRHubProblem() {
            const images = require.context('../assets/img/RHub', false, /\.(png|jpe?g|svg)$/)
            
            this.problems.forEach(async (point) => {
                const problem = await this.$store.dispatch('hub/getRHubProblem', point.problemID)
                point.problemImg = images(`./${problem.titleImage}`)
            })
        },
    }

  }
</script>

<style scoped>
    .plsCenter {
        justify-content: center;
    }

    .v-application--wrap {
        min-height: 0;
    }

    .theme--light.v-application {
        background: #FBF5F2;
    }

    .resourceHeading {
        text-align: center;
        font-size: 2.5rem;
        color: white;
    }

    .roundedCard {
        border-radius: 33px;
    }

    .problemMiniCards {
        word-break: break-word;
        justify-content: center;
    }
    
    .content{
        position: relative;
        bottom: 5px;
    }

    .container {
        padding-top: 0px;
    }

    .col-padding{
        padding-top: 0px;
    }
</style>