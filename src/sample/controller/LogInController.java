package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sample.Main;

public class LogInController //Ο LogInController διαχειρίζεται το FXML logIn
{

    @FXML
    private VBox storeSide;
    @FXML
    private  VBox customerSide;
    @FXML
    public void storeSideClicked(MouseEvent mouseEvent)//Click Listener για να φορτώσει την μεριά του καταστήματος
    {Main.openStore();}
    @FXML
    public void storeSideHovered(MouseEvent mouseEvent)//Εμφανισιακό: Όταν ο χρήστης τοποθετεί το mouse πάνω απο το κουμπί να αλλάζει το χρώμα του
    {
        storeSide.setStyle("-fx-border-color: #c4c4c4; -fx-background-color: #eaeaea");
    }
    @FXML
    public void storeSideExited(MouseEvent mouseEvent)//Εμφανισιακό: Επαναφορά του χρώματος
    {
        storeSide.setStyle("");
    }
    @FXML
    public void customerSideClicked(MouseEvent mouseEvent)//Click Listener για να φορτώσει την μεριά του πελάτη
    {Main.openCustomer();}
    @FXML
    public void customerSideHovered(MouseEvent mouseEvent)//Εμφανισιακό: Όταν ο χρήστης τοποθετεί το mouse πάνω απο το κουμπί να αλλάζει το χρώμα του
    {
        customerSide.setStyle("-fx-border-color: #c4c4c4; -fx-background-color: #eaeaea");
    }
    @FXML
    public void customerSideExited(MouseEvent mouseEvent)//Εμφανισιακό: Επαναφορά του χρώματος
    {
        customerSide.setStyle("");
    }
}
