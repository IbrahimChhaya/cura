package Appointment.Models;

import com.example.mhaprototype.PsychModel;
import com.example.mhaprototype.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingParties {

    @SerializedName("psychologist")
    @Expose
    private PsychModel psychologist;

    @SerializedName("patient")
    @Expose
    private UserModel patient;


    //getter and setter methods
    public PsychModel getPsychologist() {
        return psychologist;
    }

    public void setPsychologist(PsychModel psychologist) {
        this.psychologist = psychologist;
    }

    public UserModel getPatient() {
        return patient;
    }

    public void setPatient(UserModel patient) {
        this.patient = patient;
    }
}
