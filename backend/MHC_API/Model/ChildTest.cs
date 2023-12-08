using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class ChildTest
    {
        [Key]
        public int ChildTestID { get; set; }
        public DateTime Date { get; set; }
        public String Status { get; set; }
        public int ChildID { get; set; }
        public int TestID { get; set; }
        public int? NoteID { get; set; }
    }
}
