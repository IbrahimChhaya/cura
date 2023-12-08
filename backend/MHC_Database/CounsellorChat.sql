CREATE TABLE [dbo].[CounsellorChat]
(
	[CounsellorChatID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [CounsellorID] INT NOT NULL, 
    [ChildID] INT NOT NULL, 
    [Focus] INT NULL,
    FOREIGN KEY ([CounsellorID]) REFERENCES [dbo].[Counsellor](CounsellorID),
    FOREIGN KEY ([ChildID]) REFERENCES [dbo].[User](UserID),
    FOREIGN KEY ([Focus]) REFERENCES [dbo].[RhubProblems](ProblemID)
)
