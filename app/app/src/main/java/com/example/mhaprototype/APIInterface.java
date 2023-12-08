package com.example.mhaprototype;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import Appointment.AppointmentModel;
import Appointment.Models.BookingParties;
import Chat.ChatModel;
import Chat.Counsellor.Models.CounsellorChatMessage;
import Chat.Counsellor.Models.CounsellorInfo;
import Chat.PsychChatListItem;
import ChildMoodTracker.Answers;
import ChildMoodTracker.ChildTest;
import ChildReports.AppointmentModel1;
import ChildReports.DateNumPair;
import ChildReports.Models.MoodResult;
import ChildReports.Notes;
import ChildTestSubsystem.ChildTestModel;
import ChildTestSubsystem.Model.AssignedTest;
import ChildTestSubsystem.Questions;
import ChildTestSubsystem.Test;
import ChildTestSubsystem.TestInfo;
import MapLocation.ModelSavingRating;
import MapLocation.Ratings;
import PsychologistCalendar.BookingModel2;
import PsychologistList.PsychologistInfoModel;
import ResourceHub.RHubDescriptionModel;
import ResourceHub.ResourceHubModel;
import VideoSubsystem.BookingModelVideo;
import psychologistHome.AppointmentModelPsychologist;
import psychologistHome.AppointmentModelPsychologist2;
import psychologistHome.MonthBookingCount;
import psychologistHome.numAttendedCancelledMissed;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {

//    @Headers({"Accept: application/json"})
    @GET("api/Bookings/GetUserBookings/{userID}")
    Call<List<BookingModel>> getuserBookings(@Path("userID") int userID);
    //@Path("userID") int userID);, @Header("Content-Type")String content_type, @Body Map<String,String>user);

    @Headers({"Accept: application/json"})
    @POST("api/User/login")
    Call<UserModel> loginUser(@Header("Content-Type")String content_type, @Body Map<String,String> user);

    @Headers({"Accept: application/json"})
    @POST("api/User/Register")
    Call<RegisterModel>RegisterUser(@Header("Content-Type")String content_type, @Body Map<String,Object> usr);

    //Booking functions**********************************************************************************
    @Headers({"Accept: application/json"})
    @POST("api/Bookings/AddBooking")
    Call<Object> addBooking(@Header("Content-Type")String content_type, @Body Map<String,Object> add);

    @Headers({"Accept: application/json"})
    @POST("api/Bookings/cancelBooking")
    Call<Object> cancelBooking(@Header("Content-Type")String content_type, @Body Map<String,Object> cancel);

    ///api/Bookings/getBookingPatientAndPsych/{bookingID}
    @GET("api/Bookings/getBookingPatientAndPsych/{bookingID}")
    Call<BookingParties> getBookingPatientAndPsych(@Path("bookingID") int bookingID);

    //alreadyHasRecurringBookings
    @Headers({"Accept: application/json"})
    @POST("api/Bookings/alreadyHasRecurringBookings")
    Call<Boolean> alreadyHasRecurringBookings(@Header("Content-Type")String content_type, @Body Map<String,Object> consentCheck);

    //Booking Functions End********************************************************************************



    @Headers({"Accept: application/json"})
    @POST("api/User/GeneratePairCode")
    Call<String> generatePairCode(@Header("content-type")String content_type,@Body Map<String,Integer> map);

    @Headers({"Accept: application/json"})
    @POST("api/User/Pair")
    Call<String> pair(@Header("content-type")String content_type,@Body Map<String,Object> map2);

    @Headers({"Accept: application/json"})
    @POST("api/User/RegisterPsychologist")
    Call<RegisterModel>RegisterPsychologist(@Header("Content-Type")String content_type, @Body Map<String,String> usr);


    @Headers({"Accept: application/json"})
    @POST("api/User/UpdateUserDetails")
    Call<UserModel>UpdateGuardianEmail(@Header("Content-Type")String content_type, @Body Map<String,Object> usr);

    //Api call to get the psychologist calendar based on psychologist ID
    @GET("api/Bookings/getPsychologistCalendars/{psychId}")
    Call<List<PsychologistCalendarModel>> getPsychCalendar(@Path("psychId") int psychId);

    //api call to get all the bookings of a psychologist
    @GET("api/Bookings/getPsychBookings/{psychId}")
    Call<List<BookingModel>> getPsychBookings(@Path("psychId") int psychId);

    ///api/Bookings/getPsychByCalendarID/{calendarID}
    @GET("api/Bookings/getPsychByCalendarID/{calendarID}")
    Call<PsychologistInfoModel> getPsychByCalendarID(@Path("calendarID") int calendarID);

    //Function to get the psychologist ID given the user ID
//    @GET("api/User/isLinked/{usrID}")
//    Call<Integer> isLinked(@Path("usrID") int usrID);

    @Headers({"Accept: application/json"})
    @POST("api/User/isLinked")
    Call<Integer>isLinked(@Header("Content-Type")String content_type, @Body Map<String,Object> userPsych);

    ///api/User/getPairId/{userId}
    @GET("api/User/getPairId/{userId}")
    Call<Integer> getPairId(@Path("userId") int userId);


    @GET("api/User/getAllPsychologists")
    Call<List<PsychologistInfoModel>> getAllPsychologists();

    //function to get all the childrenIDs linked to the parent
    @GET("api/User/getLinkedChildren/{parentID}")
    Call<List<Integer>> getLinkedChildren(@Path("parentID") int parentID);

    //function to get all the users
    @GET("api/User/getAllUsers")
    Call<List<UserModel>> getAllUsers();

//    getAllRating

    //function to get the psychologist IDs that are linked to the parent
    @GET("api/User/isLinkedToParent/{usrID}")
    Call<Integer> isLinkedToParent(@Path("usrID") int usrID);

    @GET("api/Bookings/GetUserBookingsForApp/{userID}")
    Call<List<AppointmentModel>> GetUserBookingsForApp(@Path("userID") int userID);

    //call to api for updating account details without requiring password
    @Headers({"Accept: application/json"})
    @POST("api/User/UpdateAccount")
    Call<UserModel>updateAccount(@Header("Content-Type")String content_type, @Body Map<String,Object> usr);

    //call to api to remove/deactivate an account
    @Headers({"Accept: application/json"})
    @POST("api/User/removeAccount")
    Call<Integer> removeAccount(@Header("content-type")String content_type,@Body Map<String,Object> map2);

    //api call to change a user's password
    @Headers({"Accept: application/json"})
    @POST("api/User/ChangePassword")
    Call<UserModel> changePassword(@Header("content-type")String content_type, @Body Map<String, Object> userDetails);

    //Second calender call
    @GET("api/Bookings/getPsychBookings/{psychId}")
    Call<List<BookingModel2>> getPsychBookings2(@Path("psychId") int psychId);

    //method to get the rating for the psychologist
    @GET("api/Psychologist/GetPsychologistRating/{userId}")
    Call<Double> GetPsychologistRating(@Path("userId") int userId);

    //function to get the resourcehub description page details
    @GET("api/Hub/fetchResourceHubImages/{problemID}")
    Call<List<RHubDescriptionModel>> getResourceHubImages(@Path("problemID") int problemID);


    ///api/Hub/fetchAllFocusPoints
    @GET("api/Hub/fetchAllFocusPoints")
    Call<List<ResourceHubModel>> fetchAllFocusPoints();

    ///api/Hub/fetchChildFocusPoints/{childID}
    @GET("api/Hub/fetchChildFocusPoints/{childID}")
    Call<List<ResourceHubModel>> fetchChildFocusPoints(@Path("childID") int childID);


    //<Test Methods**************************************************************>
    @Headers({"Accept: application/json"})
    @POST("api/Tests/fetchAllChildPsychTests")
    Call<List<ChildTestModel>> fetchAllChildPsychTests(@Header("content-type")String content_type, @Body Map<String, Object> PsychPatientPair);

    @GET("api/Tests/getAllChildTestsAssigned/{childId}")
    Call<List<AssignedTest>> getAllChildTestsAssigned(@Path("childId") int childId);
    //submitTest

    @Headers({"Accept: application/json"})
    @POST("api/Tests/submitTest")
    Call<Integer> submitTest(@Header("content-type")String content_type,@Body Map<String, Object> test);

    @Headers({"Accept: application/json"})
    @POST("api/Tests/checkIfMoodWasLoggedToday")
    Call<ChildTest> checkIfMoodWasLoggedToday(@Header("content-type")String content_type,@Body Map<String, Object> childTest);

    @Headers({"Accept: application/json"})
    @POST("api/Tests/editTest")
    Call<Integer> editTest(@Header("content-type")String content_type,@Body Map<String, Object> childTestInfo);

    //getTestAnswers
    @Headers({"Accept: application/json"})
    @POST("api/Tests/getTestAnswers")
    Call<List<Answers>> getTestAnswers(@Header("content-type")String content_type, @Body Map<String, Object> childTestInfo);


    //getTestAnswers
    @Headers({"Accept: application/json"})
    @POST("api/Tests/getTestQuestions")
    Call<List<Questions>> getTestQuestions(@Header("content-type")String content_type, @Body Map<String, Object> childTestQuestions);

    @Headers({"Accept: application/json"})
    @POST("api/Tests/fetchCompleteTest")
    Call<TestInfo> fetchCompleteTest(@Header("content-type")String content_type, @Body Map<String, Object> childTestFull);


    //Test Methods End ********************************************************************************

    //Booking Methods *********************************************************************************

    @GET("api/Bookings/getAllPreviousBookings/{userID}")
    Call<List<AppointmentModel>> getAllPreviousBookings(@Path("userID") int userID);

    @GET("api/Bookings/getAllUpcomingBookings/{userID}")
    Call<List<AppointmentModel>> getAllUpcomingBookings(@Path("userID") int userID);

    @GET("api/Bookings/getAllCancelledBookings/{userID}")
    Call<List<AppointmentModel>> getAllCancelledBookings(@Path("userID") int userID);

    //adding patient function
    ///
    @Headers({"Accept: application/json"})
    @POST("api/Patient/addPatient")
    Call<Link> addPatient(@Header("content-type")String content_type, @Body Map<String, Object> info);

    ///api/Bookings/getVirtualSession/{userId}
    @GET("api/Bookings/getVirtualSession/{userId}")
    Call<BookingModelVideo> getVirtualSession(@Path("userId") int userId);
    //Booking methods end******************************************************************************



    //####### REPORTING METHODS Start ******************************************************************
    @GET("api/Tests/getWeeklyMoodTrend/{childID}")
    Call<List<MoodResult>> getWeeklyMoodTrend(@Path("childID") int childID);

   //method to get number of mood logs per week
    @GET("api/Tests/getTotalMoodLogsPerWeek/{childID}")
    Call<Integer> getTotalMoodLogsPerWeek(@Path("childID") int childID);

    //method to get average mood per week
    @GET("api/Tests/getAverageMoodPerWeek/{childID}")
    Call<Double> getAverageMoodPerWeek(@Path("childID") int childID);

    @GET("api/Bookings/getAllUpcomingBookings/{userID}")
    Call<List<AppointmentModel1>> getAllUpcomingBookings2(@Path("userID") int userID);

    @GET("api/Bookings/getAllCancelledBookings/{userID}")
    Call<List<AppointmentModel1>> getAllCancelledBookings2(@Path("userID") int userID);

    @GET("api/Bookings/GetUserBookingsForApp/{userID}")
    Call<List<AppointmentModel1>> GetUserBookingsForApp2(@Path("userID") int userID);


    @GET("api/Reports/getCancelledMeetingsPerMonth/{userId}")
    Call<List<DateNumPair>> getCancelledMeetingsPerMonth(@Path("userID") int userID);

    //fetching the feedback for the child
    ///api/Notes/fetchAllChildsNotesByID/{childID}
    @GET("api/Notes/fetchAllChildsNotesByID/{childID}")
    Call<List<Notes>> fetchAllChildsNotesByID(@Path("childID") int childID);

    //fetching the points for the child
    ///api/Tests/getChildPoints/{childId}
    @GET("api/Tests/getChildPoints/{childId}")
    Call<Integer> getChildPoints(@Path("childId") int childID);

    //####### REPORTING METHODS End ******************************************************************
    //addChild
    @Headers({"Accept: application/json"})
    @POST("api/User/addChild")
    Call<UserModel>GuardianRegistersChild(@Header("Content-Type")String content_type, @Body Map<String,Object> usrInfo);

    //method to check if a child has picture password or not
    @GET("api/User/hasPicturePass/{email}")
    Call<Boolean> hasPicturePass(@Path("email") String childID);



    /***** Chat methods - START **********************************************************************************/
    @Headers({"Accept: application/json"})
    @POST("api/Chats/getChatMessages")
    Call<List<ChatModel>> getChatHistory(@Header("content-type") String content_type, @Body Map<String, Object> PsychPatientPair);

    @Headers({"Accept: application/json"})
    @POST("api/Chats/sendMessage")
    Call<ChatModel> sendMessage(@Header("content-type") String content_type, @Body Map<String, Object> messageInfo);

    @GET("api/Chats/getChildPsychologists/{childID}")
    Call<List<PsychChatListItem>> getChildPsychologists(@Path("childID") int childID);


    //Counsellor chat functions
    @Headers({"Accept: application/json"})
    @POST("api/Chats/saveCounsellorChatMessage")
    Call<CounsellorChatMessage> saveCounsellorChatMessage(@Header("content-type") String content_type, @Body Map<String, Object> messageInfo);

    @Headers({"Accept: application/json"})
    @POST("api/Chats/getCounsellingChatMessages")
    Call<List<CounsellorChatMessage>> getCounsellingChatMessages(@Header("content-type") String content_type, @Body Map<String, Object> chatParties);

    @GET("api/Chats/fetchChildsCounsellor/{childID}")
    Call<UserModel> fetchChildsCounsellor(@Path("childID") int childID);

    @Headers({"Accept: application/json"})
    @POST("api/Chats/getCounsellorChatID")
    Call<Integer> getCounsellorChatID(@Header("content-type") String content_type, @Body Map<String, Object> chatParties);


//    getAllRating
    @GET("api/Psychologist/GetAllPsychologistRatings/{psychID}")
    Call<List<Ratings>> getAllUserRating(@Path("psychID") int psychID);

    //saveRating
    @Headers({"Accept: application/json"})
    @POST("/api/Psychologist/setRating")
    Call<ModelSavingRating> SaveRating(@Header("content-type") String content_type, @Body Map<String, Integer> savepsychrating);


    /***** Chat methods - END **********************************************************************************/

    ////api/Patient/GetPsychologistPatients/{psychId}
    @GET("api/Patient/GetPsychologistPatients/{psychID}")
    Call<List<UserModel>> GetPsychologistPatients(@Path("psychID") int psychID);

    ///api/Reports/getBookingsCountsPerMonth/{psychId}
    @GET("api/Reports/getBookingsCountsPerMonth/{psychId}")
    Call<List<MonthBookingCount>> getBookingsCountsPerMonth(@Path("psychId") int psychId);

    ///api/Reports/getPatientPercentIncrease/{psychId}
    @GET("api/Reports/getPatientPercentIncrease/{psychId}")
    Call<Double> getPatientPercentIncrease(@Path("psychId") int psychId);

    ///api/Reports/getNumberOfNewPatientsThisMonth/{psychId}
    @GET("api/Reports/getNumberOfNewPatientsThisMonth/{psychId}")
    Call<Integer> getNumberOfNewPatientsThisMonth(@Path("psychId") int psychId);

    ///api/Reports/getNumEachBookingPerPsych/{psychID}
    @GET("api/Reports/getNumEachBookingPerPsych/{psychID}")
    Call<numAttendedCancelledMissed> getNumEachBookingPerPsych(@Path("psychID") int psychID);

    ///api/Bookings/GetUpcomingBookings/{psychId}
    @GET("api/Bookings/GetUpcomingBookings/{psychId}")
    Call<List<AppointmentModelPsychologist>> GetUpcomingBookings(@Path("psychId") int psychId);

    ///api/User/GetPairChild/{pairId}
    @GET("api/User/GetPairChild/{pairId}")
    Call<UserModel> GetPairChild(@Path("pairId") int pairId);

    ///api/User/updatePsychologistInfo

    @Headers({"Accept: application/json"})
    @POST("api/User/updatePsychologistInfo")
    Call<String> updatePsychologistInfo(@Header("content-type") String content_type, @Body Map<String, Object> PsychDetails);


    ///api/Reports/getNumBookingsThisMonth

    @Headers({"Accept: application/json"})
    @POST("api/Reports/getNumBookingsThisMonth")
    Call<Integer> getNumBookingsThisMonth(@Header("content-type") String content_type, @Body Map<String, Object> PsychDetails);


    @GET("api/Bookings/getAllUpcomingBookings/{userID}")
    Call<List<AppointmentModelPsychologist2>> getAllUpcomingBookings3(@Path("userID") int userID);

}
