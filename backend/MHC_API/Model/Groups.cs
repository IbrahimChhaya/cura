using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class Groups
    {
        [Key]
        public int GroupID { get; set; }
        public String Diagnosis { get; set; }
        public int PsychID { get; set; }
        public int ChildID { get; set; }

    }
}
