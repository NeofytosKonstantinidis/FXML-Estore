package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.listeners.OrderItemListener;

public class OrderItemController //Ο CartItemController διαχειρίζεται το FXML cartItem
{
    @FXML
    private Label dateLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label statusLabel;

    @FXML
    private AnchorPane background;

    @FXML
    public void orderClicked(MouseEvent mouseEvent)//Click Event για να φορτώσει την παραγγελία που επιλέχθηκε αναλυτικώτερα
    {orderItemListener.orderClicked(id, this);}
    OrderItemListener orderItemListener;
    private int id;
    public void setData(int id, String date, String name, String price, int status, OrderItemListener orderItemListener, AnchorPane background)//Ορίζει τα δεδομένα του αντικειμένου
    {
        this.id = id;
        this.orderItemListener = orderItemListener;
        this.background = background;
        dateLabel.setText(date);
        nameLabel.setText(name);
        priceLabel.setText(price);
        switch (status){
            case 0:
                statusLabel.setText("Processing");
                statusLabel.setStyle("-fx-text-fill: #dc000a");
                break;
            case 1:
                statusLabel.setText("Packing");
                statusLabel.setStyle("-fx-text-fill: #ce7201");
                break;
            case 2:
                statusLabel.setText("Shipping");
                statusLabel.setStyle("-fx-text-fill: #fdb602");
                break;
            case 3:
                statusLabel.setText("Arriving");
                statusLabel.setStyle("-fx-text-fill: #0266fd");
                break;
            case 4:
                statusLabel.setText("Completed");
                statusLabel.setStyle("-fx-text-fill: #52c100");
                break;
        }

    }
    public void setSelected()//Ορίζει το εμφανισιακό του αντικειμένου ως επιλεγμένο
    {
        background.setStyle("-fx-border-color: #000000");
    }
    public void setUnselected()//Ορίζει το εμφανισιακό του αντικειμένου ως μη επιλεγμένο
    {
        background.setStyle("");
    }
}
