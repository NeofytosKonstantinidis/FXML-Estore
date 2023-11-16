package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.listeners.MyListener;
import sample.model.Item;

import java.text.DecimalFormat;


public class ItemController//Ο ItemController διαχειρίζεται το FXML item
{
    private static DecimalFormat df2 = new DecimalFormat("00.00");
    @FXML
    private Label itemName;

    @FXML
    private Label itemPrice;

    @FXML
    private ImageView itemImage;

    @FXML
    private void clickBtn(MouseEvent mouseEvent)//Είναι Click Listener στο κουμπί που έχει κάθε αντικείμενο στην σελίδα του Shop, ώστε να προσθέσει το αντικείμενο στο Cart
    { myListener.onClickListener(item);}

    private Item item;
    private MyListener myListener;

    public void setData(Item item, MyListener myListener)//Ορίζει τα δεδομένα του αντικειμένου
    {
        this.item = item;
        this.myListener = myListener;
        itemName.setText(item.getName());
        itemPrice.setText(df2.format(item.getPrice())+ Main.CURRENCY);
        Image image = new Image(Main.class.getResourceAsStream(item.getImgSrc()));
        itemImage.setImage(image);

    }

}
