using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class Link
    {
        [Key]
        public int  LinkID { get; set; }
        public int PairID { get; set; }
        public int PsychID { get; set; }
        public String Status { get; set; }
        public int Rating { get; set; }

        public DateTime? DateLinked { get; set; }
    }
}
