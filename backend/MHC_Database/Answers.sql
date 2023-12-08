CREATE TABLE [dbo].[Answers]
(
	[AnswerID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [Answer] VARCHAR(MAX) NOT NULL, 
    [AnswerValue] INT NOT NULL, 
    [QuestionID] INT NOT NULL,
    [Position] INT NULL,
    FOREIGN KEY ([QuestionID]) REFERENCES [dbo].[Questions](QuestionID)
)
