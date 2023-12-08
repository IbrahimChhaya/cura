CREATE TABLE [dbo].[Counsellor]
(
	[CounsellorID] INT NOT NULL PRIMARY KEY, 
    [PracticeNum] VARCHAR(50) NOT NULL, 
    [HighestCertificate] VARCHAR(50) NOT NULL,
    FOREIGN KEY ([CounsellorID]) REFERENCES [dbo].[User](UserID)
)
