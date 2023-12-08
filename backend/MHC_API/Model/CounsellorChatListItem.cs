using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class CounsellorChatListItem
    {
        public User ChatterPerson { get; set; }
        public CounsellorChatMessages LastMessage { get; set; }
        public int nUnreadMessages { get; set; }
        public RHubProblems ChatFocus { get; set; }
        public int CounsellorChatID { get; set; }
    }
}
