using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class RHubProblems
    {
        [Key]
        public int ProblemID { get; set; }
        public String Problem { get; set; }
        public string Description { get; set; }
        public string TitleImage { get; set; }
        public string Colour { get; set; }
    }
}
