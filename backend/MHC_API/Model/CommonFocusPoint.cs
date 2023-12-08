using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class CommonFocusPoint
    {
        public string PointName { get; set; }
        [Key]
        public int PointID { get; set; }
        public int PointTotal { get; set; }
    }
}
