using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class CalendarEvent
    {
        public String PatientName { get; set; }
        public DateTime DayTime { get; set; }
        public int pairId { get; set; }
        public String type { get; set; }
        public String Cancelled { get; set; }

    }
}
