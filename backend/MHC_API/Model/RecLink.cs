using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class RecLink
    {
        [Key]
        public int recLinkID { get; set; }
        public int RecID { get; set; }
        public int UserID { get; set; }
    }
}
