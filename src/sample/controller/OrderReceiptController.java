package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import sample.Main;
import sample.listeners.ReceiptListener;
import sample.model.Order;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.text.DecimalFormat;


public class OrderReceiptController //Ο OrderReceiptController διαχειρίζεται το FXML orderReceipt
{
    private static DecimalFormat df2 = new DecimalFormat("#00.00");
    @FXML
    private Label orderNumber;

    @FXML
    private Label firstname;

    @FXML
    private Label lastname;

    @FXML
    private Label address;

    @FXML
    private Label city;

    @FXML
    private Label country;

    @FXML
    private GridPane orderGrid;

    @FXML
    private Label totalCost;

    @FXML
    private Label trackingNumber;

    @FXML
    private VBox printNode;

    @FXML
    public void printInvoice(MouseEvent mouseEvent)//Click Event καλεί την printNode()
    {
        printNode();
    }

    @FXML
    public void backToShop(MouseEvent mouseEvent) {receiptListener.returnToShop();}

    @FXML
    public void copyTrackingNumber(MouseEvent mouseEvent)//Αντιγράφει το tracking number.
    {
        StringSelection stringSelection = new StringSelection(trackingNumber.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection,null);
    }
    private ReceiptListener receiptListener;

    public void setContent(Order order, String trackingnumber, ReceiptListener receiptListener)//Ορίζει τα δεδομένα του orderReceipt
    {
        this.receiptListener = receiptListener;
        orderNumber.setText("#"+trackingnumber);
        firstname.setText(order.getFirstname());
        lastname.setText(order.getLastname());
        address.setText(order.getAddress());
        city.setText(order.getCity());
        country.setText(order.getCountry());
        boolean success=true;
        for (int i=0; i<order.getItems().getItems().size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Main.class.getResource("views/checkoutItem.fxml"));
                AnchorPane anchorPane = null;
                anchorPane = fxmlLoader.load();
                CheckoutItemController checkoutItemController = fxmlLoader.getController();
                checkoutItemController.setData(order.getItems().getItems().get(i));
                orderGrid.add(anchorPane, 0, i);
                orderGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                orderGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                orderGrid.setMaxWidth(Region.USE_PREF_SIZE);

                orderGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                orderGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                orderGrid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(4));
            } catch (IOException e) {
                System.out.println("Order Receipt Error");
                e.printStackTrace();
                success = false;
            }
        }
        if(success){
            totalCost.setText(df2.format(order.getItems().getTotalCost())+ Main.CURRENCY);
        }
        trackingNumber.setText(trackingnumber);
    }
    private void printNode()//Εκτυπώνει σε pdf την απόδειξη της παραγγελίας
    {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if(printerJob!=null){

            Scene scene = printNode.getScene();
            Window window = scene.getWindow();
            printerJob.showPrintDialog(window);
            printerJob.printPage(printNode);
            printerJob.endJob();
        }
    }



}
