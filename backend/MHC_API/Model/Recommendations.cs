using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class Recommendations
    {
        [Key]
        public int RecID { get; set; }
        public String recText { get; set; }
        public String Category { get; set; }
        public int PsychID { get; set; }
    }
}
