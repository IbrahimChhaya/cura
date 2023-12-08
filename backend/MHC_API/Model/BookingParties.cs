using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class BookingParties
    {
        public User patient { get; set; }
        public PsychologistInfo psychologist { get; set; }
    }
}
