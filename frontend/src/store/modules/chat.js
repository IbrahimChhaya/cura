import Vue from 'vue'
import apiClient from '../../services/apiClient'

const namespaced = true

const state = {
  chatList:               [],
  psychChattingChildID:   0,
  psychCurrentChats:      {},
  currentCounsellorChats: [],
  psychChatList:          [],
}

const getters = {
  chatList: state => {
    return state.chatList || []
  },

  psychChattingChildID: state => {
    return state.psychChattingChildID
  },

  psychCurrentChats: (state) => (childID) => {
    return state.psychCurrentChats[childID]
  },

  currentCounsellorChats: (state) => {
    return state.currentCounsellorChats || []
  },

  psychChatList: state => {
    return state.psychChatList || []
  },
}

const mutations = {
  updateChatList(state, chatsList){
    state.chatList = chatsList
  },

  updatePsychChattingChildID(state, childID) {
    state.psychChattingChildID = childID
  },

  updatePsychCurrentChats(state, { childID, currentMessages }) {
    Vue.set(state.psychCurrentChats, childID, currentMessages)
  },

  updateCurrentCounsellorChats(state, messages){
    state.currentCounsellorChats = messages
  },

  clearPsychMessages(state){
    state.psychCurrentChats.messages = []
  },

  updatePsychChatList(state, listChats) {
    state.psychChatList = listChats
  },
}

const actions = {
  async assignChatFocus(context, requestParams){
    return apiClient.chat.assignChatFocus(requestParams)
  },

  async getCounsellorChatList(context, counsellor){
    const resp = await apiClient.chat.getCounsellorChatList(counsellor)

    // const list = resp.map((chat) => {
    //   chat.active = false
    //   chat.muted  = false

    //   return chat
    // })

    context.commit('updateChatList', resp)

    return resp
  },

  async getCurrentChatMessages(context, params){
    const resp = await apiClient.chat.getCurrentChatMessages(params)

    let messages = []
    if(resp != undefined){
      messages = resp.map((message) => {
       message.mine = (message.senderID === params.CounsellorID)

       return message  
      })
    }

    context.commit('updateCurrentCounsellorChats', messages)
  },

  async saveCounsellorChatMessage(context, param){
    const resp = await apiClient.chat.saveCounsellorChatMessage(param)

    const currentMessageList = context.getters.currentCounsellorChats

    resp.mine = true
    currentMessageList.push(resp)

    context.commit('updateCurrentCounsellorChats', currentMessageList)

    return resp
  },

  async markMessagesAsRead(context, params){
    const resp = await apiClient.chat.markMessagesAsRead(params)

    context.commit('updateChatList', resp)
    
    return resp
  },

  setChattingChild(context, childID){
    context.commit('updatePsychChattingChildID', childID)
  },

  async getChatHistory(context, requestParams){
    const resp = await apiClient.chat.getChatHistory(requestParams)

    let messages = []
    if(resp != undefined){
      messages = resp.map((message) => {
       message.mine = (message.senderID === requestParams.PsychID)

       return message  
      })
    }

    const payload = {
      childID:         requestParams.ChildID,
      currentMessages: messages
    }

    context.commit('updatePsychCurrentChats', payload)
    
  },

  async saveMessage(context, requestParams){
    const resp = await apiClient.chat.saveMessage(requestParams)

    resp.mine = true

    const current = context.getters.psychCurrentChats(requestParams.UserID)

    current.push(resp)
    const payload = {
      childID:         requestParams.UserID,
      currentMessages: current
    }

    context.commit('updatePsychCurrentChats', payload)
    
  },

  async getPsychHomeChatList(context, requestParam){
    return apiClient.chat.getPsychHomeChatList(requestParam)
  },

  async getPsychChatList(context, requestParam) {
    return apiClient.chat.getPsychChatList(requestParam)
  },

  async markPsychMessagesAsRead(context, requestParam) {
    return apiClient.chat.markPsychMessagesAsRead(requestParam)
  },
}

export default {
  namespaced,
  state,
  getters,
  state, 
  getters, 
  mutations,
  actions,
}