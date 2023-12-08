CREATE TABLE [dbo].[RHubUserBridge]
(
	[BridgeID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[ProblemID] INT NOT NULL, 
    [UserID] INT NOT NULL,
	FOREIGN KEY ([ProblemID]) REFERENCES [dbo].[RHubProblems](ProblemID),
	FOREIGN KEY ([UserID]) REFERENCES [dbo].[User](UserID)

)
