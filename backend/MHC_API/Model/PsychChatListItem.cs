using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class PsychChatListItem
    {
        public int psychID { get; set; }
        public String psychName { get; set; }
        public Chats lastMessage { get; set; }
        public String profilePicture { get; set; }
    }
}
