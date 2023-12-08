using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class MoodWeekAvg
    {
        [Key]
        public double WeekEndAvg { get; set; }
        public double WeekDayAvg { get; set; }
        public String Analysis { get; set; }
    }
}
