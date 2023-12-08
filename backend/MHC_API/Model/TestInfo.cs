using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class TestInfo
    {
        public Tests TestDetails { get; set; }
        public List<Questions> TestQuestions { get; set; }
        public List<Answers> TestAnswers { get; set; }
    }
}
