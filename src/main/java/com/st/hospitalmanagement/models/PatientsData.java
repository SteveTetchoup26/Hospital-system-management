package com.st.hospitalmanagement.models;

import java.util.Date;

public class PatientsData {

    private Integer id;
    private Integer patientID;
    private String password;
    private String fullName;
    private String number;
    private String address;
    private String image;
    private String description;
    private String diagnosis;
    private String treatment;
    private String doctor;
    private String specialized;
    private Date date;
    private Date dateModify;
    private Date dateDelete;
    private String status;
    private String gender;



    public PatientsData(Integer id, Integer patientID, String fullName, String gender, String description, String treatment,
                        String image, String diagnosis, String doctor, Date date) {
        this.id = id;
        this.patientID = patientID;
        this.fullName = fullName;
        this.gender  = gender;
        this.image = image;
        this.diagnosis = diagnosis;
        this.doctor = doctor;
        this.date = date;
        this.description = description;
        this.treatment = treatment;
    }

    public PatientsData(Integer id, Integer patientID, String description, String treatment, String diagnosis, Date date) {
        this.id = id;
        this.patientID = patientID;
        this.diagnosis = diagnosis;
        this.date = date;
        this.description = description;
        this.treatment = treatment;
    }

    public PatientsData(Integer id, Integer patientID, String password, String fullName, String gender,
                        String number, String address, String image, String description,
                        String diagnosis, String treatment, String doctor, String specialized,
                        Date date, Date dateModify, Date dateDelete, String status) {
        this.id = id;
        this.patientID = patientID;
        this.password = password;
        this.fullName = fullName;
        this.gender  = gender;
        this.number = number;
        this.address = address;
        this.image = image;
        this.description = description;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.doctor = doctor;
        this.specialized = specialized;
        this.date = date;
        this.dateModify = dateModify;
        this.dateDelete = dateDelete;
        this.status = status;
    }

    public PatientsData(Integer id, Integer patientID, String fullName,
                        String number, String address, Date date,
                        Date dateModify, Date dateDelete, String status, String gender) {
        this.id = id;
        this.patientID = patientID;
        this.fullName = fullName;
        this.number = number;
        this.address = address;
        this.date = date;
        this.dateModify = dateModify;
        this.dateDelete = dateDelete;
        this.status = status;
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPatientID() {
        return patientID;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getSpecialized() {
        return specialized;
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

    public String getGender() {
        return  gender;
    }
}
