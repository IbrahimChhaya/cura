using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class Patient
    {
        public User patientInfo { get; set; }
        public User guardian { get; set; }
        public DateTime lastAppointment { get; set; }
        public DateTime nextAppointment { get; set; }
        public List<String> focusPoints { get; set; }
    }
}
