using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class Psychologist
    {
        [Key]
        public int PsychID { get; set; }
        public string Address { get; set; }
        public string Qualification { get; set; }
        public string RegNumber { get; set; }
        public string Description { get; set; }
        public string Speciality { get; set; }
        public string Status { get; set; }
    }
}
