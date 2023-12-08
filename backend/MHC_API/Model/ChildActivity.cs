using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class ChildActivity
    {
        [Key]
        public int ActivityID { get; set; }
        public String ActivityName { get; set; }
        public String FileName { get; set; }
        public DateTime Date { get; set; }
        public int ChildID { get; set; }
        public int? NoteID { get; set; }
        public int? PsychID { get; set; }
    }
}
