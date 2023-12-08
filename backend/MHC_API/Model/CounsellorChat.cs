using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class CounsellorChat
    {
        [Key]
        public int CounsellorChatID { get; set; }
        public int CounsellorID { get; set; }
        public int ChildID { get; set; }
        public int? Focus { get; set; }

    }
}
