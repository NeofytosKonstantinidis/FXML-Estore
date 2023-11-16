package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    public static final String CURRENCY ="€";
    public static Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception//Η start() τρέχει με την έναρξη της εφαρμογής και ορίζει ως Scene το login page
    {
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("views/logIn.fxml"));
        primaryStage.setTitle("e-Shop");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void openStore()//Η openStore() ορίζει ως Scene την μεριά του καταστήματος
    {
        try{
            FXMLLoader storeFXML = new FXMLLoader();
            storeFXML.setLocation(Main.class.getResource("views/storeView.fxml"));
            Scene storeScene = new Scene(storeFXML.load());
            window.setScene(storeScene);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void openCustomer()//Η openCustomer() ορίζει ως Scene την μεριά του πελάτη
    {
        try{
            FXMLLoader storeFXML = new FXMLLoader();
            storeFXML.setLocation(Main.class.getResource("views/shop.fxml"));
            Scene customerScene = new Scene(storeFXML.load());
            window.setScene(customerScene);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void openMainScene(){
        try{
            FXMLLoader storeFXML = new FXMLLoader();
            storeFXML.setLocation(Main.class.getResource("views/logIn.fxml"));
            Scene mainScene = new Scene(storeFXML.load());
            window.setScene(mainScene);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
