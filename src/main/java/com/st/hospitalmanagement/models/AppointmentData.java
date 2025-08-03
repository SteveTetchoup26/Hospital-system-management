package com.st.hospitalmanagement.models;

import java.util.Date;

public class AppointmentData {
    private Integer id;
    private String appointmentID;
    private String name;
    private String patientID;
    private String gender;
    private String description;
    private String diagnosis;
    private String treatment;
    private String mobileNumber;
    private String address;
    private Date date;
    private Date dateModify;
    private Date dateDelete;
    private Date schedule;
    private String doctorID;
    private String specialized;
    private String status;


    public AppointmentData(String appointmentID, String name,
                           String description,
                           Date date, String status) {
        this.appointmentID = appointmentID;
        this.name = name;
        this.description = description;
        this.date = date;
        this.status = status;
    }

    public AppointmentData(String appointmentID,
                           String description, String diagnosis,
                           String treatment, String doctorID, Date schedule) {
        this.appointmentID = appointmentID;
        this.description = description;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.doctorID = doctorID;
        this.schedule = schedule;
    }

    public AppointmentData(String appointmentID, String name,
                           String gender, String mobileNumber,
                           String description, String diagnosis,
                           String treatment, String address,
                           Date date, Date dateModify,
                           Date dateDelete, String status,
                           Date schedule) {
        this.appointmentID = appointmentID;
        this.name = name;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.description = description;
        this.date = date;
        this.dateModify = dateModify;
        this.dateDelete = dateDelete;
        this.status = status;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.address = address;
        this.schedule = schedule;
    }

    public AppointmentData(Integer id, String appointmentID, String name,
                           String gender, String mobileNumber,
                           String description, String diagnosis,
                           String treatment, String address, String doctorID, String specialized,
                           Date date, Date dateModify,
                           Date dateDelete, String status,
                           Date schedule) {
        this.id = id;
        this.appointmentID = appointmentID;
        this.name = name;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.description = description;
        this.date = date;
        this.dateModify = dateModify;
        this.dateDelete = dateDelete;
        this.status = status;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.address = address;
        this.doctorID = doctorID;
        this.specialized = specialized;
        this.schedule = schedule;
    }


    public Integer getId() {
        return id;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getDescription() {
        return description;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public Date getDate() {
        return date;
    }

    public Date getDateModify() {
        return dateModify;
    }

    public Date getDateDelete() {
        return dateDelete;
    }

    public String getStatus() {
        return status;
    }

    public String getTreatment() {
        return treatment;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public Date getSchedule() {
        return schedule;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getSpecialized() {
        return specialized;
    }
}
