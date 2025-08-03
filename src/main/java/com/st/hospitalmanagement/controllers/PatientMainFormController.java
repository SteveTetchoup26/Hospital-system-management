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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PatientMainFormController implements Initializable {

    @FXML
    private Label appointment_ad_address;

    @FXML
    private Button appointment_ad_bookBtn;

    @FXML
    private Label appointment_ad_description;

    @FXML
    private Label appointment_ad_doctorName;

    @FXML
    private Label appointment_ad_gender;

    @FXML
    private Label appointment_ad_name;

    @FXML
    private Label appointment_ad_number;

    @FXML
    private Label appointment_ad_schedule;

    @FXML
    private Label appointment_ad_specialization;

    @FXML
    private Button appointment_btn;

    @FXML
    private Button appointment_d_clearBtn;

    @FXML
    private Button appointment_d_confirmBtn;

    @FXML
    private TextArea appointment_d_description;

    @FXML
    private ComboBox<String> appointment_d_doctor;

    @FXML
    private DatePicker appointment_d_schedule;

    @FXML
    private AnchorPane appointments_form;

    @FXML
    private Label current_form;

    @FXML
    private Label date_time;

    @FXML
    private Button doctor_btn;

    @FXML
    private AnchorPane doctors_form;

    @FXML
    private TableColumn<AppointmentData, String> home_appointment_col_appID;

    @FXML
    private TableColumn<AppointmentData, String> home_appointment_col_description;

    @FXML
    private TableColumn<AppointmentData, String> home_appointment_col_diagnosis;

    @FXML
    private TableColumn<AppointmentData, String> home_appointment_col_doctor;

    @FXML
    private TableColumn<AppointmentData, String> home_appointment_col_schedule;

    @FXML
    private TableColumn<AppointmentData, String> home_appointment_col_treament;

    @FXML
    private TableView<AppointmentData> home_appointment_tableView;

    @FXML
    private Button home_btn;

    @FXML
    private Circle home_doctor_circleImage;

    @FXML
    private Label home_doctor_email;

    @FXML
    private Label home_doctor_name;

    @FXML
    private Label home_doctor_number;

    @FXML
    private Label home_doctor_specialization;

    @FXML
    private AnchorPane home_form;

    @FXML
    private TableColumn<PatientsData, String> home_patient_col_dateDischarge;

    @FXML
    private TableColumn<PatientsData, String>home_patient_col_dateIn;

    @FXML
    private TableColumn<PatientsData, String>home_patient_col_description;

    @FXML
    private TableColumn<PatientsData, String>home_patient_col_diagnosis;

    @FXML
    private TableColumn<PatientsData, String>home_patient_col_treatment;

    @FXML
    private TableView<PatientsData> home_patient_tableView;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label nav_patientID;

    @FXML
    private TextArea profile_address;

    @FXML
    private Circle profile_circleImage;

    @FXML
    private Button profile_editBtn;

    @FXML
    private AnchorPane profile_form;

    @FXML
    private ComboBox<String> profile_gender;

    @FXML
    private Button profile_importBtn;

    @FXML
    private Label profile_label_dateCreated;

    @FXML
    private Label profile_label_name;

    @FXML
    private Label profile_label_number;

    @FXML
    private Label profile_label_patientID;

    @FXML
    private TextField profile_name;

    @FXML
    private TextField profile_number;

    @FXML
    private TextField profile_password;

    @FXML
    private TextField profile_patientID;

    @FXML
    private Button profile_setting_btn;

    @FXML
    private Circle top_profile;

    @FXML
    private Label top_username;

    @FXML
    private GridPane doctors_gridPane;

    @FXML
    private ScrollPane doctors_scrollPane;

    private AlertMessage alert = new AlertMessage();

    private Image image;

    private ObservableList<PatientsData> patientListData;

    private ObservableList<AppointmentData> appointmentListData;

    private ObservableList<DoctorData> doctorListData;

    public ObservableList<PatientsData> patientGetData()  {

        ObservableList<PatientsData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM patient WHERE patient_id = '" + Data.patient_id + "' AND date_delete IS NULL";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectData)) {

            ResultSet rs = ps.executeQuery();

            PatientsData pData;

            while(rs.next()) {
                pData = new PatientsData(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getString("description"),
                        rs.getString("diagnosis"),
                        rs.getString("treatment"),
                        rs.getDate("date")
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

        home_patient_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        home_patient_col_diagnosis.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        home_patient_col_treatment.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        home_patient_col_dateIn.setCellValueFactory(new PropertyValueFactory<>("date"));


        home_patient_tableView.setItems(patientListData);
    }

    public ObservableList<AppointmentData> appointmentGetData() {

        ObservableList<AppointmentData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointment WHERE patient_id = '" + Data.patient_id + "' AND date_delete IS NULL";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            AppointmentData appData;

            while(rs.next()) {
                appData = new AppointmentData(
                        rs.getString("appointment_id"),
                        rs.getString("description"),
                        rs.getString("diagnosis"),
                        rs.getString("treatment"),
                        rs.getString("doctor"),
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

        home_appointment_col_appID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        home_appointment_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        home_appointment_col_diagnosis.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        home_appointment_col_doctor.setCellValueFactory(new PropertyValueFactory<>("doctorID"));
        home_appointment_col_schedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        home_appointment_col_treament.setCellValueFactory(new PropertyValueFactory<>("treatment"));

        home_appointment_tableView.setItems(appointmentListData);

    }

    public void homeDoctorInfoDisplay() {

        String sql = "SELECT * FROM patient WHERE patient_id = '" + Data.patient_id + "' AND date_delete IS NULL";
        String doctorID = "";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();) {

            if(rs.next()) {
                doctorID = rs.getString("doctor");
            }

            String doctorSql = "SELECT * FROM doctor WHERE doctor_id = '" + doctorID + "' AND date_delete IS NULL";

            try (PreparedStatement psDoctor = connection.prepareStatement(doctorSql)) {
                ResultSet rsDoctor = psDoctor.executeQuery();

                if(rsDoctor.next()) {
                    home_doctor_name.setText(rsDoctor.getString("fullname"));
                    home_doctor_email.setText(rsDoctor.getString("email"));
                    home_doctor_number.setText(rsDoctor.getString("mobile_number"));
                    home_doctor_specialization.setText(rsDoctor.getString("specialized"));

                    String path = rsDoctor.getString("image");

                    if(path != null && !path.isEmpty()) {
                        path = path.replace("\\", "\\\\");
                        image = new Image("File:" + path, 116, 75, false, true);
                        home_doctor_circleImage.setFill(new ImagePattern(image));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void appointmentInfoDisplay() {

        String sql = "SELECT * FROM patient WHERE patient_id = '" + Data.patient_id + "' AND date_delete IS NULL";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();) {

            if(rs.next()) {
                appointment_ad_name.setText(rs.getString("fullname"));
                appointment_ad_gender.setText(rs.getString("gender"));
                appointment_ad_number.setText(rs.getString("mobile_number"));
                appointment_ad_address.setText(rs.getString("address"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void appointmentConfirmBtn() {

        if(appointment_d_description.getText().isEmpty() || appointment_d_schedule.getValue() == null || appointment_d_doctor.getSelectionModel().isEmpty()) {
            alert.errorMessage("Please fill all the fields");
        } else {

            appointment_ad_description.setText(appointment_d_description.getText());
            appointment_ad_schedule.setText(appointment_d_schedule.getValue().toString());
            appointment_ad_doctorName.setText(appointment_d_doctor.getSelectionModel().getSelectedItem().toString());

            String sql = "SELECT * FROM doctor WHERE doctor_id = '" + appointment_d_doctor.getSelectionModel().getSelectedItem() + "'";

            try (Connection connection = Database.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery();) {

                if(rs.next()) {
                    appointment_ad_specialization.setText(rs.getString("specialized"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void appointmentGetDoctor() {

        String sql = "SELECT * FROM doctor WHERE date_delete IS NULL";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();) {

            ObservableList<String> doctorList = FXCollections.observableArrayList();

            while(rs.next()) {
                doctorList.add(rs.getString("doctor_id"));
            }

            appointment_d_doctor.setItems(doctorList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void appointmentClearBtn () {
        appointment_d_description.clear();
        appointment_d_schedule.setValue(null);
        appointment_d_doctor.getSelectionModel().clearSelection();

        appointment_ad_description.setText("");
        appointment_ad_schedule.setText("");
        appointment_ad_doctorName.setText("");
        appointment_ad_specialization.setText("");
    }

    public void appointmentBookBtn () {
        if(appointment_ad_description.getText().isEmpty()
                || appointment_ad_specialization.getText().isEmpty()
                || appointment_ad_schedule.getText().isEmpty()
                || appointment_ad_doctorName.getText().isEmpty()
        ) {
            alert.errorMessage("Invalid");
        } else {
            String sql = "INSERT INTO appointment (appointment_id, patient_id, name, gender, " +
                    "description, mobile_number, address, date, doctor, specialized, schedule, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String selectApp = "SELECT MAX(appointment_id) FROM appointment WHERE date_delete IS NULL";
            int appointmentID = 0;

            try (Connection connection = Database.getConnection();
                 PreparedStatement ps = connection.prepareStatement(selectApp);
                 ResultSet rs = ps.executeQuery()){

                if(rs.next()) {
                    appointmentID = rs.getInt("MAX(appointment_id)") ;

                    appointmentID++;

                    try (PreparedStatement psInsert = connection.prepareStatement(sql)) {
                        psInsert.setInt(1, appointmentID);   //     A revoir
                        psInsert.setInt(2, Data.patient_id);
                        psInsert.setString(3, appointment_ad_name.getText());
                        psInsert.setString(4, appointment_ad_gender.getText());
                        psInsert.setString(5, appointment_ad_description.getText());
                        psInsert.setString(6, appointment_ad_number.getText());
                        psInsert.setString(7, appointment_ad_address.getText());
                        psInsert.setDate(8, new java.sql.Date(new Date().getTime()));
                        psInsert.setString(9, appointment_d_doctor.getSelectionModel().getSelectedItem());
                        psInsert.setString(10, appointment_ad_specialization.getText());
                        psInsert.setString(11, appointment_ad_schedule.getText());
                        psInsert.setString(12, "Active");

                        if(alert.confirmationMessage("Are you sure you want to book this appointment?")) {
                            psInsert.executeUpdate();

                            alert.successMessage("Appointment booked successfully");

                            appointmentClearBtn();
                        } else {
                            alert.errorMessage("Appointment booking cancelled");
                        }
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void profileGenderList() {

        List<String> listS = new ArrayList<>();

        Collections.addAll(listS, Data.gender);

        ObservableList listData = FXCollections.observableArrayList(listS);

        profile_gender.setItems(listData);
    }

    public void profileDisplay() {

        String selectData = "SELECT * FROM hospital.patient WHERE patient_id = '" + Data.patient_id + "'";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectData);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                profile_label_patientID.setText(rs.getString("patient_id"));
                profile_label_name.setText(rs.getString("fullname"));
                profile_label_number.setText(rs.getString("mobile_number"));
                profile_label_dateCreated.setText(rs.getDate("date").toString());

                profile_address.setText(rs.getString("address"));
                profile_patientID.setText(rs.getString("patient_id"));
                profile_name.setText(rs.getString("fullname"));
                profile_number.setText(rs.getString("mobile_number"));
                profile_gender.getSelectionModel().select(rs.getString("gender"));
                profile_password.setText(rs.getString("password"));

                String path = rs.getString("image");
                File file = new File(path);
                if(file.exists()) {
                    image = new Image(file.toURI().toString(), 135, 100, false, true);
                    profile_circleImage.setFill(new ImagePattern(image));
                } else {
                   profile_circleImage.setFill(Color.LIGHTGRAY);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<DoctorData> doctorGetData()  {

        ObservableList<DoctorData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM doctor WHERE status = 'Active' AND date_delete IS NULL";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectData)) {

            ResultSet rs = ps.executeQuery();

            DoctorData pData;

            while(rs.next()) {
                pData = new DoctorData(
                        rs.getInt("id"),
                        rs.getString("doctor_id"),
                        rs.getString("fullname"),
                        rs.getString("specialized"),
                        rs.getString("email"),
                        rs.getString("image")
                );

                listData.add(pData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listData;
    }

    public void doctorShowData() {
        doctorListData = doctorGetData();

        doctors_gridPane.getChildren().clear();

        int column = 0;
        int row = 0;

        for (DoctorData data : doctorListData) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/doctorCard.fxml"));
                StackPane stackPane = fxmlLoader.load();

                DoctorCardController cardController = fxmlLoader.getController();
                cardController.setData(data);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                doctors_gridPane.add(stackPane, column++, row);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void logoutBtn(ActionEvent event) {
        try {
            if(alert.confirmationMessage("Are you sure you want to logout?")) {
                Data.doctor_id = "";
                Data.doctor_name = "";

                Parent root = FXMLLoader.load(Main.class.getResource("fxml/patient-view.fxml"));
                Stage stage = new Stage();

                stage.setScene(new Scene(root));
                stage.show();

                logout_btn.getScene().getWindow().hide();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                || profile_number.getText().isEmpty()
                || profile_address.getText().isEmpty()
                || profile_gender.getSelectionModel().getSelectedItem() == null
                || profile_patientID.getText().isEmpty()
                || profile_password.getText().isEmpty())
        {
            alert.errorMessage("Please fill all fields");
        } else {
            if(Data.path == null || Data.path.isEmpty()) {
                String sqlUpdate = "UPDATE patient SET fullname = ?, mobile_number = ?," +
                        "address = ?, password = ?, gender = ?, date_modify = ? WHERE patient_id = ?";

                try (Connection connection = Database.getConnection();
                     PreparedStatement ps = connection.prepareStatement(sqlUpdate)) {

                    ps.setString(1, profile_name.getText());
                    ps.setString(2, profile_number.getText());
                    ps.setString(3, profile_address.getText());
                    ps.setString(4, profile_password.getText());
                    ps.setString(5, profile_gender.getSelectionModel().getSelectedItem());
                    ps.setDate(6, new java.sql.Date(new Date().getTime()));
                    ps.setString(7, profile_patientID.getText());

                    ps.executeUpdate();
                    alert.successMessage("Profile updated successfully");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }  else {
                String sqlUpdate = "UPDATE patient SET fullname = ?, mobile_number = ?," +
                        "address = ?, password = ?, gender = ?, date_modify = ?, image = ? WHERE patient_id = ?";

                try (Connection connection = Database.getConnection();
                     PreparedStatement ps = connection.prepareStatement(sqlUpdate)) {

                    ps.setString(1, profile_name.getText());
                    ps.setString(2, profile_number.getText());
                    ps.setString(3, profile_address.getText());
                    ps.setString(4, profile_password.getText());
                    ps.setString(5, profile_gender.getSelectionModel().getSelectedItem());
                    ps.setDate(6, new java.sql.Date(new Date().getTime()));

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

                    Path copy = Paths.get("F:\\java\\HospitalManagement\\src\\patient_directory\\" + Data.patient_id + fileExtension);

                    try {
                        Files.copy(transfer, copy, StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception e) {
                        e.printStackTrace();
                        alert.errorMessage("Failed to copy image file");
                        return;
                    }

                    ps.setString(7, copy.toAbsolutePath().toString());
                    ps.setInt(8, Data.patient_id);

                    ps.executeUpdate();
                    alert.successMessage("Profile updated successfully");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void profileDisplayPicture() {

        String selectData = "SELECT image FROM hospital.patient WHERE patient_id = '" + Data.patient_id + "'";


        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectData);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                if(rs.getString("image") != null) {
                    String path = rs.getString("image");

                    File file = new File(path);
                    if(file.exists()) {
                        image = new Image(file.toURI().toString(), 1003, 21, false, true);
                        top_profile.setFill(new ImagePattern(image));

                        image = new Image(file.toURI().toString(), 135, 100, false, true);
                        profile_circleImage.setFill(new ImagePattern(image));
                    } else {
                        top_profile.setFill(Color.LIGHTGRAY);
                        profile_circleImage.setFill(Color.LIGHTGRAY);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayPatientIDName (){

        String sql = "SELECT * FROM hospital.patient WHERE patient_id = '" + Data.patient_id + "'";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()) {
                nav_patientID.setText(resultSet.getString("patient_id"));
                top_username.setText(resultSet.getString("fullname"));
            }

        } catch (SQLException e) {
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

    @FXML
    void switchForm(ActionEvent event) {
        if(event.getSource() == home_btn) {
            home_form.setVisible(true);
            appointments_form.setVisible(false);
            profile_form.setVisible(false);
            doctors_form.setVisible(false);

            patientShowData();
            appointmentShowData();
            homeDoctorInfoDisplay();

            current_form.setText("Home Form");

        } else if(event.getSource() == appointment_btn) {
            appointments_form.setVisible(true);
            home_form.setVisible(false);
            profile_form.setVisible(false);
            doctors_form.setVisible(false);

            appointmentInfoDisplay();

            current_form.setText("Appointment Form");
        } else if(event.getSource() == profile_setting_btn) {
            profile_form.setVisible(true);
            home_form.setVisible(false);
            appointments_form.setVisible(false);
            doctors_form.setVisible(false);

            current_form.setText("Profile Form");
        } else if(event.getSource() == doctor_btn) {
            profile_form.setVisible(false);
            home_form.setVisible(false);
            appointments_form.setVisible(false);
            doctors_form.setVisible(true);

            current_form.setText("Doctor Form");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        runTime();
        patientShowData();
        appointmentShowData();
        homeDoctorInfoDisplay();

        appointmentInfoDisplay();
        appointmentGetDoctor();

        profileGenderList();
        profileDisplay();
        profileDisplayPicture();

        doctorShowData();

        displayPatientIDName();
    }
}
