using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class AssignedTest
    {
        public int ChildTestID { get; set; }
        public Tests TestAssigned { get; set; }
    }
}
