using System;
using System.Collections.Generic;
using System.Linq;
using System.ComponentModel.DataAnnotations;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class BookingDatePsych
    {
        [Key]
        public DateTime dateNow { get; set; }
        public int psychID { get; set; }
    }
}
