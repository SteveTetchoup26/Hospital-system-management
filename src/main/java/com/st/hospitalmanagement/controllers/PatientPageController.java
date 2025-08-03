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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class PatientPageController implements Initializable {

    @FXML
    private Button login_btn;

    @FXML
    private CheckBox login_checkBox;

    @FXML
    private AnchorPane login_form;

    @FXML
    private PasswordField login_password;

    @FXML
    private TextField login_patientID;

    @FXML
    private TextField login_showPassword;

    @FXML
    private ComboBox<?> login_user;

    @FXML
    private AnchorPane main_form;

    private final AlertMessage alert = new AlertMessage();

    @FXML
    void loginAccount(ActionEvent event) {
        if(login_patientID.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            String checkUser = "SELECT patient_id FROM hospital.patient WHERE patient_id = ? AND password = ? AND date_delete IS NULL";

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

                checkStmt.setString(1, login_patientID.getText());
                checkStmt.setString(2, login_password.getText());

                ResultSet rs = checkStmt.executeQuery();

                if(rs.next()) {
                    Data.patient_id = Integer.parseInt(login_patientID.getText());
                    alert.successMessage("Login successfully");

                    Parent root = FXMLLoader.load(Main.class.getResource("fxml/PatientMainForm.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Hospital Management System | Patient Main Form");

                    stage.setScene(new Scene(root));
                    stage.show();

                    login_btn.getScene().getWindow().hide();
                } else {
                    alert.errorMessage("Patient not found");
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

    public void userList() {

        List<String> listU = new ArrayList<>();

        Collections.addAll(listU, Users.user);

        ObservableList listData = FXCollections.observableArrayList(listU);
        login_user.setItems(listData);
    }

    @FXML
    void switchPage(ActionEvent event) {
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
    }
}
