CREATE TABLE [dbo].[Recommendations]
(
	[RecID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [recText] VARCHAR(MAX) NOT NULL, 
    [Category] VARCHAR(50) NOT NULL, 
    [PsychID] INT NOT NULL,
    FOREIGN KEY ([PsychID]) REFERENCES [dbo].[Psychologist](PsychID)
)
