import Vue        from 'vue'
import Router     from 'vue-router'
import Dashboard  from '@/components/Dashboard'
import Login      from '@/components/Login'
import SideNav    from '@/components/SideNav'
import Home       from '@/components/Home'
import TopNav     from '@/components/TopNav'
import Calendar   from '@/components/Calendar'
import Patients   from '@/components/Patients'
import Profile    from '@/components/Profile'
import EditCalendar from '@/components/EditCalendar'
import Account    from '@/components/Account'
import Practice   from '@/components/PracticeDetails'
import Notes from '@/components/Notes'
import ResourceHub from '@/components/ResourceHub'
import Problem from '@/components/Problem'
import Locations from '@/components/Locations'
import Appointments from '@/components/Appointments'
import PastAppointments from '@/components/PastAppointments'
import Appointment from '@/components/Appointment'
import Counsellor from '@/components/Counsellor'
import Chats from '@/components/Chats'
import Tests from '@/components/Tests'
import Test from '@/components/Test'
import Results from '@/components/Results'
import ChildResults from '@/components/ChildResults'
import PsychologistChat from '@/components/PsychologistChat'
import Reports from '@/components/Reports'
import Admin from '@/components/Admin'
import Approvals from '@/components/Approvals'
import Approval from '@/components/Approval'
import EditResource from '@/components/EditResource'
import Psychologists from '@/components/Psychologists'
import Counsellors from '@/components/Counsellors'
import EditAccount from '@/components/EditAccount'
import ChildReports from '@/components/ChildReports'


Vue.use(Router)

const router = new Router({
  routes: [
    {
      path:      '/',
      name:      'Login',
      component: Login
    },
    {
      path:      '/dashboard',
      name:      'Dashboard',
      component: Dashboard
    },
    {
      path:      '/sideNav',
      name:      'SideNav',
      component: SideNav
    },
    {
      path:      '/home',
      name:      'Home',
      component: Home
    },
    {
      path:      '/topNav',
      name:      'TopNav',
      component: TopNav
    },
    {
      path:      '/calendar',
      name:      'Calendar',
      component: Calendar
    },
    {
      path:      '/patients',
      name:      'Patients',
      component: Patients
    },
    {
      path:      '/profile',
      name:      'Profile',
      component: Profile
    },
    {
      path:      '/editcalendar',
      name:      'Edit Calendar',
      component: EditCalendar
    },
    {
      path:      '/account',
      name:      'Account',
      component: Account
    },
    {
      path:      '/practice',
      name:      'Practice',
      component: Practice
    },
    {
      path:      '/notes',
      name:      'Notes',
      component: Notes
    },
    {
      path:      '/resourcehub',
      name:      'Resource Hub',
      component: ResourceHub
    },
    {
      path:      '/problem',
      name:      'Problem',
      component: Problem
    },
    {
      path:      '/locations',
      name:      'Resource Hub Locations',
      component: Locations
    },
    {
      path:      '/appointments',
      name:      'Appointments',
      component: Appointments
    },
    {
      path:      '/pastappointments',
      name:      'Past Appointments',
      component: PastAppointments
    },
    {
      path:      '/appointment',
      name:      'Appointment Details',
      component: Appointment
    },
    {

      path:      '/counsellor',
      name:      'Counsellor Home',
      component: Counsellor
    },
    {
      path:      '/chats',
      name:      'Chats',
      component: Chats
    },
    {
      path:      '/tests',
      name:      'Tests',
      component: Tests
    },
    {
      path:      '/test',
      name:      'Test',
      component: Test
    },
    {
      path:      '/results',
      name:      'Test Results',
      component: Results
    },
    {
      path:      '/psychologistChat',
      name:      'Psychologist Chat',
      component: PsychologistChat
    },
    {
      path:      '/childresults',
      name:      'Child Results',
      component: ChildResults
    },
    {
      path:      '/reports',
      name:      'Reports',
      component: Reports
    },
    {
      path:      '/admin',
      name:      'Admin Home',
      component: Admin
    },
    {
      path:      '/approvals',
      name:      'Account Approvals',
      component: Approvals
    },
    {
      path:      '/approval',
      name:      'Account Approval',
      component: Approval
    },
    {
      path:      '/editresource',
      name:      'Edit Resource Hub Problem',
      component: EditResource
    },
    {
      path:      '/psychologists',
      name:      'Psychologists',
      component: Psychologists
    },
    {
      path:      '/counsellors',
      name:      'Counsellors',
      component: Counsellors
    },
    {
      path:      '/editaccount',
      name:      'Edit Account',
      component: EditAccount
    },
    {
      path:      '/childreports',
      name:      'Child Reports',
      component: ChildReports
    },
  ]
})

router.beforeEach((to, from, next) => {
  const user = JSON.parse(window.localStorage.getItem('currentUser')) || {}

  if (user && user.isLoggedIn !== true && to.name !== 'Login') {
    next(false)
    window.location.href = '/'
  } else {
    next()
  }
})

export default router
