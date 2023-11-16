package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.listeners.CartListener;
import sample.model.Item;

import java.text.DecimalFormat;

public class CartItemController //Ο CartItemController διαχειρίζεται το FXML cartItem
{
    private static DecimalFormat df2 = new DecimalFormat("00.00");

    @FXML
    private Label quantity;

    @FXML
    private Label name;

    @FXML
    private Label price;

    @FXML
    private ImageView cartItemImage;

    @FXML
    private void addCartQuantity(javafx.scene.input.MouseEvent mouseEvent)//Είναι Click Listener στο καλάθι προϊόντων, στο βελάκι για αύξηση ποσότητας προϊόντος
    {cListener.addCartQuantity(item,this);}

    @FXML
    private void reduceCartQuantity(javafx.scene.input.MouseEvent mouseEvent)//Είναι Click Listener στο καλάθι προϊόντων, στο βελάκι για μείωση ποσότητας προϊόντος
    {cListener.reduceCartQuantity(item, this);}

    @FXML
    private void removeCartItem(javafx.scene.input.MouseEvent mouseEvent)////Είναι Click Listener στο καλάθι προϊόντων, στο Χ για αφαίρεση προϊόντος
    {cListener.removeCartItem(item);}

    private Item item;
    private CartListener cListener;
    private int quantitynum;

    public void setCartItem(Item item, int quantity, CartListener cListener)//Ορίζει τα δεδομένα του cartItem
    {
        this.item = item;
        this.cListener = cListener;
        name.setText(item.getName());
        quantitynum=quantity;
        this.quantity.setText(quantity+"x");
        this.price.setText(df2.format((item.getPrice()*quantity))+Main.CURRENCY);
        Image image =new Image(Main.class.getResourceAsStream(item.getImgSrc()));
        cartItemImage.setImage(image);
    }
    public void setQuantity(int diff)//Αλλάζει την ποσότητα του cartItem
    {
        if((quantitynum+diff)>0){
            quantitynum+=diff;
        }
        this.quantity.setText(quantitynum+"x");
        this.price.setText(df2.format((item.getPrice()*quantitynum))+Main.CURRENCY);
    }

}
