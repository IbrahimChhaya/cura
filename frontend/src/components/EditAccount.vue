<template>
    <v-app>
        <div class="row">
            <div class="col-xl-7">
                <div class="card">
                    <h3 class="card-header">{{ name }}</h3>
                    <div class="card-body">
                        <div style="text-align: center;">
                            <v-avatar size="100">
                                <img :src="avatar">
                            </v-avatar>
                        </div>
                        <div>
                            <h4 class="account-h4">Email</h4>
                            <v-text-field
                                :value="email"
                                label="Email Address"
                                readonly
                                solo>
                            </v-text-field>
                        </div>
                        <div>
                            <h4 class="account-h4">Date of Birth</h4>
                            <v-text-field
                                :value="dob"
                                label="Date of Birth"
                                readonly
                                solo>
                            </v-text-field>
                        </div>
                        <div v-if="type == 'Psychologist'">
                            <div>
                                <h4 class="account-h4">Address</h4>
                                <v-text-field
                                    :value="address"
                                    label="Address"
                                    readonly
                                    solo>
                                </v-text-field>
                            </div>
                            <div>
                                <h4 class="account-h4">Qualification</h4>
                                <v-text-field
                                    :value="qualification"
                                    label="Qualification"
                                    readonly
                                    solo>
                                </v-text-field>
                            </div>
                            <div>
                                <h4 class="account-h4">Practice Number</h4>
                                <v-text-field
                                    :value="regNumber"
                                    label="Practice Number"
                                    readonly
                                    solo>
                                </v-text-field>
                            </div>
                            <div>
                                <h4 class="account-h4">Description</h4>
                                <v-text-field
                                    :value="description"
                                    label="Description"
                                    readonly
                                    solo>
                                </v-text-field>
                            </div>
                            <div>
                                <h4 class="account-h4">Speciality</h4>
                                <v-text-field
                                    :value="speciality"
                                    label="Speciality"
                                    readonly
                                    solo>
                                </v-text-field>
                            </div>
                        </div>
                        <div v-else>
                            <div>
                                <h4 class="account-h4">Practice Number</h4>
                                <v-text-field
                                    :value="practiceNumber"
                                    label="Practice Number"
                                    readonly
                                    solo>
                                </v-text-field>
                            </div>
                            <div>
                                <h4 class="account-h4">Highest Certification</h4>
                                <v-text-field
                                    :value="certification"
                                    label="Highest Certification"
                                    readonly
                                    solo>
                                </v-text-field>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-xl-5">
                <div class="card">
                    <h3 class="card-header">Actions</h3>
                    <div class="card-body">
                        <div class="row">
                            <div class="col centerCol">
                                <span class="numberFont">{{ type }}</span> 
                                <div>Type</div>
                            </div>
                            <div class="col centerCol">
                                <span class="numberFont">{{ dateRegistered }}</span> 
                                <div>Date Registered</div>
                            </div>
                            <div class="col centerCol">
                                <span class="numberFont" :style="status == 'Inactive' ? 'color: red' : 'color: green'">{{ status }}</span> 
                                <div>Status</div>
                            </div>
                        </div>
                        <div class="mt-5" style="text-align: center;">
                            <button 
                            v-if="status != 'Active'"
                            type="button" 
                            class="btn btn-outline-primary ignoreThisBitch approve-btn"
                            @click="changeStatus(userID, 'Active')">
                                <span class="fas fa-check"></span> Activate Account
                            </button>
                            <button 
                            v-if="status == 'Active'"
                            type="button" 
                            class="btn btn-outline-primary ignoreThisBitch reject-btn" 
                            @click="changeStatus(userID, 'Inactive')">
                                <span class="fas fa-times"></span> Deactivate Account
                            </button>
                            <!-- 
                                make this page the same for all psychs/counsellors
                                approve/reject pending ones
                                deactivate active ones
                                active deactivated ones
                                etc
                            -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <v-snackbar
        v-model="snackbar"
        >
        {{ snackbarText }}

            <template v-slot:action="{ attrs }">
                <v-btn
                color="#FF9565"
                text
                v-bind="attrs"
                @click="snackbar = false"
                >
                Close
                </v-btn>
            </template>
        </v-snackbar>
    </v-app>
</template>

<script>
import moment from "moment";

export default {

  data() {
        return {
            
            snackbar: false,
            snackbarText: '',

            userID: 0,
            name: 'Tayyibah',
            email: 'tay@gmail.com',
            avatar: '',
            dob: '14 September 1997',
            type: 'Psychologist',
            dateRegistered: '14 April 2021',
            status: 'Pending',
            
            //psych
            address: '123 Street Road',
            qualification: 'BSc Computer Science & Informatics Ext',
            regNumber: '123psykid',
            description: 'Child ',
            speciality: 'child specialist',

            //counsellor
            practiceNumber: '123psykid',
            certification: 'hacker level 3',
        }
    },

    async mounted() {
        this.userID = parseInt(this.$route.query.id)
        await this.getUserById(this.userID)
    },

    methods: {
        async changeStatus(userID, status)
        {
            const params = {
                userID: userID,
                newStatus: status
            }
            const resp = await this.$store.dispatch('users/ChangeUserStatus', params)
            this.status = status

            if(status == 'Active')
                this.snackbarText = 'Account activated'
            else
                this.snackbarText = 'Account deactivated'
            this.snackbar = true

            //window.location.reload();
            // if(this.type == 'Psychologist')
            // {
            //     this.newRedirect('psychologists')
            // }
            // else
            // {
            //     this.newRedirect('counsellors')
            // }
        },

        newRedirect(page) {
	        this.$router.push({ path: page })
		},

        async getUserById(userID)
        {
            const user = await this.$store.dispatch('users/getUserById', userID)
            this.name = user.name
            this.email = user.email
            this.dob = moment(user.dob).format('DD MMMM YYYY')
            const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)
            this.avatar = images(`./${user.profilePicture}`)
            this.dateRegistered = moment(user.dateRegistered).format('DD MMMM YYYY')
            
            if(user.userType == 'Psychologist')
            {
                await this.fetchPsychologist(userID)
            }
            else
            {
                await this.fetchCounsellor(userID)
                this.status = user.status
            }
        },

        async fetchPsychologist(psychID)
        {
            const psychIDparam = {
                psychID: psychID,
            }
            const user = await this.$store.dispatch('users/fetchPsychologist', psychIDparam)
            this.address = user.address
            this.qualification = user.qualification
            this.regNumber = user.regNumber
            this.description = user.description
            this.speciality = user.speciality
            this.status = user.status
        },

        async fetchCounsellor(counsellorID)
        {
            const counsellorIDparam = {
                counsellorID: counsellorID,
            }
            const user = await this.$store.dispatch('users/fetchCounsellor', counsellorIDparam)
            this.practiceNumber = user.practiceNumber
            this.certification = user.highestCertificate
        },
    },  
}
</script>

<style scoped>

    .approve-btn
    {
        color: green;
        border-color: green;
    }

    .approve-btn:hover
    {
        color: white;
        background-color: #0e9a0ee0;
    }

    .approve-btn:not(:disabled):not(.disabled):active,
    .approve-btn:not(:disabled):not(.disabled).active,
    .show > .approve-btn.dropdown-toggle {
        color: #fff;
        border-color: #0d670de0;
        background-color: #0d670de0;
    }

    .reject-btn
    {
        color: red;
        border-color: red;
    }

    .reject-btn:hover
    {
        color: white;
        background-color: red;
    }

    .reject-btn:not(:disabled):not(.disabled):active,
    .reject-btn:not(:disabled):not(.disabled).active,
    .show > .reject-btn.dropdown-toggle {
        color: #fff;
        border-color: #ad0303e8;
        background-color: #ad0303e8;
    }

    .numberFont
    {
        font-size: 20px;
    }

    .centerCol
    {
        text-align: center;
    }

    .theme--light.v-application {
        background: #FBF5F2;
    }
    
    .v-application--wrap {
        min-height: 0;
    }
</style>

