using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class SubmitTestInfo
    {
        public ChildTest TestInfo { get; set; }
        public List<ChildAnswers> Answers { get; set; }
    }
}
