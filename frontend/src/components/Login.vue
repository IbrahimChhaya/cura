<template>
  <div class="login-body">

    <div v-if="accountInactive">
      <b-modal 
      id="reactivate-account" 
      title="Reactivate My Account" 
      centered 
      :ok-disabled="!accActivateEmail || !accActivatePassword"
      @ok="handleOk">
        <h4 class="account-h4">Enter your email and password to confirm account reactivation</h4>
        <input class="login-input" type="Email" placeholder="Email" required v-model="accActivateEmail">
        <input class="login-input" type="password" required placeholder="Password" v-model="accActivatePassword">
        <p class="text-danger mb-0" v-show="incorrectReactivateDetails">Email or Password is incorrect</p>
      </b-modal>
    </div>

    <div class="login-container" id="container">
      <form>
        <div class="form-container sign-up-container">

          <!-- Role selection -->
          <div v-show="!roleAssigned" class="login-form">
            <h1 class="login-h1">Sign up as: </h1>
            <button
              @click="assignRole('Counsellor')"
              class="btn no-shadow login-form__sign-up-button"
              id="btnCounsellorRole"
            >Counsellor</button>

            <button
              style= "margin-right: .5rem;"
              @click="assignRole('Psychologist')"
              class="btn no-shadow login-form__sign-up-button"
              id="btnPsychologistRole"
            >Psychologist</button>
          </div>
          <!-- Sign up - Personal details -->
          <div v-show="RegPageOne && !RegPageTwo" class="login-form" id="regForm">
            <h1 class="login-h1">Create Account</h1>
            <p style="margin-bottom: 0.5rem;">Personal Details</p>

             <!-- <div style="width: 115%; height: 6rem">
                <v-slide-group
                v-model="model"
                mandatory
                show-arrows="desktop"
                max="1"
                center-active
                style="height: 100%">
                  <v-slide-item
                    v-for="image in images"
                    :key="image.id"
                    v-slot="{ active, toggle }"
                    style="height: 100%">
                    <v-btn
                      class="mx-2 v-btn--rounded"
                      :input-value="active"
                      depressed
                      @click="toggle"
                      style="width: 4rem; height:4rem;"
                      v-ripple="false">
                      <img
                        :class="{'active': profilePicture === image}"
                        class="image avatar avatar-sm rounded-circle" 
                        :src="getImages(image)" @click="selectImage(image)">
                    </v-btn>
                  </v-slide-item>
                </v-slide-group>
            </div> -->

            <div style="width: 100%;" v-if="images">
              <vue-horizontal class="image-picker">
                <div v-for="image in images" :key="image.id">
                  <img
                  :class="{'active': profilePicture === image}"
                  class="image avatar avatar-sm rounded-circle" 
                  :src="getImages(image)" @click="selectImage(image)">
                </div>
              </vue-horizontal>
            </div>
            <input
              v-model="name"
              class="login-input"
              type="text"
              placeholder="Name"
              required
            />
            <input
            v-model="email"
              class="login-input"
              type="email"
              placeholder="Email"
              required
            />
            <!-- <h5 class="h5">Choose your avatar</h5>
            <div class="image-container" v-if="images">
              <vue-horizontal class="image-picker">
                <div v-for="image in images" :key="image.id">
                  <img
                  :class="{'active': profilePicture === image}"
                  class="image avatar avatar-sm rounded-circle" 
                  :src="getImages(image)" @click="selectImage(image)">
                </div>
              </vue-horizontal>
            </div> -->
           
						<div class="datepicker">
              <VueDatePicker
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
            <input
              v-model="password"
              class="login-input"
              type="password"
              placeholder="Password"
              id="regpass"
              required
            />
            <input
              v-model="passwordConfirm"
              class="login-input"
              type="password"
              placeholder="Confirm Password"
              id="confirmpass"
              required
            />
            <p class="text-danger mb-0" v-show="userExists">A user with this email address already exists</p>
            <p class="text-danger mb-0" v-show="passwordMismatch">Password and password confirmation do not match</p>
            <div class="button-group">
              <button
                @click="signUpBack"
                class="btn no-shadow"
                id="btnSignUpBack"
                style="color: white;background-color: #ff9565;"
              >Back</button>
            <button
              @click="nextRegistrationForm2"
              class="btn no-shadow login-form__sign-in-button"
              id="btnSignUp"
              :disabled="!registerFilled || passwordMismatch"
            >Next</button>
            </div>
          </div>

          <!-- Psychologist Practice details -->
          <div v-show="RegPageTwo && roleChosen == 'Psychologist'" class="login-form">
            <h1 class="login-h1">Create Account</h1>
            <p>Qualification Details</p>
            <input
              v-model="psychAddress"
              class="login-input"
              type="text"
              placeholder="Practice Address"
              id="PracAddress"
              required
            />
            <input
              v-model="psychQualification"
              class="login-input"
              type="text"
              placeholder="Qualification"
              id="PracQualification"
              required
            />
            <input
              v-model="psychRegNum"
              class="login-input"
              type="text"
              placeholder="Practice Number"
              id="regNumber"
              required
            />
            <input
              v-model="psychDescription"
              class="login-input"
              type="text"
              placeholder="Description"
              id="description"
              required
            />
            <!-- <input
              v-model="psychSpeciality"
              class="login-input"
              type="text"
              placeholder="Speciality"
              id="speciality"
              required
            /> -->
            <div class="login-select">
              <b-form-select class="scope-select" v-model="psychSpeciality" :options="ScopeOfPracticeOptions"></b-form-select>
            </div>
            <p class="text-danger mb-0" v-show="userExists">A user with this email address already exists</p>
            <div class="button-group">
              <button
              @click="nextRegistrationForm1"
              class="btn no-shadow back-button "
              id="btnSignUpBack"
              :disabled="!registerFilled && !passwordMismatch"
            >Back</button>
            <button
              @click="registerPsychologist()"
              class="btn no-shadow login-form__sign-in-button"
              id="btnSignUp"
              :disabled="!psychPracticeFilled && !passwordMismatch"
            >Sign Up</button>
            </div>
          </div>

          <!-- Counsellor Practice Details-->
          <div v-show="RegPageTwo && roleChosen == 'Counsellor'" class="login-form">
            <h1 class="login-h1">Create Account</h1>
            <p>Qualification Details</p>
            <input
              v-model="counsellorPracNum"
              class="login-input"
              type="text"
              placeholder="Practice Number"
              id="CounsellorPracNumber"
              required
            />
            <input
              v-model="counsellorHighestCert"
              class="login-input"
              type="text"
              placeholder="Highest Certificate"
              id="CounsellorCertificate"
              required
            />
            <p class="text-danger mb-0" v-show="userExists">A user with this email address already exists</p>
            <p class="text-danger mb-0" v-if="passwordMismatch && RegPageOne">Password and password confirmation do not match</p>
            <div class="button-group">
              <button
              @click="nextRegistrationForm1"
              class="btn no-shadow back-button "
              id="btnSignUpBack"
              :disabled="!registerFilled && !passwordMismatch"
            >Back</button>
            <button
              @click="registerCounsellor()"
              class="btn no-shadow login-form__sign-in-button"
              id="btnSignUp"
              :disabled="!counsPracticeFilled"
            >Sign Up</button>
            </div>
          </div>
        </div>

        <div class="form-container sign-in-container">
          <div class="login-form">
            <h1 class="login-h1">Sign in</h1>
            <input
              v-model="email"
              class="login-input"
              type="email"
              placeholder="Email"
              id="email"
              required
            />
            <input
              v-model="password"
              class="login-input"
              type="password"
              placeholder="Password"
              id="password"
              required
            />
            <p class="text-danger mb-0" v-show="incorrectDetails" >Email or Password is incorrect</p>
            <a class="login-a" href="#">Forgot your password?</a>
            <button id="sign-in-button" @click="login()" :disabled="!loginFilled" class="btn no-shadow login-form__sign-in-button">Sign In</button>

            <div v-if="accountPending" style="margin-top: 25px;">
              <b-alert v-model="accountPending" variant="primary" dismissible>
                Your account is in the process of being approved. Please try again later
              </b-alert>
            </div>
         
          </div>
        </div>
      </form>
      <div class="overlay-container">
        <div class="overlay">
          <div class="overlay-panel overlay-left">
            <img src="@/assets/img/webLogo.png" />
            <h1 class="login-h1" style="color: #fff">Welcome Back!</h1>
            <p class="login-p">
              To keep connected with us please login with your personal info
            </p>
            <button @click="toggleActiveForm('remove')" class="login-btn btn btn-primary" id="signIn">
              Sign In
            </button>
          </div>
          <div class="overlay-panel overlay-right">
            <img src="@/assets/img/webLogo.png" />
            <h1 class="login-h1" style="color: #fff">Don't have an account?</h1>
            <p class="login-p">
              Enter your personal details and start your journey with us
            </p>
            <button @click="toggleActiveForm('add')" class="login-btn btn btn-primary" id="signUp">
              Sign Up
            </button>
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
</template>

<script>
import moment from 'moment'
export default {

  data() {
    return {
      email:               '',
      password:            '',
      name:                '',
      dob:                 null,
      profilePicture:      '',
      passwordConfirm:     '',
      incorrectDetails:    false,
      userExists:          false,
      accountPending:      false,
      accountInactive:     false,
      accActivateEmail:    '',
      accActivatePassword: '',
      incorrectReactivateDetails: false,

      RegPageOne:       false,
      RegPageTwo:       false,
      roleChosen:       '',
      roleAssigned:     false,

      counsellorPracNum:     '',
      counsellorHighestCert: '',

      psychAddress:       '',
      psychQualification: '',
      psychRegNum:        '',
      psychDescription:   '',
      psychSpeciality:    '',
      psychStatus:        '',

      ScopeOfPracticeOptions: [
        { value: '', text: 'Scope of practice', disabled: true },
        { value: 'Child Psychologist', text: 'Child Psychologist' },
        { value: 'Educational Psychologist', text: 'Educational Psychologist' },
        { value: 'Development Psychologist', text: 'Development Psychologist' },
        { value: 'Family Therapist', text: 'Family Therapist' },
      ],

      menu:   false,
      images: ["adult1.png", "adult2.png", "adult3.png",
      "adult4.png", "adult5.png", "adult6.png",
      "adult7.png", "adult8.png", "adult9.png"],

      snackbar:     false,
      snackbarText: 'Registration submitted to admin for approval',
      timeout:      5000,

      model: '', //for v-slide-group
    
    }
  },

  watch: {
    menu (val) {
      val && setTimeout(() => (this.$refs.menu.$refs.agenda.mode = 'year'));
    },
  },

  computed: {
      loginFilled() {
        return (!!this.email && !!this.password)
      },

      registerFilled() {
        return (!!this.email && !!this.password && !!this.name && !!this.passwordConfirm && !!this.dob)
      },

      passwordMismatch() {
        return (this.password !== this.passwordConfirm)
      },

      maxDate() {
        return moment().subtract(25, 'years').format('YYYY-MM-D')
      },

      psychPracticeFilled() {
        return (!!this.psychAddress && !!this.psychQualification && !!this.psychRegNum && !!this.psychDescription && !!this.psychSpeciality)
      },

      counsPracticeFilled() {
        return (!!this.counsellorPracNum && !!this.counsellorHighestCert)
      },
  },

  mounted() {
    const user = JSON.parse(window.localStorage.getItem('currentUser'))

    if (user && user.isLoggedIn) {
      this.$router.push({ name: 'Home' })
    }
  },

  methods: {
    toggleActiveForm(action) {
      document.getElementById('container').classList[action]("right-panel-active")
    },

    nextRegistrationForm1() {
      this.RegPageOne = true
      this.RegPageTwo = false
    },

    nextRegistrationForm2() {
      this.RegPageOne = false
      this.RegPageTwo = true
    },

    assignRole(role) {
      this.roleChosen   = role
      this.roleAssigned = true
      this.nextRegistrationForm1()
    },

    signUpBack() {
      this.roleAssigned = false
      this.RegPageOne   = false
      this.roleChosen   = ''
    },

    async login() {
      const requestParams = {
        Email:    this.email,
        Password: this.password
      }

      let resp = await this.$store.dispatch('auth/loginUser', requestParams)

      if (resp.userID !== 0 && resp.password == undefined) {

        //check if account is inactive, display modal asking if they'd like to reactivate
        //                      pending, display account approval in progress

        if(resp.status === 'Pending'){
          this.accountPending = true
          this.password       = ''
          return 
        }
        
        if(resp.status === 'Inactive') {
          this.accountInactive = true
          this.$bvModal.show('reactivate-account')
          return
        }

        resp.isLoggedIn = true

        localStorage.setItem('currentUser', JSON.stringify(resp))
        this.$store.commit('auth/updateLoggedInUser', resp)

        if(resp.userType === "Counsellor"){
          this.$router.push({ name: 'Counsellor Home' })
        }
        else if(resp.userType === "Psychologist"){
          this.$router.push({ name: 'Home' })
        }
        else if(resp.userType === "Admin"){
          this.$router.push({ name: 'Admin Home' })
        }
        
      } else if(resp.password === "Incorrect Password") {
        this.incorrectDetails = true
      }
    },


    async registerCounsellor() {
      const requestParams = {
      Name:               this.name,
      Email:              this.email,
      Password:           this.password,
      DOB:                this.dob,
      ProfilePicture:     this.profilePicture,
      DateRegistered:     new Date(),
      PracticeNum:        this.counsellorPracNum,
      HighestCertificate: this.counsellorHighestCert
    }

      const resp = await this.$store.dispatch('auth/registerCounsellor', requestParams)

      if(resp.counsellorID > 0){
        this.snackbar = true

        //clear email and password fields
        this.email    = ''
        this.password = ''

        this.toggleActiveForm('remove')
      } else if(resp.counsellorID === 0){
        this.userExists = true
      } else {
        window.alert("An unexpected error occurred. Please try again")
      }
    },

    async registerPsychologist() {

      const requestParams = {
      Name:           this.name,
      Email:          this.email,
      Password:       this.password,
      DOB:            this.dob,
      ProfilePicture: this.profilePicture,
      Address:        this.psychAddress,
      Qualification:  this.psychQualification,
      RegNumber:      this.psychRegNum,
      Description:    this.psychDescription,
      Speciality:     this.psychSpeciality,
      PsychStatus:    "Pending"
    }

    const resp = await this.$store.dispatch('auth/registerPsychologist', requestParams)

    if(resp.psychID > 0){
      this.snackbar = true

      //clear email and password fields
      this.email    = ''
      this.password = ''

      this.toggleActiveForm('remove')
    } else if(resp.psychID === 0){
      this.userExists = true
    } else {
      window.alert("An unexpected error occurred. Please try again")
    }
    },

    handleOk(bvModalEvt) {
      // Prevent modal from closing
      bvModalEvt.preventDefault()

      this.reactivateAccount()
    },

    async reactivateAccount() {
      const requestParams = {
        Email:    this.accActivateEmail,
        Password: this.accActivatePassword,
      }

      const resp = await this.$store.dispatch('auth/reactivateAccount', requestParams)

      if (resp.userID !== 0 && resp.password == undefined) {
        resp.isLoggedIn = true

        localStorage.setItem('currentUser', JSON.stringify(resp))
        this.$store.commit('auth/updateLoggedInUser', resp)

        // Hide the modal manually
        this.$nextTick(() => {
          this.$bvModal.hide('remove-account')
        })

        this.$router.push({ name: 'Home' })
      } else if(resp.password === "Incorrect Password") {
        this.incorrectReactivateDetails = true
      }
    },

    selectImage(image) {
      this.profilePicture = image
    },

    getImages(image) {
			const storedImages = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)
      return storedImages(`./${image}`)
		},
  }
}
</script>

<style scoped>

.alert-primary {
  background-color: rgba(28.2%,63.9%, 89.4%, 0.8);
  border-color: rgba(28.2%,63.9%, 89.4%, 0.8);
  font-weight: bold;
}

* {
    box-sizing: border-box;
}

.login-form__sign-in-button {
    background: #FF9965;
    color: white;
}

.login-form__sign-up-button {
  background: #FF9965;
  color: white;
  margin-top: 10px;
  width: 50%;
}

.back-button {
  width: 98.45px;
  background: #FF9965;
  color: white;
}

html {
    font-family: 'Segoe UI Semibold', Tahoma, Geneva, Verdana, sans-serif;
}

.login-body {
  background: #FBF5F2;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 100vh;
  margin: -10px 0 50px;
}

.login-h1 {
  font-weight: bold;
  margin: 0;
}

.login-h2 {
    text-align: center;
}

.login-p {
    font-size: 14px;
    font-weight: 100;
    line-height: 20px;
    letter-spacing: 0.5px;
    margin: 20px 0 30px;
}

.login-span {
    font-size: 12px;
}

.login-a {
    color: #333;
    font-size: 14px;
    text-decoration: none;
    margin: 15px 0;
}

.login-button {
    border-radius: 20px;
    border: 1px solid #FF4B2B;
    background-color: #FF4B2B;
    color: #FFFFFF;
    font-size: 12px;
    font-weight: bold;
    padding: 12px 45px;
    letter-spacing: 1px;
    text-transform: uppercase;
    transition: transform 80ms ease-in;
}

    .login-button:active {
        transform: scale(0.95);
    }

    .login-button:focus {
        outline: none;
    }

    .login-button.ghost {
        background-color: transparent;
        border-color: #FFFFFF;
    }

.no-shadow {
    box-shadow: none;
}

.login-btn {
    background-color: transparent;
    border-color: #FFFFFF;
    box-shadow: none;
}

    .login-btn:active {
        border-color: #FFFFFF;
        box-shadow: none;
    }

    .login-btn:hover {
        border-color: #FFFFFF;
        box-shadow: none;
    }

.login-form {
    background-color: #FFFFFF;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    padding: 0 50px;
    height: 100%;
    text-align: center;
}

.login-input {
    background-color: #eee;
    border: none;
    padding: 12px 15px;
    margin: 8px 0;
    width: 100%;
}

.login-select {
    border: none;
    padding: 12px 0px;
    /* margin: 8px 0; */
    width: 100%;
}

.scope-select {
  background-color: #eee;
  color: #6e6e6e;
  border-radius: 0rem;
  border: none;
}

.login-container {
    background-color: #fff;
    border-radius: 10px;
    /*box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);*/
    position: relative;
    overflow: hidden;
    width: 800px;
    max-width: 100%;
    min-height: 610px;
}

.form-container {
    position: absolute;
    top: 0;
    height: 100%;
    transition: all 0.6s ease-in-out;
}

.sign-in-container {
    left: 0;
    width: 50%;
    z-index: 2;
}

.login-container.right-panel-active .sign-in-container {
    transform: translateX(100%);
}

.sign-up-container {
    left: 0;
    width: 50%;
    opacity: 0;
    z-index: 1;
}

.login-container.right-panel-active .sign-up-container {
    transform: translateX(100%);
    opacity: 1;
    z-index: 5;
    animation: show 0.6s;
}

@keyframes show {
    0%, 49.99% {
        opacity: 0;
        z-index: 1;
    }

    50%, 100% {
        opacity: 1;
        z-index: 5;
    }
}

.overlay-container {
    position: absolute;
    top: 0;
    left: 50%;
    width: 50%;
    height: 100%;
    overflow: hidden;
    transition: transform 0.6s ease-in-out;
    z-index: 100;
}

.login-container.right-panel-active .overlay-container {
    transform: translateX(-100%);
}

.overlay {
    background: #FF9565;
    /*background: -webkit-linear-gradient(to right, #FF4B2B, #FF416C);
    background: linear-gradient(to right, #FF4B2B, #FF416C);*/
    background-repeat: no-repeat;
    background-size: cover;
    background-position: 0 0;
    color: #FFFFFF;
    position: relative;
    left: -100%;
    height: 100%;
    width: 200%;
    transform: translateX(0);
    transition: transform 0.6s ease-in-out;
}

.login-container.right-panel-active .overlay {
    transform: translateX(50%);
}

.overlay-panel {
    position: absolute;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    padding: 0 40px;
    text-align: center;
    top: 0;
    height: 100%;
    width: 50%;
    transform: translateX(0);
    transition: transform 0.6s ease-in-out;
}

.overlay-left {
    transform: translateX(-20%);
}

.login-container.right-panel-active .overlay-left {
    transform: translateX(0);
}

.overlay-right {
    right: 0;
    transform: translateX(0);
}

.login-container.right-panel-active .overlay-right {
    transform: translateX(20%);
}

.social-container {
    margin: 20px 0;
}

.social-container a {
    border: 1px solid #DDDDDD;
    border-radius: 50%;
    display: inline-flex;
    justify-content: center;
    align-items: center;
    margin: 0 5px;
    height: 40px;
    width: 40px;
}

.datepicker
  .table-condensed {
  width: 100%;
  margin: 2px 0px;
  /* height:50px; */
}

.datepicker /deep/ .dropdown-menu {
  max-height: 500%;
  overflow-y: auto;
}

.datepicker-label{
  font-size: smaller;
}

.image {
  margin-top: 9px !important;
}

.h5 {
  margin-bottom: -8px;
  margin-top: 5px;
}

.avatar-sm {
  /* background-position: center !important; */
  width: 4rem;
  height: 4rem;
  margin: .5rem 1rem;
}

.active {
  background-size: 200% 200%;
  background-color: #FF9565;
  border: 4px solid transparent;
}

.image-picker {
  height: 5rem;
}

.image-container {
  background-color: whitesmoke;
  margin: 8px;
  width: 284px;
}

.v-btn--rounded{
  border-radius: 50px;
}

.v-btn::before{
  background-color: transparent;
}

.logo {
  width: 58%;
}

</style>