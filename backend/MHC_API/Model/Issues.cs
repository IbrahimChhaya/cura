using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class Issues
    {
        [Key]
        public int IssueID { get; set; }
        public String Name { get; set; }
        public String Status { get; set; }
        public int ChildID { get; set; }
        public int PsychID { get; set; }

    }
}
