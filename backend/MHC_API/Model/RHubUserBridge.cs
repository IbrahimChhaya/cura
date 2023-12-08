using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class RHubUserBridge
    {
        [Key]
        public int BridgeID { get; set; }
        public int ProblemID { get; set; }
        public int UserID { get; set; }
    }
}
