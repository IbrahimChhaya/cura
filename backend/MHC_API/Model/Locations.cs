using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class Locations
    {
        [Key]
        public int LocationID { get; set; }
        public String Facility { get; set; }
        public String Type { get; set; }
        public String Location { get; set; }
    }
}
