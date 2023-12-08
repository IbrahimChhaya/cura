<template>
  <div>
    <nav class="navbar navbar-top navbar-expand navbar-dark  border-bottom" 
    :class="{'bg-primary' : displayCurrentPageName, 'rhub-topNav' : !displayCurrentPageName}">
      <div class="container-fluid">
        <div class="collapse navbar-collapse" id="navbarSupportedContent">

          <div class="row align-items-center py-4">
            <div class="col-lg-6 col-7">
            <h6 v-if="displayCurrentPageName" class="h2 d-inline-block mb-0">{{ currentPageName }}</h6>
            </div>
          </div>

              <!-- Navbar links -->
          <ul class="navbar-nav align-items-center  ml-md-auto ">
            <li class="nav-item d-xl-none">
            <!-- Sidenav toggler -->
            <div class="pr-3 sidenav-toggler sidenav-toggler-dark" data-action="sidenav-pin" data-target="#sidenav-main">
                <div class="sidenav-toggler-inner">
                <i class="sidenav-toggler-line"></i>
                <i class="sidenav-toggler-line"></i>
                <i class="sidenav-toggler-line"></i>
                </div>
            </div>
            </li>
            <li class="nav-item d-sm-none">
            <a class="nav-link" href="#" data-action="search-show" data-target="#navbar-search-main">
                <i class="ni ni-zoom-split-in"></i>
            </a>
            </li>
            <!-- <li class="nav-item dropdown">
                <a class="nav-link" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="ni ni-bell-55" :class="{'bell': !displayCurrentPageName}"></i>
                <span class="bell-badge" :class="{'badge-bell' : !displayCurrentPageName}">3</span>
                </a>
                <div class="dropdown-menu dropdown-menu-xl  dropdown-menu-right  py-0 overflow-hidden">
                    Dropdown header
                    <div class="px-3 py-3">
                    <h6 class="text-sm text-muted m-0">You have <strong class="text-primary">13</strong> notifications.</h6>
                    </div>
                     List group 
                    <div class="list-group list-group-flush">
                    <a href="#!" class="list-group-item list-group-item-action">
                        <div class="row align-items-center">
                        <div class="col-auto">
                             Avatar 
                        <img src="@/assets/img/guardian.jpg" class="avatar rounded-circle">
                        </div>
                        <div class="col ml--2">
                            <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h4 class="mb-0 text-sm">Sue San</h4>
                            </div>
                            <div class="text-right text-muted">
                                <small>2 hrs ago</small>
                            </div>
                            </div>
                            <p class="text-sm mb-0">Let's meet at Starbucks at 11:30. Wdyt?</p>
                        </div>
                        </div>
                    </a>
                    <a href="#!" class="list-group-item list-group-item-action">
                        <div class="row align-items-center">
                        <div class="col-auto">
                             Avatar 
                        <img src="@/assets/img/guardian.jpg" class="avatar rounded-circle">
                        </div>
                        <div class="col ml--2">
                            <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h4 class="mb-0 text-sm">Sue San</h4>
                            </div>
                            <div class="text-right text-muted">
                                <small>3 hrs ago</small>
                            </div>
                            </div>
                            <p class="text-sm mb-0">A new issue has been reported for Argon.</p>
                        </div>
                        </div>
                    </a>
                    <a href="#!" class="list-group-item list-group-item-action">
                        <div class="row align-items-center">
                        <div class="col-auto">
                             Avatar 
                        <img src="@/assets/img/guardian.jpg" class="avatar rounded-circle">
                        </div>
                        <div class="col ml--2">
                            <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h4 class="mb-0 text-sm">Sue San</h4>
                            </div>
                            <div class="text-right text-muted">
                                <small>5 hrs ago</small>
                            </div>
                            </div>
                            <p class="text-sm mb-0">Your posts have been liked a lot.</p>
                        </div>
                        </div>
                    </a>
                    <a href="#!" class="list-group-item list-group-item-action">
                        <div class="row align-items-center">
                        <div class="col-auto">
                             Avatar 
                            <img alt="Image placeholder" src="@/assets/img/guardian.jpg" class="avatar rounded-circle">
                        </div>
                        <div class="col ml--2">
                            <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h4 class="mb-0 text-sm">Sue San</h4>
                            </div>
                            <div class="text-right text-muted">
                                <small>2 hrs ago</small>
                            </div>
                            </div>
                            <p class="text-sm mb-0">Let's meet at Starbucks at 11:30. Wdyt?</p>
                        </div>
                        </div>
                    </a>
                    <a href="#!" class="list-group-item list-group-item-action">
                        <div class="row align-items-center">
                        <div class="col-auto">
                             Avatar 
                        <img src="@/assets/img/guardian.jpg" class="avatar rounded-circle">
                        </div>
                        <div class="col ml--2">
                            <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h4 class="mb-0 text-sm">Sue San</h4>
                            </div>
                            <div class="text-right text-muted">
                                <small>3 hrs ago</small>
                            </div>
                            </div>
                            <p class="text-sm mb-0">A new issue has been reported for Argon.</p>
                        </div>
                        </div>
                    </a>
                    </div>
                     View all 
                    <a href="#!" class="dropdown-item text-center text-primary font-weight-bold py-3">View all</a>
                </div>
                </li> -->
          </ul>

          <ul class="navbar-nav align-items-center  ml-auto ml-md-0 " id="vstep8">
            <li :class="{'show': userDropdown, '': !userDropdown}" class="nav-item dropdown">

              <a @click="toggleDropdown" class="nav-link pr-0" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <div class="media align-items-center">
                  <span class="avatar avatar-sm rounded-circle">
                      <img :src="getUserAvatar()" class="avatar-image">
                  </span>
                  <div class="media-body  ml-2  d-none d-lg-block">
                      <span class="mb-0 text-sm  font-weight-bold" :class="{'rhub-nameText': !displayCurrentPageName}">{{loggedIn.name}}</span>
                  </div>
                </div>
              </a>

              <div :class="{'show': userDropdown, '': !userDropdown}" class="dropdown-menu dropdown-menu-right">
                  <div class="dropdown-header noti-title">
                  <h6 class="text-overflow m-0">Welcome!</h6>
                  </div>
                  <router-link :to="{name: 'Account'}" class="dropdown-item" @click.native="userDropdown = false">
                    <i class="ni ni-single-02"></i>
                    My Profile
                  </router-link>
                    <!-- <a href="#!" class="dropdown-item">
                    <i class="ni ni-single-02"></i>
                    <span>My profile</span>
                    </a> -->
                  <router-link :to="{name: 'Practice'}" class="dropdown-item" @click.native="userDropdown = false">
                    <i class="ni ni-single-02"></i>
                    Practice Details
                  </router-link>
                  <a href="#!" class="dropdown-item" hidden>
                  <i class="ni ni-settings-gear-65"></i>
                  <span>Settings</span>
                  </a>
                  <a href="#!" class="dropdown-item" hidden>
                  <i class="ni ni-calendar-grid-58"></i>
                  <span>Activity</span>
                  </a>
                  <a href="#!" class="dropdown-item" hidden>
                  <i class="ni ni-support-16"></i>
                  <span>Support</span>
                  </a>
                  <div class="dropdown-divider"></div>
                  <a href="#!" class="dropdown-item" @click="logout()">
                  <i class="ni ni-user-run"></i>
                  <span>Logout</span>
                  </a>
              </div>
              </li>
          </ul>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
  export default {
    computed: {
      loggedIn() {
        return this.$store.getters['auth/loggedInUser']
      },

      currentPageName() {
        return this.$route.name
      },

      displayCurrentPageName() {
        return this.currentPageName !== "Resource Hub"
      }
    },

    data() {
      return {
        userDropdown: false,
      }
    },

    methods: {
      getUserAvatar() {
        if(this.loggedIn.profilePicture == null) return ''

        const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)

        return images(`./${this.loggedIn.profilePicture}`)
      },

      toggleDropdown() {
        this.userDropdown = !this.userDropdown
      },

      logout() {
        localStorage.removeItem('currentUser')

        this.$store.commit('auth/updateLoggedInUser', {})
        this.$router.push({ name: 'Login' })
      }
    },

  }
</script>

<style scoped>

.rhub-topNav {
  padding-bottom: 0px;
  background-color: #FF9565 !important;
}

.bell{
  color: white;
}

.badge-bell{
  background-color: white;
  color: #FF9565;
  font-weight: bold;
}

.rhub-nameText{
  color: white !important;
}

.avatar-image{
  height: inherit;
  width: inherit;
}

</style>
