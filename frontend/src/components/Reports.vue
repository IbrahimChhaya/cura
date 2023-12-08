<style>
    .v-application--wrap {
        min-height: 0;
    }    
</style>
<template>
	<div class="main-content">
        <v-app>
            <div class="row">
                <div class="col-xl-8">
                    <div class="card">
                        <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h3 class="mb-0">{{ endDate + ' - ' + startDate}}</h3>
                                </div>
                            </div>
                        </div>
                        <div class="container">
                            <div class="row">
                                <div class="col-xl-8">
                                    <div id="chart">
                                        <apexchart type="donut" height="350" :options="chartOptions" :series="series"></apexchart>
                                    </div>                                
                                </div>

                                <div class="col-xl-4" style="align-self: center;">
                                    <div class="row mb-5">
                                        <v-card
                                        color="#ff9090"
                                        dark
                                        width="50"
                                        height="50"
                                        style="border-radius: 15px">
                                            <v-card-text class="text-center cardStatText">{{ numNewPatients }}</v-card-text>
                                        </v-card>
                                        <div class="ml-2">
                                            <div>New Patients</div>
                                            <div
                                            class="percentStat"
                                            :style="percNewPatients.substring(0, 1) == '-' ? 'color: red;' : percNewPatients.substring(0, 1) == '+' ? 'color: limegreen;' : '' ">
                                                {{ percNewPatients }}
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row mt-5">
                                        <v-card
                                        color="#c56eff"
                                        dark
                                        width="50"
                                        height="50"
                                        style="border-radius: 15px">
                                            <v-card-text class="text-center cardStatText">{{ numNewBookings }}</v-card-text>
                                        </v-card>
                                        <div class="ml-2">
                                            <div>New Appointments</div>
                                            <div
                                            class="percentStat"
                                            :style="percNewBookings.substring(0, 1) == '-' ? 'color: red;' : percNewBookings.substring(0, 1) == '+' ? 'color: limegreen;' : '' ">
                                                {{ percNewBookings }}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-4 float-left">
                    <div class="card">
                        <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h3 class="mb-0">Average Child Mood</h3>
                                </div>
                            </div>
                        </div>

                        <div class="card-body overflow-y-auto" style="max-height: 343px">
                            <v-col
                            cols="12"
                            style="margin-top: -3rem;">
                                <v-text-field
                                    ref="search"
                                    v-model="search"
                                    full-width
                                    hide-details
                                    label="Search"
                                    single-line >
                                </v-text-field>
                            </v-col>
                            <v-simple-table>
                                <template v-slot:default>
                                    <tbody style="text-align: center">
                                        <tr
                                        v-for="item in categories"
                                        :key="item.childID"
                                        @click="newRedirect('profile', item.childID)"
                                        class="cursorClick" >
                                            <td>
                                                <v-avatar>
                                                    <img :src="item.childAvatar">
                                                </v-avatar>
                                            </td>
                                            <td>{{ item.childName }}</td>
                                            <td>
                                                <v-avatar size="40">
                                                    <img v-if="item.childMood != 'N/A'" :src="item.childMood">
                                                    <span v-if="item.childMood == 'N/A'">N/A</span>
                                                </v-avatar>
                                            </td>
                                        </tr>
                                    </tbody>
                                </template>
                            </v-simple-table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xl-12">
                    <div class="card">
                        <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h3 class="mb-0">This Year </h3>
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
                                <div class="col-xl-6">
                                    <div id="agechart">
                                        <apexchart type="bar" height="350" :options="ageChartOptions" :series="ageSeries"></apexchart>
                                    </div>  
                                </div>               
                            </div>
                            <div class="row">
                                <div class="col-xl-6">
                                    <div id="focuschart">
                                        <apexchart type="bar" height="350" :options="focusChartOptions" :series="focusSeries"></apexchart>
                                    </div>  
                                </div>       
                                <div class="col-xl-6">
                                    <div id="focusagechart">
                                        <apexchart type="bar" height="350" :options="focusAgeChartOptions" :series="focusAgeSeries"></apexchart>
                                    </div>  
                                </div>           
                            </div>
                        <!-- </div> -->
                    </div>
                    <div class="card">
                        <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h3 class="mb-0">Analysis</h3>
                                </div>
                            </div>
                        </div>
                        <!-- <div class="container"> -->
                            <div class="row">
                                <div class="offset-xl-1 col-xl-5">
                                    According to historical data, next month ({{ nextMonth }}) you may have approximately {{ numBookingsLastYear }} appointments
                                </div>    
                                <div class="col-xl-6">
                                    <div id="agechart">
                                        <apexchart type="bar" height="350" :options="timeChartOptions" :series="timeSeries"></apexchart>
                                    </div>  
                                </div>
                            </div>
                            <!-- <div class="row">
                                <div class="col-xl-6">
                                    <div id="focuschart">
                                        <apexchart type="bar" height="350" :options="focusChartOptions" :series="focusSeries"></apexchart>
                                    </div>  
                                </div>       
                                <div class="col-xl-6">
                                    <div id="focusagechart">
                                        <apexchart type="bar" height="350" :options="focusAgeChartOptions" :series="focusAgeSeries"></apexchart>
                                    </div>  
                                </div>           
                            </div> -->
                        <!-- </div> -->
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
            nextMonth: 'July 2021',
            numBookingsLastYear: 5,

            childMoods:
            [
                // {
                //     childID: 0,
                //     childName: 'Nathan Drake',
                //     childAvatar: '',
                //     childMood: '',
                // },
            ],

            focusAge: 
            [
                {
                    age: 7,
                    focus: 'Depression',
                },
                {
                    age: 8,
                    focus: 'Anxiety',
                },
                {
                    age: 9,
                    focus: 'Bullying',
                },
                {
                    age: 10,
                    focus: 'Abuse',
                },
                {
                    age: 11,
                    focus: 'Anxiety',
                },
                {
                    age: 12,
                    focus: 'Depression',
                },
            ],

            search: '',
            selected: [],

            startDate:       '13 August',
            endDate:         '13 September 2021',
            numNewPatients:  0,
            percNewPatients: '0%',
            numNewBookings:  0,
            percNewBookings: '0%',

                    bookings:               [],
                    bookingsPatients:       [],
                    formattedBookings:      [],
                    nextBooking:            null,
                    nextPatient:            null,
                    nextPatientParent:      null,
                    nextPatientPrevBooking: null,

            colors:   ["#2196F3", "#fb6340", "#11cdef", "#2dce89", "#f5365c"],

            attendedMeetings:
            [
                {
                    weekStartDate: '2021-09-12',
                    count: 0,
                }
            ],

            // series: [44, 55, 41],
            series: [],
            chartOptions: {
                chart: 
                {
                    type: 'donut',
                    width: 380,
                    toolbar: {
                        show: false,
                    },
                },
                labels: ['Attended', 'Cancelled', 'Missed'],
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
                }],
                legend: {
                    position: 'bottom'
                },
                tooltip: {
                    enabled: true,
                }, 
                colors:['#FF9565', '#11D6FF', '#F44165'],
            },

            ageSeries: 
            [
                // {
                //     name: 'Age 7',
                //     data: [23, 31, 40, 71, 40, 36, 32, 23, 14, 8, 5, 2]
                // }, 
                // {
                //     name: 'Age 8',
                //     data: [13, 23, 20, 8, 13, 27, 32, 23, 14, 8, 5, 2]
                // }, 
                // {
                //     name: 'Age 9',
                //     data: [11, 17, 15, 15, 21, 14, 32, 23, 14, 8, 5, 2]
                // }, 
                // {
                //     name: 'Age 10',
                //     data: [21, 7, 25, 13, 22, 8, 32, 23, 14, 8, 5, 2]
                // }
            ],
            ageChartOptions: {
                chart: 
                {
                    height: 350,
                    type: 'bar',
                    stacked: true,
                    toolbar: {
                        show: false,
                    },
                },
                plotOptions: {
                    bar: {
                        dataLabels: {
                            position: 'center', // top, center, bottom
                        },
                    }
                },
                dataLabels: {
                    enabled: true,
                    formatter: function (val) {
                        return val;
                    },
                    
                },
                title:
                {
                    text: 'Ages per Month',
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
                    tooltip: {
                        enabled: true,
                    }
                },
                yaxis: {
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
                colors: ['#2196F3', '#91E391', '#FEBC3B', '#455A64', '#594EFC', '#F78BF7', '#F5365C']
            },

            bookingSeries: [{
                name: 'Appointments',
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

            focusSeries: 
            [
                // {
                //     name: 'Anxiety',
                //     data: [21, 7, 25, 13, 22, 8, 32, 23, 14, 8, 5, 2]
                // }, 
                // {
                //     name: 'Bipolar',
                //     data: [21, 7, 25, 13, 22, 8, 32, 23, 14, 8, 5, 2]
                // }, 
                // {
                //     name: 'Depression',
                //     data: [21, 7, 25, 13, 22, 8, 32, 23, 14, 8, 5, 2]
                // }, 
                // {
                //     name: 'Eating Disorder',
                //     data: [21, 7, 25, 13, 22, 8, 32, 23, 14, 8, 5, 2]
                // },
                // {
                //     name: 'OCD',
                //     data: [21, 7, 25, 13, 22, 8, 32, 23, 14, 8, 5, 2]
                // }, 
                // {
                //     name: 'Bullying',
                //     data: [21, 7, 25, 13, 22, 8, 32, 23, 14, 8, 5, 2]
                // },
                // {
                //     name: 'Abuse',
                //     data: [21, 7, 25, 13, 22, 8, 32, 23, 14, 8, 5, 2]
                // }
            ],
            focusChartOptions: {
                chart: 
                {
                    height: 350,
                    type: 'bar',
                    stacked: true,
                    toolbar: {
                        show: false,
                    },
                },
                plotOptions: {
                    bar: {
                        dataLabels: {
                            position: 'center', // top, center, bottom
                        },
                    }
                },
                dataLabels: {
                    enabled: true,
                    formatter: function (val) {
                        return val;
                    },
                    
                },
                title:
                {
                    text: 'Focus Points per Month',
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
                    tooltip: {
                        enabled: true,
                    }
                },
                yaxis: {
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
                colors: ['#2196F3', '#fb6340', '#11cdef', '#2dce89', '#f5365c', '#C56EFF', '#91E391', '#FEBC3B', '#455A64', '#594EFC', '#F78BF7', '#940202FF']
            },

            focusAgeSeries: 
            [
                // {
                //     name: 'Anxiety',
                //     data: [23, 31, 40, 71, 40, 36, 32]
                // }, 
                // {
                //     name: 'Bipolar',
                //     data: [13, 23, 20, 8, 13, 27, 32]
                // }, 
                // {
                //     name: 'Depression',
                //     data: [11, 17, 15, 15, 21, 14, 32]
                // }, 
                // {
                //     name: 'Eating Disorder',
                //     data: [21, 7, 25, 13, 22, 8, 32]
                // },
                // {
                //     name: 'OCD',
                //     data: [11, 17, 15, 15, 21, 14, 32]
                // }, 
                // {
                //     name: 'Bullying',
                //     data: [21, 7, 25, 13, 22, 8, 32]
                // },
                // {
                //     name: 'Abuse',
                //     data: [21, 7, 25, 13, 22, 8, 32]
                // }
            ],
            focusAgeChartOptions: {
                chart: 
                {
                    height: 350,
                    type: 'bar',
                    stacked: true,
                    toolbar: {
                        show: false,
                    },
                },
                plotOptions: {
                    bar: {
                        dataLabels: {
                            position: 'center', // top, center, bottom
                        },
                    }
                },
                dataLabels: {
                    enabled: true,
                    formatter: function (val) {
                        return val;
                    },
                    
                },
                title:
                {
                    text: 'Most Common Focus Points per Age',
                    align: 'center',
                    style: {
                        fontSize:  '16px',
                        fontWeight:  '600',
                        fontFamily:  undefined,
                        color:  '#263238'
                    },
                },
                xaxis: {
                    categories: ["Age 7", "Age 8", "Age 9", "Age 10", "Age 11", "Age 12", "Age 13"],
                    position: 'top',
                    axisBorder: {
                        show: false
                    },
                    axisTicks: {
                        show: false
                    },
                    tooltip: {
                        enabled: true,
                    }
                },
                yaxis: {
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
                colors: ['#2196F3', '#fb6340', '#11cdef', '#2dce89', '#f5365c', '#C56EFF', '#91E391', '#FEBC3B', '#455A64', '#594EFC', '#F78BF7', '#940202FF']
            },

            timeSeries: [{
                name: 'Number of Appointments',
                //data: [1, 2, 3, 4, 5, 6, 7]
                data: [0, 0, 0, 0, 0, 0, 0]
            }],
            timeChartOptions: {
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
                    text: 'Popular Time Slots',
                    align: 'center',
                    style: {
                        fontSize:  '16px',
                        fontWeight:  '600',
                        fontFamily:  undefined,
                        color:  '#263238'
                    },
                },
                xaxis: {
                    categories: ["08:00", "08:00", "08:00", "08:00", "08:00", "08:00", "08:00"],
                    position: 'bottom',
                    axisBorder: {
                        show: true
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
      	}
	},

	watch: {
      selected () {
        this.search = ''
      },
    },

    computed: {
        categories () {
            const search = this.search.toLowerCase()

            if (!search) return this.childMoods

            return this.childMoods.filter(item => {
                const text = item.childName.toLowerCase()
                return text.indexOf(search) > -1
            })
        },
    },

	async mounted() {
		const user = JSON.parse(window.localStorage.getItem('currentUser'))
        const psychID = parseInt(user.userID)
		await this.getNumberOfNewPatientsThisMonth(psychID)
		await this.getPatientPercentIncrease(psychID)
		await this.getNumBookingsThisMonth(psychID)
		await this.getBookingsPercentIncrease(psychID)
        await this.GetPsychologistPatients(psychID)
        await this.getNumEachBookingPerPsych(psychID)
        await this.getBookingsCountsPerMonth(psychID)
        await this.getBookingsMonthlyAgeCounts(psychID)
        await this.getBookingsMonthlyFocusCounts(psychID)
        await this.getFocusCountsPerAge(psychID)
        await this.numBookingsLastYearPerMonth(psychID)
        await this.getPopularTimes(psychID)

        var max = this.bookingSeries[0].data.reduce(function(a, b) {
            return Math.max(a, b);
        }, 0);
        max = parseInt(max / 10, 10) + 1 * 10
        
        this.bookingChartOptions = {
            yaxis: {
                max: max,
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
            }
        }
	},

	methods: {

        async getPopularTimes(psychID)
        {
            const resp = await this.$store.dispatch('reports/getPopularTimes', psychID)
            var tempData = []
            var tempTimes = []
            resp.forEach(element => {
                tempTimes.push(moment(element.month, 'HH:mm:ss').format('HH:mm'))
                tempData.push(element.bookingCount)
            });
            this.timeSeries = [{
                data: tempData
            }]

            var max = this.timeSeries[0].data.reduce(function(a, b) {
                return Math.max(a, b);
            }, 0);
            //max = parseInt(max / 10, 10) + 1 * 10
            
            this.timeChartOptions = {
                yaxis: {
                    max: max + 1,
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
                xaxis: {
                    categories: tempTimes,
                    position: 'bottom',
                    axisBorder: {
                        show: true
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
                }
            }
        },

        async numBookingsLastYearPerMonth(psychID)
        {
            this.numBookingsLastYear = await this.$store.dispatch('reports/numBookingsLastYearPerMonth', psychID)
            this.nextMonth = moment().add(1, 'months').format('MMMM YYYY')

        },

        async getFocusCountsPerAge(psychID)
        {
            var tempSeries = []

            const focusPerAge = await this.$store.dispatch('reports/getFocusCountsPerAge', psychID)
            focusPerAge.forEach(element => {
                
                var temp =
                {
                    name: element.focusPoint,
                    data: element.counts
                }
                tempSeries.push(temp)
                
            });
            
            this.focusAgeSeries = tempSeries
        },

        async getBookingsMonthlyFocusCounts(psychID)
        {
            var tempSeries = []

            const focusPerMonth = await this.$store.dispatch('reports/getBookingsMonthlyFocusCounts', psychID)
            focusPerMonth.forEach(element => {
                
                var temp =
                {
                    name: element.focusName,
                    data: element.monthlyCounts
                }
                tempSeries.push(temp)
                
            });
            this.focusSeries = []
            this.focusSeries = tempSeries
        },

        async getBookingsMonthlyAgeCounts(psychID)
        {
            var tempSeries = []

            const agesPerMonth = await this.$store.dispatch('reports/getBookingsMonthlyAgeCounts', psychID)
            agesPerMonth.forEach(element => {
                var agePlease = element.focusName
                if(element.focusName == 0)
                    agePlease = 'N/A'
                var temp =
                {
                    name: 'Age ' + agePlease,
                    data: element.monthlyCounts
                }
                tempSeries.push(temp)
            });
            this.ageSeries = []
            this.ageSeries = tempSeries
        },

        async getBookingsCountsPerMonth(psychID)
        {
            const bookingsPerMonth = await this.$store.dispatch('reports/getBookingsCountsPerMonth', psychID)
            
            //this.bookingSeries[0].data = []
            var tempData = []
            bookingsPerMonth.forEach(element => {
                tempData.push(element.bookingCount)
            });
            this.bookingSeries = [{
                data: tempData
            }]
        },

        newRedirect(page, paramValue) 
        {
            if(paramValue != undefined)
            {
                this.$router.push({ path: page, query: { id: paramValue } })
            }
        },

        async GetPsychologistPatients(psychID)
        {
            const children = await this.$store.dispatch('patients/GetPsychologistPatients', psychID)
            const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)

            children.forEach(async element => {
                var moodBlob = ''
                switch(await this.$store.dispatch('tests/getAverageMoodPerWeek', element.userID)) {
                    case 0:
                        moodBlob = 'N/A'
                        break;
                    case 1:
                        moodBlob = 'blobsob.png'
                        break;
                    case 2:
                        moodBlob = 'blobsad.png'
                        break;
                    case 3:
                        moodBlob = 'blobneutral.png'
                        break;
                    case 4:
                        moodBlob = 'blobsmile.png'
                        break;
                    case 5:
                        moodBlob = 'blobgrin.png'
                        break;
                }

                var temp = 
                {
                    childID: element.userID,
                    childName: element.name,
                    childAvatar: images(`./${element.profilePicture}`),
                }
                if(moodBlob != 'N/A')
                {
                    temp.childMood = images(`./${moodBlob}`)
                }
                else
                    temp.childMood = moodBlob

                this.childMoods.unshift(temp)
            });
        },

        async getNumBookingsThisMonth(psychID)
        {
            const requestParams = {
                dateNow: "1999-06-15",
                psychID: psychID
            }
            this.numNewBookings = await this.$store.dispatch('reports/getNumBookingsThisMonth', requestParams)
        },

        async getBookingsPercentIncrease(psychID)
        {
            var percNewBookings = await this.$store.dispatch('reports/getBookingsPercentIncrease', psychID)
            
            if(percNewBookings > 0)
            {
                this.percNewBookings = '+' + percNewBookings + '%'
            }
            else if(percNewBookings == 0)
            {
                this.percNewBookings = 'No Change'
            }
            else
                this.percNewBookings = percNewBookings + '%'
        },


        async getNumEachBookingPerPsych(psychID)
        {
            const resp = await this.$store.dispatch('reports/getNumEachBookingPerPsych', psychID)

            this.series.push(resp.attended)
            this.series.push(resp.cancelled)
            this.series.push(resp.missed)
            this.startDate = moment(resp.startDate).format('DD MMMM YYYY')
            this.endDate = moment(resp.endDate).format('DD MMMM')
        },

		newRedirect(page, paramValue) {
			if(paramValue != undefined)
			{
				this.$router.push({ path: page, query: { id: paramValue } })
			}
		},

        async getNumberOfNewPatientsThisMonth(psychID)
        {
            this.numNewPatients = await this.$store.dispatch('reports/getNumberOfNewPatientsThisMonth', psychID)
        },

        async getPatientPercentIncrease(psychID)
        {
            var percNewPatients = await this.$store.dispatch('reports/getPatientPercentIncrease', psychID)
            
            if(percNewPatients > 0)
            {
                this.percNewPatients = '+' + percNewPatients + '%'
            }
            else if(percNewPatients == 0)
            {
                this.percNewPatients = 'No Change'
            }
            else
                this.percNewPatients = percNewPatients + '%'
        },
	},

}
</script>

<style scoped>
    .cursorClick {
        cursor: pointer;
    }

    .cardStatText
    {
        color: white !important;
        font-size: 16px;
    }

    .percentStat
    {
        font-size: medium;
    }

    .theme--light.v-application {
        background: #FBF5F2;
    }
    
    .v-application--wrap {
        min-height: 0;
    }

    .apexcharts-bar-series.apexcharts-plot-series  .apexcharts-series path {
        clip-path: inset(0% 0% -11% 0% round 8px);
    }

</style>
