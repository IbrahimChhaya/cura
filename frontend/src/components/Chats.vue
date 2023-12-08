<style>
  /* body {
    overflow: hidden;
  } */
</style>
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
            <v-simple-table>
              <template v-slot:default>
                <tbody class="selectChat">
                  <tr
                    v-for="(chat, index) in chatSearch"
                    :key="chat.id"
                    :class="chat.active ? 'chatRowBack' : ''"
                    @click="activateChat(chat, index)"
                  >
                    <td class="paddingChatRow">
                      <v-avatar :size="windowWidth < 1400 ? 30 : 40">
                        <img :src="getUserAvatar(chat.chatterPerson.profilePicture)" />
                      </v-avatar>
                    </td>

                    <td class="paddingChatRow">
                      <div class="name-chat">
                        <strong>{{ chat.chatterPerson.name }}</strong>

                        <div class="ellipsis">
                          <span :class="chat.nUnreadMessages > 0 ? 'chat-message unread' : 'chat-message'">
                            {{ chat.lastMessage.message }}
                          </span>
                        </div>
                      </div>
                    </td>

                    <td>
                      <div :class="chat.lastMessage.date.length > 5 ? 'chatDateTime dateLong' : 'chatDateTime'">
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
              <div v-if="messages.length">

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

              <div v-if="!messages.length">
                <v-card 
                class="justify-center mt-2"
                flat
                style="text-align: center">
                    <span class="far fa-comment-alt fa-5x" style="color: #FF9565"></span>
                    <v-card-title style="justify-content: center; color: #022F5B">Nothing here yet!</v-card-title>
                    <v-card-subtitle>Check back later to see if anyone has messaged</v-card-subtitle>
                </v-card>
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
              v-if="messages.length"
              ref="typeMessage"
              v-model="typeMessage"
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

        <div class="col-xl-3" v-if="activeChatPerson.userID">
          <div class="card">
            <div class="card-body">
              <v-card flat class="userAvatar">
                <v-avatar size="120">
                  <img :src="getUserAvatar(activeChatPerson.profilePicture)" />
                </v-avatar>

                <v-card-text>
                  <h3>{{ activeChatPerson.name }}</h3>
                </v-card-text>
              </v-card>

              <v-divider></v-divider>

              <h4 style="color: #ff9565">Focus Point</h4>
              <br />

              <v-dialog v-model="dialog" width="400">
                <template v-slot:activator="{ on, attrs }">
                  <v-chip
                    v-if="currentChatFocus != 'Not Specified'"
                    color="#11cdef"
                    outlined
                    label
                    v-bind="attrs"
                    v-on="on"
                  >
                    {{ chatList[activeChat].chatFocus.problem }}

                    <v-icon right> mdi-pencil </v-icon>
                  </v-chip>

                  <v-chip v-else outlined label v-bind="attrs" v-on="on">
                    Not Specified

                    <v-icon right> mdi-pencil </v-icon>
                  </v-chip>
                </template>

                <v-card>
                  <div class="text-center">
                    <br />
                    <h2>Select Focus Point</h2>
                  </div>

                  <v-container class="py-0">
                    <v-row align="center" justify="start">
                      <div class="noFocusPosition" v-if="selections[0] == undefined">
                        <v-chip outlined label>No Focus Specified</v-chip>
                      </div>

                      <v-col v-for="(selection, i) in selections" :key="selection.problemID" class="shrink">
                        <v-chip
                          :disabled="loading"
                          close
                          outlined
                          label
                          color="#11cdef"
                          @click:close="
                            selected.splice(i, 1);
                            allSelected = false;
                          "
                        >
                          {{ selection.problem }}
                        </v-chip>
                      </v-col>

                      <v-col v-if="!allSelected" cols="12">
                        <v-text-field
                          ref="dialogSearch"
                          v-model="dialogSearch"
                          full-width
                          hide-details
                          label="Search"
                          single-line
                        >
                        </v-text-field>
                      </v-col>
                    </v-row>
                  </v-container>

                  <v-divider v-if="!allSelected"></v-divider>

                  <v-list
                    v-if="!allSelected"
                    style="max-height: 169px"
                    class="overflow-y-auto"
                  >
                    <template v-for="item in categories">
                      <v-list-item
                        v-if="!selected.includes(item)"
                        :key="item.problemID"
                        :disabled="loading"
                        @click="selected.push(item)"
                      >
                        <v-list-item-title
                          v-text="item.problem"
                        ></v-list-item-title>
                      </v-list-item>
                    </template>
                  </v-list>

                  <v-divider></v-divider>

                  <v-card-actions>
                    <v-spacer></v-spacer>

                    <button type="button" class="btn btn-secondary" @click="dialog = false">
                      Close
                    </button>

                    <button type="button" class="btn btn-primary" @click="saveFocus()">
                      Save
                    </button>
                  </v-card-actions>
                </v-card>
              </v-dialog>

              <v-divider></v-divider>

              <h4 style="color: #ff9565">Chat Settings</h4>
              <div>
                <div class="row">
                  <span class="muteText" style="align-self: center">Mute Notifications</span>

                  <v-switch
                    class="muteSwitch"
                    v-model="chatList[activeChat].muted"
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
import moment from "moment"
import FuzzySearch from 'fuzzy-search'
//import notifications from "../assets/js/notifications"'
export default {
  data() {
    return {
      fab:          false,
      snackbar:     false,
      timeout:      2000,
      typeMessage:  "",
      content:      "",
      editorOption: {
        placeholder: "Add new note...",
        modules:     {
          toolbar: [
            [
              {
                size: ["small", false, "large", "huge"],
              },
            ],
            ["bold", "italic", "underline", "strike", { color: [] }],
            [{ list: "ordered" }, { list: "bullet" }, { align: [] }],
            ["clean"],
          ],
        },
      },
      search:   "",
      // messages: [],

      activeChat:       0,
      chatList:         [],
      chatSearch:       [],
      activeChatPerson: {},

      focusPoints:      [],
      currentChatFocus: '',

      loading:      false,
      selected:     [],
      dialogSearch: "",
      dialog:       false,
      windowWidth:  window.innerWidth,

      polling: null,
    };
  },

  async mounted() {
    this.scrollToBottom();

    this.$nextTick(function () {
      window.addEventListener("resize", this.getWindowWidth);
      //Init
      this.getWindowWidth();
    });

    const user = JSON.parse(window.localStorage.getItem('currentUser'))

    // this.activeChat = 0
    // await this.getChatList()
    await this.fetchAllFocusPoints()
  },

  computed: {
    allSelected() {
      return this.selected.length === 1;
    },

    noPointSelected(){
      return this.selectedFocusPoint === ''
    },

    categories() {
      const search = this.dialogSearch.toLowerCase();

      if (!search) return this.focusPoints;

      return this.focusPoints.filter((item) => {
        const text = item.problem.toLowerCase();

        return text.indexOf(search) > -1;
      });
    },

    selections() {
      const selections = [];

      for (const selection of this.selected) {
        selections.push(selection);
      }

      return selections;
    },

    messages() {
      return this.$store.getters['chat/currentCounsellorChats']
    },

    // chatList() {
    //   return this.$store.getters['chat/chatList']
    // },
  },

  watch: {
    selected() {
      this.dialogSearch = "";
    },

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

  updated() {
    // whenever data changes and the component re-renders, this is called.
    // this.$nextTick(() => this.scrollToEnd());
    this.scrollToBottom()
  },

  async created() {
    window.addEventListener("resize", this.resizeAvatar);
    this.activeChat = 0
    await this.getList()
  },

  destroyed() {
    window.removeEventListener("resize", this.resizeAvatar);

    clearInterval(this.polling)
  },

  beforeDestroy() {
    window.removeEventListener("resize", this.getWindowWidth);
  },

  methods: {
    getWindowWidth(event) {
      this.windowWidth = document.documentElement.clientWidth;

      this.scrollToBottom();
    },

    onScroll(e) {
      if (typeof window === "undefined") return;

      const top = -window.pageYOffset || e.target.scrollHeight || 9999;
      this.fab  = top > e.target.scrollHeight - 20;
    },

    scrollToBottom() {
      const container     = this.$el.querySelector("#chatWindow");
      container.scrollTop = container.scrollHeight;
    },

    async saveFocus() {
      const counsellor = JSON.parse(window.localStorage.getItem('currentUser'))
      let chatFocusParam = {};

      if(this.selections[0] !== undefined){
        chatFocusParam = {
          CounsellorID: counsellor.userID,
          ChildID:      this.chatList[this.activeChat].chatterPerson.userID,
          Focus:        this.selections[0].problemID
        };
      }
      else{
        return
      }

      const resp = await this.$store.dispatch('chat/assignChatFocus', chatFocusParam);
      if(resp === 0){
        this.currentChatFocus                    = this.selections[0].problem
        this.chatList[this.activeChat].chatFocus = this.selections[0]
      }else{
        this.currentChatFocus = 'Not Specified'
      }

      this.dialog = false
    },

    next() {
      this.loading = true;

      setTimeout(() => {
        this.search   = "";
        this.selected = [];
        this.loading  = false;
      }, 2000);
    },

    activateChat(chatObj, index) {
      this.currentChatFocus = (chatObj.chatFocus == null) ? 'Not Specified' : chatObj.chatFocus.problem
      this.activeChat       = index;
      this.activeChatPerson = chatObj.chatterPerson

      // this.chatSearch.splice(index, 1, chatObj)
      this.chatList.forEach((item) => {
        item.active = (item.chatterPerson.userID === this.activeChatPerson.userID)
      })

      this.getActiveChatMessages()
      this.markMessagesAsRead()
    },

    async fetchAllFocusPoints() {
      this.focusPoints = await this.$store.dispatch("hub/fetchAllFocusResource")
    },

    async sendMessage() {
      if (this.typeMessage == "" || this.typeMessage == null) {
        this.snackbar = true;

      } else {
        const counsellorLoggedIn = JSON.parse(window.localStorage.getItem('currentUser'))

        const requestParam = {
          CounsellorChatID: this.chatList[this.activeChat].counsellorChatID,
          Message:          this.typeMessage,
          SenderID:         counsellorLoggedIn.userID
        }

        const resp = await this.$store.dispatch('chat/saveCounsellorChatMessage', requestParam)

        if(resp != undefined && resp.chatID > 0){
          this.typeMessage = ""
        }
      }

      this.scrollToBottom()
    },

    scrollToEnd: function () {
      this.$refs.chatWindow.scrollTop = this.$refs.chatWindow.lastElementChild.offsetTop;
    },

    async getList(){
      this.polling = setInterval(async () => {
        this.chatList = await this.getChatList()
      }, 2000)
    },

    async getChatList() {
      const counsellorLoggedIn = JSON.parse(window.localStorage.getItem('currentUser'))
      const requestParam = {
        counsellorID: counsellorLoggedIn.userID
      }

      const tempChatList = await this.$store.dispatch('chat/getCounsellorChatList', requestParam)

      this.chatList = tempChatList.map((chat) => {
        chat.active = false
        chat.muted  = false

        return chat
      })

      this.chatSearch = this.chatList
      this.activateChat(this.chatList[this.activeChat], this.activeChat)
    },


    getUserAvatar(image) {
			const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)

			return images(`./${image}`)
		},

    async getActiveChatMessages(){
      const counsellorLoggedIn = JSON.parse(window.localStorage.getItem('currentUser'))

      const requestParam = {
        CounsellorID: counsellorLoggedIn.userID,
        ChildID:      this.chatSearch[this.activeChat].chatterPerson.userID
      }

      const resp = await this.$store.dispatch('chat/getCurrentChatMessages', requestParam)
      if(resp != undefined){
        this.messages = resp.map((message) => {
         message.mine = (message.senderID === counsellorLoggedIn.userID)

         return message
        })
      }

      this.scrollToBottom()
    },

    async markMessagesAsRead(){
      const counsellorLoggedIn = JSON.parse(window.localStorage.getItem('currentUser'))

      const requestParam = {
        CounsellorChatID: this.chatList[this.activeChat].counsellorChatID,
        CounsellorID:     counsellorLoggedIn.userID,
        SenderID:         counsellorLoggedIn.userID
      }

      const resp = await this.$store.dispatch('chat/markMessagesAsRead', requestParam)

      this.chatList = []
      this.chatList = resp.map((chat) => {
        chat.active = (chat.chatterPerson.userID === this.activeChatPerson.userID)
        chat.muted  = false

        return chat
      })

      this.chatSearch = this.chatList

      // const element = document.getElementsByClassName('chatWindow')[0]
      //   element.scrollIntoView({ behavior: "smooth", block: "nearest" })

      // this.$nextTick(() => {
      //   let messageDisplay = this.$refs.chatMessages
      //   messageDisplay.scrollTop = messageDisplay.scrollHeight
      // })
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
      const currentDate = moment().format('DD MMMM yyyy')

      const messsageDate = moment(date).format('DD MMMM yyyy')

      if(currentDate === messsageDate){
        return moment(date).format('HH:mm')
      }
      else{
        return moment(date).format('yyyy/MM/DD')
      }
    }
  },
};
</script>

<style scoped>
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