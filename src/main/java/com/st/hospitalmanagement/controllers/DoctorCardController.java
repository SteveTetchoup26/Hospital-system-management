package com.st.hospitalmanagement.controllers;

import com.st.hospitalmanagement.models.DoctorData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DoctorCardController implements Initializable {

    @FXML
    private Circle doctor_circleImage;

    @FXML
    private Label doctor_doctorID;

    @FXML
    private Label doctor_email;

    @FXML
    private Label doctor_fullName;

    @FXML
    private Label doctor_specialization;

    private Image image;

    public void setData(DoctorData data) {

        if(data.getImage() != null) {
            File file = new File(data.getImage());
            if(file.exists()) {
                image = new Image(file.toURI().toString(), 52, 52, false, true);
                doctor_circleImage.setFill(new ImagePattern(image));
            } else {
                doctor_circleImage.setFill(Color.LIGHTGRAY);
            }
        }

        doctor_fullName.setText(data.getFullname());
        doctor_doctorID.setText(data.getDoctorID());
        doctor_specialization.setText(data.getSpecialized());
        doctor_email.setText(data.getEmail());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
