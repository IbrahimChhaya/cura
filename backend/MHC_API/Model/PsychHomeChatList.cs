using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class PsychHomeChatList
    {
        public User chatterPerson { get; set; }
        public int LinkID { get; set; }
        public Chats lastMessage { get; set; }
        public int nUnreadMessages { get; set; }
    }
}
