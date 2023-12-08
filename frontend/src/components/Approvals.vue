<template>
    <div>
        <v-app>
            <div class="container-fluid mt-2">
                <div class="row">
                    <div class="col">
                        <v-expansion-panels v-model="panels">
                            <v-expansion-panel>
                                <v-expansion-panel-header>
                                    Pending Account Approvals
                                </v-expansion-panel-header>
                                <v-expansion-panel-content>
                                    <div class="card">
                                        <!-- Light table -->
                                        <div class="table-responsive" v-show="!nothingHerePending">
                                            <table class="table align-items-center table-flush">
                                                <thead class="thead-light">
                                                <tr>
                                                    <th scope="col" class="sort" data-sort="name">User</th>
                                                    <th scope="col" class="sort" data-sort="budget">Type</th>
                                                    <th scope="col" class="sort" data-sort="status">Date Registered</th>
                                                    <th scope="col" class="sort" data-sort="completion">Action</th>
                                                    <!-- <th scope="col"></th> -->
                                                </tr>
                                                </thead>
                                                <tbody class="list" id="patientlist">
                                                    <tr 
                                                    class="cursorClick"
                                                    v-for="item in pending"
                                                    :key="item.userID">
                                                    <!-- @click="newRedirect('approval', item.userID)"> -->
                                                        <th scope="row" @click="newRedirect('approval', item.userID)">
                                                            <div class="media align-items-center">
                                                                <div class="avatar rounded-circle mr-3">
                                                                    <img :src="item.avatar" class="avatar rounded-circle">
                                                                </div>
                                                                <!-- <a href="#" class="avatar rounded-circle mr-3">
                                                                    <img :src="getUserAvatar(patient.patientInfo)" class="avatar rounded-circle">
                                                                </a> -->
                                                                <div class="media-body">
                                                                    <span class="name mb-0 text-sm" style="color: #32325d">{{ item.name }}</span>                                                    
                                                                <!-- <span class="name mb-0 text-sm">{{ patient.patientInfo.name }}</span> -->
                                                                </div>
                                                            </div>
                                                        </th>
                                                        <td class="budget" @click="newRedirect('approval', item.userID)">
                                                            {{ item.userType }}
                                                        </td>
                                                        <td class="budget" @click="newRedirect('approval', item.userID)">
                                                            {{ item.dateRegistered }}
                                                        </td>
                                                        <td >
                                                            <!-- <button 
                                                            class="btn btn-primary"
                                                            @click="removePatient(patient)">
                                                                Remove Patient
                                                            </button> -->
                                                            <v-icon 
                                                            class="outlinedCheck" 
                                                            color="green"
                                                            @click="changeStatus(item.userID, 'Active')">
                                                                mdi-check
                                                            </v-icon>
                                                            <v-icon 
                                                            class="outlinedCross" 
                                                            color="red"
                                                            @click="changeStatus(item.userID, 'Rejected')">
                                                                mdi-close
                                                            </v-icon>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>

                                        <v-card 
                                        v-show="nothingHerePending == true" 
                                        class="justify-center"
                                        flat
                                        style="text-align: center">
                                            <span class="far fa-check-circle fa-5x" style="color: #FF9565"></span>
                                            <v-card-title style="justify-content: center; color: #022F5B">Nothing here yet!</v-card-title>
                                            <v-card-subtitle>Check back later to see if anyone has registered</v-card-subtitle>
                                        </v-card>
                                    </div>
                                </v-expansion-panel-content>
                            </v-expansion-panel>

                            <v-expansion-panel v-if="!nothingHereRejected">
                                <v-expansion-panel-header>
                                    Rejected Accounts
                                </v-expansion-panel-header>
                                <v-expansion-panel-content>
                                    <div class="card">
                                        <!-- Light table -->
                                        <div class="table-responsive">
                                            <table class="table align-items-center table-flush">
                                                <thead class="thead-light">
                                                <tr>
                                                    <th scope="col" class="sort" data-sort="name">User</th>
                                                    <th scope="col" class="sort" data-sort="budget">Type</th>
                                                    <th scope="col" class="sort" data-sort="status">Date Registered</th>
                                                    <th scope="col" class="sort" data-sort="completion">Action</th>
                                                    <!-- <th scope="col"></th> -->
                                                </tr>
                                                </thead>
                                                <tbody class="list" id="patientlist">
                                                    <tr 
                                                    class="cursorClick"
                                                    v-for="item in rejected"
                                                    :key="item.userID"
                                                    @click="newRedirect('approval', item.userID)">
                                                        <th scope="row">
                                                            <div class="media align-items-center">
                                                                <div class="avatar rounded-circle mr-3">
                                                                    <img :src="item.avatar" class="avatar rounded-circle">
                                                                </div>
                                                                <div class="media-body">
                                                                    <span class="name mb-0 text-sm" style="color: #32325d">{{ item.name }}</span>                                                    
                                                                </div>
                                                            </div>
                                                        </th>
                                                        <td class="budget">
                                                            {{ item.userType }}
                                                        </td>
                                                        <td class="budget">
                                                            {{ item.dateRegistered }}
                                                        </td>
                                                        <td>
                                                            <v-icon 
                                                            class="outlinedCheck" 
                                                            color="green"
                                                            @click="newRedirect('')">
                                                                mdi-check
                                                            </v-icon>
                                                        <!-- <v-icon 
                                                            class="outlinedCross" 
                                                            color="red"
                                                            @click="newRedirect('')">
                                                                mdi-close
                                                            </v-icon> -->
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </v-expansion-panel-content>
                            </v-expansion-panel>
                        </v-expansion-panels>
                    </div>
                </div>
            </div>
        </v-app>
    </div>
</template>

<script>
import moment from 'moment'

export default {

    data() {
		return {
            panels: 0,
            pending: 
            [
                // {
                //     userID: 0,
                //     avatar: '',
                //     name: '',
                //     type: '',
                //     dateRegistered: '',
                // },
            ],

            rejected: 
            [
                // {
                //     userID: 0,
                //     avatar: '',
                //     name: '',
                //     type: '',
                //     dateRegistered: '',
                // },
            ],
            nothingHerePending: false,
            nothingHereRejected: false,
        }
    },
    
    async mounted() {
        await this.getPendingAccounts()
        await this.getRejectedAccounts()
    },

    methods: {

        async getRejectedAccounts()
        {
            const rejectedAccounts = await this.$store.dispatch('users/getRejectedAccounts')
            
            if(rejectedAccounts == '')
                this.nothingHereRejected = true
            else
            {
                const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)

                rejectedAccounts.forEach(element => {
                    var temp =
                    {
                        userID: element.userID,
                        avatar: images(`./${element.profilePicture}`),
                        name: element.name,
                        userType: element.userType,
                        dateRegistered: moment(element.dateRegistered).format('DD MMMM YYYY'),
                    }
                    this.rejected.push(temp)
                });
            }
        },

        async getPendingAccounts()
        {
            const pendingAccounts = await this.$store.dispatch('users/getPendingAccounts')
            
            if(pendingAccounts == '')
                this.nothingHerePending = true
            else
            {
                const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)

                pendingAccounts.forEach(element => {
                    var temp =
                    {
                        userID: element.userID,
                        avatar: images(`./${element.profilePicture}`),
                        name: element.name,
                        userType: element.userType,
                        dateRegistered: moment(element.dateRegistered).format('DD MMMM YYYY'),
                    }
                    this.pending.push(temp)
                });
            }
        },
        
        newRedirect(page, paramValue) {
			if(paramValue != undefined)
			{
				this.$router.push({ path: page, query: { id: paramValue } })
			}
		},

        async changeStatus(userID, status)
        {
            const params = {
                userID: userID,
                newStatus: status
            }
            const resp = await this.$store.dispatch('users/ChangeUserStatus', params)
            //this.newRedirect('approvals')
            location.reload();
        },
    },
}
</script>

<style scoped>

    .cursorClick
    {
        cursor: pointer;
    }

    .v-icon.outlinedCheck
    {
        border: 1px solid green;
        border-radius:50%;
        height: 35px;
        width: 35px;
    }

    .outlinedCheck:hover 
    {
        border: 2px solid green;
    }

    .outlinedCheck:active 
    {
        background-color: #0e9a0ee0;
    }

    .v-icon.outlinedCross
    {
        border: 1px solid red;
        border-radius:50%;
        height: 35px;
        width: 35px;
    }

    .outlinedCross:hover 
    {
        border: 2px solid red;
    }

    .outlinedCross:active 
    {
        background-color: #f82f2fe8;
    }

    .theme--light.v-application {
        background: #FBF5F2;
    }
    
    .v-application--wrap {
        min-height: 0;
    }
    
</style>