<template>
    <div>
        <v-app>
            <div class="row">
                <div class="col-xl-8">
                    <div class="card">
                        <div class="card-body text-center">
                            <div class="map-responsive">
                                <iframe :src="mapLink" width="640" height="480"></iframe>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-4">
                    <div class="card">
                        <div class="card-header bg-transparent">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h5 class="h3 mb-0">Locations</h5>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <v-radio-group
                            v-model="radios"
                            mandatory>
                                <v-radio
                                    label="All"
                                    value="0"
                                    @click="redirectMap(0)"
                                ></v-radio>
                                <v-radio
                                    label="Psychologists"
                                    value="1"
                                    @click="redirectMap(1)"
                                ></v-radio>
                                <v-radio
                                    label="Childline"
                                    value="2"
                                    @click="redirectMap(2)"
                                ></v-radio>
                                <v-radio
                                    label="Mental Health "
                                    value="3"
                                    @click="redirectMap(3)"
                                ></v-radio>
                                <v-radio
                                    label="Police Stations"
                                    value="4"
                                    @click="redirectMap(4)"
                                ></v-radio>
                            </v-radio-group>
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
            radios: null,
            mapLink: '',
            all: 'https://www.google.com/maps/d/u/0/embed?mid=1vr-mpmjIHct1vKfDGRnn6ueyuVW-uZSd',
            childline: 'https://www.google.com/maps/d/u/0/embed?mid=1EAlZJKiDbrnxU8zidM-sNZjLB9EusIyb',
            psychs: 'https://www.google.com/maps/d/u/0/embed?mid=1EEW6uXQFHTPzxSkLI8byG3gL3GRQUPap',
            mental: 'https://www.google.com/maps/d/u/0/embed?mid=1HaeJYCnVmeiBwX9yZXmDlKBDOMbHdbA4',
            police: 'https://www.google.com/maps/d/u/0/embed?mid=1mFbVcDTKlPsn_82JFegI-P42h28luUxZ',
        }
    },
    mounted() {
        const mapType = this.$route.query.id
        this.populateMap(mapType)
        this.correctRadio(mapType)
    },

    methods: {
        correctRadio(mapType) {
            this.radios = mapType
        },

        populateMap(mapType) 
        {
            if(mapType == 0)
            {
                this.mapLink = this.all
            }
            else if(mapType == 1)
            {
                this.mapLink = this.childline
            }
            else if(mapType == 2)
            {
                this.mapLink = this.psychs
            }
            else if(mapType == 3)
            {
                this.mapLink = this.mental
            }
            else if(mapType == 4)
            {
                this.mapLink = this.police
            }
        },

        redirectMap(mapType) 
        {
            this.redirect('/#/locations?id=' + mapType)
        },

        redirect: function(link) {
            window.location.href = link;
            window.location.reload(true); 
        },
    }
}
</script>

<style scoped>
    .theme--light.v-application {
        background: #FBF5F2;
    }
    
    .v-application--wrap {
        min-height: 0;
    }

    .map-responsive
    {
        overflow:hidden;
        padding-bottom:56.25%;
        position:relative;
        height:0;
    }

    .map-responsive iframe
    {
        left:0;
        top:0;
        height:100%;
        width:100%;
        position:absolute;
    }
</style>