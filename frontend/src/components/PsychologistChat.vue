<template>
  <div class="main-content">
    <v-app>
      <div class="row">
        <div class="col-xl-3">
          <div class="card">
            <div class="card-header bg-transparent">
              <div class="row align-items-center">
                <div class="col">
                  <v-text-field
                    v-model="search"
                    append-icon="mdi-magnify"
                    label="Search Chats"
                    color="#022F5B"
                    single-line
                    clear-icon="mdi-close-circle"
                    rounded
                    solo
                    hide-details
                    @click:clear="search == ''"
                  >
                  </v-text-field>
                </div>
              </div>
            </div>
            <!-- <div class="card-body"> -->
            <v-simple-table class="chat-list">
              <template v-slot:default>
                <tbody class="selectChat">
                  <tr
                    v-for="chat in chatSearch"
                    :key="chat.id"
                    :class="chat.chatterPerson.userID == chattingChild.userID ? 'chatRowBack' : ''"
                    @click="activateChat(chat)"
                  >
                    <td class="paddingChatRow">
                      <v-avatar :size="windowWidth < 1400 ? 30 : 40">
                        <img :src="getUserAvatar(chat.chatterPerson)" />
                      </v-avatar>
                    </td>

                    <td class="paddingChatRow">
                      <div class="name-chat">
                        <strong>{{ chat.chatterPerson.name }}</strong>

                        <div class="ellipsis">
                          <span  v-if="chat.lastMessage" :class="chat.nUnreadMessages > 0 ? 'chat-message unread' : 'chat-message'">
                            {{ chat.lastMessage.message }}
                          </span>
                        </div>
                      </div>
                    </td>

                    <td>
                      <div v-if="chat.lastMessage" :class="chat.lastMessage.date.length > 5 ? 'chatDateTime dateLong' : 'chatDateTime'">
                        {{ formatChatListDate(chat.lastMessage.date) }}
                      </div>

                      <span class="badge mbadge" :style="chat.nUnreadMessages > 0 ? 'visibility: visible' : 'visibility: hidden'">
                        {{ chat.nUnreadMessages }}
                      </span>
                    </td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
            <!-- </div> -->
          </div>
        </div>

        <div class="col-xl-6">
          <div class="card">
            <div class="card-body chatWindow" style="height: 75vh; overflow-y: auto" id="chatWindow" ref="chatWindow">
              <div v-if="chattingChild.userID > 0 && messages.length">

                <div v-for="message in messages" :key="message.id">
                  <div class="row no-gutters">
                    <div class="col-md-6 relative" :class="message.mine ? 'offset-md-6' : ''">
                      <v-card :color="message.mine ? '#FF9565' : '#F1F2F6'" flat outlined shaped :style="message.mine ? 'display: inline-block; float: right' : 'display: inline-block;'">
                        <v-card-text :style="message.mine ? 'color: white' : 'color: black'" >
                          {{ message.message }}
                        </v-card-text>
                      </v-card>

                      <span class="col-md-6" :class="message.mine ? 'myMessageDate' : 'messageDate'">
                        {{ formatMessageDate(message.date) }}
                      </span>
                    </div>
                  </div>
                  <br />
                </div>
              </div>

              <div v-if="chattingChild.userID > 0 && !messages.length">
                <span style="justify-content: center; color: #022F5B">No messages to display</span>
              </div>

              <div class="no-messages" v-if="chattingChild.userID <= 0">
                <span class="fas fa-comments fa-5x" style="justify-content: center; color: #FF9565"></span>
                <v-card-title style="justify-content: center; color: #022F5B">Nothing here yet!</v-card-title>
                <v-card-subtitle style="text-align: center; color: #022F5B">Select a chat on the left to see a specific chat</v-card-subtitle>
              </div>
              <!-- <v-btn
                v-scroll="onScroll"
                v-show="fab"
                color="#FF9565"
                fab
                absolute
                bottom
                right
                dark
                @click="scrollToBottom">
                    <v-icon>mdi-chevron-double-down</v-icon>
              </v-btn> -->
            </div>


            <v-text-field
              v-if="chattingChild.userID > 0"
              ref="newMessage"
              v-model="newMessage"
              append-icon="mdi-send"
              label="Type message"
              color="#FF9565"
              solo
              hide-details
              @click:append="sendMessage()"
              @keydown.enter="sendMessage()"
            >
            </v-text-field>
          </div>
        </div>

        <div class="col-xl-3" v-if="chattingChild.userID > 0">
          <div class="card">
            <div class="card-body">
              <v-card flat class="userAvatar">
                <v-avatar size="120">
                  <img :src="getUserAvatar(chattingChild)" />
                </v-avatar>

                <v-card-text>
                  <h3>{{ chattingChild.name }}</h3>
                </v-card-text>
              </v-card>

              <v-divider></v-divider>

              <h4 style="color: #ff9565">Chat Settings</h4>
              <div>
                <div class="row">
                  <span class="muteText" style="align-self: center">Mute Notifications</span>

                  <v-switch
                    class="muteSwitch"
                    v-model="muted"
                    dense
                    flat
                    inset
                    color="#FF9565"
                  >
                  </v-switch>
                </div>
              </div>

              <div>
                <a href="#" style="color: red">Report Chat</a>
              </div>
            </div>
          </div>
        </div>
      </div>

      <v-snackbar v-model="snackbar" :timeout="timeout">
        Can't send empty message

        <template v-slot:action="{ attrs }">
          <v-btn color="#FF9565" text v-bind="attrs" @click="snackbar = false">
            Close
          </v-btn>
        </template>
      </v-snackbar>
    </v-app>
  </div>
  
</template>

<script>
import moment from 'moment'
import FuzzySearch from 'fuzzy-search'
export default {
  computed: {
    loggedIn() {
      return this.$store.getters['auth/loggedInUser']
    },

    childID(){
      return this.$store.getters['chat/psychChattingChildID']
    },

    messages(){
      return this.$store.getters['chat/psychCurrentChats'](this.childID) || []
    },

  },

  watch: {
    search(value) {
      let result = this.chatList

      if (value !== "") {
        const searcher = new FuzzySearch(this.chatList, ['chatterPerson.name'], {
          caseSensitive: false
        })

        result = searcher.search(value)
      }

      this.chatSearch = result
    }
  },

  async created(){
    await this.getList()
    await this.getChatHistory()
  },

  // updated() {
  //   this.scrollToBottom()
  // },

  beforeDestroy() {
    clearInterval(this.polling)
    clearInterval(this.listPolling)
    window.removeEventListener("resize", this.getWindowWidth)
  },

  async mounted() {
    await this.getChild(this.childID)

    this.$nextTick(function () {
      window.addEventListener("resize", this.getWindowWidth)
      //Init
      this.getWindowWidth()
    })
  },

  data() {
    return{
      newMessage: '',

      polling:     null,
      listPolling: null,

      search:   "",
      snackbar: false,
      timeout:  2000,

      chattingChild: {},
      chatList:      [],
      chatSearch:    [],
      muted:         true,

      windowWidth:  window.innerWidth,

    }
  },

  methods: {
    getWindowWidth() {
      this.windowWidth = document.documentElement.clientWidth

      this.scrollToBottom()
    },

    async sendMessage() {
      if (!this.newMessage) {
        this.snackbar = true
      }
      else
        await this.saveMessage()
    },

    clearAllMessages() {
      this.messages = []
    },

    scrollToBottom() {
      const container     = this.$el.querySelector("#chatWindow");
      container.scrollTop = container.scrollHeight;
    },

    async activateChat(chatObj) {
      this.chattingChild = chatObj.chatterPerson

      await this.$store.dispatch('chat/setChattingChild', chatObj.chatterPerson.userID)

      this.chatList.forEach((item) => {
        item.active = (item.chatterPerson.userID === this.chattingChild.userID)
      })

      this.getChatHistory()
      this.markMessagesAsRead(chatObj)
    },

    async getChatHistory(){
      const psych = JSON.parse(window.localStorage.getItem('currentUser'))
      const childID = this.childID

      const requestParams = {
        ChildID: childID,
        PsychID: psych.userID
      }

      await this.$store.dispatch('chat/getChatHistory', requestParams)
      this.polling = setInterval(() => {
        this.$store.dispatch('chat/getChatHistory', requestParams)
      }, 1000) 

      this.scrollToBottom()
      
    },

    async getChild(){
      const psych = JSON.parse(window.localStorage.getItem('currentUser'))
      
      const requestParam = {
        ChildID: this.childID,
        PsychID: psych.userID
      }

      this.chattingChild = await this.$store.dispatch('users/GetChild', requestParam)
    },

    async saveMessage(){
      const requestParams = {
        PractitionerID: this.loggedIn.userID,
        UserID:         this.childID,                    
        MessageContent: this.newMessage,
        SenderID:       this.loggedIn.userID
      }

      await this.$store.dispatch('chat/saveMessage', requestParams)

      this.newMessage = ''
      this.scrollToBottom()
    },

    formatTimeStamp(date){
      let timestamp = moment(date).format('HH:mm')
      let messageDate = moment(date).format('MMM D')

      const originalDate = moment(date).format('DD MMMM YYYY')
      const today = moment()
      let dateDiff = today.diff(moment(originalDate), 'days')

      if(dateDiff === 0){
        return timestamp
      }

      return timestamp + ' | ' + messageDate
    },

    getUserAvatar(user) {
			const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)

			return images(`./${user.profilePicture}`)
		},

    async getList(){
      this.listPolling = setInterval(async () => {
         await this.getPsychChatList()
      }, 2000)
    },

    async getPsychChatList() {
			const requestParam = {
				psychID: JSON.parse(window.localStorage.getItem('currentUser')).userID
			}

			const tempChatList = await this.$store.dispatch('chat/getPsychChatList', requestParam)
      this.chatList = tempChatList.map((chat) => {
        chat.active = false
        chat.muted  = false

        return chat
      })

      this.chatSearch = this.chatList

		},

    async markMessagesAsRead(listItem){
      const psych = JSON.parse(window.localStorage.getItem('currentUser'))

      //find the index of the active chat
      const activeChatListIndex = this.chatList.indexOf(listItem)

      const requestParam = {
        LinkID:   this.chatList[activeChatListIndex].linkID,
        SenderID: psych.userID
      }

      const resp = await this.$store.dispatch('chat/markPsychMessagesAsRead', requestParam)

      this.chatList = []
      this.chatList = resp.map((chat) => {
        chat.active = (chat.chatterPerson.userID === this.chattingChild.userID)
        chat.muted  = false

        return chat
      })

      this.chatSearch = this.chatList
    },

    formatMessageDate(date){
      const currentDate = moment().format('DD MMMM yyyy')

      const messsageDate = moment(date).format('DD MMMM yyyy')

      if(currentDate === messsageDate){
        return moment(date).format('HH:mm')
      }
      else{
        return moment(date).format('DD MMM | HH:mm')
      }
    },

    formatChatListDate(date){
      const currentDate  = moment().format('DD MMMM yyyy')
      const messsageDate = moment(date).format('DD MMMM yyyy')

      if(currentDate == messsageDate){
        return moment(date).format('HH:mm')
      }
      else{
        return moment(date).format('yyyy/MM/DD')
      }
    },

  }
 
}
</script>

<style scoped>

  /* width */
  ::-webkit-scrollbar {
    width: 3px;
  }

  /* Track */
  ::-webkit-scrollbar-track {
    background: #f1f1f1; 
    border-radius: 10px;
  }

  /* Handle */
  ::-webkit-scrollbar-thumb {
    background: #FF9565; 
    border-radius: 10px;
    height: 5%;
  }

  .no-messages {
    text-align: center;
  }

  .chat-area {
  /*   border: 1px solid #ccc; */
    background: white;
    height: 50vh;
    padding: 1em;
    overflow: auto;
    max-width: 350px;
    margin: 0 auto 2em auto;
    box-shadow: 2px 2px 5px 2px rgba(0, 0, 0, 0.3)
  }

  .chat-list {
    height: 68vh;
    overflow-y: auto;
    overflow-x: hidden;
    padding-right: 0px;
  }

  .chat-body{
    overflow: auto;
    height: 370px;
    width: 100%;
  }

  .message {
    width: 50%;
    border-radius: 10px;
    padding: .5em;
    margin-bottom: .5em;
    font-size: .8em;
  }
  .message-out {
    background: #FF9565;
    color: white;
    margin-left: 50%;
    border-radius: 15px;
    padding: 0px 15px;
    min-height: 32px;
    max-width: 50%;
    margin-bottom: 20px;
    text-align: left;
  }
  .message-in {
    background: #F1F0F0;
    color: #32325d;
    border-radius: 15px;
    padding: 0px 15px;
    margin-right: 50%;
    min-height: 32px;
    max-width: 50%;
    margin-bottom: 20px;
  }
  .chat-inputs {
    top: 570px;
    position: fixed;
    width: 75%;
  }
  #person1-input {
    padding: .5em;
  }
  #person2-input {
    padding: .5em;  
    width: 90%;
    background-color: whitesmoke;
    border-radius: .375rem;
    white-space: pre-wrap;
  }

  .p-out{
    float: right;
    white-space: pre-line;
  }

  .p-in{
    float: left;
    white-space: pre-line;
  }

  .p-out-time{
    color: white;
  }

  .message-time{
    position: relative;
    top: -10px;
  }

  .send-button{
    float: right;
    margin-right: 20px;
    background-color: #ff9565;
    color: white;
    width: 70px;
    height: 45px;
    border-radius: .375rem;
    margin-top: 5px;
  }

  .text-small {
    font-size: 0.9rem;
  }

  .small{
    float: right;
    font-size: 0.7rem;
    margin-top: -30px;
    font-weight: 400;
    margin-bottom: 0px;
  }

  .image {
    margin-top: 16px !important;
  }

  .avatar-sm {
    /* background-position: center !important; */
    width: 2.5rem;
    height: 2.5rem;
  }

  .active {
    background-size: 200% 200%;
    background-color: #FF9565;
    border: 4px solid transparent;
  }

  .empty-chat{
    position: relative;
    top: -385px;
    margin: 5px 43%;
    color: gray;
  }

  .no-scroll{
    overflow: hidden;
  }

  .name-chat {
    margin-left: -20px;
  }

  .myMessageDate {
    font-size:     12px;
    color:         #00000099;
    position:      absolute;
    top:           84%;
    right:         0;
    text-align:    right;
    padding-left:  0;
    padding-right: 0;
    /* margin-top:    5%; */
  }

  @media only screen and (min-width: 992px) {
    /*Not necessary if text-align: right on base style*/
    /* .myMessageDate {
      right: -10%;
    } */
  }

  @media only screen and (min-width: 992px) {
    /*Not necessary if text-align: right on base style*/
    /* .myMessageDate {
      right: -15%;
    } */
  }

  .messageDate {
    font-size: 12px;
    color:     #00000099;
    display:   block;
  }

  .muteText {
    display:     inline-block;
    align-self:  center;
    margin-left: 15px;
  }

  .muteSwitch {
    display:  inline-block;
    position: relative;
    left:     25%;
  }

  @media only screen and (min-width: 992px) {
    .muteSwitch {
      left: 15%;
    }
  }

  .userAvatar {
    text-align:    center;
    margin-bottom: -25px;
  }

  .chatRowBack {
    background-color: #eee;
  }

  .selectChat {
    cursor: pointer;
  }

  .unread {
    font-weight: bold;
  }

  .mbadge {
    top:   0;
    right: 0rem;
  }

  .dateLong {
    margin-left: -32px !important;
  }

  .chatDateTime {
    font-size:   10px;
    color:       #00000099;
    margin-left: -5px;
    text-align:  right;
  }

  .paddingChatRow {
    padding-top:    10px !important;
    padding-bottom: 10px !important;
  }

  .ellipsis {
    text-overflow: ellipsis;
    width:         215px;
    /* Required for text-overflow to do anything */
    white-space: nowrap;
    overflow:    hidden;
  }

  @media only screen and (min-width: 992px) {
    .ellipsis {
      width: 120px;
    }
  }

  @media only screen and (min-width: 1400px) {
    .ellipsis {
      width: 125px;
    }
  }

  .v-application--wrap {
    min-height: 0;
  }

  .theme--light.v-application {
    background: #fbf5f2;
  }

  .v-application {
    min-height: 0;
  }

</style>
