export default function ChatClient(instance){
  this.apiInstance = instance


  this.assignChatFocus = (params) => {
    return this.apiInstance.post('/Chats/assignChatFocus', params).then((resp) => {
      return resp.data
    }).catch((error) => {
      return -4
    })
  }

  this.getCounsellorChatList = (params) => {
    return this.apiInstance.get(`/Chats/getCounsellorChatList/${params.counsellorID}`).then((resp) => {
      return resp.data
    }).catch((error) => {
      return []
    })
  }

  this.getCurrentChatMessages = (params) => {
    return this.apiInstance.post('/Chats/getCounsellingChatMessages', params).then((resp) => {
       return resp.data
    }).catch(() => {
      return []
    })
  }

  this.getChatHistory = (params) => {
    return this.apiInstance.post('/Chats/getChatMessages', params).then((resp) => {
      return resp.data
    }).catch(() => {
      return []
    })
  }

  this.saveCounsellorChatMessage = (param) => {
    return this.apiInstance.post('/Chats/saveCounsellorChatMessage', param).then((resp) => {
      return resp.data
    }).catch(() => {
      return {}
    })
  }

  this.saveMessage = (params) => {
    return this.apiInstance.post('/Chats/sendMessage', params).then((resp) => {
      return resp.data
    }).catch(() => {
      return {}
    })
  }


  this.markMessagesAsRead = (param) => {
    return this.apiInstance.post('/Chats/markMessagesAsRead', param).then((resp) => {
     return resp.data
    }).catch(() => {
      return []
    })
  }
  
  this.getPsychHomeChatList = (param) => {
    return this.apiInstance.get(`/Chats/getPsychHomeChatList/${param.psychID}`).then((resp) => {
      return resp.data
    }).catch(() => {
      return []
    })
  }

  this.getPsychChatList = (param) => {
    return this.apiInstance.get(`/Chats/getPsychChatList/${param.psychID}`).then((resp) => {
      return resp.data
    }).catch(() => {
      return []
    })
  }

  this.markPsychMessagesAsRead = (param) => {
    return this.apiInstance.post('/Chats/markPsychChatMessagesAsRead', param).then((resp) => {
      return resp.data
    }).catch(() => {
      return []
    })
  }
}