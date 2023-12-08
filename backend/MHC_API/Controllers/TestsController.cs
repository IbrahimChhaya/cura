using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Threading.Tasks;
using MHC_API.Data;
using MHC_API.Model;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace MHC_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TestsController : ControllerBase
    {
        private MHCDatabaseDBContext db;
        public TestsController(MHCDatabaseDBContext db)
        {
            this.db = db;
        }

        //Function to get a test's answers
        [HttpPost("getTestAnswers")]
        public List<Answers> getTestAnswers(Tests test)
        {
            List<Answers> testAnswers = new List<Answers>();

            //find all the tests questions
            var testQuestions = getTestQuestions(test);

            if(testQuestions != null)
            {
                //loop through each question and fetch answers with matching question ID
                foreach(Questions q in testQuestions)
                {
                    //find answer with matching question id
                    var answers = db.Answers.Where(a => a.QuestionID.Equals(q.QuestionID));

                    if(answers.Any())
                    {
                        List<Answers> orderedAnswerList = answers.OrderBy(answers => answers.Position).ToList();
                        foreach (Answers a in orderedAnswerList)
                        {
                            testAnswers.Add(a);
                        }
                    }
                }
                

                return testAnswers;//testAnswers.OrderBy(Answers => Answers.Position).ToList();
            }
            else
            {
                return null; //no questions found, therefore there are no answers
            }
        }

        //Function to get a test's answers
        [HttpGet("getQuestionAnswers/{questionID}")]
        public List<Answers> getQuestionAnswers(int questionID)
        {
            List<Answers> testAnswers = new List<Answers>();

            //find answer with matching question id
            var answers = db.Answers.Where(a => a.QuestionID.Equals(questionID));

            if (answers.Any())
            {
                foreach (Answers a in answers)
                {
                    testAnswers.Add(a);
                }
            }
            List<Answers> sortedTestAnswers = testAnswers.OrderBy(answers => answers.Position).ToList();

            return sortedTestAnswers;
        }

        //Function to get a test's questions
        [HttpPost("getTestQuestions")]
        public List<Questions> getTestQuestions(Tests test)
        {
            List<Questions> testQuestions = new List<Questions>();

            var questions = db.Questions.Where(q => q.TestID.Equals(test.TestID));

            if(questions.Any())
            {
                //questions were found for the given test
                foreach(Questions tq in questions)
                {
                    testQuestions.Add(tq);
                }
                List<Questions> orderedQuestionList = testQuestions.OrderBy(questions => questions.Position).ToList();

                return orderedQuestionList;//testQuestions.OrderBy(questions => questions.Position).ToList();
            }
            else
            {
                return null; //no questions found for given test
            }
        }

        //Function to fetch a test (With it's questions and answers)
        [HttpPost("fetchCompleteTest")]
        public TestInfo fetchTest(Tests test)
        {
            TestInfo testToReturn = new TestInfo();
            //fetch test details
            var testDetails = getTest(test.TestID);

            if(testDetails != null)
            {
                testToReturn.TestDetails = testDetails;

                //get test questions
                var questions = getTestQuestions(testDetails);

                if(questions != null)
                {
                    testToReturn.TestQuestions = questions;

                    //get test answers
                    var answers = getTestAnswers(testDetails);

                    if(answers != null)
                    {
                        testToReturn.TestAnswers = answers;
                    }
                }
                else
                {
                    //no questions, therefore no answers
                    testToReturn.TestQuestions = null;
                    testToReturn.TestAnswers = null;
                }

                return testToReturn;
            }
            else
            {
                return null; //test does not exist
            }

        }

        //Function to get only test details, given a test's id
        [HttpGet("getTest/{testId}")]
        public Tests getTest(int testId)
        {
            var test = db.Tests.Where(t => t.TestID.Equals(testId)).FirstOrDefault();

            if(test != null)
            {
                return test;
            }
            else 
            { 
                return null;    //test not found
            
            }
        }

        //Function to get a list of all ChildTests by child id and psych id
        [HttpPost("fetchAllChildPsychTests")]
        public List<ChildTest> fetchAllChildPsychTests(PsychPatientPair psyPat)
        {
            List<ChildTest> childTests = new List<ChildTest>();

            //get all child tests
            List<ChildTest> allChildTests = getAllChildTestsCompleted(psyPat.ChildID);

            if(allChildTests != null)
            {
                //get all tests assigned by the psychologist
                List<Tests> regularTests = getPsychTests(psyPat.PsychID);


                //check for mood tracker test (psych ID = 0)
                Tests moodTracker = getMoodTrackerTest();

                if (moodTracker != null)
                {
                    //make sure that the psychologist tests is not null
                    if (regularTests != null)
                    {
                        regularTests.Add(moodTracker);

                        //loop through tests
                        foreach(Tests t in regularTests)
                        {
                            List<ChildTest> tempTests = allChildTests.FindAll(
                                delegate (ChildTest test) 
                                {
                                    return test.TestID == t.TestID;
                                });
                            if(tempTests.Any())
                            {
                                if (childTests.Count() != 0)
                                    childTests.AddRange(tempTests);
                                else
                                    childTests = tempTests;
                            }
                        }
                    }
                    else
                    {
                        //just look for mood tracker tests
                        List<ChildTest> moodTests = allChildTests.FindAll(
                                delegate (ChildTest test)
                                {
                                    return test.TestID == moodTracker.TestID;
                                });

                        if(moodTests.Any())
                        {
                            childTests = moodTests;
                        }
                    }

                    childTests.Sort((x, y) => y.Date.CompareTo(x.Date));    //sort tests according to date
                    return childTests;
                }
                else
                {
                    //no mood tracker test completed and none of the tests completed were completed with the specified psych
                    return null;
                }
            }
            else
            {
                //child has not completed any tests yet
                return null;
            }
        }

        [HttpPost("getTestsDoneWithPsych")]
        public List<CompletedTestInfo> getTestsDoneWithPsych(PsychPatientPair psychPatientPair)
        {
            List<CompletedTestInfo> testInfo = new List<CompletedTestInfo>();

            //
            List<ChildTest> childPsychTest = fetchAllChildPsychTests(psychPatientPair);

            if(childPsychTest != null)
            {
                foreach(ChildTest cTest in childPsychTest)
                {
                    //find the test with matching id
                    var test = db.Tests.Where(t => t.TestID.Equals(cTest.TestID)).FirstOrDefault();

                    if(test != null)
                    {
                        var tempInfo = new CompletedTestInfo
                        {
                            ChildTestID = cTest.ChildTestID,
                            testName = test.Name,
                            dateCompleted = cTest.Date
                        };

                        testInfo.Add(tempInfo);
                    }
                }

                return testInfo;
            }
            else
            {
                //nothing found
                return null;
            }
        }

        //function to get all tests completed by a child
        [HttpGet("getAllChildTestsCompleted/{childId}")]
        public List<ChildTest> getAllChildTestsCompleted(int childId)
        {
            List<ChildTest> childTests = new List<ChildTest>();

            var tests = db.ChildTest.Where(t => t.ChildID.Equals(childId) && t.Status.Equals("Completed"));

            if(tests.Any())
            {
                foreach(ChildTest t in tests)
                {
                    childTests.Add(t);
                }

                return childTests;
            }
            else
            {
                return null;
            }

        }

        //function to get all tests assigned to a child but not yet completed
        [HttpGet("getAllChildTestsAssigned/{childId}")]
        public List<AssignedTest> getAllChildTestsAssigned(int childId)
        {
            List<ChildTest> childTests = new List<ChildTest>();
            List<AssignedTest> assignedTests = new List<AssignedTest>();

            var tests = db.ChildTest.Where(t => t.ChildID.Equals(childId) && t.Status.Equals("Assigned")).ToList();
            var actualTests = (from at in db.Tests
                               select at);


            if (tests.Any())
            {
                /*childTests = tests;*/
                foreach(ChildTest ct in tests)
                {
                    foreach(Tests t in actualTests)
                    {
                        if(t.TestID.Equals(ct.TestID))
                        {
                            //create  and add assigned test object
                            AssignedTest temp = new AssignedTest
                            {
                                ChildTestID = ct.ChildTestID,
                                TestAssigned = t
                            };

                            assignedTests.Add(temp);
                        }
                    }
                }
                return assignedTests;
                //return childTests;
            }
            else
            {
                return null;    //no tests found with status "assigned"
            }

        }

        //utility function: get all the tests that were assigned by a psychologist
        [HttpGet("getPsychTests/{psychID}")]
        public List<Tests> getPsychTests(int? psychId)
        {
            List<Tests> psychTests = new List<Tests>();

            //var tests = db.Tests.Where(t => t.PsychID.Equals(psychId));

            var tests = (from t in db.Tests
                         where t.PsychID.Equals(psychId) || (t.PsychID == null)
                         select t);
                         //where t.PsychID.Equals(psychId) || t.PsychID.Equals(null)
                         //select t);


            if (tests.Any())
            {
                List<Tests> orderedTestList = tests.OrderBy(tests => tests.LastEdited).ToList();
                foreach (Tests t in orderedTestList)
                {
                    psychTests.Add(t);
                }

                //psychTests.Sort((x, y) => DateTime.Compare(y.LastEdited, x.LastEdited));
                //env.OrderByDescending(x => x.ReportDate)


                return psychTests;
            }
            else
                return null;
        }

        //Function to submit a test 
        /*
            If mood tracker test - give ChildID, TestID, and List of answers
            If any other test - give ChildTestID, and list of answers
         */
        [HttpPost("submitTest")]
        public int submitTest(SubmitTestInfo info)
        {
            ChildTest testToSubmit;
            //check if a child Test ID was given
            if(info.TestInfo.ChildTestID > 0)
            {
                //fetch the existing test
                testToSubmit = db.ChildTest.Where(ct => ct.ChildTestID.Equals(info.TestInfo.ChildTestID)).FirstOrDefault();

                if(testToSubmit != null)
                {
                    testToSubmit.Date = DateTime.Now;
                    testToSubmit.Status = "Completed";
                }
            }
            else
            {
                //create new ChildTest (for moodTracker)
                testToSubmit = new ChildTest
                {
                    Date = DateTime.Now,
                    Status = "Completed",
                    ChildID = info.TestInfo.ChildID,
                    TestID = info.TestInfo.TestID
                };
                db.ChildTest.Add(testToSubmit);

            }

            try
            {
                db.SaveChanges();
            }
            catch (Exception ex)
            {
                ex.GetBaseException();
                return -1; //Something went wrong creating/saving the child test submission
            }


            //save child answers
            foreach (ChildAnswers a in info.Answers)
            {
                var tempAnswer = new ChildAnswers
                {
                    ChildTestID = testToSubmit.ChildTestID,
                    AnswerID = a.AnswerID
                };
                db.ChildAnswers.Add(tempAnswer);
            }

            try
            {
                db.SaveChanges();
            }
            catch (Exception ex)
            {
                ex.GetBaseException();
                return -2; //Something went wrong creating the child's answers
            }

            return 0; //everything successfully saved
        }

        //utility method: get a test's answer by id
        private Answers getTestAnswer(int answerID)
        {
            var answer = db.Answers.Where(a => a.AnswerID.Equals(answerID)).FirstOrDefault();

            return answer; //will be null if not found
        }

        //get child's answers to a test - given test id, child id (equivalent to view test response)
        [HttpGet("getChildTestAnswersWithID/{childTestID}")]
        public List<Answers> getChildTestAnswersWithID(int childTestID)
        {
            List<Answers> childAnswers = new List<Answers>();

            if (childTestID != 0)
            {
                var answers = db.ChildAnswers.Where(a => a.ChildTestID.Equals(childTestID));

                if (answers != null)
                {
                    foreach (ChildAnswers c in answers)
                    {
                        //get answer from answerID
                        var tempAnswer = getTestAnswer(c.AnswerID);

                        if (tempAnswer != null)
                        {
                            childAnswers.Add(tempAnswer);
                        }
                    }

                    List<Answers> sortedchildAnswers = childAnswers.OrderBy(answers => answers.Position).ToList();


                    return sortedchildAnswers;
                }
                else
                {
                    return null; //no answers found
                }
            }
            else
            {
                return null; //child has not submitted any responses yet
            }
        }

        //get child's answers to a test - given test id, child id (equivalent to view test response)
        [HttpPost("getChildTestAnswers")]
        public List<Answers> getChildTestAnswers(ChildTest test)
        {
            List<Answers> childAnswers = new List<Answers>();

            var childTestID = (from t in db.ChildTest
                               where t.ChildID.Equals(test.ChildID) && t.TestID.Equals(test.TestID)
                               select t.ChildTestID).FirstOrDefault();

            if(childTestID != 0)
            {
                var answers = db.ChildAnswers.Where(a => a.ChildTestID.Equals(childTestID));

                if(answers != null)
                {
                    foreach(ChildAnswers c in answers)
                    {
                        //get answer from answerID
                        var tempAnswer = getTestAnswer(c.AnswerID);

                        if(tempAnswer != null)
                        {
                            childAnswers.Add(tempAnswer);
                        }
                    }

                    return childAnswers;
                }
                else
                {
                    return null; //no answers found
                }
            }
            else
            {
                return null; //child has not submitted any responses yet
            }
        }

        //utility method: get list of answer IDs from ChildAnswers
        private List<ChildAnswers> getChildAnswers(int childTestID)
        {
            List<ChildAnswers> childAnswers = new List<ChildAnswers>();

            var answers = db.ChildAnswers.Where(a => a.ChildTestID.Equals(childTestID));

            if(answers.Any())
            {
                foreach(ChildAnswers c in answers)
                {
                    childAnswers.Add(c);
                }
            }

            return childAnswers;

        }

        //utility method: change ChildAnswer
        private void changeAnswer(ChildAnswers oldAnswer, ChildAnswers newAnswer)
        {
            var childAnswer = db.ChildAnswers.Where(a => a.ChildTestID.Equals(oldAnswer.ChildTestID) && a.AnswerID.Equals(oldAnswer.AnswerID)).FirstOrDefault();

            if( childAnswer != null)
            {
                childAnswer.AnswerID = newAnswer.AnswerID;

                try
                {
                    db.SaveChanges(); //successfully saved
                }
                catch (Exception ex)
                {
                    ex.GetBaseException(); //couldn't save changed
                }

            }
        }

        //edit test response - same as submit but check ChildTestID
        [HttpPost("editTest")]
        public int editTest(SubmitTestInfo info)
        {
            var test = db.ChildTest.Where(t => t.ChildTestID.Equals(info.TestInfo.ChildTestID)).FirstOrDefault();

            if(test != null)
            {
                List<ChildAnswers> prevAnswers = getChildAnswers(test.ChildTestID);

                if(prevAnswers != null)
                {
                    for(int i = 0; i < prevAnswers.Count; i++)
                    {
                        changeAnswer(prevAnswers[i], info.Answers[i]);
                    }

                    

                    return 0; //all changes saved
                }
                else
                {
                    return -3; //no answers found
                }
            }
            else
            {
                //test was never saved/submitted
                return -1;
            }
        }

        //Utility Function: fetch mood tracker test
        private Tests getMoodTrackerTest()
        {
            return db.Tests.Where(t => t.PsychID == null).FirstOrDefault();
        }

        //monthly mood trend - given child id
        [HttpGet("getMonthlyMoodTrend/{childID}")]
        public List<MoodResult> getMonthlyMoodTrend(int childID) 
        {
            List<DateTime> monthDates = getMonthDates();

            Tests moodTracker = getMoodTrackerTest();

            List<MoodResult> monthlyMood = new List<MoodResult>();

            foreach(DateTime date in monthDates)
            {
                var answerValue = getMoodAnswerValue(date, childID, moodTracker.TestID);

                MoodResult tempResult = new MoodResult
                {
                    date = date,
                    moodValue = answerValue
                };

                monthlyMood.Add(tempResult);
            }

            return monthlyMood;

        }

        //Function to get a monthly mood average
        [HttpGet("getMonthlyMoodAverage/{childId}")]
        public double getMonthlyMoodAverage(int childId)
        {
            List<MoodResult> monthlyValues = getMonthlyMoodTrend(childId);
            //int count = 0;

            //foreach(MoodResult r in monthlyValues)
            //{
            //    count += r.moodValue;
            //}

            //double average = count / monthlyValues.Count();

            //return (int)Math.Ceiling(average
            //

            double average =  monthlyValues.Average(v => v.moodValue);

            return Math.Ceiling(average);
        }

        //Function to get a monthly mood average
        [HttpGet("getYearlyMoodAverage/{childID}")]
        public List<MonthMoodAverage> getYearlyMoodAverage(int childID)
        {
            List<MonthMoodAverage> moodValuesPerMonth = new List<MonthMoodAverage>();

            List<int> months = new List<int>(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });

            foreach (var month in months)
            {
                var monthStr = new DateTime(DateTime.Now.Year, month, 1).ToString("MMM", CultureInfo.InvariantCulture);
                moodValuesPerMonth.Add(new MonthMoodAverage { month = monthStr, moodAvg = monthlyMoodAvg(childID, month) });
            }

            return moodValuesPerMonth;
        }

        private double monthlyMoodAvg(int childID, int month)
        {
            Tests moodTracker = getMoodTrackerTest();
            double avg = 0;
            int count = 0;

            var result = (from c in db.ChildTest
                          where c.ChildID.Equals(childID) && c.Date.Date.Month.Equals(month) && c.Date.Date.Year == DateTime.Now.Year && c.TestID.Equals(moodTracker.TestID)
                          select c).ToList();

            foreach(ChildTest ct in result)
            {
                count++;
                var answerValue = getMoodAnswerValue(ct.Date, childID, moodTracker.TestID);
                avg += answerValue;
            }

            if (count != 0)
                avg = (avg / (double) count);
            
            return Math.Round(avg, 2);
        }

        //mood trend weekly - current date as the last day in the week
        //requires child id
        [HttpGet("getWeeklyMoodTrend/{childID}")]
        public List<MoodResult> getWeeklyMoodTrend(int childID)
        {
            List<DateTime> weekDates = getWeekDates();

            Tests moodTracker = getMoodTrackerTest();

            List<MoodResult> weeklyMood = new List<MoodResult>();

            foreach(DateTime d in weekDates)
            {
                var answerValue = getMoodAnswerValue(d, childID, moodTracker.TestID);

                MoodResult temp = new MoodResult
                {
                    date = d,
                    moodValue = answerValue
                };

                weeklyMood.Add(temp);
            }

            return weeklyMood;
        }


        //method to get the number of times mood was logged in th past week (Requires ChildID)
        [HttpGet("getTotalMoodLogsPerWeek/{childID}")]
        public int getTotalMoodLogsPerWeek(int childID)
        {
            //the number of mood logs
            int numLogs = 0;
            List<DateTime> weekDates = getWeekDates();

            Tests moodTracker = getMoodTrackerTest();

            List<MoodResult> weeklyMood = new List<MoodResult>();

            foreach (DateTime d in weekDates)
            {
                var answerValue = getMoodAnswerValue(d, childID, moodTracker.TestID);

                MoodResult temp = new MoodResult
                {
                    date = d,
                    moodValue = answerValue

                };

                weeklyMood.Add(temp);
            }
            foreach(MoodResult mr in weeklyMood)
            {
                if(mr.moodValue > 0)
                {
                    numLogs++;
                }
            }

            return numLogs;
        }

        //method to get the average mood in the past week (Requires ChildID)
        [HttpGet("getAverageMoodPerWeek/{childID}")]
        public double getAverageMoodPerWeek(int childID)
        {
            //the number of mood logs
            int numLogs = 0;
            int totalMoodValue = 0;
            double moodAverage = 0.0;

            List<DateTime> weekDates = getWeekDates();

            Tests moodTracker = getMoodTrackerTest();

            List<MoodResult> weeklyMood = new List<MoodResult>();

            foreach (DateTime d in weekDates)
            {
                var answerValue = getMoodAnswerValue(d, childID, moodTracker.TestID);

                MoodResult temp = new MoodResult
                {
                    date = d,
                    moodValue = answerValue

                };

                weeklyMood.Add(temp);
            }
            foreach (MoodResult mr in weeklyMood)
            {
                totalMoodValue += mr.moodValue;
                //checking if the mood was logged
                if(mr.moodValue >0)
                {
                    numLogs++;
                }
               
              
            }
            //only divide by a nonzero numlogs
            if(numLogs > 0)
            {
                moodAverage = totalMoodValue/numLogs;
            }else
            {
                moodAverage = 0;
            }

            //rounding up the mood average
            moodAverage = Math.Ceiling(moodAverage);
            return moodAverage;
        }


        //utility method: return mood answer value, given a date, child id and test id
        private int getMoodAnswerValue(DateTime date, int childId, int testId)
        {
            //find the child test that matches the given parameters
            var cTest = (from c in db.ChildTest
                         where c.ChildID.Equals(childId) && c.Date.Date.Equals(date.Date) && c.TestID.Equals(testId)
                         select c).FirstOrDefault();

            if(cTest != null)
            {
                var answerChild = db.ChildAnswers.Where(a => a.ChildTestID.Equals(cTest.ChildTestID)).FirstOrDefault();

                if(answerChild != null)
                {
                    //get the Answer id from the child answer
                    Answers answer = getTestAnswer(answerChild.AnswerID);
                    return answer.AnswerValue;
                    //return  answer[0].AnswerValue;
                }
            }

            return 0;  //no test found
        }

        //utility method: get dates for the past week
        private List<DateTime> getWeekDates()
        {
            List<DateTime> dates = new List<DateTime>();
            DateTime currentDate = DateTime.Now.Date;
            dates.Add(currentDate);
            DateTime endDate = currentDate;

            for (int i = 6; i > 0; i--)
            {
                DateTime prevDate = endDate.AddDays(-1); //minus 1 day from current date to get next date
                dates.Add(prevDate);

                endDate = prevDate;

            }

            dates.Sort((x, y) => x.CompareTo(y));

            return dates;
        }

        //utility method: get month dates, given a date
        private List<DateTime> getMonthDates()
        {
            List<DateTime> monthDates = new List<DateTime>();
            DateTime date = DateTime.Now.Date;

            int month = date.Month;
            //get first day of month
            DateTime startDate = new DateTime(date.Year, month, 1);
            //monthDates.Add(startDate);

            while(startDate.Month == month)
            {
                monthDates.Add(startDate);
                startDate = startDate.AddDays(1);
            }

            return monthDates;
        }


        //Function to check whether a child has logged their mood for the day
        //parameters: date, test id and child id
        [HttpPost("checkIfMoodWasLoggedToday")]
        public ChildTest checkIfMoodWasLoggedToday(ChildTest test)
        {
            //get child Test with matching date, test id and child id
            var cTest = (from t in db.ChildTest
                         where t.Date.Date.Equals(test.Date) && t.TestID.Equals(test.TestID) && t.ChildID.Equals(test.ChildID)
                         select t).FirstOrDefault();

            if (cTest != null)
                return cTest;   //completed mood log for the day
            else
                return null;  //mood log not yet completed
        }

        //utility method: get all answers to mood tracker
        private List<Answers> getMoodTrackerAnswers(int testId)
        {
            Tests moodTracker = getMoodTrackerTest();

            List<Answers> moodAnswers = new List<Answers>();

            if(moodTracker != null)
            {
                //get the question id for mood tracker
                var question = getTestQuestions(moodTracker);

                if(question != null)
                {
                    //get all the answers linked to this question
                    var answers = db.Answers.Where(a => a.QuestionID.Equals(question[0].QuestionID));

                    foreach(Answers a in answers)
                    {
                        moodAnswers.Add(a);
                    }
                }
            }

            return moodAnswers;
        }

        //Function to assign a test to a child - given psych ID and child ID
        [HttpPost("assignTestToPatient")]
        public ChildTest assignTestToPatient(TestAssignmentInfo info)
        {
            //check if the child is actually psych's patient
            UserController usrController = new UserController(db);
            int pairId = usrController.getPairId(info.ChildID);

            if(pairId != 0)
            {
                var linked = db.Link.Where(l => l.PairID.Equals(pairId)).FirstOrDefault();

                if(linked != null)
                {
                    var newTest = new ChildTest
                    {
                        Date = DateTime.Now,
                        Status = "Assigned",
                        ChildID = info.ChildID,
                        TestID = info.TestID
                    };

                    db.ChildTest.Add(newTest);

                    try
                    {
                        db.SaveChanges();
                        return newTest;
                    }
                    catch(Exception ex)
                    {
                        ex.GetBaseException();
                        return new ChildTest { ChildTestID = -1 };  //something went wrong when saving the test
                    }
                }
                else
                {
                    return new ChildTest { ChildTestID = -3 }; //not a patient of the psychologist
                }
            }
            else
            {
                return new ChildTest { ChildTestID = -2 }; //child is not paired
            }
        }

        //Function to update a test - given Test, Questions, and Answers
        [HttpPost("updateTest")]
        public int updateTest(TestQuestionsAnswers testQuestionsAnswers)
        {
            Tests test = (from t in db.Tests
                          where t.TestID.Equals(testQuestionsAnswers.Test.TestID)
                         select t).FirstOrDefault();

            if (test != null)
            {

                test.Name = testQuestionsAnswers.Test.Name;
                test.LastEdited = testQuestionsAnswers.Test.LastEdited;

                foreach (QuestionAnswers qa in testQuestionsAnswers.QuestionAnswers)
                {
                    Questions question = (from q in db.Questions
                                          where q.QuestionID.Equals(qa.Question.QuestionID)
                                          select q).FirstOrDefault();

                    if (question != null)
                    {

                        question.Question = qa.Question.Question;

                        foreach (Answers a in qa.Answers)
                        {
                            Answers answer = (from aa in db.Answers
                                              where aa.AnswerID.Equals(a.AnswerID)
                                              select aa).FirstOrDefault();

                            if (answer != null)
                            {
                                answer.Answer = a.Answer;
                            }
                            else
                                return -2; //answer null
                        }
                    }
                    else
                    {   //if there isn't a question, we need to add it
                        var newQuestion = new Questions
                        {
                            Question = qa.Question.Question,
                            TestID = testQuestionsAnswers.Test.TestID,
                            Position = qa.Question.Position
                        };

                        db.Questions.Add(newQuestion);
                        try
                        {
                            db.SaveChanges();
                        }
                        catch (Exception ex)
                        {
                            ex.GetBaseException();
                        }

                        //now we need to add the answers
                        foreach (Answers a in qa.Answers)
                        {
                            var newAnswers = new Answers
                            {
                                Answer = a.Answer,
                                AnswerValue = a.AnswerValue,
                                QuestionID = newQuestion.QuestionID,
                                Position = a.Position
                            };
                            db.Answers.Add(newAnswers);
                            try
                            {
                                db.SaveChanges();
                            }
                            catch (Exception ex)
                            {
                                ex.GetBaseException();
                            }
                        }
                    }
                }
                try
                {
                    db.SaveChanges();
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                }
            }
            else
                return 0; //test null

            return 0; //all good
        }


        //Function to add a test - given Test, Questions, and Answers
        private int addTest(TestQuestionsAnswers testQuestionsAnswers)
        {
            //List<QuestionAnswers> qaList = new List<QuestionAnswers>();
            
            var newTest = new Tests
            {
                Name = testQuestionsAnswers.Test.Name,
                Total = testQuestionsAnswers.Test.Total,
                PsychID = testQuestionsAnswers.Test.PsychID,
                DateCreated = DateTime.Now.Date,
                LastEdited = DateTime.Now,
                Status = "Active"
            };
            db.Tests.Add(newTest);
            try
            {
                db.SaveChanges();
                return newTest.TestID;
            }
            catch (Exception ex)
            {
                ex.GetBaseException();
                return -1;
            }
        }

        //Function to add questions - given Test, Questions, and Answers
        private QuestionListTestID addQuestions(TestQuestionsAnswers testQuestionsAnswers)
        {
            List<Questions> questionsList = new List<Questions>();
            List<int> questionIDs = new List<int>();

            int testID = addTest(testQuestionsAnswers);
            QuestionListTestID qListTest = new QuestionListTestID
            {
                QuestionList = questionIDs,
                testID = testID
            };

            if (testID != -1)
            {
                foreach (QuestionAnswers qa in testQuestionsAnswers.QuestionAnswers)
                {
                    var newQuestion = new Questions
                    {
                        Question = qa.Question.Question,
                        TestID = testID,
                        Position = qa.Question.Position
                    };

                    //questionsList.Add(newQuestion);


                    db.Questions.Add(newQuestion);
                    try
                    {
                        db.SaveChanges();
                        questionIDs.Add(newQuestion.QuestionID);
                    }
                    catch (Exception ex)
                    {
                        ex.GetBaseException();
                        questionIDs.Add(-1);
                    }
                }
                qListTest.QuestionList = questionIDs;
                return qListTest;
            }
            else
                return qListTest;
        }

        //Function to add answers - given Test, Questions, and Answers
        [HttpPost("addAnswers")]
        public int addAnswers(TestQuestionsAnswers testQuestionsAnswers)
        {
            List<Answers> answersList = new List<Answers>();

            var qListTest = addQuestions(testQuestionsAnswers);
            List<int> questionsID = qListTest.QuestionList;
            int testID = qListTest.testID;

            if (questionsID[0] > 0)
            {
                int index = 0;
                foreach (int q in questionsID)
                {
                    foreach (Answers a in testQuestionsAnswers.QuestionAnswers[index].Answers)
                    {
                        var newAnswers = new Answers
                        {
                            Answer = a.Answer,
                            AnswerValue = a.AnswerValue,
                            QuestionID = q,
                            Position = a.Position
                        };
                        answersList.Add(newAnswers);
                    }
                    index++;
                }
                db.Answers.AddRange(answersList);
                try
                {
                    db.SaveChanges();
                    return testID;
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return -3;
                }
            }
            else
                return questionsID[0];
        }

        //Function to return number of responses given testID
        [HttpPost("getNumResponses")]
        public int getNumResponses(TestPsych testPsych)
        {
            var responses = db.ChildTest.Where(ct => ct.TestID.Equals(testPsych.TestID));
            int numResponses = 0;

            foreach (ChildTest ct in responses)
            {
                UserController usrController = new UserController(db);
                int pairId = usrController.getPairId(ct.ChildID);

                var psychPair = new PsychPair
                {
                    PsychID = testPsych.PsychID,
                    PairID = pairId
                };

                Boolean linked = isPatient(psychPair);

                if (linked)
                {
                    if(ct.Status == "Completed")
                        numResponses++;
                }
            }

            return numResponses;
        }

        //Function to return number of responses given testID
        [HttpPost("getNumIncomplete")]
        public int getNumIncomplete(TestPsych testPsych)
        {
            var responses = db.ChildTest.Where(ct => ct.TestID.Equals(testPsych.TestID));
            int numIncomplete = 0;

            foreach (ChildTest ct in responses)
            {
                UserController usrController = new UserController(db);
                int pairId = usrController.getPairId(ct.ChildID);

                var psychPair = new PsychPair
                {
                    PsychID = testPsych.PsychID,
                    PairID = pairId
                };

                Boolean linked = isPatient(psychPair);

                if (linked)
                {
                    if (ct.Status == "Assigned")
                        numIncomplete++;
                }
            }

            return numIncomplete;
        }


        //Function to fetch the points for the child
        [HttpGet("getChildPoints/{childId}")]
        public int getChildPoints(int childId)
        {
            List<ChildTest> childTests = new List<ChildTest>();

            var tests = db.ChildTest.Where(t => t.ChildID.Equals(childId) && t.Status.Equals("Completed"));

            //fetch all the tests apart from the mood tracker
            var allTests = (from t in db.Tests
                              select t);
            int totalPoints = 0;

            if (tests.Any())
            {
                foreach (ChildTest t in tests)
                {
                    foreach(Tests ct in allTests)
                    {
                        if(t.TestID.Equals(ct.TestID))
                        {
                            totalPoints += ct.Total;
                        }

                    }
                }
                return totalPoints;
            }
            else
            {
                return 0;
            }

        }

        //Function to return number of responses given testID
        [HttpGet("getNumQuestions/{testID}")]
        public int getNumQuestions(int testID)
        {
            int questions = db.Questions.Where(q => q.TestID.Equals(testID)).Count();
            
            return questions;
        }

        //Function to set status of question to inactive
        [HttpGet("deleteQuestion/{questionID}")]
        public int deleteQuestion(int questionID)
        {
            Questions question = db.Questions.Where(q => q.QuestionID.Equals(questionID)).FirstOrDefault();

            if (question != null)
            {
                question.Status = "Inactive";
                try
                {
                    db.SaveChanges();
                    return 1;
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return 0;
                }
            }
            else
                return -1;
        }

        //Function to set status of test to inactive
        [HttpGet("deleteTest/{testID}")]
        public int deleteTest(int testID)
        {
            deleteTestQuestions(testID);

            Tests test = db.Tests.Where(q => q.TestID.Equals(testID)).FirstOrDefault();

            if (test != null)
            {
                test.Status = "Inactive";
                try
                {
                    db.SaveChanges();
                    return 1;
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return 0;
                }
            }
            else
                return -1;
        }

        //Helper method to delete all questions in a test
        private void deleteTestQuestions(int testID)
        {
            var questions = db.Questions.Where(q => q.TestID.Equals(testID));

            foreach (Questions q in questions)
            {

                q.Status = "Inactive";
                try
                {
                    db.SaveChanges();
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                }
            }
        }

        //returns number of tests assigned to children by a psych
        [HttpPost("getNumAssignedPerTest")]
        public int getNumAssignedPerTest(TestPsych testPsych)
        {
            var childTests = db.ChildTest.Where(ct => ct.TestID.Equals(testPsych.TestID));
            int numAssigned = 0;

            foreach (ChildTest ct in childTests)
            {
                UserController usrController = new UserController(db);
                int pairId = usrController.getPairId(ct.ChildID);

                var psychPair = new PsychPair
                {
                    PsychID = testPsych.PsychID,
                    PairID = pairId
                };

                Boolean linked = isPatient(psychPair);

                if (linked)
                {
                    numAssigned++;
                }
            }

            return numAssigned;
        }

        //helper method to check if patient is linked
        private Boolean isPatient(PsychPair psychPair)
        {
            var link = (from l in db.Link
                        where l.PairID.Equals(psychPair.PairID) && l.PsychID.Equals(psychPair.PsychID)
                        select l).FirstOrDefault();

            if (link != null)
                return true;
            else
                return false;
        }

        //get all children and a bool that says if they are assigned to this test or not
        [HttpPost("getChildTestAssignedStatus")]
        public List<ChildTestAssigned> getChildTestAssignedStatus(TestPsych testPsych)
        {
            List<ChildTestAssigned> assignedChildren = new List<ChildTestAssigned>();
            PatientController patientController = new PatientController(db);

            List<User> linkedChildren = patientController.GetPsychologistPatients(testPsych.PsychID);
            
            foreach (User u in linkedChildren)
            {
                ChildTestAssigned cta = new ChildTestAssigned
                {
                    Child = u,
                    Status = isAssignedToTest(testPsych.TestID, u.UserID)
                };
                assignedChildren.Add(cta);
            }

            return assignedChildren;
        }

        //helper to check if child is assigned a test 
        private Boolean isAssignedToTest(int testID, int childID)
        {
            var isAssigned = db.ChildTest.Where(ct => ct.TestID.Equals(testID) && ct.ChildID.Equals(childID) && ct.Status.Equals("Assigned"));
            if (isAssigned.Any())
                return true;
            else
                return false;
        }

        //save child test assignment
        [HttpPost("assignChildToTest")]
        public void assignChildToTest(ChildTestStatus childTestStatus)
        {
            var childTest = db.ChildTest.Where(ct => ct.TestID.Equals(childTestStatus.TestID) && ct.ChildID.Equals(childTestStatus.ChildID) && ct.Status.Equals("Assigned")).FirstOrDefault();

            if(childTest == null)
            {
                if(childTestStatus.Status == true)
                { 
                    var newChildTest = new ChildTest
                    {
                        Date = DateTime.Now,
                        Status = "Assigned",
                        ChildID = childTestStatus.ChildID,
                        TestID = childTestStatus.TestID,
                        NoteID = null
                    };
                    db.ChildTest.Add(newChildTest);

                    try
                    {
                        //all is well
                        db.SaveChanges();
                    }
                    catch (Exception ex)
                    {
                        ex.GetBaseException();
                    }
                }
            }
            else if(childTest != null)
            {
                if (childTestStatus.Status == false)
                {
                    //there is a test (NOT null) but I don't want there to be so DELETE it
                    db.ChildTest.Remove(childTest);
                    db.SaveChanges();
                }
            }

        }

        //save child test assignment
        [HttpPost("getChildAnswersPerQuestion")]
        public void getChildAnswersPerQuestion(ChildTestQuestion childTestQuestion)
        {
            int answerID = (from a in db.Answers
                            where a.QuestionID.Equals(childTestQuestion.QuestionID)
                           select a.AnswerID).FirstOrDefault();
            //get the child's answer for a question
        }

        //save child test assignment
        [HttpGet("getCompletedTests/{testID}")]
        public List<ChildTest> getCompletedTests(int testID)
        {
            List<ChildTest> completedTests = db.ChildTest.Where(ct => ct.TestID.Equals(testID) && ct.Status.Equals("Completed")).ToList();

            return completedTests;
        }

        //save child test assignment
        [HttpGet("getTotalAnswerScore/{childTestID}")]
        public int getTotalAnswerScore(int childTestID)
        {
            int totalScore = 0;

            List<int> listAnswerIDs = (from ca in db.ChildAnswers
                                  where ca.ChildTestID.Equals(childTestID)
                                    select ca.AnswerID).ToList();

            foreach(int answerID in listAnswerIDs)
            {
                int score = (from a in db.Answers
                             where a.AnswerID.Equals(answerID)
                             select a.AnswerValue).FirstOrDefault();
                
                totalScore += score;
            }

            return totalScore;
        }

        [HttpGet("getChildTest/{childTestID}")]
        public ChildTest getChildTest(int childTestID)
        {
            ChildTest childTest = db.ChildTest.Where(ct => ct.ChildTestID.Equals(childTestID)).FirstOrDefault();
            return childTest;
        }


        [HttpGet("getPsychTestsList/{psychId}")]
        public List<PsychTestListItem> getPsychTestsList(int psychId)
        {
            List<PsychTestListItem> result = new List<PsychTestListItem>();

            var psychTests = getPsychTests(psychId);

            if (psychTests != null)
            {
                foreach (Tests t in psychTests)
                {
                    PsychTestListItem temp = new PsychTestListItem();

                    //get no. questions for current test
                    int numQuestions = getNumQuestions(t.TestID);

                    //get no. responses for current test
                    int numResponses = getNumResponses(new TestPsych { PsychID = psychId, TestID = t.TestID });

                    //add to list
                    result.Add(new PsychTestListItem { test = t, numQuestions = numQuestions, responses = numResponses });
                }
            }

            return result;
        }

        [HttpGet("getStandardTests")]
        public List<PsychTestListItem> getStandardTests()
        {
            List<PsychTestListItem> result = new List<PsychTestListItem>();

            var psychTests = getPsychTests(null);

            if (psychTests != null)
            {
                foreach (Tests t in psychTests)
                {
                    //PsychTestListItem temp = new PsychTestListItem();

                    //get no. questions for current test
                    int numQuestions = getNumQuestions(t.TestID);

                    //get no. responses for current test
                    int numAssigned = getNumAssignedStandardTest(t.TestID);

                    //add to list
                    result.Add(new PsychTestListItem { test = t, numQuestions = numQuestions, responses = numAssigned });
                }
            }

            return result;
        }

        //returns number of tests assigned to children by a psych
        [HttpGet("getNumAssignedStandardTest/{testID}")]
        public int getNumAssignedStandardTest(int testID)
        {
            var childTests = db.ChildTest.Where(ct => ct.TestID.Equals(testID));
            int numAssigned = 0;

            foreach (ChildTest ct in childTests)
            {
                UserController usrController = new UserController(db);
                int pairId = usrController.getPairId(ct.ChildID);

                numAssigned++;
            }

            return numAssigned;
        }

        //get average of mood for weekdays in the past month + average of mood for weekends in the past month and compare for one child
        //[HttpGet("getMoodAnalysis/{childID}")]
        //public MoodWeekAvg getMoodAnalysis(int childID)
        //{
        //    DateTime currentDate = DateTime.Now.Date;
        //    int weekDayCount = 0;
        //    double weekDayAvg = 0;
        //    int weekEndCount = 0;
        //    double weekEndAvg = 0;
        //    String analysis = "";

        //    //PatientController patientController = new PatientController(db);
        //    //List<User> patients = patientController.GetPsychologistPatients(psychID);

        //    for (int k = 0; k < 30; k++)
        //    {
        //        DateTime oldDate = currentDate.AddDays(-1);

        //        //foreach (User u in patients)
        //        {
        //            //weekdays
        //            if (oldDate.DayOfWeek != DayOfWeek.Saturday && oldDate.DayOfWeek != DayOfWeek.Sunday)
        //            {
        //                weekDayCount++;
        //                weekDayAvg += getMoodAnswerValue(oldDate, childID, 1);
        //            }
        //            //weekends
        //            else
        //            {
        //                weekEndCount++;
        //                weekEndAvg += getMoodAnswerValue(oldDate, childID, 1);
        //            }
        //        }
        //    }
        //    if(weekDayCount != 0)
        //        weekDayAvg /= weekDayCount;
        //    if(weekEndCount != 0)
        //        weekEndAvg /= weekEndCount;

        //    if (weekEndAvg - weekDayAvg >= 2)
        //        analysis = "Child is experiencing better moods on weekends";
        //    else if (weekDayAvg - weekEndAvg >= 2)
        //        analysis = "Child is experiencing better moods on weekdays";
        //    else
        //        analysis = "Child mood does not differ greatly on weekdays and weekends";

        //    MoodWeekAvg mva = new MoodWeekAvg
        //    {
        //        WeekDayAvg = weekDayAvg,
        //        WeekEndAvg = weekEndAvg,
        //        Analysis = analysis
        //    };
            
        //    return mva;
        //}


        //get average of mood for weekdays in the past month + average of mood for weekends in the past month and compare for one child
        [HttpGet("getMoodAnalysis/{childID}")]
        public MoodWeekAvg getMoodAnalysis(int childID)
        {
            DateTime currentDate = DateTime.Now.Date;
            int weekDayCount = 0;
            double weekDayAvg = 0;
            int weekEndCount = 0;
            double weekEndAvg = 0;
            String analysis = "";

            Tests moodTracker = getMoodTrackerTest();

            var cTest = (from t in db.ChildTest
                         where t.TestID.Equals(moodTracker.TestID) && t.ChildID.Equals(childID)
                         select t).ToList();

            foreach(ChildTest ct in cTest)
            {
                //weekdays
                if (ct.Date.DayOfWeek != DayOfWeek.Saturday && ct.Date.DayOfWeek != DayOfWeek.Sunday)
                {
                    weekDayCount++;
                    weekDayAvg += getMoodAnswerValue(ct.Date, childID, 1);
                }
                //weekends
                else
                {
                    weekEndCount++;
                    weekEndAvg += getMoodAnswerValue(ct.Date, childID, 1);
                }
            }
            if (weekDayCount != 0)
                weekDayAvg /= weekDayCount;
            if (weekEndCount != 0)
                weekEndAvg /= weekEndCount;

            if (weekEndAvg - weekDayAvg >= 2)
                analysis = "Child is experiencing better moods on weekends";
            else if (weekDayAvg - weekEndAvg >= 2)
                analysis = "Child is experiencing better moods on weekdays";
            else
                analysis = "Child mood does not differ greatly on weekdays and weekends";

            MoodWeekAvg mva = new MoodWeekAvg
            {
                WeekDayAvg = weekDayAvg,
                WeekEndAvg = weekEndAvg,
                Analysis = analysis
            };

            return mva;
        }

    }
}
