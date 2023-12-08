<style>
    .v-application--wrap {
        min-height: 0;
    }    
</style>
<template>
	<div class="main-content">
        <v-app>
            <div class="row">
                <div class="col-xl-12">
                    <div class="card">
                        <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h3 class="mb-0">Analytics</h3>
                                </div>
                            </div>
                        </div>
                        <!-- <div class="container"> -->
                            <div class="row">
                                <div class="col-xl-6">
                                    <div id="chart">
                                        <apexchart type="bar" height="350" :options="bookingChartOptions" :series="bookingSeries"></apexchart>
                                    </div>                                 
                                </div>


                                <div class="col-xl-4" style="align-self: center;">
                                    <div id="chart">
                                        <apexchart type="donut" height="350" :options="pieChartOptions" :series="pieSeries"></apexchart>
                                    </div> 
                                </div>
                                
                                <div class="col-xl-2" style="align-self: center;">
                                    <div id="chart">
                                        <apexchart type="donut" height="350" :options="linkChartOptions" :series="linkSeries"></apexchart>
                                    </div> 
                                </div>
                            </div>
                        <!-- </div> -->
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xl-8">
                    <div class="card">
                        <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h3 class="mb-0">Analytics</h3>
                                </div>
                            </div>
                        </div>
                        <div class="container">
                            <!-- <div class="row"> -->
                                <div class="row">
                                    <div class="col-xl-12" style="align-self: center;">
                                        <div id="chart">
                                            <apexchart type="bar" height="350" :options="registerChartOptions" :series="registerSeries"></apexchart>
                                        </div>
                                    </div>
                                </div>
                                <div class="row" style="justify-content: center;">
                                    <h3>Total Users</h3>
                                </div>
                                <div class="row">
                                    <div class="col" style="text-align: -webkit-center;">
                                        <v-card
                                        color="#CE84FF"
                                        dark
                                        width="50"
                                        height="50"
                                        style="border-radius: 15px;">
                                            <v-card-text class="text-center cardStatText" style="width: 51px;">{{ numChild }}</v-card-text>
                                        </v-card>
                                        <div class="ml-2">
                                            <div>Children</div>
                                        </div>
                                    </div>
                                    <div class="col" style="text-align: -webkit-center;">
                                        <v-card
                                        color="#11D6FF"
                                        dark
                                        width="50"
                                        height="50"
                                        style="border-radius: 15px;">
                                            <v-card-text class="text-center cardStatText" style="width: 51px;">{{ numGuardians }}</v-card-text>
                                        </v-card>
                                        <div class="ml-2">
                                            <div>Guardians</div>
                                        </div>
                                    </div>
                                    <div class="col" style="text-align: -webkit-center;">
                                        <v-card
                                        color="#ff9090"
                                        dark
                                        width="50"
                                        height="50"
                                        style="border-radius: 15px;">
                                            <v-card-text class="text-center cardStatText" style="width: 51px;">{{ numPsychologists }}</v-card-text>
                                        </v-card>
                                        <div class="ml-2">
                                            <div>Psychologists</div>
                                        </div>
                                    </div>
                                    <div class="col" style="text-align: -webkit-center;">
                                        <v-card
                                        color="#54fc4e"
                                        dark
                                        width="50"
                                        height="50"
                                        style="border-radius: 15px;">
                                            <v-card-text class="text-center cardStatText" style="width: 51px;">{{ numCounsellors }}</v-card-text>
                                        </v-card>
                                        <div class="ml-2">
                                            <div>Counsellors</div>
                                        </div>
                                    </div>
                                </div>                        
                            <!-- </div> -->
                        </div>
                    </div>
                </div>


                <div class="col-xl-4 float-left">
                    <div class="card">
                        <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h3 class="mb-0">Pending Approvals</h3>
                                </div>    
                                <div class="col text-right">
                                    <button type="button" class="btn btn-sm btn-primary" @click="redirect('/#/approvals')">See all</button>
                                </div>
                            </div>
                        </div>

                        <div class="card-body overflow-y-auto" style="max-height: 290px">
                            <v-simple-table v-if="registrations.length > 0">
                                <template v-slot:default>
                                    <tbody style="text-align: center">
                                        <tr
                                        v-for="item in registrations"
                                        :key="item.userID"
                                        @click="newRedirect('approval', item.userID)"
                                        class="cursorClick" >
                                            <td>
                                                <v-avatar>
                                                    <img :src="item.avatar">
                                                </v-avatar>
                                            </td>
                                            <td>{{ item.name }}</td>
                                            <td>
                                                {{ item.userType }}
                                            </td>
                                        </tr>
                                    </tbody>
                                </template>
                            </v-simple-table>
                            <v-card 
                            v-else 
                            class="justify-center"
                            flat
                            style="text-align: center">
                                <span class="far fa-check-circle fa-5x" style="color: #FF9565"></span>
                                <v-card-title style="justify-content: center; color: #022F5B">Nothing here yet!</v-card-title>
                                <v-card-subtitle>Check back later to see if anyone has registered</v-card-subtitle>
                            </v-card>
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
	data() {
		return {
            numCounsellors: 0,
            numPsychologists: 0,
            numGuardians: 0,
            numChild: 0,

            registrations: 
            [
                // {
                //     userID: 0,
                //     avatar: '',
                //     name: 'Tayyibah Khan',
                //     userType: 'Psychologist',
                // },
            ],

            //linkSeries: [44, 55],
            linkSeries: [0, 0],
            linkChartOptions: {
                chart: 
                {
                    type: 'donut',
                    width: 380,
                    toolbar: {
                        show: false,
                    },
                },
                labels: ['Pairs Linked', 'Pairs Not Linked'],
                legend: {
                    position: 'bottom'
                },
                title:
                {
                    text: 'Pairs Linked vs Pairs Not Linked',
                    align: 'center',
                    style: {
                        fontSize:  '12px',
                        fontWeight:  '500',
                        fontFamily:  undefined,
                        color:  '#263238'
                    },
                },
                tooltip: {
                    enabled: true,
                }, 
                colors:['#91E391', '#f78bf7'],
            },

            //pieSeries: [44, 55, 41],
            pieSeries: [0, 0, 0],
            pieChartOptions: {
                chart: 
                {
                    type: 'donut',
                    width: 380,
                    toolbar: {
                        show: false,
                    },
                },
                labels: ['Only Children', 'Only Guardians', 'Pairs'],
                title:
                {
                    text: 'Only Children vs Only Guardians vs Pairs',
                    align: 'center',
                    style: {
                        fontSize:  '14px',
                        fontWeight:  '500',
                        fontFamily:  undefined,
                        color:  '#263238'
                    },
                },
                legend: {
                    position: 'bottom'
                },
                tooltip: {
                    enabled: true,
                }, 
                colors:['#FF9565', '#11D6FF', '#F44165'],
            },

            bookingSeries: [{
                name: 'Appointments',
                //data: [23, 31, 40, 71, 40, 36, 32, 23, 14, 8, 5, 2]
                data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
            }],
            bookingChartOptions: {
                chart: 
                {
                    height: 350,
                    type: 'bar',
                    toolbar: {
                        show: false,
                    },
                },
                plotOptions: {
                    bar: {
                        dataLabels: {
                            position: 'top', // top, center, bottom
                        },
                    }
                },
                dataLabels: {
                    enabled: true,
                    formatter: function (val) {
                        return val;
                    },
                    offsetY: -20,
                    style: {
                        fontSize: '12px',
                        colors: ["#304758"]
                    }
                },

                title:
                {
                    text: 'Appointments per Month',
                    align: 'center',
                    style: {
                        fontSize:  '16px',
                        fontWeight:  '600',
                        fontFamily:  undefined,
                        color:  '#263238'
                    },
                },
                xaxis: {
                    categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
                    position: 'top',
                    axisBorder: {
                        show: false
                    },
                    axisTicks: {
                        show: false
                    },
                    crosshairs: {
                        fill: {
                        type: 'gradient',
                        gradient: {
                            colorFrom: '#D8E3F0',
                            colorTo: '#BED1E6',
                            stops: [0, 100],
                            opacityFrom: 0.4,
                            opacityTo: 0.5,
                        }
                        }
                    },
                    tooltip: {
                        enabled: true,
                    }
                },
                yaxis: {
                    forceNiceScale: true,
                    axisBorder: {
                        show: false
                    },
                    axisTicks: {
                        show: false,
                    },
                    labels: {
                        show: false,
                        formatter: function (val) {
                            return val;
                        }
                    }
                },
                colors: ['#C56EFF']
            },

            registerSeries: 
            [
                // {
                //     name: 'Children',
                //     data: [44, 55, 57, 56, 61, 58, 63, 60, 66, 105, 91, 114]
                // },
                // {
                //     name: 'Guardians',
                //     data: [76, 85, 101, 98, 87, 105, 91, 114, 94, 98, 87, 105]
                // },
                // {
                //     name: 'Psychologists',
                //     data: [35, 41, 36, 26, 45, 48, 52, 53, 41, 35, 41, 36]
                // },
                // {
                //     name: 'Counsellors',
                //     data: [98, 87, 105, 26, 63, 60, 66, 53, 41, 76, 85, 101]
                // }
            ],
            registerChartOptions: {
                chart: {
                    type: 'bar',
                    height: 350,
                    width: '100%'
                },
                plotOptions: {
                    bar: {
                        horizontal: false,
                        // columnWidth: '55%',
                    },
                },
                dataLabels: {
                    enabled: false
                },
                stroke: {
                    show: true,
                    width: 2,
                    colors: ['transparent']
                },
                xaxis: {
                    categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                },
                yaxis: {
                    forceNiceScale: true,
                },
                fill: {
                    opacity: 1
                },
                tooltip: {
                    y: {
                        formatter: function (val) {
                            return val + " registrations"
                        }
                    }
                },
                title: {
                    text: 'Number of Registrations Per Month',
                    align: 'center',
                    style: {
                        fontSize:  '16px',
                        fontWeight:  '600',
                        fontFamily:  undefined,
                        color:  '#263238'
                    },
                },
                colors: ['#CE84FF', '#11D6FF', '#ff9090', '#54fc4e']
            },
      	}
	},

	async mounted() {
		const user = JSON.parse(window.localStorage.getItem('currentUser'))
        const adminID = parseInt(user.userID)
        await this.getPendingAccounts()
        await this.getBookingsCountsPerMonthAdmin()
        await this.getOnlyKids()
        await this.getNumPairsLinked()
        await this.getNumPsychCouns()
        await this.getUserTypeRegistrationsPerMonth()

        // bookingSeries: [{
        //     name: 'Appointments',
        //     data: [23, 31, 40, 71, 40, 36, 32, 23, 14, 8, 5, 2]
        // }],

        var max = this.bookingSeries[0].data.reduce(function(a, b) {
            return Math.max(a, b);
        }, 0);
        max = parseInt(max / 10, 10) + 1 * 10
        
        this.bookingChartOptions = {
            yaxis: {
                max: max
            }
        }
    },

	methods: {
    
        redirect: function(link) {
            window.location.href = link;
        },

        newRedirect(page, paramValue) {
			if(paramValue != undefined)
			{
				this.$router.push({ path: page, query: { id: paramValue } })
			}
		},

        async getUserTypeRegistrationsPerMonth()
        {
            
            const childRegistrations = await this.$store.dispatch('reports/getUserTypeRegistrationsPerMonth', 'Child')
            var tempChildData = []
            childRegistrations.forEach(element => {
                tempChildData.push(element.bookingCount)
            });

            const guardianRegistrations = await this.$store.dispatch('reports/getUserTypeRegistrationsPerMonth', 'Parent')
            var tempGuardianData = []
            guardianRegistrations.forEach(element => {
                tempGuardianData.push(element.bookingCount)
            });

            const psychRegistrations = await this.$store.dispatch('reports/getUserTypeRegistrationsPerMonth', 'Psychologist')
            var tempPsychData = []
            psychRegistrations.forEach(element => {
                tempPsychData.push(element.bookingCount)
            });

            const counsRegistrations = await this.$store.dispatch('reports/getUserTypeRegistrationsPerMonth', 'Counsellor')
            var tempCounsData = []
            counsRegistrations.forEach(element => {
                tempCounsData.push(element.bookingCount)
            });
            
            this.registerSeries = 
            [
                {
                    name: 'Children',
                    data: tempChildData
                },
                {
                    name: 'Guardians',
                    data: tempPsychData
                },
                {
                    name: 'Psychologists',
                    data: tempGuardianData
                },
                {
                    name: 'Counsellors',
                    data: tempCounsData
                },
            ]
        },
        
        async getPendingAccounts()
        {
            const pendingAccounts = await this.$store.dispatch('users/getPendingAccounts')
            const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)

            if(pendingAccounts.length > 0)
            {
                pendingAccounts.forEach(element => {
                    var temp =
                    {
                        userID: element.userID,
                        avatar: images(`./${element.profilePicture}`),
                        name: element.name,
                        userType: element.userType
                    }
                    this.registrations.push(temp)
                });
            }
        },

        async getBookingsCountsPerMonthAdmin()
        {
            const allBookings = await this.$store.dispatch('reports/getBookingsCountsPerMonthAdmin')
            
            var tempData = []
            allBookings.forEach(element => {
                tempData.push(element.bookingCount)
            });
            this.bookingSeries = [{
                data: tempData
            }]
        },

        async getOnlyKids()
        {
            var onlyKids = await this.$store.dispatch('reports/getOnlyKids')
            var onlyGuardians = await this.$store.dispatch('reports/getOnlyGuardians')
            var getNumPairs = await this.$store.dispatch('reports/getNumPairs')
            this.pieSeries = [onlyKids, onlyGuardians, getNumPairs]
        },

        async getNumPairsLinked()
        {
            var numPairsLinked = await this.$store.dispatch('reports/getNumPairsLinked')
            var numPairsNotLinked = await this.$store.dispatch('reports/getNumPairsNotLinked')
            this.linkSeries = [numPairsLinked, numPairsNotLinked]
        },

        async getNumPsychCouns()
        {
            this.numPsychologists = await this.$store.dispatch('users/getNumPsychologists')
            this.numCounsellors = await this.$store.dispatch('users/getNumCounsellors')
            this.numGuardians = await this.$store.dispatch('users/getNumGuardians')
            this.numChild = await this.$store.dispatch('users/getNumChild')
        },
	},
}
</script>

<style scoped>

    .cardStatText
    {
        color: white !important;
        font-size: 16px;
    }

    .cursorClick {
        cursor: pointer;
    }

    .theme--light.v-application {
        background: #FBF5F2;
    }
    
    .v-application--wrap {
        min-height: 0;
    }

</style>
