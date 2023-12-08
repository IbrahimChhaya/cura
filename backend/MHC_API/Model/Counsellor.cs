using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class Counsellor
    {
        [Key]
        public int CounsellorID { get; set; }
        public string PracticeNum { get; set; }
        public string HighestCertificate { get; set; }

    }
}
