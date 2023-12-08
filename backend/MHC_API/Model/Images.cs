using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class Images
    {
        [Key]
        public int ImageID { get; set; }
        public String FileName { get; set; }
        public String Description { get; set; }
        public int ProblemID { get; set; }
    }
}
