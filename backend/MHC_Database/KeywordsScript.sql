CREATE TABLE [dbo].[KeywordsScript]
(
	[ScriptID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [Keyword] VARCHAR(50) NOT NULL, 
    [Answers] VARCHAR(MAX) NULL 
)
