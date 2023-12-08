using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class CounsellorReg
    {
        public String Name { get; set; }
        public String Email { get; set; }
        public String Password { get; set; }
        public DateTime DOB { get; set; }
        public String ProfilePicture { get; set; }
        public DateTime DateRegistered { get; set; }

        public string PracticeNum { get; set; }
        public string HighestCertificate { get; set; }
    }
}
