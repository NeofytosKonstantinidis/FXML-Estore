package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import sample.Connect;
import sample.Main;
import sample.listeners.OrderItemListener;
import sample.model.CartItems;
import sample.model.Item;
import sample.model.Order;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StoreViewController implements Initializable //Ο StoreViewController διαχειρίζεται το FXML storeView
{
    private static DecimalFormat df2 = new DecimalFormat("###,#00.00");
    @FXML
    private ScrollPane orderDetails;
    @FXML
    private GridPane ordersList;
    @FXML
    private Label totalIncomeLabel;
    @FXML
    private Label totalOrdersLabel;
    @FXML
    private Label soldProductsLabel;

    private int ordersCount=0;
    private OrderItemController lastController=null;
    private int lastIdSelected = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) //Καλείται κατά την δημιουργία του storeView και καλεί την setupThread()
    {
        setupThread();
    }

    private ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
    public void setupThread() //Ορίζει πρόγραμμα στο thread ώστε κάθε 5 δευτερόλεπτα να καλείται η checkOrders()
    {
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                checkOrders();
            }
        },0,5,TimeUnit.SECONDS);
    }

    public void checkOrders() //Γίνεται έλεγχος στην βάση για νέες παραγγελίες
    {
        try{
            Connect connect1 = new Connect();
            connect1.connectDB();
            ResultSet rs1 = connect1.getSelect("SELECT COUNT(shoporder.id) FROM shoporder");
            while(rs1.next()){
                if(ordersCount<rs1.getInt(1))
                {
                    ordersCount = rs1.getInt(1);
                    System.out.println(ordersCount);
                    loadOrdersList(false);
                    loadSaleStats();
                }else{
                    System.out.println(ordersCount);
                }
            }
            connect1.connectClose();
        }catch(SQLException e){
            System.out.println("Counter Error");
            e.printStackTrace();
        }
    }

    public void loadOrdersList(boolean updated) // Φορτώνει την λίστα από παραγγελίες
    {
        try{
            Platform.runLater(new Runnable() {
                public void run() {
                    ordersList.getChildren().clear();
                }
            });
            int row = 0;
            OrderItemListener orderItemListener = new OrderItemListener() {
                @Override
                public void orderClicked(int id, OrderItemController orderItemController) {
                    loadOrderDetails(id, orderItemController);
                }
            };
            Connect connect = new Connect();
            connect.connectDB();
            ResultSet rs = connect.getSelect("SELECT shoporder.id, shoporder.status, shoporder.order_time, shipingdetails.firstname, shipingdetails.lastname FROM shoporder INNER JOIN shipingdetails ON shoporder.id=shipingdetails.orderid INNER JOIN tracking ON shoporder.id=tracking.orderid ORDER BY shoporder.order_time DESC");
            while(rs.next()){
                Connect connect2 = new Connect();
                connect2.connectDB();
                int id = rs.getInt("id");
                int status = rs.getInt("status");
                Timestamp timestamp = rs.getTimestamp("order_time");
                String tmstampStr = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(timestamp);
                String name = rs.getString("firstname")+" "+rs.getString("lastname");
                float totalCost = 0;
                ResultSet rs2 = connect2.getSelect("SELECT orderitems.quantity, product.price FROM orderitems LEFT JOIN product ON orderitems.itemid = product.id WHERE orderitems.orderid="+id);
                while (rs2.next()){
                    totalCost+=rs2.getInt("quantity")* rs2.getDouble("price");
                }
                connect2.connectClose();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Main.class.getResource("views/orderItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                OrderItemController orderItemController = fxmlLoader.getController();
                orderItemController.setData(id, tmstampStr, name,df2.format(totalCost)+"€", status, orderItemListener, anchorPane);
                int finalRow = row;
                Platform.runLater(new Runnable() {
                    public void run() {
                        ordersList.add(anchorPane, 0, finalRow);
                        ordersList.setMinWidth(Region.USE_COMPUTED_SIZE);
                        ordersList.setPrefWidth(Region.USE_COMPUTED_SIZE);
                        ordersList.setMaxWidth(Region.USE_PREF_SIZE);

                        ordersList.setMinHeight(Region.USE_COMPUTED_SIZE);
                        ordersList.setPrefHeight(Region.USE_COMPUTED_SIZE);
                        ordersList.setMaxHeight(Region.USE_PREF_SIZE);
                        GridPane.setMargin(anchorPane,new Insets(5));
                    }
                });
                if(updated && id==lastIdSelected){
                    loadOrderDetails(id, orderItemController);
                }
                else if(!updated && row==0){
                    loadOrderDetails(id, orderItemController);
                }
                row++;
            }
            connect.connectClose();

        }catch(SQLException | IOException e){
            System.out.println("Orders List Error");
            e.printStackTrace();
        }
    }
    private void loadSaleStats() //Φορτώνει κάποια στατιστικά πωλήσεων
    {
        try{
            Connect connect = new Connect();
            connect.connectDB();
             ResultSet rs = connect.getSelect("SELECT SUM(product.price*orderitems.quantity) as totalIncome, SUM(orderitems.quantity) as soldProducts, COUNT(orderitems.id) as totalOrders FROM orderitems JOIN product ON orderitems.itemid=product.id");
            double totalIncome= 0;
            int soldProducts= 0;
            int totalOrders = 0;
            if (rs.next())
            {
                totalIncome = rs.getDouble("totalIncome");
                soldProducts = rs.getInt("soldProducts");
                totalOrders = rs.getInt("totalOrders");
            }
            connect.connectClose();
            double finalTotalIncome = totalIncome;
            int finalSoldProducts = soldProducts;
            int finalTotalOrders = totalOrders;
            Platform.runLater(new Runnable() {
                public void run() {
                    totalIncomeLabel.setText(df2.format(finalTotalIncome)+Main.CURRENCY);
                    soldProductsLabel.setText(String.valueOf(finalSoldProducts));
                    totalOrdersLabel.setText(String.valueOf(finalTotalOrders));
                }
            });
        }catch (SQLException e){
            System.out.println("Stats Error");
            e.printStackTrace();
        }
    }

    private void loadOrderDetails(int id, OrderItemController orderItemController) //Φορτώνει τις επιπλέον λεπτομέρειες της παραγγελίας
    {
        System.out.println(id);
        if(lastController != orderItemController){
            if(lastController!=null){
                lastController.setUnselected();
            }
        }
        lastController = orderItemController;
        lastIdSelected = id;
        lastController.setSelected();
        try{
            Connect connect = new Connect();
            connect.connectDB();
            CartItems cartItems= new CartItems();
            Order order = null;
            int status=-1;
            String tmstampStr = "";
            String tracking_number = "";
            ResultSet rs = connect.getSelect("SELECT orderitems.itemid, quantity, product.name, product.price, product.imgsrc FROM orderitems INNER JOIN product ON orderitems.itemid=product.id WHERE orderitems.orderid ="+id);
            while (rs.next()){
                cartItems.addItem(new Item(rs.getString("name"), "Images/"+rs.getString("imgsrc"), rs.getDouble("price"), rs.getInt("itemid")),rs.getInt("quantity"));
            }
            rs = connect.getSelect("SELECT shoporder.status, shoporder.order_time, tracking.tracking_number, shipingdetails.firstname, shipingdetails.lastname, shipingdetails.address, shipingdetails.city, shipingdetails.country FROM shoporder INNER JOIN tracking ON shoporder.id= tracking.orderid INNER JOIN shipingdetails ON shoporder.id = shipingdetails.orderid WHERE shoporder.id="+id);
            if(rs.next()){
                status = rs.getInt("status");
                Timestamp timestamp = rs.getTimestamp("order_time");
                tmstampStr = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(timestamp);
                tracking_number = rs.getString("tracking_number");
                order = new Order(rs.getString("firstname"), rs.getString("lastname"), rs.getString("address"), rs.getString("city"),rs.getString("country"),cartItems);
            }
            connect.connectClose();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("views/selectedOrder.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            SelectedOrderController selectedOrderController = fxmlLoader.getController();
            selectedOrderController.setData(order,tracking_number, status, id, tmstampStr, this);
            Platform.runLater(new Runnable() {
                public void run() {
                    orderDetails.setContent(anchorPane);
                }
            });
        }catch (IOException | SQLException e){
            System.out.println("Selected Order Error");
            e.printStackTrace();
        }
    }

    public void logOut(MouseEvent mouseEvent) //Επιστρέφει στην σελίδα LogIn
    {
        exec.shutdown();
        Main.openMainScene();
    }
}
