<template>
    <div>
        <v-app>
            <div class="row">
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
                <div class="col-xl-8">
                    <div class="card">
                        <div class="card-body">
                            <div style="text-align: center;">
                                <img :src="problems[0].image" width="400">
                                <v-file-input
                                @change="previewImage"
                                class="centerImageUpload mt-2"
                                accept="image/png, image/jpeg, image/bmp"
                                chips
                                label="Upload Problem Image"
                                prepend-icon="mdi-camera"
                                solo
                                v-model="image"
                                @click:clear="problems[0].image = ''">
                                </v-file-input>
                            </div>
                            <h4 class="account-h4">Problem Name</h4>
                            <v-text-field
                                :value="problemName"
                                label="Problem Name"
                                solo
                                v-model="problemName">
                            </v-text-field>
                            <div>
                                <h4 class="account-h4">Description</h4>
                                <v-textarea
                                :value="problems[0].description"
                                solo
                                auto-grow
                                v-model="problems[0].description"
                                label="Problem Description">
                                </v-textarea>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-4">
                    <div class="card">
                        <div class="card-header bg-transparent">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h5 class="h3 mb-0">Actions</h5>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="container">
                                <div class="row">
                                    <div class="col">
                                        Preview
                                    </div>
                                    <div class="col">
                                        <span class="btn-keyword keyword-1" :style="'color: ' + color + ' ; border-color: ' + color + ';'">
                                            {{ problemName }}
                                        </span>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col" style="align-self: center;">
                                        Colour
                                    </div>
                                    <div class="col">
                                        <v-text-field v-model="color" hide-details class="ma-0 pa-0 colourPickerWidth" solo readonly>
                                            <template v-slot:append>
                                                <v-menu 
                                                v-model="menu" 
                                                top 
                                                nudge-bottom="320" 
                                                nudge-left="265" 
                                                :close-on-content-click="closeOnContentClick"
                                                :close-on-click="closeOnClick">
                                                    <template v-slot:activator="{ on }">
                                                        <div :style="swatchStyle" v-on="on" />
                                                    </template>
                                                    <v-card>
                                                        <v-card-text class="pa-0">
                                                            <v-color-picker v-model="color" flat hide-mode-switch mode="hexa" />
                                                        </v-card-text>
                                                    </v-card>
                                                </v-menu>
                                            </template>
                                        </v-text-field>
                                    </div>
                                </div>
                                <div class="row justifyCenter mt-5">
                                    <button type="button" class="btn btn-block btn-primary" @click="saveChanges()">Save Changes</button>
                                    <button 
                                    v-if="problemID != 0"
                                    type="button" 
                                    class="btn btn-block btn-outline-primary" 
                                    @click="newRedirect('problem', problemID)">
                                        View Problem
                                    </button>
                                </div>
                            </div>
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
            closeOnClick: true,
            closeOnContentClick: false,
            problemID: 0,
            imageName: '',
            image: null,
            problemName: '',
            problems: 
            [
                // {
                //     id: '',
                //     image: '',
                //     description: '',
                // },
            ],
            description: '',
            color: '#000',
            menu: false,
            
            update: '',
        }
    },
    async mounted() {
        this.problemID = parseInt(this.$route.query.ID)
        if(this.problemID != 0)
            await this.fetchResourceHubImages()
        else
        {
            var temp =
            {
                id: '',
                image: '',
                description: '',
            }
            this.problems.push(temp)
        }
        this.update = this.$route.query.update
    },

      computed: {
        swatchStyle() {
            const { color, menu } = this
            return {
                backgroundColor: color,
                cursor: 'pointer',
                height: '30px',
                width: '30px',
                borderRadius: menu ? '50%' : '4px',
                transition: 'border-radius 200ms ease-in-out'
            }
        }
    },

    methods: {

        async saveChanges()
        {
            if(this.problemID == 0)
            {
                const params =
                {
                    problem: this.problemName,
                    description: this.problems[0].description,
                    titleImage: this.imageName,
                    colour: this.color
                }
                const resp = await this.$store.dispatch('hub/AddResourceHubProblem', params)
                
                const imageParams =
                {
                    fileName: this.imageName,
                    description: this.problems[0].description,
                    problemID: resp.problemID
                }
                const imageresp = await this.$store.dispatch('hub/AddRHubProblemImage', imageParams)
                this.redirect('/#/editresource?ID=' + resp.problemID + '&update=0')
                window.location.reload();
            }
            else
            {
                const params =
                {
                    problemID: this.problemID,
                    problem: this.problemName,
                    description: this.problems[0].description,
                    titleImage: this.imageName,
                    colour: this.color
                }
                const resp = await this.$store.dispatch('hub/updateRHubProblem', params)
                
                const imageParams =
                {
                    fileName: this.imageName,
                    description: this.problems[0].description,
                    problemID: this.problemID
                }
                const imageresp = await this.$store.dispatch('hub/updateRHubProblemImage', imageParams)
                debugger
                this.redirect('/#/editresource?ID=' + this.problemID + '&update=1')
                window.location.reload();
            }
        },

        previewImage() {
            this.problems[0].image = URL.createObjectURL(this.image)
            this.imageName = this.image.name
        },

        newRedirect(page, paramValue, update) {
            if(paramValue != undefined)
            {
                this.$router.push({ path: page, query: { id: paramValue, update: update } })
            }
        },

        newRedirect(page, paramValue) {
            if(paramValue != undefined)
            {
                this.$router.push({ path: page, query: { ID: paramValue } })
            }
        },

        redirect: function(link) {
            window.location.href = link;
        },

        async getRHubProblemName() {
            var urlProblemID = this.$route.query.ID
            const problem = await this.$store.dispatch('hub/getRHubProblem', urlProblemID)   
            this.color = problem.colour 
            
            return problem.problem
        },

        async fetchResourceHubImages() {
            var urlProblemID = this.$route.query.ID
            const problemImages = await this.$store.dispatch('hub/fetchResourceHubImages', urlProblemID)
            const image = require.context('../assets/img/RHub/', false, /\.(png|jpe?g|svg)$/)
            var temp

            problemImages.forEach(async (problem) => {
                this.imageName = problem.fileName
                //this.image = problem.fileName

                temp = {
                    id: problem.imageID,
                    image: image(`./${problem.fileName}`),
                    description: problem.description
                }
                this.problems.push(temp)
            })
            this.problemName = await this.getRHubProblemName()
        },
    },
}
</script>

<style scoped>

    .centerImageUpload
    {
        margin-left: auto;
        margin-right: auto;
        width: 16.5rem;
    }

    .colourPickerWidth
    {
        width: 10rem;
    }

    .theme--light.v-application {
        background: #FBF5F2;
    }
    
    .v-application--wrap {
        min-height: 0;
    }
    
</style>