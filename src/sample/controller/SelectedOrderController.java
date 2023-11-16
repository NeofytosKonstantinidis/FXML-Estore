package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import sample.Connect;
import sample.Main;
import sample.model.Order;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class SelectedOrderController //Ο SelectedOrderController διαχειρίζεται το FXML selectedOrder
{
    private static DecimalFormat df2 = new DecimalFormat("###,#00.00");
    @FXML
    private RadioButton processingButton;
    @FXML
    private RadioButton packingButton;
    @FXML
    private RadioButton shippingButton;
    @FXML
    private RadioButton arrivedButton;

    @FXML
    private Label orderNumber;
    @FXML
    private Label dateTime;
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
    private Label totalCost;

    @FXML
    private GridPane orderGrid;
    @FXML
    public void updateOrderStatus(MouseEvent mouseEvent) //Click Listener ελέγχει ποιό status είναι επιλεγμένο στην παραγγελία και το ενημερώνει στην βάση δεδομένων
    {
        int orderStatus=0;
        if(processingButton.isSelected()){
            orderStatus=1;
        }else if (packingButton.isSelected()){
            orderStatus=2;
        }else if(shippingButton.isSelected()){
            orderStatus=3;
        }else if(arrivedButton.isSelected()){
            orderStatus=4;
        }else{
            orderStatus=0;
        }
        if (id>-1){
            try{
                Connect connect = new Connect();
                connect.connectDB();
                if(connect.Update("UPDATE shoporder SET status="+orderStatus+" WHERE id="+id)>0){
                    JOptionPane.showMessageDialog(new JFrame(),"Status successfully changed.");
                    storeViewController.loadOrdersList(true);
                }else{
                    JOptionPane.showMessageDialog(new JFrame(),"Something went wrong.");
                }
            }catch(SQLException e){
                e.printStackTrace();
            }

        }
    }
    private int id=-1;
    private StoreViewController storeViewController = null;

    public void setData(Order order, String trackingNumber, int status,int id,String order_time, StoreViewController storeViewController) //Ορίζει τα δεδομένα της επιλεγμένης παραγγελίας
    {
        this.id = id;
        if(order!=null)
        {
            this.storeViewController = storeViewController;
            orderNumber.setText(trackingNumber);
            dateTime.setText(order_time);
            firstname.setText(order.getFirstname());
            lastname.setText(order.getLastname());
            address.setText(order.getAddress());
            city.setText(order.getCity());
            country.setText(order.getCountry());
            boolean success=true;
            for (int i = 0; i<order.getItems().getItems().size(); i++){
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
                    System.out.println("Selected Order Error");
                    e.printStackTrace();
                    success = false;
                }
            }
            if(success){
                totalCost.setText(df2.format(order.getItems().getTotalCost())+ Main.CURRENCY);
            }
            switch (status){
                case 1:
                    processingButton.setSelected(true);
                    break;
                case 2:
                    packingButton.setSelected(true);
                    break;
                case 3:
                    shippingButton.setSelected(true);
                    break;
                case 4:
                    arrivedButton.setSelected(true);
                    break;
                default:
                    break;
            }
        }else {
            System.out.println("Error: Null Order");
        }
    }


}
