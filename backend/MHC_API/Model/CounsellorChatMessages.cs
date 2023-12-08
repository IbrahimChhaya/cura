using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class CounsellorChatMessages
    {
        [Key]
        public int ChatID { get; set; }
        public int CounsellorChatID { get; set; }
        public DateTime Date { get; set; }
        public int SenderID { get; set; }

        [Column(TypeName = "varchar(MAX)")]
        public string Message { get; set; }
        public Boolean Read { get; set; }
    }
}
