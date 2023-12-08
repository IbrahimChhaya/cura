using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class Tests
    {
        [Key]
        public int TestID { get; set; }
        public String Name { get; set; }
        public int Total { get; set; }
        public int? PsychID { get; set; }
        public DateTime DateCreated { get; set; }
        public DateTime LastEdited { get; set; }
        public String Status { get; set; }
    }
}
