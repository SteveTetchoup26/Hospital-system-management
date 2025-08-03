package com.st.hospitalmanagement.controllers;

import com.st.hospitalmanagement.Main;
import com.st.hospitalmanagement.database.AlertMessage;
import com.st.hospitalmanagement.database.Database;
import com.st.hospitalmanagement.models.AppointmentData;
import com.st.hospitalmanagement.models.Data;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class DoctorMainFormController implements Initializable {

    @FXML
    private TextArea appointment_address;

    @FXML
    private TextField appointment_appointmentID;

    @FXML
    private Button appointment_btn;

    @FXML
    private Button appointment_clearBtn;

    @FXML
    private Button appointment_deleteBtn;

    @FXML
    private TextField appointment_description;

    @FXML
    private TextField appointment_diagnosis;

    @FXML
    private ComboBox<String> appointment_gender;

    @FXML
    private DatePicker appointment_schedule;

    @FXML
    private Button appointment_insertBtn;

    @FXML
    private TextField appointment_mobile;

    @FXML
    private TextField appointment_name;

    @FXML
    private ComboBox<String> appointment_status;

    @FXML
    private TextField appointment_treatment;

    @FXML
    private Button appointment_updateBtn;

    @FXML
    private TableColumn<?, ?> appointments_col_actions;

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
    private AnchorPane appointments_form;

    @FXML
    private TableView<AppointmentData> appointments_tableView;

    @FXML
    private Label current_form;

    @FXML
    private Label dashboard_AP;

    @FXML
    private Label dashboard_IP;

    @FXML
    private Label dashboard_TA;

    @FXML
    private Label dashboard_TP;

    @FXML
    private Button dashboard_btn;

    @FXML
    private BarChart<AppointmentData, String> dashboard_chart_DD;

    @FXML
    private AreaChart<AppointmentData, String> dashboard_chart_PD;

    @FXML
    private TableColumn<AppointmentData, String> dashboard_col_appID;

    @FXML
    private TableColumn<AppointmentData, String> dashboard_col_name;

    @FXML
    private TableColumn<AppointmentData, String> dashboard_col_date;

    @FXML
    private TableColumn<AppointmentData, String> dashboard_col_description;

    @FXML
    private TableColumn<AppointmentData, String> dashboard_col_status;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private TableView<AppointmentData> dashboard_tableView;

    @FXML
    private Button logout_btn;

    @FXML
    private Label date_time;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label nav_adminID;

    @FXML
    private Label nav_username;

    @FXML
    private Button patient_btn;

    @FXML
    private Label patients_PA_dateCreated;

    @FXML
    private Label patients_PA_password;

    @FXML
    private Label patients_PA_patientID;

    @FXML
    private Button patients_PI_addBtn;

    @FXML
    private Label patients_PI_address;

    @FXML
    private Label patients_PI_gender;

    @FXML
    private Label patients_PI_number;

    @FXML
    private Label patients_PI_patientName;

    @FXML
    private Button patients_PI_recordBtn;

    @FXML
    private TextArea patients_address;

    @FXML
    private Button patients_btn_confirm;

    @FXML
    private AnchorPane patients_form;

    @FXML
    private TextField patients_number;

    @FXML
    private TextField patients_password;

    @FXML
    private ComboBox<?> patients_patientGender;

    @FXML
    private TextField patients_patientID;

    @FXML
    private TextField patients_patientName;

    @FXML
    private Button profile_setting_btn;

    @FXML
    private Circle top_profile;

    @FXML
    private Label top_username;


    @FXML
    private TextArea profile_address;

    @FXML
    private Circle profile_circleImage;

    @FXML
    private TextField profile_doctorID;

    @FXML
    private Button profile_editBtn;

    @FXML
    private TextField profile_email;

    @FXML
    private AnchorPane profile_form;

    @FXML
    private ComboBox<String> profile_gender;

    @FXML
    private Button profile_importBtn;

    @FXML
    private Label profile_label_dateCreated;

    @FXML
    private Label profile_label_doctorID;

    @FXML
    private Label profile_label_email;

    @FXML
    private Label profile_label_name;

    @FXML
    private TextField profile_name;

    @FXML
    private TextField profile_number;


    @FXML
    private ComboBox<String> profile_specialization;

    @FXML
    private ComboBox<String> profile_status;

    @FXML
    private Label nav_doctorID;

    private Integer appointmentID;

    private final AlertMessage alert = new AlertMessage();

    private Image image;

    public ObservableList<AppointmentData> appointmentListData;

    public ObservableList<AppointmentData> dashboardGetData;


    public void dashboardDisplayIP() {

        String sql = "SELECT COUNT(id) FROM patient WHERE status = 'Inactive' AND doctor = '" + Data.doctor_id    + "'";
        int countIP = 0;
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                countIP = rs.getInt("COUNT(id)");
            }

            dashboard_IP.setText(String.valueOf(countIP));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dashboardDisplayTP() {

        String sql = "SELECT COUNT(id) FROM patient WHERE doctor = '" + Data.doctor_id + "'";
        int countTP = 0;
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                countTP = rs.getInt("COUNT(id)");
            }

            dashboard_TP.setText(String.valueOf(countTP));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dashboardDisplayAP() {

        String sql = "SELECT COUNT(id) FROM patient WHERE status = 'Active' AND doctor = '" + Data.doctor_id    + "'";
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

    public void dashboardDisplayTA() {

        String sql = "SELECT COUNT(id) FROM appointment WHERE status = 'Active' AND doctor = '" + Data.doctor_id    + "'";
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

    public ObservableList<AppointmentData> dashboardAppointmentTableViewData() {

        ObservableList<AppointmentData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointment WHERE doctor = '" + Data.doctor_id + "' AND date_delete IS NULL";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            AppointmentData appData;

            while(rs.next()) {
                appData = new AppointmentData(
                        rs.getString("appointment_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDate("date"),
                        rs.getString("status")
                        );

                listData.add(appData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public void dashboardAppointmentTableViewDataShowData() {
        dashboardGetData = dashboardAppointmentTableViewData();

        dashboard_col_appID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        dashboard_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        dashboard_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        dashboard_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        dashboard_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        dashboard_tableView.setItems(appointmentListData);

    }

    private void patientGenderList() {
        List<String> listG = new ArrayList<>();

        Collections.addAll(listG, Data.gender);

        ObservableList listData = FXCollections.observableArrayList(listG);

        patients_patientGender.setItems(listData);
    }

    public void dashboardChartNOB() {

        dashboard_chart_PD.getData().clear();
        XYChart.Series chart = new XYChart.Series<>();

        String sql = "SELECT DATE(date) as day, COUNT(id) FROM patient WHERE doctor = ? " +
                "GROUP BY day LIMIT 8";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setString(1, Data.doctor_id);


            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                chart.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }


            dashboard_chart_PD.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardChartNOA() {

        dashboard_chart_DD.getData().clear();
        XYChart.Series chart = new XYChart.Series<>();

        String sql = "SELECT DATE(date) as day, COUNT(id) FROM appointment WHERE doctor = ? " +
                "GROUP BY day LIMIT 7";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setString(1, Data.doctor_id);


            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                chart.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }


            dashboard_chart_DD.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void patientConfirmBtn() {

        if (patients_patientID.getText().isEmpty()
                || patients_patientName.getText().isEmpty()
                || patients_patientGender.getSelectionModel().getSelectedItem() == null
                || patients_number.getText().isEmpty()
                || patients_address.getText().isEmpty()
                || patients_password.getText().isEmpty()) {

            alert.errorMessage("Please fill all fields");
        } else {

            patients_PA_patientID.setText(patients_patientID.getText());
            patients_PA_password.setText(patients_password.getText());
            patients_PA_dateCreated.setText(String.valueOf(new java.sql.Date((new Date().getTime()))));

            patients_PI_patientName.setText(patients_patientName.getText());
            patients_PI_gender.setText(patients_patientGender.getSelectionModel().getSelectedItem().toString());
            patients_PI_number.setText(patients_number.getText());
            patients_PI_address.setText(patients_address.getText());
        }
    }

    @FXML
    public void patientAddBtn() {
        if(patients_PA_patientID.getText().isEmpty()
                || patients_PA_password.getText().isEmpty()
                || patients_PA_dateCreated.getText().isEmpty()
                || patients_PI_patientName.getText().isEmpty()
                || patients_PI_gender.getText().isEmpty()
                || patients_PI_number.getText().isEmpty()
                || patients_PI_address.getText().isEmpty()) {

            alert.errorMessage("Something went wrong");
        } else {
            String insertData = "INSERT INTO hospital.patient (patient_id, password, fullname, gender, mobile_number, address, image, description, diagnosis, treatment," +
                    "doctor, specialized, date, date_modify, date_delete, status)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection connection = Database.getConnection();
                 PreparedStatement ps = connection.prepareStatement(insertData);
                 Statement st = connection.createStatement();) {

                String getDoctor = "SELECT * FROM hospital.doctor WHERE doctor_id = '" + nav_doctorID.getText() + "'";
                String checkPatientID = "SELECT * FROM hospital.patient WHERE patient_id = '" + patients_PA_patientID.getText() + "'";
                String doctorName = "";
                String doctorSpecialized = "";

                ResultSet rs = st.executeQuery(getDoctor);

                if(rs.next()) {
                    doctorName = rs.getString("fullname");
                    doctorSpecialized = rs.getString("specialized");
                }

                ResultSet rsCheck = st.executeQuery(checkPatientID);

                if(rsCheck.next()) {
                    alert.errorMessage("This patient ID already exists");
                } else {
                    ps.setString(1, patients_PA_patientID.getText());
                    ps.setString(2, patients_PA_password.getText());
                    ps.setString(3, patients_PI_patientName.getText());
                    ps.setString(4, patients_PI_gender.getText());
                    ps.setString(5, patients_PI_number.getText());
                    ps.setString(6, patients_PI_address.getText());
                    ps.setString(7, "default.png"); // Assuming default image
                    ps.setString(8, ""); // Description
                    ps.setString(9, ""); // Diagnosis
                    ps.setString(10, ""); // Treatment
                    ps.setString(11, nav_doctorID.getText());

                    if(doctorSpecialized.isEmpty()) {
                        doctorSpecialized = "N/A";
                    }

                    ps.setString(12, doctorSpecialized);
                    ps.setDate(13, new java.sql.Date(new Date().getTime()));
                    ps.setDate(14, null); // Date Modify
                    ps.setDate(15, null); // Date Delete
                    ps.setString(16, "Confirm");

                    ps.executeUpdate();

                    patientClearFields();

                    alert.successMessage("Patient added successfully");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void patientRecordBtn() {

        try {
            Parent root = FXMLLoader.load(Main.class.getResource("fxml/RecordPageForm.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Hospital Management System | Record Page Form");

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void patientClearFields() {
        patients_patientID.clear();
        patients_patientName.clear();
        patients_number.clear();
        patients_address.clear();
        patients_password.clear();
        patients_patientGender.getSelectionModel().clearSelection();

        patients_PA_patientID.setText(".........");
        patients_PA_password.setText(".........");
        patients_PA_dateCreated.setText(".........");

        patients_PI_patientName.setText(".........");
        patients_PI_gender.setText(".........");
        patients_PI_number.setText(".........");
        patients_PI_address.setText(".........");
    }

    public void appointmentGenderList() {

        List<String> listG = new ArrayList<>();

        Collections.addAll(listG, Data.gender);

        ObservableList listData = FXCollections.observableArrayList(listG);

        appointment_gender.setItems(listData);
    }

    public void appointmentStatusList() {

        List<String> listS = new ArrayList<>();

        Collections.addAll(listS, Data.status);

        ObservableList listData = FXCollections.observableArrayList(listS);

        appointment_status.setItems(listData);
    }

    public ObservableList<AppointmentData> appointmentGetData() {

        ObservableList<AppointmentData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointment WHERE date_delete IS NULL AND doctor = '" + Data.doctor_id + "'";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            AppointmentData appData;

            while(rs.next()) {
                appData = new AppointmentData(
                        rs.getString("appointment_id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("mobile_number"),
                        rs.getString("description"),
                        rs.getString("diagnosis"),
                        rs.getString("treatment"),
                        rs.getString("address"),
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

    @FXML
    public void appointmentInsertBtn() {

        if(appointment_appointmentID.getText().isEmpty()
                || appointment_name.getText().isEmpty()
                || appointment_gender.getSelectionModel().getSelectedItem() == null
                || appointment_description.getText().isEmpty()
                || appointment_diagnosis.getText().isEmpty()
                || appointment_treatment.getText().isEmpty()
                || appointment_mobile.getText().isEmpty()
                || appointment_address.getText().isEmpty()
                || appointment_status.getSelectionModel().getSelectedItem() == null
                || appointment_schedule.getValue() == null) {

            alert.errorMessage("Please fill all fields");
        } else {
            String checkAppointID = "SELECT appointment_id FROM appointment WHERE appointment_id = ?";
            String sqlInsert = "INSERT INTO appointment (appointment_id, name, gender, description," +
                    "diagnosis, treatment, mobile_number, date, " +
                    "address, status, doctor, specialized, schedule)  " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String getDoctorData = "SELECT * FROM doctor WHERE doctor_id = '" + Data.doctor_id + "'";
            String getSpecialized = "";


            try (Connection connection = Database.getConnection();
                 PreparedStatement ps = connection.prepareStatement(checkAppointID);
                 PreparedStatement psInsert = connection.prepareStatement(sqlInsert);
                 Statement GetDoctor = connection.createStatement()) {

                ps.setString(1, appointment_appointmentID.getText());
                ResultSet rs = ps.executeQuery();

                ResultSet rsDoctor = GetDoctor.executeQuery(getDoctorData);

                if(rsDoctor.next()) {
                    getSpecialized = rsDoctor.getString("specialized");
                }

                if(rs.next()) {
                    alert.errorMessage("This appointment ID already exists");
                } else {

                    psInsert.setString(1, appointment_appointmentID.getText());
                    psInsert.setString(2, appointment_name.getText());
                    psInsert.setString(3, (String) appointment_gender.getSelectionModel().getSelectedItem());
                    psInsert.setString(4, appointment_description.getText());
                    psInsert.setString(5, appointment_diagnosis.getText());
                    psInsert.setString(6, appointment_treatment.getText());
                    psInsert.setString(7, appointment_mobile.getText());
                    psInsert.setDate(8, new java.sql.Date(new Date().getTime()));
                    psInsert.setString(9, appointment_address.getText());
                    psInsert.setString(10, (String) appointment_status.getSelectionModel().getSelectedItem());
                    psInsert.setString(11, Data.doctor_id);
                    psInsert.setString(12, getSpecialized);
                    psInsert.setString(13, "" + appointment_schedule.getValue());

                    psInsert.executeUpdate();

                    appointmentShowData();;

                    appointmentAppointmentID();
                    appointmentClearBtn();
                    alert.successMessage("Appointment added successfully");

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void appointmentClearBtn() {
        appointment_appointmentID.clear();
        appointment_name.clear();
        appointment_address.clear();
        appointment_description.clear();
        appointment_diagnosis.clear();
        appointment_treatment.clear();
        appointment_mobile.clear();
        appointment_gender.getSelectionModel().clearSelection();
        appointment_status.getSelectionModel().clearSelection();
        appointment_schedule.setValue(null);
    }

    @FXML
    public void appointmentUpdateBtn () {
        if(appointment_appointmentID.getText().isEmpty()
                || appointment_name.getText().isEmpty()
                || appointment_gender.getSelectionModel().getSelectedItem() == null
                || appointment_description.getText().isEmpty()
                || appointment_diagnosis.getText().isEmpty()
                || appointment_treatment.getText().isEmpty()
                || appointment_mobile.getText().isEmpty()
                || appointment_address.getText().isEmpty()
                || appointment_status.getSelectionModel().getSelectedItem() == null
                || appointment_schedule.getValue() == null) {

            alert.errorMessage("Please fill all fields");
        } else {
            String sqlUpdate = "UPDATE appointment SET name = '"
                    + appointment_name.getText() + "', gender = '"
                    + appointment_gender.getSelectionModel().getSelectedItem() + "', description = '"
                    + appointment_description.getText() + "', mobile_number = '"
                    + appointment_mobile.getText() + "', address = '"
                    + appointment_address.getText() + "', status = '"
                    + appointment_status.getSelectionModel().getSelectedItem() + "', schedule = '"
                    + appointment_schedule.getValue() + "', date_modify = '"
                    + new java.sql.Date(new Date().getTime())  + "' WHERE appointment_id = '"
                    + appointment_appointmentID.getText() + "'";

            try (Connection connection = Database.getConnection();
                 PreparedStatement updateAppointment = connection.prepareStatement(sqlUpdate)) {

                if(alert.confirmationMessage("Are you sure you want to update this appointment?")) {

                    updateAppointment.executeUpdate();

                    appointmentShowData();
                    appointmentAppointmentID();
                    appointmentClearBtn();
                    alert.successMessage("Appointment updated successfully");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    public void appointmentDeleteBtn() {

        if(appointment_appointmentID.getText().isEmpty()) {
            alert.errorMessage("Please select an appointment to delete");
        } else {
            String sqlDelete = "UPDATE appointment SET date_delete = ? WHERE appointment_id = ?";

            try (Connection connection = Database.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sqlDelete)) {

                if(alert.confirmationMessage("Are you sure you want to delete this appointment?")) {

                    ps.setDate(1, new java.sql.Date(new Date().getTime()));
                    ps.setString(2, appointment_appointmentID.getText());

                    ps.executeUpdate();

                    appointmentShowData();
                    appointmentAppointmentID();
                    appointmentClearBtn();
                    alert.successMessage("Appointment deleted successfully");
                } else {
                    alert.errorMessage("Cancelled");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void appointmentGetAppointmentID () {
        String sql = "SELECT MAX(appointment_id) FROM appointment";
        int tempAppID = 0;
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                tempAppID = rs.getInt("MAX(appointment_id)");
            }

            if(tempAppID == 0) {
                appointmentID += 1;
            } else {
               tempAppID += 1;
            }

            appointmentID = tempAppID;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void appointmentAppointmentID () {
        appointmentGetAppointmentID();

        appointment_appointmentID.setText("" + appointmentID);
        appointment_appointmentID.setDisable(true);
    }

    @FXML
    public void appointmentSelect() {

        AppointmentData appData = appointments_tableView.getSelectionModel().getSelectedItem();
        int num = appointments_tableView.getSelectionModel().getSelectedIndex();

        if((num - 1) < -1) return;

        appointment_appointmentID.setText(appData.getAppointmentID());
        appointment_name.setText(appData.getName());
        appointment_gender.getSelectionModel().select(appData.getGender());
        appointment_description.setText(appData.getDescription());
        appointment_diagnosis.setText(appData.getDiagnosis());
        appointment_status.getSelectionModel().select(appData.getStatus());
        appointment_treatment.setText(appData.getTreatment());
        appointment_mobile.setText(appData.getMobileNumber());
        appointment_address.setText(appData.getAddress());

    }


    public void displayAdminIDNumberName() {

        String name = Data.doctor_name;
        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        nav_username.setText(name);
        nav_doctorID.setText(Data.doctor_id);
        top_username.setText(name);
    }

    public void profileSpecializedList() {

        List<String> listS = new ArrayList<>();

        Collections.addAll(listS, Data.specialized);

        ObservableList listData = FXCollections.observableArrayList(listS);

        profile_specialization.setItems(listData);
    }

    public void profileGenderList() {

        List<String> listS = new ArrayList<>();

        Collections.addAll(listS, Data.gender);

        ObservableList listData = FXCollections.observableArrayList(listS);

        profile_gender.setItems(listData);
    }

    public void profileStatusList() {

        List<String> listS = new ArrayList<>();

        Collections.addAll(listS, Data.status);

        ObservableList listData = FXCollections.observableArrayList(listS);

        profile_status.setItems(listData);
    }

    public void profileFields() {

        String selectData = "SELECT * FROM hospital.doctor WHERE doctor_id = '" + Data.doctor_id + "'";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectData);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                profile_doctorID.setText(rs.getString("doctor_id"));
                profile_name.setText(rs.getString("fullname"));
                profile_number.setText(rs.getString("mobile_number"));
                profile_email.setText(rs.getString("email"));
                profile_address.setText(rs.getString("address"));
                profile_specialization.getSelectionModel().select(rs.getString("specialized"));
                profile_gender.getSelectionModel().select(rs.getString("gender"));
                profile_status.getSelectionModel().select(rs.getString("status"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void profileLabel() {
        String selectData = "SELECT * FROM hospital.doctor WHERE doctor_id = '" + Data.doctor_id + "'";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectData);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                profile_label_doctorID.setText(rs.getString("doctor_id"));
                profile_label_name.setText(rs.getString("fullname"));
                profile_label_email.setText(rs.getString("email"));
                profile_label_dateCreated.setText(String.valueOf(rs.getDate("date")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void profileEditBtn() {
        if(profile_name.getText().isEmpty()
                || profile_number.getText().isEmpty()
                || profile_address.getText().isEmpty()
                || profile_specialization.getSelectionModel().getSelectedItem() == null
                || profile_gender.getSelectionModel().getSelectedItem() == null
                || profile_status.getSelectionModel().getSelectedItem() == null
                || profile_email.getText().isEmpty()
                || profile_doctorID.getText().isEmpty())
        {
            alert.errorMessage("Please fill all fields");
        } else {
            if(Data.path == null || Data.path.isEmpty()) {
                String sqlUpdate = "UPDATE doctor SET fullname = ?, mobile_number = ?, email = ?, " +
                        "address = ?, specialized = ?, gender = ?, status = ?, date_modify = ? WHERE doctor_id = ?";

                try (Connection connection = Database.getConnection();
                        PreparedStatement ps = connection.prepareStatement(sqlUpdate)) {

                    ps.setString(1, profile_name.getText());
                    ps.setString(2, profile_number.getText());
                    ps.setString(3, profile_email.getText());
                    ps.setString(4, profile_address.getText());
                    ps.setString(5, profile_specialization.getSelectionModel().getSelectedItem());
                    ps.setString(6, profile_gender.getSelectionModel().getSelectedItem());
                    ps.setString(7, profile_status.getSelectionModel().getSelectedItem());
                    ps.setDate(8, new java.sql.Date(new Date().getTime()));
                    ps.setString(9, Data.doctor_id);

                    ps.executeUpdate();
                    alert.successMessage("Profile updated successfully");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }  else {
                String sqlUpdate = "UPDATE doctor SET fullname = ?, mobile_number = ?, email = ?, " +
                        "address = ?, specialized = ?, gender = ?, status = ?, date_modify = ?, image = ? WHERE doctor_id = ?";

                try (Connection connection = Database.getConnection();
                     PreparedStatement ps = connection.prepareStatement(sqlUpdate)) {

                    ps.setString(1, profile_name.getText());
                    ps.setString(2, profile_number.getText());
                    ps.setString(3, profile_email.getText());
                    ps.setString(4, profile_address.getText());
                    ps.setString(5, profile_specialization.getSelectionModel().getSelectedItem());
                    ps.setString(6, profile_gender.getSelectionModel().getSelectedItem());
                    ps.setString(7, profile_status.getSelectionModel().getSelectedItem());
                    ps.setDate(8, new java.sql.Date(new Date().getTime()));

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

                    Path copy = Paths.get("F:\\java\\HospitalManagement\\src\\directory\\" + Data.doctor_id + fileExtension);

                    try {
                        Files.copy(transfer, copy, StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception e) {
                        e.printStackTrace();
                        alert.errorMessage("Failed to copy image file");
                        return;
                    }

                    ps.setString(9, copy.toAbsolutePath().toString());
                    ps.setString(10, Data.doctor_id);

                    ps.executeUpdate();
                    alert.successMessage("Profile updated successfully");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void profileChangePicture() {

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

    public void profileDisplayPicture() {

        String selectData = "SELECT image FROM hospital.doctor WHERE doctor_id = '" + Data.doctor_id + "'";

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


    @FXML
    public void switchForm(ActionEvent event) {

        if(event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            patients_form.setVisible(false);
            appointments_form.setVisible(false);
            profile_form.setVisible(false);

            current_form.setText("Dashboard Form");

        } else if(event.getSource() == appointment_btn) {
            appointments_form.setVisible(true);
            dashboard_form.setVisible(false);
            patients_form.setVisible(false);
            profile_form.setVisible(false);

            current_form.setText("Appointment Form");
        } else if(event.getSource() == patient_btn) {
            patients_form.setVisible(true);
            dashboard_form.setVisible(false);
            appointments_form.setVisible(false);
            profile_form.setVisible(false);

            current_form.setText("Patient Form");

        } else if(event.getSource() == profile_setting_btn) {
            profile_form.setVisible(true);
            dashboard_form.setVisible(false);
            appointments_form.setVisible(false);
            patients_form.setVisible(false);

            current_form.setText("Profile Form");
        }
    }

    public void logoutBtn() {

        try {
            if(alert.confirmationMessage("Are you sure you want to logout?")) {
                Data.doctor_id = "";
                Data.doctor_name = "";
                Data.temp_date = "";
                Data.temp_name = "";
                Data.temp_gender = "";
                Data.temp_number = "";
                Data.temp_address = "";
                Data.temp_status = "";
                Data.path = "";
                Data.temp_patientID = 0;


                Parent root = FXMLLoader.load(Main.class.getResource("fxml/doctor-view.fxml"));
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
        displayAdminIDNumberName();
        runTime();

        appointmentShowData();
        appointmentGenderList();
        appointmentStatusList();
        appointmentAppointmentID();

        patientGenderList();

        profileSpecializedList();
        profileGenderList();
        profileStatusList();
        profileFields();
        profileLabel();
        profileDisplayPicture();

        dashboardDisplayIP();
        dashboardDisplayTP();
        dashboardDisplayAP();
        dashboardDisplayTA();
        dashboardAppointmentTableViewDataShowData();

        dashboardChartNOB();
        dashboardChartNOA();
    }
}
