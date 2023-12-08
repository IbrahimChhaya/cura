using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class ChildNote
    {
        public String Feedback { get; set; }
        public DateTime DateCreated { get; set; }
        public String Type { get; set; }
        public int PsychID { get; set; }
        public int ChildID { get; set; }

        //these ids are used to determine where else to store the new note id
        //public int BookingID { get; set; }
        //public int ChildTestID { get; set; }
        //public int ChildActivityID { get; set; }

        public int OtherTableID { get; set; }
    }
}
