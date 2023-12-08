using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class TestQuestionsAnswers
    {
        [Key]
        public Tests Test { get; set; }
        public List<QuestionAnswers> QuestionAnswers { get; set; }
    }
}
