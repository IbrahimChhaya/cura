CREATE TABLE [dbo].[ChildAnswers]
(
	[ChildAnswerID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[ChildTestID] INT NOT NULL, 
    [AnswerID] INT NOT NULL,
	FOREIGN KEY ([ChildTestID]) REFERENCES [dbo].[ChildTest](ChildTestID),
	FOREIGN KEY ([AnswerID]) REFERENCES [dbo].[Answers](AnswerID)
)
