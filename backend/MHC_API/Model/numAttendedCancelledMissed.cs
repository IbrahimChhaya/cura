using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class numAttendedCancelledMissed
    {
        [Key]
        public DateTime StartDate { get; set; }
        public DateTime EndDate { get; set; }
        public int Attended { get; set; }
        public int Cancelled { get; set; }
        public int Missed { get; set; }
    }
}
