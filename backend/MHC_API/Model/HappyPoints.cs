using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class HappyPoints
    {
        [Key]
        public int ChildID { get; set; }
        public int Points { get; set; }
    }
}
