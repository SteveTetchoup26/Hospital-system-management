package com.st.hospitalmanagement.controllers;

import com.st.hospitalmanagement.Main;
import com.st.hospitalmanagement.database.AlertMessage;
import com.st.hospitalmanagement.database.Database;
import com.st.hospitalmanagement.models.AppointmentData;
import com.st.hospitalmanagement.models.Data;
import com.st.hospitalmanagement.models.DoctorData;
import com.st.hospitalmanagement.models.PatientsData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

public class AdminMainFormController implements Initializable {

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_appID;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_contact;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_date;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_date_delete;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_date_modify;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_description;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_gender;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_name;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_status;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_actions;

    @FXML
    private AnchorPane appointments_form;

    @FXML
    private TableView<AppointmentData> appointments_tableView;

    @FXML
    private Button appointment_btn;

    @FXML
    private Label current_form;

    @FXML
    private Label dashboard_AD;

    @FXML
    private Label dashboard_AP;

    @FXML
    private Label dashboard_TA;

    @FXML
    private Label dashboard_TP;

    @FXML
    private Button dashboard_btn;

    @FXML
    private BarChart<DoctorData, String> dashboard_chart_DD;

    @FXML
    private AreaChart<DoctorData, String> dashboard_chart_PD;

    @FXML
    private TableColumn<DoctorData, String> dashboard_col_doctorID;

    @FXML
    private TableColumn<DoctorData, String> dashboard_col_name;

    @FXML
    private TableColumn<DoctorData, String> dashboard_col_specialized;

    @FXML
    private TableColumn<DoctorData, String> dashboard_col_status;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private TableView<DoctorData> dashboard_tableView;

    @FXML
    private Label date_time;

    @FXML
    private Button doctor_btn;

    @FXML
    private TableColumn<DoctorData, String> doctors_col_actions;

    @FXML
    private TableColumn<DoctorData, String> doctors_col_address;

    @FXML
    private TableColumn<DoctorData, String> doctors_col_contact;

    @FXML
    private TableColumn<DoctorData, String> doctors_col_doctorID;

    @FXML
    private TableColumn<DoctorData, String> doctors_col_email;

    @FXML
    private TableColumn<DoctorData, String> doctors_col_gender;

    @FXML
    private TableColumn<DoctorData, String> doctors_col_name;

    @FXML
    private TableColumn<DoctorData, String> doctors_col_specialization;

    @FXML
    private TableColumn<DoctorData, String> doctors_col_status;

    @FXML
    private AnchorPane doctors_form;

    @FXML
    private TableView<DoctorData> doctors_tableView;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label nav_adminID;

    @FXML
    private Label nav_username;

    @FXML
    private Button patient_btn;

    @FXML
    private TableColumn<PatientsData, String> patients_col_actions;

    @FXML
    private TableColumn<PatientsData, String> patients_col_contact;

    @FXML
    private TableColumn<PatientsData, String> patients_col_date;

    @FXML
    private TableColumn<PatientsData, String> patients_col_date_delete;

    @FXML
    private TableColumn<PatientsData, String> patients_col_date_modify;

    @FXML
    private TableColumn<PatientsData, String> patients_col_description;

    @FXML
    private TableColumn<PatientsData, String> patients_col_name;

    @FXML
    private TableColumn<PatientsData, String> patients_col_patientID;

    @FXML
    private TableColumn<PatientsData, String> patients_col_status;

    @FXML
    private TableColumn<PatientsData, String> patients_col_gender;

    @FXML
    private AnchorPane patients_form;

    @FXML
    private TableView<PatientsData> patients_tableView;

    @FXML
    private Button payment_btn;

    @FXML
    private Button profile_setting_btn;

    @FXML
    private Circle top_profile;

    @FXML
    private Label top_username;

    @FXML
    private TextField profile_adminID;

    @FXML
    private Circle profile_circleImage;

    @FXML
    private Button profile_editBtn;

    @FXML
    private TextField profile_email;

    @FXML
    private AnchorPane profile_form;

    @FXML
    private Button profile_importBtn;

    @FXML
    private Button logout_btn;

    @FXML
    private Label profile_label_adminID;

    @FXML
    private Label profile_label_dateCreated;

    @FXML
    private Label profile_label_email;

    @FXML
    private Label profile_label_name;

    @FXML
    private TextField profile_name;

    @FXML
    private ComboBox<String> profile_gender;

    @FXML
    private Button payment_checkoutBtn;

    @FXML
    private Circle payment_circleImage;

    @FXML
    private TableColumn<PatientsData, String> payment_col_date;

    @FXML
    private TableColumn<PatientsData, String> payment_col_diagnosis;

    @FXML
    private TableColumn<PatientsData, String> payment_col_doctor;

    @FXML
    private TableColumn<PatientsData, String> payment_col_gender;

    @FXML
    private TableColumn<PatientsData, String> payment_col_name;

    @FXML
    private TableColumn<PatientsData, String> payment_col_patientID;

    @FXML
    private AnchorPane payment_form;

    @FXML
    private Label payment_label_date;

    @FXML
    private Label payment_label_doctor;

    @FXML
    private Label payment_label_name;

    @FXML
    private Label payment_label_patientID;

    @FXML
    private TableView<PatientsData> payment_tableView;

    private Image image;


    private ObservableList<DoctorData> doctorListData;

    private ObservableList<PatientsData> patientListData;

    private ObservableList<AppointmentData> appointmentListData;

    private ObservableList<PatientsData> paymentListData;

    private AlertMessage alert = new AlertMessage();



    public void dashboardDisplayAD() {

        String sql = "SELECT COUNT(id) FROM doctor WHERE date_delete IS NULL AND status = 'Active'";
        int countIP = 0;
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                countIP = rs.getInt("COUNT(id)");
            }

            dashboard_AD.setText(String.valueOf(countIP));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dashboardDisplayTP() {

        String sql = "SELECT COUNT(id) FROM patient WHERE date_delete IS NULL";
        int countIP = 0;
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                countIP = rs.getInt("COUNT(id)");
            }

            dashboard_TP.setText(String.valueOf(countIP));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dashboardDisplayTA() {

        String sql = "SELECT COUNT(id) FROM appointment WHERE date_delete IS NULL";
        int countIP = 0;
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                countIP = rs.getInt("COUNT(id)");
            }

            dashboard_TA.setText(String.valueOf(countIP));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dashboardDisplayAP() {

        String sql = "SELECT COUNT(id) FROM patient WHERE status = 'Active' AND date_delete IS NULL";
        int countAP = 0;
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                countAP = rs.getInt("COUNT(id)");
            }

            dashboard_AP.setText(String.valueOf(countAP));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<DoctorData> dashboardDoctorTableViewData() {

        ObservableList<DoctorData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM doctor WHERE date_delete IS NULL";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            DoctorData docData;

            while(rs.next()) {
                docData = new DoctorData(
                        rs.getString("doctor_id"),
                        rs.getString("fullname"),
                        rs.getString("specialized"),
                        rs.getString("status")
                );

                listData.add(docData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public void dashboardDoctorTableViewDataShowData() {
        doctorListData = dashboardDoctorTableViewData();

        dashboard_col_doctorID.setCellValueFactory(new PropertyValueFactory<>("doctorID"));
        dashboard_col_name.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        dashboard_col_specialized.setCellValueFactory(new PropertyValueFactory<>("specialized"));
        dashboard_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        dashboard_tableView.setItems(doctorListData);

    }

    public void dashboardChartPatientData() {

        dashboard_chart_PD.getData().clear();
        XYChart.Series chart = new XYChart.Series<>();

        String sql = "SELECT DATE(date) as day, COUNT(id) FROM patient WHERE date_delete IS NULL GROUP BY day LIMIT 7";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                chart.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }

            dashboard_chart_PD.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardChartDoctorData() {

        dashboard_chart_DD.getData().clear();
        XYChart.Series chart = new XYChart.Series<>();

        String sql = "SELECT DATE(date) as day, COUNT(id) FROM doctor WHERE date_delete IS NULL GROUP BY day LIMIT 7";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                chart.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }

            dashboard_chart_DD.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<DoctorData> doctorGetData() {

        ObservableList<DoctorData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM doctor WHERE date_delete IS NULL";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            DoctorData docData;

            while(rs.next()) {
                docData = new DoctorData(
                        rs.getInt("id"),
                        rs.getString("doctor_id"),
                        rs.getString("password"),
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getString("gender"),
                        rs.getString("mobile_number"),
                        rs.getString("specialized"),
                        rs.getString("address"),
                        rs.getString("image"),
                        rs.getDate("date"),
                        rs.getDate("date_modify"),
                        rs.getDate("date_delete"),
                        rs.getString("status")
                );

                listData.add(docData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listData;
    }

    public void doctorShowData() {
        doctorListData = doctorGetData();

        doctors_col_doctorID.setCellValueFactory(new PropertyValueFactory<>("doctorID"));
        doctors_col_name.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        doctors_col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        doctors_col_contact.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        doctors_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        doctors_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        doctors_col_specialization.setCellValueFactory(new PropertyValueFactory<>("specialized"));
        doctors_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        doctors_tableView.setItems(doctorListData);

    }

    public void doctorActionButton() {
        doctorListData = doctorGetData();

        Callback<TableColumn<DoctorData, String>, TableCell<DoctorData, String>> cellFactory = (TableColumn<DoctorData, String> param) -> {
            final TableCell<DoctorData, String> cell = new TableCell<DoctorData, String>() {
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
                                DoctorData pData = doctors_tableView.getSelectionModel().getSelectedItem();
                                int num  = doctors_tableView.getSelectionModel().getSelectedIndex();

                                if((num - 1) < -1) {
                                    alert.errorMessage("Please select item first");
                                    return;
                                }

                                Data.temp_doctorID = pData.getDoctorID();
                                Data.temp_doctorName = pData.getFullname();
                                Data.temp_doctorEmail = pData.getEmail();
                                Data.temp_doctorGender = pData.getGender();
                                Data.temp_doctorNumber = pData.getMobileNumber();
                                Data.temp_doctorAddress = pData.getAddress();
                                Data.temp_doctorSpecialized = pData.getSpecialized();
                                Data.temp_doctorImagePath = pData.getImage();
                                Data.temp_doctorPassword = pData.getPassword();
                                Data.temp_doctorStatus = pData.getStatus();


                                Parent root = FXMLLoader.load(Main.class.getResource("fxml/EditDoctorForm.fxml"));
                                Stage stage = new Stage();

                                stage.setScene(new Scene(root));
                                stage.show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        removeButton.setOnAction(event -> {
                            try {
                                DoctorData pData = doctors_tableView.getSelectionModel().getSelectedItem();
                                int num  = doctors_tableView.getSelectionModel().getSelectedIndex();

                                if((num - 1) < -1) {
                                    alert.errorMessage("Please select item first");
                                    return;
                                }

                                String deleteQuery = "UPDATE hospital.doctor SET date_delete = ? WHERE doctor_id = ?";
                                try (Connection connection = Database.getConnection();
                                     PreparedStatement ps = connection.prepareStatement(deleteQuery)) {

                                    if(alert.confirmationMessage("Are you sure you want to delete this doctor ?")) {
                                        ps.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                                        ps.setString(2, pData.getDoctorID());
                                        ps.executeUpdate();

                                        alert.successMessage("Doctor record deleted successfully");
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

        doctors_col_actions.setCellFactory(cellFactory);
        doctors_tableView.setItems(doctorListData);
    }

    public ObservableList<PatientsData> patientGetData()  {

        ObservableList<PatientsData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM patient WHERE date_delete IS NULL";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectData)) {

            ResultSet rs = ps.executeQuery();

            PatientsData pData;

            while(rs.next()) {
                pData = new PatientsData(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getString("password"),
                        rs.getString("fullname"),
                        rs.getString("gender"),
                        rs.getString("mobile_number"),
                        rs.getString("address"),
                        rs.getString("image"),
                        rs.getString("description"),
                        rs.getString("diagnosis"),
                        rs.getString("treatment"),
                        rs.getString("doctor"),
                        rs.getString("specialized"),
                        rs.getDate("date"),
                        rs.getDate("date_modify"),
                        rs.getDate("date_delete"),
                        rs.getString("status")
                );

                listData.add(pData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listData;
    }


    public void patientShowData() {
        patientListData = patientGetData();

        patients_col_patientID.setCellValueFactory(new PropertyValueFactory<>("patientID"));
        patients_col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        patients_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        patients_col_contact.setCellValueFactory(new PropertyValueFactory<>("number"));
        patients_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        patients_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        patients_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        patients_col_date_modify.setCellValueFactory(new PropertyValueFactory<>("dateModify"));
        patients_col_date_delete.setCellValueFactory(new PropertyValueFactory<>("dateDelete"));


        patients_tableView.setItems(patientListData);
    }

    public void patientActionButton() {
        patientListData = patientGetData();

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
                                PatientsData pData = patients_tableView.getSelectionModel().getSelectedItem();
                                int num  = patients_tableView.getSelectionModel().getSelectedIndex();

                                if((num - 1) < -1) {
                                    alert.errorMessage("Please select item first");
                                    return;
                                }

                                Data.temp_patientID = pData.getPatientID();
                                Data.temp_name = pData.getFullName();
                                Data.temp_number = pData.getNumber();
                                Data.temp_address = pData.getAddress();
                                Data.temp_status = pData.getStatus();
                                Data.temp_gender = pData.getGender(); ;



                                Parent root = FXMLLoader.load(Main.class.getResource("fxml/EditPatientForm.fxml"));
                                Stage stage = new Stage();

                                stage.setScene(new Scene(root));
                                stage.show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        removeButton.setOnAction(event -> {
                            try {
                                PatientsData pData = patients_tableView.getSelectionModel().getSelectedItem();
                                int num  = patients_tableView.getSelectionModel().getSelectedIndex();

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

        patients_col_actions.setCellFactory(cellFactory);
        patients_tableView.setItems(patientListData);
    }

    public ObservableList<AppointmentData> appointmentGetData() {

        ObservableList<AppointmentData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointment WHERE date_delete IS NULL";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            AppointmentData appData;

            while(rs.next()) {
                appData = new AppointmentData(
                        rs.getInt("id"),
                        rs.getString("appointment_id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("mobile_number"),
                        rs.getString("description"),
                        rs.getString("diagnosis"),
                        rs.getString("treatment"),
                        rs.getString("address"),
                        rs.getString("doctor"),
                        rs.getString("specialized"),
                        rs.getDate("date"),
                        rs.getDate("date_modify"),
                        rs.getDate("date_delete"),
                        rs.getString("status"),
                        rs.getDate("schedule")
                );

                listData.add(appData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listData;
    }

    public void appointmentShowData() {
        appointmentListData = appointmentGetData();

        appointments_col_appID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointments_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        appointments_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        appointments_col_contact.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        appointments_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointments_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        appointments_col_date_modify.setCellValueFactory(new PropertyValueFactory<>("dateModify"));
        appointments_col_date_delete.setCellValueFactory(new PropertyValueFactory<>("dateDelete"));
        appointments_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        appointments_tableView.setItems(appointmentListData);

    }

    public void appointmentActionButton() {
        patientListData = patientGetData();

        Callback<TableColumn<AppointmentData, String>, TableCell<AppointmentData, String>> cellFactory = (TableColumn<AppointmentData, String> param) -> {
            final TableCell<AppointmentData, String> cell = new TableCell<AppointmentData, String>() {
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
                                AppointmentData aData = appointments_tableView.getSelectionModel().getSelectedItem();
                                int num  = appointments_tableView.getSelectionModel().getSelectedIndex();

                                if((num - 1) < -1) {
                                    alert.errorMessage("Please select item first");
                                    return;
                                }

                                Data.temp_appointmentID = aData.getAppointmentID();
                                Data.temp_appointmentName = aData.getName();
                                Data.temp_appointmentGender = aData.getGender();
                                Data.temp_appointmentMobileNumber = String.valueOf(aData.getMobileNumber());
                                Data.temp_appointmentDescription = aData.getDescription();
                                Data.temp_appointmentDiagnosis = aData.getDiagnosis();
                                Data.temp_appointmentTreatment = aData.getTreatment();
                                Data.temp_appointmentAddress = aData.getAddress();
                                Data.temp_appointmentStatus = aData.getStatus();
                                Data.temp_appointmentSpecialized = aData.getSpecialized();
                                Data.temp_appointmentDoctor = aData.getDoctorID();

                                Parent root = FXMLLoader.load(Main.class.getResource("fxml/EditAppointmentForm.fxml"));
                                Stage stage = new Stage();

                                stage.setScene(new Scene(root));
                                stage.show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        removeButton.setOnAction(event -> {
                            try {
                                AppointmentData pData = appointments_tableView.getSelectionModel().getSelectedItem();
                                int num  = appointments_tableView.getSelectionModel().getSelectedIndex();

                                if((num - 1) < -1) {
                                    alert.errorMessage("Please select item first");
                                    return;
                                }

                                String deleteQuery = "UPDATE hospital.appointment SET date_delete = ? WHERE appointment_id = ?";
                                try (Connection connection = Database.getConnection();
                                     PreparedStatement ps = connection.prepareStatement(deleteQuery)) {

                                    if(alert.confirmationMessage("Are you sure you want to delete this patient ?")) {
                                        ps.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                                        ps.setString(2, pData.getAppointmentID());
                                        ps.executeUpdate();

                                        alert.successMessage("Appointment record deleted successfully");
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

        appointments_col_actions.setCellFactory(cellFactory);
        appointments_tableView.setItems(appointmentListData);
    }

    public void profileGenderList() {

        List<String> listS = new ArrayList<>();

        Collections.addAll(listS, Data.gender);

        ObservableList listData = FXCollections.observableArrayList(listS);

        profile_gender.setItems(listData);
    }

    public void profileLabel() {

        String selectData = "SELECT * FROM hospital.admin WHERE admin_id = '" + Data.admin_id + "'";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectData);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                profile_label_adminID.setText(rs.getString("admin_id"));
                profile_label_name.setText(rs.getString("username"));
                profile_label_email.setText(rs.getString("email"));
                profile_label_dateCreated.setText(String.valueOf(rs.getDate("date")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void profileFields() {

        String selectData = "SELECT * FROM hospital.admin WHERE admin_id = '" + Data.admin_id + "'";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectData);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                profile_adminID.setText(rs.getString("admin_id"));
                profile_name.setText(rs.getString("username"));
                profile_email.setText(rs.getString("email"));
                profile_gender.getSelectionModel().select(rs.getString("gender"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<PatientsData> paymentGetData()   {

        ObservableList<PatientsData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM patient WHERE date_delete IS NULL AND status_pay IS Null";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectData)) {

            ResultSet rs = ps.executeQuery();

            PatientsData pData;

            while(rs.next()) {
                pData = new PatientsData(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getString("fullname"),
                        rs.getString("gender"),
                        rs.getString("description"),
                        rs.getString("treatment"),
                        rs.getString("image"),
                        rs.getString("diagnosis"),
                        rs.getString("doctor"),
                        rs.getDate("date")
                );

                listData.add(pData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listData;
    }

    public void paymentShowData() {
        paymentListData = paymentGetData();

        payment_col_patientID.setCellValueFactory(new PropertyValueFactory<>("patientID"));
        payment_col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        payment_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        payment_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        payment_col_diagnosis.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        payment_col_doctor.setCellValueFactory(new PropertyValueFactory<>("doctor"));


        payment_tableView.setItems(paymentListData);
    }

    public void paymentSelectItems() {
        PatientsData pData = payment_tableView.getSelectionModel().getSelectedItem();
        int num = payment_tableView.getSelectionModel().getSelectedIndex();

        // Vérifie qu'une ligne est bien sélectionnée
        if (pData == null || num < 0) {
            return;
        }

        if (pData.getImage() != null) {
            String path = "File:" + pData.getImage();
            image = new Image(path, 134, 90, false, true);
            payment_circleImage.setFill(new ImagePattern(image));

            Data.temp_path = pData.getImage();
        }

        Data.temp_patientID = pData.getPatientID();
        Data.temp_name = pData.getFullName();
        Data.temp_date = String.valueOf(pData.getDate());

        payment_label_patientID.setText(String.valueOf(pData.getPatientID()));
        payment_label_name.setText(pData.getFullName());
        payment_label_date.setText(String.valueOf(pData.getDate()));
        payment_label_doctor.setText(pData.getDoctor());
    }


    public void paymentCheckoutBtn() {

        try{
            Parent root = FXMLLoader.load(Main.class.getResource("fxml/CheckoutPatient.fxml"));
            Stage stage = new Stage();

            stage.setTitle("Hospital Management System | Checkout Patient");

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void switchForm(ActionEvent event)  {

        if(event.getSource() == dashboard_btn) {
            appointments_form.setVisible(false);
            doctors_form.setVisible(false);
            patients_form.setVisible(false);
            dashboard_form.setVisible(true);
            profile_form.setVisible(false);
            payment_form.setVisible(false);

            dashboardDisplayAD();
            dashboardDisplayAP();
            dashboardDisplayTP();
            dashboardDisplayTA();
            dashboardDoctorTableViewData();
            dashboardChartPatientData();
            dashboardChartDoctorData();

            current_form.setText("Dashboard Form");

        } else if(event.getSource() == appointment_btn) {
            dashboard_form.setVisible(false);
            doctors_form.setVisible(false);
            patients_form.setVisible(false);
            appointments_form.setVisible(true);
            profile_form.setVisible(false);
            payment_form.setVisible(false);

            appointmentShowData();
            appointmentActionButton();

            current_form.setText("Appointment Form");

        } else if(event.getSource() == doctor_btn) {
            dashboard_form.setVisible(false);
            appointments_form.setVisible(false);
            patients_form.setVisible(false);
            doctors_form.setVisible(true);
            profile_form.setVisible(false);
            payment_form.setVisible(false);

            doctorShowData();
            doctorActionButton();

            current_form.setText("Doctor Form");
        } else if(event.getSource() == patient_btn) {
            dashboard_form.setVisible(false);
            appointments_form.setVisible(false);
            doctors_form.setVisible(false);
            patients_form.setVisible(true);
            profile_form.setVisible(false);
            payment_form.setVisible(false);

            patientShowData();
            patientActionButton();

            current_form.setText("Patient Form");
        } else if(event.getSource() == profile_setting_btn) {
            dashboard_form.setVisible(false);
            appointments_form.setVisible(false);
            doctors_form.setVisible(false);
            patients_form.setVisible(false);
            profile_form.setVisible(true);
            payment_form.setVisible(false);

            profileGenderList();
            profileLabel();
            profileFields();
            profileDisplayPicture();

            current_form.setText("Profile Form");

        } else if(event.getSource() == payment_btn) {
            dashboard_form.setVisible(false);
            appointments_form.setVisible(false);
            doctors_form.setVisible(false);
            patients_form.setVisible(false);
            profile_form.setVisible(false);
            payment_form.setVisible(true);

            paymentShowData();

            current_form.setText("Payment Form");
        }
    }

    @FXML
    void profileChangePicture(ActionEvent event) {
        FileChooser open = new FileChooser();
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Images", "*.png", "*.jpg", "*.jpeg"));

        File file = open.showOpenDialog(profile_importBtn.getScene().getWindow());

        if(file != null) {
            Data.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 135, 100, false, true);
            profile_circleImage.setFill(new ImagePattern(image));
        } else {
            alert.errorMessage("Please select an image");
        }
    }

    @FXML
    void profileEditBtn(ActionEvent event) {
        if(profile_name.getText().isEmpty()
                || profile_gender.getSelectionModel().getSelectedItem() == null
                || profile_email.getText().isEmpty())
        {
            alert.errorMessage("Please fill all fields");
        } else {
            if(Data.path == null || Data.path.isEmpty()) {
                String sqlUpdate = "UPDATE admin SET username = ?, email = ?, gender = ?, date_modify = ? WHERE admin_id = ?";

                try (Connection connection = Database.getConnection();
                     PreparedStatement ps = connection.prepareStatement(sqlUpdate)) {

                    ps.setString(1, profile_name.getText());
                    ps.setString(2, profile_email.getText());
                    ps.setString(3, profile_gender.getSelectionModel().getSelectedItem());
                    ps.setDate(4, new java.sql.Date(new Date().getTime()));
                    ps.setInt(5, Data.admin_id);

                    ps.executeUpdate();
                    alert.successMessage("Profile updated successfully");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }  else {
                String sqlUpdate = "UPDATE admin SET username = ?, email = ?, gender = ?, date_modify = ?, image = ? WHERE admin_id = ?";

                try (Connection connection = Database.getConnection();
                     PreparedStatement ps = connection.prepareStatement(sqlUpdate)) {

                    ps.setString(1, profile_name.getText());
                    ps.setString(2, profile_email.getText());
                    ps.setString(3, profile_gender.getSelectionModel().getSelectedItem());
                    ps.setDate(4, new java.sql.Date(new Date().getTime()));

                    String path = Data.path;
                    path = path.replace("\\", "\\\\"); // Escape backslashes for SQL
                    java.nio.file.Path transfer = Paths.get(path);

                    String originalFileName = Paths.get(Data.path).getFileName().toString();
                    String fileExtension = "";

                    int dotIndex = originalFileName.lastIndexOf('.');
                    if (dotIndex > 0) {
                        fileExtension = originalFileName.substring(dotIndex).toLowerCase();
                    }

                    // 2. Si pas d'extension ou extension non valide, utiliser .png par défaut
                    if (fileExtension.isEmpty() || !(fileExtension.equals(".png")
                            || fileExtension.equals(".jpg") || fileExtension.equals(".jpeg"))) {
                        fileExtension = ".png";
                    }

                    Path copy = Paths.get("F:\\java\\HospitalManagement\\src\\admin_directory\\" + Data.admin_id + fileExtension);

                    try {
                        Files.copy(transfer, copy, StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception e) {
                        e.printStackTrace();
                        alert.errorMessage("Failed to copy image file");
                        return;
                    }

                    ps.setString(5, copy.toAbsolutePath().toString());
                    ps.setInt(6, Data.admin_id);

                    ps.executeUpdate();
                    alert.successMessage("Profile updated successfully");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void profileDisplayPicture() {

        String selectData = "SELECT image FROM hospital.admin WHERE admin_id = '" + Data.admin_id + "'";
        String temp_path1 = "";
        String temp_path2 = "";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectData);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                temp_path1 = "File:" + rs.getString("image");
                temp_path2 = "File:" + rs.getString("image");

                if(rs.getString("image") != null) {
                    image = new Image(temp_path1, 1003, 21, false, true);
                    top_profile.setFill(new ImagePattern(image));

                    image = new Image(temp_path2, 135, 100, false, true);
                    profile_circleImage.setFill(new ImagePattern(image));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayAdminIDUsername (){

        String sql = "SELECT * FROM hospital.admin WHERE username = '" + Data.admin_username + "'";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()) {
                nav_adminID.setText(resultSet.getString("admin_id"));
                String username = resultSet.getString("username");
                username = username.substring(0, 1).toUpperCase() + username.substring(1);
                nav_username.setText(username);
                top_username.setText(username);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void logoutBtn() {

        try {
            if(alert.confirmationMessage("Are you sure you want to logout?")) {
                Data.admin_id = null;
                Data.admin_username = "";

                Parent root = FXMLLoader.load(Main.class.getResource("fxml/main-view.fxml"));
                Stage stage = new Stage();

                stage.setScene(new Scene(root));
                stage.show();

                logout_btn.getScene().getWindow().hide();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runTime() {

        new Thread(() -> {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> {
                    date_time.setText(format.format(new Date()));
                });
            }
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        runTime();
        displayAdminIDUsername();

        dashboardDisplayAD();
        dashboardDisplayAP();
        dashboardDisplayTP();
        dashboardDisplayTA();
        dashboardDoctorTableViewDataShowData();
        dashboardChartPatientData();
        dashboardChartDoctorData();


        doctorShowData();
        doctorActionButton();


        patientShowData();
        patientActionButton();

        appointmentShowData();
        appointmentActionButton();

        profileGenderList();
        profileLabel();
        profileFields();
        profileDisplayPicture();

        paymentShowData();
    }
}
