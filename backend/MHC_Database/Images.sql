CREATE TABLE [dbo].[Images]
(
	[ImageID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [FileName] VARCHAR(MAX) NOT NULL, 
    [Description] VARCHAR(MAX) NOT NULL, 
    [ProblemID] INT NOT NULL,
    FOREIGN KEY ([ProblemID]) REFERENCES [dbo].[RHubProblems](ProblemID)
)
