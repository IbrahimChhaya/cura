using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class BookingInfo
    {
        public DateTime date { get; set; }
       // public TimeSpan time { get; set; }
       // public String type { get; set; }
        public int calendarID { get; set; }
        public int userId { get; set; }

        public int PsychID { get; set; }
    }
}
