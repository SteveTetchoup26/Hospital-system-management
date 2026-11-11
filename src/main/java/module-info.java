module com.st.hospitalmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;
    // Icônes Gluon (<Icon>) utilisées dans les FXML
    requires com.gluonhq.charm.glisten;


    opens com.st.hospitalmanagement to javafx.fxml;
    exports com.st.hospitalmanagement;
    exports com.st.hospitalmanagement.controllers;
    opens com.st.hospitalmanagement.controllers to javafx.fxml;
    // Nécessaire pour que les TableView (PropertyValueFactory) lisent les modèles
    opens com.st.hospitalmanagement.models to javafx.base;
}