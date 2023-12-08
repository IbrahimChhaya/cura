using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class PsychCalendar
    {
        [Key]
        public int PsychCalendarID { get; set; }
        public string DayOfWeek { get; set; }
        public DateTime SingleStart { get; set; }
        public DateTime SingleEnd { get; set; }
        public DateTime RepeatStart { get; set; }
        public DateTime RepeatEnd { get; set; }
        public int PsychID { get; set; }
        public Boolean Closed { get; set; }
    }
}
