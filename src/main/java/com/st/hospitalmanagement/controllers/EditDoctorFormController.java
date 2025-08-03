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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;


import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

public class EditDoctorFormController implements Initializable {

    @FXML
    private TextArea editDoctor_address;

    @FXML
    private Button editDoctor_cancelBtn;

    @FXML
    private TextField editDoctor_doctorID;

    @FXML
    private Button editDoctor_editBtn;

    @FXML
    private TextField editDoctor_email;

    @FXML
    private TextField editDoctor_fullname;

    @FXML
    private ComboBox<String> editDoctor_gender;

    @FXML
    private ImageView editDoctor_imageView;

    @FXML
    private Button editDoctor_importBtn;

    @FXML
    private TextField editDoctor_number;

    @FXML
    private TextField editDoctor_password;

    @FXML
    private ComboBox<String> editDoctor_specialized;

    @FXML
    private ComboBox<String> editDoctor_status;

    private Image image;

    private AlertMessage alert = new AlertMessage();

    public void setField() {
        editDoctor_doctorID.setText(String.valueOf(Data.temp_doctorID));
        editDoctor_fullname.setText(Data.temp_doctorName);
        editDoctor_number.setText(Data.temp_doctorNumber);
        editDoctor_email.setText(Data.temp_doctorEmail);
        editDoctor_address.setText(Data.temp_doctorAddress);
        editDoctor_status.getSelectionModel().select(Data.temp_doctorStatus);
        editDoctor_specialized.getSelectionModel().select(Data.temp_doctorSpecialized);
        editDoctor_gender.getSelectionModel().select(Data.temp_doctorGender);
        editDoctor_password.setText(Data.temp_doctorPassword);

        image = new Image("File:" + Data.temp_doctorImagePath, 96, 102, false, true);
        editDoctor_imageView.setImage(image);
    }

    public void doctorGenderList() {

        List<String> listG = new ArrayList<>()  ;

        Collections.addAll(listG, Data.gender);

        ObservableList listData = FXCollections.observableArrayList(listG);

        editDoctor_gender.setItems(listData);
    }

    public void doctorStatusList() {

        List<String> listS = new ArrayList<>();

        Collections.addAll(listS, Data.status);

        ObservableList listData = FXCollections.observableArrayList(listS);

        editDoctor_status.setItems(listData);
    }

    public void doctorSpecializedList() {

        List<String> listS = new ArrayList<>();

        Collections.addAll(listS, Data.specialized);

        ObservableList listData = FXCollections.observableArrayList(listS);

        editDoctor_specialized.setItems(listData);
    }

    public void importBtn () {
        FileChooser open = new FileChooser();
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Images", "*.png", "*.jpg", "*.jpeg"));

        File file = open.showOpenDialog(editDoctor_importBtn.getScene().getWindow());

        if(file != null) {
            Data.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 96, 102, false, true);
            editDoctor_imageView.setImage(image);
        } else {
            alert.errorMessage("Please select an image");
        }
    }

    public void editBtn() {

        if(editDoctor_fullname.getText().isEmpty() || editDoctor_number.getText().isEmpty()
                || editDoctor_email.getText().isEmpty() || editDoctor_address.getText().isEmpty()
                || editDoctor_specialized.getSelectionModel().isEmpty() || editDoctor_status.getSelectionModel().isEmpty()
                || editDoctor_gender.getSelectionModel().isEmpty() || editDoctor_password.getText().isEmpty()
                || editDoctor_doctorID.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            if(Data.path == null || Data.path.isEmpty()) {
                String sqlUpdate = "UPDATE doctor SET fullname = ?, mobile_number = ?, email = ?, " +
                        "address = ?, specialized = ?, gender = ?, status = ?, date_modify = ? WHERE doctor_id = ?";

                try (Connection connection = Database.getConnection();
                     PreparedStatement ps = connection.prepareStatement(sqlUpdate)) {

                    ps.setString(1, editDoctor_fullname.getText());
                    ps.setString(2, editDoctor_number.getText());
                    ps.setString(3, editDoctor_email.getText());
                    ps.setString(4, editDoctor_address.getText());
                    ps.setString(5, editDoctor_specialized.getSelectionModel().getSelectedItem());
                    ps.setString(6, editDoctor_gender.getSelectionModel().getSelectedItem());
                    ps.setString(7, editDoctor_status.getSelectionModel().getSelectedItem());
                    ps.setDate(8, new java.sql.Date(new Date().getTime()));
                    ps.setString(9, editDoctor_doctorID.getText());

                    try {
                        if(alert.confirmationMessage("Are you sure you want to update this doctor?")) {
                            ps.executeUpdate();
                            alert.successMessage("Doctor updated successfully");
                        } else {
                            alert.errorMessage("Update cancelled");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }  else {
                String sqlUpdate = "UPDATE doctor SET fullname = ?, mobile_number = ?, email = ?, " +
                        "address = ?, specialized = ?, gender = ?, status = ?, date_modify = ?, image = ? WHERE doctor_id = ?";

                try (Connection connection = Database.getConnection();
                     PreparedStatement ps = connection.prepareStatement(sqlUpdate)) {

                    ps.setString(1, editDoctor_fullname.getText());
                    ps.setString(2, editDoctor_number.getText());
                    ps.setString(3, editDoctor_email.getText());
                    ps.setString(4, editDoctor_address.getText());
                    ps.setString(5, editDoctor_specialized.getSelectionModel().getSelectedItem());
                    ps.setString(6, editDoctor_gender.getSelectionModel().getSelectedItem());
                    ps.setString(7, editDoctor_status.getSelectionModel().getSelectedItem());
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

                    Path copy = Paths.get("F:\\java\\HospitalManagement\\src\\doctor_directory\\" + Data.doctor_id + fileExtension);

                    try {
                        Files.copy(transfer, copy, StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception e) {
                        e.printStackTrace();
                        alert.errorMessage("Failed to copy image file");
                        return;
                    }

                    ps.setString(9, copy.toAbsolutePath().toString());
                    ps.setString(10, Data.doctor_id);

                    try {
                        if(alert.confirmationMessage("Are you sure you want to update this doctor?")) {
                            ps.executeUpdate();
                            alert.successMessage("Doctor updated successfully");
                        } else {
                            alert.errorMessage("Update cancelled");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void cancelBtn() {

        if(alert.confirmationMessage("Are you sure you want to cancel?")) {
            editDoctor_cancelBtn.getScene().getWindow().hide();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setField();

        doctorSpecializedList();
        doctorGenderList();
        doctorStatusList();
    }
}
