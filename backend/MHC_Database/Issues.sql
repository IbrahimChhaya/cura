CREATE TABLE [dbo].[Issues]
(
	[IssueID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [Name] VARCHAR(50) NOT NULL, 
    [Status] VARCHAR(50) NOT NULL, 
    [ChildID] INT NOT NULL, 
    [PsychID] INT NOT NULL,
    FOREIGN KEY ([ChildID]) REFERENCES [dbo].[User](UserID),
    FOREIGN KEY ([PsychID]) REFERENCES [dbo].[Psychologist](PsychID)
)
