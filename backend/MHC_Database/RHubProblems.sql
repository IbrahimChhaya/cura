CREATE TABLE [dbo].[RHubProblems]
(
	[ProblemID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [Problem] VARCHAR(MAX) NOT NULL, 
    [Description] VARCHAR(MAX) NULL, 
    [TitleImage] VARCHAR(MAX) NOT NULL, 
    [Colour] VARCHAR(10) NULL,
)
