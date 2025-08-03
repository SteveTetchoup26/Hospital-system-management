package com.st.hospitalmanagement.controllers;

import com.st.hospitalmanagement.database.AlertMessage;
import com.st.hospitalmanagement.database.Database;
import com.st.hospitalmanagement.models.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class EditPatientFormController implements Initializable {

    @FXML
    private TextArea edt_address;

    @FXML
    private Button edt_btn;

    @FXML
    private ComboBox<String> edt_gender;

    @FXML
    private TextField edt_name;

    @FXML
    private TextField edt_number;

    @FXML
    private TextField edt_patientID;

    @FXML
    private ComboBox<String> edt_status;

    @FXML
    private AnchorPane main_form;

    AlertMessage alert = new AlertMessage();

    @FXML
    public void updateBtn () {

        if(edt_status.getSelectionModel().getSelectedItem() == null
            || edt_patientID.getText().isEmpty()
            || edt_name.getText().isEmpty()
            || edt_number.getText().isEmpty()
            || edt_address.getText().isEmpty()
            || edt_gender.getSelectionModel().getSelectedItem() == null) {

            alert.errorMessage("Please fill all fields");
        } else {
            String sqlUpdate = "UPDATE patient SET fullname = ?, gender = ?, mobile_number = ?, address = ?, date_modify = ?, status = ? WHERE patient_id = ?";
            try (Connection connection = Database.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sqlUpdate)) {

                if(alert.confirmationMessage("Are you sure you want to update this patient?")) {

                    ps.setString(1, edt_name.getText());
                    ps.setString(2, edt_gender.getSelectionModel().getSelectedItem());
                    ps.setString(3, edt_number.getText());
                    ps.setString(4, edt_address.getText());
                    ps.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
                    ps.setString(6, edt_status.getSelectionModel().getSelectedItem());
                    ps.setString(7, edt_patientID.getText());

                    ps.executeUpdate();

                    alert.successMessage("Patient record updated successfully");

                    edt_btn.getScene().getWindow().hide();

                } else {
                    alert.errorMessage("Cancelled");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setField() {
        edt_patientID.setText(String.valueOf(Data.temp_patientID));
        edt_patientID.setDisable(true);
        edt_name.setText(Data.temp_name);
        edt_number.setText(Data.temp_number);
        edt_address.setText(Data.temp_address);
        edt_status.getSelectionModel().select(Data.temp_status);
        edt_gender.getSelectionModel().select(Data.temp_gender);


    }

    public void genderList() {

        List<String> listG = new ArrayList<>();

        Collections.addAll(listG, Data.gender);

        ObservableList listData = FXCollections.observableArrayList(listG);

        edt_gender.setItems(listData);
    }

    public void satusList() {

        List<String> listS = new ArrayList<>();

        Collections.addAll(listS, Data.status);

        ObservableList listData = FXCollections.observableArrayList(listS);

        edt_status.setItems(listData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderList();
        satusList();


        setField();
    }
}
