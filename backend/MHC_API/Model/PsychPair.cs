using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class PsychPair
    {
        [Key]
        public int PairID { get; set; }
        public int PsychID { get; set; }
    }
}
