using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class ChatInfo
    {
        public int PractitionerID { get; set; }
        public int UserID { get; set; }
        public string MessageContent { get; set; }
        public int SenderID { get; set; }
    }
}
