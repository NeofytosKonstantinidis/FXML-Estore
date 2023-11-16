package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Connect;
import sample.Main;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TrackOrderController //Ο TrackOrderController διαχειρίζεται το FXML trackOrder
{

    @FXML
    private TextField searchOrderInput;

    @FXML
    private ScrollPane trackingContent;

    @FXML
    public void searchOrderClicked(MouseEvent mouseEvent) //Click Event παίρνει την φράση που δόθηκε και την στέλνει στην μέθοδο loadOrder()
    {
        String search = searchOrderInput.getText();
        if(search.length()>0){
            loadOrder(search);
        }
    }
    private void loadOrder(String search) //Ορίζει τα δεδομένα του cartItem
    {
        try{
            boolean found=false;
            Connect connect = new Connect();
            if(connect.connectDB()){
                ResultSet rs = connect.getSelect("SELECT shoporder.status, shoporder.order_time, shipingdetails.firstname, shipingdetails.lastname, shipingdetails.address, shipingdetails.city, shipingdetails.country FROM tracking JOIN shoporder ON tracking.orderid=shoporder.id JOIN shipingdetails ON tracking.orderid=shipingdetails.orderid WHERE tracking.tracking_number='"+search+"' ");
                System.out.println("SELECT shoporder.status, shoporder.order_time, shipingdetails.firstname, shipingdetails.lastname, shipingdetails.address, shipingdetails.city, shipingdetails.country FROM tracking JOIN shoporder ON tracking.orderid=shoporder.id JOIN shipingdetails ON tracking.orderid=shipingdetails.orderid WHERE tracking.tracking_number='"+search+"' ");
                while(rs.next())
                {
                    found = true;
                    int status = rs.getInt("status");
                    Timestamp timestamp = rs.getTimestamp("order_time");
                    String tmstampStr = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(timestamp);
                    String firstname = rs.getString("firstname");
                    String lastname = rs.getString("lastname");;
                    String address = rs.getString("address");;
                    String city = rs.getString("city");;
                    String country = rs.getString("country");;
                    try{
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(Main.class.getResource("views/resultOrder.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        ResultOrderController resultOrderController = fxmlLoader.getController();
                        resultOrderController.setData(status,tmstampStr, lastname+" "+firstname, address+", "+city+", "+country);
                        trackingContent.setContent(anchorPane);
                    }catch(IOException e){

                    }

                }
                if(!found){
                    System.out.println("No Results found.");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}
