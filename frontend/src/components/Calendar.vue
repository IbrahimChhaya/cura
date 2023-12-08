<template>
    <div class="calendar-page card">
        <div class="card-body" id="vstep1">
            <FullCalendar :options="calendarOptions"/>
        </div>
        <v-tour name="myTour" :steps="steps" :callbacks="myCallbacks"></v-tour>
    </div>
</template>

<script>
import '@fullcalendar/core/vdom' 
import FullCalendar  from '@fullcalendar/vue'
import dayGridPlugin from '@fullcalendar/daygrid'

export default {
	components: {
		FullCalendar
	},

	data() {
		return {

            steps: 
            [
                {
                    target: '#vstep1',  // We're using document.querySelector() under the hood
                    content: `The calendar allows you to keep track of past and future appointments`,
                    params: {
                        placement: 'middle',
                        enableScrolling: false,
                    }
                },
            ],

            myCallbacks: {
                onFinish: this.endTour,
            },

			calendarOptions: {
				plugins:         [ dayGridPlugin],
				initialView:     'dayGridMonth',
				eventColor:      '#FF9565',
				eventTimeFormat: {
					hour:     '2-digit',
					minute:   '2-digit',
					meridiem: 'short',
					hour12:   false
				},
				customButtons: {
					myCustomButton: {
                        text: 'Edit Hours',
						//icon: 'fa temp fontAwesome fas fa-cog',
						click: function() {
							window.location.href = "/#/editcalendar";
						}
					}
				},
				headerToolbar: {
					right: 'today prev,next myCustomButton'
				},
			},
			calendarEvents: null
		}
	},

    mounted()
    {
        this.getPsychEvents()
        var tourBool = this.$route.query.tour
        if(tourBool)
            this.$tours['myTour'].start()
    },

	methods: {
        endTour()
        {
            this.redirect('/#/editcalendar?tour=true')
        },

        redirect: function(link) {
            window.location.href = link;
        },

		async getPsychEvents() {
			const psych = JSON.parse(window.localStorage.getItem('currentUser'))
			this.calendarEvents = await this.$store.dispatch('bookings/getPsychCalendar', psych.userID)

			const formattedCalendarPayload = this.calendarEvents.map((event) => {
				return {
					cancelled: event.cancelled,
					title:     `${event.patientName}-${event.type}`,
					date:      event.dayTime,
					color:     (event.cancelled === 'Cancelled') ? "#f5365c" : (event.type === 'Repeat') ? '#298FCA':'#FF9565',
					textColor: '#fff',
					display:   'block',
				}
			})
	
			this.calendarOptions = {
				...this.calendarOptions,
				events: formattedCalendarPayload
			}
		},
	},

};

</script>

<style>

    .temp
    {
        width: fit-content;
    }

    .temp:before
    {
        content: "\F013 Edit Hours";
        font-size: 1rem;
    }

	.fontAwesome
	{
		font-family: "Font Awesome 5 Free" !important;
		padding-top: 15%;
	}

	.fontAwesome:hover
	{
		color: white;
	}

   .calendar-page .external-event { /* try to mimick the look of a real event */
		margin: 10px 0;
		padding: 2px 4px;
		background: #3366CC;
		color: #fff;
		font-size: .85em;
		cursor: pointer;
	}

	.calendar-page #wrap {
		width: 1100px; 
		margin: 0 auto;
	}

	.calendar-page #external-events {
		float: left;
		width: 150px;
		padding: 0 10px;
		text-align: left;
	}

	.calendar-page #external-events h4 {
		font-size: 16px;
		margin-top: 0;
		padding-top: 1em;
	}

	.calendar-page #external-events p {
		margin: 1.5em 0;
		font-size: 11px;
		color: #666;
	}

	.calendar-page #external-events p input {
		margin: 0;
		vertical-align: middle;
	}

	.calendar-page #calendar {
		/* 		float: right; */
		margin: 0 auto;
		width: 900px;
		background-color: #FFFFFF;
		border-radius: 6px;
		box-shadow: 0 1px 2px #C3C3C3;
	}

	.fc .fc-button-primary {
		color: white !important;
		background-color: #FF9565 !important;
		border-color: #FF9565 !important;
	}

</style>
