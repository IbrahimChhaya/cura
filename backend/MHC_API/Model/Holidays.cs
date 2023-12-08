using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class Holidays
    {
        [Key]
        public String Name { get; set; }
        public DateTime Date { get; set; }

    }
}
