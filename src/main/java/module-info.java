module com.st.hospitalmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;


    opens com.st.hospitalmanagement to javafx.fxml;
    exports com.st.hospitalmanagement;
    exports com.st.hospitalmanagement.controllers;
    opens com.st.hospitalmanagement.controllers to javafx.fxml;
}