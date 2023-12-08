using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class PsychologistReg
    {
        //user info
        public String Name { get; set; }
        public String Email { get; set; }
        public String Password { get; set; }
        public DateTime DOB { get; set; }
        public String ProfilePicture { get; set; }

        //Psychologist info
        public string Address { get; set; }
        public string Qualification { get; set; }
        public string RegNumber { get; set; }
        public string Description { get; set; }
        public string Speciality { get; set; }
        public string PsychStatus { get; set; }
    }
}
