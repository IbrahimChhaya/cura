using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class Bookings
    {
        [Key]
        public int BookingID { get; set; }
        public DateTime Date { get; set; }
        public TimeSpan Time { get; set; }
        public string Cancelled { get; set; }
        public string Type { get; set; }
        public int CalendarID { get; set; }
        public int PairID { get; set; }
        public int? NoteID { get; set; }
        public int? ProblemID { get; set; }
        public string MeetingID { get; set; }
        public string MeetingPassword { get; set; }
    }
}
