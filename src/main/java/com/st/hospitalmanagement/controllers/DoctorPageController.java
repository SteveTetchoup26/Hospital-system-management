package com.st.hospitalmanagement.controllers;

import com.st.hospitalmanagement.Main;
import com.st.hospitalmanagement.database.AlertMessage;
import com.st.hospitalmanagement.database.Database;
import com.st.hospitalmanagement.models.Data;
import com.st.hospitalmanagement.models.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class DoctorPageController implements Initializable {

    @FXML
    private Button login_btn;

    @FXML
    private CheckBox login_checkBox;

    @FXML
    private TextField login_doctorID;

    @FXML
    private AnchorPane login_form;

    @FXML
    private PasswordField login_password;

    @FXML
    private Hyperlink login_registerHere;

    @FXML
    private TextField login_showPassword;

    @FXML
    private ComboBox<?> login_user;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button register_btnSignUp;

    @FXML
    private CheckBox register_checkBox;

    @FXML
    private TextField register_doctorID;

    @FXML
    private TextField register_email;

    @FXML
    private AnchorPane register_form;

    @FXML
    private TextField register_fullname;

    @FXML
    private Hyperlink register_loginHere;

    @FXML
    private PasswordField register_password;

    @FXML
    private TextField register_showPassword;

    private final AlertMessage alert = new AlertMessage();

    @FXML
    void loginAccount(ActionEvent event) {

        if(login_doctorID.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            String checkUser = "SELECT * FROM hospital.doctor WHERE doctor_id = ? AND password = ?";

            try (Connection connection = Database.getConnection();
                 PreparedStatement checkStmt = connection.prepareStatement(checkUser)) {

                if(!login_showPassword.isVisible()) {
                    if(!login_showPassword.getText().equals(login_password.getText())) {
                        login_showPassword.setText(login_password.getText());
                    }
                } else {
                    if(!login_showPassword.getText().equals(login_password.getText())) {
                        login_password.setText(login_showPassword.getText());
                    }
                }

                checkStmt.setString(1, login_doctorID.getText());
                checkStmt.setString(2, login_password.getText());

                ResultSet rs = checkStmt.executeQuery();

                if(rs.next()) {
                    Data.doctor_id = rs.getString("doctor_id");
                    Data.doctor_name = rs.getString("fullname");

                    alert.successMessage("Login successfully");

                    Parent root = FXMLLoader.load(Main.class.getResource("fxml/DoctorMainForm.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Hospital Management System | Doctor Main Form");

                    stage.setScene(new Scene(root));
                    stage.show();

                    login_btn.getScene().getWindow().hide();
                } else {
                    alert.errorMessage("Doctor not found");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void loginShowPassword(ActionEvent event) {
        if(login_checkBox.isSelected()) {
            login_showPassword.setText(login_password.getText());
            login_showPassword.setVisible(true);
            login_password.setVisible(false);
        } else {
            login_password.setText(login_showPassword.getText());
            login_showPassword.setVisible(false);
            login_password.setVisible(true);
        }
    }

    @FXML
    void registerAccount(ActionEvent event) {
        if(register_email.getText().isEmpty() || register_fullname.getText().isEmpty() || register_doctorID.getText().isEmpty() ||
                register_password.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
            return;
        }

        String checkDoctorID = "SELECT doctor_id FROM hospital.doctor WHERE doctor_id = ?";
        String insertData = "INSERT INTO hospital.doctor (email, fullname, password, doctor_id, date, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement checkStmt = connection.prepareStatement(checkDoctorID);
             PreparedStatement insertStmt = connection.prepareStatement(insertData)) {

            if(!register_showPassword.isVisible()) {
                if(!register_showPassword.getText().equals(register_password.getText())) {
                    register_showPassword.setText(register_password.getText());
                }
            } else {
                if(!register_showPassword.getText().equals(register_password.getText())) {
                    register_password.setText(register_showPassword.getText());
                }
            }

            checkStmt.setString(1, register_doctorID.getText());
            ResultSet rs = checkStmt.executeQuery();

            if(rs.next()) {
                alert.errorMessage(register_doctorID.getText() + "is already taken");
                return;
            }


            java.util.Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());


            insertStmt.setString(1, register_email.getText());
            insertStmt.setString(2, register_fullname.getText());
            insertStmt.setString(3, register_password.getText());
            insertStmt.setString(4, register_doctorID.getText());
            insertStmt.setString(5, String.valueOf(sqlDate));
            insertStmt.setString(6, "Confirm");

            int rowsAffected = insertStmt.executeUpdate();

            if(rowsAffected > 0) {
                alert.successMessage("Registered successfully!");
                clearRegisterFields();

                login_form.setVisible(true);
                register_form.setVisible(false);
            } else {
                alert.errorMessage("Some problem wrong");
            }

        } catch (SQLException e) {
            alert.errorMessage("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void registerDoctorID() {
        String doctorID = "DID-";
        int tempID = 0;
        String sql = "SELECT MAX(id) FROM hospital.doctor";

        try(Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                tempID = rs.getInt("MAX(id)");
            }

            if(tempID == 0) {
                tempID += 1;
                doctorID += tempID;
            } else {
                doctorID += (tempID + 1);
            }

            register_doctorID.setText(doctorID);
            register_doctorID.setDisable(true);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void registerShowPassword(ActionEvent event) {
        if(register_checkBox.isSelected()) {
            register_showPassword.setText(register_password.getText());
            register_showPassword.setVisible(true);
            register_password.setVisible(false);
        } else {
            register_password.setText(register_showPassword.getText());
            register_showPassword.setVisible(false);
            register_password.setVisible(true);
        }
    }

    @FXML
    void switchForm(ActionEvent event) {
        if(event.getSource() == login_registerHere) {
            login_form.setVisible(false);
            register_form.setVisible(true);
        } else if(event.getSource() == register_loginHere) {
            login_form.setVisible(true);
            register_form.setVisible(false);
        }
    }

    private void clearRegisterFields() {
        register_email.clear();
        register_fullname.clear();
        register_password.clear();
        register_doctorID.clear();
        register_showPassword.clear();
    }

    public void userList() {

        List<String> listU = new ArrayList<>();

        Collections.addAll(listU, Users.user);

        ObservableList listData = FXCollections.observableArrayList(listU);
        login_user.setItems(listData);
    }

    @FXML
    public void switchPage() {

        if(login_user.getSelectionModel().getSelectedItem() == "Admin Portal") {
            try {
                Parent root = FXMLLoader.load(Main.class.getResource("fxml/main-view.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Hospital Management");

                stage.setMinHeight(550);
                stage.setMinWidth(350);

                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if(login_user.getSelectionModel().getSelectedItem() == "Doctor Portal") {
            try {
                Parent root = FXMLLoader.load(Main.class.getResource("fxml/doctor-view.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Hospital Management");

                stage.setMinHeight(550);
                stage.setMinWidth(350);

                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if(login_user.getSelectionModel().getSelectedItem() == "Patient Portal") {
            try {
                Parent root = FXMLLoader.load(Main.class.getResource("fxml/patient-view.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Patient");

                stage.setMinHeight(550);
                stage.setMinWidth(350);

                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        login_user.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userList();
        registerDoctorID();
    }
}
