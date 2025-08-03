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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private CheckBox login_checkBox;

    @FXML
    private AnchorPane login_form;

    @FXML
    private Button login_btn;

    @FXML
    private PasswordField login_password;

    @FXML
    private Hyperlink login_registerHere;

    @FXML
    private TextField login_showPassword;

    @FXML
    private ComboBox<?> login_user;

    @FXML
    private TextField login_username;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button register_btnSignUp;

    @FXML
    private CheckBox register_checkBox;

    @FXML
    private TextField register_email;

    @FXML
    private AnchorPane register_form;

    @FXML
    private Hyperlink register_loginHere;

    @FXML
    private PasswordField register_password;

    @FXML
    private TextField register_showPassword;

    @FXML
    private TextField register_username;


    private AlertMessage alert = new AlertMessage();

    public void loginAccount() {

        if(login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
            return;
        } else {
            String checkUser = "SELECT * FROM hospital.admin WHERE username = ? AND password = ?";

            try (Connection connection = Database.getConnection();
                 PreparedStatement checkStmt = connection.prepareStatement(checkUser);) {

                if(!login_showPassword.isVisible()) {
                    if(!login_showPassword.getText().equals(login_password.getText())) {
                        login_showPassword.setText(login_password.getText());
                    }
                } else {
                    if(!login_showPassword.getText().equals(login_password.getText())) {
                        login_password.setText(login_showPassword.getText());
                    }
                }

                checkStmt.setString(1, login_username.getText());
                checkStmt.setString(2, login_password.getText());

                ResultSet rs = checkStmt.executeQuery();

                if(rs.next()) {
                    Data.admin_username = login_username.getText();
                    Data.admin_id = rs.getInt("admin_id");

                    alert.successMessage("Login successfully");

                    Parent root = FXMLLoader.load(Main.class.getResource("fxml/AdminMainForm.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Hospital Management System | Admin Portal");

                    stage.setScene(new Scene(root));
                    stage.show();

                    login_btn.getScene().getWindow().hide();
                } else {
                    alert.errorMessage("User not found");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void registerAccount() {

        if(register_email.getText().isEmpty() || register_username.getText().isEmpty() ||
                register_password.getText().isEmpty()) {
            alert.errorMessage("Veuillez remplir tous les champs");
            return;
        }

        String checkUsername = "SELECT username FROM hospital.admin WHERE username = ?";
        String insertData = "INSERT INTO hospital.admin (email, username, password, date) VALUES (?, ?, ?, ?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement checkStmt = connection.prepareStatement(checkUsername);
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

            checkStmt.setString(1, register_username.getText());
            ResultSet rs = checkStmt.executeQuery();

            if(rs.next()) {
                alert.errorMessage("Ce nom d'utilisateur existe déjà");
                return;
            }


            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());


            insertStmt.setString(1, register_email.getText());
            insertStmt.setString(2, register_username.getText());
            insertStmt.setString(3, register_password.getText());
            insertStmt.setString(4, String.valueOf(sqlDate));

            int rowsAffected = insertStmt.executeUpdate();

            if(rowsAffected > 0) {
                alert.successMessage("Inscription réussie!");
                clearRegisterFields();

                login_form.setVisible(true);
                register_form.setVisible(false);
            } else {
                alert.errorMessage("Échec de l'inscription");
            }

        } catch (SQLException e) {
            alert.errorMessage("Erreur base de données: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearRegisterFields() {
        register_email.clear();
        register_username.clear();
        register_password.clear();
        register_showPassword.clear();
    }
    @FXML
    public void switchForm(ActionEvent event) {

        if(event.getSource() == login_registerHere) {
            login_form.setVisible(false);
            register_form.setVisible(true);
        } else if(event.getSource() == register_loginHere) {
            login_form.setVisible(true);
            register_form.setVisible(false);
        }
    }

    @FXML
    public void loginShowPassword() {

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
    public void registerShowPassword() {

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

    public void userList() {

        List<String> listU = new ArrayList<>();

        for(String data : Users.user) {
            listU.add(data);
        }

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
    }
}