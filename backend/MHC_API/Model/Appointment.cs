using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class Appointment
    {
        public Bookings bookingInfo { get; set; }
        public String patientName { get; set; }
    }
}
