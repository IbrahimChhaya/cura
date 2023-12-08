CREATE TABLE [dbo].[Groups]
(
	[GroupID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [Diagnosis] VARCHAR(50) NOT NULL, 
    [PsychID] INT NOT NULL, 
    [ChildID] INT NOT NULL,
    FOREIGN KEY ([PsychID]) REFERENCES [dbo].[Psychologist](PsychID),
    FOREIGN KEY ([ChildID]) REFERENCES [dbo].[User](UserID)
)
