using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class ToDo
    {
        [Key]
        public int ToDoID { get; set; }
        public String Status { get; set; }
        public int IssueID { get; set; }
        public int? NoteID { get; set; }
    }
}
