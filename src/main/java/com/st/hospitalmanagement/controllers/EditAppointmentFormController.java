package com.st.hospitalmanagement.controllers;

import com.st.hospitalmanagement.database.AlertMessage;
import com.st.hospitalmanagement.database.Database;
import com.st.hospitalmanagement.models.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class EditAppointmentFormController implements Initializable {

    @FXML
    private TextArea editAppointment_address;

    @FXML
    private TextField editAppointment_appID;

    @FXML
    private Button editAppointment_cancelBtn;

    @FXML
    private TextArea editAppointment_description;

    @FXML
    private TextField editAppointment_diagnosis;

    @FXML
    private ComboBox<String> editAppointment_doctor;

    @FXML
    private Button editAppointment_editBtn;

    @FXML
    private TextField editAppointment_name;

    @FXML
    private TextField editAppointment_number;

    @FXML
    private ComboBox<String> editAppointment_specialized;

    @FXML
    private ComboBox<String> editAppointment_status;

    @FXML
    private ComboBox<String> editAppointment_gender;

    @FXML
    private TextField editAppointment_treatment;


    private AlertMessage alert = new AlertMessage();

    public void doctorList() {

        String sql  = "SELECT * FROM doctor WHERE date_delete IS NULL";

        ObservableList<String> listD = FXCollections.observableArrayList();

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String doctorName = resultSet.getString("doctor_id");
                listD.add(doctorName);
            }

            editAppointment_doctor.setItems(listD);
            appointmentSpecializedList();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setFields(){
        editAppointment_appID.setText(Data.temp_appointmentID);
        editAppointment_name.setText(Data.temp_appointmentName);
        editAppointment_number.setText(Data.temp_appointmentMobileNumber);
        editAppointment_address.setText(Data.temp_appointmentAddress);
        editAppointment_status.getSelectionModel().select(Data.temp_appointmentStatus);
        editAppointment_specialized.getSelectionModel().select(Data.temp_appointmentSpecialized);
        editAppointment_gender.getSelectionModel().select(Data.temp_appointmentGender);
        editAppointment_description.setText(Data.temp_appointmentDescription);
        editAppointment_diagnosis.setText(Data.temp_appointmentDiagnosis);
        editAppointment_treatment.setText(Data.temp_appointmentTreatment);
        editAppointment_doctor.getSelectionModel().select(Data.temp_appointmentDoctor);
    }

    public void appointmentSpecializedList() {

        String sql  = "SELECT * FROM doctor WHERE date_delete IS NULL AND doctor_id = " + editAppointment_doctor.getSelectionModel().getSelectedItem() + "'";

        ObservableList<String> listS = FXCollections.observableArrayList();

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                listS.add(resultSet.getString("specialized"));
            }

            editAppointment_doctor.setItems(listS);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void appointmentStatusList() {

        List<String> listS = new ArrayList<>();

        Collections.addAll(listS, Data.status);

        ObservableList listData = FXCollections.observableArrayList(listS);

        editAppointment_status.setItems(listData);
    }

    public void appointmentGenderList() {

        List<String> listS = new ArrayList<>();

        Collections.addAll(listS, Data.gender);

        ObservableList listData = FXCollections.observableArrayList(listS);

        editAppointment_gender.setItems(listData);
    }

    @FXML
    void cancelBtn(ActionEvent event) {
        if(alert.confirmationMessage("Are you sure you want to cancel?")) {
            editAppointment_editBtn.getScene().getWindow().hide();
        }
    }

    @FXML
    void editBtn(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFields();
        appointmentStatusList();
        appointmentGenderList();
        doctorList();


    }
}
