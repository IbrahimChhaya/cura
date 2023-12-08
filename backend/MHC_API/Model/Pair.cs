using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class Pair
    {
        [Key]
        public int PairID { get; set; }
        public int ParentID { get; set; }
        public int ChildID { get; set; }
    }
}
