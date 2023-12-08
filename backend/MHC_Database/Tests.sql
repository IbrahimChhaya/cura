CREATE TABLE [dbo].[Tests]
(
	[TestID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [Name] VARCHAR(50) NOT NULL, 
    [Total] INT NOT NULL, 
    [PsychID] INT NULL,
    [DateCreated] DATETIME     NULL,
    [LastEdited]  DATETIME     NULL,
    [Status]      VARCHAR (50) NULL,
    FOREIGN KEY ([PsychID]) REFERENCES [dbo].[Psychologist](PsychID)
)
