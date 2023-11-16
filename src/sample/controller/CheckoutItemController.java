package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Main;
import sample.model.CartItem;
import java.text.DecimalFormat;

public class CheckoutItemController //Ο CheckoutItemController διαχειρίζεται το FXML checkoutItem
{
    private static DecimalFormat df2 = new DecimalFormat("00.00");
    @FXML
    private Label itemQuantity;
    @FXML
    private Label itemName;
    @FXML
    private ImageView itemImage;
    @FXML
    private Label itemPrice;
    @FXML
    private Label itemSubtotal;

    public void setData(CartItem cartItem)//Ορίζει τα δεδομένα του cartItem
    {
        itemQuantity.setText(cartItem.getQuantity()+"x");
        itemName.setText(cartItem.getItem().getName());
        Image image =new Image(Main.class.getResourceAsStream(cartItem.getItem().getImgSrc()));
        itemImage.setImage(image);
        itemPrice.setText(df2.format(cartItem.getItem().getPrice())+Main.CURRENCY);
        itemSubtotal.setText(df2.format((cartItem.getItem().getPrice()*cartItem.getQuantity()))+Main.CURRENCY);
    }
}
