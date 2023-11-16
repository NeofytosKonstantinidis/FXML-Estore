package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import sample.Main;
import sample.listeners.CheckoutListener;
import sample.model.CartItem;
import sample.model.CartItems;
import sample.model.Order;

import java.io.IOException;
import java.text.DecimalFormat;

public class CheckoutController //Ο CheckoutController διαχειρίζεται το FXML checkout
{
    private static DecimalFormat df2 = new DecimalFormat("00.00");
    @FXML
    private TextField firstnameInput;
    @FXML
    private TextField lastnameInput;
    @FXML
    private TextField addressInput;
    @FXML
    private TextField cityInput;
    @FXML
    private TextField countryInput;
    @FXML
    private GridPane checkoutGrid;
    @FXML
    private Label totalCost;
    @FXML
    public void buyClicked(MouseEvent mouseEvent)//Είναι Click Listener στο checkout Page για την ολοκλήρωση της παραγγελίας
    {checkoutListener.buyClicked();}
    private CheckoutListener checkoutListener;
    private CartItems items;
    public void sentItems(CartItems cartItems,CheckoutListener checkoutListener){
        this.checkoutListener =checkoutListener;
        items = cartItems;
        boolean success=true;
        for (int i=0; i<items.getItems().size(); i++){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Main.class.getResource("views/checkoutItem.fxml"));
                AnchorPane anchorPane = null;
                anchorPane = fxmlLoader.load();
                CheckoutItemController checkoutItemController = fxmlLoader.getController();
                checkoutItemController.setData(items.getItems().get(i));
                checkoutGrid.add(anchorPane,0,i);
                checkoutGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                checkoutGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                checkoutGrid.setMaxWidth(Region.USE_PREF_SIZE);

                checkoutGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                checkoutGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                checkoutGrid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane,new Insets(4));
            } catch (IOException e) {
                e.printStackTrace();
                success=false;
            }
            if(success){
                totalCost.setText(df2.format(items.getTotalCost())+Main.CURRENCY);
            }
        }
    }
    public Order getOrder()//Επιστρέφει τα δεδομένα της παραγγελίας
    {
        String firstname= firstnameInput.getText();
        String lastname = lastnameInput.getText();
        String address = addressInput.getText();
        String city = cityInput.getText();
        String country = countryInput.getText();
        if(firstname.length()>0 && lastname.length()>0 && address.length()>0 && city.length()>0 && country.length()>0)
        {

            Order order = new Order(firstname,lastname,address,city,country,new CartItems(items.getItems()));
            return order;
        }
        return null;
    }

}
