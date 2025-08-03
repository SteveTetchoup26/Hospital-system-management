package com.st.hospitalmanagement.controllers;

import com.st.hospitalmanagement.database.AlertMessage;
import com.st.hospitalmanagement.database.Database;
import com.st.hospitalmanagement.models.Data;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;

public class CheckoutPatientController implements Initializable {

    @FXML
    private DatePicker checkout_checkoutDate;

    @FXML
    private DatePicker checkout_date;

    @FXML
    private Label checkout_doctor;

    @FXML
    private ImageView checkout_imageView;

    @FXML
    private Label checkout_name;

    @FXML
    private Label checkout_patientID;

    @FXML
    private Button checkout_payBtn;

    @FXML
    private Button checkout_countBtn;

    @FXML
    private Label checkout_totalDay;

    @FXML
    private Label checkout_totalPrice;

    private Image image;

    private AlertMessage alert = new AlertMessage();

    public void countBtn() {
        long countDays = 0;
        if(checkout_date.getValue() != null && checkout_checkoutDate.getValue() != null) {
            countDays = ChronoUnit.DAYS.between(checkout_date.getValue(), checkout_checkoutDate.getValue());
        } else {
            alert.errorMessage("SOmething went wrong");
        }

        double price = 20.5;

        double totalPrice = countDays * price;

        checkout_totalDay.setText(String.valueOf(countDays));
        checkout_totalPrice.setText(String.valueOf(totalPrice));
    }

    public void payBtn () {

        if(checkout_checkoutDate.getValue() == null || checkout_totalDay.getText().isEmpty() || checkout_totalPrice.getText().isEmpty()) {
            alert.errorMessage("Invalid");
            return;
        }
        String sql = "INSERT INTO payment (patient_id, doctor, total_days, total_price, date, date_checkout, date_pay)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String updatePatient = "UPDATE patient SET status_pay = ? WHERE patient_id = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             PreparedStatement psUpdate = connection.prepareStatement(updatePatient)) {

            ps.setString(1, checkout_patientID.getText());
            ps.setString(2, checkout_doctor.getText());
            ps.setString(3, checkout_totalDay.getText());
            ps.setString(4, checkout_totalPrice.getText());
            ps.setDate(5, java.sql.Date.valueOf(checkout_date.getValue()));
            ps.setDate(6, java.sql.Date.valueOf(checkout_checkoutDate.getValue()));
            ps.setDate(7, java.sql.Date.valueOf(LocalDate.now()));

            if(alert.confirmationMessage("Are you sure you want to pay ? ")) {
                ps.executeUpdate();

                psUpdate.setString(1, "Paid");
                psUpdate.setString(2, checkout_patientID.getText());

                psUpdate.executeUpdate();

                alert.successMessage("Payment successful");
            } else {
                alert.errorMessage("Payment cancelled");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayFields() {

        checkout_patientID.setText(String.valueOf(Data.temp_patientID));
        checkout_name.setText(Data.temp_name);
        checkout_date.setValue(LocalDate.parse(Data.temp_date));
        checkout_date.setDisable(true);
        checkout_doctor.setText(Data.temp_doctorID);

        image = new Image("File:" + Data.temp_path, 127, 143, false, true);
        checkout_imageView.setImage(image);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayFields();
    }
}
