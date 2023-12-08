using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class KeywordsScript
    {
        [Key]
        public int ScriptID { get; set; }
        public String Keyword { get; set; }
        public String Answers { get; set; }
    }
}
