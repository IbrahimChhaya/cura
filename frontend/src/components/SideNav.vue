<template>
    <div>
        <nav class="sidenav navbar navbar-vertical  fixed-left  navbar-expand-xs navbar-light bg-white" id="sidenav-main">
    <div class="scrollbar-inner">
      <!-- Brand -->
      <div class="sidenav-header  align-items-center">
        <a class="navbar-brand" href="javascript:void(0)">
          <img src="@/assets/img/logo.png" class="navbar-brand-img" alt="..." style="max-width: 65%">
        </a>
      </div>
      <div class="navbar-inner">
        <!-- Collapse -->
        <div class="collapse navbar-collapse" id="sidenav-collapse-main">
          <!-- Nav items -->
          <ul class="navbar-nav">

            <!--<li class="nav-item active-sidebar">
              <a class="nav-link active" href="home.aspx">
                :class="$route.name == 'home' ? 'nav-link active' : 'nav-link'">-->
            <li class="nav-item active">
              <router-link 
              :to="isCounsellor() ? '/counsellor' :  isAdmin() ? '/admin' : '/home'"
              :class="$route.name == 'Home' || $route.name == 'Admin Home' ? 'nav-link navActive' : 'nav-link'">
                <i class="fas fa-home" style="color: red"></i>
                <span class="nav-link-text">Home</span>
              </router-link>
            </li>
            <li class="nav-item" v-if="!isAdmin()">
              <router-link 
            :class="$route.name == 'Chats' ? 'nav-link navActive' : 'nav-link'"              
            :to="isCounsellor() ? '/chats' : '/PsychologistChat'">
                <i class="far fa-comment-alt" style="color: purple"></i>
                <span class="nav-link-text">Chats</span>
              </router-link>
            </li>
            <li class="nav-item" v-if="isAdmin()">
              <router-link 
            :class="$route.name == 'Account Approvals' ? 'nav-link navActive' : 'nav-link'"              
            :to="'/approvals'">
                <i class="far fa-check-circle" style="color: #4CAF50"></i>
                <span class="nav-link-text">Account Approvals</span>
              </router-link>
            </li>
            <li class="nav-item" v-if="!isCounsellor() && !isAdmin()" :hidden="hideBookings">
              <router-link 
            :class="$route.name == 'Reports' ? 'nav-link navActive' : 'nav-link'"              
            :to="'/reports'">
                <i class="fas fa-chart-pie calendar-padding" style="color: #BE4BDB"></i>
                <span class="nav-link-text">Reports</span>
              </router-link>
            </li>
            <li class="nav-item" v-if="!isCounsellor() && !isAdmin()">
              <router-link 
            :class="$route.name == 'Calendar' ? 'nav-link navActive' : 'nav-link'"              
            :to="'/calendar'">
                <i class="far fa-calendar calendar-padding"></i>
                <span class="nav-link-text">Calendar</span>
              </router-link>
            </li>
            <li class="nav-item" v-if="!isCounsellor() && !isAdmin()">
              <router-link 
                :class="$route.name == 'Appointments' ? 'nav-link navActive' : 'nav-link'"              
                :to="'/appointments'">
                <i class="far fa-calendar-alt calendar-padding" style="color: blue"></i>
                <span class="nav-link-text">Appointments</span>
              </router-link>
            </li>
            <li class="nav-item" v-if="!isCounsellor() && !isAdmin()">
              <router-link 
                :class="$route.name == 'Patients' ? 'nav-link navActive' : 'nav-link'"               
                :to="'/patients'">
                <i class="fas fa-users" style="color: #F68D03"></i>
                <span class="nav-link-text">Patients</span>
              </router-link>
            </li>
            <li class="nav-item" v-if="isAdmin()">
              <router-link 
                :class="$route.name == 'Psychologists' ? 'nav-link navActive' : 'nav-link'"               
                :to="'/psychologists'">
                <i class="fas fa-user-md" style="color: #F68D03"></i>
                <span class="nav-link-text">Psychologists</span>
              </router-link>
            </li>
            <li class="nav-item" v-if="isAdmin()">
              <router-link 
                :class="$route.name == 'Counsellors' ? 'nav-link navActive' : 'nav-link'"               
                :to="'/counsellors'">
                <i class="fas fa-headset" style="color: #C56EFF"></i>
                <span class="nav-link-text">Counsellors</span>
              </router-link>
            </li>
            <li class="nav-item" v-if="!isCounsellor()">
              <router-link 
                :class="$route.name == 'Tests' ? 'nav-link navActive' : 'nav-link'"               
                :to="'/tests'">
                <i class="fas fa-clipboard-check calendar-padding" style="color: #C5364F"></i>
                <span class="nav-link-text">Tests</span>
              </router-link>
            </li>
            <li class="nav-item">
              <router-link
                :class="$route.name == 'Resource Hub' ? 'nav-link navActive' : 'nav-link'"              
                :to="'/resourcehub'">
                <i class="text-orange">
                    <img src="@/assets/img/resourceHub.svg" style="width: 1rem; height: 1.2rem;">
                </i>
                <span class="nav-link-text">Resource Hub</span>
              </router-link>
            </li>
            <li class="nav-item" hidden>
              <a class="nav-link">
                <i class="far fa-question-circle" style="color: cyan"></i>
                <span class="nav-link-text">Help</span>
              </a>
            </li>
               <li class="nav-item" hidden>
              <a class="nav-link">
                <i class="fas fa-cog"></i>
                <span class="nav-link-text">Settings</span>
              </a>
            </li>
          </ul>

              <!-- Heading -->

            <!--<h6 class="navbar-heading p-0 text-muted">
            <span class="docs-normal">Documentation</span>
          </h6>-->
          <!-- Navigation -->
          <!--<ul class="navbar-nav mb-md-3">
            <li class="nav-item">
              <a class="nav-link" href="https://demos.creative-tim.com/argon-dashboard/docs/getting-started/overview.html" target="_blank">
                <i class="ni ni-spaceship"></i>
                <span class="nav-link-text">Getting started</span>
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="https://demos.creative-tim.com/argon-dashboard/docs/foundation/colors.html" target="_blank">
                <i class="ni ni-palette"></i>
                <span class="nav-link-text">Foundation</span>
              </a>
            </li>
          </ul>-->
        </div>
      </div>
    </div>
  </nav>
    </div>
</template>

<script>
export default {
    data: () => ({
        hideBookings: '',
    }),

    async mounted() {
        var user = JSON.parse(localStorage.getItem('currentUser'))
        if(user.userType == "Psychologist")
            await this.checkAnyBookings()
    },

    methods:
    {
      isCounsellor()
      {
        var user = JSON.parse(localStorage.getItem('currentUser'))
        return (user.userType === "Counsellor")
      },
      isAdmin()
      {
        var user = JSON.parse(localStorage.getItem('currentUser'))
        return (user.userType === "Admin")
      },

        async checkAnyBookings()
        {
            var user = JSON.parse(localStorage.getItem('currentUser'))
            this.hideBookings = !(await this.$store.dispatch('bookings/checkAnyBookings', user.userID))
        },
    },
}
</script>

<style scoped>
    .navActive
    {
        background: #FBF5F2;
    }
</style>