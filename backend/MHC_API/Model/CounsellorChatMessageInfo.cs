using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class CounsellorChatMessageInfo
    {
        public int CounsellorChatID { get; set; }
        public int CounsellorID { get; set; }
        public int ChildID { get; set; }
        public DateTime Date { get; set; }
        public int SenderID { get; set; }
        public string Message { get; set; }
        public Boolean Read { get; set; }
    }
}
