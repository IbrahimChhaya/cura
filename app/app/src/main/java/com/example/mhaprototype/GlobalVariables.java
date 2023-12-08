package com.example.mhaprototype;

import java.util.ArrayList;
import java.util.Date;

import PsychologistList.PsychologistInfoModel;
import psychologistHome.MonthBookingCount;

public class GlobalVariables {
    public static UserModel loggedInUser;

    public static int userID;
    public static int userType;
    public static String profilePicture;
    public static String name;
    public static String birthDate;
    public static String typeUser;
    public static String email;
    public static String userName;

    //Guardian register variables
    public static String guardianName;
    public static String guardianDOB;
    public static String grade;

    //List of all the bookings
    public static ArrayList<String> bookingList = new ArrayList<>();

    //Booking details
   public static String bookingDate;
   public static int CalendarID;
   public static String startTime;
   public static String endTime;


    //Ethernet UK
   // public static String url = "http://192.168.43.3:45455/";
    //WIFI UK
    //public static String url = "http://192.168.8.105:45455/";

    //public static String url = "http://192.168.1.106:45455/";


    public static String url = "http://10.0.0.10:45455/";

    //public static String url = "http://10.0.0.3:45455/";



   // public static String url = "http://10.0.0.12:45455/";


    //Getting the psychologist ID from psychList
    public static int psychologistID;
    public static int GuardianChildID;

    //Psychologist details
    public static String psychologistName;
    public static String psychologistDegree;
    public static String psycologistDescription;
    public static double psychLatitude;
    public static double psychLongitude;

    public static String Coordinates;
    public static String psychAddress;

    public static int counsellorAssignedForChat;
    public static int chattingID;

    //reports variables
    public static ArrayList<MonthBookingCount> monthBookingCount;

    public static int globalMissed;
    public static int globalAttended;
    public static int globalCancelled;

    public static ArrayList<String> patientImages;
    public static ArrayList<String> patientNames;

    public static PsychologistInfoModel LoggedInPsychologist;
}
