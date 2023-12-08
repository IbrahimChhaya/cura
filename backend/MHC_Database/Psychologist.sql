CREATE TABLE [dbo].[Psychologist]
(
	[PsychID] INT NOT NULL PRIMARY KEY, 
    [Address] VARCHAR(MAX) NOT NULL, 
    [Qualification] VARCHAR(MAX) NOT NULL, 
    [RegNumber] VARCHAR(50) NOT NULL, 
    [Description] VARCHAR(MAX) NOT NULL, 
    [Speciality] VARCHAR(250) NOT NULL, 
    [Status] VARCHAR(50) NOT NULL,
    FOREIGN KEY ([PsychID]) REFERENCES [dbo].[User](UserID)
)
