/*
Post-Deployment Script Template							
--------------------------------------------------------------------------------------
 This file contains SQL statements that will be appended to the build script.		
 Use SQLCMD syntax to include a file in the post-deployment script.			
 Example:      :r .\myfile.sql								
 Use SQLCMD syntax to reference a variable in the post-deployment script.		
 Example:      :setvar TableName MyTable							
               SELECT * FROM [$(TableName)]					
--------------------------------------------------------------------------------------
*/

     /*RHubProblems*/

UPDATE [dbo].[RHubProblems]
SET TitleImage =  N'social_anxiety.png'     /*Anxiety*/
WHERE ProblemID = 5;

UPDATE [dbo].[RHubProblems]
SET TitleImage =  N'depression.png'     /*Depression*/
WHERE ProblemID = 9;

UPDATE [dbo].[RHubProblems]
SET TitleImage =  N'ocd.png'     /*OCD*/
WHERE ProblemID = 12;

UPDATE [dbo].[RHubProblems]
SET TitleImage =  N'bipolar.png'     /*Bipolar*/
WHERE ProblemID = 8;

UPDATE [dbo].[RHubProblems]
SET TitleImage =  N'anorexia.png'     /*Insecurity*/
WHERE ProblemID = 19;

UPDATE [dbo].[RHubProblems]
SET TitleImage =  N'abuse.png'     /*Abuse*/
WHERE ProblemID = 20;

UPDATE [dbo].[RHubProblems]
SET TitleImage =  N'bullying.png'     /*Bullying*/
WHERE ProblemID = 18;

UPDATE [dbo].[RHubProblems]
SET TitleImage =  N'ptsd.png'     /*PTSD*/
WHERE ProblemID = 14;

UPDATE [dbo].[RHubProblems]
SET TitleImage =  N'eating.png'     /*Eating disorder*/
WHERE ProblemID = 11;

UPDATE [dbo].[RHubProblems]
SET TitleImage =  N'adhd.png'     /*ADHD*/
WHERE ProblemID = 17;
     
     /*Images*/

UPDATE [dbo].[Images]
SET [FileName] =  N'social_anxiety.png'     /*Anxiety*/
WHERE ProblemID = 5;

UPDATE [dbo].[Images]
SET [FileName] =  N'depression.png'     /*Depression*/
WHERE ProblemID = 9;

UPDATE [dbo].[Images]
SET [FileName] =  N'ocd.png'     /*OCD*/
WHERE ProblemID = 12;

UPDATE [dbo].[Images]
SET [FileName] =  N'bipolar.png'     /*Bipolar*/
WHERE ProblemID = 8;

UPDATE [dbo].[Images]
SET [FileName] =  N'anorexia.png'     /*Insecurity*/
WHERE ProblemID = 19;

UPDATE [dbo].[Images]
SET [FileName] =  N'abuse.png'     /*Abuse*/
WHERE ProblemID = 20;

UPDATE [dbo].[Images]
SET [FileName] =  N'bullying.png'     /*Bullying*/
WHERE ProblemID = 18;

UPDATE [dbo].[Images]
SET [FileName] =  N'ptsd.png'     /*PTSD*/
WHERE ProblemID = 14;

UPDATE [dbo].[Images]
SET [FileName] =  N'eating.png'     /*Eating disorder*/
WHERE ProblemID = 11;

UPDATE [dbo].[Images]
SET [FileName] =  N'adhd.png'     /*ADHD*/
WHERE ProblemID = 17;