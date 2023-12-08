<template>
	<div class="main-content">
        <v-app>
            <div class="row">
                <div class="col-xl-8">
                    <div class="card">
                        <div style="display: flex">
                            <div style="width: 70%">
                                <img src="../assets/img/counsellorHome.png" width="450px">
                            </div>
                            <div style="align-self: center">
                                <h2>Get started</h2>
                                <button type="button" class="btn btn-primary" @click="newRedirect('chats', '')">Start Chats</button>
                            </div>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h3 class="mb-0">Analytics</h3>
                                </div>
                                <div class="col text-right">
                                    <!-- <a href="#!" class="btn btn-sm btn-primary" style="color: white;" disabled>See all</a> -->
                                    <button type="button" class="btn btn-primary btn-sm" disabled>See All</button>
                                </div>
                            </div>
                        </div>
                        <div class="container">
                            <div class="row">
                                <div class="col">
                                    <div id="chart">
                                        <apexchart type="bar" height="350" :options="chartOptions" :series="series"></apexchart>
                                    </div>                        
                                </div>
                                <div class="col">
                                    <h2 style="text-align: center;">Most Common Focus Points</h2>
                                    <div id="chart">
                                        <apexchart type="pie" v-if="pieChart.series.length" height="330" :options="pieChart.chartOptions" :series="pieChart.series"></apexchart>
                                    </div> 
                                    <!-- <v-simple-table v-if="commonFocusPoints.length">
                                        <template v-slot:default>
                                            <tbody style="text-align: center">
                                                <tr
                                                v-for="(point, i) in commonFocusPoints"
                                                :key="point.id" >
                                                    <td>
                                                        <v-chip
                                                            :color="getPointColour(i)"
                                                            outlined
                                                            label >
                                                                {{ point.pointName }}
                                                        </v-chip>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </template>
                                    </v-simple-table> -->

                                    <span class="btn-keyword" v-if="!pieChart.series.length" style="margin-left: 33%;">
                                        None Assigned
                                    </span>

                                </div>                             
                            </div>
                            <div class="row">
                                
                                <div class="col">
                                    <v-card style="text-align: center">
                                        <br/>
                                        <h2 style="text-align: center;">Average Response Time</h2>
                                        
                                            <span class="far fa-clock fa-3x" style="color: #048A1A;"></span>
                                            <span class="xxLargeFont">{{ averageResponseTime=="" ? 'No replies yet' : averageResponseTime }}</span>
                                            <!-- <span v-if="minuteCounter < 10" class="xxLargeFont" style="margin-right: -5px">0</span>
                                            <number
                                            class="xxLargeFont"
                                            ref="minutes"
                                            :from="0"
                                            :to="minuteCounter"
                                            :duration="5"
                                            easing="Power1.easeOut"/>    
                                            <span style="margin-right: -5px; margin-left: -5px" class="xxLargeFont">:</span>
                                            <span v-if="secondCounter < 10" class="xxLargeFont" style="margin-right: -5px">0</span>
                                            <number
                                            class="xxLargeFont"
                                            ref="seconds"
                                            :from="0"
                                            :to="secondCounter"
                                            :duration="5"
                                            easing="Power1.easeOut"/> -->
                                        <br/>

                                        <br/>
                                    </v-card>
                                </div>
                                <!-- <div class="col">
                                    <v-card 
                                    v-if="minuteCounter < 10" 
                                    width="">
                                        <v-card-title>something else</v-card-title>
                                        <span class="far fa-clock" style="color: #048A1A;"></span>
                                        <span style="margin-right: -5px">0</span>
                                        <number
                                        ref="minutes"
                                        :from="0"
                                        :to="5"
                                        :duration="5"
                                        easing="Power1.easeOut"/>    
                                        <span style="margin-right: -5px; margin-left: -5px">:</span>
                                        <number
                                        ref="seconds"
                                        :from="0"
                                        :to="40"
                                        :duration="5"
                                        easing="Power1.easeOut"/>
                                    </v-card>
                                    <v-card v-else>
                                        <number
                                        ref="minutes"
                                        :from="0"
                                        :to="5"
                                        :duration="5"
                                        easing="Power1.easeOut"/>    
                                        :
                                        <number
                                        ref="seconds"
                                        :from="0"
                                        :to="40"
                                        :duration="5"
                                        easing="Power1.easeOut"/>
                                    </v-card>
                                </div> -->
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-4 float-left">
                    <div class="card">
                        <div class="card-header bg-transparent">
                            <div class="row align-items-center">
                                <div class="col">
                                    <h5 class="h3 mb-0">Recent Chats</h5>
                                </div>
                                <div class="col">
                                    <div class="nav nav-pills justify-content-end">
                                        <button type="button" class="btn btn-primary btn-sm" @click="newRedirect('chats', '')" :disabled="pastChatList">View All</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body" v-if="pastChatList">
                            <v-container>
                                <div class="hoverColour" v-for="chat in pastChatList" :key="chat.id">
                                    <v-row 
                                    align="center"
                                    justify="center">
                                        <!-- <v-col cols=2> -->
                                            <v-avatar
                                            
                                            size="48" 
                                            style="z-index: 9999;">
                                                <span class="white--text text-h5" style="background-color: #795548">
                                                    <img :src="getUserAvatar(chat.chatterPerson.profilePicture)">
                                                </span>
                                            </v-avatar>
                                        <!-- </v-col>
                                        <v-col> -->
                                            <div class="past-name">
                                                <strong>{{ chat.chatterPerson.name }}</strong>
                                            </div>
                                        <!-- </v-col>
                                        <v-col> -->
                                            <div class="consultation-type">
                                                <h6 class="text-uppercase text-muted ls-1 mb-1">Focus</h6>
                                                <span class="btn-keyword keyword-1 appointChip" v-if="chat.chatFocus">
                                                    {{ chat.chatFocus.problem }}
                                                </span>

                                                <span class="btn-keyword appointChip" v-if="!chat.chatFocus">
                                                    Not Specified
                                                </span>
                                            </div>
                                        <!-- </v-col>
                                        <v-col cols=2> -->
                                            <v-avatar
                                            size="30" 
                                            color="#FF9565" 
                                            class="intoAvatar"
                                            @click="redirect('#')">
                                                    >
                                            </v-avatar>
                                        <!-- </v-col> -->
                                    </v-row>
                                </div>
                                <!-- <div class="hoverColour">
                                    <v-row 
                                    align="center"
                                    justify="center">
                                         <v-col cols=2> 
                                            <v-avatar
                                            color="green"
                                            size="48"
                                            style="z-index: 9999;">
                                                <span class="white--text text-h5" style="background-color: #4CAF50">
                                                    M
                                                </span>
                                            </v-avatar>
                                         </v-col>
                                        <v-col> 
                                            <div class="past-name">
                                                <strong>Milton</strong>
                                            </div>
                                         </v-col>
                                        <v-col> 
                                            <div class="consultation-type">
                                                <h6 class="text-uppercase text-muted ls-1 mb-1">Focus</h6>
                                                <span class="btn-keyword keyword-2 appointChip">
                                                    Depression
                                                </span>
                                            </div>
                                         </v-col>
                                        <v-col cols=2> 
                                            <v-avatar
                                            size="30" 
                                            color="#FF9565" 
                                            class="intoAvatar"
                                            @click="redirect('#')">
                                                    >
                                            </v-avatar>
                                        < </v-col> 
                                    </v-row>
                                </div> -->
                            </v-container>
                            <!-- <div class="card-body hoverColour">
                                <div class="appointment-card cursorClick">
                                    <v-avatar
                                    color="brown"
                                    size="48" >
                                        <span class="white--text text-h5">
                                            B
                                        </span>
                                    </v-avatar>
                                    <div class="appointDate">
                                        <strong>Brian</strong>
                                    </div>
                                    <span class="grey-line"></span>
                                    <div class="consultation-type">
                                        <h6 class="text-uppercase text-muted ls-1 mb-1">Focus</h6>
                                        <span class="btn-keyword appointChip">
                                            Abuse
                                        </span>
                                    </div>
                                    <span class="grey-line grey-lineSecondary"></span>
                                    <v-avatar color="indigo">
                                        >
                                    </v-avatar>
                                </div>
                            </div> -->
                        </div>
                        <v-card 
                        v-else 
                        class="justify-center mt-2"
                        flat
                        style="text-align: center">
                            <span class="far fa-comment-alt fa-5x" style="color: #FF9565"></span>
                            <v-card-title style="justify-content: center; color: #022F5B">Nothing here yet!</v-card-title>
                            <v-card-subtitle>Check back later to see if anyone has messaged</v-card-subtitle>
                        </v-card>
                    </div>
                    <div class="card">
                        <div id="myCal" style="align-self: center;"></div>
                    </div>
                </div>
            </div>
        </v-app>
	</div>
</template>

<script>
import Calendar from "color-calendar"
import "color-calendar/dist/css/theme-glass.css"
import moment from 'moment'

export default {
    name: "ColorCalendar",
	data() {
		return {
            minuteCounter: 5,
            secondCounter: 5,

            series: 
            [
                {
                    name: 'Number of Chats',
                    data: [0, 0, 0, 0, 0, 0, 0]
                },
            ],

            chartOptions: 
            {
                chart: 
                {
                    type:                 'bar',
                    height:               350,
                    redrawOnParentResize: true,
                },
                colors:['#FF9565'],
                title: {
                    text:    'Number of Chats per Week',
                    align:   'center',
                    offsetX: -10,
                    style:   {
                        fontSize:   '20px',
                        fontWeight: 'bold',
                        fontFamily: 'Segoe UI Semibold',
                        color:      '#022F5B'
                    },
                },
                plotOptions: {
                    bar: 
                    {
                        horizontal: false,
                        columnWidth: '55%',
                    },
                },
                dataLabels: 
                {
                    enabled: false
                },
                stroke: 
                {
                    show: true,
                    width: 2,
                    colors: ['transparent']
                },
                xaxis: {
                    categories: this.getPastWeekDates(new Date()),
                },
                fill: {
                    opacity: 1
                },
                tooltip: 
                {
                    y: 
                    {
                        formatter: function (val) 
                        {
                            return val
                        }
                    }
                }
            },

            pastChatList:        [],
            commonFocusPoints:   [],
            averageResponseTime: '',

            colors: ["#2196F3", "#fb6340", "#11cdef", "#2dce89", "#f5365c"],

            //pie chart options
            pieChart: {
                
                series: [],
                chartOptions: {
                    colors: ["#2196F3", "#fb6340", "#11cdef", "#2dce89", "#f5365c"],
                    chart: {
                        width: 380,
                        type: 'pie',
                    },
                    labels: ['Bullying', 'Anxiety', 'Insecurity', 'Depression', 'OCD'],
                    responsive: [{
                        breakpoint: 480,
                        options: {
                            chart: {
                            width: 300
                            },
                            legend: {
                            position: 'bottom'
                            }
                        }
                    }],
                    dataLabels: {
                        textAnchor: 'middle',
                        style: {
                            fontSize: '12px',
                            fontWeight: 'bold',
                        },
                    },
                    legend: {
                        position: 'bottom'
                    }


                },

               
            }
        }
	},

	async mounted() {
		new Calendar
        ({
            id:                    "#myCal",
            theme:                 "glass",
            primaryColor:          '#FF9565',
            headerBackgroundColor: "#FF9565",
            fontFamilyHeader:      'Segoe UI Semibold',
            fontFamilyWeekdays:    'Segoe UI Semibold',
            fontFamilyBody:        'Segoe UI Semibold',
            dropShadow:            '0',
            disableDayClick:       false,
            startWeekday:          1,
        })

        const user = JSON.parse(window.localStorage.getItem('currentUser'))
        await this.getChatList(user.userID)
        await this.getCommonChatFocus(user.userID)
        await this.getNumCounsellorChatsForWeek(user.userID)
        await this.getAverageResponseTime(user.userID)
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

        theFormat(number) {
            return number.toFixed(2);
        },

        async getChatList(CounsellorID) {
            const requestParam = {
                counsellorID: CounsellorID
            }

            const tempChatList = await this.$store.dispatch('chat/getCounsellorChatList', requestParam)

            if(tempChatList.length > 1){
                this.pastChatList = tempChatList.slice(0, 5)
            }
            else{
                this.pastChatList = tempChatList
            }
        },

        async getCommonChatFocus(CounsellorID) {
            const requestParam = {
                counsellorID: CounsellorID
            }
            
            this.commonFocusPoints = await this.$store.dispatch('reports/getCommonChatFocus', requestParam)

            const arrFocusCounts = this.commonFocusPoints.map((item) => {
                return item.pointTotal
            })

            this.pieChart.series = arrFocusCounts

            const arrFocusNames = this.commonFocusPoints.map((item) => {
                return item.pointName
            })

            this.pieChart.chartOptions = {...this.pieChart.chartOptions, ...{
                labels: arrFocusNames
            }}
        },

        async getNumCounsellorChatsForWeek(counsID){
            const requestParam = {
                counsellorID: counsID
            }

            const resp = await this.$store.dispatch('reports/getNumCounsellorChatsForWeek', requestParam)

            const arrChatCounts = resp.map((pair) => {
                return pair.count
            })

            this.series = [{
                data: arrChatCounts
            }]
        },

        async getAverageResponseTime(userID){
            const requestParam = {
                counsellorID: userID
            }

            const resp = await this.$store.dispatch('reports/getAverageResponseTime', requestParam)

            //format time to something more user friendly
            if(resp !== undefined){
                const date = "2021-09-13"
                const time = resp.hours + ':' + resp.minutes + ':' + resp.seconds

                const newDate = moment(date + ' ' + time).format('HH:mm:ss')

                if(resp.days === 0)
                    this.averageResponseTime = newDate
                else
                    this.averageResponseTime = resp.days + ' days ' + newDate
            }

        },

        getUserAvatar(image) {
			const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)

			return images(`./${image}`)
		},

        getPointColour(index){
            return this.colors[index % 5]
        },

        getPastWeekDates(date){
            const currentDate = moment(date)

            let dates = []

            dates.push(currentDate.format('DD/MM'));
            var startDate = currentDate;

            for (var i = 6; i > 0; i--)
            {
                var nextDate = moment(startDate).subtract(1, 'days'); //subtract 1 day to given date to get next date
                dates.push(nextDate.format('DD/MM'));

                startDate = nextDate;
            }

            return dates.reverse()
        },
    }
}
</script>

<style scoped>

    .xxLargeFont
    {
        font-size: xx-large;
    }

    .numberBox 
    {
        width: 150px;
        display: inline-block;
        padding: 20px 0;
        border-radius: 15px;
        box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.18);
    }

    .intoAvatar
    {
        color: white;
    }

    .hoverColour :hover
    {
        background-color: #eeeeee;
        cursor: pointer;
    }

    /* @media only screen and (max-width: 1400px) {
        .small-past-name {
            font-size: small;
        }

        .consultation-type-small{
            line-height: 50%;
            padding-bottom: 1rem;
            margin-left: 1rem;
            margin-right: 1rem;
        }
    } */

    .past-name {
        padding-left: 10px;
        align-self: center;
        white-space: nowrap
    }
    
    .btn-keyword
    {
        padding: 0.625rem 0.625rem;
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

    .fa-3x {
        font-size: 2.5em;
    }

    .xxLargeFont {
        font-size: xx-large;
    }
</style>
