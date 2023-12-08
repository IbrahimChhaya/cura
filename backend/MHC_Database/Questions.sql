CREATE TABLE [dbo].[Questions]
(
	[QuestionID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [Question] VARCHAR(MAX) NOT NULL, 
    [TestID] INT NOT NULL,
    [Position] INT NULL,
    [Status]      VARCHAR (50) NULL,
    FOREIGN KEY ([TestID]) REFERENCES [dbo].[Tests](TestID)
)
