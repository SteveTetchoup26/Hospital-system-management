package com.st.hospitalmanagement.controllers;

import com.st.hospitalmanagement.Main;
import com.st.hospitalmanagement.database.AlertMessage;
import com.st.hospitalmanagement.database.Database;
import com.st.hospitalmanagement.models.Data;
import com.st.hospitalmanagement.models.PatientsData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class RecordPageController implements Initializable {
    @FXML
    private TableColumn<PatientsData, String> recordpage_col_actions;

    @FXML
    private TableColumn<PatientsData, String> recordpage_col_address;

    @FXML
    private TableColumn<PatientsData, String> recordpage_col_dateCreated;

    @FXML
    private TableColumn<PatientsData, String> recordpage_col_dateDelete;

    @FXML
    private TableColumn<PatientsData, String> recordpage_col_dateModify;

    @FXML
    private TableColumn<PatientsData, String> recordpage_col_gender;

    @FXML
    private TableColumn<PatientsData, String> recordpage_col_mobileNumber;

    @FXML
    private TableColumn<PatientsData, String> recordpage_col_name;

    @FXML
    private TableColumn<PatientsData, String> recordpage_col_patientID;

    @FXML
    private AnchorPane recordpage_mainForm;

    @FXML
    private TextField recordpage_search;

    @FXML
    private TableView<PatientsData> recordpage_tableView;

    private ObservableList<PatientsData> patientsRecordData;

    AlertMessage alert = new AlertMessage();

    public ObservableList<PatientsData> getPatientRecordData() {

        ObservableList<PatientsData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM patient WHERE date_delete IS NULL AND doctor = '" + Data.doctor_id + "'";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectData);
             Statement st = connection.createStatement();) {

            ResultSet rs = ps.executeQuery();

            PatientsData pData;

            while(rs.next()) {
                pData = new PatientsData(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getString("fullname"),
                        rs.getString("mobile_number"),
                        rs.getString("address"),
                        rs.getDate("date"),
                        rs.getDate("date_modify"),
                        rs.getDate("date_delete"),
                        rs.getString("status"),
                        rs.getString("gender")
                );

                listData.add(pData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listData;
    }

    public void displayPatientsData() {
        patientsRecordData = getPatientRecordData();

        recordpage_col_patientID.setCellValueFactory(new PropertyValueFactory<>("patientID"));
        recordpage_col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        recordpage_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        recordpage_col_mobileNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        recordpage_col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        recordpage_col_dateCreated.setCellValueFactory(new PropertyValueFactory<>("date"));
        recordpage_col_dateModify.setCellValueFactory(new PropertyValueFactory<>("dateModify"));
        recordpage_col_dateDelete.setCellValueFactory(new PropertyValueFactory<>("dateDelete"));

        recordpage_tableView.setItems(patientsRecordData);
    }

    public void actionButtons() {

        patientsRecordData = getPatientRecordData();

        Callback<TableColumn<PatientsData, String>, TableCell<PatientsData, String>> cellFactory = (TableColumn<PatientsData, String> param) -> {
            final TableCell<PatientsData, String> cell = new TableCell<PatientsData, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        Button editButton = new Button("Edit");
                        Button removeButton = new Button("Remove");

                        editButton.setStyle("""
                                -fx-background-color: linear-gradient(to bottom right, #188ba7, #306090); -fx-text-fill: #fff;
                                    -fx-font-family: Arial;
                                    -fx-cursor: hand;""");

                        removeButton.setStyle("""
                                -fx-background-color: linear-gradient(to bottom right, #188ba7, #306090); -fx-text-fill: #fff;
                                    -fx-font-family: Arial;
                                    -fx-cursor: hand;""");

                        editButton.setOnAction(event -> {
                            try {
                                PatientsData pData = recordpage_tableView.getSelectionModel().getSelectedItem();
                                int num  = recordpage_tableView.getSelectionModel().getSelectedIndex();

                                if((num - 1) < -1) {
                                    alert.errorMessage("Please select item first");
                                    return;
                                }

                                Data.temp_patientID = pData.getPatientID();
                                Data.temp_name = pData.getFullName();
                                Data.temp_number = pData.getNumber();
                                Data.temp_address = pData.getAddress();
                                Data.temp_status = pData.getStatus();
                                Data.temp_gender = pData.getGender();

                                Parent root = FXMLLoader.load(Main.class.getResource("fxml/EditPatientForm.fxml"));
                                Stage stage = new Stage();
                                stage.setTitle("Hospital Management System | Record Page Form");

                                stage.setScene(new Scene(root));
                                stage.show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        removeButton.setOnAction(event -> {
                            try {
                                PatientsData pData = recordpage_tableView.getSelectionModel().getSelectedItem();
                                int num  = recordpage_tableView.getSelectionModel().getSelectedIndex();

                                if((num - 1) < -1) {
                                    alert.errorMessage("Please select item first");
                                    return;
                                }

                                String deleteQuery = "UPDATE hospital.patient SET date_delete = ? WHERE patient_id = ?";
                                try (Connection connection = Database.getConnection();
                                     PreparedStatement ps = connection.prepareStatement(deleteQuery)) {

                                    if(alert.confirmationMessage("Are you sure you want to delete this patient ?")) {
                                        ps.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                                        ps.setInt(2, pData.getPatientID());
                                        ps.executeUpdate();

                                        alert.successMessage("Patient record deleted successfully");
                                        displayPatientsData();
                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        HBox manageBtn = new HBox(editButton, removeButton);
                        manageBtn.setAlignment(Pos.CENTER);
                        manageBtn.setSpacing(5);
                        setGraphic(manageBtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };

        recordpage_col_actions.setCellFactory(cellFactory);
        recordpage_tableView.setItems(patientsRecordData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayPatientsData();
        actionButtons();
    }
}
