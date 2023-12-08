using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class PsychologistInfo
    {
        //personal information
        public int UserID { get; set; }
        public String Name { get; set; }
        public String Email { get; set; }
        public String ProfilePicture { get; set; }
        public DateTime DateRegistered { get; set; }
        public String Status { get; set; }

        //practice details
        public int PsychID { get; set; }
        public string Address { get; set; }
        public string Qualification { get; set; }
        public string RegNumber { get; set; }
        public string Description { get; set; }
        public string Speciality { get; set; }
    }

}
