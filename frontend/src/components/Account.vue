<template>
  <!-- <div class="container-fluid mt-6"> -->
    <div class="row">
      <div class="col-xl-7">
        <div class="card">
        <h3 class="card-header">Account Details</h3>
        <div class="card-body">
        <div>
          <h4 class="account-h4">Name</h4>
          <input class="login-input" type="text" :placeholder="name" id="Name" v-model="name">
        </div>
        <div>
          <h4 class="account-h4">Email</h4>
          <input class="login-input" type="Email" :placeholder="email" id="Email1" v-model="email">
        </div>
        <div v-if="images">
          <h4 class="account-h4">Profile Picture</h4>
          <!-- <input class="login-input" type="text" :placeholder="email" id="profilePicture" v-model="profilePicture"> -->
          <vue-horizontal responsive style="height: 8rem;">
            <div v-for="image in images" :key="image.id">
              <img
              :class="{'active': profilePicture === image}"
              class="image avatar avatar-sm rounded-circle" 
              :src="getImages(image)" @click="selectImage(image)">
            </div>
          </vue-horizontal>
        </div>
        <div>
          <h4 class="account-h4">Date of Birth</h4>
          <VueDatePicker
            class="login-input"
            v-model="dob"
            placeholder="Select your birth date"
            no-header color="#FF9565"
            ref="menu"
            :max-date="maxDate"
            min-date="1971-01-01"
            @onOpen="menu = true"
            @onClose="menu = false"
          />
        </div>
        <div>
          <h4 class="account-h4">Password</h4>
          <input class="login-input" type="password" required placeholder="Password" id="password" v-model="password">
        </div>
        <p class="text-danger mb-0" v-show="incorrectPassword">Password is incorrect</p>
        <button ID="Submit" class="btn no-shadow account-button" @click="updateUserDetails" :disabled="!detailsFilled">Update Account</button>
      </div>

      </div>
      </div>

      <div class="col-xl-5">
        <div class="card">
          <h3 class="card-header">Update Password</h3>
          <div class="card-body">
          <div>
            <h4 class="account-h4">Current Password</h4>
            <input class="login-input" type="Password" required placeholder="Current Password" v-model="currentPassword">
          </div>
          <div>
            <h4 class="account-h4">New Password</h4>
            <input class="login-input" type="Password" required placeholder="New Password" v-model="newPassword">
          </div>
          <div>
            <h4 class="account-h4">Confirm New Password</h4>
            <input class="login-input" type="Password" required placeholder="Confirm New Password" v-model="confirmPassword">
          </div>
          <p class="text-danger mb-0" v-show="incorrectPassword">Password is incorrect</p>
          <p class="text-danger mb-0" v-show="!passwordsMatch">New Password and confirmation passwords must match</p>
          <button class="btn no-shadow account-button" :disabled="!passwordsFilled || !passwordsMatch" @click="changePassword">Update Password</button>
        </div>
        </div>

        <div class="row">
          <div class="col-xl-12">
            <div class="card">
              <h3 class="card-header" style="padding-bottom: 4px; border: none">Remove Account</h3>
              <div class="card-body" style="padding-top: 14px">
                <b-button v-b-modal.remove-account class="btn no-shadow account-button delete-btn">Delete my account</b-button>

                <b-modal id="remove-account" title="Delete My Account" centered :ok-disabled="!accDeleteEmail" @ok="handleOk">
                  <h4 class="account-h4">Enter your email to confirm account deletion</h4>

                  <input class="login-input" type="Email" placeholder="Email" required v-model="accDeleteEmail">
                  <!--  <input class="login-input" type="password" required placeholder="Password" v-model="accDeletePassword"> -->
                  <p class="text-danger mb-0" v-show="deleteEmailIncorrect">Email is incorrect</p>
                </b-modal>
              </div>
            </div>
          </div>
        </div>


      </div>

      <v-snackbar
        v-model="snackbar"
        :timeout="timeout">
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

    </div>

    


  <!-- </div> -->
</template>

<script>
import moment from 'moment'

export default {
  computed: {
    loggedIn() {
      return JSON.parse(window.localStorage.getItem('currentUser'))
    },

    detailsFilled() {
      return (!!this.name && !!this.email && !!this.profilePicture && !!this.dob && !!this.password)
    },

    passwordsFilled() {
      return (!!this.currentPassword && !!this.newPassword && !!this.confirmPassword)
    },

    passwordsMatch() {
      return (this.newPassword === this.confirmPassword)
    },

    maxDate() {
      return moment().subtract(25, 'years').format('YYYY-MM-D')
    },
  },

  data() {
    return {
      name:              '',
      email:             '',
      dob:               null,
      profilePicture:    '',
      password:          '',
      incorrectPassword: false,
      currentPassword:   '',
      newPassword:       '',
      confirmPassword:   '',

      accDeleteEmail:       '',
      deleteEmailIncorrect: false,

      menu: false,

      images:       ["adult1.png", "adult2.png", "adult3.png",
			 "adult4.png", "adult5.png", "adult6.png",
			 "adult7.png", "adult8.png", "adult9.png"],

      snackbar:     false,
      snackbarText: 'Successfully updated',
      timeout:      5000,
    }
  },

  watch: {
    menu (val) {
      val && setTimeout(() => (this.$refs.menu.$refs.agenda.mode = 'year'));
    },
  },

  methods: {
    async updateUserDetails() {
      const requestParams = {
        userID:            this.loggedIn.userID,
        oldPassword:       this.password,
        newName:           this.name,
        newEmail:          this.email,
        newDOB:            this.dob,
        newProfilePicture: this.profilePicture,
      }

      const resp = await this.$store.dispatch('auth/updateAccount', requestParams)

      if(resp.userID > 0){
        resp.isLoggedIn = true
        localStorage.setItem('currentUser', JSON.stringify(resp))
        this.$store.commit('auth/updateLoggedInUser', resp)

        this.snackbarText = 'Account details successfully updated'
        this.snackbar     = true

      }
      else if(resp.userID === -2) {
        this.incorrectPassword = true
      }
      else {
        window.alert("An unexpected error occurred. Please try again")
      }

      this.password = ''
    },

    async changePassword(){
      const requestParams = {
        userID:      this.loggedIn.userID,
        oldPassword: this.currentPassword,
        newPassword: this.newPassword
      }

      const resp = await this.$store.dispatch('auth/changePassword', requestParams)

      if(resp.userID > 0){
        this.snackbarText = 'Password successfully changed'
        this.snackbar     = true

        this.$router.push({ name: 'Home' })

      }
      else if(resp.userID === -2) {
        this.incorrectPassword = true
      }
      else {
        window.alert("An unexpected error occurred. Please try again")
      }

    },

    handleOk(bvModalEvt) {
      // Prevent modal from closing
      bvModalEvt.preventDefault()

      this.removeAccount()
    },

    async removeAccount() {

      const requestParams = {
        UserID: this.loggedIn.userID,
        Email:  this.accDeleteEmail,
      }

      const resp = await this.$store.dispatch('auth/removeAccount', requestParams)

      if(resp === 0) { //account was successfully deleted
        //clear local storage
        localStorage.removeItem('currentUser')

        // Hide the modal manually
        this.$nextTick(() => {
          this.$bvModal.hide('remove-account')
        })

        //redirect to login
        this.$router.push({name: 'Login'})

      }
      else if(resp === -2) {  //incorrect details provided
        this.deleteEmailIncorrect = true
      }
      else{
        // Hide the modal manually
        this.$nextTick(() => {
          this.$bvModal.hide('remove-account')
        })

        window.alert(resp)

      }
    },

    selectImage(image) {
      this.profilePicture = image
    },

    getImages(image) {
			const storedImages = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)
      return storedImages(`./${image}`)
		}
  },

  mounted() {
    this.name           = this.loggedIn.name
    this.email          = this.loggedIn.email
    this.dob            = this.loggedIn.dob
    this.profilePicture = this.loggedIn.profilePicture
  },
}
</script>

<style scoped>

.login-input {
  background-color: #FBF5F2;
  border: none;
  border-radius: .375rem;
  padding: 12px 15px;
  margin-bottom: 8px;
  margin-top: 2px;
  width: 100%;
}
.account-h4 {
  font-family: inherit;
  font-weight: 600;
  line-height: 1.5;
  color: #32325d;
  margin-top: .5rem;
  margin-bottom: 0px;
}

.account-button {
    border-radius: .375rem;
    border: 1px solid #FF9565;
    background-color: #FF9565;
    color: #FFFFFF;
    font-size: 15px;
    font-weight: bolder;
    padding: 12px 45px;
    letter-spacing: 1px;
    text-transform: uppercase;
    transition: transform 80ms ease-in;
    width: 100%;
    margin-top: 10px;
}

    .account-button:active {
        transform: scale(0.95);
    }

    .account-button:focus {
        outline: none;
    }

    .account-button.ghost {
        background-color: transparent;
        border-color: #FFFFFF;
    }

.no-shadow {
    box-shadow: none;
}

.delete-btn {
    border: 1px solid red;
    background-color: red;
}

.datepicker
  .table-condensed {
  width: 100%;
  margin-bottom: 8px;
  /* height:50px; */
}

.image {
  margin-top: 16px !important;
}

.avatar-sm {
  /* background-position: center !important; */
  width: 6rem;
  height: 6rem;
}

.active {
  background-size: 200% 200%;
  background-color: #FF9565;
  border: 4px solid transparent;
}
</style>

