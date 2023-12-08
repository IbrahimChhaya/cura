using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class PsychTestListItem
    {
        public Tests test { get; set; }
        public int numQuestions { get; set; }
        public int responses { get; set; }
    }
}
