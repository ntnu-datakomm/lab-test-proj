module no.ntnu.lab {
  requires javafx.controls;
  requires javafx.fxml;

  opens no.ntnu.lab to javafx.fxml;
  exports no.ntnu.lab;
}